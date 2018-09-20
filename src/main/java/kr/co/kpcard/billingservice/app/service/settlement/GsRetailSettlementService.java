package kr.co.kpcard.billingservice.app.service.settlement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsRetailSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsRetailSettlementMapper;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.common.utils.RetrofitHttpManager;
import kr.co.kpcard.common.utils.StringUtil;
import kr.co.kpcard.protocol.settlement.gs.GsrDataRecord;
import kr.co.kpcard.protocol.settlement.gs.GsrHeaderRecord;
import kr.co.kpcard.protocol.settlement.gs.GsrTailRecord;
import kr.co.kpcard.protocol.settlement.happycash.HappyCashRecord;
import kr.co.kpcard.protocol.settlement.happycash.HappyCashTailRecord;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Service
@Deprecated
public class GsRetailSettlementService{
	
	private Logger logger = LoggerFactory.getLogger(GsRetailSettlementService.class);
	
	@Value("${kpc.http.baseUrl}")
	String baseUrl;
	@Value("${kpc.http.baseUrl}")
	String pathUrl;
	@Value("${http.gsRetail.id}")
	String id;
	@Value("${http.gsRetail.name}")
	String name;
	
	@Autowired
	private RetrofitHttpManager retrofitHttpManager;
	@Autowired
	private SettlementJobHistoryService settlementJobHistoryService;	
	@Autowired
	private GsRetailSettlementMapper gsRetailSettlementMapper;
	
	public boolean gsRetailDailySettlement(int year, int month, int day) {
		
		String date = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("id", id);
		param.put("type", "download");
		param.put("name", name + date);	
		param.put("baseUrl", baseUrl);
		param.put("pathUrl", pathUrl);		
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.GSRETAIL,date,JobStatusCode.JOB_WORKING_CODE,JobStatusCode.JOB_WORKING_MSG);		

		try {
			Response<ResponseBody> response = retrofitHttpManager.postHttpResponse(param.get("baseUrl"), param.get("pathUrl"), param);
			List<String> resultList =retrofitHttpManager.responseBodyToList(response.body());
			logger.info("resultList : {}" , resultList);
			List<GsRetailSettlement> gsRetailDataRecordList = new ArrayList<GsRetailSettlement>();
			for(String data : resultList){
				
				String divider = data.substring(0, 1);
				GsrHeaderRecord headerRecord;
				GsrTailRecord tailRecord; 
				if(HappyCashRecord.HEADER_DIVIDER.equals(divider)){
					headerRecord = new GsrHeaderRecord();
					headerRecord.parse(data.getBytes());
				}
				else if(GsrDataRecord.DATA_DIVIDER.equals(divider)){
					GsrDataRecord gsRetailChargeData = new GsrDataRecord();
					boolean parseFlag = gsRetailChargeData.parse(data.getBytes());
					if (parseFlag) {
						GsRetailSettlement gsRetailSettlement = new GsRetailSettlement();
						gsRetailSettlement.setWorkDt(param.get("startDate"));
						gsRetailSettlement.setDivider(StringUtil.trim(gsRetailChargeData.getDivider()));
						gsRetailSettlement.setSaleDt(StringUtil.trim(gsRetailChargeData.getSaleDate()));
						gsRetailSettlement.setStoreCd(StringUtil.trim(gsRetailChargeData.getStoreCode()));
						gsRetailSettlement.setDealNo(StringUtil.trim(gsRetailChargeData.getDealNo()));
						gsRetailSettlement.setApprovalNo(StringUtil.trim(gsRetailChargeData.getApprovalNo()));
						gsRetailSettlement.setApprovalDt(StringUtil.trim(gsRetailChargeData.getApprovalDate()));
						gsRetailSettlement.setApprovalTime(StringUtil.trim(gsRetailChargeData.getApprovalTime()));
						gsRetailSettlement.setCardNo(StringUtil.trim(gsRetailChargeData.getCardNo()));
						gsRetailSettlement.setDealAmt(StringUtil.strToLong(gsRetailChargeData.getDealAmount()));
						gsRetailSettlement.setPaymentAmt(StringUtil.strToLong(gsRetailChargeData.getPaymentAmount()));
						gsRetailSettlement.setBalance(StringUtil.strToLong(gsRetailChargeData.getBalance()));
						gsRetailSettlement.setDealDivider(StringUtil.trim(gsRetailChargeData.getDealDivider()));
						gsRetailSettlement.setResponseCd(StringUtil.trim(gsRetailChargeData.getResponseCode()));
						gsRetailDataRecordList.add(gsRetailSettlement);
					}
				}
				else if(HappyCashTailRecord.TAIL_DIVIDER.equals(divider)){
					tailRecord = new GsrTailRecord();
					tailRecord.parse(data.getBytes());
				}
				// DB 저장 추가
			}
			if(gsRetailDataRecordList.size() > 0){
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("파싱 끝 insert 시작 :  " + gsRetailDataRecordList.size());
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				// TODO : 재작업 대비 이전날짜 데이터 삭제 처리 추가 필요
				gsRetailSettlementMapper.delete(param.get("startDate"));
				// 안정적인  insert 처리를 위해 LOOPCNT 건씩 나눠서 insert 처리
				int loopCnt = gsRetailDataRecordList.size() < JobStatusCode.LOOPCNT ? 1 : gsRetailDataRecordList.size() / JobStatusCode.LOOPCNT;
				int startIdx = 0;
				int endIdx = 0;
				for(int idx = 0 ; idx <= loopCnt ; idx++){
					if(idx == loopCnt){
						endIdx = gsRetailDataRecordList.size();
					}else {
						endIdx = (JobStatusCode.LOOPCNT * (idx + 1));
					}
					gsRetailSettlementMapper.insertSettlement(gsRetailDataRecordList.subList(startIdx,endIdx), startIdx);
					startIdx = startIdx + JobStatusCode.LOOPCNT;
				}				
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				logger.info("insert 종료 ");
				logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				settlementJobHistoryService.jobStart(JobStatusCode.GSRETAIL,date,JobStatusCode.JOB_END_CODE,JobStatusCode.JOB_END_MSG);
			}else {
				// 데이터없음   
				settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSRETAIL,date,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);				
			}
			return true;
		} catch (Exception e) {
			logger.error("getDailySettlementListData " , e);
			// 작업 오류 상태 추가용 transaction 생성
			settlementJobHistoryService.jobUpdateStatus(JobStatusCode.GSRETAIL,date,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		}
	}

}
