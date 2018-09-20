package kr.co.kpcard.backoffice.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kpcard.backoffice.component.account.AccountParam;
import kr.co.kpcard.backoffice.component.account.AccountSalementParam;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsDailyList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetChargementsMonthList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsDailyList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetPaymentsMonthList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetSalementsList;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseApprovalsGetSalementsMonthList;
import kr.co.kpcard.backoffice.service.account.AccountService;

@Controller
public class AccountController {
	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	private static final String URI_APPROVALS_PAYMENTS = "/approvals/payments";
	private static final String URI_APPROVALS_PAYMENTS_DAILY = "/approvals/paymentsDaily";
	private static final String URI_APPROVALS_PAYMENTS_MONTH = "/approvals/paymentsMonth";
	private static final String URI_APPROVALS_CHARGEMENTS = "/approvals/chargements";
	private static final String URI_APPROVALS_CHARGEMENTS_DAILY = "/approvals/chargementsDaily";
	private static final String URI_APPROVALS_CHARGEMENTS_MONTH = "/approvals/chargementsMonth";
	private static final String URI_APPROVALS_SALEMENTS = "/approvals/salements";
	private static final String URI_APPROVALS_SALEMENTS_MONTH = "/approvals/salementsMonth";
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value = URI_APPROVALS_PAYMENTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetPaymentsList getPayments(
			@RequestParam(value="merchantId") String merchantId,
			@RequestParam(value="serviceId") String serviceId,
			@RequestParam(value="approvalStatus") String approvalStatus,
			@RequestParam(value="orderNo") String orderNo,
			@RequestParam(value="approvalNo") String approvalNo,
			@RequestParam(value="dateType") String dateType,
			@RequestParam(value="svcConnId") String svcConnId,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm") String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="sort") String sort,
			@RequestParam(value="clause") String clause,
			@RequestParam(value="payMethod") String payMethod,
			@RequestParam(value="saleDivider") String saleDivider,
			@RequestParam(value="cardType") String cardType,
			@RequestParam(value="excelAllFlag") String excelAllFlag
			){
		ResponseApprovalsGetPaymentsList responseApprovalsGetPaymentsList = new ResponseApprovalsGetPaymentsList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetPaymentsList = accountService.getPayments(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetPaymentsList;
	}
	
	@RequestMapping(value = URI_APPROVALS_PAYMENTS_DAILY, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetPaymentsDailyList getPaymentsDaily(
			@RequestParam(value="merchantId", defaultValue="") String merchantId,
			@RequestParam(value="serviceId", defaultValue="") String serviceId,
			@RequestParam(value="approvalStatus", defaultValue="") String approvalStatus,
			@RequestParam(value="orderNo", defaultValue="") String orderNo,
			@RequestParam(value="approvalNo", defaultValue="") String approvalNo,
			@RequestParam(value="dateType", required=true) String dateType,
			@RequestParam(value="svcConnId", defaultValue="") String svcConnId,
			@RequestParam(value="startDate", required=true, defaultValue="") String startDate,
			@RequestParam(value="endDate", required=true, defaultValue="") String endDate,
			@RequestParam(value="merchantNm", required=true, defaultValue="") String merchantNm,
			@RequestParam(value="serviceNm", defaultValue="") String serviceNm,
			@RequestParam(value="offset", required=true, defaultValue="") int offset,
			@RequestParam(value="limit", required=true, defaultValue="") int limit,
			@RequestParam(value="sort", required=true, defaultValue="") String sort,
			@RequestParam(value="clause", defaultValue="") String clause,
			@RequestParam(value="payMethod", defaultValue="") String payMethod,
			@RequestParam(value="saleDivider", defaultValue="") String saleDivider,
			@RequestParam(value="cardType", defaultValue="") String cardType,
			@RequestParam(value="excelAllFlag", defaultValue="") String excelAllFlag
			){
		ResponseApprovalsGetPaymentsDailyList responseApprovalsGetPaymentsDailyList = new ResponseApprovalsGetPaymentsDailyList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetPaymentsDailyList = accountService.getPaymentsDaily(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetPaymentsDailyList;
	}
	
	@RequestMapping(value = URI_APPROVALS_PAYMENTS_MONTH, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetPaymentsMonthList getPaymentsMonth(
			@RequestParam(value="merchantId", required=true, defaultValue="") String merchantId,
			@RequestParam(value="serviceId", required=true, defaultValue="") String serviceId,
			@RequestParam(value="approvalStatus", required=true, defaultValue="") String approvalStatus,
			@RequestParam(value="orderNo", required=true, defaultValue="") String orderNo,
			@RequestParam(value="approvalNo", required=true, defaultValue="") String approvalNo,
			@RequestParam(value="dateType", required=true, defaultValue="") String dateType,
			@RequestParam(value="svcConnId", required=true, defaultValue="") String svcConnId,
			@RequestParam(value="startDate", required=true, defaultValue="") String startDate,
			@RequestParam(value="endDate", required=true, defaultValue="") String endDate,
			@RequestParam(value="merchantNm", required=true, defaultValue="") String merchantNm,
			@RequestParam(value="serviceNm", required=true, defaultValue="") String serviceNm,
			@RequestParam(value="offset", required=true, defaultValue="") int offset,
			@RequestParam(value="limit", required=true, defaultValue="") int limit,
			@RequestParam(value="sort", required=true, defaultValue="") String sort,
			@RequestParam(value="clause", required=true, defaultValue="") String clause,
			@RequestParam(value="payMethod", required=true, defaultValue="") String payMethod,
			@RequestParam(value="saleDivider", required=true, defaultValue="") String saleDivider,
			@RequestParam(value="cardType", required=true, defaultValue="") String cardType,
			@RequestParam(value="excelAllFlag", required=true, defaultValue="") String excelAllFlag
			){
		ResponseApprovalsGetPaymentsMonthList responseApprovalsGetPaymentsMonthList = new ResponseApprovalsGetPaymentsMonthList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetPaymentsMonthList = accountService.getPaymentsMonth(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetPaymentsMonthList;
	}
	
	@RequestMapping(value = URI_APPROVALS_CHARGEMENTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetChargementsList getChargements(
			@RequestParam(value="merchantId", required=true) String merchantId,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="approvalStatus", required=true) String approvalStatus,
			@RequestParam(value="orderNo", required=true) String orderNo,
			@RequestParam(value="approvalNo", required=true) String approvalNo,
			@RequestParam(value="dateType", required=true) String dateType,
			@RequestParam(value="svcConnId", required=true) String svcConnId,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm", required=true) String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="sort", required=true) String sort,
			@RequestParam(value="clause", required=true) String clause,
			@RequestParam(value="payMethod", required=true) String payMethod,
			@RequestParam(value="saleDivider", required=true) String saleDivider,
			@RequestParam(value="cardType", required=true) String cardType,
			@RequestParam(value="excelAllFlag", required=true) String excelAllFlag
			){
		ResponseApprovalsGetChargementsList responseApprovalsGetChargementsList = new ResponseApprovalsGetChargementsList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetChargementsList = accountService.getChargements(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetChargementsList;
	}
	
	@RequestMapping(value = URI_APPROVALS_CHARGEMENTS_DAILY, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetChargementsDailyList getChargementsDaily(
			@RequestParam(value="merchantId", required=true) String merchantId,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="approvalStatus", required=true) String approvalStatus,
			@RequestParam(value="orderNo", required=true) String orderNo,
			@RequestParam(value="approvalNo", required=true) String approvalNo,
			@RequestParam(value="dateType", required=true) String dateType,
			@RequestParam(value="svcConnId", required=true) String svcConnId,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm", required=true) String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="sort", required=true) String sort,
			@RequestParam(value="clause", required=true) String clause,
			@RequestParam(value="payMethod", required=true) String payMethod,
			@RequestParam(value="saleDivider", required=true) String saleDivider,
			@RequestParam(value="cardType", required=true) String cardType,
			@RequestParam(value="excelAllFlag", required=true) String excelAllFlag
			){
		ResponseApprovalsGetChargementsDailyList responseApprovalsGetChargementsDailyList = new ResponseApprovalsGetChargementsDailyList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetChargementsDailyList = accountService.getChargementsDaily(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetChargementsDailyList;
	}
	
	@RequestMapping(value = URI_APPROVALS_CHARGEMENTS_MONTH, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetChargementsMonthList getChargementsMonth(
			@RequestParam(value="merchantId", required=true) String merchantId,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="approvalStatus", required=true) String approvalStatus,
			@RequestParam(value="orderNo", required=true) String orderNo,
			@RequestParam(value="approvalNo", required=true) String approvalNo,
			@RequestParam(value="dateType", required=true) String dateType,
			@RequestParam(value="svcConnId", required=true) String svcConnId,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm", required=true) String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="sort", required=true) String sort,
			@RequestParam(value="clause", required=true) String clause,
			@RequestParam(value="payMethod", required=true) String payMethod,
			@RequestParam(value="saleDivider", required=true) String saleDivider,
			@RequestParam(value="cardType", required=true) String cardType,
			@RequestParam(value="excelAllFlag", required=true) String excelAllFlag
			){
		ResponseApprovalsGetChargementsMonthList responseApprovalsGetChargementsMonthList = new ResponseApprovalsGetChargementsMonthList();
		
		try{
			AccountParam param = new AccountParam();
			param.setValues(merchantId, serviceId, approvalStatus, orderNo, approvalNo, dateType, svcConnId, startDate, endDate, merchantNm, serviceNm, offset, limit, sort, clause, payMethod, saleDivider, cardType, excelAllFlag);
			responseApprovalsGetChargementsMonthList = accountService.getChargementsMonth(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetChargementsMonthList;
	}
	
	
	@RequestMapping(value = URI_APPROVALS_SALEMENTS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetSalementsList getSalements(
			@RequestParam(value="merchantId", required=true) String merchantId,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="approvalStatus", required=true) String approvalStatus,
			@RequestParam(value="saleMethod", required=true) String saleMethod,
			@RequestParam(value="prodType", required=true) String prodType,
			@RequestParam(value="orderNo", required=true) String orderNo,
			@RequestParam(value="approvalNo", required=true) String approvalNo,
			@RequestParam(value="svcConnId", required=true) String svcConnId,
			@RequestParam(value="amount", required=true) String amount,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm", required=true) String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="excelAllFlag", required=true) String excelAllFlag
			){
		ResponseApprovalsGetSalementsList responseApprovalsGetSalementsList = new ResponseApprovalsGetSalementsList();
		
		try{
			AccountSalementParam param = new AccountSalementParam();
			param.setValues(merchantId, serviceId, approvalStatus, saleMethod, prodType, orderNo, approvalNo, svcConnId, amount, startDate, endDate, merchantNm, serviceNm, offset, limit, excelAllFlag);
			responseApprovalsGetSalementsList = accountService.getSalements(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetSalementsList;
	}
	
	@RequestMapping(value = URI_APPROVALS_SALEMENTS_MONTH, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseApprovalsGetSalementsMonthList getSalementsMonth(
			@RequestParam(value="merchantId", required=true) String merchantId,
			@RequestParam(value="serviceId", required=true) String serviceId,
			@RequestParam(value="approvalStatus", required=true) String approvalStatus,
			@RequestParam(value="saleMethod", required=true) String saleMethod,
			@RequestParam(value="prodType", required=true) String prodType,
			@RequestParam(value="orderNo", required=true) String orderNo,
			@RequestParam(value="approvalNo", required=true) String approvalNo,
			@RequestParam(value="svcConnId", required=true) String svcConnId,
			@RequestParam(value="amount", required=true) String amount,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="merchantNm", required=true) String merchantNm,
			@RequestParam(value="serviceNm", required=true) String serviceNm,
			@RequestParam(value="offset", required=true) int offset,
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="excelAllFlag", required=true) String excelAllFlag
			){
		ResponseApprovalsGetSalementsMonthList responseApprovalsGetSalementsMonthList = new ResponseApprovalsGetSalementsMonthList();
		
		try{
			AccountSalementParam param = new AccountSalementParam();
			param.setValues(merchantId, serviceId, approvalStatus, saleMethod, prodType, orderNo, approvalNo, svcConnId, amount, startDate, endDate, merchantNm, serviceNm, offset, limit, excelAllFlag);
			responseApprovalsGetSalementsMonthList = accountService.getSalementsMonth(param);
		} catch(Exception e){
			logger.error("getServices {}" , e);
		}
		return responseApprovalsGetSalementsMonthList;
	}
}
