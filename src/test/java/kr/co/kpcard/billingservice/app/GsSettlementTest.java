package kr.co.kpcard.billingservice.app;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.GsPointSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GsSettlementTest {
	
	@Autowired
	GsSettlementService gsDailySettlementService;
	@Autowired
	GsPointSettlementService gsPointSettlementService;
	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;
	@Test
	public void gsDownloadTest(){
		Date startDate = DateUtil.createDate(2017, 10, 1);
		Date endDate  = DateUtil.createDate(2017, 12, 5);
//		//gsDailySettlementService.gsUploadByDuration(startDate, endDate);
////		gsDailySettlementService.gsUploadByDuration( startDate, endDate);
////		gsDailySettlementService.gsDownloadByDuration(startDate, endDate);
////		gsDailySettlementService.gsDownloadByDuration(startDate, endDate);
//		gsDailySettlementService.gsSettlementByDuration(startDate, endDate);
		settlementFailReCompareService.updateSettleCancelDataToSuccessByDuration(startDate, endDate);
	}
	
	//@Test
	public void gsUploadTest(){
		
	}	
	
}
