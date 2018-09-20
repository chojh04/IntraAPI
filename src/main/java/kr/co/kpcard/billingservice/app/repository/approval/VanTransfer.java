package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;

public class VanTransfer {
	private String workDate;
	private String transferType;
	private String transferDate;
	private String transDate;
	private String demandDate;
	private String depositDate;
	private String returnDate;
	private String cardNumber;
	private String approvalNumber;
	private int payAmount;
	private int vat;
	private int serviceCharge;
	private String purchase;
	private String merchantNumber;
	private String cardReturnCode;
	private String vanReturnCode;
	private String vanKind;
	private Date regDateTime;
	
	public VanTransfer(){}
	
	public VanTransfer(String workDate, String transferType, String transferDate, String transDate, String demandDate,
			String depositDate, String returnDate, String cardNumber, String approvalNumber, int payAmount, int vat,
			int serviceCharge, String purchase, String merchantNumber, String cardReturnCode, String vanReturnCode,
			String vanKind, Date regDateTime) {
		super();
		this.workDate = workDate;
		this.transferType = transferType;
		this.transferDate = transferDate;
		this.transDate = transDate;
		this.demandDate = demandDate;
		this.depositDate = depositDate;
		this.returnDate = returnDate;
		this.cardNumber = cardNumber;
		this.approvalNumber = approvalNumber;
		this.payAmount = payAmount;
		this.vat = vat;
		this.serviceCharge = serviceCharge;
		this.purchase = purchase;
		this.merchantNumber = merchantNumber;
		this.cardReturnCode = cardReturnCode;
		this.vanReturnCode = vanReturnCode;
		this.vanKind = vanKind;
		this.regDateTime = regDateTime;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(String demandDate) {
		this.demandDate = demandDate;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
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

	public String getCardReturnCode() {
		return cardReturnCode;
	}

	public void setCardReturnCode(String cardReturnCode) {
		this.cardReturnCode = cardReturnCode;
	}

	public String getVanReturnCode() {
		return vanReturnCode;
	}

	public void setVanReturnCode(String vanReturnCode) {
		this.vanReturnCode = vanReturnCode;
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

