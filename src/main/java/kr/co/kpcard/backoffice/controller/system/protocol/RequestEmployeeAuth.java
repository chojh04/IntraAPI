package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmployeeAuth {
	private String empId;
	private String menuId;
	private String insFlag;
	private String updFlag;
	private String delFlag;
	private String selFlag;
	private String apprFlag;
	private String createId;
	private String updateId;
}