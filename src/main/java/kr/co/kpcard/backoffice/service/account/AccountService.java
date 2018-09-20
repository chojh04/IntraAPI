package kr.co.kpcard.backoffice.service.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountApprovalSummResult;
import kr.co.kpcard.backoffice.component.account.AccountChargementResult;
import kr.co.kpcard.backoffice.component.account.AccountParam;
import kr.co.kpcard.backoffice.component.account.AccountPaymentResult;
import kr.co.kpcard.backoffice.component.account.AccountSalementParam;
import kr.co.kpcard.backoffice.component.account.AccountSalementResult;
import kr.co.kpcard.backoffice.component.account.AccountSalementSummResult;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsDailyList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsMonthList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsDailyList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsMonthList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetSalementsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetSalementsMonthList;
import kr.co.kpcard.backoffice.repository.account.mapper.AccountMapper;
import kr.co.kpcard.backoffice.repository.account.model.AccountApprovalSumm;
import kr.co.kpcard.backoffice.repository.account.model.AccountChargement;
import kr.co.kpcard.backoffice.repository.account.model.AccountPayment;
import kr.co.kpcard.backoffice.repository.account.model.AccountSalement;
import kr.co.kpcard.backoffice.repository.account.model.AccountSalementSumm;
import kr.co.kpcard.backoffice.repository.account.model.AccountSummaryModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	AccountMapper accountMapper;
	
	public ResponseApprovalsGetPaymentsList getPayments(AccountParam param){
		ResponseApprovalsGetPaymentsList responseApprovalsGetPaymentsList = new ResponseApprovalsGetPaymentsList();
		
		logger.debug(param.toStringForLog());
		
		AccountSummaryModel approvalsSummary = accountMapper.getPaymentsByCount(param);
		List<AccountPayment> approvalsPayments = accountMapper.getPayments(param);
		
		List<AccountPaymentResult> resultList = new ArrayList<AccountPaymentResult>();
		
		float kpcComission = 0;
		long customerProvideAmount = 0;
		for(AccountPayment approvalsPayment : approvalsPayments){
			AccountPaymentResult approvalsPaymentResult = new AccountPaymentResult();			
			kpcComission = (approvalsPayment.getKpc_commision()>0)? Math.round(approvalsPayment.getAmount()*(approvalsPayment.getKpc_commision()*0.01)) : 0;
			
			//환불상태 일떄 고객지급금액 계산(거래금액 - 수수료)
			customerProvideAmount = (approvalsPayment.getApproval_status().equals("PAYT-0003"))? (approvalsPayment.getAmount()-Integer.parseInt(approvalsPayment.getCommision())) : 0;
			
			approvalsPaymentResult.setValues(approvalsPayment.getSeq(), approvalsPayment.getSumm_seq(), approvalsPayment.getSubmerchant_id(), approvalsPayment.getSubmerchant_nm(),
					approvalsPayment.getService_id(), approvalsPayment.getService_nm(), approvalsPayment.getService_type(), approvalsPayment.getService_category(), approvalsPayment.getSvc_conn_id(), 
					approvalsPayment.getDeal_divider(), approvalsPayment.getDeal_dt(), approvalsPayment.getApproval_dt(), approvalsPayment.getApproval_status_nm(), approvalsPayment.getApproval_status(), 
					approvalsPayment.getApproval_no(), approvalsPayment.getOrder_no(), approvalsPayment.getSale_divider(), approvalsPayment.getSaleDividerName(), approvalsPayment.getCardCdName(), 
					approvalsPayment.getCard_cd(), approvalsPayment.getCard_nm(), approvalsPayment.getCard_no(), approvalsPayment.getCard_enc_no(), approvalsPayment.getCard_mng_no(), approvalsPayment.getAmount(), 
					approvalsPayment.getBalance(), approvalsPayment.getPayTypeName(), approvalsPayment.getPay_type(), approvalsPayment.getPay_method(), approvalsPayment.getPayMethodName(), 
					approvalsPayment.getStore_cd(), approvalsPayment.getStore_nm(), approvalsPayment.getPos_no(), approvalsPayment.getCp_id(), approvalsPayment.getCp_user_id(), approvalsPayment.getCp_user_ip(), 
					approvalsPayment.getAgent_id(), approvalsPayment.getCommision(), approvalsPayment.getMerchant_commision(), approvalsPayment.getMerchant_tax_type(), approvalsPayment.getMerchant_tax(), 
					approvalsPayment.getKpc_comm_type(), approvalsPayment.getKpc_commision(), approvalsPayment.getKpc_tax_type(), approvalsPayment.getKpc_tax(), approvalsPayment.getAdd_comm_type01(), 
					approvalsPayment.getAdd_commision01(), approvalsPayment.getAdd_tax_type01(), approvalsPayment.getAdd_tax01(), approvalsPayment.getAdd_comm_type02(), approvalsPayment.getAdd_commision02(), 
					approvalsPayment.getAdd_tax_type02(), approvalsPayment.getAdd_tax02(), approvalsPayment.getBilling_sum(), approvalsPayment.getDesc01(), approvalsPayment.getDesc02(), approvalsPayment.getLingUrl(), 
					approvalsPayment.getOrigAmount(), approvalsPayment.getDcAmount(), kpcComission, customerProvideAmount);
			resultList.add(approvalsPaymentResult);
		}
		
		responseApprovalsGetPaymentsList.setOffset(param.getOffset());
		responseApprovalsGetPaymentsList.setLimit(param.getLimit());
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), approvalsSummary.getOrigAmountSum(), approvalsSummary.getDcAmountSum(), approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetPaymentsList.setSummary(summary);
		responseApprovalsGetPaymentsList.setResultList(resultList);
		return responseApprovalsGetPaymentsList;
	}
	
	public ResponseApprovalsGetPaymentsDailyList getPaymentsDaily(AccountParam param){
		ResponseApprovalsGetPaymentsDailyList responseApprovalsGetPaymentsDailyList = new ResponseApprovalsGetPaymentsDailyList();
		
		logger.debug(param.toStringForLog());
		
		AccountSummaryModel approvalsSummary = accountMapper.getPaymentDailyByCount(param);
		
		List<AccountApprovalSumm> approvalsApprovalSumms = accountMapper.getPaymentDaily(param);
		
		List<AccountApprovalSummResult> resultList = new ArrayList<AccountApprovalSummResult>();
		for(AccountApprovalSumm approvalsApprovalSumm : approvalsApprovalSumms){
			AccountApprovalSummResult approvalsApprovalSummResult = new AccountApprovalSummResult();
			approvalsApprovalSummResult.setValues(approvalsApprovalSumm.getSubmerchant_nm(), approvalsApprovalSumm.getService_nm(), approvalsApprovalSumm.getSvc_conn_id(), approvalsApprovalSumm.getDeal_divider(), 
					approvalsApprovalSumm.getDeal_dt(), approvalsApprovalSumm.getApproval_status_nm(), approvalsApprovalSumm.getApproval_status(), approvalsApprovalSumm.getCardCdName(), approvalsApprovalSumm.getCard_cd(), 
					approvalsApprovalSumm.getPayTypeName(), approvalsApprovalSumm.getPay_type(), approvalsApprovalSumm.getCnt(), approvalsApprovalSumm.getAmount(), approvalsApprovalSumm.getCommision());
			resultList.add(approvalsApprovalSummResult);
		}
		
		responseApprovalsGetPaymentsDailyList.setLimit(param.getLimit());
		responseApprovalsGetPaymentsDailyList.setOffset(param.getOffset());
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetPaymentsDailyList.setSummary(summary);
		responseApprovalsGetPaymentsDailyList.setResultList(resultList);
		return responseApprovalsGetPaymentsDailyList;
	}
	
	public ResponseApprovalsGetPaymentsMonthList getPaymentsMonth(AccountParam param){
		ResponseApprovalsGetPaymentsMonthList responseApprovalsGetPaymentsMonthList = new ResponseApprovalsGetPaymentsMonthList();
		AccountSummaryModel approvalsSummary = accountMapper.getPaymentMonthByCount(param);
		List<AccountApprovalSumm> approvalsApprovalSumms = accountMapper.getPaymentMonth(param);
		
		List<AccountApprovalSummResult> resultList = new ArrayList<AccountApprovalSummResult>();
		for(AccountApprovalSumm approvalsApprovalSumm : approvalsApprovalSumms){
			AccountApprovalSummResult approvalsApprovalSummResult = new AccountApprovalSummResult();
			approvalsApprovalSummResult.setValues(approvalsApprovalSumm.getSubmerchant_nm(), approvalsApprovalSumm.getService_nm(), approvalsApprovalSumm.getSvc_conn_id(), approvalsApprovalSumm.getDeal_divider(), 
					approvalsApprovalSumm.getDeal_dt(), approvalsApprovalSumm.getApproval_status_nm(), approvalsApprovalSumm.getApproval_status(), approvalsApprovalSumm.getCardCdName(), approvalsApprovalSumm.getCard_cd(), 
					approvalsApprovalSumm.getPayTypeName(), approvalsApprovalSumm.getPay_type(), approvalsApprovalSumm.getCnt(), approvalsApprovalSumm.getAmount(), approvalsApprovalSumm.getCommision());
			resultList.add(approvalsApprovalSummResult);
		}
		
		responseApprovalsGetPaymentsMonthList.setLimit(param.getLimit());
		responseApprovalsGetPaymentsMonthList.setOffset(param.getOffset());
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetPaymentsMonthList.setSummary(summary);
		responseApprovalsGetPaymentsMonthList.setResultList(resultList);
		return responseApprovalsGetPaymentsMonthList;
	}
	
	public ResponseApprovalsGetChargementsList getChargements(AccountParam param){
		ResponseApprovalsGetChargementsList responseApprovalsGetChargementsList = new ResponseApprovalsGetChargementsList();
		logger.debug("PayMethod:'" + param.getPayMethod() + "'");
		AccountSummaryModel approvalsSummary = accountMapper.getChargmentsByCount(param);
		List<AccountChargement> approvalsChargements = accountMapper.getChargments(param);
		
		List<AccountChargementResult> resultList = new ArrayList<AccountChargementResult>();
		
		for(AccountChargement approvalsChargement : approvalsChargements){
			AccountChargementResult approvalsChargementResult = new AccountChargementResult();
			
			approvalsChargementResult.setValues(approvalsChargement.getSeq(), approvalsChargement.getSumm_seq(), approvalsChargement.getSubmerchant_id(), 
					approvalsChargement.getSubmerchant_nm(), approvalsChargement.getService_id(), approvalsChargement.getService_nm(), 
					approvalsChargement.getService_type(), approvalsChargement.getService_category(), approvalsChargement.getSvc_conn_id(), 
					approvalsChargement.getDeal_divider(), approvalsChargement.getDeal_dt(), approvalsChargement.getApproval_dt(), approvalsChargement.getApproval_status_nm(), 
					approvalsChargement.getApproval_status(), approvalsChargement.getApproval_no(), approvalsChargement.getOrder_no(), approvalsChargement.getSale_divider(), 
					approvalsChargement.getSaleDividerName(), approvalsChargement.getCardCdName(), approvalsChargement.getCard_cd(), approvalsChargement.getCard_nm(), 
					approvalsChargement.getCard_no(), approvalsChargement.getCard_enc_no(), approvalsChargement.getCard_mng_no(), approvalsChargement.getAmount(), 
					approvalsChargement.getBalance(), approvalsChargement.getCharge_type(), approvalsChargement.getChargeTypeName(), approvalsChargement.getCharge_method(), 
					approvalsChargement.getChargeMethodName(), approvalsChargement.getChargeTypeName(), approvalsChargement.getStore_cd(), approvalsChargement.getStore_nm(), 
					approvalsChargement.getPos_no(), approvalsChargement.getCp_id(), approvalsChargement.getCp_user_id(), approvalsChargement.getCp_user_ip(), 
					approvalsChargement.getAgent_id(), approvalsChargement.getCommision(), approvalsChargement.getMerchant_comm_type(), approvalsChargement.getMerchant_commision(), 
					approvalsChargement.getMerchant_tax_type(), approvalsChargement.getMerchant_tax(), approvalsChargement.getKpc_comm_type(), approvalsChargement.getKpc_commision(), 
					approvalsChargement.getKpc_tax_type(), approvalsChargement.getKpc_tax(), approvalsChargement.getAdd_comm_type01(), approvalsChargement.getAdd_commision01(), 
					approvalsChargement.getAdd_tax_type01(), approvalsChargement.getAdd_tax01(), approvalsChargement.getAdd_comm_type02(), approvalsChargement.getAdd_commision02(), 
					approvalsChargement.getAdd_tax_type02(), approvalsChargement.getAdd_tax02(), approvalsChargement.getBilling_sum(), approvalsChargement.getDesc01(), 
					approvalsChargement.getDesc02(), approvalsChargement.getLingUrl());
			resultList.add(approvalsChargementResult);
		}
		
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetChargementsList.setSummary(summary);
		responseApprovalsGetChargementsList.setResultList(resultList);
		return responseApprovalsGetChargementsList;
	}
	
	public ResponseApprovalsGetChargementsDailyList getChargementsDaily(AccountParam param){
		ResponseApprovalsGetChargementsDailyList responseApprovalsGetChargementsDailyList = new ResponseApprovalsGetChargementsDailyList();
		AccountSummaryModel approvalsSummary = accountMapper.getChargmentsDailyByCount(param);
		List<AccountApprovalSumm> approvalsApprovalSumms = accountMapper.getChargementsDaily(param);
		
		List<AccountApprovalSummResult> resultList = new ArrayList<AccountApprovalSummResult>();
		for(AccountApprovalSumm approvalsApprovalSumm : approvalsApprovalSumms){
			AccountApprovalSummResult approvalsApprovalSummResult = new AccountApprovalSummResult();
			approvalsApprovalSummResult.setValues(approvalsApprovalSumm.getSubmerchant_nm(), approvalsApprovalSumm.getService_nm(), approvalsApprovalSumm.getSvc_conn_id(), approvalsApprovalSumm.getDeal_divider(), 
					approvalsApprovalSumm.getDeal_dt(), approvalsApprovalSumm.getApproval_status_nm(), approvalsApprovalSumm.getApproval_status(), approvalsApprovalSumm.getCardCdName(), approvalsApprovalSumm.getCard_cd(), 
					approvalsApprovalSumm.getPayTypeName(), approvalsApprovalSumm.getPay_type(), approvalsApprovalSumm.getCnt(), approvalsApprovalSumm.getAmount(), approvalsApprovalSumm.getCommision());
			resultList.add(approvalsApprovalSummResult);
		}
		
		responseApprovalsGetChargementsDailyList.setLimit(param.getLimit());
		responseApprovalsGetChargementsDailyList.setOffset(param.getOffset());
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetChargementsDailyList.setSummary(summary);
		responseApprovalsGetChargementsDailyList.setResultList(resultList);
		return responseApprovalsGetChargementsDailyList;
	}
	
	public ResponseApprovalsGetChargementsMonthList getChargementsMonth(AccountParam param){
		ResponseApprovalsGetChargementsMonthList responseApprovalsGetChargementsMonthList = new ResponseApprovalsGetChargementsMonthList();
		AccountSummaryModel approvalsSummary = accountMapper.getChargementsMonthByCount(param);
		List<AccountApprovalSumm> approvalsApprovalSumms = accountMapper.getChargementMonth(param);
		
		List<AccountApprovalSummResult> resultList = new ArrayList<AccountApprovalSummResult>();
		for(AccountApprovalSumm approvalsApprovalSumm : approvalsApprovalSumms){
			AccountApprovalSummResult approvalsApprovalSummResult = new AccountApprovalSummResult();
			approvalsApprovalSummResult.setValues(approvalsApprovalSumm.getSubmerchant_nm(), approvalsApprovalSumm.getService_nm(), approvalsApprovalSumm.getSvc_conn_id(), approvalsApprovalSumm.getDeal_divider(), 
					approvalsApprovalSumm.getDeal_dt(), approvalsApprovalSumm.getApproval_status_nm(), approvalsApprovalSumm.getApproval_status(), approvalsApprovalSumm.getCardCdName(), approvalsApprovalSumm.getCard_cd(), 
					approvalsApprovalSumm.getPayTypeName(), approvalsApprovalSumm.getPay_type(), approvalsApprovalSumm.getCnt(), approvalsApprovalSumm.getAmount(), approvalsApprovalSumm.getCommision());
			resultList.add(approvalsApprovalSummResult);
		}
		
		responseApprovalsGetChargementsMonthList.setLimit(param.getLimit());
		responseApprovalsGetChargementsMonthList.setOffset(param.getOffset());
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), 
						  approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetChargementsMonthList.setSummary(summary);
		responseApprovalsGetChargementsMonthList.setResultList(resultList);
		return responseApprovalsGetChargementsMonthList;
	}
	
	public ResponseApprovalsGetSalementsList getSalements(AccountSalementParam param){
		ResponseApprovalsGetSalementsList responseApprovalsGetSalementsList = new ResponseApprovalsGetSalementsList();
		AccountSummaryModel approvalsSummary = accountMapper.getSalementsByCount(param);
		List<AccountSalement> salements = accountMapper.getsalements(param);		
		List<AccountSalementResult> resultList = new ArrayList<AccountSalementResult>();
		
		for(AccountSalement salement : salements){
			AccountSalementResult approvalsSalementResult = new AccountSalementResult();
			approvalsSalementResult.setValues(salement.getSeq(), salement.getSummSeq(), salement.getSubmerchantId(), salement.getSubmerchantNm(), salement.getServiceId(), 
					salement.getServiceNm(), salement.getServiceType(), salement.getServiceCategory(), salement.getSvcConnId(), salement.getDealDivider(), salement.getDealDt(), 
					salement.getApprovalDt(), salement.getApprovalStatusNm(), salement.getApprovalStatus(), salement.getApprovalNo(), salement.getOrderNo(), salement.getProdTypeNm(), 
					salement.getProdTypeNm(), salement.getProdCd(), salement.getProdNm(), salement.getSaleCnt(), salement.getSaleAmt(), salement.getSaleType(), salement.getSaleMethod(), 
					salement.getSaleMethodName(), salement.getStoreCd(), salement.getStoreNm(), salement.getPosNo(), salement.getCpId(), salement.getCpUserId(), salement.getCpUserIp(), 
					salement.getMerchantCommType(), salement.getMerchantCommision(), salement.getMerchantTaxType(), salement.getMerchantTax(), salement.getKpcCommType(), salement.getKpcCommision(), 
					salement.getKpcTax_type(), salement.getKpcTax(), salement.getAddCommType01(), salement.getAddCommision01(), salement.getAddTaxType01(), salement.getAddTax01(), 
					salement.getAddCommType02(), salement.getAddCommision02(), salement.getAddTaxType02(), salement.getAddTax02(), salement.getBillingSum(), salement.getDesc01(), salement.getDesc02());
			
			resultList.add(approvalsSalementResult);
		}
		
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetSalementsList.setSummary(summary);
		responseApprovalsGetSalementsList.setResultList(resultList);
		return responseApprovalsGetSalementsList;
	}
	
	public ResponseApprovalsGetSalementsMonthList getSalementsMonth(AccountSalementParam param){
		ResponseApprovalsGetSalementsMonthList responseApprovalsGetSalementsMonthList = new ResponseApprovalsGetSalementsMonthList();
		AccountSummaryModel approvalsSummary = accountMapper.getSalementsMothByCount(param);
		List<AccountSalementSumm> salementSumms = accountMapper.getSalementsMonth(param);
		List<AccountSalementSummResult> resultList = new ArrayList<AccountSalementSummResult>();
		
		for(AccountSalementSumm salementSumm : salementSumms){
			AccountSalementSummResult approvalsSalementSummResult = new AccountSalementSummResult();
			approvalsSalementSummResult.setValues(salementSumm.getSubmerchantNm(), salementSumm.getServiceNm(), salementSumm.getSvcConnId(), salementSumm.getApprovalStatus(), 
					salementSumm.getApprovalStatusNm(), salementSumm.getProdNm(), salementSumm.getProdCd(), salementSumm.getProdTypeNm(), salementSumm.getDealDt(), salementSumm.getSaleAmt(), 
					salementSumm.getSaleCnt(), salementSumm.getCnt());
			resultList.add(approvalsSalementSummResult);
		}
		
		kr.co.kpcard.backoffice.component.account.AccountSummary summary = new kr.co.kpcard.backoffice.component.account.AccountSummary();
		summary.setValues(approvalsSummary.getCount(), approvalsSummary.getAmountCount(), approvalsSummary.getRefundCount(), approvalsSummary.getCancelCount(),
				approvalsSummary.getTotalSum(), approvalsSummary.getAmountSum(), 0, 0, approvalsSummary.getCancelSum(), approvalsSummary.getOrigCancelSum(), approvalsSummary.getRefundSum(), approvalsSummary.getBillingSum(), approvalsSummary.getProvideAmountSum());
		responseApprovalsGetSalementsMonthList.setSummary(summary);
		responseApprovalsGetSalementsMonthList.setResultList(resultList);
		return responseApprovalsGetSalementsMonthList;
	}
}
