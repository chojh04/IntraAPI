package kr.co.kpcard.backoffice.controller.billing.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.ListSummary;
import kr.co.kpcard.backoffice.repository.billing.model.BillingEnrollment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetRegBilling {
	private BillingEnrollment resultList;
}
