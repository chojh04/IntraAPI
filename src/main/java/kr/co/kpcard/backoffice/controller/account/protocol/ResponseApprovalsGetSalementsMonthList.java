package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.account.AccountSalementSummResult;
import kr.co.kpcard.backoffice.component.account.AccountSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalsGetSalementsMonthList {
	private AccountSummary summary;
	private List<AccountSalementSummResult> resultList;
	private long offset;
	private long limit;
}
