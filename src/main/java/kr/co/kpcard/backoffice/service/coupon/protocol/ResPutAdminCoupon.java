package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.Date;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;

public class ResPutAdminCoupon extends ResBody implements ResponseCode {
	private String couponNo;
	private String restrictFlag;
	private Date expireDate;
	
	public ResPutAdminCoupon()
	{
		super(RES_CODE_SERVER_ERROR, RES_MSG_SERVER_ERROR);
	}	
	public ResPutAdminCoupon(String code, String message) {
		super(code, message);
	}

	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getRestrictFlag() {
		return restrictFlag;
	}
	public void setRestrictFlag(String restrictFlag) {
		this.restrictFlag = restrictFlag;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
