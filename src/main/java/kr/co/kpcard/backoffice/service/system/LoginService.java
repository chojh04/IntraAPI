package kr.co.kpcard.backoffice.service.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.backoffice.repository.system.mapper.LoginMapper;
import kr.co.kpcard.backoffice.repository.system.model.Login;
import kr.co.kpcard.common.utils.EncodeString;

@Service
public class LoginService {
	
	Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	LoginMapper loginMapper;
	
	/**
	 * 로그인 가능 여부 확인
	 * @param employeeId
	 * @param password
	 * @param loginIp
	 * @return
	 */
	public ResIsLoginable isLoginable (String employeeId, String password, String loginIp)
	{
		ResIsLoginable resIsLoginable = new ResIsLoginable();
		String loginStatus = "LOGIN-0003";
		logger.info(employeeId);
		int loginFailCount = loginMapper.getLoginHstStatus(employeeId, "R2");
		logger.info("count"+loginFailCount);
		if(loginFailCount<5)
		{
			Login login = loginMapper.isLoginable(employeeId, EncodeString.encodeSha512(password));
			loginStatus = (login==null)? "LOGIN-0002" : "LOGIN-0001" ;
			
			loginMapper.loginHst("R2", employeeId, loginStatus, loginIp);
			
			resIsLoginable.setLoginStatus(loginStatus); 
			resIsLoginable.setLogin(login);
		}		
		else if(loginFailCount>=5)
		{
			resIsLoginable.setLoginStatus(loginStatus);			
		}		
		return resIsLoginable;
	}
}