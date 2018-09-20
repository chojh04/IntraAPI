package kr.co.kpcard.backoffice.service.billing;

import java.util.List;

import kr.co.kpcard.backoffice.component.ListSummary;
import kr.co.kpcard.backoffice.repository.billing.model.BillingEnrollment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetRegBillings {
	private ListSummary summary;
	private List<BillingEnrollment> resultList;
}
