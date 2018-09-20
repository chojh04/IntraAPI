package kr.co.kpcard.billingservice.app;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.GsSmSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;
import kr.co.kpcard.common.utils.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GsSmDailySettlementTest {
	
	@Autowired
	GsSmSettlementService gsSmDailySettlementService;
	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;
	@Test
	public void gsDownloadTest(){
		Date startDate = DateUtil.createDate(2017,9, 20);
		Date endDate  = DateUtil.createDate(2017, 10, 18);
//		gsSmDailySettlementService.gsUploadByDuration(startDate, endDate);
//		gsSmDailySettlementService.gsUploadByDuration( startDate, endDate);
//		gsSmDailySettlementService.gsDownloadByDuration(startDate, endDate);
		//gsSmDailySettlementService.gsDownloadByDuration(startDate, endDate);
		gsSmDailySettlementService.gsSettlementByDuration(startDate, endDate);
		settlementFailReCompareService.updateSettleCancelDataToSuccessByDuration(startDate, endDate);
	}

}
