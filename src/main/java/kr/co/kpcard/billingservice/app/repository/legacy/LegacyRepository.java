package kr.co.kpcard.billingservice.app.repository.legacy;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LegacyRepository 
{

	@Autowired
	HmGsRetailPaymentMapper hmGsRetailPaymentMapper;
	
	@Autowired
	HmVanDemandMapper hmVanDemandMapper;
	
	@Autowired
	HmVanTransferMapper hmVanTransferMapper;
	
	@Autowired
	HmVanTransMapper hmVanTransMapper;
	
	
	/*Payment*/
	public Integer insertHmPaymentRecord(HmGsRetailPayment HmGsRetailPayment){
		return hmGsRetailPaymentMapper.insertHmPaymentRecord(HmGsRetailPayment);			
	}
	
	public Integer deleteHmPaymentRecord(String currentStartDate, String currentEndDate, String merchantId){
		return hmGsRetailPaymentMapper.deleteDailyHmPaymentRecord(currentStartDate, currentEndDate, merchantId);			
	}
	
	public List<HmGsRetailPayment> selectHmPaymentRecord(String currentStartDate, String currentEndDate, String merchantId){
		return hmGsRetailPaymentMapper.selectHmPaymentRecord(currentStartDate, currentEndDate, merchantId);			
	}
	
		
	/*Trans*/
	public Integer insertHmVanTransRecord(HmVanTrans hmVanTrans){
		return hmVanTransMapper.insertHmVanTransRecord(hmVanTrans);			
	}
	
	public Integer deleteHmVanTransRecord(String transStartDate, String transEndDate, String vanKind){
		return hmVanTransMapper.deleteDailyHmVanTransRecord(transStartDate, transEndDate, vanKind);			
	}

	public List<HmVanTrans> selectHmVanTransRecord(String transStartDate, String transEndDate, String vanKind){
		return hmVanTransMapper.seleteHmVanTransRecord(transStartDate, transEndDate, vanKind);			
	}

	
	/*Transfer*/
	public Integer insertHmVanTransferRecord(HmVanTransfer hmVanTransfer){
		return hmVanTransferMapper.insertHmVanTransferRecord(hmVanTransfer);			
	}
	
	public Integer deleteHmVanTransferRecord(String transferStartDate, String transferEndDate, String vanKind){
		return hmVanTransferMapper.deleteDailyHmVanTransferRecord(transferStartDate, transferEndDate, vanKind);			
	}

	public List<HmVanTransfer> selectHmVanTransferRecord(String transferStartDate, String transferEndDate, String vanKind){
		return hmVanTransferMapper.selectHmVanTransferRecord(transferStartDate, transferEndDate, vanKind);			
	}

	
	/*Demand*/
	public Integer insertHmVanDemandRecord(HmVanDemand hmVanDemand){
		return hmVanDemandMapper.insertHmVanDemandRecord(hmVanDemand);			
	}
	
	public Integer deleteHmVanDemandRecord(String demandStartDate, String demandEndDate, String vanKind){
		return hmVanDemandMapper.deleteDailyHmVanDemandRecord(demandStartDate, demandEndDate, vanKind);			
	}
	
	public List<HmVanDemand> selectHmVanDemandRecord(String demandStartDate, String demandEndDate, String vanKind){
		return hmVanDemandMapper.selectHmVanDemandRecord(demandStartDate, demandEndDate, vanKind);			
	}
	
	
}
