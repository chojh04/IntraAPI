package kr.co.kpcard.backoffice.component.billing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingSpecificationSummary {
	private int count;
	private int offset;
	private int limit;
	public void setValues(int count, int offset, int limit) {
		this.count = count;
		this.offset = offset;
		this.limit = limit;
	}
	
}
