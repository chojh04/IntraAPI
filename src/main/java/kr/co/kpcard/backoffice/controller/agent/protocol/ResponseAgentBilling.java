package kr.co.kpcard.backoffice.controller.agent.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAgentBilling {
	private String serviceBillingId;
	private String serviceId;
	private String name;
	private String code;
	private String codeName;
	private String divider;
	private String dividerName;
	private String bankCd;
	private String bankNm;
	private String bankAccNo;
	private String vrBankNm;
	private String vrBankAccNo;
	private String bankHolder;
	private String billingDt;
	private String billingDtName;
	private String billingDuration;
	private String billingDurationName;
	private String merchantCommType;
	private String merchantCommTypeName;
	private String merchantCommision;
	private String merchantCommisionName;
	private String merchantTaxType;
	private String merchantTaxTypeName;
	private String merchantTax;
	private String billingNm;
	private String billingTel;
	private String billingEmail;
	private String kpcBillingNm;
	private String kpcBillingTel;
	private String kpcBillingEmail;
	private String createDt;
	private String aplEndDt;
	private String expectedMerchantCommision;
	
	public void setValues(String serviceBillingId, String serviceId,
			String name, String code, String codeName, String divider,
			String dividerName, String bankCd, String bankNm, String bankAccNo,
			String vrBankNm, String vrBankAccNo, String bankHolder,
			String billingDt, String billingDtName, String billingDuration,
			String billingDurationName, String merchantCommType,
			String merchantCommTypeName, String merchantCommision,
			String merchantCommisionName, String merchantTaxType,
			String merchantTaxTypeName, String merchantTax, String billingNm,
			String billingTel, String billingEmail, String kpcBillingNm,
			String kpcBillingTel, String kpcBillingEmail, String createDt,
			String aplEndDt, String expectedMerchantCommision) {
		this.serviceBillingId = serviceBillingId;
		this.serviceId = serviceId;
		this.name = name;
		this.code = code;
		this.codeName = codeName;
		this.divider = divider;
		this.dividerName = dividerName;
		this.bankCd = bankCd;
		this.bankNm = bankNm;
		this.bankAccNo = bankAccNo;
		this.vrBankNm = vrBankNm;
		this.vrBankAccNo = vrBankAccNo;
		this.bankHolder = bankHolder;
		this.billingDt = billingDt;
		this.billingDtName = billingDtName;
		this.billingDuration = billingDuration;
		this.billingDurationName = billingDurationName;
		this.merchantCommType = merchantCommType;
		this.merchantCommTypeName = merchantCommTypeName;
		this.merchantCommision = merchantCommision;
		this.merchantCommisionName = merchantCommisionName;
		this.merchantTaxType = merchantTaxType;
		this.merchantTaxTypeName = merchantTaxTypeName;
		this.merchantTax = merchantTax;
		this.billingNm = billingNm;
		this.billingTel = billingTel;
		this.billingEmail = billingEmail;
		this.kpcBillingNm = kpcBillingNm;
		this.kpcBillingTel = kpcBillingTel;
		this.kpcBillingEmail = kpcBillingEmail;
		this.createDt = createDt;
		this.aplEndDt = aplEndDt;
		this.expectedMerchantCommision = expectedMerchantCommision;
	}
	
}
