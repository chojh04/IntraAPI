package kr.co.kpcard.backoffice.component.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSummary {
	private long count;
	private long amountCount;
	private long refundCount;
	private long cancelCount;
	private long totalSum;
	private long amountSum;
	private long origAmountSum;
	private long dcAmountSum;
	private long cancelSum;
	private long origCancelSum;
	private long refundSum;
	private long billingSum;
	private long customerProvideSum;
	public void setValues(long count, 
						  long amountCount,
						  long refundCount,
						  long cancelCount,
						  long totalSum,
						  long amountSum,
						  long origAmountSum,
						  long dcAmountSum,
						  long cancelSum,
						  long origCancelSum,
						  long refundSum,
						  long billingSum,
						  long customerProvideSum) {
		this.count = count;
		this.amountCount = amountCount;
		this.refundCount = refundCount;
		this.cancelCount = cancelCount;
		this.totalSum = totalSum;
		this.amountSum = amountSum;
		this.origAmountSum = origAmountSum;
		this.dcAmountSum = dcAmountSum;
		this.cancelSum = cancelSum;
		this.origCancelSum = origCancelSum;
		this.refundSum = refundSum;
		this.billingSum = billingSum;
		this.customerProvideSum = customerProvideSum;
	}
	
}
