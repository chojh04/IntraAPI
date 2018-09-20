package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBatch {
	private String message;
	private long batchId;
}
