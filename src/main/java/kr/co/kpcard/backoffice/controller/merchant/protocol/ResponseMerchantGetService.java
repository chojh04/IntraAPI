package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantGetService {
	private String serviceId;
	private String submerchantId;
	private String name;
	private String categoryName;
	private String category;
	private String serviceTypeName;
	private String serviceType;
	private String saleDivider;
	private String saleDividerName;
	private String useFlag;
	private String useFlagName;
	private String svcConnId;
	private String svcConnPw;
	private String path;
	public void setValues(String serviceId, String submerchantId,
			String name, String categoryName, String category,
			String serviceTypeName, String serviceType, String saleDivider,
			String saleDividerName, String useFlag, String useFlagName,
			String svcConnId, String svcConnPw, String path) {
		this.serviceId = serviceId;
		this.submerchantId = submerchantId;
		this.name = name;
		this.categoryName = categoryName;
		this.category = category;
		this.serviceTypeName = serviceTypeName;
		this.serviceType = serviceType;
		this.saleDivider = saleDivider;
		this.saleDividerName = saleDividerName;
		this.useFlag = useFlag;
		this.useFlagName = useFlagName;
		this.svcConnId = svcConnId;
		this.svcConnPw = svcConnPw;
		this.path = path;
	}
	
}
