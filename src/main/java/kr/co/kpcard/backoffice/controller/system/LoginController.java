package kr.co.kpcard.backoffice.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kpcard.backoffice.component.system.LoginAuthBadRequestResponse;
import kr.co.kpcard.backoffice.component.system.LoginAuthFailureMessageException;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestLogin;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseLoginIsLoginable;
import kr.co.kpcard.backoffice.service.system.LoginService;
import kr.co.kpcard.backoffice.service.system.ResIsLoginable;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;

@RestController
public class LoginController  implements ResultCode{
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	private static final String LOGIN = "/login";
	
	@RequestMapping(value=LOGIN, method=RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseLoginIsLoginable isLoginable(RequestLogin Request)
	{
		ResponseLoginIsLoginable responseIsLoginable = new ResponseLoginIsLoginable();
		String returnMessage="";
		try{
			if("".equals(Request.getId()) || "".equals(Request.getPwd()) || "".equals(Request.getLoginIp()))
			{
				responseIsLoginable.setMessage("Not Validate Parameter requestValue['employeeId' : "+Request.getId()+", 'password' : "+Request.getPwd()+", 'loginIp' : "+Request.getLoginIp()+"]");
				return responseIsLoginable;
			}
			
			ResIsLoginable resIsLoginable = loginService.isLoginable(Request.getId(), Request.getPwd(), Request.getLoginIp());
			
			logger.info(resIsLoginable.getLoginStatus());
			
			if("LOGIN-0003".equals(resIsLoginable.getLoginStatus()))
			{
				returnMessage = "로그인오류가 5회 초과하였습니다.\n관리자에게 문의바랍니다.";
				throw new LoginAuthFailureMessageException(returnMessage, false, returnMessage);
			}
			else if("LOGIN-0002".equals(resIsLoginable.getLoginStatus()))
			{
				returnMessage = "아이디 또는 비밀번호를 확인하세요.\nR2에 등록되지 않은 아이디거나, 아이디 또는 비밀번호를\n입력하셨습니다.";
				throw new LoginAuthFailureMessageException(returnMessage, false, returnMessage);
				
			}else
			{
				responseIsLoginable.setLogin(resIsLoginable.getLogin().getEmpId(),
											 resIsLoginable.getLogin().getName(),
											 resIsLoginable.getLogin().getPosition(),
											 resIsLoginable.getLogin().getEmail(),
											 resIsLoginable.getLogin().getPhone()											 
											);
			}			
		}
		
		catch(LoginAuthFailureMessageException le)
		{			
			logger.error("Login fail Exception : "+le.getMessage());
			throw le;
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());		
			throw e;
		}
		return responseIsLoginable;
	}	
}
