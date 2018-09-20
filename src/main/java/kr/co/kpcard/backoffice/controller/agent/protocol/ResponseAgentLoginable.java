package kr.co.kpcard.backoffice.controller.agent.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAgentLoginable {
	private String agentId;
	private String loginTime;
	private String submerchantId;
}
