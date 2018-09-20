package kr.co.kpcard.backoffice.repository.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountChargementsSummary {
	private long count;
	private long amountCount;
	private long cancelCount;
	private long refundCount;
	private long totalSum;
	private long amountSum;
	private long cancelSum;
	private long refundSum;
	private long billingSum;
}
