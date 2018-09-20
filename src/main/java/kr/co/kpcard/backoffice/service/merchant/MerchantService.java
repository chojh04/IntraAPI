package kr.co.kpcard.backoffice.service.merchant;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.backoffice.component.merchant.MerchantBillingList;
import kr.co.kpcard.backoffice.component.merchant.MerchantList;
import kr.co.kpcard.backoffice.component.merchant.MerchantMerchantSummary;
import kr.co.kpcard.backoffice.component.merchant.RepresentList;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseGetRepresentMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantBilling;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantBillings;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetRepresentMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetService;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantServices;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantTotalMerchants;
import kr.co.kpcard.backoffice.repository.merchant.MerchantRepository;
import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantMapper;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantBillingsList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantMerchantList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantRepresent;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantRepresentList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantServiceList;

@Service
public class MerchantService {
	private final Logger logger = LoggerFactory.getLogger(MerchantService.class);
	
	@Autowired
	MerchantMapper merchantMapper;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	public ResponseMerchantGetMerchants getMerchants(int limit, int offset, String name, String merchantId, String alias, 
			String depth, String childId, String status, String bizRegNo){
		ResponseMerchantGetMerchants responseMerchantGetMerchants = new ResponseMerchantGetMerchants();
		try {
			int listTotalCount = merchantMapper.getSubMerchantsByCount(name, merchantId, alias, childId, status, bizRegNo);
			List<MerchantMerchantList> merchantMerchantList = merchantMapper.getMerchants(limit, offset, name, merchantId, alias, childId, status, bizRegNo);
			
			MerchantMerchantSummary merchantSummary = new MerchantMerchantSummary();
			merchantSummary.setCount(listTotalCount);
			merchantSummary.setLimit(limit);
			merchantSummary.setOffset(offset);
			List<MerchantList> merchantLists = new ArrayList<MerchantList>();
			for(MerchantMerchantList merchantList : merchantMerchantList){
				MerchantList list = new MerchantList();
				list.setValues(merchantList.getSubmerchant_id(), merchantList.getGubun(), merchantList.getParent_id(), 
						merchantList.getChild_id(), merchantList.getName(), merchantList.getAlias(), merchantList.getUse_flag(), 
						merchantList.getCreate_dt(), merchantList.getUpdatedt(), merchantList.getChild_id(), merchantList.getLingUrl());
				
				
				merchantLists.add(list);
			}
			responseMerchantGetMerchants.setSummary(merchantSummary);
			responseMerchantGetMerchants.setResultList(merchantLists);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetMerchants;
	}
	
	public ResponseMerchantTotalMerchants getTotalMerchants(int limit, int offset, String name, String merchantId, 
															String alias, String status, String bizRegNo){
		ResponseMerchantTotalMerchants responseMerchantTotalMerchants = new ResponseMerchantTotalMerchants();
		
		try {
			int count = merchantMapper.getTotalMerchantsByCount(name, merchantId, alias, status, bizRegNo);
			int subCount = merchantMapper.getTotalMerchantsBySubCount(name, merchantId, alias, status, bizRegNo);
			List<MerchantRepresentList> merchantRepresentLists = merchantMapper.getTotalMerchants(limit, offset, name, merchantId, alias, status, bizRegNo);
			List<RepresentList> representLists = new ArrayList<RepresentList>(); 
		
			for(MerchantRepresentList merchantRepresentList : merchantRepresentLists){
				RepresentList representList = new RepresentList();
				representList.setValues(merchantRepresentList.getMerchant_id(), merchantRepresentList.getGubun(), merchantRepresentList.getName(), 
						merchantRepresentList.getCreate_dt(), merchantRepresentList.getBiz_kind(), merchantRepresentList.getBiz_cond(), 
						merchantRepresentList.getBiz_grp(), merchantRepresentList.getCorp_reg_no(), merchantRepresentList.getAlias(), 
						merchantRepresentList.getUse_flag(), merchantRepresentList.getUpdatedt(), merchantRepresentList.getSubmerchantcnt(), 
						merchantRepresentList.getLingUrl());
				representLists.add(representList);
			}
			
			MerchantMerchantSummary summary = new MerchantMerchantSummary(); 
			summary.setLimit(limit);
			summary.setOffset(offset);
			summary.setCount(count+subCount);
			responseMerchantTotalMerchants.setResultList(representLists);
			responseMerchantTotalMerchants.setSummary(summary);
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return responseMerchantTotalMerchants;
	}
	
	public int getBznoCheck(String bizRegNo, String merchantId){
		int result = 0;
		try {
			result = merchantMapper.getBzNoCheck(bizRegNo, merchantId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	public int getCorpNoCheck(String corpRegNo, String merchantId){
		int result = 0;
		try {
			result = merchantMapper.getCorpNoCheck(corpRegNo, merchantId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	public int getSvcConnIdCheck(String svcConnId, String serviceId){
		int result = 0;
		try {
			result = merchantMapper.getSvcConnIdCheck(svcConnId, serviceId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	public ResponseMerchantGetMerchant getMerchant(String subMerchantId){
		ResponseMerchantGetMerchant responseMerchantGetMerchant = new ResponseMerchantGetMerchant();
		try {
			MerchantMerchant merchant = merchantMapper.getSubMerchant(subMerchantId);
			responseMerchantGetMerchant.setValues(merchant.getSubmerchant_id(), merchant.getErp_code(), merchant.getDepth(), 
					merchant.getParent_id(), merchant.getParentName(), merchant.getChild_id(), merchant.getName(), merchant.getName_eng(),
					merchant.getAlias(), merchant.getAlias_eng(), merchant.getUse_flag(), merchant.getUseFlagName(), merchant.getReuse_flag(),
					merchant.getDel_flag(), merchant.getPg_id(), merchant.getPg_pw(), merchant.getAgent_id(), merchant.getAgent_pw(), 
					merchant.getBiz_kind(), merchant.getBiz_cond(), merchant.getCorp_reg_no(), merchant.getBiz_reg_no(), merchant.getCust_nm(), 
					merchant.getCust_tel(), merchant.getCeo_nm(), merchant.getType(), merchant.getBiz_grp(), merchant.getBankCd(), 
					merchant.getBankNm(), merchant.getBank_acc_no(), merchant.getBank_holder(), merchant.getZipcode(), merchant.getAddress(),
					merchant.getAddress_dtl(), merchant.getTel(), merchant.getFax(), merchant.getTax_cust_nm(), merchant.getTax_email(),
					merchant.getTax_tel(), merchant.getTax_phone(), merchant.getTax_fax(), merchant.getSales_nm(), merchant.getSales_tel(),
					merchant.getBilling_nm(), merchant.getBilling_tel(), merchant.getKpc_sales_nm(), merchant.getKpc_sales_tel(), 
					merchant.getKpc_billing_nm(), merchant.getBilling_tel(), merchant.getUrl01(), merchant.getUrl02(), merchant.getUrl_home(),
					merchant.getOpenDt(), merchant.getPath());			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetMerchant;
	}
	/*
	@Deprecated
	public String postMerchant(String parentId, String name, String alias, String ceoName, String openDate, String bizRegNo,
			String corpRegNo, String bizKind, String bizCond, String zipcode, String address, String addressDetail,
			String bizGrp, String tel, String fax, String taxCustName, String taxTel, String taxFax, String taxPhone,
			String taxEmail, String bankNm, String bankAccNo, String bankHolder, String salesNm, String salesTel,
			String billingNm, String billingTel, String kpcSalesNm, String kpcSalesTel, String kpcBillingNm, String kpcBillingTel,
			String agentId, String agentPw, String useFlag, String urlHome){
		String result = "";
		
		try {
			String subMerchantId = merchantMapper.createSubMerchantId();
			int dbResult = 0;
			dbResult = merchantRepository.postMerchant(subMerchantId, parentId, name, alias, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, zipcode, address, addressDetail, bizGrp, tel, fax, taxCustName, taxTel, taxFax, taxPhone, taxEmail, bankNm, bankAccNo, bankHolder, salesNm, salesTel, billingNm, billingTel, kpcSalesNm, kpcSalesTel, kpcBillingNm, kpcBillingTel, agentId, agentPw, useFlag, urlHome);
			if(dbResult < 2) logger.error("DB");
			result = subMerchantId;	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}

	@Deprecated
	public int putMerchant(String subMerchantId, String parentId, String name, String alias, String ceoName, String openDate, String bizRegNo,
			String corpRegNo, String bizKind, String bizCond, String zipcode, String address, String addressDetail,
			String bizGrp, String tel, String fax, String taxCustName, String taxTel, String taxFax, String taxPhone,
			String taxEmail, String bankNm, String bankAccNo, String bankHolder, String salesNm, String salesTel,
			String billingNm, String billingTel, String kpcSalesNm, String kpcSalesTel, String kpcBillingNm, String kpcBillingTel,
			String agentId, String agentPw, String encAgentPw, String useFlag, String urlHome){
		int result = 0;
		
		try {
			int dbResult = merchantRepository.putMerchant(subMerchantId, parentId, name, alias, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, zipcode, address, addressDetail, bizGrp, tel, fax, taxCustName, taxTel, taxFax, taxPhone, taxEmail, bankNm, bankAccNo, bankHolder, salesNm, salesTel, billingNm, billingTel, kpcSalesNm, kpcSalesTel, kpcBillingNm, kpcBillingTel, agentId, agentPw, encAgentPw, useFlag, urlHome);
			result = dbResult;	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	@Deprecated
	public int deleteMerchant(String merchantId){
		int result = 0;
		
		try {
			result = merchantRepository.deleteMerchant(merchantId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	*/
	
	public ResponseMerchantGetRepresentMerchants getRepresentMerchants(int limit, int offset, String name, String merchantId, String alias, 
			String status, String bizRegNo){
		ResponseMerchantGetRepresentMerchants responseMerchantGetRepresentMerchants = new ResponseMerchantGetRepresentMerchants();
		
		try {
			int listTotalCount = merchantMapper.getMerchantsByCount(name, merchantId, alias, status, bizRegNo);
			List<MerchantRepresentList> representLists = merchantMapper.getRepresentList(limit, offset, name, merchantId, alias, status, bizRegNo);
			
			MerchantMerchantSummary merchantSummary = new MerchantMerchantSummary();
			merchantSummary.setCount(listTotalCount);
			merchantSummary.setLimit(limit);
			merchantSummary.setOffset(offset);
			List<kr.co.kpcard.backoffice.component.merchant.RepresentList> merchantLists = new ArrayList<kr.co.kpcard.backoffice.component.merchant.RepresentList>();
			for(MerchantRepresentList merchantRepresentList : representLists){
				kr.co.kpcard.backoffice.component.merchant.RepresentList merchant = new kr.co.kpcard.backoffice.component.merchant.RepresentList();
				merchant.setValues(merchantRepresentList.getMerchant_id(), merchantRepresentList.getGubun(), merchantRepresentList.getName(), 
						merchantRepresentList.getCreate_dt(), merchantRepresentList.getBiz_kind(), merchantRepresentList.getBiz_cond(), 
						merchantRepresentList.getBiz_grp(), merchantRepresentList.getCorp_reg_no(), merchantRepresentList.getAlias(), 
						merchantRepresentList.getUse_flag(), merchantRepresentList.getUpdatedt(), merchantRepresentList.getSubmerchantcnt(), merchantRepresentList.getLingUrl());
				merchantLists.add(merchant);
			}
			responseMerchantGetRepresentMerchants.setSummary(merchantSummary);
			responseMerchantGetRepresentMerchants.setResultList(merchantLists);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return responseMerchantGetRepresentMerchants;
	}
	
	public ResponseGetRepresentMerchant getRepresentMerchant(String representId){
		ResponseGetRepresentMerchant responseGetRepresentMerchant = new ResponseGetRepresentMerchant();
		try {
			MerchantRepresent merchantRepresent = merchantMapper.getMerchant(representId);
			responseGetRepresentMerchant.setValue(merchantRepresent.getMerchant_id(), merchantRepresent.getErp_code(), merchantRepresent.getName(), 
					merchantRepresent.getName_eng(), merchantRepresent.getAlias(), merchantRepresent.getAlias_eng(), merchantRepresent.getCreate_dt(), 
					merchantRepresent.getCreate_desc(), merchantRepresent.getUpdate_dt(), merchantRepresent.getUpdate_desc(), merchantRepresent.getBiz_kind(), 
					merchantRepresent.getBiz_cond(), merchantRepresent.getBiz_grp(), merchantRepresent.getBizGrpName(), merchantRepresent.getBiz_reg_no(), 
					merchantRepresent.getCorp_reg_no(), merchantRepresent.getOpen_dt(), merchantRepresent.getClose_dt(), merchantRepresent.getClosed_flag(), 
					merchantRepresent.getCapital_amt(), merchantRepresent.getEmp_cnt(), merchantRepresent.getStore_cnt(), merchantRepresent.getAvg_sale_month(), 
					merchantRepresent.getAvg_sale_year(), merchantRepresent.getCeo_nm(), merchantRepresent.getZipcode(), merchantRepresent.getAddress(), 
					merchantRepresent.getAddress_dtl(), merchantRepresent.getTel(), merchantRepresent.getTel_sub(), merchantRepresent.getFax(), 
					merchantRepresent.getFax_sub(), merchantRepresent.getSubmerchantCnt(), merchantRepresent.getUse_flag(), merchantRepresent.getUseFlagName());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return responseGetRepresentMerchant;
	}

	
	public String postRepresentMerchant(String merchantId, String name, String alias, String ceoName, String openDate,
							String bizRegNo, String corpRegNo, String bizKind, String bizCond, String bizGrp,
							String useFlag, String zipcode, String address, String addressDetail, String tel,
							String fax, String createDesc){
		String result = "";
		
		try {
			merchantId = merchantMapper.createMerchantId();
			if("".equals(merchantId)) return result;
			
			
			int dbResult = merchantRepository.postRepresentMerchant(merchantId, name, alias, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, bizGrp, useFlag, zipcode, address, addressDetail, tel, fax, createDesc);
			if(dbResult > 1)
			{
				result = merchantId;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}
	

	public int putRepresentMerchant(String merchantId, String name, String alias, String ceoName, String openDate,
			String bizRegNo, String corpRegNo, String bizKind, String bizCond, String bizGrp,
			String useFlag, String zipcode, String address, String addressDetail, String tel,
			String fax, String updateDesc){
		int result = 0;
		
		try {
			result = merchantRepository.putRepresentMerchant(merchantId, name, alias, ceoName, openDate, bizRegNo, corpRegNo, bizKind, bizCond, bizGrp, useFlag, zipcode, address, addressDetail, tel, fax, updateDesc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	

	public int deleteRepresentMerchant(String merchantId){
		int result = 0;
		
		try {
			result = merchantRepository.deleteRepresentMerchant(merchantId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	public ResponseMerchantServices getMerchantServices(int limit, int offset, String merchantId, String name,
			String useFlag, String serviceId, String svcConnId, String serviceType, String merchantName, String billingRegFlag){
		ResponseMerchantServices responseMerchantServices = new ResponseMerchantServices();
		
		try {
			int servicesCount = merchantMapper.getServicesByCount(merchantId, serviceId, svcConnId, name, useFlag, serviceType, billingRegFlag);
			List<MerchantServiceList> merchantServiceLists = merchantMapper.getServices(limit, offset, merchantId, name, useFlag, serviceId, svcConnId, serviceType, merchantName, billingRegFlag);
			
			List<kr.co.kpcard.backoffice.component.merchant.MerchantServiceList> serviceLists = new ArrayList<kr.co.kpcard.backoffice.component.merchant.MerchantServiceList>();
			
			for(MerchantServiceList merchantServiceList : merchantServiceLists)
			{
				kr.co.kpcard.backoffice.component.merchant.MerchantServiceList serviceList = new kr.co.kpcard.backoffice.component.merchant.MerchantServiceList();
				serviceList.setValues(merchantServiceList.getService_id(), 
									  merchantServiceList.getSubmerchant_id(), 
									  merchantServiceList.getName(),
									  merchantServiceList.getMerchantName(),
									  merchantServiceList.getCategoryName(), 
									  merchantServiceList.getCategory(),
									  merchantServiceList.getTypeName(),
									  merchantServiceList.getTypeCode(), 
									  merchantServiceList.getUse_flag(),
									  merchantServiceList.getUseFlagName(),
									  merchantServiceList.getBillingName(),
									  merchantServiceList.getService_billing_id(),
									  merchantServiceList.getSvc_conn_id()
									  );
				serviceLists.add(serviceList);
			}
			
			MerchantMerchantSummary summary = new MerchantMerchantSummary();
			summary.setCount(servicesCount);
			summary.setLimit(limit);
			summary.setOffset(offset);
			
			responseMerchantServices.setResultList(serviceLists);
			responseMerchantServices.setSummary(summary);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return responseMerchantServices;
	}
	
	@Deprecated
	public ResponseMerchantGetService getMerchantService(String serviceId){
		ResponseMerchantGetService responseMerchantGetService = new ResponseMerchantGetService();
		
		try {
			logger.debug(serviceId);
			kr.co.kpcard.backoffice.repository.merchant.model.MerchantService merchantService = merchantMapper.getService(serviceId);
			responseMerchantGetService.setValues(merchantService.getService_id(), merchantService.getSubmerchant_id(), merchantService.getName(),
					merchantService.getCategoryName(), merchantService.getCategory(), merchantService.getServiceTypeName(), merchantService.getServiceType(),
					merchantService.getSaleDivider(), merchantService.getSaleDividerName(), merchantService.getUse_flag(), merchantService.getUseFlagName(), 
					merchantService.getSvc_conn_id(), merchantService.getSvc_conn_pw(), merchantService.getPath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return responseMerchantGetService;
	}
	
	@Deprecated
	public String postMerchantService(String submerchantId, String serviceName, String category, String serviceType,
			String saleDivider, String useFlag, String svcConnId, String svcConnPw, String agentId, String agentPw,
			String createDesc, String createAdmId)
	{
		String result = "";
		
		try {
			String serviceId = "";
			
			serviceId = merchantMapper.createServiceId();
			
			if(!"".equals(serviceId))
			{
				merchantMapper.postService(serviceId, submerchantId, serviceName, category, serviceType, useFlag, svcConnId, svcConnPw, saleDivider, createDesc, createAdmId);	
			}
			
			result = serviceId;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	
	@Deprecated
	public int putMerchantService(String serviceId, String submerchantId, String name, String category, String serviceType,
			String useFlag, String saleDivider, String svcConnId, String svcConnPw, String agentId, String agentPw,
			String updateDesc, String updateAdmId){
		int result = 0;
		
		try {
			result = merchantMapper.putService(serviceId, submerchantId, name, category, serviceType, useFlag, svcConnId, svcConnPw, saleDivider, updateDesc, updateAdmId);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	/*
	@Deprecated
	public int deleteMerchantService(String serviceId){
		int result = 0;
		
		try {
			result = merchantRepository.deleteMerchantService(serviceId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	
	@Deprecated
	public ResponseMerchantBillings getMerchantBiilings(int limit, int offset, String serviceId){
		ResponseMerchantBillings responseMerchantBillings = new ResponseMerchantBillings();
		
		try {
			int billingsCount = merchantMapper.getBillingsByCount(serviceId);
			List<MerchantBillingsList> billingsLists = merchantMapper.getBillings(limit, offset, serviceId);
			List<MerchantBillingList> resultList = new ArrayList<MerchantBillingList>();
			for(MerchantBillingsList merchantBillingsList : billingsLists ){
				MerchantBillingList billingList = new MerchantBillingList();
				if(billingList!=null){
				billingList.setValues(merchantBillingsList.getService_billing_id(), merchantBillingsList.getService_id(), merchantBillingsList.getName(), 
						merchantBillingsList.getCode(), merchantBillingsList.getCodeName(), merchantBillingsList.getDivider(), 
						merchantBillingsList.getDividerName(), merchantBillingsList.getBankCd(), merchantBillingsList.getBankNm(), 
						merchantBillingsList.getBank_acc_no(), merchantBillingsList.getVr_bank_nm(), merchantBillingsList.getVr_bank_acc_no(),
						merchantBillingsList.getBank_holder(), merchantBillingsList.getBilling_dt(), merchantBillingsList.getBilling_dt(),
						merchantBillingsList.getBilling_duration(), merchantBillingsList.getBillingDurationName(), merchantBillingsList.getMerchant_comm_type(), 
						merchantBillingsList.getMerchantCommTypeName(), merchantBillingsList.getMerchant_commision(), merchantBillingsList.getMerchant_commisionName(),
						merchantBillingsList.getMerchant_tax_type(), merchantBillingsList.getMerchantTaxTypeName(), merchantBillingsList.getMerchant_tax(), 
						merchantBillingsList.getMng_nm(), merchantBillingsList.getMng_tel(), merchantBillingsList.getMng_email(), 
						merchantBillingsList.getKpc_mng_nm(), merchantBillingsList.getKpc_mng_tel(), merchantBillingsList.getKpc_mng_email(), 
						merchantBillingsList.getCreate_dt(), merchantBillingsList.getApl_end_dt(), merchantBillingsList.getExpectedMerchantCommision(), merchantBillingsList.getBillingCommType());
				resultList.add(billingList);
				}
			}
			MerchantMerchantSummary summary = new MerchantMerchantSummary();
			summary.setCount(billingsCount);
			summary.setLimit(limit);
			summary.setOffset(offset);
			responseMerchantBillings.setResultList(resultList);
			responseMerchantBillings.setSummary(summary);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return responseMerchantBillings;
	}
	
	@Deprecated
	public ResponseMerchantBilling getMerchantBilling(String serviceBillingId){
		ResponseMerchantBilling responseMerchantBilling = new ResponseMerchantBilling();
		
		try {
			MerchantBillingsList merchantBilling = merchantMapper.getBilling(serviceBillingId);
			if(merchantBilling!=null){
			responseMerchantBilling.setValues(merchantBilling.getService_billing_id(), merchantBilling.getService_id(), merchantBilling.getName(), 
					merchantBilling.getCode(), merchantBilling.getCodeName(), merchantBilling.getDivider(), merchantBilling.getDividerName(), 
					merchantBilling.getBankCd(), merchantBilling.getBankNm(), merchantBilling.getBank_acc_no(), merchantBilling.getVr_bank_nm(),
					merchantBilling.getVr_bank_acc_no(), merchantBilling.getBank_holder(), merchantBilling.getBilling_dt(), merchantBilling.getBilling_dt_name(), 
					merchantBilling.getBilling_duration(), merchantBilling.getBillingDurationName(), merchantBilling.getMerchant_comm_type(),
					merchantBilling.getMerchantCommTypeName(), merchantBilling.getMerchant_commision(), merchantBilling.getMerchant_commisionName(),
					merchantBilling.getMerchant_tax_type(), merchantBilling.getMerchantTaxTypeName(), merchantBilling.getMerchant_tax(), 
					merchantBilling.getMng_nm(), merchantBilling.getMng_tel(), merchantBilling.getMng_email(), merchantBilling.getKpc_mng_nm(),
					merchantBilling.getKpc_mng_tel(), merchantBilling.getKpc_mng_email(), merchantBilling.getCreate_dt(), merchantBilling.getApl_end_dt(),
					merchantBilling.getExpectedMerchantCommision(),merchantBilling.getBillingCommType(), merchantBilling.getBillingCommTypeName());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return responseMerchantBilling;
	}
	
	@Deprecated
	public String postMerchantBilling(String serviceId, String name, String bankNm, String bankAccNo, String bankHolder,
			String billingNm, String billingTel, String billingEmail, String kpcBillingNm, String kpcBillingTel,
			String kpcBillingEmail, String divider, String code, String billingDuration, String billingDt,
			String merchantCommType, String merchantTaxType, String merchantCommision, String createAdmId, String billingCommType)
	{
		String result = "";
		
		try {
			String serviceBillingId = merchantMapper.createBillingId();
			if("".equals(serviceBillingId) || serviceBillingId == null)
			{
				return result;
			}
			
			int dbResult = merchantMapper.postBilling(serviceBillingId, serviceId, name, bankNm, bankAccNo, bankHolder, billingNm, billingTel, billingEmail, kpcBillingNm, kpcBillingTel, kpcBillingEmail, divider, code, billingDuration, billingDt, merchantCommType, merchantTaxType, merchantCommision, createAdmId, billingCommType);
			
			if(dbResult > 0)
			{
				result = serviceBillingId;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	
	@Deprecated
	public int putMerchantBilling(String serviceBillingId, String serviceId, String name,  String bankAccNo, String bankHolder,
			String billingNm, String billingTel, String billingEmail, String kpcBillingNm, String kpcBillingTel, 
			String kpcBillingEmail, String divider, String code, String billingDuration, String billingDt, String bankNm,
			String merchantCommType, String merchantTaxType, String merchantCommision, String aplEndDt, String updateAdmId, String billingCommType){
		int result = 0;
		
		try {
			result = merchantRepository.putMerchantBilling(serviceBillingId, serviceId, name, bankAccNo, bankHolder, billingNm, billingTel, billingEmail, kpcBillingNm, kpcBillingTel, kpcBillingEmail, divider, code, billingDuration, billingDt, bankNm, merchantCommType, merchantTaxType, merchantCommision, aplEndDt, updateAdmId, billingCommType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	
	@Deprecated
	public int deleteMerchantBilling(String billingId){
		int result = 0;
		
		try {
			result = merchantMapper.deleteBilling(billingId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		
		return result;
	}
	*/
}
