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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bnl.brts.service.BrtsBarCodeLib;

import kr.co.kpcard.billingservice.app.repository.settlement.popcard.PopCardDealsSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.popcard.PopCardDealsSettlementMapper;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.HttpUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.common.utils.RetrofitHttpManager;
import kr.co.kpcard.protocol.settlement.popcard.PopDataRecord;
import kr.co.kpcard.protocol.settlement.popcard.PopHeaderRecord;
import kr.co.kpcard.protocol.settlement.popcard.PopTailRecord;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Service
public class PopCardSettlementService {

	private Logger logger = LoggerFactory.getLogger(PopCardSettlementService.class);

	public static final String CHARSET = "EUC-KR";
	public static final String lineEnd = "\r\n";
	
	@Value("${upload_file_path}")
	String UPLOAD_FILE_PATH;
	
	@Value("${kpc.http.baseUrl}")
	String baseUrl;
	@Value("${kpc.http.pathUrl}")
	String pathUrl;
	@Value("${http.gsPopCard.id}")
	String id;
	@Value("${http.gsPopCard.download.name}")
	String downloadName;	
	@Value("${http.gsPopCard.upload.name}")
	String uploadName;
	
	@Autowired
	private RetrofitHttpManager retrofitHttpManager;
	
	@Autowired
	private PopCardDealsSettlementMapper popCardSettlementMapper;
	
	@Autowired
	private SettlementJobHistoryService settlementJobHistoryService;
	
	
	// (1) 거래 이력 대사 저장 및 업로드 잡 
	//1. GS쪽 팝카드 거래이력  파일 메모리에 로드한다.  
	// 2. 메모리에 로드된 파일을 파싱하고, DB에 저장한다. (GS 대사 파일 저장, 테이블은 신규로 생성한다.
	public boolean downloadGsPopcardDealDetails(int year, int month, int day)
	{
		String date = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		HashMap<String, String> param = new HashMap<String,String>();
		BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
		param.put("id", id);
		param.put("type", "download");
		param.put("name", downloadName + date);	
		param.put("baseUrl", baseUrl);
		param.put("pathUrl", pathUrl);			
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.POPCARD,date,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);
		try
		{
			Response<ResponseBody> response = retrofitHttpManager.postHttpResponse(baseUrl, pathUrl, param);
			logger.info(response.headers().toString());
			List<String> resultList =retrofitHttpManager.responseBodyToListByCharset(response.body(),CHARSET);
			logger.info("response.body() : {} " , response.body().string());
			//retrofitHttpManager.writeResponseBodyToDisk(response.body() , name + date);
			List<PopCardDealsSettlement> popDataRecordList = new ArrayList<PopCardDealsSettlement>();
			for(String data : resultList)
			{
				String divider = data.substring(0, 1);
				PopHeaderRecord headerRecord;
				PopTailRecord tailRecord; 
				if(PopHeaderRecord.HEADER_DIVIDER.equals(divider))
				{
					headerRecord = new PopHeaderRecord();
					headerRecord.parse(data.getBytes());
				}
				else if(PopTailRecord.TAIL_DIVIDER.equals(divider))
				{
					tailRecord = new PopTailRecord();
					tailRecord.parse(data.getBytes());
				}
				else
				{
					PopDataRecord popCardData = new PopDataRecord();
					PopCardDealsSettlement popCardSettlement = new PopCardDealsSettlement();
					boolean parseFlag = popCardData.parseToComma(data);
					if (parseFlag) 
					{
						BeanUtils.copyProperties(popCardData, popCardSettlement);
						popCardSettlement.setWorkDt(date);
						popCardSettlement.setCardNo(brtsBarCodeLib.EncodeBy(popCardData.getCardNo(), 9));
						popDataRecordList.add(popCardSettlement);
					}
				}
				// DB 저장 추가
			}		
			if(popDataRecordList.size() > 0)
			{
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("파싱 끝 insert 시작 :  " + popDataRecordList.size());
				logger.info("파싱 끝 insert 시작 :  " + date);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				// 재작업 대비 이전날짜 데이터 삭제 처리 추가 
				popCardSettlementMapper.delete(date);
				// 안정적인  insert 처리를 위해 LOOPCNT 건씩 나눠서 insert 처리
				int loopCnt = popDataRecordList.size() < JobStatusCode.LOOPCNT ? 1 : popDataRecordList.size() / JobStatusCode.LOOPCNT;
				int startIdx = 0;
				int endIdx = 0;
				for(int idx = 0 ; idx <= loopCnt ; idx++)
				{
					if(idx == loopCnt)
					{
						endIdx = popDataRecordList.size();
					}else 
					{
						endIdx = (JobStatusCode.LOOPCNT * (idx + 1));
					}
					popCardSettlementMapper.insert(popDataRecordList.subList(startIdx,endIdx));
					startIdx = startIdx + JobStatusCode.LOOPCNT;
				}
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("insert 종료 ");
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
				settlementJobHistoryService.jobUpdateStatus(JobStatusCode.POPCARD,date,JobStatusCode.JOB_SETTLE_INS_END_CODE,JobStatusCode.JOB_SETTLE_INS_END_MSG);
				// TODO : insert 후 대사 시작 job을 분리할지 결정 필요
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("대사 종료 ");
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
				settlementJobHistoryService.jobStart(JobStatusCode.POPCARD,date,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);
			}else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobUpdateStatus(JobStatusCode.POPCARD,date,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}			
		}catch(Exception e)
		{
			logger.error("downloadGsPopcardDealDetails " , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.POPCARD,date,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		}
		return true; 
	}
	
	public boolean downloadGsPopcardDealDetailsByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			logger.info("addDay : {}" , addDay);
			logger.info("downloadDate : {}" , DateUtil.getDateFormatString(downloadDate, DateUtil.DATE_FORMAT));
			result = downloadGsPopcardDealDetails(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}
	
	public boolean uploadGsPopcardDealDetailsByDuration(Date startDate, Date endDate)
	{
		boolean result = true;
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			logger.info("addDay : {}" , addDay);
			logger.info("downloadDate : {}" , DateUtil.getDateFormatString(downloadDate, DateUtil.DATE_FORMAT));
			result = uploadKpcPopCardDealDetails(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
		return result;
	}	
	
	// 3. KPC 팝카드 거래 이력을 조회한다. 
	// 4. 조회한 거래 이력을 업로드한다.
	public boolean uploadKpcPopCardDealDetails(int year, int month, int day)
	{
		String date = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		String fileName = uploadName + date;
		String filePath = "";
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("id", id);
		param.put("type", "upload");
		param.put("name", "file");	
		param.put("filename", fileName);	
	
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.POPCARD,date,JobStatusCode.JOB_FILE_UPLOAD_START_CODE,JobStatusCode.JOB_FILE_UPLOAD_START_MSG);
		try
		{
			// Head Data 
			PopHeaderRecord popHeaderRecord = new PopHeaderRecord(PopHeaderRecord.HEADER_DIVIDER,date,DateUtil.getCurrenntDateYYYYMMDD(),"");
			StringBuffer data = new StringBuffer();
			String header = popHeaderRecord.makeRecordData() + lineEnd;
			data.append(header);
			// Content data
			// DB data 조회
			List<PopCardDealsSettlement> popDataRecordList = popCardSettlementMapper.select(date);
			logger.info("popDataRecordList : {} " , popDataRecordList.size());
			for(PopCardDealsSettlement popCardSettlement: popDataRecordList){
				// test Data 생성
				PopDataRecord popCardData = new PopDataRecord();
				popCardData.setDealDate       (popCardSettlement.getDealDate       ()); // 거래일자     
				popCardData.setOrderNo        (popCardSettlement.getOrderNo        ()); // 주문번호     
				popCardData.setApprovalNo     (popCardSettlement.getApprovalNo     ()); // 승인번호      
				popCardData.setDealDivider    (popCardSettlement.getDealDivider    ()); // 거래구분     
				popCardData.setCardNo         (popCardSettlement.getCardNo         ()); // 카드번호     
				popCardData.setUseDate        (popCardSettlement.getUseDate        ()); // 사용시간     
				popCardData.setAmount         (popCardSettlement.getAmount         ()); // 금액     
				popCardData.setBalance        (popCardSettlement.getBalance        ()); // 잔액     
				popCardData.setMerchantCode   (popCardSettlement.getMerchantCode   ()); // 업체코드     
				popCardData.setMerchantName   (popCardSettlement.getMerchantName   ()); // 업체명     
				popCardData.setOnOffLineDivier(popCardSettlement.getOnOffLineDivier()); // 온,오프라인구분     
				popCardData.setCardDivider    (popCardSettlement.getCardDivider    ()); // 카드구분     
				data.append(popCardData.makeRecordData() + lineEnd);
				
			}
			// Tail Data  
			PopTailRecord popTailRecord = new PopTailRecord(PopTailRecord.TAIL_DIVIDER,String.valueOf(popDataRecordList.size()),"");
			String tailData = popTailRecord.makeRecordData()  + lineEnd;
			data.append(tailData);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("데이터 조회 끝 파일 생성 시작:  " + popDataRecordList.size());
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			
			filePath = UPLOAD_FILE_PATH + File.separator + uploadName + date;
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
			HttpUtil.upload(baseUrl + pathUrl + "?id=" + id + "&type=upload&name="+fileName+"&filename=" + fileName, filePath , fileName);
			
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" upload  종료 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" 파일삭제");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			// 파일 삭제
			new File(filePath).delete();
			if(popDataRecordList.size() > 0)
			{
				// 대사 종료 HST   
				settlementJobHistoryService.jobStart(JobStatusCode.POPCARD,date,JobStatusCode.JOB_FILE_UPLOAD_END_CODE,JobStatusCode.JOB_FILE_UPLOAD_END_MSG);
			}
			else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobStart(JobStatusCode.POPCARD,date,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}
		}
		catch(Exception e)
		{
			logger.error("downloadGsPopcardDealDetails " , e);
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.POPCARD,date,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		}
		return true;  
	}
	
	
	
}
