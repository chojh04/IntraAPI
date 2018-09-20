package kr.co.kpcard.backoffice.repository.account.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountChargement {
	private String seq;
	private String summ_seq;
	private String submerchant_id;
	private String submerchant_nm;
	private String service_id;
	private String service_nm;
	private String service_type;
	private String service_category;
	private String svc_conn_id;
	private String deal_divider;
	private String deal_dt;
	private Date approval_dt;
	private String approval_status_nm;
	private String approval_status;
	private String approval_no;
	private String order_no;
	private String sale_divider;
	private String saleDividerName;
	private String cardCdName;
	private String card_cd;
	private String card_nm;
	private String card_no;
	private String card_enc_no;
	private String card_mng_no;
	private String amount;
	private String balance;
	private String charge_type;
	private String chargeTypeName;
	private String charge_method;
	private String chargeMethodName;
	private String chargeStatusName;
	private String store_cd;
	private String store_nm;
	private String pos_no;
	private String cp_id;
	private String cp_user_id;
	private String cp_user_ip;
	private String agent_id;
	private String commision;
	private String merchant_comm_type;
	private String merchant_commision;
	private String merchant_tax_type;
	private String merchant_tax;
	private String kpc_comm_type;
	private String kpc_commision;
	private String kpc_tax_type;
	private String kpc_tax;
	private String add_comm_type01;
	private String add_commision01;
	private String add_tax_type01;
	private String add_tax01;
	private String add_comm_type02;
	private String add_commision02;
	private String add_tax_type02;
	private String add_tax02;
	private String billing_sum;
	private String desc01;
	private String desc02;
	
	public String getLingUrl()
	{
		return "/approvals/chargements/chargement?seq=" + this.seq;
	}
	
	public String getApproval_dt()
	{		
		SimpleDateFormat transForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return transForm.format(this.approval_dt); 
	}
}
