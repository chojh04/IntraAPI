package kr.co.kpcard.backoffice.repository.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantMapper;

@Repository
@Deprecated
public class MerchantRepository{
	private final Logger logger = LoggerFactory.getLogger(MerchantRepository.class);
	
	@Autowired
	MerchantMapper merchantMapper;
	//private static final int FAIL_INSERT = 1; 쓰지 않는 값
	/*
	@Deprecated
	public int postMerchant(String subMerchantId, String parentId, String name, String alias, String ceoName, String openDate, String bizRegNo,
			String corpRegNo, String bizKind, String bizCond, String zipcode, String address, String addressDetail,
			String bizGrp, String tel, String fax, String taxCustName, String taxTel, String taxFax, String taxPhone,
			String taxEmail, String bankNm, String bankAccNo, String bankHolder, String salesNm, String salesTel,
			String billingNm, String billingTel, String kpcSalesNm, String kpcSalesTel, String kpcBillingNm, String kpcBillingTel,
			String agentId, String agentPw, String useFlag, String urlHome)
	{
		//PlatformTransactionManager transactionManager = super.getTransactionManager();
		int result = 0;
		try {
			int dbResult = 0;
			
			dbResult = merchantMapper.postSubMerchant(subMerchantId, parentId, name, alias, useFlag, agentId, agentPw);
			dbResult += merchantMapper.postSubMerchantDetail(subMerchantId, bizKind, bizCond, corpRegNo, bizRegNo, ceoName, bizGrp, openDate, bankNm, bankAccNo, bankHolder, zipcode, address, addressDetail, tel, fax, taxCustName, taxEmail, taxTel, taxPhone, taxFax, salesNm, salesTel, billingNm, billingTel, kpcSalesNm, kpcSalesTel, kpcBillingNm, kpcBillingTel, urlHome);
			
			//transactionManager.commit(getTransactionStatus());
			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
			//transactionManager.rollback(getTransactionStatus());
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	
	@Deprecated
	public int putMerchant(String subMerchantId, String parentId, String name, String alias, String ceoName, String openDate, String bizRegNo,
			String corpRegNo, String bizKind, String bizCond, String zipcode, String address, String addressDetail,
			String bizGrp, String tel, String fax, String taxCustName, String taxTel, String taxFax, String taxPhone,
			String taxEmail, String bankNm, String bankAccNo, String bankHolder, String salesNm, String salesTel,
			String billingNm, String billingTel, String kpcSalesNm, String kpcSalesTel, String kpcBillingNm, String kpcBillingTel,
			String agentId, String agentPw, String encAgentPw, String useFlag, String urlHome)
	{
		//PlatformTransactionManager transactionManager = super.getTransactionManager();
		int result = 0;
		try {
			int dbResult = 0;
			
			String updateAgentPw = "";
			if(agentPw.equals(encAgentPw)){
				updateAgentPw = encAgentPw;
			}
			else
			{
				updateAgentPw = agentPw;//암호화 되어야 함
			}
			
			dbResult = merchantMapper.putSubMerchant(subMerchantId, taxCustName, alias, useFlag, agentId, updateAgentPw);
			dbResult += merchantMapper.putSubMerchantDetail(subMerchantId, bizKind, bizCond, corpRegNo, bizRegNo, ceoName, bizGrp, openDate, bankNm, bankAccNo, bankHolder, zipcode, address, addressDetail, tel, fax, taxCustName, taxEmail, taxTel, taxPhone, taxFax, salesNm, salesTel, billingNm, billingTel, kpcSalesNm, kpcSalesTel, kpcBillingNm, kpcBillingTel, urlHome);
			
			//transactionManager.commit(getTransactionStatus());
			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
			//transactionManager.rollback(getTransactionStatus());
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	
	@Deprecated
	public int deleteMerchant(String merchantId)
	{
		PlatformTransactionManager transactionManager = super.getTransactionManager();
		int result = 0;
		try {
			int dbResult = 0;
			
			dbResult += merchantMapper.deleteSubMerchantByBilling(merchantId);
			dbResult += merchantMapper.deleteSubMerchantByService(merchantId);
			dbResult += merchantMapper.deleteSubMerchantBySubMerchant(merchantId);
			transactionManager.commit(getTransactionStatus());
			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(getTransactionStatus());
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	*/
	@Deprecated
	public int postRepresentMerchant(String merchantId, String name, String alias, String ceoName, String openDate,
			String bizRegNo, String corpRegNo, String bizKind, String bizCond, String bizGrp,
			String useFlag, String zipcode, String address, String addressDetail, String tel,
			String fax, String createDesc)
	{
		
		int result = 0;
		try {
			int dbResult = 0;
			
			
			dbResult = merchantMapper.postRepresentMerchant(merchantId, name, alias, useFlag, createDesc);
			dbResult += merchantMapper.postRepresentMerchantDetail(merchantId, bizKind, bizCond, bizGrp, corpRegNo, bizRegNo, openDate, ceoName, zipcode, address, addressDetail, tel, fax);
			
			

			
			result = dbResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	
	@Deprecated
	public int putRepresentMerchant(String merchantId, String name, String alias, String ceoName, String openDate,
			String bizRegNo, String corpRegNo, String bizKind, String bizCond, String bizGrp,
			String useFlag, String zipcode, String address, String addressDetail, String tel,
			String fax, String updateDesc){
		int result = 0;
		try {
			int dbResult = 0;
			logger.debug("Repo merchantId : " + merchantId);
			dbResult += merchantMapper.putRepresentMerchant(merchantId, ceoName, alias, useFlag, updateDesc);
			dbResult += merchantMapper.putRepresentMerchantDetail(merchantId, addressDetail, bizGrp, bizKind, bizCond, corpRegNo, bizRegNo, openDate, ceoName, zipcode, addressDetail, tel, fax);

			if(!"".equals(bizRegNo))
			{
				int bizNoCount = merchantMapper.getBzNoCheck(bizRegNo, merchantId);
				if(bizNoCount > 0) 
				{
					return 0;
				}
			}
			if(!"".equals(corpRegNo))
			{
				int corpNoCount = merchantMapper.getCorpNoCheck(corpRegNo, merchantId);
				if(corpNoCount > 0)
				{
					return 0;
				}	
			}
			int detailCount = merchantMapper.getRepresentMerchantDetailCount(merchantId, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, zipcode, address, addressDetail, bizGrp, tel, fax);
			if(detailCount == 0)
			{
				dbResult +=  merchantMapper.putRepresentMerchantBySubMerchantDetail(merchantId, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, zipcode, address, addressDetail, bizGrp, tel, fax);
			}
				
			

			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
		
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	
	@Deprecated
	public int deleteRepresentMerchant(String merchantId){

		int result = 0;
		try {
			int dbResult = 0;
			
			dbResult += merchantMapper.deleteMerchantByMerchant(merchantId);
			dbResult += merchantMapper.deleteMerchantBySubMerchant(merchantId);
			dbResult += merchantMapper.deleteMerchantByService(merchantId);
			dbResult += merchantMapper.deleteMerchantByBilling(merchantId);

			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	/*
	@Deprecated
	public int deleteMerchantService(String serviceId){
		PlatformTransactionManager transactionManager = super.getTransactionManager();
		int result = 0;
		try {
			int dbResult = 0;
			
			dbResult += merchantMapper.deleteService(serviceId);
			dbResult += merchantMapper.deleteServiceByBilling(serviceId);
			transactionManager.commit(getTransactionStatus());
			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(getTransactionStatus());
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
	
	@Deprecated
	public int putMerchantBilling(String serviceBillingId, String serviceId, String name,  String bankAccNo, String bankHolder,
			String billingNm, String billingTel, String billingEmail, String kpcBillingNm, String kpcBillingTel, 
			String kpcBillingEmail, String divider, String code, String billingDuration, String billingDt, String bankNm,
			String merchantCommType, String merchantTaxType, String merchantCommision, String aplEndDt, String updateAdmId, String billingCommType){
		PlatformTransactionManager transactionManager = super.getTransactionManager();
		int result = 0;
		try {
			int dbResult = 0;
			
			dbResult += merchantMapper.putBilling(serviceBillingId, serviceId, name, code, divider, billingDuration, bankNm, bankAccNo, bankHolder, billingNm, billingTel, billingEmail, kpcBillingNm, kpcBillingTel, kpcBillingEmail, billingDt, merchantCommType, merchantTaxType, merchantCommision, billingCommType);
			dbResult += merchantMapper.putBillingHst(aplEndDt, merchantCommision, serviceBillingId);
			
			transactionManager.commit(getTransactionStatus());
			
			result = dbResult;
		} catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(getTransactionStatus());
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
		}
		return result;
	}
*/
}
