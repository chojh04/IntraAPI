package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantBilling {
	private String service_billing_id;
	private String service_id;
	private String name;
	private String code;
	private String type;
	private String divider;
	private String bank_nm;
	private String bank_acc_no;
	private String vr_bank_nm;
	private String vr_bank_acc_no;
	private String bank_holder;
	private long share_rate;
	private long unpaid_amt;
	private long limits_amt;
	private long credit_setting_amt;
	private String billing_dt;
	private String billing_duration;
	private String merchant_billing_type;
	private String merchant_commision;
	private String merchant_tax;
	private String kpc_billing_type;
	private long kpc_commision;
	private long kpc_tax;
	private String add_billing_type01;
	private long add_commision01;
	private long add_tax01;
	private String add_billing_type02;
	private long add_commision02;
	private long add_tax02;
	private String mng_nm;
	private String mng_tel;
	private Date create_dt;
	private String create_desc;
	private String create_adm_id;
	private Date update_dt;
	private String update_desc;
	private String update_adm_id;
}
