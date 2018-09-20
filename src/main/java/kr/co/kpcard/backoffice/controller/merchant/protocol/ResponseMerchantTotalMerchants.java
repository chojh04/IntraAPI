package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.merchant.MerchantMerchantSummary;
import kr.co.kpcard.backoffice.component.merchant.RepresentList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantTotalMerchants {
	private MerchantMerchantSummary summary;
	private List<RepresentList> resultList;
}
