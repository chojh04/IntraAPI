package kr.co.kpcard.billingservice.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.repository.settlement.popcard.PopCardDealsSettlement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectCopyTest {
	private final Logger logger = LoggerFactory.getLogger(ObjectCopyTest.class);
	
	@Test
	public void test(){
		PopCardDealsSettlement popCardSettlement = new PopCardDealsSettlement();
		
	}
}
