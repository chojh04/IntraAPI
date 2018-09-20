package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GsSettlementResult {
	private int seq;
	private String jobDivider;
	private String orderNo;
	private String approvalNo;
	private String svcConnId;
	private String dealType;
	private String dealDivider;
	private long orderAmt;
	private String workDt;
	private String settleSeq;
	private String status;
	private String statusDesc;
	private Date createDt;
	private String approvalDt;
	private String approvalTime;
	
	public void setValue(int seq, String jobDivider, String orderNo, String approvalNo, String svcConnId,
			String dealType, String dealDivider, long orderAmt, String workDt, String settleSeq, String status,
			String statusDesc, Date createDt, String approvalDt, String approvalTime) {
		this.seq=seq;
		this.jobDivider = jobDivider;
		this.orderNo = orderNo;
		this.approvalNo = approvalNo;
		this.svcConnId = svcConnId;
		this.dealType = dealType;
		this.dealDivider = dealDivider;
		this.orderAmt = orderAmt;
		this.workDt = workDt;
		this.settleSeq = settleSeq;
		this.status = status;
		this.statusDesc = statusDesc;
		this.createDt = createDt;
		this.approvalDt = approvalDt;
		this.approvalTime = approvalTime;
	}		
}
