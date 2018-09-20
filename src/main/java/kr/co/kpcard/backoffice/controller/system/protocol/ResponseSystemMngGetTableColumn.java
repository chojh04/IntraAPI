package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngTableColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetTableColumn {
	private String message;
	private String menuId;
	private String employeeId;
	private String tableId;
	private String descText;	
	
	public void setSystemMngTableColumn(String menuId, String employeeId, String tableId, String descText){
		this.menuId=menuId;
		this.employeeId=employeeId;
		this.tableId=tableId;
		this.descText=descText;				
	}
}
