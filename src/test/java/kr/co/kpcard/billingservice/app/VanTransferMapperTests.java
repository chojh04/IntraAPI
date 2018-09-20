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
import kr.co.kpcard.billingservice.app.repository.approval.VanTransfer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VanTransferMapperTests {
	@Autowired
	ApprovalRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	//@Test
	public void vanTransferInsert(){
		
		VanTransfer vanTransfer = new VanTransfer("0", "0", "0", "0", "0", "0", "0", "0", "0", 0, 0, 0, "0", "0", "0", "0", "0", new Date());
		try{
			logger.debug("================vanTransferInsert START");
			int result = repo.insertVanTransferRecord(vanTransfer);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void vanTransferDelete(){
		try{
			logger.debug("================vanTransDelete START");
			String transSDate = "0";
			String transEDate = "0";
			String vanKind = "0";
			int result = repo.deleteVanTransferRecord(transSDate, transEDate, vanKind);
			logger.debug("result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void vanTransferSelect(){
		try{
			logger.debug("================vanTransSelect START");
			String transSDate = "20150107";
			String transEDate = "20150107";
			String vanKind = "SMARTRO";
			List<VanTransfer> results = repo.selectVanTransferRecord(transSDate, transEDate, vanKind);
			
			for(VanTransfer result : results){
				logger.debug("Approval_no"+result.getApprovalNumber());
				logger.debug("Card_no"+result.getCardNumber());
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
