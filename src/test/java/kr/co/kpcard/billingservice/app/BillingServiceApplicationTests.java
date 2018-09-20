package kr.co.kpcard.billingservice.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import kr.co.kpcard.billingservice.app.service.BillingServiceService;
import kr.co.kpcard.billingservice.app.service.ResultDailyGsRetailDataCreate;
import kr.co.kpcard.billingservice.app.service.ResultDailyVanDemandDataCreate;
import kr.co.kpcard.billingservice.app.service.ResultDailyVanTransDataCreate;
import kr.co.kpcard.billingservice.app.service.ResultDailyVanTransferDataCreate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingServiceApplicationTests {

	@Autowired
	BillingServiceService service;
	
	private final Logger logger = LoggerFactory.getLogger(GsRetailPaymentMapperTests.class);
	
	@Test
	public void paymentInsert(){
		String startDate = "20111011";
		String endDate = "20111011";
		String merchantId = "gsretail";
		
		ResultDailyGsRetailDataCreate result= service.DailyGsRetailDataCreate(startDate, endDate, merchantId);
		logger.info("ResultCode"+result.getResultCode()+"/Msg:"+result.getResultMsg()+"/total:"+result.getTotalCount()+"/Suc:"+result.getSuccessCount()+"/Fail:"+result.getFailCount());	
	}
	
	
	
	
	//@Test
	public void vanTransInsert(){
		String startDate = "20111011";
		String endDate = "20111011";
		String vanKind = "";
		
		ResultDailyVanTransDataCreate result= service.DailyVanTransDataCreate(startDate, endDate, vanKind);
		logger.info("ResultCode"+result.getResultCode()+"/Msg:"+result.getResultMsg()+"/total:"+result.getTotalCount()+"/Suc:"+result.getSuccessCount()+"/Fail:"+result.getFailCount());	
	}
	
	
	//@Test
	public void vanTransferInsert(){
		String startDate = "20111011";
		String endDate = "20111011";
		String vanKind = "SMARTRO";
		
		ResultDailyVanTransferDataCreate result= service.DailyVanTransferDataCreate(startDate, endDate, vanKind);
		logger.info("ResultCode"+result.getResultCode()+"/Msg:"+result.getResultMsg()+"/total:"+result.getTotalCount()+"/Suc:"+result.getSuccessCount()+"/Fail:"+result.getFailCount());	
	}
	
	
	
	//@Test
	public void vanDemandInsert(){
		String startDate = "20111011";
		String endDate = "20111011";
		String vanKind = "NICE";
		
		ResultDailyVanDemandDataCreate result= service.DailyVanDemandDataCreate(startDate, endDate, vanKind);
		logger.info("ResultCode"+result.getResultCode()+"/Msg:"+result.getResultMsg()+"/total:"+result.getTotalCount()+"/Suc:"+result.getSuccessCount()+"/Fail:"+result.getFailCount());	
	}
}
