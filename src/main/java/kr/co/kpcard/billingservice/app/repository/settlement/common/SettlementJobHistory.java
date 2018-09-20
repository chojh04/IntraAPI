package kr.co.kpcard.billingservice.app.repository.settlement.common;

public class SettlementJobHistory{
	private int    seq       ; 
	private String svcconnId ;
	private String workDt    ;
	private String status    ;
	private String statusDesc;
	private String createDt  ;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSvcconnId() {
		return svcconnId;
	}
	public void setSvcconnId(String svcconnId) {
		this.svcconnId = svcconnId;
	}
	public String getWorkDt() {
		return workDt;
	}
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	
	
}
