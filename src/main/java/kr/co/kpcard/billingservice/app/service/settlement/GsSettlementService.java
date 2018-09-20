package kr.co.kpcard.billingservice.app.service.settlement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bnl.brts.service.BrtsBarCodeLib;

import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettleResultCount;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementMapper;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementResult;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementSelect;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.HttpUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.common.utils.RetrofitHttpManager;
import kr.co.kpcard.protocol.settlement.gs.GsDataRecord;
import kr.co.kpcard.protocol.settlement.gs.GsHeaderRecord;
import kr.co.kpcard.protocol.settlement.gs.GsPointTailRecord;
import kr.co.kpcard.protocol.settlement.gs.GsTailRecord;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Service
public class GsSettlementService {
	
	private Logger logger = LoggerFactory.getLogger(GsSettlementService.class);
	private static final String lineEnd = "\r\n";
	private static final String FILLER = "";
	
	
	@Autowired
	Environment env;
	
	@Autowired
	SettlementJobHistoryService settlementJobHistoryService;
	
	@Autowired
	GsSettlementMapper gsSettlementMapper;
	
	@Autowired
	private RetrofitHttpManager retrofitHttpManager;	
	
	@Value("${upload_file_path}")
	String UPLOAD_FILE_PATH;
	
	
	@Value("${kpc.http.baseUrl}")
	String baseUrl;
	@Value("${kpc.http.pathUrl}")
	String pathUrl;
	
	@Value("${http.gsPop.id}")
	String httpId;	
	@Value("${http.gsPop.upload.name}")
	String uploadName;
	@Value("${http.gsPop.download.name}")
	String downloadName;
	@Value("${http.gsPop.otisOnline}")
	String otisOnline;
	@Value("${http.gsPop.pgOnline}")
	String pgOnline;
	
	private final String JOB_DIVIDER = JobStatusCode.GSPOP;
	
	
	public boolean gsDownloadByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			result = download(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}
	public boolean gsUploadByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			result = upload(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}		
	public boolean gsSettlementByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			result = settlement(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}	

	
	public boolean settlement(int year, int month, int day)
	{
		String settleDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info("대사 시작! :  {}"  ,JOB_DIVIDER);
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JOB_DIVIDER,settleDate,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);
		try
		{
			// 기존 대사 작업 내역 삭제 후 처리
			gsSettlementMapper.deleteSettlement(JOB_DIVIDER,settleDate);
			//cnt = gsSettlementMapper.settlementGspop(JOB_DIVIDER,settleDate , otisOnline, pgOnline);			

			int seq = gsSettlementMapper.selectSeq();
			List<GsSettlementSelect> resultList = gsSettlementMapper.settlementSelectGspop(JOB_DIVIDER, otisOnline, settleDate, pgOnline);
			List<GsSettlementResult> settleResultMatchedList = new ArrayList<GsSettlementResult>();
			List<GsSettlementResult> settleResultUnmatchedList = new ArrayList<GsSettlementResult>();
			GsSettleResultCount resultCount = new GsSettleResultCount();
			GsSettlementResult settleResult = new GsSettlementResult();
			GsSettlementSelect eachValue = new GsSettlementSelect();
			String status,statusDesc;			
			resultCount.setJobDivider(JOB_DIVIDER);
			resultCount.setWorkDt(settleDate);
		
			for(int i=0; i<resultList.size(); i++){				
				settleResult = new GsSettlementResult();
				status="";
				statusDesc="";
				eachValue = resultList.get(i);				
				//거래 데이터 비교 결과
				if(eachValue.getStl1()!=null && eachValue.getStl1().equals("STL-0001"))
				{//거래데이터가 KPC에만 존재
					status = "STS-0003";
					statusDesc = eachValue.getStl1();
				}else if(eachValue.getStl2()!=null && eachValue.getStl2().equals("STL-0002"))
				{//거래데이터가 GS에만 존재
					status = "STS-0004";
					statusDesc = eachValue.getStl2();
				}else if(eachValue.getStl1()!=null || eachValue.getStl2()!=null || eachValue.getStl6()!=null ||	eachValue.getStl7()!=null || eachValue.getStl8()!=null || eachValue.getStl9()!=null)
				{//거래내역 불칠치
					status = "STS-0002";
					statusDesc = (eachValue.getStl1()!=null)? statusDesc+","+eachValue.getStl1() : statusDesc;
					statusDesc = (eachValue.getStl2()!=null)? statusDesc+","+eachValue.getStl2() : statusDesc;
					statusDesc = (eachValue.getStl6()!=null)? statusDesc+","+eachValue.getStl6() : statusDesc;
					statusDesc = (eachValue.getStl7()!=null)? statusDesc+","+eachValue.getStl7() : statusDesc;
					statusDesc = (eachValue.getStl8()!=null)? statusDesc+","+eachValue.getStl8() : statusDesc;
					statusDesc = (eachValue.getStl9()!=null)? statusDesc+","+eachValue.getStl9() : statusDesc;					
				}else
				{//거래내역 일치
					status = "STS-0001";
				}
				
				settleResult.setValue((seq+i),
									  JOB_DIVIDER, 
									  eachValue.getDeal_no(), 
									  eachValue.getApproval_no(),
									  eachValue.getMerchantid(),
									  eachValue.getDeal_type(), 
									  eachValue.getDeal_divider(), 
									  eachValue.getUse_point(),
									  settleDate,
									  eachValue.getSeq(),
									  status,
									  statusDesc,
									  DateUtil.createDate(),
									  eachValue.getApproval_dt(),
									  eachValue.getApproval_time());
				
				if("STS-0001".equals(status)){
					//대사 일치 데이터 등록
					//settleResultMatchedList.add(settleResult);
					//대사 일치 주문 건 수
					resultCount.setSts1((resultCount.getSts1()+1));
					//대사 일치 금액
					resultCount.setStsAmt1(resultCount.getStsAmt1() + settleResult.getOrderAmt());
				}else{
					//대사 불일치 데이터 등록
					settleResultUnmatchedList.add(settleResult);
					
					if("STS-0002".equals(status))
					{						
						resultCount.setSts2(resultCount.getSts2()+1);//대사 불일치 주문 건 수
						resultCount.setStsAmt2(resultCount.getStsAmt2()+settleResult.getOrderAmt());//대사 불일치 금액
					}
					else if("STS-0003".equals(status))
					{
						resultCount.setSts3(resultCount.getSts3()+1);
						resultCount.setStsAmt3(resultCount.getStsAmt3()+settleResult.getOrderAmt());
					}else if("STS-0004".equals(status))
					{
						resultCount.setSts4(resultCount.getSts4()+1);
						resultCount.setStsAmt4(resultCount.getStsAmt4()+settleResult.getOrderAmt());
					}
				}
			}			
			logger.info("settleResultUnmatchedList.size() "+settleResultUnmatchedList.size() );
			//대사 HST 등록
			int fromIndex = 0;
			int toIndex = 0;
			int insertCount = 0;
			int loopCnt = settleResultUnmatchedList.size() < JobStatusCode.LOOPCNT ? 1 : (settleResultUnmatchedList.size()%JobStatusCode.LOOPCNT>0)? ((settleResultUnmatchedList.size()/JobStatusCode.LOOPCNT)+1) : settleResultUnmatchedList.size()/JobStatusCode.LOOPCNT;
			
			//insertCount = gsSettlementMapper.settlementInsert(settleResultUnmatchedList.subList(fromIndex, 1000));
			if(settleResultUnmatchedList.size()>0)
			{
				for(int idx=0; idx<loopCnt; idx++)
				{
					if(loopCnt==1)
					{
						toIndex = settleResultUnmatchedList.size();
					}else
					{
						toIndex = JobStatusCode.LOOPCNT * (idx+1);
					}
					logger.info("fromIndex:"+fromIndex+"/toIndex"+toIndex);
					insertCount += gsSettlementMapper.settlementInsert(settleResultUnmatchedList.subList(fromIndex, toIndex));
					fromIndex = fromIndex + JobStatusCode.LOOPCNT;
				}			
			}
	
			settlementJobHistoryService.jobInsertSettleResultCount(resultCount);			
			settlementJobHistoryService.jobStart(JOB_DIVIDER,settleDate,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);			
			
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("대사 종료 :  {}"  ,JOB_DIVIDER);
			logger.info("대사 결과[SuccessCount : "+resultCount.getSts1()+", SuccessAmount : "+resultCount.getStsAmt1()+"] :  {}"  );
			logger.info("대사 결과[UnmatchedCount : "+(resultCount.getSts2()+resultCount.getSts3()+resultCount.getSts4())+", UnmatchedAmount : "+(resultCount.getStsAmt2()+resultCount.getStsAmt3()+resultCount.getStsAmt4())+"] :  {}"  );
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");			
		}
		catch(Exception e)
		{
			logger.error("대사 중 오류 발생 " , e);
			// 작업 오류 상태 추가용 transaction 생성
			
			settlementJobHistoryService.jobStart(JOB_DIVIDER,settleDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;			
		}
		
		return true;
	}
	
	public boolean download(int year, int month, int day)
	{
		
		String downloadDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		HashMap<String, String> param = new HashMap<String,String>();
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JOB_DIVIDER,downloadDate,JobStatusCode.JOB_FILE_DOWNLOAD_START_CODE,JobStatusCode.JOB_FILE_DOWNLOAD_START_MSG);
		String fileName = downloadName + downloadDate;
		List<String> resultList = null;
		List<GsSettlement> gsDataRecordList = new ArrayList<GsSettlement>();
		BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
		try 
		{
			param.put("type", "download");
			param.put("id", httpId);
			param.put("name", fileName);	
			// 대사 HST data 생성
			Response<ResponseBody> response = retrofitHttpManager.postHttpResponse(baseUrl, pathUrl, param);
			logger.debug("baseUrl : {}", baseUrl);
			logger.debug("pathUrl : {}", pathUrl);
			resultList =retrofitHttpManager.responseBodyToList(response.body());				
			for(String data : resultList)
			{
				String divider = data.substring(0, 1);
				GsHeaderRecord headerRecord;
				GsTailRecord tailRecord; 
				if(GsHeaderRecord.HEADER_DIVIDER.equals(divider))
				{
					headerRecord = new GsHeaderRecord();
					headerRecord.parse(data.getBytes());
				}
				else if(GsDataRecord.DATA_DIVIDER.equals(divider))
				{
					GsDataRecord gsData = new GsDataRecord();
					boolean parseFlag = gsData.parse(data.getBytes());
					if (parseFlag) 
					{
						GsSettlement gsSettlement = new GsSettlement();
						gsSettlement.setWorkDt      (downloadDate                         );
						gsSettlement.setDivider     (GsDataRecord.DATA_DIVIDER            );                                                 
						gsSettlement.setSaleDt      (gsData.getSaleDate()                 );                                                  
						gsSettlement.setStoreCd     (gsData.getStoreCode()                );                                                  
						gsSettlement.setDealNo      (gsData.getDealNo()                   );                                             
						gsSettlement.setApprovalNo  (gsData.getApprovalNo()               );                                                 
						gsSettlement.setApprovalDt  (gsData.getApprovalDate()             );                                                   
						gsSettlement.setApprovalTime(gsData.getApprovalTime()             );
						gsSettlement.setCardNo      (brtsBarCodeLib.DecodeBy(gsData.getCardNo(),9));                                            
						gsSettlement.setOrderAmt    (Long.valueOf(gsData.getOrderAmount()));                                                 
						gsSettlement.setDealAmt     (Long.valueOf(gsData.getDealAmount()) );                                                 
						gsSettlement.setDealType    (gsData.getDealType()                 );   
						gsSettlement.setDealDivider (gsData.getDealDivider()              );                                     
						gsSettlement.setCreditCorp  (gsData.getCreditCorp()               );                                                  
						gsSettlement.setCreditAplNo (gsData.getCreditApprovalNo()         );                                          
						gsSettlement.setCreditAplDt (gsData.getCreditApprovalDate()       );                                           
						gsSettlement.setOrderNo     (gsData.getOrderNo()                  );                                                   
						gsSettlement.setVanDivider  (gsData.getVanDivider()               );                                                 
						gsSettlement.setGsPoint     (Integer.parseInt(gsData.getGsPoint()));
						gsDataRecordList.add(gsSettlement);
					}
				}
				else if(GsPointTailRecord.TAIL_DIVIDER.equals(divider))
				{
					tailRecord = new GsTailRecord();
					tailRecord.parse(data.getBytes());
				}
			}
			if(gsDataRecordList.size() > 0)
			{
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("파싱 끝 insert 시작 :  {}"  ,JOB_DIVIDER);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
				// TODO : 재작업 대비 이전날짜 데이터 삭제 처리 추가 
				int delCount = gsSettlementMapper.delete(downloadDate,JOB_DIVIDER);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("delCount :  {}"  ,delCount);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				// 안정적인  insert 처리를 위해 LOOPCNT 건씩 나눠서 insert 처리
				int loopCnt = gsDataRecordList.size() < JobStatusCode.LOOPCNT ? 0 : gsDataRecordList.size() / JobStatusCode.LOOPCNT;
				int startIdx = 0;
				int endIdx = 0;
				int totalInsertCnt = 0;
				for(int idx = 0 ; idx <= loopCnt ; idx++)
				{
					if(idx == loopCnt)
					{
						endIdx = gsDataRecordList.size();
					}
					else 
					{
						endIdx = (JobStatusCode.LOOPCNT * (idx + 1));
					}					
					totalInsertCnt += gsSettlementMapper.insert(gsDataRecordList.subList(startIdx, endIdx) , JOB_DIVIDER, startIdx);
					startIdx = startIdx + JobStatusCode.LOOPCNT;
				}
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("insert 종료 totalInsertCnt : {} , Total Data cnt : {}" , totalInsertCnt, gsDataRecordList.size());
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				settlementJobHistoryService.jobStart(JOB_DIVIDER,downloadDate,JobStatusCode.JOB_FILE_DOWNLOAD_END_CODE,JobStatusCode.JOB_FILE_DOWNLOAD_END_MSG);
			}
			else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobStart(JOB_DIVIDER,downloadDate,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);					
			}
			
			return true;
		} 
		catch (Exception e) 
		{
			logger.error("download error : {}" , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobStart(JOB_DIVIDER,downloadDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		}		
	}
	public boolean upload(int year, int month, int day)
	{
		String uploadDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		String fileName = uploadName + uploadDate;
		String filePath = "";
		BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("id", httpId);
		param.put("type", "upload");
		param.put("name", "file");	
		param.put("filename", fileName);	
		
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JOB_DIVIDER,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_START_CODE,JobStatusCode.JOB_FILE_UPLOAD_START_MSG);
		try
		{
			// Head Data 
			GsHeaderRecord gssmHeaderRecord = new GsHeaderRecord(GsHeaderRecord.HEADER_DIVIDER,uploadDate,DateUtil.getCurrenntDateYYYYMMDD(),FILLER);
			StringBuffer data = new StringBuffer();
			String header = gssmHeaderRecord.makeRecordData() + lineEnd;
			data.append(header);
			// Content data
			// DB data 조회
			logger.info("otisOnline : {}" , otisOnline);
			logger.info("pgOnline : {}" , pgOnline);
			List<GsSettlement> gsRecordList = null;
			gsRecordList = gsSettlementMapper.gspopSelect(uploadDate , otisOnline, pgOnline);
			logger.info("gsRecordList count  : {}"  , gsRecordList.size());
			GsTailRecord gssmTailRecord = new GsTailRecord();
			for(GsSettlement gsSettlement: gsRecordList)
			{
				// test Data 생성
				GsDataRecord gsDataRecord = new GsDataRecord();
				
				gsDataRecord.setDivider           (GsDataRecord.DATA_DIVIDER);                                                 
				gsDataRecord.setSaleDate          (gsSettlement.getSaleDt      ());                                                  
				gsDataRecord.setStoreCode         (gsSettlement.getStoreCd     ());                                                  
				gsDataRecord.setDealNo            (gsSettlement.getDealNo      ());                                             
				gsDataRecord.setApprovalNo        (gsSettlement.getApprovalNo  ());                                                 
				gsDataRecord.setApprovalDate      (gsSettlement.getApprovalDt  ());                                                   
				gsDataRecord.setApprovalTime      (gsSettlement.getApprovalTime());                                                   
				gsDataRecord.setCardNo            (brtsBarCodeLib.EncodeBy(gsSettlement.getCardNo() , 9));                                            
				gsDataRecord.setOrderAmount       ("");                                                 
				gsDataRecord.setDealAmount        (String.valueOf(gsSettlement.getDealAmt()));                                                 
				gsDataRecord.setDealType          (gsSettlement.getDealType    ());   
				gsDataRecord.setDealDivider       (gsSettlement.getDealDivider ());                                     
				gsDataRecord.setPayType           (gsSettlement.getPayType     ());                                     
//				gsDataRecord.setCreditCorp        (gsSettlement.getCreditCorp  ());                                                  
//				gsDataRecord.setCreditApprovalNo  (gsSettlement.getCreditAplNo ());                                          
//				gsDataRecord.setCreditApprovalDate(gsSettlement.getCreditAplDt ());                                           
//				gsDataRecord.setOrderNo           (gsSettlement.getOrderNo     ());                                                   
//				gsDataRecord.setVanDivider        (gsSettlement.getVanDivider  ());                                                 
//				gsDataRecord.setGsPoint           (String.valueOf(gsSettlement.getGsPoint()));
				gsDataRecord.setFiller            (FILLER);
				
				setTailData(gssmTailRecord, gsSettlement);
				data.append(gsDataRecord.makeRecordData() + lineEnd);
				
			}
			// Tail Data  
			gssmTailRecord.setSendCount(gsRecordList.size());
			gssmTailRecord.setDivider(GsTailRecord.TAIL_DIVIDER);
			gssmTailRecord.setFiller(FILLER);
			logger.info("gssmTailRecord : {} " , gssmTailRecord);
			String tailData = gssmTailRecord.makeRecordData()  + lineEnd;
			data.append(tailData);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("데이터 조회 끝 파일 생성 시작:  {}" ,JOB_DIVIDER);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			
			filePath = UPLOAD_FILE_PATH + "/" + uploadName + uploadDate;
			File file = new File(UPLOAD_FILE_PATH);
			if (!file.exists())
			{
				file.mkdirs();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			out.write(data.toString());
			out.flush();
			out.close();
			
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 끝 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("업로드 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			String url = baseUrl + pathUrl + "?id=" + httpId + "&type=upload&name="+fileName+"&filename=" + fileName;
			HttpUtil.upload(url, filePath , fileName);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" upload  종료 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

			if(gsRecordList.size() > 0)
			{
				// 대사 종료 HST   
				settlementJobHistoryService.jobStart(JOB_DIVIDER,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_END_CODE,JobStatusCode.JOB_FILE_UPLOAD_END_MSG);
			}
			else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobStart(JOB_DIVIDER,uploadDate,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}
		}
		catch(Exception e)
		{
			logger.error("downloadGsPopcardDealDetails " , e);
			settlementJobHistoryService.jobStart(JOB_DIVIDER,uploadDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		} 
		finally
		{
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" 파일삭제");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			// 파일 삭제
			try
			{
				new File(filePath).delete();			
			}
			catch(Exception e)
			{
			}			
		}
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info(" gsDailySettlementUpload  종료 ");
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		return true;  
	}
	
	public void setTailData(GsTailRecord gssmTailRecord,GsSettlement gsSettlement)
	{
		if ( "0".equals(gsSettlement.getDealDivider()) ) 
		{
			if ("ACT".equals(gsSettlement.getDealType()) || "CHR".equals(gsSettlement.getDealType()))
			{//활성화 재충전
				gssmTailRecord.setActiveCount(gssmTailRecord.getActiveCount() + 1);
				gssmTailRecord.setActiveAmount(gssmTailRecord.getActiveAmount() + gsSettlement.getDealAmt());
			} 
			else if ("PAY".equals(gsSettlement.getDealType())) 
			{	//결제
				gssmTailRecord.setPaymentCount(gssmTailRecord.getPaymentCount() + 1);
				gssmTailRecord.setPaymentAmount(gssmTailRecord.getPaymentAmount() + gsSettlement.getDealAmt());
			} 
			else if ("REF".equals(gsSettlement.getDealType()))
			{// 환불
				gssmTailRecord.setRefundCount(gssmTailRecord.getRefundCount() + 1);
				gssmTailRecord.setRefundSum(gssmTailRecord.getRefundSum() + gsSettlement.getDealAmt());
			} else if ("TRN".equals(gsSettlement.getDealType()))
			{// 이체
				gssmTailRecord.setTransCount(gssmTailRecord.getTransCount() + 1);
				gssmTailRecord.setTransSum(gssmTailRecord.getTransSum() + gsSettlement.getDealAmt());
			}
		} 
		else 
		{
			if ("ACT".equals(gsSettlement.getDealType()) || "CHR".equals(gsSettlement.getDealType()))
			{//활성화 재충전
				gssmTailRecord.setActivecancelCount(gssmTailRecord.getActivecancelCount() + 1);
				gssmTailRecord.setActiveCancelAmount(gssmTailRecord.getActiveCancelAmount() + gsSettlement.getDealAmt());
			} 
			else if ("PAY".equals(gsSettlement.getDealType())) 
			{	//결제
				gssmTailRecord.setPaymentCancelCount(gssmTailRecord.getPaymentCancelCount() + 1);
				gssmTailRecord.setPaymentCancelAmount(gssmTailRecord.getPaymentCancelAmount() + gsSettlement.getDealAmt());
			} 
			else if ("REF".equals(gsSettlement.getDealType()))
			{// 환불
				gssmTailRecord.setRefundCount(gssmTailRecord.getRefundCount() + 1);
				gssmTailRecord.setRefundSum(gssmTailRecord.getRefundSum() + gsSettlement.getDealAmt());
			} 
			else if ("TRN".equals(gsSettlement.getDealType()))
			{// 이체
				gssmTailRecord.setTransCount(gssmTailRecord.getTransCount() + 1);
				gssmTailRecord.setTransSum(gssmTailRecord.getTransSum() + gsSettlement.getDealAmt());
			}
		}		
	}

}
