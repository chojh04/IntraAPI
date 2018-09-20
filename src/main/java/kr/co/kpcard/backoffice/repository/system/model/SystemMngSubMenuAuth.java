package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngSubMenuAuth {
	private String urlId;
	private String name;
	private String url;
	private String parMenuId;
	private String selFlag;
	private String insFlag;
	private String updFlag;
	private String delFlag;
	private String apprFlag;
}
