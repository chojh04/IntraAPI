package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.merchant.MerchantList;
import kr.co.kpcard.backoffice.component.merchant.MerchantMerchantSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantGetMerchants {
	private MerchantMerchantSummary summary;
	private List<MerchantList> resultList;
}
