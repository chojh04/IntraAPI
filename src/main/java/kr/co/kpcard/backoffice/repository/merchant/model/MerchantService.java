package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantService {
	private String service_id;
	private String submerchant_id;
	private String name;
	private String categoryName;
	private String category;
	private String serviceTypeName;
	private String serviceType;
	private String saleDivider;
	private String saleDividerName;
	private String use_flag;
	private String useFlagName;
	private String svc_conn_id;
	private String svc_conn_pw;
	private String path;
}
