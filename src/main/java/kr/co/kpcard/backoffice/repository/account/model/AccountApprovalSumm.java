package kr.co.kpcard.backoffice.repository.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountApprovalSumm {
	private String submerchant_nm;
	private String service_nm;
	private String svc_conn_id;
	private String deal_divider;
	private String deal_dt;
	private String approval_status_nm;
	private String approval_status;
	private String cardCdName;
	private String card_cd;
	private String payTypeName;
	private String pay_type;
	private String cnt;
	private String amount;
	private String commision;
}
