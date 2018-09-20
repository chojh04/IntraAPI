package kr.co.kpcard.billingservice.app.service.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.settlement.hm.HappyMoneySettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.hm.HappyMoneySettlementMapper;
import kr.co.kpcard.billingservice.app.repository.settlement.hm.HappymoneyApiSettlementBaseApiObject;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.common.utils.RetrofitHttpManager;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class HappyMoneySettlementService {
	
	private Logger logger = LoggerFactory.getLogger(HappyMoneySettlementService.class);
	
	@Autowired
	private SettlementJobHistoryService settlementJobHistoryService;
	
	@Autowired
	private RetrofitHttpManager retrofitHttpManager;
	
	@Autowired
	private HappyMoneySettlementMapper happyMoneySettlementMapper;
	
	@Value("${hm.http.baseUrl}")
	String baseUrl;
	@Value("${hm.http.pathUrl}")
	String pathUrl;
	
	public boolean hmDownloadByDuration(Date startDate, Date endDate)
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
	
	public boolean hmSettlementByDuration(Date startDate, Date endDate)
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
		logger.info("대사 시작! :  {}"  ,JobStatusCode.HAPPYMONEY);
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");				
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,settleDate,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);
		try
		{
			// 기존 대사 작업 내역 삭제 후 처리
			happyMoneySettlementMapper.deleteSettlement(JobStatusCode.HAPPYMONEY,settleDate);
			int cnt = 0;
			cnt = happyMoneySettlementMapper.settlement(settleDate);
			settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,settleDate,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("대사 종료 :  {}"  ,JobStatusCode.HAPPYMONEY);
			logger.info("대사 CNT :  {}"  ,cnt);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");			
		}
		catch(Exception e)
		{
			logger.error("대사 중 오류 발생 " , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,settleDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;			
		}
		
		return true;
	}	
	
	public boolean download(int year, int month,int day){
		
		String date = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("payMethod", "0");
		param.put("dailyDate", date);
		
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,date,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);
		try
		{
			logger.info("baseUrl : {} " , baseUrl);
			logger.info("pathUrl : {} " , pathUrl);
			Call<?> call = retrofitHttpManager.getHttpResponseByWildcard(baseUrl, pathUrl, param);
			Response<?> response = call.execute();
			logger.info("response.code : {}" , response.code());
			HappymoneyApiSettlementBaseApiObject happyMoneySettlementBaseApiObject = (HappymoneyApiSettlementBaseApiObject) response.body();
			logger.debug("happyMoneySettlementBaseApiObject : {} " , happyMoneySettlementBaseApiObject);
			if(happyMoneySettlementBaseApiObject.getTotalCount() > 0){
				@SuppressWarnings("unchecked")
				List<HappyMoneySettlement> happyMoneySettlementList = (List<HappyMoneySettlement>) happyMoneySettlementBaseApiObject.getList();
				logger.info("happyMoneySettlement : {}" , happyMoneySettlementList);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("파싱 끝 insert 시작 :  ");
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				// TODO : 재작업 대비 이전날짜 데이터 삭제 처리 추가
				happyMoneySettlementMapper.delete(date);
				// 안정적인  insert 처리를 위해 LOOPCNT 건씩 나눠서 insert 처리
				int loopCnt = happyMoneySettlementList.size() < JobStatusCode.LOOPCNT ? 0 : happyMoneySettlementList.size() / JobStatusCode.LOOPCNT;
				logger.debug("happyMoneySettlementList.size() : {} " , happyMoneySettlementList.size());
				logger.debug("loopCnt : {} " , loopCnt);
				int startIdx = 0;
				int endIdx = 0;
				int totalInsertCnt = 0;
				for(int idx = 0 ; idx <= loopCnt ; idx++)
				{
					if(idx == loopCnt)
					{
						endIdx = happyMoneySettlementList.size();
					}
					else 
					{
						endIdx = (JobStatusCode.LOOPCNT * (idx + 1));
					}					
					totalInsertCnt += happyMoneySettlementMapper.insert(happyMoneySettlementList.subList(startIdx, endIdx) , date);
					startIdx = startIdx + JobStatusCode.LOOPCNT;
				}			
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("insert 종료 total cnt : {}" , totalInsertCnt);
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,date,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);
			} else {
				settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,date,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}
		}
		catch(Exception e)
		{
			logger.error("happyMoneySettlement" , e);
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSRETAIL,date,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());			
			return false;
		}
		return true;
	}

}
