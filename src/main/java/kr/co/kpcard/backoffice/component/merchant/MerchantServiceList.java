package kr.co.kpcard.backoffice.component.merchant;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MerchantServiceList {
	private String serviceId;
	private String submerchantId;
	private String name;
	private String merchantName;
	private String categoryName;
	private String category;
	private String typeName;
	private String typeCode;
	private String useFlag;
	private String useFlagName;
	private String billingName;
	private String serviceBillingId;
	private String svcConnId;
	public void setValues(String serviceId, String submerchantId,
			String name, String merchantName, String categoryName,
			String category, String typeName, String typeCode, String useFlag,
			String useFlagName, String billingName, String serviceBillingId,
			String svcConnId) {
		this.serviceId = serviceId;
		this.submerchantId = submerchantId;
		this.name = name;
		this.merchantName = merchantName;
		this.categoryName = categoryName;
		this.category = category;
		this.typeName = typeName;
		this.typeCode = typeCode;
		this.useFlag = useFlag;
		this.useFlagName = useFlagName;
		this.billingName = billingName;
		this.serviceBillingId = serviceBillingId;
		this.svcConnId = svcConnId;
	}
	
}
