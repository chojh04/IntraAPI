package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;

public class GsRetailPayment {
	private String merchantId;
	private String workDate;
	private String branchCode;
	private String orderNumber;
	private	String admitNumber;
	private String admitDate;
	private String admitTime;
	private String barcodeNumber;
	private int orderAmount;
	private int payAmount;
	private String payType;
	private String currentDate;
	private Date insertDate;
	private String payOption1;
	private String payOption2;
	private	String payOption3;
	private String payOption4;
	private String payOption5;
	private String payOption6;
	private String payOption7;
	
	public GsRetailPayment(){}
		
	public GsRetailPayment(String merchantId, String workDate, String branchCode, String orderNumber,
			String admitNumber, String admitDate, String admitTime, String barcodeNumber, int orderAmount,
			int payAmount, String payType, String currentDate, Date insertDate, String payOption1, String payOption2,
			String payOption3, String payOption4, String payOption5, String payOption6, String payOption7) {
		super();
		this.merchantId = merchantId;
		this.workDate = workDate;
		this.branchCode = branchCode;
		this.orderNumber = orderNumber;
		this.admitNumber = admitNumber;
		this.admitDate = admitDate;
		this.admitTime = admitTime;
		this.barcodeNumber = barcodeNumber;
		this.orderAmount = orderAmount;
		this.payAmount = payAmount;
		this.payType = payType;
		this.currentDate = currentDate;
		this.insertDate = insertDate;
		this.payOption1 = payOption1;
		this.payOption2 = payOption2;
		this.payOption3 = payOption3;
		this.payOption4 = payOption4;
		this.payOption5 = payOption5;
		this.payOption6 = payOption6;
		this.payOption7 = payOption7;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getAdmitNumber() {
		return admitNumber;
	}
	public void setAdmitNumber(String admitNumber) {
		this.admitNumber = admitNumber;
	}
	public String getAdmitDate() {
		return admitDate;
	}
	public void setAdmitDate(String admitDate) {
		this.admitDate = admitDate;
	}
	public String getAdmitTime() {
		return admitTime;
	}
	public void setAdmitTime(String admitTime) {
		this.admitTime = admitTime;
	}
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public int getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getPayOption1() {
		return payOption1;
	}
	public void setPayOption1(String payOption1) {
		this.payOption1 = payOption1;
	}
	public String getPayOption2() {
		return payOption2;
	}
	public void setPayOption2(String payOption2) {
		this.payOption2 = payOption2;
	}
	public String getPayOption3() {
		return payOption3;
	}
	public void setPayOption3(String payOption3) {
		this.payOption3 = payOption3;
	}
	public String getPayOption4() {
		return payOption4;
	}
	public void setPayOption4(String payOption4) {
		this.payOption4 = payOption4;
	}
	public String getPayOption5() {
		return payOption5;
	}
	public void setPayOption5(String payOption5) {
		this.payOption5 = payOption5;
	}
	public String getPayOption6() {
		return payOption6;
	}
	public void setPayOption6(String payOption6) {
		this.payOption6 = payOption6;
	}
	public String getPayOption7() {
		return payOption7;
	}
	public void setPayOption7(String payOption7) {
		this.payOption7 = payOption7;
	}
	

}

