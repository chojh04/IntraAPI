package kr.co.kpcard.billingservice.app;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.PopCardSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;
import kr.co.kpcard.common.utils.DateUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PopCardSettlementTest {
	
	@Autowired
	PopCardSettlementService popCardDailySettlementService;
	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;	
	
	@Test
	public void downloadGsPopcardDealDetails(){
		Assert.assertTrue(popCardDailySettlementService.downloadGsPopcardDealDetails(2017,8,25));
	}
	
	@Test
	public void uploadKpcPopCardDealDetails(){
		popCardDailySettlementService.uploadKpcPopCardDealDetails(2017,9,20);
	}
	
	//@Test
	public void downloadGsPopcardDealDetailsByDuration(){
		Date startDate = DateUtil.createDate(2017, 11, 17);
		Date endDate  = DateUtil.createDate(2017, 11, 17);
		popCardDailySettlementService.downloadGsPopcardDealDetailsByDuration(startDate,endDate);
	}
	

}
