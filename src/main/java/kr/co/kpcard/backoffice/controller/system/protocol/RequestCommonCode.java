package kr.co.kpcard.backoffice.controller.system.protocol;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCommonCode {
	private String typeCode;
	private String code;
	private String codeName;
	private String descText;
	private String createId;		
}
