package kr.co.kpcard.backoffice.repository.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSummaryModel {
	private long count;
	private long amountCount;
	private long cancelCount;
	private long refundCount;
	private long totalSum;
	private long amountSum;
	private long origAmountSum;
	private long dcAmountSum;
	private long cancelSum;
	private long origCancelSum;
	private long refundSum;
	private long billingSum;
	private long provideAmountSum;
}
