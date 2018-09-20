package kr.co.kpcard.billingservice.app.service.settlement;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.settlement.common.SettlementResult;
import kr.co.kpcard.billingservice.app.repository.settlement.common.SettlementResultMapper;
import kr.co.kpcard.billingservice.app.repository.settlement.common.SettlementWorkDate;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;

@Service
public class SettlementFailReCompareService {
	private Logger logger = LoggerFactory.getLogger(SettlementFailReCompareService.class);
	
	@Autowired
	SettlementResultMapper settlementResultMapper;
	
	@Autowired
	GsPointSettlementService gsPointSettlementService;
	@Autowired
	GsSettlementService gsDailySettlementService;
	@Autowired
	GsSmSettlementService gssmSettlementService;	
	@Autowired
	SettlementJobHistoryService settlementJobHistoryService;	
	@Autowired
	HappyMoneySettlementService happyMoneySettlementService;	
	
	public void settlementFailReCompareService(int year ,int month, int day){
		String workDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		// 실패건 대사 재처리 GSPOINT
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSPOINT);
		List<SettlementResult> settleFailDateList = settlementResultMapper.selectSettlementFailList(JobStatusCode.GSPOINT, workDate);
		for(SettlementResult settlementResult : settleFailDateList){
			String failWorkDt = settlementResult.getWorkDt();
			Date reWorkDt= DateUtil.createDate(failWorkDt, DateUtil.DATE_FORMAT);
			settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
			gsPointSettlementService.gsPointDailySettlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
			settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
		}
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSPOINT);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		
		// 실패건 대사 재처리 GSPOP
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSPOP);
		settleFailDateList = settlementResultMapper.selectSettlementFailList(JobStatusCode.GSPOP, workDate);
		for(SettlementResult settlementResult : settleFailDateList){
			String failWorkDt = settlementResult.getWorkDt();
			Date reWorkDt= DateUtil.createDate(failWorkDt, DateUtil.DATE_FORMAT);
			settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
			gsDailySettlementService.settlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
			settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
		}
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSPOP);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		
		// 실패건 대사 재처리 GSSM 
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSSM);
		settleFailDateList = settlementResultMapper.selectSettlementFailList(JobStatusCode.GSSM, workDate);
		for(SettlementResult settlementResult : settleFailDateList){
			String failWorkDt = settlementResult.getWorkDt();
			Date reWorkDt= DateUtil.createDate(failWorkDt, DateUtil.DATE_FORMAT);
			settlementJobHistoryService.jobStart(JobStatusCode.GSSM,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
			gssmSettlementService.settlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
			settlementJobHistoryService.jobStart(JobStatusCode.GSSM,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
		}		
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG + " JOB_DIVIDER : {}" , JobStatusCode.GSSM);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		
		// 실패건 대사 재처리 HAPPYMNOEY 
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG + " JOB_DIVIDER : {}" , JobStatusCode.HAPPYMONEY);
		settleFailDateList = settlementResultMapper.selectSettlementFailList(JobStatusCode.HAPPYMONEY, workDate);
		for(SettlementResult settlementResult : settleFailDateList){
			String failWorkDt = settlementResult.getWorkDt();
			Date reWorkDt= DateUtil.createDate(failWorkDt, DateUtil.DATE_FORMAT);
			settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
			happyMoneySettlementService.settlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
			settlementJobHistoryService.jobStart(JobStatusCode.HAPPYMONEY,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
		}		
		logger.info(JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG + " JOB_DIVIDER : {}" , JobStatusCode.HAPPYMONEY);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");		
	}
	
	public void updateSettleCancelDataToSuccess(int year ,int month, int day){
		String workDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		logger.info(JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_START + " : " + JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_START_MSG);
		settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,workDate,JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_START,JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_START_MSG);
		settlementResultMapper.updateSettleCancelDataToSuccess(workDate);
		settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,workDate,JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_END,JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_END_MSG);
		logger.info(JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_END + " : " + JobStatusCode.JOB_SETTLE_CANCEL_DATA_SUCCESS_END_MSG);
		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
	}
	
	public void updateSettleCancelDataToSuccessByDuration(Date startDate, Date endDate)
	{
		long diffDays = DateUtil.getDiffDays(startDate, endDate);
		int addDay = 0;
		Date downloadDate = startDate;
		do
		{
			downloadDate = DateUtil.addDay(startDate, addDay);
			updateSettleCancelDataToSuccess(DateUtil.getYear(downloadDate), DateUtil.getMonth(downloadDate), DateUtil.getDay(downloadDate));
		}while((addDay++) < diffDays);
		
	}	
	
	
	public void settlementReCompareService(int year ,int month, int day) throws Exception{	
		logger.info("---------------------대사 재배치 실행");
		List<SettlementWorkDate> dateList = settlementResultMapper.getSettlementReWorkDate(DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT));
		
		for(SettlementWorkDate date : dateList)
		{
			String failWorkDt = date.getWork_dt();
			Date reWorkDt = DateUtil.createDate(failWorkDt, DateUtil.DATE_FORMAT);
			
			switch(date.getJob_divider()){
				case "GSPOP" : 
					settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
					gsDailySettlementService.settlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
					settlementJobHistoryService.jobStart(JobStatusCode.GSPOP,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
					
					break;
				case "GSPOINT" : 
					settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
					gsPointSettlementService.gsPointDailySettlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
					settlementJobHistoryService.jobStart(JobStatusCode.GSPOINT,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
					break;
					
				case "GSSM" : 
					settlementJobHistoryService.jobStart(JobStatusCode.GSSM,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_START_MSG);
					gssmSettlementService.settlement(DateUtil.getYear(reWorkDt), DateUtil.getMonth(reWorkDt), DateUtil.getDay(reWorkDt));
					settlementJobHistoryService.jobStart(JobStatusCode.GSSM,failWorkDt,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END,JobStatusCode.JOB_SETTLE_FAIL_RE_COMPARE_END_MSG);
					break;				
			}
			Thread.sleep(30);
		}
		
	}
}
