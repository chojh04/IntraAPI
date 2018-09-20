package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;

public class VanTrans {
	private String idx;
	private String workDate;
	private String transType;
	private String transDate;
	private String transTime;
	private String origTransDate;
	private String cardNumber;
	private int approvalNumber;
	private int payAmount;
	private int vat;
	private String serviceCharge;
	private String issure;
	private String purchase;
	private String merchatNumber;
	private String terminalNumber;
	private String keyinSwipe;
	private String vanResCode;
	private String vanKind;
	private Date regDatetime;	
	
	public VanTrans(){}
	
	public VanTrans(String idx, String workDate, String transType, String transDate, String transTime,
			String origTransDate, String cardNumber, int approvalNumber, int payAmount, int vat, String serviceCharge,
			String issure, String purchase, String merchatNumber, String terminalNumber, String keyinSwipe, String vanResCode,
			String vanKind, Date regDatetime) {
		this.idx = idx;
		this.workDate = workDate;
		this.transType = transType;
		this.transDate = transDate;
		this.transTime = transTime;
		this.origTransDate = origTransDate;
		this.cardNumber = cardNumber;
		this.approvalNumber = approvalNumber;
		this.payAmount = payAmount;
		this.vat = vat;
		this.serviceCharge = serviceCharge;
		this.issure = issure;
		this.purchase = purchase;
		this.merchatNumber = merchatNumber;
		this.terminalNumber = terminalNumber;
		this.keyinSwipe = keyinSwipe;
		this.vanResCode = vanResCode;
		this.vanKind = vanKind;
		this.regDatetime = regDatetime;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getOrigTransDate() {
		return origTransDate;
	}
	public void setOrigTransDate(String origTransDate) {
		this.origTransDate = origTransDate;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(int approvalNumber) {
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
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getIssure() {
		return issure;
	}
	public void setIssure(String issure) {
		this.issure = issure;
	}
	public String getPurchase() {
		return purchase;
	}
	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}
	public String getMerchatNumber() {
		return merchatNumber;
	}
	public void setMerchatNumber(String merchatNumber) {
		this.merchatNumber = merchatNumber;
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
	public String getVanResCode() {
		return vanResCode;
	}
	public void setVanResCode(String vanResCode) {
		this.vanResCode = vanResCode;
	}
	public String getVanKind() {
		return vanKind;
	}
	public void setVanKind(String vanKind) {
		this.vanKind = vanKind;
	}
	public Date getRegDatetime() {
		return regDatetime;
	}
	public void setRegDatetime(Date regDatetime) {
		this.regDatetime = regDatetime;
	}
		
	
}

