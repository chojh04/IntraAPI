package kr.co.kpcard.billingservice.app.repository.settlement.gs;

public class GsRetailSettlement {
	
	private String workDt      ;
	private int    seq         ;
	private String divider     ;
	private String saleDt      ;
	private String storeCd     ;
	private String dealNo      ;
	private String approvalNo  ;
	private String approvalDt  ;
	private String approvalTime;
	private String cardNo      ;
	private long   dealAmt     ;
	private long   paymentAmt  ;
	private long   balance     ;
	private String dealDivider ;
	private String responseCd  ;
	private String createDt    ;
	 
	public String getWorkDt() {
		return workDt;
	}
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getDivider() {
		return divider;
	}
	public void setDivider(String divider) {
		this.divider = divider;
	}
	public String getSaleDt() {
		return saleDt;
	}
	public void setSaleDt(String saleDt) {
		this.saleDt = saleDt;
	}
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public String getApprovalDt() {
		return approvalDt;
	}
	public void setApprovalDt(String approvalDt) {
		this.approvalDt = approvalDt;
	}
	public String getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public long getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(long dealAmt) {
		this.dealAmt = dealAmt;
	}
	public long getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(long paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public String getDealDivider() {
		return dealDivider;
	}
	public void setDealDivider(String dealDivider) {
		this.dealDivider = dealDivider;
	}
	public String getResponseCd() {
		return responseCd;
	}
	public void setResponseCd(String responseCd) {
		this.responseCd = responseCd;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

}
