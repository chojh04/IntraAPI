package kr.co.kpcard.billingservice.app;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.repository.legacy.HmGsRetailPayment;
import kr.co.kpcard.billingservice.app.repository.legacy.LegacyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HmGsRetailPaymentMapperTests {
	@Autowired
	LegacyRepository repo;	

	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);	
	
	//@Test
	public void gsRetailInsert(){
		
		Date insertDate = new Date();
		
		HmGsRetailPayment gsRetailPayment = new HmGsRetailPayment("gsretail", "20111010", "VD888", "20111010VD652 100171111010190009", "20111010626736",
				"20111010", "190008", "9744709501605043", 5000, 5000, "0", "20111011", insertDate, "", "", "", "", "", "", "");
		try{
			logger.debug("================HmGsRetailInsert START");
			int result = repo.insertHmPaymentRecord(gsRetailPayment);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void gsRetailDelete(){
		try{
			logger.debug("================HmGsRetailDelete START");
			String currentStartDateDate = "20111011";
			String currentEndDateDate = "20111011";
			String merchantId = "gsretail";
			int result = repo.deleteHmPaymentRecord(currentStartDateDate, currentEndDateDate, merchantId);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	@Test
	public void gsRetailSelect(){
		try{
			logger.debug("================HmGsRetailSelect START");
			String currentStartDateDate = "20111011";
			String currentEndDateDate = "20111011";
			String merchantId = "gsretail";
			List<HmGsRetailPayment> gsRetailPayment = repo.selectHmPaymentRecord(currentStartDateDate, currentEndDateDate, merchantId);
			logger.debug("result:"+gsRetailPayment.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
