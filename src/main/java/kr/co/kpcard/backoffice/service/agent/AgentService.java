package kr.co.kpcard.backoffice.service.agent;


import java.util.ArrayList;
import java.util.List;

import kr.co.kpcard.backoffice.component.agent.AgentBillingHistory;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentBilling;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentGetAgentMerchant;
import kr.co.kpcard.backoffice.repository.agent.mapper.AgentMapper;
import kr.co.kpcard.backoffice.repository.agent.model.AgentBillingCycle;
import kr.co.kpcard.backoffice.repository.agent.model.AgentBillings;
import kr.co.kpcard.backoffice.repository.agent.model.AgentLoginInfo;
import kr.co.kpcard.backoffice.repository.agent.model.AgentMerchant;
import kr.co.kpcard.backoffice.repository.system.mapper.LoginMapper;
import kr.co.kpcard.common.utils.EncodeString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
	private final Logger logger = LoggerFactory.getLogger(AgentService.class);
	
	@Autowired
	AgentMapper agentMapper;
	
	@Autowired
	LoginMapper loginMapper;
	
	public AgentLoginable isLoginable(String id, String pwd, String loginIp){
		AgentLoginable agentLoginable = new AgentLoginable();
		try {
			int loginFalseCount = loginMapper.getLoginHstStatus(id, "AGENT");
			String loginStatus = "LOGIN-0003";
			if(loginFalseCount < 5)
			{
				EncodeString encodeString = new EncodeString();
				AgentLoginInfo agentLoginInfo = agentMapper.isLoginable(id, encodeString.encodeSha512(pwd));
				logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!" + agentLoginable.toStringForLog());
				if(agentLoginInfo == null) {
					loginStatus = "LOGIN-0002";
				} else {
					loginStatus = "LOGIN-0001";
					agentLoginable.setAgentId(agentLoginInfo.getAgent_id());
					agentLoginable.setLoginTime(agentLoginInfo.getLoginTime());
					agentLoginable.setSubmerchant(agentLoginInfo.getSubmerchant_id());
				}
				
				loginMapper.loginHst("AGENT", id, loginStatus, loginIp);
				
				
				
			}
			
			agentLoginable.setLoginStatus(loginStatus);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		logger.debug(agentLoginable.toStringForLog());
		
		return agentLoginable;
	}
	
	public List<kr.co.kpcard.backoffice.component.agent.AgentService> getServices(String subMerchantId){
		List<kr.co.kpcard.backoffice.component.agent.AgentService> services = new ArrayList<kr.co.kpcard.backoffice.component.agent.AgentService>();
		
		try {
			logger.debug(subMerchantId);
			List<kr.co.kpcard.backoffice.repository.agent.model.AgentService> agentServices = agentMapper.getServices(subMerchantId);
			
			for(kr.co.kpcard.backoffice.repository.agent.model.AgentService agentService : agentServices)
			{
				kr.co.kpcard.backoffice.component.agent.AgentService service = new kr.co.kpcard.backoffice.component.agent.AgentService();
				service.setServiceId(agentService.getService_id());
				service.setServiceBillingId(agentService.getService_billing_id());
				logger.debug(agentService.getService_id());
				logger.debug(agentService.getService_billing_id());
				
				services.add(service);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return services;
	}
	
	public List<AgentBillingHistory> getBillingHistories(String subMerchantId, String serviceId, String startDate, String endDate){
		List<AgentBillingHistory> billingHistories = new ArrayList<AgentBillingHistory>();
		try {
			List<AgentBillingCycle> agentBillingCycles = agentMapper.getBillingCycles(subMerchantId, serviceId, startDate, endDate);
			for(AgentBillingCycle agentBillingCycle : agentBillingCycles){
				List<kr.co.kpcard.backoffice.repository.agent.model.AgentBillingHistory> agentBillingHistories = agentMapper.getBillingHistories(subMerchantId, serviceId, 
						agentBillingCycle.getStartdate(), agentBillingCycle.getEnddate(), agentBillingCycle.getBillingdate());
				for(kr.co.kpcard.backoffice.repository.agent.model.AgentBillingHistory agentBillingHistory : agentBillingHistories){
					AgentBillingHistory billingHistory = new AgentBillingHistory();
					billingHistory.setValues(agentBillingHistory.getSubmerchant_nm(), agentBillingHistory.getService_id(), 
							agentBillingHistory.getService_nm(), agentBillingHistory.getStart_date(), agentBillingHistory.getEnd_date(),
							agentBillingHistory.getBilling_date(), agentBillingHistory.getStatus(), agentBillingHistory.getPayment_cnt(),
							agentBillingHistory.getPayment_sum(), agentBillingHistory.getCancel_cnt(), agentBillingHistory.getCancel_sum(),
							agentBillingHistory.getBilling_amount(), agentBillingHistory.getAffiliate_comm(), agentBillingHistory.getAffiliate_vat(),
							agentBillingHistory.getAffiliate_summ(), agentBillingHistory.getFinal_billing_amount());
					billingHistories.add(billingHistory);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return billingHistories;
	}
	
	public ResponseAgentGetAgentMerchant getMerchant(String subMerchantId){
		ResponseAgentGetAgentMerchant  responseGetAgentMerchant = new ResponseAgentGetAgentMerchant();
		try {
			AgentMerchant agentMerchant = agentMapper.getMerchant(subMerchantId);
			responseGetAgentMerchant.setValues(agentMerchant.getSubmerchant_id(), agentMerchant.getErp_code(), agentMerchant.getDepth(),
					agentMerchant.getParent_id(), agentMerchant.getParentName(), agentMerchant.getChild_id(), agentMerchant.getName(), 
					agentMerchant.getName_eng(), agentMerchant.getAlias(), agentMerchant.getAlias_eng(), agentMerchant.getUse_flag(), 
					agentMerchant.getUseFlagName(), agentMerchant.getReuse_flag(), agentMerchant.getDel_flag(), agentMerchant.getPg_id(), 
					agentMerchant.getAgent_id(), agentMerchant.getBiz_kind(), agentMerchant.getBiz_cond(), agentMerchant.getCorp_reg_no(), 
					agentMerchant.getBiz_reg_no(), agentMerchant.getCust_nm(), agentMerchant.getCust_tel(), agentMerchant.getCeo_nm(), 
					agentMerchant.getType(), agentMerchant.getBiz_grp(), agentMerchant.getZipcode(), agentMerchant.getAddress(), 
					agentMerchant.getAddress_dtl(), agentMerchant.getTel(), agentMerchant.getFax(), agentMerchant.getTax_cust_nm(), 
					agentMerchant.getTax_email(), agentMerchant.getTax_tel(), agentMerchant.getTax_phone(), agentMerchant.getTax_fax(), 
					agentMerchant.getSales_nm(), agentMerchant.getSales_tel(), agentMerchant.getBilling_nm(), agentMerchant.getBilling_tel(),
					agentMerchant.getKpc_sales_nm(), agentMerchant.getKpc_sales_tel(), agentMerchant.getKpc_billing_nm(), agentMerchant.getKpc_billing_tel());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseGetAgentMerchant;
	}
	
	public ResponseAgentBilling getBilling(String subMerchantId, String serviceBillingId){
		ResponseAgentBilling responseAgentBilling = new ResponseAgentBilling();
		try {
			AgentBillings agentBillings = agentMapper.getBilling(subMerchantId, serviceBillingId);
			responseAgentBilling.setValues(agentBillings.getService_billing_id(), agentBillings.getService_id(), agentBillings.getName(), 
					agentBillings.getCode(), agentBillings.getCodeName(), agentBillings.getDivider(), agentBillings.getDividerName(), 
					agentBillings.getBankCd(), agentBillings.getBankNm(), agentBillings.getBank_acc_no(), agentBillings.getVr_bank_nm(), 
					agentBillings.getVr_bank_acc_no(), agentBillings.getBank_holder(), agentBillings.getBilling_dt(), agentBillings.getBilling_dt_name(), 
					agentBillings.getBilling_duration(), agentBillings.getBillingDurationName(), agentBillings.getMerchant_comm_type(), 
					agentBillings.getMerchantCommTypeName(), agentBillings.getMerchant_commision(), agentBillings.getMerchant_commisionName(),
					agentBillings.getMerchant_tax_type(), agentBillings.getMerchantTaxTypeName(), agentBillings.getMerchant_tax(), 
					agentBillings.getMng_nm(), agentBillings.getMng_tel(), agentBillings.getMng_email(), agentBillings.getKpc_mng_nm(), 
					agentBillings.getKpc_mng_tel(), agentBillings.getKpc_mng_email(), agentBillings.getCreate_dt(), agentBillings.getApl_end_dt(),
					agentBillings.getExpectedMerchantCommision());			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseAgentBilling;
	}
	
}
