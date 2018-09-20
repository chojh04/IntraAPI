package kr.co.kpcard.backoffice.service.billing;


import java.util.ArrayList;
import java.util.List;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.component.ListSummary;
import kr.co.kpcard.backoffice.component.billing.BillingSpecificationList;
import kr.co.kpcard.backoffice.component.billing.BillingSpecificationSummary;
import kr.co.kpcard.backoffice.controller.billing.protocol.RequestGetRegBillings;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetBilling;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetBillings;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.billing.BillingRepository;
import kr.co.kpcard.backoffice.repository.billing.mapper.BillingMapper;
import kr.co.kpcard.backoffice.repository.billing.model.BillingEnrollment;
import kr.co.kpcard.backoffice.repository.billing.model.BillingSpecification;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantBasicMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantServiceMapper;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
import kr.co.kpcard.backoffice.service.card.ReqRestrictCardInfo;
import kr.co.kpcard.common.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


/**
 * @author chris
 * 정산명세서 조회 서비스
 */
@Service
public class BillingService {
	private static Logger logger = LoggerFactory.getLogger(BillingService.class);
	
	@Autowired
	BillingRepository billingRepository;
		
	@Autowired
	NewApprovalRepository newApprovalRepository;
	
	@Autowired
	SubMerchantServiceMapper subMerchantServiceMapper;
	
	@Autowired
	SubMerchantBasicMapper subMerchantBasicMapper;
	
	/**
	 * 정산명세서 리스트 조회
	 * @return 정산명세서 리스트
	 */
	public ResponseGetBillings getBillings(ReqGetBillings reqGetBillings){
		ResponseGetBillings responseBillingGetSpecsBillings = new ResponseGetBillings();
		BillingSpecificationSummary summary = new BillingSpecificationSummary();
		try {
			int count = billingRepository.getbillingsByCount(reqGetBillings.getMerchantId()
														, reqGetBillings.getServiceId()
														, reqGetBillings.getServiceType()
														, reqGetBillings.getDateType()
														, reqGetBillings.getStartDate()
														, reqGetBillings.getEndDate()
														, reqGetBillings.getBillingDuration()
														, reqGetBillings.getAdjustType());
			
			summary.setValues(count, reqGetBillings.getOffset(), reqGetBillings.getLimit());
			responseBillingGetSpecsBillings.setSummary(summary);
			
			if(count>0){
				List<BillingSpecification> billingSpecifications = billingRepository.getBillings(reqGetBillings.getOffset()
																							, reqGetBillings.getLimit()
																							, reqGetBillings.getMerchantId()
																							, reqGetBillings.getServiceId()
																							, reqGetBillings.getServiceType()
																							, reqGetBillings.getDateType()
																							, reqGetBillings.getStartDate()
																							, reqGetBillings.getEndDate()
																							, reqGetBillings.getBillingDuration()
																							, reqGetBillings.getExcelAllFlag()
																							, reqGetBillings.getAdjustType());
				responseBillingGetSpecsBillings.setResultList(billingSpecifications);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}		
		return responseBillingGetSpecsBillings;
	}
	
	/**
	 * 정산명세서 조회
	 * @param seq
	 * @return
	 */
	public ResGetBilling getBilling(String seq)
	{	
		ResGetBilling resGetBilling = new ResGetBilling();
		try{
			BillingSpecification billing = billingRepository.getBiliing(seq);
			
			//거래구분(충전, 결제)
			String dealType = ("TRNT-0001".equals(billing.getType()))? "CHR" : ("TRNT-0002".equals(billing.getType()))? "PAY" : "";
			logger.info("getBilling value : [billing.getType():"+billing.getType()+", dealType : "+dealType+"]");
			
			//GS대사 불일치 금액 조회
			long notMatchedAmount = billingRepository.getGsNotMatchedAmount(billing.getServiceConnId(), billing.getApprovalDtMin(), billing.getApprovalDtMax(), dealType);
			logger.info("notMatchedAmount : "+notMatchedAmount);
			
			billing.setNotMatchedAmount(notMatchedAmount);
			billing.setDifferenceAmount(0);
			resGetBilling.setBilling(billing);			
			
		}catch(Exception e){
			logger.error("Service Error : "+e.toString());			
		}
		return resGetBilling;
	}
	

	/**
	 * 정산명세서 등록
	 * @param apprInfo
	 * @return
	 */
	public boolean approveBillingApproval(Approval apprInfo){
		boolean result = false;
		Gson gson = new Gson();
		try{
			ApprovalContent approvalContent = newApprovalRepository.readApprovalContent(apprInfo.getContentSeq());
			logger.info("json : "+approvalContent.getContent());
			RegistBillingContent registBillingContent = gson.fromJson(approvalContent.getContent(), RegistBillingContent.class);
			logger.info("registBillingContent.getSeq() : "+ registBillingContent.getSeq());
			logger.info("registBillingContent.getAmount() : "+registBillingContent.getAmount());
			BillingSpecification billingSpecification = billingRepository.getBiliing(registBillingContent.getSeq());
			
			BillingEnrollment billingEnrollment = new BillingEnrollment(registBillingContent, billingSpecification);
			
			String nowDate = DateUtil.getCurrentDate(DateUtil.DATE_FORMAT);
			
			String seq =  billingRepository.getRegBillingSeq(nowDate);
			logger.info("now Seq : "+seq);
			billingEnrollment.setSeq(seq);
			
			result = billingRepository.regBilling(billingEnrollment);
						
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}		
		return result;
	}
	

	
	public ResGetRegBillings getRegBillings(RequestGetRegBillings requestBody){
		ResGetRegBillings resGetRegBillings = new ResGetRegBillings();
		try{
			logger.info(this.getClass().getName());
			int count = billingRepository.getRegBillingsCount(requestBody.getServiceId(), 
															requestBody.getMerchantId(),
															requestBody.getServiceType(),
															requestBody.getDateType(),
															requestBody.getStartDate(),
															requestBody.getEndDate(),
															requestBody.getStatus(),
															requestBody.getBillingType());
			
			ListSummary summary = new ListSummary(count, requestBody.getOffset(), requestBody.getLimit());
			resGetRegBillings.setSummary(summary);
			
			if(count>0){
				List<BillingEnrollment> regBillingList = billingRepository.getRegBillings(requestBody.getOffset(),
																						requestBody.getLimit(),
																						requestBody.getServiceId(), 
																						requestBody.getMerchantId(),
																						requestBody.getServiceType(),
																						requestBody.getDateType(),
																						requestBody.getStartDate(),
																						requestBody.getEndDate(),
																						requestBody.getStatus(),
																						requestBody.getBillingType(),
																						requestBody.getExcelAllFlag());
				
				resGetRegBillings.setResultList(regBillingList);
				
			}
			
		}catch(Exception e){
			logger.error("Error : " + e.toString());
		}		
		return resGetRegBillings;
	}
	

	public ResGetRegBilling getRegBilling(String seq){
		ResGetRegBilling resGetRegBilling = new ResGetRegBilling();
		try{
			logger.info(getClass().getName());
			BillingEnrollment regBillingList = billingRepository.getRegBilling(seq);				
			resGetRegBilling.setResultList(regBillingList);
		}catch(Exception e){
			logger.error("Error : " + e.toString());
		}		
		return resGetRegBilling;
	}
	
	
}
