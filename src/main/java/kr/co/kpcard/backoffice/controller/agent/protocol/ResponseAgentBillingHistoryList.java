package kr.co.kpcard.backoffice.controller.agent.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.agent.AgentBillingHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAgentBillingHistoryList {
	private List<AgentBillingHistory> resultList;
}
