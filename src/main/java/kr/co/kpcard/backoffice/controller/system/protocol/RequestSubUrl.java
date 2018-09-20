package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSubUrl {
	private String urlId;
	private String name;
	private String parMenuId;
	private String url;
	private String createId;
	private String updateId;
}
