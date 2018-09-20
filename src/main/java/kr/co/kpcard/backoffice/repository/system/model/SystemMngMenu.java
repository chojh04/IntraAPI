package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngMenu {
	private String menuId;
	private String name;
	private String parMenuId; 
	private String parMenuName;
	private String menuUrl;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
}
