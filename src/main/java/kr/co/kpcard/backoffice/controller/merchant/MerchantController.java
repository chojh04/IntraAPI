package kr.co.kpcard.backoffice.controller.merchant;



import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestGetMetchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestMerchantCheck;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestPutPepresent;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestRepresentMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestServiceConnCheck;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestSubMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.RequestTotalMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseGetRepresentMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantAction;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantBilling;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantBillings;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantCheck;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetRepresentMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantGetService;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantServices;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchantTotalMerchants;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseSubMerchantBilling;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseSubMerchantPath;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestBilling;
import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantMapper;
import kr.co.kpcard.backoffice.service.merchant.MerchantService;
import kr.co.kpcard.backoffice.service.merchant.NewMerchantService;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;

@Controller
public class MerchantController implements ResultCode{
	
	private static Logger logger = LoggerFactory.getLogger(MerchantController.class);
	
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	NewMerchantService newMerchantService;

	
	@Autowired
	MerchantMapper merchantMapper;
	
	private static final String URI_MERCHANTS = "/merchants";
	private static final String URI_TOTAL_MERCHANTS = "/merchants/totalMerchants";
	private static final String URI_BZNO_CHECK = "/merchants/merchant/bznoCheck";
	private static final String URI_CORP_NO_CHECK = "/merchants/merchant/corpNoCheck";
	private static final String URI_SVC_CONN_ID_CHECK = "/merchants/merchant/svcConnIdCheck";
	private static final String URI_MERCHANT = "/merchants/merchant";
	private static final String URI_REPRESENTS = "/merchants/represents";
	private static final String URI_REPRESENT = "/merchants/represent";
	private static final String URI_SERVICES = "/merchants/services";
	private static final String URI_SERVICE = "/merchants/services/service";
	private static final String URI_BILLINGS = "/merchants/billings";
	private static final String URI_BILLING = "/merchants/services/service/billing";
	
	
	@ApiOperation(value="거래처 정보 조회(리스트)", nickname="거래처 정보 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantGetMerchants.class),
						 @ApiResponse(code=400, message="Parameter not vaild")})
	@RequestMapping(value = URI_MERCHANTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantGetMerchants getMerchants(RequestGetMetchants requestGetMetchants){
		ResponseMerchantGetMerchants responseMerchantGetMerchants = new ResponseMerchantGetMerchants();		
		try {
			if(requestGetMetchants.getLimit()<0 || requestGetMetchants.getOffset()<0)
			{			
				throw new FailureMessageException("Not Validated Parameter","Parameter 값이 올바르지 않습니다.[limit:"+requestGetMetchants.getLimit()+", offset:"+requestGetMetchants.getOffset()+"]");
			}			
			
			responseMerchantGetMerchants = merchantService.getMerchants(requestGetMetchants.getLimit(), 
																		requestGetMetchants.getOffset(),
																		requestGetMetchants.getName(),
																		requestGetMetchants.getMerchantId(),
																		requestGetMetchants.getAlias(),
																		requestGetMetchants.getDepth(),
																		requestGetMetchants.getChildId(), 
																		requestGetMetchants.getStatus(),
																		requestGetMetchants.getBizRegNo()
																		);			
		}catch(FailureMessageException fe){
			fe.printStackTrace();
			logger.error(fe.getMessage());
			throw fe;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		}
		return responseMerchantGetMerchants;
	}
	
	
	@ApiOperation(value="거래처 종합정보 조회(리스트)", nickname="거래처 종합 정보 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantTotalMerchants.class)})
	@RequestMapping(value = URI_TOTAL_MERCHANTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantTotalMerchants getTotalMerchants(RequestTotalMerchants requestTotalMerchants){
		ResponseMerchantTotalMerchants responseMerchantTotalMerchants = new ResponseMerchantTotalMerchants();
		try {
			if(requestTotalMerchants.getLimit()<0 || requestTotalMerchants.getOffset()<0)
			{			
				throw new FailureMessageException("Not Validated Parameter","Parameter 값이 올바르지 않습니다.[limit:"+requestTotalMerchants.getLimit()+", offset:"+requestTotalMerchants.getOffset()+"]");
			}
			
			responseMerchantTotalMerchants = merchantService.getTotalMerchants(requestTotalMerchants.getLimit(),
																				requestTotalMerchants.getOffset(),
																			   requestTotalMerchants.getName(),
																			   requestTotalMerchants.getRepresentId(),
																			   requestTotalMerchants.getAlias(),
																			   requestTotalMerchants.getStatus(),
																			   requestTotalMerchants.getBizRegNo()
																			   );
		}catch(FailureMessageException fe){
			fe.printStackTrace();
			logger.error(fe.getMessage());
			throw fe;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantTotalMerchants;
	}
	
	@ApiOperation(value="사업자 정보 조회", nickname="사업자 정보 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantCheck.class)})
	@RequestMapping(value = URI_BZNO_CHECK, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantCheck getBznoCheck(RequestMerchantCheck requestMerchantCheck){
		ResponseMerchantCheck responseMerchantCheck = new ResponseMerchantCheck();
		try {
			if(requestMerchantCheck.getBizRegNo()==null || 
			   requestMerchantCheck.getMerchantId()==null ||
			   "".equals(requestMerchantCheck.getBizRegNo()) ||
			   "".equals(requestMerchantCheck.getMerchantId())
			   )
			{
				throw new FailureMessageException("Not Validated Parameter","Parameter 값이 올바르지 않습니다.[limit:"+requestMerchantCheck.getBizRegNo()+", offset:"+requestMerchantCheck.getMerchantId()+"]");
			}			
			
			int result = merchantService.getBznoCheck(requestMerchantCheck.getBizRegNo(), requestMerchantCheck.getMerchantId());
			
			responseMerchantCheck.setCnt(result);
		}catch(FailureMessageException fe){
			fe.printStackTrace();
			logger.error(fe.getMessage());
			throw fe;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantCheck;
	}
	
	@ApiOperation(value="법인 정보 조회", nickname="법인 정보 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantCheck.class)})
	@RequestMapping(value = URI_CORP_NO_CHECK, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantCheck getCorpNoCheck(RequestMerchantCheck requestMerchantCheck){
		ResponseMerchantCheck responseMerchantCheck = new ResponseMerchantCheck();
		try {
			if(requestMerchantCheck.getBizRegNo()==null || 
					   requestMerchantCheck.getMerchantId()==null ||
					   "".equals(requestMerchantCheck.getBizRegNo()) ||
					   "".equals(requestMerchantCheck.getMerchantId())
					   )
			{
				throw new FailureMessageException("Not Validated Parameter","Parameter 값이 올바르지 않습니다.[limit:"+requestMerchantCheck.getBizRegNo()+", offset:"+requestMerchantCheck.getMerchantId()+"]");
			}	
			int result = merchantService.getCorpNoCheck(requestMerchantCheck.getCorpRegNo(), requestMerchantCheck.getMerchantId());
			responseMerchantCheck.setCnt(result);
			
		}catch(FailureMessageException fe){
			fe.printStackTrace();
			logger.error(fe.getMessage());
			throw fe;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantCheck;
	}
	
	
	@ApiOperation(value="서비스 연동ID 조회", nickname="서비스 연동ID 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantCheck.class)})
	@RequestMapping(value = URI_SVC_CONN_ID_CHECK, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantCheck getSvcConnIdCheck(RequestServiceConnCheck requestServiceConnCheck){
		ResponseMerchantCheck responseMerchantCheck = new ResponseMerchantCheck();		
		try {
			
			if(requestServiceConnCheck.getSvcConnId()==null || 
					requestServiceConnCheck.getServiceId()==null ||
					   "".equals(requestServiceConnCheck.getSvcConnId()) ||
					   "".equals(requestServiceConnCheck.getServiceId())
					   )
			{
				throw new FailureMessageException("Not Validated Parameter","Parameter 값이 올바르지 않습니다.[limit:"+requestServiceConnCheck.getSvcConnId()+", offset:"+requestServiceConnCheck.getServiceId()+"]");
			}
			
			int result = merchantService.getSvcConnIdCheck(requestServiceConnCheck.getSvcConnId(), requestServiceConnCheck.getServiceId());
			responseMerchantCheck.setCnt(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantCheck;
	}
	
	@ApiOperation(value="거래처 정보 조회", nickname="거래처 정보 조회")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantGetMerchant.class)})
	@RequestMapping(value = URI_MERCHANT, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantGetMerchant getMerchant(		   
		   @RequestParam(value="merchantId", required=true) String merchantId
			){
		ResponseMerchantGetMerchant responseMerchantGetMerchant = new ResponseMerchantGetMerchant();
		try {
			responseMerchantGetMerchant = merchantService.getMerchant(merchantId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetMerchant;
	}
	/*
	@ApiOperation(value="거래처 정보 등록", nickname="거래처 정보 등록")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResponseMerchantGetMerchant.class)})
	@RequestMapping(value = URI_MERCHANT, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseMerchantAction postMerchant(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestSubMerchant requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			String result = merchantService.postMerchant(requestParam.getParentId(), 
														 requestParam.getName(),
														 requestParam.getAlias(),
														 requestParam.getCeoName(),
														 requestParam.getOpenDate(), 
														 requestParam.getBizRegNo(),
														 requestParam.getCorpRegNo(), 
														 requestParam.getBizKind(),
														 requestParam.getBizCond(),
														 requestParam.getZipcode(),
														 requestParam.getAddress(),
														 requestParam.getAddressDetail(),
														 requestParam.getBizGrp(),
														 requestParam.getTel(),
														 requestParam.getFax(),
														 requestParam.getTaxCustName(), 
														 requestParam.getTaxTel(),
														 requestParam.getTaxFax(),
														 requestParam.getTaxPhone(),
														 requestParam.getTaxEmail(),
														 requestParam.getBankNm(),
														 requestParam.getBankAccNo(),
														 requestParam.getBankHolder(),
														 requestParam.getSalesNm(),
														 requestParam.getSalesTel(), 
														 requestParam.getBillingNm(),
														 requestParam.getBillingTel(),
														 requestParam.getKpcSalesNm(),
														 requestParam.getKpcSalesTel(),
														 requestParam.getKpcBillingNm(),
														 requestParam.getKpcBillingTel(),
														 requestParam.getAgentId(), 
														 requestParam.getAgentPw(),
														 requestParam.getUseFlag(),
														 requestParam.getUrlHome());
			if("".equals(result))
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Name '"+requestParam.getName()+"' not saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Name '"+requestParam.getName()+"' saved.");
				responseMerchantAction.setData(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_MERCHANT, method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseMerchantAction putMerchant(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestSubMerchant requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			
			
			int result = merchantService.putMerchant(requestParam.getSubmerchantId(), 
													 requestParam.getParentId(), 
													 requestParam.getName(),
													 requestParam.getAlias(), 
													 requestParam.getCeoName(),
													 requestParam.getOpenDate(),
													 requestParam.getBizRegNo(), 
													 requestParam.getCorpRegNo(), 
													 requestParam.getBizKind(),
													 requestParam.getBizCond(),
													 requestParam.getZipcode(), 
													 requestParam.getAddress(),
													 requestParam.getAddressDetail(),
													 requestParam.getBizGrp(),
													 requestParam.getTel(),
													 requestParam.getFax(),
													 requestParam.getTaxCustName(), 
													 requestParam.getTaxTel(),
													 requestParam.getTaxFax(),
													 requestParam.getTaxPhone(),
													 requestParam.getTaxEmail(),
													 requestParam.getBankNm(), 
													 requestParam.getBankAccNo(),
													 requestParam.getBankHolder(),
													 requestParam.getSalesNm(),
													 requestParam.getSalesTel(), 
													 requestParam.getBillingNm(),
													 requestParam.getBillingTel(),
													 requestParam.getKpcSalesNm(),
													 requestParam.getKpcSalesTel(),
													 requestParam.getKpcBillingNm(),
													 requestParam.getKpcBillingTel(),
													 requestParam.getAgentId(), 
													 requestParam.getAgentPw(),
													 requestParam.getEncAgentPw(),
													 requestParam.getUseFlag(),
													 requestParam.getUrlHome());
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+requestParam.getName()+"' Not Deleted.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Name '"+requestParam.getName()+"' saved.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_MERCHANT, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody ResponseMerchantAction deleteMerchant(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="merchantId") String merchantId
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			int result = merchantService.deleteMerchant(merchantId);
			
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+merchantId+"' Not Deleted.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+merchantId+"' Deleted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
*/
	@RequestMapping(value = URI_REPRESENTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantGetRepresentMerchants getRepersentMerchants(
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="representId", required=true) String representId,
			@RequestParam(value="alias", required=true) String alias,
			@RequestParam(value="status", required=true) String status,
			@RequestParam(value="bizRegNo", required=true) String bizRegNo
			){
		ResponseMerchantGetRepresentMerchants responseMerchantGetRepresentMerchants = new ResponseMerchantGetRepresentMerchants();
		try {
			responseMerchantGetRepresentMerchants = merchantService.getRepresentMerchants(limit, offset, name, representId, alias, status, bizRegNo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetRepresentMerchants;
	}

	@RequestMapping(value = URI_REPRESENT, method = RequestMethod.GET, produces = "application/json")
	@Deprecated
	public @ResponseBody ResponseGetRepresentMerchant getRepersentMerchant(
			@RequestParam(value="representId", required=true) String representId
			){
		ResponseGetRepresentMerchant responseMerchantGetRepresentMerchant = new ResponseGetRepresentMerchant();
		try {
			responseMerchantGetRepresentMerchant = merchantService.getRepresentMerchant(representId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetRepresentMerchant;
	}
	
	@RequestMapping(value = URI_REPRESENT, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseMerchantAction postRepresent(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestRepresentMerchant form
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			if(!"".equals(form.getBizRegNo()))
			{
				int bizNoCount = merchantMapper.getBzNoCheck(form.getBizRegNo(), form.getMerchantId());
				if(bizNoCount > 0) 
				{
					response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
					responseMerchantAction.setMessage("사업자등록번호가 중복 입니다.");
					return responseMerchantAction;
				}
			}
			if(!"".equals(form.getCorpRegNo()))
			{
				int corpNoCount = merchantMapper.getCorpNoCheck(form.getCorpRegNo(), form.getMerchantId());
				if(corpNoCount > 0)
				{
					response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
					responseMerchantAction.setMessage("법인등록번호가 중복 입니다.");
					return responseMerchantAction;
				}	
			}
			
			String result = merchantService.postRepresentMerchant(form.getMerchantId(), form.getName(), form.getAlias(), form.getCeoName(), form.getOpenDate(), form.getBizRegNo(), form.getCorpRegNo(), form.getBizKind(), form.getBizCond(), form.getBizGrp(), form.getUseFlag(), form.getZipcode(), form.getAddress(), form.getAddressDetail(), form.getTel(), form.getFax(), form.getCreateDesc());
			
			if("".equals(result))
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setMessage("Merchant Name '" + form.getName() + "' not saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+form.getMerchantId()+"' Deleted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_REPRESENT, method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseMerchantAction putRepresent(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestPutPepresent param
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			logger.debug("Join Put ReresentMerchant : " + param.getMerchantId());
			
			if(!"".equals(param.getBizRegNo()))
			{
				int bizNoCount = merchantMapper.getBzNoCheck(param.getBizRegNo(), param.getMerchantId());
				if(bizNoCount > 0) 
				{
					response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
					responseMerchantAction.setMessage("사업자등록번호가 중복 입니다.");
					return responseMerchantAction;
				}
			}
			if(!"".equals(param.getCorpRegNo()))
			{
				int corpNoCount = merchantMapper.getCorpNoCheck(param.getCorpRegNo(), param.getMerchantId());
				if(corpNoCount > 0)
				{
					response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
					responseMerchantAction.setMessage("법인등록번호가 중복 입니다.");
					return responseMerchantAction;
				}	
			}
			
			int result = merchantService.putRepresentMerchant(param.getMerchantId(), param.getName(), param.getAlias(), param.getCeoName(), 
					param.getOpenDate(), param.getBizRegNo(), param.getCorpRegNo(), param.getBizKind(), param.getBizCond(), param.getBizGrp(), 
					param.getUseFlag(), param.getZipcode(), param.getAddress(), param.getAddressDetail(), param.getTel(), param.getFax(), 
					param.getUpdateDesc());
			
			if(result < 2)
			{
				logger.debug("result : " + result);
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+param.getMerchantId()+"' Saved.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_REPRESENT, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody ResponseMerchantAction deleteRepresentMerchant(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="merchantId") String merchantId
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			int result = merchantService.deleteRepresentMerchant(merchantId);
			
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+merchantId+"' Not Deleted.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant ID '"+merchantId+"' Deleted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}

	@RequestMapping(value = URI_SERVICES, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantServices getMerchantServices(
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="merchantId") String merchantId,
			@RequestParam(value="name") String name,
			@RequestParam(value="useFlag") String useFlag,
			@RequestParam(value="serviceId") String serviceId,
			@RequestParam(value="svcConnId") String svcConnId,
			@RequestParam(value="serviceType") String serviceType,
			@RequestParam(value="merchantName") String merchantName,
			@RequestParam(value="billingRegFlag") String billingRegFlag
			){
		ResponseMerchantServices responseMerchantServices = new ResponseMerchantServices();
		try {
			responseMerchantServices = merchantService.getMerchantServices(limit, offset, merchantId, name, useFlag, serviceId, svcConnId, serviceType, merchantName, billingRegFlag);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantServices;
	}
	
	@RequestMapping(value = URI_SERVICE, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantGetService getMerchantService(
			@RequestParam(value="serviceId", required=true) String serviceId
			){
		ResponseMerchantGetService responseMerchantGetService = new ResponseMerchantGetService();
		try {
			responseMerchantGetService = merchantService.getMerchantService(serviceId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantGetService;
	}
	
	@RequestMapping(value = URI_SERVICE, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseMerchantAction postMerchantService(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestMerchant requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			
			if("".equals(requestParam.getSubmerchantId()) ||
			   "".equals(requestParam.getServiceName()) ||
			   "".equals(requestParam.getCategory()) ||
			   "".equals(requestParam.getServiceType()) ||
			   "".equals(requestParam.getSaleDivider()) ||
			   "".equals(requestParam.getUseFlag()) ||
			   "".equals(requestParam.getSvcConnId()) ||					   
			   "".equals(requestParam.getSvcConnPw())
			   )
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setMessage("Merchant Service Name '" + requestParam.getServiceName() + "' not validated.");
				return responseMerchantAction;
			}
			
			
			String result = merchantService.postMerchantService(requestParam.getSubmerchantId(),
																requestParam.getServiceName(),
																requestParam.getCategory(),
																requestParam.getServiceType(),
																requestParam.getSaleDivider(),
																requestParam.getUseFlag(),
																requestParam.getSvcConnId(),
																requestParam.getSvcConnPw(),
																requestParam.getAgentId(),
																requestParam.getAgentPw(),
																requestParam.getCreateDesc(),
																requestParam.getCreateAdmId());
			
			if("".equals(result))
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setMessage("Merchant Service Name '" + requestParam.getServiceName() + "' not saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Service ID '"+result+"' Saved.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_SERVICE, method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseMerchantAction putMerchantService(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestMerchant requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			
			if(!"".equals(requestParam.getSvcConnId()))
			{
				int svcConnIdCount = merchantMapper.getSvcConnIdCheck(requestParam.getSvcConnId(), requestParam.getServiceId());
				if(svcConnIdCount > 0) 
				{
					response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
					responseMerchantAction.setMessage("연동아이디가 중복 입니다.");
					return responseMerchantAction;
				}
			}
			logger.info("requestParam.getServiceId()"+requestParam.getServiceId());
			int result = merchantService.putMerchantService(requestParam.getServiceId(), 
															requestParam.getSubmerchantId(), 
															requestParam.getName(),
															requestParam.getCategory(),
															requestParam.getServiceType(),
															requestParam.getUseFlag(),
															requestParam.getSaleDivider(),
															requestParam.getSvcConnId(),
															requestParam.getSvcConnPw(),
															requestParam.getAgentId(),
															requestParam.getAgentPw(),
															requestParam.getUpdateDesc(),
															requestParam.getUpdateAdmId()
															);
			
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("400");
				responseMerchantAction.setMessage("Merchant Service ID '"+requestParam.getServiceId()+"' not Saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Service ID '"+requestParam.getServiceId()+"' Saved.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	/*
	@RequestMapping(value = URI_SERVICE, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody ResponseMerchantAction deletetMerchantService(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="serviceId") String serviceId
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			int result = merchantService.deleteMerchantService(serviceId);
			
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Service ID '"+serviceId+"' Not Deleted.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Service ID '"+serviceId+"' Deleted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_BILLINGS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantBillings getMerchantBiilings(
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="billingCommType", defaultValue="") String billingCommType
			){
		ResponseMerchantBillings responseMerchantBillings = new ResponseMerchantBillings();
		try {
			responseMerchantBillings = merchantService.getMerchantBiilings(limit, offset, serviceId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantBillings;
	}
	
	@RequestMapping(value = URI_BILLING, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseMerchantBilling getMerchantBiiling(
			@RequestParam(value="serviceBillingId", required=true) String serviceBillingId,
			@RequestParam(value="billingCommType", required=true, defaultValue="") String billingCommType
			){
		ResponseMerchantBilling responseMerchantBilling = new ResponseMerchantBilling();
		try {
			responseMerchantBilling = merchantService.getMerchantBilling(serviceBillingId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantBilling;
	}

	@RequestMapping(value = URI_BILLING, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseMerchantAction postMerchantBiiling(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestBilling requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {			
			String result = merchantService.postMerchantBilling(requestParam.getServiceId(), 
																requestParam.getName(),
																requestParam.getBankNm(),
																requestParam.getBankAccNo(),
																requestParam.getBankHolder(),
																requestParam.getBillingNm(),
																requestParam.getBillingTel(),
																requestParam.getBillingEmail(),
																requestParam.getKpcBillingNm(),
																requestParam.getKpcBillingTel(), 
																requestParam.getKpcBillingEmail(), 
																requestParam.getDivider(), 
																requestParam.getCode(),
																requestParam.getBillingDuration(),
																requestParam.getBillingDt(), 
																requestParam.getMerchantCommType(),
																requestParam.getMerchantTaxType(),
																requestParam.getMerchantCommision(),
																requestParam.getCreateAdmId(),
																requestParam.getBillingCommType());
			
			if("".equals(result))
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setMessage("Merchant Name '" + requestParam.getBankNm() + "' not saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Billing ID '"+result+"' Inserted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_BILLING, method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseMerchantAction putMerchantBiiling(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody RequestBilling requestParam
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			
			int result = merchantService.putMerchantBilling(requestParam.getServiceBillingId(),
															requestParam.getServiceId(),
															requestParam.getName(),
															requestParam.getBankAccNo(),
															requestParam.getBankHolder(),															
															requestParam.getBillingNm(),
															requestParam.getBillingTel(),
															requestParam.getBillingEmail(),
															requestParam.getKpcBillingNm(), 
															requestParam.getKpcBillingTel(),															
															requestParam.getKpcBillingEmail(),
															requestParam.getDivider(),
															requestParam.getCode(),
															requestParam.getBillingDuration(),
															requestParam.getBillingDt(),
															requestParam.getBankNm(),
															requestParam.getMerchantCommType(),
															requestParam.getMerchantTaxType(),
															requestParam.getMerchantCommision(),
															requestParam.getAplEndDt(), 
															requestParam.getUpdateAdmId(),
															requestParam.getBillingCommType()
															);
			
			if(result < 2)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("400");
				responseMerchantAction.setMessage("Merchant Billing ID '"+requestParam.getServiceBillingId()+"' not Saved.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Billing ID '"+requestParam.getServiceBillingId()+"' Saved.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	
	@RequestMapping(value = URI_BILLING, method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody ResponseMerchantAction deletetMerchantBiiling(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="billingId") String billingId
			){
		ResponseMerchantAction responseMerchantAction = new ResponseMerchantAction();
		try {
			int result = merchantService.deleteRepresentMerchant(billingId);
			
			if(result < 1)
			{
				response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Billing ID '"+billingId+"' Deleted.");
			}
			else
			{
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				responseMerchantAction.setStatus("200");
				responseMerchantAction.setMessage("Merchant Billing ID '"+billingId+"' Deleted.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return responseMerchantAction;
	}
	*/
	/**
	 * 대표 거래처 상세 정보 조회
	 * @param merchantId 거래처 아이디
	 * @return
	 */
	@GetMapping(value = "/merchants//representative/{merchantId}")
	public ResponseEntity<ResponseMerchant> readMerchant(@PathVariable("merchantId")String merchantId){

		ResponseMerchant merchant = newMerchantService.readMerchantInfo(merchantId);
		
		return ResponseEntity.ok(merchant);
	}
	
	/**
	 * 일반 거래처 경로 조회
	 * @param subMerchantId
	 * @return
	 */
	@GetMapping("/sub-merchant/{subMerchantId}/path")
	public ResponseEntity<ResponseSubMerchantPath> readSubMerchantPath(@PathVariable("subMerchantId")String subMerchantId){

		ResponseSubMerchantPath path = newMerchantService.readSubMerchantPath(subMerchantId);
		
		return ResponseEntity.ok(path);
	}

	/**
	 * 서비스정산 수수료 이력 조회
	 * @param serviceBillingId
	 * @return
	 */
	@GetMapping("/sub-merchant/{serviceId}/billing/commision-histories")
	public ResponseEntity<List<ResponseSubMerchantBilling>> readSubMerchantCommisionHistories(@PathVariable("serviceId")String serviceId) {
		
		List<ResponseSubMerchantBilling> commisionHistories = newMerchantService.readSubmerchantCommisionHistories(serviceId);
		
		return ResponseEntity.ok(commisionHistories);
	}
	
	/**
	 * 서비스정산 정보 조회
	 * @param serviceBillingId
	 * @return
	 */
	@GetMapping("/sub-merchant/billing/{serviceBillingId}")
	public ResponseEntity<ResponseSubMerchantBilling> readSubMerchantServiceBilling(@PathVariable("serviceBillingId")String serviceBillingId) {
		
		ResponseSubMerchantBilling result = newMerchantService.readSubMerchantServiceBilling(serviceBillingId, "today");
		
		return ResponseEntity.ok(result);
		
	}

	/**
	 * 서비스의 searchType에 따른(last or last-second) 서비스정산 정보 조회
	 * @param serviceBillingId
	 * @return
	 */
	@GetMapping("/sub-merchant/billing/{serviceBillingId}/{searchType}")
	public ResponseEntity<ResponseSubMerchantBilling> readSubMerchantServiceLastBilling(@PathVariable("serviceBillingId")String serviceBillingId,
			@PathVariable("searchType")String searchType) {
		
		ResponseSubMerchantBilling result = newMerchantService.readSubMerchantServiceBilling(serviceBillingId, searchType);
		
		return ResponseEntity.ok(result);
		
	}

	/**
	 * 수수료Id로 서비스정산 정보 조회
	 * @param commisionId
	 * @return
	 */
	@GetMapping("/sub-merchant/billing/commision/{commisionId}")
	public ResponseEntity<ResponseSubMerchantBilling> readSubMerchantServiceBillingByCommisionId(@PathVariable("commisionId")Long commisionId) {
		
		ResponseSubMerchantBilling result = newMerchantService.readSubMerchantServiceBillingByCommisionId(commisionId);
		
		return ResponseEntity.ok(result);
		
	}
	
}
