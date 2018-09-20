package kr.co.kpcard.billingservice.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.SettlementFailReCompareService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettlementFailReCompareServiceTest {
	
	@Autowired
	SettlementFailReCompareService settlementFailReCompareService;
	
	@Test
	public void settlementFailReCompareServiceTest() throws Exception{	
		settlementFailReCompareService.settlementReCompareService(2017, 3, 1);		
	}

}
