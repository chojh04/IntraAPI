package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSystemHistory {
	private String menuId;
	private String typeCode;
	private String desc1;
	private String desc2;
	private String desc3;
	private String regId;
}
