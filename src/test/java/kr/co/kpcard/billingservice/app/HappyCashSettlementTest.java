package kr.co.kpcard.billingservice.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.HappyCashSettlementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HappyCashSettlementTest {
	
	@Autowired
	HappyCashSettlementService happyCashSettlementService;

	@Test
	public void gsPopChargeDailySettlmentServiceToListTest(){
		happyCashSettlementService.upload(2016,9,25);
	}

}
