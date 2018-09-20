package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.Date;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;

public class ResAdminCoupon extends ResBody implements ResponseCode {
	private String masterNo;
	private String couponType;
	private String title;
	private String amount;
	private String couponNo;
	private String status;
	private String restrictFlag;
	private String useFlag;
	private Date issueDate;
	private Date expireDate;
	private Date provideDate;
	private Date useDate;
	private Date cancelDate;
	private Date disuseDate;
	
	public String getMasterNo() {
		if(this.masterNo == null) this.masterNo = "";
		return masterNo;
	}
	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}
	public String getCouponType() {
		if(this.couponType == null) this.couponType = "";
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getTitle() {
		if(this.title == null) this.title = "";
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAmount() {
		if(this.amount == null) this.amount = "";
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCouponNo() {
		if(this.couponNo == null) this.couponNo = "";
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getStatus() {
		if(this.status == null) this.status = "";
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRestrictFlag() {
		if(this.restrictFlag == null) this.restrictFlag = "N";
		return restrictFlag;
	}
	public void setRestrictFlag(String restrictFlag) {
		this.restrictFlag = restrictFlag;
	}
	public String getUseFlag() {
		if(this.useFlag == null) this.useFlag = "N";
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Date getProvideDate() {
		return provideDate;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getDisuseDate() {
		return disuseDate;
	}
	public void setDisuseDate(Date disuseDate) {
		this.disuseDate = disuseDate;
	}
	
}
