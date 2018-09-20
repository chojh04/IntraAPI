package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.merchant.MerchantBillingList;
import kr.co.kpcard.backoffice.component.merchant.MerchantMerchantSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantBillings {
	private MerchantMerchantSummary summary;
	private List<MerchantBillingList> resultList;
}
