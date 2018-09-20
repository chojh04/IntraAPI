package kr.co.kpcard.billingservice.app.service.settlement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bnl.brts.service.BrtsBarCodeLib;

import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementResult;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettleResultCount;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsPointSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsPointSettlementMapper;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementSelect;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementMapper;
import kr.co.kpcard.common.utils.ApacheFtpManager;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.common.utils.StringUtil;
import kr.co.kpcard.protocol.settlement.gs.GsPointDataRecord;
import kr.co.kpcard.protocol.settlement.gs.GsPointHeaderRecord;
import kr.co.kpcard.protocol.settlement.gs.GsPointTailRecord;

@Service
public class GsPointSettlementService{
	
	private Logger logger = LoggerFactory.getLogger(GsPointSettlementService.class);
	private static final String lineEnd = "\r\n";
	private static final String FILLER = "";
	
	@Autowired
	private GsPointSettlementMapper gsPointSettlementMapper;
	
	@Autowired
	private ApacheFtpManager apacheFtpManager;
	
	@Autowired
	private SettlementJobHistoryService settlementJobHistoryService;
	
	@Autowired
	GsSettlementMapper gsSettlementMapper;	
	
	@Value("${upload_file_path}")
	String UPLOAD_FILE_PATH;	
	@Value("${ftp.serverIp}")
	String ftpServerIp;
	@Value("${ftp.path}")
	String ftpPath;
	@Value("${ftp.id}")
	String ftpId;
	@Value("${ftp.password}")
	String password;
	@Value("${http.gsPoint.otisOnline}")
	String otisOnline;	
	@Value("${ftp.gsPoint.download.name}")
	String downloadFileName;
	@Value("${ftp.gsPoint.upload.name}")
	String uploadFileName;
	
	public boolean gsDownloadByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			result = gsPointDailySettlementDownload(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
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
			result = gsPointDailySettlementUpload(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
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
			result = gsPointDailySettlement(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}	
	
	public boolean gsPointDailySettlement(int year, int month, int day)
	{
		String settleDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,settleDate,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info("대사 시작! :  {}"  ,JobStatusCode.GSPOINT);
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");		
		try
		{
			// 기존 대사 작업 내역 삭제 후 처리
			gsSettlementMapper.deleteSettlement(JobStatusCode.GSPOINT,settleDate);
			//int cnt = gsPointSettlementMapper.settlement(JobStatusCode.GSPOINT,settleDate);	
			
			int seq = gsSettlementMapper.selectSeq();
			List<GsSettlementSelect> resultList = gsPointSettlementMapper.settlementSelect(JobStatusCode.GSPOINT, otisOnline, settleDate);
			List<GsSettlementResult> settleResultMatchedList = new ArrayList<GsSettlementResult>();
			List<GsSettlementResult> settleResultUnmatchedList = new ArrayList<GsSettlementResult>();
			GsSettleResultCount resultCount = new GsSettleResultCount();
			GsSettlementResult settleResult = new GsSettlementResult();
			String status,statusDesc = "";
			resultCount.setJobDivider(JobStatusCode.GSPOINT);
			resultCount.setWorkDt(settleDate);
			logger.info("resultList"+resultList.size());
			for(GsSettlementSelect eachValue : resultList){
				settleResult = new GsSettlementResult();
				status = "";
				statusDesc = "";				
				seq=seq++;
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
				
				settleResult.setValue((seq),
									  JobStatusCode.GSPOINT, 
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
					settleResultMatchedList.add(settleResult);
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
					}else if("STS-0003".equals(status))
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
			//대사 HST 등록
			if(settleResultUnmatchedList.size()>0){
				int cnt = gsPointSettlementMapper.settlementInsert(settleResultUnmatchedList);
			}
			settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,settleDate,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);
			settlementJobHistoryService.jobInsertSettleResultCount(resultCount);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("대사 종료 :  {}"  ,JobStatusCode.GSPOINT);
			logger.info("대사 결과[SuccessCount : "+resultCount.getSts1()+", SuccessAmount : "+resultCount.getStsAmt1()+"] :  {}"  );
			logger.info("대사 결과[UnmatchedCount : "+(resultCount.getSts2()+resultCount.getSts3()+resultCount.getSts4())+", UnmatchedAmount : "+(resultCount.getStsAmt2()+resultCount.getStsAmt3()+resultCount.getStsAmt4())+"] :  {}"  );
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");			
		}
		catch(Exception e)
		{
			logger.error("대사 작업중 오류발생 : {}" , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSPOINT,settleDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;			
		}
		
		return true;
	}
		

	public boolean gsPointDailySettlementDownload(int year, int month, int day) {
		
		String downloadDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,downloadDate,JobStatusCode.JOB_FILE_DOWNLOAD_START_CODE,JobStatusCode.JOB_FILE_DOWNLOAD_START_MSG);
		
		try {
			logger.debug("ftpServerIp : {}" , ftpServerIp);
			apacheFtpManager.connect(ftpServerIp , ftpId, password); 
			List<String> resultList = apacheFtpManager.getFileDataToList(downloadFileName + downloadDate,ftpPath);
			List<GsPointSettlement> gsPointDataRecordList = new ArrayList<GsPointSettlement>();
			BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
			for(String data : resultList){
				
				String divider = data.substring(0, 1);
				
				GsPointHeaderRecord headerRecord;
				GsPointTailRecord tailRecord; 
				if(GsPointHeaderRecord.HEADER_DIVIDER.equals(divider)){
					headerRecord = new GsPointHeaderRecord();
					headerRecord.parse(data.getBytes());
				}
				else if(GsPointDataRecord.DATA_DIVIDER.equals(divider)){
					GsPointDataRecord gsPointData = new GsPointDataRecord();
					boolean parseFlag = gsPointData.parse(data.getBytes());
					if (parseFlag) {
						GsPointSettlement gsPointSettlement = new GsPointSettlement();
						gsPointSettlement.setWorkDt(downloadDate);
						gsPointSettlement.setDivider(StringUtil.trim(gsPointData.getDivider()));
						gsPointSettlement.setSeqNo(StringUtil.trim(gsPointData.getSeqNo()));
						gsPointSettlement.setCardNo(brtsBarCodeLib.DecodeBy(StringUtil.trim(gsPointData.getCardNo()),9));
						gsPointSettlement.setDealDt(StringUtil.trim(gsPointData.getDealDate()));
						logger.debug("gsPointData.getDealDate() : {} " , gsPointData.getDealDate());
						gsPointSettlement.setStoreCd(StringUtil.trim(gsPointData.getStoreCode()));
						gsPointSettlement.setDealDivider(StringUtil.trim(gsPointData.getDealDivider()));
						gsPointSettlement.setUsePoint(StringUtil.trim(gsPointData.getUsePoint()));
						gsPointSettlement.setApprovalNo(StringUtil.trim(gsPointData.getApprovalNo()));
						gsPointSettlement.setApprovalDt(StringUtil.trim(gsPointData.getApprovalDate()));
						gsPointSettlement.setOrigAplDt(StringUtil.trim(gsPointData.getOrignApprovalDate()));
						gsPointSettlement.setOrigAplNo(StringUtil.trim(gsPointData.getOrignApprovalNo()));
						gsPointSettlement.setFiller(StringUtil.trim(gsPointData.getFiller()));
						gsPointDataRecordList.add(gsPointSettlement);
					}
				}
				else if(GsPointTailRecord.TAIL_DIVIDER.equals(divider)){
					tailRecord = new GsPointTailRecord();
					tailRecord.parse(data.getBytes());
				}
			}
			if(gsPointDataRecordList.size() > 0){
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("파싱 끝 insert 시작 :  " + gsPointDataRecordList.size());
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
				// TODO : 재작업 대비 이전날짜 데이터 삭제 처리 추가 
				gsPointSettlementMapper.delete(downloadDate);
				// 안정적인  insert 처리를 위해 LOOPCNT 건씩 나눠서 insert 처리
				int loopCnt = gsPointDataRecordList.size() < JobStatusCode.LOOPCNT ? 0 : gsPointDataRecordList.size() / JobStatusCode.LOOPCNT;
				logger.debug("loopCnt : {}" , loopCnt);
				int startIdx = 0;
				int endIdx = 0;
				for(int idx = 0 ; idx <= loopCnt ; idx++){
					if(idx == loopCnt){
						endIdx = gsPointDataRecordList.size();
					}else {
						endIdx = (JobStatusCode.LOOPCNT * (idx + 1));
					}					
					gsPointSettlementMapper.insert(gsPointDataRecordList.subList(startIdx, endIdx), startIdx);
					startIdx = startIdx + JobStatusCode.LOOPCNT;
				}
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("insert 종료 ");
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,downloadDate,JobStatusCode.JOB_FILE_DOWNLOAD_END_CODE,JobStatusCode.JOB_FILE_DOWNLOAD_END_MSG);
			}else {
				// 데이터없음   
				settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSPOINT,downloadDate,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);					
			}
			
			return true;
		} catch (Exception e) {
			logger.error("gsPointDailySettlementDownload " , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSRETAIL,downloadDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		}
	}
	
	public boolean gsPointDailySettlementUpload(int year, int month, int day)
	{
		Date settleDate = DateUtil.createDate(year, month, day);
		String uploadDate = DateUtil.getDateFormatString(settleDate , DateUtil.DATE_FORMAT);
		String dealDate = DateUtil.getDateFormatString(DateUtil.addDay(settleDate, -1), DateUtil.DATE_FORMAT);
		String filePath = "";
		
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_START_CODE,JobStatusCode.JOB_FILE_UPLOAD_START_MSG);
		try
		{
			BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
			
			StringBuffer data = new StringBuffer();
			
			// Head Data 
			GsPointHeaderRecord gsPointHeaderRecord = new GsPointHeaderRecord();
			gsPointHeaderRecord.setRecord(GsPointHeaderRecord.HEADER_DIVIDER,dealDate,DateUtil.getCurrentDate(DateUtil.DATETIME_FORMAT),FILLER);
			String header = gsPointHeaderRecord.makeRecordData() + lineEnd;
			data.append(header);
			// Content data
			// DB data 조회
			List<GsPointSettlement> gsRecordList = gsPointSettlementMapper.select(uploadDate);
			int cnt = 1;
			for(GsPointSettlement gsSettlement: gsRecordList)
			{
				// test Data 생성
				GsPointDataRecord gsDataRecord = new GsPointDataRecord();
				gsDataRecord.setRecord(GsPointDataRecord.DATA_DIVIDER, String.valueOf(cnt++), brtsBarCodeLib.EncodeBy(gsSettlement.getCardNo(),9), gsSettlement.getStoreCd()
						, gsSettlement.getDealDt(), gsSettlement.getDealDivider(), gsSettlement.getUsePoint(), gsSettlement.getApprovalDt(), gsSettlement.getApprovalNo()
						, "", "", FILLER);
				
				data.append(gsDataRecord.makeRecordData() + lineEnd);
				
			}
			GsPointTailRecord gsPointTailRecord = new GsPointTailRecord();
			gsPointTailRecord .setRecrd(GsPointTailRecord.TAIL_DIVIDER, String.valueOf(gsRecordList.size()), FILLER);
			// Tail Data  
			logger.info("gssmTailRecord : {} " , gsPointTailRecord);
			String tailData = gsPointTailRecord.makeRecordData()  + lineEnd;
			data.append(tailData);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("데이터 조회 끝 파일 생성 시작:  " + gsRecordList.size());
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			
			filePath = UPLOAD_FILE_PATH + "/" + uploadFileName + uploadDate;
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
			logger.debug("ftpServerIp : {}" , ftpServerIp);
			apacheFtpManager.connect(ftpServerIp , ftpId, password);
			apacheFtpManager.upload(ftpPath, filePath, uploadFileName+uploadDate);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" upload  종료 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

			if(gsRecordList.size() > 0)
			{
				// 대사 종료 HST   
				settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_END_CODE,JobStatusCode.JOB_FILE_UPLOAD_END_MSG);
			}
			else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,uploadDate,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}
		}
		catch(Exception e)
		{
			logger.error("downloadGsPopcardDealDetails " , e);
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSPOINT,uploadDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
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

}
