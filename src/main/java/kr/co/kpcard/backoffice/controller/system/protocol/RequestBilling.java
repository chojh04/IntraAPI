package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestBilling {
	private String serviceBillingId;
	private String serviceId;
	private String name;
	private String bankNm;
	private String bankAccNo;
	private String bankHolder;
	private String billingNm;
	private String billingTel;
	private String billingEmail;
	private String kpcBillingNm;
	private String kpcBillingTel;
	private String kpcBillingEmail;
	private String divider;
	private String code;
	private String billingDuration;
	private String billingDt;
	private String merchantCommType;
	private String merchantTaxType;
	private String merchantCommision;
	private String aplEndDt;
	private String createAdmId;
	private String updateAdmId;
	private String billingCommType;
}
