package kr.co.kpcard.backoffice.controller.system.protocol;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestLogin {
	private String id;
	private String pwd;
	private String loginIp;	
}
