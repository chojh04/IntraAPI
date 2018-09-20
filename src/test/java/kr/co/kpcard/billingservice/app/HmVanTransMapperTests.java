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

import kr.co.kpcard.billingservice.app.repository.legacy.HmVanTrans;
import kr.co.kpcard.billingservice.app.repository.legacy.LegacyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HmVanTransMapperTests {

	@Autowired
	LegacyRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	//@Test
	public void vanTransInsert(){
		
		HmVanTrans vanTrans = new HmVanTrans("000", "000", "0", "000", "000", "000", "0", 0, 0, 0, "000", "000", "000", "000", "000", "000", "000", "000", new Date());
		try{
			logger.debug("================HmVanTransInsert START");
			int result = repo.insertHmVanTransRecord(vanTrans);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanTransDelete(){
		try{
			logger.debug("================HmVanTransDelete START");
			String startDate = "000";
			String endDate = "000";
			String vanKind = "000";
			int result = repo.deleteHmVanTransRecord(startDate, endDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void SelectVanTransRecord(){
		try{
			logger.debug("================HmvanTransDelete START");
			String startDate = "000";
			String endDate = "000";
			String vanKind = "NICE";
			List<HmVanTrans> result= repo.selectHmVanTransRecord(startDate, endDate, vanKind);
			logger.debug("result:"+result.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
