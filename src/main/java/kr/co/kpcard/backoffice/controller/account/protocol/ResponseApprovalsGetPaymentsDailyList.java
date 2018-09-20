package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountApprovalSummResult;
import kr.co.kpcard.backoffice.component.account.AccountSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalsGetPaymentsDailyList {
	private AccountSummary summary;
	private List<AccountApprovalSummResult> resultList;
	private long offset;
	private long limit;
}
