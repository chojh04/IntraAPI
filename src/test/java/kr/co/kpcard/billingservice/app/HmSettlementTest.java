package kr.co.kpcard.billingservice.app;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.HappyMoneySettlementService;
import kr.co.kpcard.common.utils.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HmSettlementTest {
	
	@Autowired
	HappyMoneySettlementService happyMoneySettlementService;
	
	@Test
	public void hmTest(){
		Date startDate = DateUtil.createDate(2017, 11, 9);
		Date endDate = DateUtil.createDate(2017, 11, 12);
		happyMoneySettlementService.hmDownloadByDuration(startDate,endDate);
	}
	
	@Test
	public void hmSettlementTest(){
		Date startDate = DateUtil.createDate(2017, 11, 9);
		Date endDate = DateUtil.createDate(2017, 11, 9);
		happyMoneySettlementService.hmSettlementByDuration(startDate, endDate);
	}	
}
