package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ApprovalRepository {
	
	@Autowired
	GsRetailPaymentMapper gsRetailPaymentMapper;
	
	@Autowired
	VanDemandMapper vanDemandMapper;
	
	@Autowired
	VanTransferMapper vanTransferMapper;
	
	@Autowired
	VanTransMapper vanTransMapper;
	
	
	/*Payment*/
	public Integer insertPaymentRecord(GsRetailPayment gsRetailPayment){
		return gsRetailPaymentMapper.insertPaymentRecord(gsRetailPayment);			
	}
	
	public Integer deletePaymentRecord(String currentStartDate, String currentEndDate, String merchantId){
		return gsRetailPaymentMapper.deleteDailyPaymentRecord(currentStartDate, currentEndDate, merchantId);			
	}
	
	public List<GsRetailPayment> selectPaymentRecord(String currentStartDate, String currentEndDate, String merchantId){
		return gsRetailPaymentMapper.selectPaymentRecord(currentStartDate, currentEndDate, merchantId);			
	}
	
		
	/*Trans*/
	public Integer insertVanTransRecord(VanTrans vanTrans){
		return vanTransMapper.insertVanTransRecord(vanTrans);			
	}
	
	public Integer deleteVanTransRecord(String workStartDate, String workEndDate, String vanKind){
		return vanTransMapper.deleteDailyVanTransRecord(workStartDate, workEndDate, vanKind);			
	}

	public List<VanTrans> selectVanTransRecord(String workStartDate, String workEndDate, String vanKind){
		return vanTransMapper.seleteVanTransRecord(workStartDate, workEndDate, vanKind);			
	}

	
	/*Transfer*/
	public Integer insertVanTransferRecord(VanTransfer vanTransfer){
		return vanTransferMapper.insertVanTransferRecord(vanTransfer);			
	}
	
	public Integer deleteVanTransferRecord(String transStartDate, String transEndDate, String vanKind){
		return vanTransferMapper.deleteDailyVanTransferRecord(transStartDate, transEndDate, vanKind);			
	}

	public List<VanTransfer> selectVanTransferRecord(String transStartDate, String transEndDate, String vanKind){
		return vanTransferMapper.selectVanTransferRecord(transStartDate, transEndDate, vanKind);			
	}

	
	/*Demand*/
	public Integer insertVanDemandRecord(VanDemand vanDemand){
		return vanDemandMapper.insertVanDemandRecord(vanDemand);			
	}
	
	public Integer deleteVanDemandRecord(String demandStartDate, String demandEndDate, String vanKind){
		return vanDemandMapper.deleteDailyVanDemandRecord(demandStartDate, demandEndDate, vanKind);			
	}
	
	public List<VanDemand> selectVanDemandRecord(String demandStartDate, String demandEndDate, String vanKind){
		return vanDemandMapper.selectVanDemandRecord(demandStartDate, demandEndDate, vanKind);			
	}
	
	
}

