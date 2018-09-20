package kr.co.kpcard.billingservice.app.job.settlement;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;
import kr.co.kpcard.common.utils.DateUtil;

public class SettlementFailReCompare implements Job{

	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date date = DateUtil.createDate();
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
		int day = DateUtil.getDay(date);

		try {
			settlementFailReCompareService.settlementReCompareService(year, month, day);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//settlementFailReCompareService.settlementFailReCompareService(year, month, day);
		//settlementFailReCompareService.updateSettleCancelDataToSuccess(year, month, day);
	}

}
