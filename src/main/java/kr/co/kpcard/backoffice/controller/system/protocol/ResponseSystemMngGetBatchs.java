package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.BatchSummary;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetBatchs {
	private String message;
	private BatchSummary summary;
	private List<SystemMngBatch> resultList;	
}
