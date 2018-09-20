/**
 * 
 */
package kr.co.kpcard.backoffice.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseDailyBalance;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseTransaction;
import kr.co.kpcard.backoffice.service.account.BalanceService;

/**
 * 카드잔액관리 컨트롤러
 * @author chris
 *
 */
@Controller
public class BalanceController {
	private static Logger logger = LoggerFactory.getLogger(BalanceController.class);

	@Autowired
	private BalanceService balanceService;
	
    @ApiOperation(value = "데일리 잔액 내역을 조회한다.", nickname = "데일리 잔액 내역을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ResponseDailyBalance.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/balance/daily-balance-list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseDailyBalance> getDailyBalanceList(
    		HttpServletRequest request, HttpServletResponse response,
            @ApiParam(name = "startDate", value = "조회시작일자", defaultValue = "", required = true) @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @ApiParam(name = "endDate", value = "조회종료일자", defaultValue = "", required = true) @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @ApiParam(name = "orderBy", value = "조회일별(ASC, DESC)", allowableValues="ASC, DESC", defaultValue = "ASC", required = true) @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy,
            @ApiParam(name = "limit", value = "limit", defaultValue = "", required = true) @RequestParam(value = "limit", defaultValue = "10") int limit,
            @ApiParam(name = "offset", value = "offset", defaultValue = "", required = true) @RequestParam(value = "offset", defaultValue = "0") int offset
			) {
    	ResponseDailyBalance responseDailyBalance = null;

    	logger.debug("Request daily balance list : startDate = {}, endDate = {}, orderBy = {}, limit = {}, offset = {}", startDate, endDate, orderBy, offset, limit);
    	
    	try {
	    	responseDailyBalance = balanceService.getDailyBalance(startDate, endDate, orderBy, offset, limit);
	    	logger.debug("Response daily balance list : totalCount = {}, count = {}", responseDailyBalance.getSummary().getCount(), responseDailyBalance.getResultList().size());
    	} catch ( Exception ex ) {
    		logger.error(ex.getMessage());
			return new ResponseEntity<>(responseDailyBalance, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
		return ResponseEntity.ok(responseDailyBalance);
	}

    @ApiOperation(value = "거래별 요약조회", nickname = "거래별 요약정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ResponseTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/balance/transaction-summary", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseTransaction> getTransactionSummary(
    		HttpServletRequest request, HttpServletResponse response,
            @ApiParam(name = "startDate", value = "조회시작일자", defaultValue = "", required = true) @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @ApiParam(name = "endDate", value = "조회종료일자", defaultValue = "", required = true) @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @ApiParam(name = "transactionType", value = "거래구분(TRNT-0001: 충전, TRNT-0002: 결제, TRNT-0003: 환불)", allowMultiple = true, allowableValues="TRNT-0001, TRNT-0002, TRNT-0003", defaultValue = "TRNT-0001", required = true) @RequestParam(value = "transactionType", defaultValue = "") String transactionType,
            @ApiParam(name = "limit", value = "limit", defaultValue = "", required = true) @RequestParam(value = "limit", defaultValue = "10") int limit,
            @ApiParam(name = "offset", value = "offset", defaultValue = "", required = true) @RequestParam(value = "offset", defaultValue = "0") int offset
			) {
    	ResponseTransaction responseTransaction = null;

    	logger.debug("Request daily balance list : startDate = {}, endDate = {}, transactionType = {}, limit = {}, offset = {}", startDate, endDate, transactionType, offset, limit);
    	
    	try {
    		responseTransaction = balanceService.getTransactionSummary(startDate, endDate, transactionType, offset, limit);
	    	logger.debug("Response transaction summary : transactionType = {}, totalCount = {}, listCount = {}", transactionType, responseTransaction.getSummary().getCount(), responseTransaction.getResultList().size());
    	} catch ( Exception ex ) {
    		logger.error(ex.getMessage());
			return new ResponseEntity<>(responseTransaction, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
		return ResponseEntity.ok(responseTransaction);
	}
}
