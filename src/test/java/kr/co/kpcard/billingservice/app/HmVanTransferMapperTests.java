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

import kr.co.kpcard.billingservice.app.repository.legacy.HmVanTransfer;
import kr.co.kpcard.billingservice.app.repository.legacy.LegacyRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class HmVanTransferMapperTests {
	@Autowired
	LegacyRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	//@Test
	public void vanTransferInsert(){
		
		HmVanTransfer vanTransfer = new HmVanTransfer("0", "0", "0", "0", "0", "0", "0", "0", "0", 0, 0, 0, "0", "0", "0", "0", "0", new Date());
		try{
			logger.debug("================HmVanTransferInsert START");
			int result = repo.insertHmVanTransferRecord(vanTransfer);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanTransferDelete(){
		try{
			logger.debug("================HmVanTransDelete START");
			String startDate = "0";
			String endDate = "0";
			String vanKind = "0";
			int result = repo.deleteHmVanTransferRecord(startDate, endDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
		public void vanTransferSelect(){
			try{
				logger.debug("================HmVanTransSelect START");
				String startDate = "20150107";
				String endDate = "";
				String vanKind = "SMARTRO";
				List<HmVanTransfer> results = repo.selectHmVanTransferRecord(startDate, endDate, vanKind);
				
				for(HmVanTransfer result : results){
					logger.debug("Approval_no"+result.getApprovalNumber());
					logger.debug("Card_no"+result.getCardNumber());
				}			
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
