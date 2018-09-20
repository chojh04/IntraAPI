package kr.co.kpcard.billingservice.app;

import javax.servlet.http.HttpServletRequest;

import kr.co.kpcard.backoffice.component.ApiErrorResponse;
import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.component.GlobalException;
import kr.co.kpcard.backoffice.component.agent.AgentAuthBadRequestResponse;
import kr.co.kpcard.backoffice.component.agent.AgentAuthFailureMessageException;
import kr.co.kpcard.backoffice.component.agent.AgentFailureBadRequestResponse;
import kr.co.kpcard.backoffice.component.agent.AgentFailureMessageException;
import kr.co.kpcard.backoffice.component.system.LoginAuthBadRequestResponse;
import kr.co.kpcard.backoffice.component.system.LoginAuthFailureMessageException;
import kr.co.kpcard.backoffice.component.system.SystemMngColumnFailureMessageException;
import kr.co.kpcard.backoffice.component.system.SystemMngTableColumnRequestResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RestController
public class GlobalExceptionAdvice {
	
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

	 @ExceptionHandler(value = { GlobalException.class })
	 @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	 public ApiErrorResponse unknownException(HttpServletRequest req, Exception ex, GlobalException be) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : issueNo = " + be.getIssueNo());
		 logger.info("=====> BE : message = " + be.getMessage());
		 logger.info("=====> BE : customMsg = " + be.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("issueNo : " + be.getIssueNo() + ", msg : " + be.getMessage() + ", customMsg : " + be.getCustomMsg());
		 
	     return new ApiErrorResponse(""
	    		 				, ""
	    		 				+ String.format("[이슈번호 : %d, 담당자에게 해당 이슈에 대한 문의 부탁드립니다.| requestUrl : %s]"
	    		 				, be.getIssueNo(), req.getRequestURI()));
	 }
	 
	 @ExceptionHandler(value = { FailureMessageException.class })
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public ApiErrorResponse failureMessageException(HttpServletRequest req, Exception ex, FailureMessageException fme) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : message = " + fme.getMessage());
		 logger.info("=====> BE : customMsg = " + fme.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("msg : " + fme.getMessage() + ", customMsg : " + fme.getCustomMsg());
		 
	     return new ApiErrorResponse(""
	    		 				, ""
	    		 				+ String.format("해당 작업은 실패 하였습니다. 실패사유 :[%s]| requestUrl : %s]"
	    		 				, fme.getCustomMsg(), req.getRequestURI()));
	 }
	 
	 @ExceptionHandler(value = { AgentAuthFailureMessageException.class })
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public AgentAuthBadRequestResponse AgentAuthBadRequestException(HttpServletRequest req, Exception ex, AgentAuthFailureMessageException aafme) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : message = " + aafme.getMessage());
		 logger.info("=====> BE : customMsg = " + aafme.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("msg : " + aafme.getMessage() + ", customMsg : " + aafme.getCustomMsg());
		 logger.debug("sdgfksdjfhsdjkaghfkjsdhfjksdhfkjsbdhcfvusdjkcnusdjhbcfjhsdgfxjhsdngcfjbskgdxfnjsdngfjkasdgfj");
	     return new AgentAuthBadRequestResponse(aafme.isAuth(), aafme.getCustomMsg());
	 }
	 
	 @ExceptionHandler(value = { AgentFailureMessageException.class })
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public AgentFailureBadRequestResponse AgentFailureBadRequestException(HttpServletRequest req, Exception ex, AgentFailureMessageException afme) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : message = " + afme.getMessage());
		 logger.info("=====> BE : customMsg = " + afme.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("msg : " + afme.getMessage() + ", customMsg : " + afme.getCustomMsg());
		 
	     return new AgentFailureBadRequestResponse(afme.getCustomMsg());
	 }
	 
	 @ExceptionHandler(value = { LoginAuthFailureMessageException.class })
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public LoginAuthBadRequestResponse LoginAuthBadRequestException(HttpServletRequest req, Exception ex, LoginAuthFailureMessageException aafme) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : message = " + aafme.getMessage());
		 logger.info("=====> BE : customMsg = " + aafme.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("msg : " + aafme.getMessage() + ", customMsg : " + aafme.getCustomMsg());
	     return new LoginAuthBadRequestResponse(aafme.isAuth(), aafme.getCustomMsg());
	 }
	 
	 @ExceptionHandler(value = { SystemMngColumnFailureMessageException.class})
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public SystemMngTableColumnRequestResponse SystemMngTableColumnFailureMessageException(HttpServletRequest req, Exception ex, SystemMngColumnFailureMessageException aafme) 
	 {		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : customMsg = " + aafme.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("msg : " + aafme.getMessage() + ", customMsg : " + aafme.getCustomMsg());
	     return new SystemMngTableColumnRequestResponse(aafme.isStatus(), aafme.getCustomMsg());
	 }
	 
}
