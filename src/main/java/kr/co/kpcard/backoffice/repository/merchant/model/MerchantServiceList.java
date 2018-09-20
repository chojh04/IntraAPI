package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantServiceList {
	private String service_id;
	private String submerchant_id;
	private String name;
	private String merchantName;
	private String categoryName;
	private String category;
	private String typeName;
	private String typeCode;
	private String use_flag;
	private String useFlagName;
	private String billingName;
	private String service_billing_id;
	private String svc_conn_id;
	
}
