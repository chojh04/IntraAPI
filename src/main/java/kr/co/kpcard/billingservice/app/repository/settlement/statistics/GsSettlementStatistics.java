package kr.co.kpcard.billingservice.app.repository.settlement.statistics;

public class GsSettlementStatistics {
	
	private String jobDivider;
    private String workDt;
    private long   agreeAmt;
    private long   agreeCnt;
    private long   errorAmt;
    private long   errorCnt;
    private long   kpcOnlyAmt;
    private long   kpcOnlyCnt;
    private long   gsOnlyAmt;
    private long   gsOnlyCnt;
    private String compareResult;
	public String getJobDivider() {
		return jobDivider;
	}
	public void setJobDivider(String jobDivider) {
		this.jobDivider = jobDivider;
	}
	public String getWorkDt() {
		return workDt;
	}
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
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
	public String getCompareResult() {
		return compareResult;
	}
	public void setCompareResult(String compareResult) {
		this.compareResult = compareResult;
	}
    
    
}
