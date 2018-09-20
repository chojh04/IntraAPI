package kr.co.kpcard.protocol.settlement.happycash;

import kr.co.kpcard.common.utils.StringUtil;

public class HappyCashTailRecord implements RecordInterface {
	
	public final static String TAIL_DIVIDER = "T";
	
	protected final static int LEN_SIZE 				= 150;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_TOTAL_COUNT 			= 7;
	protected final static int LEN_APPROVAL_COUNT 		= 7;
	protected final static int LEN_APPROVAL_AMOUNT 		= 10;
	protected final static int LEN_CANCEL_COUNT 		= 7;
	protected final static int LEN_CANCEL_AMOUNT 		= 10;
	protected final static int LEN_BILLING_AMOUNT 		= 10;
	protected final static int LEN_FILLER 				= LEN_SIZE - LEN_DIVIDER - LEN_TOTAL_COUNT - LEN_APPROVAL_COUNT
														- LEN_APPROVAL_AMOUNT - LEN_APPROVAL_AMOUNT - LEN_CANCEL_COUNT - LEN_CANCEL_AMOUNT
														- LEN_BILLING_AMOUNT;
	
	private String divider; //레코드 구분 
	private long totalCount; // 전체 거래 건수 
	private long approvalCount; // 승인 건 수 
	private long apprvoalAmount; // 승인(충전) 금액  
	private long cancelCount; // 취소 건 수 
	private long cancelAmount; // 취소 금액  
	private long billingAmount; // 정산  
	private String filler; // filler 

	public String getDivider() {
		return divider;
	}

	public void setDivider(String divider) {
		this.divider = divider;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(long approvalCount) {
		this.approvalCount = approvalCount;
	}

	public long getApprvoalAmount() {
		return apprvoalAmount;
	}

	public void setApprvoalAmount(long apprvoalAmount) {
		this.apprvoalAmount = apprvoalAmount;
	}

	public long getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(long cancelCount) {
		this.cancelCount = cancelCount;
	}

	public long getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(long cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public long getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(long billingAmount) {
		this.billingAmount = billingAmount;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	@Override
	public String toString() {
		return "HappyCashTailRecord [divider=" + divider + ", totalCount=" + totalCount + ", approvalCount="
				+ approvalCount + ", apprvoalAmount=" + apprvoalAmount + ", cancelCount=" + cancelCount
				+ ", cancelAmount=" + cancelAmount + ", billingAmount=" + billingAmount + ", filler=" + filler + "]";
	}

	@Override
	public String makeRecordData() {
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padLeft(String.valueOf(this.divider         ),"0", LEN_DIVIDER));
			sb.append(StringUtil.padLeft(String.valueOf(this.totalCount      ),"0", LEN_TOTAL_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.approvalCount   ),"0", LEN_APPROVAL_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.apprvoalAmount  ),"0", LEN_APPROVAL_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.cancelCount     ),"0", LEN_CANCEL_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.cancelAmount    ),"0", LEN_CANCEL_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.billingAmount   ),"0", LEN_BILLING_AMOUNT));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e) 
		{
			sb.delete(0, sb.length());
		}
	
		return sb.toString();
	}

	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
