package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuUrl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetSubMenus {
	private String message;
	private List<SystemMngSubMenuUrl> resultList;	
}
