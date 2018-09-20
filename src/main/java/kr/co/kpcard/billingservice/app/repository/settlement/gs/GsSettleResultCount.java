package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GsSettleResultCount {
	private String jobDivider;
	private String workDt;
	private long sts1;
	private long stsAmt1;
	private long sts2;
	private long stsAmt2;
	private long sts3;
	private long stsAmt3;
	private long sts4;
	private long stsAmt4;
	private long sts5;
	private long stsAmt5;
	
	public GsSettleResultCount(){
		this.sts1=0;
		this.stsAmt1=0;
		this.sts2=0;
		this.stsAmt2=0;
		this.sts3=0;
		this.stsAmt3=0;
		this.sts4=0;
		this.stsAmt4=0;
		this.sts5=0;
		this.stsAmt5=0;
		
	}
}
