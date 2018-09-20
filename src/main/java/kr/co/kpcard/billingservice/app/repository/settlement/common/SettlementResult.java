package kr.co.kpcard.billingservice.app.repository.settlement.common;

public class SettlementResult {
	String seq;
	String jobDivider;
	String orderNo;
	String approvalNo;
	String svcconnId;
	String workDt;
	String settleSeq;
	String status;
	String statusDesc;
	String createDt;
	long   agreeAmt; 
	long   agreeCnt; 
	long   errorAmt; 
	long   errorCnt; 
	long   kpcOnlyAmt; 
	long   kpcOnlyCnt; 
	long   gsOnlyAmt; 
	long   gsOnlyCnt;   	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getJobDivider() {
		return jobDivider;
	}
	public void setJobDivider(String jobDivider) {
		this.jobDivider = jobDivider;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
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
	public String getSettleSeq() {
		return settleSeq;
	}
	public void setSettleSeq(String settleSeq) {
		this.settleSeq = settleSeq;
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
	public long getAgreeAmt() {
		return agreeAmt;
	}
	public void setAgreeAmt(long agreeAmt) {
		this.agreeAmt = agreeAmt;
	}
	public long getAgreeCnt() {
		return agreeCnt;
	}
	public void setAgreeCnt(long agreeCnt) {
		this.agreeCnt = agreeCnt;
	}
	public long getErrorAmt() {
		return errorAmt;
	}
	public void setErrorAmt(long errorAmt) {
		this.errorAmt = errorAmt;
	}
	public long getErrorCnt() {
		return errorCnt;
	}
	public void setErrorCnt(long errorCnt) {
		this.errorCnt = errorCnt;
	}
	public long getKpcOnlyAmt() {
		return kpcOnlyAmt;
	}
	public void setKpcOnlyAmt(long kpcOnlyAmt) {
		this.kpcOnlyAmt = kpcOnlyAmt;
	}
	public long getKpcOnlyCnt() {
		return kpcOnlyCnt;
	}
	public void setKpcOnlyCnt(long kpcOnlyCnt) {
		this.kpcOnlyCnt = kpcOnlyCnt;
	}
	public long getGsOnlyAmt() {
		return gsOnlyAmt;
	}
	public void setGsOnlyAmt(long gsOnlyAmt) {
		this.gsOnlyAmt = gsOnlyAmt;
	}
	public long getGsOnlyCnt() {
		return gsOnlyCnt;
	}
	public void setGsOnlyCnt(long gsOnlyCnt) {
		this.gsOnlyCnt = gsOnlyCnt;
	}
	
}
