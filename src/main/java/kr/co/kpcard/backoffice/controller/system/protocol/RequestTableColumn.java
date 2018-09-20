package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestTableColumn {
	private String menuId;
	private String empId;
	private String tableId;
	private String descText;
	private String createId;	
}
