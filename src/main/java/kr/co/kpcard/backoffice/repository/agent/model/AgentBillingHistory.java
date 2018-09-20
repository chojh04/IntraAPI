package kr.co.kpcard.backoffice.repository.agent.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentBillingHistory {
	private String submerchant_nm;
	private String service_id;
	private String service_nm;
	private String start_date;
	private String end_date;
	private String billing_date;
	private String status;
	private long payment_cnt;
	private long payment_sum;
	private long cancel_cnt;
	private long cancel_sum;
	private long billing_amount;
	private long affiliate_comm;
	private long affiliate_vat;
	private long affiliate_summ;
	private long final_billing_amount;
}
