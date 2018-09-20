package kr.co.kpcard.billingservice.app;

import kr.co.kpcard.billingservice.app.service.settlement.PopCardSettlementService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
//@ActiveProfiles(profiles = "dev")
@SpringBootTest
//@SpringBootTest(classes = {BillingServiceApplication.class, SchedulerConfiguration.class})
public class PopCardSettlementServiceTests 
{
	private Logger logger = LoggerFactory.getLogger(PopCardSettlementServiceTests.class);
	
	
	
	@Autowired
	PopCardSettlementService service;
	
	@Test
	public void testPopCardSettlementService()
	{
		logger.info("=============> testPopCardSettlementService Start. ================= ");
		
		System.out.println("sdfd");
		logger.info("test!!");
		
		
		logger.debug("=============> testPopCardSettlementService End.   ================= ");
		
	}
	
	
	

}
