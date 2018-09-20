package kr.co.kpcard.billingservice.app.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;
import kr.co.kpcard.billingservice.app.repository.approval.ApprovalRepository;
import kr.co.kpcard.billingservice.app.repository.approval.GsRetailPayment;
import kr.co.kpcard.billingservice.app.repository.approval.VanDemand;
import kr.co.kpcard.billingservice.app.repository.approval.VanTrans;
import kr.co.kpcard.billingservice.app.repository.approval.VanTransfer;
import kr.co.kpcard.billingservice.app.repository.legacy.HmGsRetailPayment;
import kr.co.kpcard.billingservice.app.repository.legacy.HmVanDemand;
import kr.co.kpcard.billingservice.app.repository.legacy.HmVanTrans;
import kr.co.kpcard.billingservice.app.repository.legacy.HmVanTransfer;
import kr.co.kpcard.billingservice.app.repository.legacy.LegacyRepository;

@Service	
public class BillingServiceService implements ResultCode{
	@Autowired
	ApprovalRepository approvalRepository;
	
	@Autowired
	LegacyRepository legacyRepository;
		
	private final Logger logger = LoggerFactory.getLogger(BillingServiceService.class);
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public ResultDailyGsRetailDataCreate DailyGsRetailDataCreate(String currentStartDate, String currentEndDate,  String merchantId){
		logger.info("DailyGsRetailDataCreate START.");
		logger.debug("DailyGsRetailDataCreate Parameter : currentStartDate->"+currentStartDate+" / currentEndDate->"+currentEndDate+"/ vanKind->"+merchantId);
		
		ResultDailyGsRetailDataCreate resultDailyGsRetailDataCreate = new ResultDailyGsRetailDataCreate();
		
		try{
			
			if(currentStartDate.equals("") || currentEndDate.equals("") || merchantId.equals("")){
				resultDailyGsRetailDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
				resultDailyGsRetailDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
				return resultDailyGsRetailDataCreate;
			}
			
			List<HmGsRetailPayment> hmGsRetailPaymentList = legacyRepository.selectHmPaymentRecord(currentStartDate, currentEndDate, merchantId);			
			int result = 0;
			resultDailyGsRetailDataCreate.setTotalCount(hmGsRetailPaymentList.size());
			
			for(HmGsRetailPayment hmGsRetailPayment : hmGsRetailPaymentList){
				GsRetailPayment gsRetailPayment = new GsRetailPayment(hmGsRetailPayment.getMerchantId(), hmGsRetailPayment.getWorkDate(), hmGsRetailPayment.getBranchCode(), hmGsRetailPayment.getOrderNumber(),
																	  hmGsRetailPayment.getAdmitNumber(), hmGsRetailPayment.getAdmitDate(), hmGsRetailPayment.getAdmitTime(), hmGsRetailPayment.getBarcodeNumber(),
																	  hmGsRetailPayment.getOrderAmount(), hmGsRetailPayment.getPayAmount(), hmGsRetailPayment.getPayType(), hmGsRetailPayment.getCurrentDate(), hmGsRetailPayment.getInsertDate(),
																	  hmGsRetailPayment.getPayOption1(), hmGsRetailPayment.getPayOption2(),hmGsRetailPayment.getPayOption3(),hmGsRetailPayment.getPayOption4(),hmGsRetailPayment.getPayOption5(),hmGsRetailPayment.getPayOption6(),hmGsRetailPayment.getPayOption7());
						
				if(approvalRepository.insertPaymentRecord(gsRetailPayment) == 1){
					resultDailyGsRetailDataCreate.setSuccessCount(resultDailyGsRetailDataCreate.getSuccessCount()+1);
				}else{
					resultDailyGsRetailDataCreate.setSuccessCount(resultDailyGsRetailDataCreate.getFailCount()+1);
				}
			}
			resultDailyGsRetailDataCreate.setResultCode(RESULT_CODE_SUCCESS);
			resultDailyGsRetailDataCreate.setResultMsg(RESULT_MSG_SUCCESS);
		}catch(Exception e){
			logger.error("error:"+e.toString());
			resultDailyGsRetailDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
			resultDailyGsRetailDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
			return resultDailyGsRetailDataCreate;				
		}		
		
		logger.info("DailyGsRetailDataCreate END.");
		return resultDailyGsRetailDataCreate;
	}
	
		
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public ResultDailyVanTransDataCreate DailyVanTransDataCreate(String transStartDate, String transEndDate, String vanKind){
		logger.info("DailyVanTransDataCreate START.");
		logger.debug("DailyVanTransDataCreate Parameter : transStartDate->"+transStartDate+" / transEndDate->"+transEndDate+" / vanKind->"+vanKind);
		
		ResultDailyVanTransDataCreate resultDailyVanTransDataCreate = new ResultDailyVanTransDataCreate();
		
		try{
			
			if(transStartDate.equals("") || transEndDate.equals("") || vanKind.equals("")){
				resultDailyVanTransDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
				resultDailyVanTransDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
				return resultDailyVanTransDataCreate;
			}
			
			List<HmVanTrans> hmVanTransList = legacyRepository.selectHmVanTransRecord(transStartDate, transEndDate, vanKind);			
			int result = 0;
			resultDailyVanTransDataCreate.setTotalCount(hmVanTransList.size());
			
			for(HmVanTrans hmVanTrans : hmVanTransList){
				VanTrans vanTrans = new VanTrans(hmVanTrans.getIdx(), hmVanTrans.getWorkDate(), hmVanTrans.getTransType(), hmVanTrans.getTransDate(),
												 hmVanTrans.getTransTime(), hmVanTrans.getOrigTransDate(), hmVanTrans.getCardNumber(), hmVanTrans.getApprovalNumber(),hmVanTrans.getPayAmount(),
												 hmVanTrans.getVat(), hmVanTrans.getServiceCharge(), hmVanTrans.getIssure(), hmVanTrans.getPurchase(), hmVanTrans.getMerchatNumber(),
												 hmVanTrans.getTerminalNumber(), hmVanTrans.getKeyinSwipe(), hmVanTrans.getVanResCode(), hmVanTrans.getVanKind(), hmVanTrans.getRegDatetime()
												);
				if(approvalRepository.insertVanTransRecord(vanTrans) == 1){
					resultDailyVanTransDataCreate.setSuccessCount(resultDailyVanTransDataCreate.getSuccessCount()+1);
				}else{
					resultDailyVanTransDataCreate.setSuccessCount(resultDailyVanTransDataCreate.getFailCount()+1);
				}
			}
			resultDailyVanTransDataCreate.setResultCode(RESULT_CODE_SUCCESS);
			resultDailyVanTransDataCreate.setResultMsg(RESULT_MSG_SUCCESS);
		}catch(Exception e){
			logger.error("error:"+e.toString());
			resultDailyVanTransDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
			resultDailyVanTransDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
			return resultDailyVanTransDataCreate;				
		}
		logger.info("DailyVanTransDataCreate END.");
		return resultDailyVanTransDataCreate;
	}
	
	
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public ResultDailyVanTransferDataCreate DailyVanTransferDataCreate(String transferStartDate, String transferEndDate, String vanKind){
		logger.info("DailyVanTransferDataCreate START.");
		logger.debug("DailyVanTransferDataCreate Parameter : transferStartDate->"+transferStartDate+" / transferEndDate->"+transferEndDate+" / vanKind->"+vanKind);
		
		ResultDailyVanTransferDataCreate resultDailyVanTransferDataCreate = new ResultDailyVanTransferDataCreate();
		
		try{
			
			if(transferStartDate.equals("") || transferEndDate.equals("") || vanKind.equals("")){
				resultDailyVanTransferDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
				resultDailyVanTransferDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
				return resultDailyVanTransferDataCreate;
			}
			
			List<HmVanTransfer> hmVanTransferList = legacyRepository.selectHmVanTransferRecord(transferStartDate, transferEndDate, vanKind);			
			int result = 0;
			resultDailyVanTransferDataCreate.setTotalCount(hmVanTransferList.size());
			
			for(HmVanTransfer hmVanTransfer : hmVanTransferList){
				VanTransfer vanTransfer = new VanTransfer(hmVanTransfer.getWorkDate(), hmVanTransfer.getTransferType(), hmVanTransfer.getTransferDate(), hmVanTransfer.getTransDate(),
														  hmVanTransfer.getDemandDate(), hmVanTransfer.getDepositDate(), hmVanTransfer.getReturnDate(), hmVanTransfer.getCardNumber(),
														  hmVanTransfer.getApprovalNumber(), hmVanTransfer.getPayAmount(), hmVanTransfer.getVat(), hmVanTransfer.getServiceCharge(),
														  hmVanTransfer.getPurchase(), hmVanTransfer.getMerchantNumber(), hmVanTransfer.getCardReturnCode(), hmVanTransfer.getVanReturnCode(),
														  hmVanTransfer.getVanKind(), hmVanTransfer.getRegDateTime()
														  );
				if(approvalRepository.insertVanTransferRecord(vanTransfer) == 1){
					resultDailyVanTransferDataCreate.setSuccessCount(resultDailyVanTransferDataCreate.getSuccessCount()+1);
				}else{
					resultDailyVanTransferDataCreate.setSuccessCount(resultDailyVanTransferDataCreate.getFailCount()+1);
				}
			}
			resultDailyVanTransferDataCreate.setResultCode(RESULT_CODE_SUCCESS);
			resultDailyVanTransferDataCreate.setResultMsg(RESULT_MSG_SUCCESS);
		}catch(Exception e){
			logger.error("error:"+e.toString());
			resultDailyVanTransferDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
			resultDailyVanTransferDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
			return resultDailyVanTransferDataCreate;				
		}		
		
		logger.info("DailyVanTransferDataCreate END.");		
		
		return resultDailyVanTransferDataCreate;
	}
	
	
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public ResultDailyVanDemandDataCreate DailyVanDemandDataCreate(String demandStartDate, String demandEndDate, String vanKind){
		logger.info("DailyVanDemandDataCreate START.");
		logger.debug("DailyVanDemandDataCreate Parameter : demandStartDate->"+demandStartDate+" / demandEndDate-> "+demandEndDate+"/ vanKind->"+vanKind);
		
		ResultDailyVanDemandDataCreate resultDailyVanDemandDataCreate = new ResultDailyVanDemandDataCreate();
		
		try{
			
			if(demandStartDate.equals("") || demandEndDate.equals("") || vanKind.equals("")){
				resultDailyVanDemandDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
				resultDailyVanDemandDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
				return resultDailyVanDemandDataCreate;
			}
			
			List<HmVanDemand> hmVanDemandList = legacyRepository.selectHmVanDemandRecord(demandStartDate, demandEndDate, vanKind);			
			int result = 0;
			resultDailyVanDemandDataCreate.setTotalCount(hmVanDemandList.size());
			
			for(HmVanDemand hmVanDemand : hmVanDemandList){
				VanDemand vanDemand = new VanDemand(hmVanDemand.getWorkDate(), hmVanDemand.getDemandType(), hmVanDemand.getDemandDate(), hmVanDemand.getTransDate(), hmVanDemand.getCardNumber(), 
													hmVanDemand.getApprovalNumber(), hmVanDemand.getPayAmount(), hmVanDemand.getVat(), hmVanDemand.getServiceCharge(), hmVanDemand.getPurchase(), hmVanDemand.getMerchantNumber(),
													hmVanDemand.getTerminalNumber(), hmVanDemand.getKeyinSwipe(), hmVanDemand.getVanKind(), hmVanDemand.getRegDateTime());
				if(approvalRepository.insertVanDemandRecord(vanDemand) == 1){
					resultDailyVanDemandDataCreate.setSuccessCount(resultDailyVanDemandDataCreate.getSuccessCount()+1);
				}else{
					resultDailyVanDemandDataCreate.setSuccessCount(resultDailyVanDemandDataCreate.getFailCount()+1);
				}
			}
			resultDailyVanDemandDataCreate.setResultCode(RESULT_CODE_SUCCESS);
			resultDailyVanDemandDataCreate.setResultMsg(RESULT_MSG_SUCCESS);
		}catch(Exception e){
			logger.error("error:"+e.toString());
			resultDailyVanDemandDataCreate.setResultCode(RESULT_CODE_NONE_REQ_PARAM);
			resultDailyVanDemandDataCreate.setResultMsg(RESULT_MSG_NONE_REQ_PARAM);
			return resultDailyVanDemandDataCreate;				
		}		
		
		logger.info("DailyVanDemandDataCreate END.");		
		
		return resultDailyVanDemandDataCreate;
	}
	
}
