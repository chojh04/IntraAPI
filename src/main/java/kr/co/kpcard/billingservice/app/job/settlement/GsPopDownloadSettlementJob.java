package kr.co.kpcard.billingservice.app.job.settlement;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GshbSettlementService;
import kr.co.kpcard.common.utils.DateUtil;

public class GsPopDownloadSettlementJob implements Job{
	
	@Autowired
	GsSettlementService gsDailySettlementService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date date = DateUtil.createDate();
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
		int day = DateUtil.getDay(date);
		gsDailySettlementService.download(year, month, day);
	}
}
