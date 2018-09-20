package kr.co.kpcard.billingservice.app.job.settlement;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kpcard.billingservice.app.service.settlement.HappyCashSettlementService;
import kr.co.kpcard.common.utils.DateUtil;

public class HappyCashUploadSettlementJob implements Job{
	
	@Autowired
	HappyCashSettlementService happyCashSettlementService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date date = DateUtil.createDate();
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
		int day = DateUtil.getDay(date);
		happyCashSettlementService.upload(year, month, day);
	}

}
