package kr.co.kpcard.billingservice.app.repository.settlement.popcharge;

public class HappyCashSettlement {
	private String transDate;
	private String giftNo;
	private long   chargeAmt;
	private String aType;
	private String giftCheck;
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	public long getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getaType() {
		return aType;
	}
	public void setaType(String aType) {
		this.aType = aType;
	}
	public String getGiftCheck() {
		return giftCheck;
	}
	public void setGiftCheck(String giftCheck) {
		this.giftCheck = giftCheck;
	}
	@Override
	public String toString() {
		return "HappyCashSettlement [transDate=" + transDate + ", giftNo=" + giftNo + ", chargeAmt=" + chargeAmt
				+ ", aType=" + aType + ", giftCheck=" + giftCheck + "]";
	}
	
	
}
