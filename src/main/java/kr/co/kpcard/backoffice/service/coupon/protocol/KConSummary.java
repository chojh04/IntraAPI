package kr.co.kpcard.backoffice.service.coupon.protocol;

import lombok.Getter;

@Getter
public class KConSummary {
	private int listTotalCount;
	private int offset;
	private int limit;
	private long totalAmount;
	public void setValues(int listTotalCount, int offset, int limit,
			long totalAmount) {
		this.listTotalCount = listTotalCount;
		this.offset = offset;
		this.limit = limit;
		this.totalAmount = totalAmount;
	}
	
}
