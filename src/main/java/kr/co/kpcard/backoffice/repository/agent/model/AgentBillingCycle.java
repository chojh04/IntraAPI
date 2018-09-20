package kr.co.kpcard.backoffice.repository.agent.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentBillingCycle {
	private String startdate;
	private String enddate;
	private String billingdate;
}
