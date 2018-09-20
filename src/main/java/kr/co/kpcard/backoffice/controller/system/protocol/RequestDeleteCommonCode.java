package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDeleteCommonCode {
	private String typeCode;
	private String code;	
}
