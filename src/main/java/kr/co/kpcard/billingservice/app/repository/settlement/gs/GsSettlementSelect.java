package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GsSettlementSelect {
	private String stl1;               
	private String stl2;               
	private String stl6;              
	private String stl7;
	private String stl8;
	private String stl9;
	private String deal_no;
	private String approval_no;
	private String merchantid;
	private String work_dt;
	private String seq;
	private String deal_type;
	private String deal_divider;		   
	private long use_point;	
	private String approval_dt;	   
	private String approval_time;
}
