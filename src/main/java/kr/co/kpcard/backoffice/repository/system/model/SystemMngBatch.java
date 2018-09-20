package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMngBatch {
	private long seq;
	private String reqId;
	private String status;
	private String statusName;
	private String content;	
	private String errMsg;
	private String startDt;
	private String endDt;
	private String filePath;
	
	public SystemMngBatch(){
		this.seq=0;
	}
}
