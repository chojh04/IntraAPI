package kr.co.kpcard.backoffice.controller.card.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.ListSummary;
import kr.co.kpcard.backoffice.repository.card.mapper.BalanceRefund;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCardBalnaceRefund {
	private BalanceRefund resultList;
}
