package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngMenu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetMenus {
	private List<SystemMngMenu> resultList;	
}
