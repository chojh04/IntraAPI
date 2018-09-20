package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestMerchant {
	private String serviceId;
	private String serviceName;
	private String submerchantId;
	private String name;
	private String category;
	private String serviceType;
	private String useFlag;
	private String saleDivider;
	private String svcConnId;
	private String svcConnPw;
	private String agentId;
	private String agentPw;
	private String createDesc;
	private String createAdmId;
	private String updateDesc;
	private String updateAdmId;
}
