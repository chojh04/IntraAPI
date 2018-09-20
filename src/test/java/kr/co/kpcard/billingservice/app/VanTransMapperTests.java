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
import kr.co.kpcard.billingservice.app.repository.approval.VanTrans;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VanTransMapperTests {

	@Autowired
	ApprovalRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	@Test
	public void vanTransInsert(){
		
		VanTrans vanTrans = new VanTrans("000", "000", "0", "000", "000", "000", "0", 0, 0, 0, "000", "000", "000", "000", "000", "000", "000", "000", new Date());
		try{
			logger.debug("================vanTransInsert START");
			int result = repo.insertVanTransRecord(vanTrans);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanTransDelete(){
		try{
			logger.debug("================vanTransDelete START");
			String workSDate = "000";
			String workEDate = "000";
			String vanKind = "000";
			int result = repo.deleteVanTransRecord(workSDate, workEDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void SelectVanTransRecord(){
		try{
			logger.debug("================vanTransDelete START");
			String workSDate = "20150104";
			String workEDate = "20150104";
			String vanKind = "NICE";
			List<VanTrans> result= repo.selectVanTransRecord(workSDate, workEDate, vanKind);
			logger.debug("result:"+result.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
