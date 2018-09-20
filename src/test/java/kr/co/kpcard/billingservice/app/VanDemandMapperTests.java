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
import kr.co.kpcard.billingservice.app.repository.approval.VanDemand;
import kr.co.kpcard.billingservice.app.repository.approval.VanTrans;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VanDemandMapperTests {

	@Autowired
	ApprovalRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	//@Test
	public void vanDemandInsert(){
		
		VanDemand vanDemand = new VanDemand("0", "0", "0", "0", "0", "0", 0, 0, 0, "0", "0", "0", "0", "0", new Date());
		try{
			logger.debug("================vanDemandInsert START");
			int result = repo.insertVanDemandRecord(vanDemand);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanDemandDelete(){
		try{
			logger.debug("================vanDemandDelete START");
			String demandSDate = "0";
			String demandEDate = "0";
			String vanKind = "0";
			int result = repo.deleteVanTransRecord(demandSDate, demandEDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void vanDemandSelect(){		
		String demandSDate = "20141231";
		String demandEDate = "20141231";
		String vanKind = "NICE";
		
		try{
			logger.debug("================vanDemandInsert START");
			List<VanDemand> result = repo.selectVanDemandRecord(demandSDate,demandEDate, vanKind);
			logger.debug("resultSize:"+result.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
