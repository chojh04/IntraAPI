package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetBatch {
	private String message;
	private String seq;
	private String reqId;
	private String status;
	private String statusName;
	private String content;	
	private String errMsg;
	private String startDt;
	private String endDt;
	private String filePath;
	
	public void setSystemMngBatch(String seq, String reqId, String status, String statusName, String content,
			String errMsg, String startDt, String endDt, String filePath)
	{
		this.seq = seq;
		this.reqId = reqId;
		this.status = status;
		this.statusName = statusName;
		this.content = content;
		this.errMsg = errMsg;
		this.startDt = startDt;
		this.endDt = endDt;
		this.filePath = filePath;
	}
}
