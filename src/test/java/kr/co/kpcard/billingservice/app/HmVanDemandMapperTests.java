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

import kr.co.kpcard.billingservice.app.repository.legacy.HmVanDemand;
import kr.co.kpcard.billingservice.app.repository.legacy.LegacyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HmVanDemandMapperTests {

	@Autowired
	LegacyRepository repo;	
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	//@Test
	public void vanDemandInsert(){
		
		HmVanDemand vanDemand = new HmVanDemand("0", "0", "0", "0", "0", "0", 0, 0, 0, "0", "0", "0", "0", "0", new Date());
		try{
			logger.debug("================vanDemandInsert START");
			int result = repo.insertHmVanDemandRecord(vanDemand);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanDemandDelete(){
		try{
			logger.debug("================vanDemandDelete START");
			String startDate = "0";
			String endDate = "0";
			String vanKind = "0";
			int result = repo.deleteHmVanTransRecord(startDate, endDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void vanDemandSelect(){		
		String startDate = "20141231";
		String endDate = "20141231";
		String vanKind = "NICE";
		
		try{
			logger.debug("================vanDemandInsert START");
			List<HmVanDemand> result = repo.selectHmVanDemandRecord(startDate, endDate, vanKind);
			logger.debug("resultSize:"+result.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
