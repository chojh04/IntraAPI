package kr.co.kpcard.backoffice.repository.agent.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentLoginInfo {
	private String agent_id;
	private String loginTime;
	private String submerchant_id;
}
