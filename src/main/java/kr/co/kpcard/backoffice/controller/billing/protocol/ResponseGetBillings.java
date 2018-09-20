package kr.co.kpcard.backoffice.controller.billing.protocol;

import java.util.List;


import kr.co.kpcard.backoffice.component.billing.BillingSpecificationSummary;
import kr.co.kpcard.backoffice.repository.billing.model.BillingSpecification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetBillings {
	private BillingSpecificationSummary summary;
	private List<BillingSpecification> resultList;
}
