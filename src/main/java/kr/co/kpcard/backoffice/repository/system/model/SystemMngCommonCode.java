package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngCommonCode {
	private String typeCode;
	private String code;
	private String codeName;
	private String descText;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
}
