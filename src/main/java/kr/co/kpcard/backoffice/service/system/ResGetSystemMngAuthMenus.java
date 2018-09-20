package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngAuthMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngCommonCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuAuth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetSystemMngAuthMenus {
	private List<SystemMngAuthMenu> systemMngAuthMenu; 
	private List<SystemMngSubMenuAuth> systemMngSubUrlAuth;
	private List<SystemMngCommonCode> systemMngCommonCode;
	
	public ResGetSystemMngAuthMenus(List<SystemMngAuthMenu> systemMngAuthMenu,
									List<SystemMngSubMenuAuth> systemMngSubUrlAuth,
									List<SystemMngCommonCode> systemMngCommonCode) {
		this.systemMngAuthMenu = systemMngAuthMenu;
		this.systemMngSubUrlAuth = systemMngSubUrlAuth;
		this.systemMngCommonCode = systemMngCommonCode;
	}
	

}
