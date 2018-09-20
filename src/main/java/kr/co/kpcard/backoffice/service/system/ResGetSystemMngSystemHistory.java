package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistory;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistorySummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetSystemMngSystemHistory {
	private SystemMngSystemHistorySummary systemMngSystemHistorySummary;
	private List<SystemMngSystemHistory> systemMngSystemHistoryList;
}
