package kr.co.kpcard.billingservice.app;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.GsPointSettlementService;
import kr.co.kpcard.common.utils.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GsPointSettlementTest {
	
	@Autowired
	GsPointSettlementService gsPointSettlementService;

	//@Test
	public void gsPopChargeDailySettlmentServiceUpload(){
		gsPointSettlementService.gsPointDailySettlementUpload(2017,9,13);
	}
	
	@Test
	public void gsPointTest(){
		Date startDate = DateUtil.createDate(2017, 9, 20);
		Date endDate  = DateUtil.createDate(2017, 11, 13);			
		gsPointSettlementService.gsDownloadByDuration(startDate, endDate);
		gsPointSettlementService.gsSettlementByDuration(startDate, endDate);

	}

}
