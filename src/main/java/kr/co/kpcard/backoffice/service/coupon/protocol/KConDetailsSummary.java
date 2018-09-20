package kr.co.kpcard.backoffice.service.coupon.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KConDetailsSummary {
	private int listTotalCount;
	private int offset;
	private int limit;
	
	public KConDetailsSummary(){}

	public KConDetailsSummary(int listTotalCount, int offset, int limit) {
		super();
		this.listTotalCount = listTotalCount;
		this.offset = offset;
		this.limit = limit;
	}
}
