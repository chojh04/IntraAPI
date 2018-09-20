package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantMerchantList {
	private String submerchant_id;
	private String gubun;
	private String parent_id;
	private String child_id;
	private String name;
	private String alias;
	private String use_flag;
	private Date create_dt;
	private String updatedt;
	private String servicecnt;
	
	public String getLingUrl()
	{
		return "/merchants/merchant?subMerchantId=" + this.submerchant_id;
	}
}
