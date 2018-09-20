package kr.co.kpcard.backoffice.component.approval;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalApproval {
	private String empId;
	private String name;
	private String menuId;
	private String nameMenuName;
	public void setValues(String empId, String name, String menuId,
			String nameMenuName) {
		this.empId = empId;
		this.name = name;
		this.menuId = menuId;
		this.nameMenuName = nameMenuName;
	}
	
}
