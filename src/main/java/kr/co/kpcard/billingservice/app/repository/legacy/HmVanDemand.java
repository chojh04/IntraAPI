package kr.co.kpcard.billingservice.app.repository.legacy;

import java.util.Date;

public class HmVanDemand {
	private String workDate;
	private String demandType;
	private String demandDate;
	private String transDate;
	private String cardNumber;
	private String approvalNumber;
	private int payAmount;
	private int vat;
	private int serviceCharge;
	private String purchase;
	private String merchantNumber;
	private String terminalNumber;
	private String keyinSwipe;
	private String vanKind;
	private Date regDateTime;
	
	public HmVanDemand(){}
	
	public HmVanDemand(String workDate, String demandType, String demandDate, String transDate, String cardNumber,
			String approvalNumber, int payAmount, int vat, int serviceCharge, String purchase, String merchantNumber,
			String terminalNumber, String keyinSwipe, String vanKind, Date regDateTime) {
		super();
		this.workDate = workDate;
		this.demandType = demandType;
		this.demandDate = demandDate;
		this.transDate = transDate;
		this.cardNumber = cardNumber;
		this.approvalNumber = approvalNumber;
		this.payAmount = payAmount;
		this.vat = vat;
		this.serviceCharge = serviceCharge;
		this.purchase = purchase;
		this.merchantNumber = merchantNumber;
		this.terminalNumber = terminalNumber;
		this.keyinSwipe = keyinSwipe;
		this.vanKind = vanKind;
		this.regDateTime = regDateTime;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(String demandDate) {
		this.demandDate = demandDate;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public int getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getPurchase() {
		return purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public String getKeyinSwipe() {
		return keyinSwipe;
	}

	public void setKeyinSwipe(String keyinSwipe) {
		this.keyinSwipe = keyinSwipe;
	}

	public String getVanKind() {
		return vanKind;
	}

	public void setVanKind(String vanKind) {
		this.vanKind = vanKind;
	}

	public Date getRegDateTime() {
		return regDateTime;
	}

	public void setRegDateTime(Date regDateTime) {
		this.regDateTime = regDateTime;
	}
	
	
	
}

