package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountPaymentResult;
import kr.co.kpcard.backoffice.component.account.AccountSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalsGetPaymentsList {
	private AccountSummary summary;
	private List<AccountPaymentResult> resultList;
	private long offset;
	private long limit;
}
