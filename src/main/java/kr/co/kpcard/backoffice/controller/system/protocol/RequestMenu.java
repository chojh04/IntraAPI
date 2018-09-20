package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestMenu {
	private String menuId;
	private String name;
	private String parMenuId;
	private String menuUrl;
	private String createId;
	private String updateId;
}
