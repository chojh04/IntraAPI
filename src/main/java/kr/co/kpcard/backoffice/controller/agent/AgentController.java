package kr.co.kpcard.backoffice.controller.agent;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kpcard.backoffice.component.agent.AgentAuthFailureMessageException;
import kr.co.kpcard.backoffice.component.agent.AgentBillingHistory;
import kr.co.kpcard.backoffice.component.agent.AgentFailureMessageException;
import kr.co.kpcard.backoffice.component.agent.AgentService;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentBilling;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentBillingHistoryList;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentGetAgentMerchant;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentLoginable;
import kr.co.kpcard.backoffice.controller.agent.protocol.ResponseAgentServices;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestLogin;
import kr.co.kpcard.backoffice.service.agent.AgentLoginable;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;

@Controller
public class AgentController implements ResultCode{
	
	private static Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	private static final String URI_KPC_AGENT_LOGIN = "/kpcAgent/login";
	private static final String URI_GET_SERVICES = "/kpcAgent/merchant/services";
	private static final String URI_GET_BILLINGS_HISTORIES = "/kpcAgent/merchant/services/billingHistories";
	private static final String URI_GET_MERCHANT = "/kpcAgent/merchant";
	private static final String URI_GET_BILLING ="/kpcAgent/merchant/services/service/billing";
	
	@Autowired
	kr.co.kpcard.backoffice.service.agent.AgentService agentService;

	/**
	 * Agent 로그인 가능여부 조회
	 * @param id 아이디
	 * @param pwd 비밀번호
	 * @param loginIp 로그인 아이피
	 * 
	 * @return
	 */
	@RequestMapping(value = URI_KPC_AGENT_LOGIN, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseAgentLoginable isLoginable(RequestLogin Request){
		logger.info("Agent Login : [Id:"+ Request.getId()+", PW : "+Request.getPwd()+", Ip : "+Request.getLoginIp()+"]");
		ResponseAgentLoginable responseAgentLoginable = new ResponseAgentLoginable();
		
		AgentLoginable agentLoginable = agentService.isLoginable(Request.getId(), Request.getPwd(), Request.getLoginIp());
		
		if("LOGIN-0003".equals(agentLoginable.getLoginStatus())){
			throw new AgentAuthFailureMessageException("해당 계정은 잠금 상태입니다.\n관리자에게 문의바랍니다.", false, "해당 계정은 잠금 상태입니다.\n관리자에게 문의바랍니다.");
		} else if("LOGIN-0002".equals(agentLoginable.getLoginStatus())){
			throw new AgentAuthFailureMessageException("아이디 또는 비밀번호를 확인하세요.\nKpc Agent에 등록되지 않은 아이디거나, 아이디 또는 비밀번호를\n입력하셨습니다.", false, "아이디 또는 비밀번호를 확인하세요.\nKpc Agent에 등록되지 않은 아이디거나, 아이디 또는 비밀번호를\n입력하셨습니다.");
		} else {
			responseAgentLoginable.setAgentId(agentLoginable.getAgentId());
			responseAgentLoginable.setLoginTime(agentLoginable.getLoginStatus());
			responseAgentLoginable.setSubmerchantId(agentLoginable.getSubmerchant());
		} 
		
		return responseAgentLoginable;
	}	
	
	@RequestMapping(value = URI_GET_SERVICES, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseAgentServices getServices(
			@RequestParam(value="submerchantId", required=true) String subMerchantId // 거래처 아이디
			){
		ResponseAgentServices responseServices = new ResponseAgentServices();
		
		try{
			List<AgentService> services = agentService.getServices(subMerchantId);
			if(services == null || services.size() < 1)
			{
				throw new AgentFailureMessageException("not found services : " + subMerchantId, "not found services : " + subMerchantId);
			}
			else
			{
				responseServices.setServices(services);
			}
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseServices;
	}
	
	@RequestMapping(value = URI_GET_BILLINGS_HISTORIES, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseAgentBillingHistoryList getBillingHistories(
			@RequestParam(value="submerchantId", required=true) String subMerchantId, // 아이디
			@RequestParam(value="serviceId", required=true) String serviceId, // 비밀번호
			@RequestParam(value="startDate", required=true) String startDate, // 로그인 아이피
			@RequestParam(value="endDate", required=true) String endDate
			){
		ResponseAgentBillingHistoryList responseBillingHistoryList = new ResponseAgentBillingHistoryList();
		
		try{
			List<AgentBillingHistory> billingHistories = agentService.getBillingHistories(subMerchantId, serviceId, startDate, endDate);
			if(billingHistories == null || billingHistories.size() < 1)
			{
				throw new AgentFailureMessageException("not found services : " + serviceId, "not found services : " + serviceId);
			}
			else
			{
				responseBillingHistoryList.setResultList(billingHistories);
			}
		} catch(Exception e){
			logger.error("getBillingHistories {}" , e);
		}
		return responseBillingHistoryList;
	}	
		
	
	@RequestMapping(value = URI_GET_MERCHANT, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseAgentGetAgentMerchant getMerchant(
			@RequestParam(value="submerchantId", required=true) String subMerchantId
			){
		ResponseAgentGetAgentMerchant responseGetAgentMerchant = new ResponseAgentGetAgentMerchant();
		
		try{
			responseGetAgentMerchant = agentService.getMerchant(subMerchantId);
		} catch(Exception e){
			logger.error("getMerchant {}" , e);
		}
		return responseGetAgentMerchant;
	}
	
	@RequestMapping(value = URI_GET_BILLING, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseAgentBilling getBilling(
			@RequestParam(value="submerchantId", required=true) String subMerchantId, // 아이디
			@RequestParam(value="serviceBillingId", required=true) String serviceBillingId // 비밀번호
			){
		ResponseAgentBilling responseAgentBilling = new ResponseAgentBilling();
		
		try{
			responseAgentBilling = agentService.getBilling(subMerchantId, serviceBillingId);
		} catch(Exception e){
			logger.error("getBilling {}" , e);
		}
		return responseAgentBilling;
	}
}
