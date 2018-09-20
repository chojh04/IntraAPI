package kr.co.kpcard.billingservice.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.settlement.GsRetailSettlementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GsRetailSettlementTest {
	
	@Autowired
	GsRetailSettlementService gsRetailSettlementService;
	
	@Test
	public void listTest(){
		gsRetailSettlementService.gsRetailDailySettlement(2017,8,21);
	}

}
