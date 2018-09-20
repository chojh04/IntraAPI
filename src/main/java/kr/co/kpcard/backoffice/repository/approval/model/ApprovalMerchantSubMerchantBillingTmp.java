package kr.co.kpcard.backoffice.repository.approval.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalMerchantSubMerchantBillingTmp {
	private String service_billing_id;
	private String service_id;
	private String name;
	private String code;
	private String codename;
	private String divider;
	private String dividername;
	private String bankcd;
	private String banknm;
	private String bank_acc_no;
	private String vr_bank_nm;
	private String vr_bank_acc_no;
	private String bank_holder;
	private String billing_dt;
	private String billing_duration;
	private String billingdurationname;
	private String merchant_comm_type;
	private String merchantcommtypename;
	private String merchant_commision;
	private String merchant_tax_type;
	private String merchanttaxtypename;
	private String merchant_tax;
	private String mng_nm;
	private String mng_tel;
	private String mng_email;
	private String kpc_mng_nm;
	private String kpc_mng_tel;
	private String kpc_mng_email;
	private String create_dt;
	private String apl_end_dt;
	private String appr_status;
	private String appr_type_code;
	private String appr_desc;
	
	public String getBilling_dt_name() {
		return "D+ " + this.billing_dt + " 일";
	}
	public String getBillingdurationName() {
		return this.billingdurationname + " 일";
	}
	public String getMerchant_commision_name() {
		return this.merchant_commision +"%";
	}
	
}
