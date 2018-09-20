package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestMngBatch {
	private long batchId;
	private String reqId;
	private String status;
	private String tableId;
	private String filePath;
	private String content;
	private String errMsg;
}
