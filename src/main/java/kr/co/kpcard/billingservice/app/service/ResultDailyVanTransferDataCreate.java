package kr.co.kpcard.billingservice.app.service;

public class ResultDailyVanTransferDataCreate {
	private String resultCode;
	private String resultMsg;
	private int successCount;
	private int failCount;
	private int totalCount;
	
	public ResultDailyVanTransferDataCreate(){
		successCount = 0;
		failCount = 0;
		totalCount = 0;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
}
