package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 사용자 권한 모델 
 *
 */
public class EmployeeAuth {
	private String name;
	private String employeeMenuId;
	private String employeeId;	
	private String menuId;
	private String insFlag;
	private String updFlag;
	private String delFlag;
	private String selFlag;
	private String apprFlag;
	private String createId;
	private String updateId;

	public EmployeeAuth() { }
	
	//appr_flag column realDB 추가시 사용
	public EmployeeAuth(String employeeId, String menuId, String insFlag,
			String updFlag, String delFlag, String selFlag, String apprFlag, String createId, String updateId) {		
		this.employeeId = employeeId;		
		this.menuId = menuId;
		this.insFlag = insFlag;
		this.updFlag = updFlag;
		this.delFlag = delFlag;
		this.selFlag = selFlag;
		this.apprFlag = apprFlag;
		this.createId = createId;
		this.updateId = updateId;
	}
}

