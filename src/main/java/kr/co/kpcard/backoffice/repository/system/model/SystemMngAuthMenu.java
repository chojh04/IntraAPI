package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngAuthMenu{
	private String level;
	private String menuId;
	private String title;
	private String routerLink;
	private String parMenuId;
	private String insFlag;
	private String updFlag;
	private String delFlag;
	private String selFlag;
	private String apprFlag;	 
}
