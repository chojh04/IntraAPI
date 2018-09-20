package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngAuthMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngCommonCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuAuth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetAuthMenus {
	private String message;
	private List<SystemMngAuthMenu> resultHighMenuList; 
	private List<SystemMngSubMenuAuth> resultSubUrlList;
	private List<SystemMngCommonCode> resultCommCodeList;
}
