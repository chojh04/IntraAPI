package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.BatchSummary;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetSystemMngBatch {
	private BatchSummary batchSummary;
	private List<SystemMngBatch> systemMngBatchs;
}
