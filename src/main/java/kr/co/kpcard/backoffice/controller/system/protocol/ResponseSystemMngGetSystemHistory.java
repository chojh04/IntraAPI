package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistory;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistorySummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetSystemHistory {
	private SystemMngSystemHistorySummary summary;
	private List<SystemMngSystemHistory> resultList;	
}
