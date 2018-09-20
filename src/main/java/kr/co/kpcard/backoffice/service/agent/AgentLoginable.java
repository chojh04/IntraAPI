package kr.co.kpcard.backoffice.service.agent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentLoginable {
	private String agentId;
	private String loginTime;
	private String submerchant;
	private String loginStatus;
	public String toStringForLog() {
		return "{[\"agentId\":\"" + agentId + "\", loginTime\":\"" + loginTime
				+ "\", submerchant\":\"" + submerchant + "\", loginStatus\":\""
				+ loginStatus + "]}}";
	}
	
}
