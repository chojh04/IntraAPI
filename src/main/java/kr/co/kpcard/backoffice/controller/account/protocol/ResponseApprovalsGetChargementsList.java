package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountChargementResult;
import kr.co.kpcard.backoffice.component.account.AccountSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalsGetChargementsList {
	private AccountSummary summary;
	private List<AccountChargementResult> resultList;
	private long offset;
	private long limit;
}
