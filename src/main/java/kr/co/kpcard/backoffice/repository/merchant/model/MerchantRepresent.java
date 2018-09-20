package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantRepresent {
	private String merchant_id;
	private String erp_code;
	private String name;
	private String name_eng;
	private String alias;
	private String alias_eng;
	private Date create_dt;
	private String create_desc;
	private Date update_dt;
	private String update_desc;
	private String biz_kind;
	private String biz_cond;
	private String biz_grp;
	private String bizGrpName;
	private String biz_reg_no;
	private String corp_reg_no;
	private String open_dt;
	private String close_dt;
	private String closed_flag;
	private int capital_amt;
	private int emp_cnt;
	private int store_cnt;
	private int avg_sale_month;
	private int avg_sale_year;
	private String ceo_nm;
	private String zipcode;
	private String address;
	private String address_dtl;
	private String tel;
	private String tel_sub;
	private String fax;
	private String fax_sub;
	private String submerchantCnt;
	private String use_flag;
	private String useFlagName;
	
}
