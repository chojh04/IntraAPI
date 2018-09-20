package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngSubMenuUrl {
	private String urlId;
	private String parMenuId;
	private String parMenuName;
	private String name;
	private String url;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
}
