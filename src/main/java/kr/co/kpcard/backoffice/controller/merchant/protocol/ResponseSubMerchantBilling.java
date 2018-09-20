package kr.co.kpcard.backoffice.controller.merchant.protocol;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;
import lombok.Data;

@Data
public class ResponseSubMerchantBilling {

	private SubMerchantSVCBilling billing;
	private SubMerchantCommision commision;
	
	public ResponseSubMerchantBilling() {}

	public ResponseSubMerchantBilling(SubMerchantSVCBilling billing, SubMerchantCommision commision) {
		this.billing = billing;
		this.commision = commision;
	}
}
