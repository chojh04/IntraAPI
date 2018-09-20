package kr.co.kpcard.backoffice.controller.billing;

import java.net.HttpURLConnection;
import java.util.List;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.controller.billing.protocol.RequestGetBillings;
import kr.co.kpcard.backoffice.controller.billing.protocol.RequestGetRegBillings;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetBilling;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetBillings;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetRegBilling;
import kr.co.kpcard.backoffice.controller.billing.protocol.ResponseGetRegBillings;
import kr.co.kpcard.backoffice.service.billing.BillingService;
import kr.co.kpcard.backoffice.service.billing.ReqGetBillings;
import kr.co.kpcard.backoffice.service.billing.ResGetBilling;
import kr.co.kpcard.backoffice.service.billing.ResGetRegBilling;
import kr.co.kpcard.backoffice.service.billing.ResGetRegBillings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * @author chris
 * 정산명세서 조회 API
 */
@Controller
public class BillingController {
	private static Logger logger = LoggerFactory.getLogger(BillingController.class);
	
	@Autowired
	BillingService billingService;
	
	private static final String URI_BILLINGS = "/billings";
	private static final String URI_BILLING = "/billing";
	private static final String URI_REG_BILLINGS = "/regbillings";
	private static final String URI_REG_BILLING = "/regbilling";
	
	/**
	 * 정산명세서 조회
	 * @param offset
	 * @param limit
	 * @param merchantId
	 * @param serviceId
	 * @param serviceType
	 * @param dateType
	 * @param startDate
	 * @param endDate
	 * @param billingDuration
	 * @return 정산명세서 리스트
	 */
	@ApiOperation(value = "예비 정산명세서 List 조회", nickname = "예비 정산명세서 조회 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value = URI_BILLINGS, method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody ResponseEntity<ResponseGetBillings> getBillings(
			@ModelAttribute RequestGetBillings requestBody
			){
		ResponseGetBillings responseBillingGetSpecsBillings = new ResponseGetBillings();
		
		try{			
			logger.debug(requestBody.toString());
			ReqGetBillings reqGetBillings = new ReqGetBillings(requestBody);
			responseBillingGetSpecsBillings = billingService.getBillings(reqGetBillings);
		} catch(Exception e){
			logger.error("getServices {}" , e);
			return new ResponseEntity<ResponseGetBillings>(responseBillingGetSpecsBillings, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseGetBillings>(responseBillingGetSpecsBillings, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "예비 정산명세서 조회", nickname = "예비 정산명세서 조회 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value = URI_BILLING, method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody ResponseEntity<ResponseGetBilling> getBilling(@RequestParam(value="seq") String seq){
		ResponseGetBilling responseGetBilling = new ResponseGetBilling();
		
		try{			
			logger.info("getBilling RequestParam[seq : "+seq+"]");	
			
			if(seq==null || seq.equals("")){
				throw new FailureMessageException("Error", "Not Vaild RequestParam 'Seq'");
			}
			
			ResGetBilling resGetBilling = billingService.getBilling(seq);
			responseGetBilling.setResultList(resGetBilling.getBilling());
			
		}catch(FailureMessageException fe){
			throw fe;
		}catch(Exception e){		
			logger.error("getServices {}" , e);
			return new ResponseEntity<ResponseGetBilling>(responseGetBilling, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseGetBilling>(responseGetBilling, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "정산명세서 등록내역 리스트 조회", nickname = "정산명세서 등록내역 리스트 조회 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value = URI_REG_BILLINGS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseGetRegBillings getRegBillings(
			@ModelAttribute RequestGetRegBillings requestBody
			){
		ResponseGetRegBillings responseGetRegBillings = new ResponseGetRegBillings();
		
		try{			
			if(requestBody.getEndDate().equals("") || requestBody.getStartDate().equals("")){
				throw new FailureMessageException("Not Vaild param", "Not Vaild RequestParameter : [EndDate : "+requestBody.getEndDate()+", StartDate :"+requestBody.getEndDate()+"]");
			}else if(requestBody.getLimit()<1){
				throw new FailureMessageException("Not Vaild param", "Not Vaild RequestParameter : [limit : "+requestBody.getLimit()+"]");
			}
			ResGetRegBillings res = billingService.getRegBillings(requestBody);	
			responseGetRegBillings.setSummary(res.getSummary());
			responseGetRegBillings.setResultList(res.getResultList());
		}catch(FailureMessageException fe){
			throw fe;
		}
		catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseGetRegBillings;
	}
	
	
	@ApiOperation(value = "정산명세서 등록내역 조회", nickname = "정산명세서 등록내역 조회 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value = URI_REG_BILLING, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseGetRegBilling getRegBilling(
			@RequestParam(value="seq") String seq
			){
		ResponseGetRegBilling responseGetRegBilling = new ResponseGetRegBilling();
		
		try{			
			if(seq.equals(null) || seq.equals("")){
				throw new FailureMessageException("Not Vaild param", "Not Vaild RequestParameter : [seq : "+seq+"]");
			}
			ResGetRegBilling res = billingService.getRegBilling(seq);	
			responseGetRegBilling.setResultList(res.getResultList());
		}catch(FailureMessageException fe){
			throw fe;
		}
		catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseGetRegBilling;
	}
	
	
}
