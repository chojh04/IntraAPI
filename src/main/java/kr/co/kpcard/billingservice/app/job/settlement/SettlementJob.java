package kr.co.kpcard.billingservice.app.job.settlement;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kpcard.billingservice.app.service.settlement.GsPointSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSmSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GshbSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.HappyMoneySettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;
import kr.co.kpcard.common.utils.DateUtil;

public class SettlementJob implements Job{
	
	@Autowired
	HappyMoneySettlementService happyMoneySettlementService;
	@Autowired
	GsPointSettlementService gsPointSettlementService;
	@Autowired
	GsSettlementService gsDailySettlementService;
	@Autowired
	GsSmSettlementService gsSmSettlementService;
	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;
	@Autowired
	GshbSettlementService gshbSettlementService;	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date date = DateUtil.createDate();
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
		int day = DateUtil.getDay(date);
		happyMoneySettlementService.settlement(year, month, day);
		gsPointSettlementService.gsPointDailySettlement(year, month, day);
		gsDailySettlementService.settlement(year, month, day);
		gshbSettlementService.settlement(year, month, day);
		gsSmSettlementService.settlement(year, month, day);
		settlementFailReCompareService.updateSettleCancelDataToSuccess(year, month, day);
	}

}
