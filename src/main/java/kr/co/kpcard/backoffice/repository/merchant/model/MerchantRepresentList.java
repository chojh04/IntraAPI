package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantRepresentList {
	private String merchant_id;
	private String gubun;
	private String name;
	private Date create_dt;
	private String biz_kind;
	private String biz_cond;
	private String biz_grp;
	private String corp_reg_no;
	private String alias;
	private String use_flag;
	private String updatedt;
	private String submerchantcnt;
	
	public String getLingUrl()
	{
		return "/merchants/represent?merchantId=" + this.merchant_id;
	}
}
