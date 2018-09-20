package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.component.merchant.MerchantMerchantSummary;
import kr.co.kpcard.backoffice.component.merchant.MerchantServiceList;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ResponseMerchantServices {
	private MerchantMerchantSummary summary;
	private List<MerchantServiceList> resultList;
}
