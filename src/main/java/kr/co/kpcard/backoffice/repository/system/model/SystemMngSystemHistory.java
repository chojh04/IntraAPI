package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngSystemHistory {
	private long rnum;
	private String menuId;
	private String typeCode;
	private String typeName;
	private String desc1;
	private String desc2;
	private String desc3;
	private String regId;
	private String regDt;
}
