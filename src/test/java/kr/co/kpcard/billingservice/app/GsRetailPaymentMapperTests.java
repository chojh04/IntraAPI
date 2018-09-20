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

import kr.co.kpcard.billingservice.app.repository.approval.ApprovalRepository;
import kr.co.kpcard.billingservice.app.repository.approval.GsRetailPayment;
import kr.co.kpcard.billingservice.app.repository.approval.GsRetailPaymentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GsRetailPaymentMapperTests {
	@Autowired
	ApprovalRepository repo;	

	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);	
	
	//@Test
	public void gsRetailInsert(){
		
		Date insertDate = new Date();
		
		GsRetailPayment gsRetailPayment = new GsRetailPayment("gsretail", "20111010", "VD888", "20111010VD652 100171111010190009", "20111010626736",
				"20111010", "190008", "9744709501605043", 5000, 5000, "0", "20111011", insertDate, "", "", "", "", "", "", "");
		try{
			logger.debug("================gsRetailInsert START");
			int result = repo.insertPaymentRecord(gsRetailPayment);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void gsRetailDelete(){
		try{
			logger.debug("================gsRetailDelete START");
			String currentSDate = "20111011";
			String currentEDate = "20111011";
			String merchantId = "gsretail";
			int result = repo.deletePaymentRecord(currentSDate, currentEDate, merchantId);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	@Test
	public void gsRetailSelect(){
		try{
			logger.debug("================gsRetailSelect START");
			String currentSDate = "20111012";
			String currentEDate = "20111012";
			String merchantId = "gsretail";
			List<GsRetailPayment> gsRetailPayment = repo.selectPaymentRecord(currentSDate, currentEDate, merchantId);
			logger.debug("result:"+gsRetailPayment.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
