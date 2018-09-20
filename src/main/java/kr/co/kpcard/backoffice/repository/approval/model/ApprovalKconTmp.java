package kr.co.kpcard.backoffice.repository.approval.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalKconTmp {
	private String seq;
	private String merchant_id;
	private String merchant_pw;
	private String product_id;
	private String title;
	private String type_code;
	private String type_code_nm;
	private String type_detail;
	private String type_detail_nm;
	private String amount;
	private String expire_days;
	private String expire_days_type;
	private String seller;
	private String usage;
	private String coupon_length;
	private String status;
	private String register;
	private String appr_status;
	private String appr_type_code;
	private String appr_desc;
}
