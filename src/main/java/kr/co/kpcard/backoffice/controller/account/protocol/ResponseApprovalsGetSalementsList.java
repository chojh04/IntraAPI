package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountSalementResult;
import kr.co.kpcard.backoffice.component.account.AccountSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalsGetSalementsList {
	private AccountSummary summary;
	private List<AccountSalementResult> resultList;
	private long offset;
	private long limit;
}
