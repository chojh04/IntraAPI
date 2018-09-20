package kr.co.kpcard.billingservice.app.repository.settlement.popcard;

public class PopCardDealsSettlement {
	private String workDt;
	private long   seq;
	private String dealDate; 
	private String orderNo;
	private String approvalNo;
	private String dealDivider;
	private String cardNo;
	private String useDate;
	private String amount;
	private String balance;
	private String merchantCode;
	private String merchantName;
	private String onOffLineDivier;
	private String cardDivider;
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
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
	public String getDealDivider() {
		return dealDivider;
	}
	public void setDealDivider(String dealDivider) {
		this.dealDivider = dealDivider;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOnOffLineDivier() {
		return onOffLineDivier;
	}
	public void setOnOffLineDivier(String onOffLineDivier) {
		this.onOffLineDivier = onOffLineDivier;
	}
	public String getCardDivider() {
		return cardDivider;
	}
	public void setCardDivider(String cardDivider) {
		this.cardDivider = cardDivider;
	}
	public String getWorkDt() {
		return workDt;
	}
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "PopCardSettlement [workDt=" + workDt + ", seq=" + seq + ", dealDate=" + dealDate + ", orderNo="
				+ orderNo + ", approvalNo=" + approvalNo + ", dealDivider=" + dealDivider + ", cardNo=" + cardNo
				+ ", useDate=" + useDate + ", amount=" + amount + ", balance=" + balance + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName + ", onOffLineDivier=" + onOffLineDivier
				+ ", cardDivider=" + cardDivider + "]";
	}
	
}
