package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsTailRecord implements RecordInterface {
	
	public final static String TAIL_DIVIDER = "T";
	
	protected final static int LEN_SIZE 					= 240;
	
	protected final static int LEN_DIVIDER 					= 1;
	protected final static int LEN_SEND_COUNT 				= 7;
	
	protected final static int LEN_ACT_COUNT 				= 7;
	protected final static int LEN_ACT_AMOUNT 				= 10;
	protected final static int LEN_ACT_CANCEL_COUNT 		= 7;
	protected final static int LEN_ACT_CANCEL_AMOUNT 		= 10;
	
	protected final static int LEN_PAY_COUNT 				= 7;
	protected final static int LEN_PAY_AMOUNT 				= 10;
	protected final static int LEN_PAY_CANCEL_COUNT 		= 7;
	protected final static int LEN_PAY_CANCEL_AMOUNT 		= 10;
	
	protected final static int LEN_REFUND_COUNT 			= 7;
	protected final static int LEN_REFUND_AMOUNT 			= 10;
	
	protected final static int LEN_TRANS_COUNT 				= 7;
	protected final static int LEN_TRANS_AMOUNT 			= 10;
	
	protected final static int LEN_FILLER 					= 130;
	
	
	private String divider;//레코드구분 : 1
	private long sendCount;// 전송 건수 : 7
	private long activeCount;//활성화(재충전)건수:7
	private long activeAmount;//활성화(재충전)금액:10
	private long activecancelCount;//활성화취소(재충전)건수:7
	private long activeCancelAmount;//활성화취소(재충전)건수:10
	private long paymentCount;//결제건수 : 7
	private long paymentAmount;//결제금액 : 10
	private long paymentCancelCount;//결제취소건수 : 7
	private long paymentCancelAmount;//결제취소금액 : 10
	private long refundCount;//환불승인건수 : 7
	private long refundSum;//환불승인금액 : 10
	private long transCount;//잔액이체 건수 : 7
	private long transSum;//잔액이체 금액 : 10
	
	private String filler;//FILLER : 130 

	public void setDivider(String divider) {
		this.divider = divider;
	}

	public long getSendCount() {
		return sendCount;
	}

	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}

	public long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(long activeCount) {
		this.activeCount = activeCount;
	}

	public long getActiveAmount() {
		return activeAmount;
	}

	public void setActiveAmount(long activeAmount) {
		this.activeAmount = activeAmount;
	}

	public long getActivecancelCount() {
		return activecancelCount;
	}

	public void setActivecancelCount(long activecancelCount) {
		this.activecancelCount = activecancelCount;
	}

	public long getActiveCancelAmount() {
		return activeCancelAmount;
	}

	public void setActiveCancelAmount(long activeCancelAmount) {
		this.activeCancelAmount = activeCancelAmount;
	}

	public long getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(long paymentCount) {
		this.paymentCount = paymentCount;
	}

	public long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public long getPaymentCancelCount() {
		return paymentCancelCount;
	}

	public void setPaymentCancelCount(long paymentCancelCount) {
		this.paymentCancelCount = paymentCancelCount;
	}

	public long getPaymentCancelAmount() {
		return paymentCancelAmount;
	}

	public void setPaymentCancelAmount(long paymentCancelAmount) {
		this.paymentCancelAmount = paymentCancelAmount;
	}

	public long getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(long refundCount) {
		this.refundCount = refundCount;
	}

	public long getRefundSum() {
		return refundSum;
	}

	public void setRefundSum(long refundSum) {
		this.refundSum = refundSum;
	}

	public long getTransCount() {
		return transCount;
	}

	public void setTransCount(long transCount) {
		this.transCount = transCount;
	}

	public long getTransSum() {
		return transSum;
	}

	public void setTransSum(long transSum) {
		this.transSum = transSum;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getDivider() {
		return divider;
	}

	@Override
	public String toString() {
		return String.format(
				"GSSMTail [divider=%s, sendCount=%s, activeCount=%s, activeAmount=%s, activecancelCount=%s, activeCancelAmount=%s, paymentCount=%s, paymentAmount=%s, paymentCancelCount=%s, paymentCancelAmount=%s, refundCount=%s, refundSum=%s, transCount=%s, transSum=%s, filler=%s]",
				divider, sendCount, activeCount, activeAmount, activecancelCount, activeCancelAmount, paymentCount,
				paymentAmount, paymentCancelCount, paymentCancelAmount, refundCount, refundSum, transCount, transSum,
				filler);
	}

	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		boolean result = false; 
		
		try 
		{
			
			result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
		
		}
		
		return result;
	}

	@Override
	public String makeRecordData() {
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padLeft(String.valueOf(this.divider            ),"0", LEN_DIVIDER));
			sb.append(StringUtil.padLeft(String.valueOf(this.sendCount          ),"0", LEN_SEND_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.activeCount        ),"0", LEN_ACT_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.activeAmount       ),"0", LEN_ACT_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.activecancelCount  ),"0", LEN_ACT_CANCEL_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.activeCancelAmount ),"0", LEN_ACT_CANCEL_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.paymentCount       ),"0", LEN_PAY_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.paymentAmount      ),"0", LEN_PAY_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.paymentCancelCount ),"0", LEN_PAY_CANCEL_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.paymentCancelAmount),"0", LEN_PAY_CANCEL_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.refundCount        ),"0", LEN_REFUND_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.refundSum          ),"0", LEN_REFUND_AMOUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.transCount         ),"0", LEN_TRANS_COUNT));
			sb.append(StringUtil.padLeft(String.valueOf(this.transSum           ),"0", LEN_TRANS_AMOUNT));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e) 
		{
			sb.delete(0, sb.length());
		}
	
		return sb.toString();
	}
	
	
	
	/*
	@Override
	public String toString() {
		StringBuffer	sb = new StringBuffer();
		
		sb.append(recordType);
		sb.append(StringUtils.leftPad(String.format("%d", totalCount), 7, '0'));
		
		sb.append(StringUtils.leftPad(String.format("%d", activeCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", activeAmt), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", canActCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", canActAmt), 10, '0'));		
		sb.append(StringUtils.leftPad(String.format("%d", payCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", payAmount), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", cancelPayCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", cancelPayAmount), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", refundCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", refundAmount), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", transferCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", transferAmount), 10, '0'));
		sb.append(StringUtils.rightPad("", FILLER_SIZE));
		
		return sb.toString();
	}	
	
	public static GSSMTail parseTail(String tailString) {
		GSSMTail		tail = null;
		int				dataSize = 0;
		int				curPos = 0;

		if ( tailString != null &&
			 tailString.length() == RECORD_SIZE-1 ) {
			tail = new GSSMTail();
			curPos = 1;
			dataSize = 7;
			tail.setTotalCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	//	전송건수		
			dataSize = 7;
			tail.setActiveCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	//	황성화(재충전) 건수
			dataSize = 10;
			tail.setActiveAmt(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		//	활성화(재충전) 금액
			dataSize = 7;
			tail.setCanActCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	//	황성화(재충전) 취소 건수
			dataSize = 10;
			tail.setCanActAmt(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		//	활성화(재충전) 취소 금액
			dataSize = 7;
			tail.setPayCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		//	결제 건수
			dataSize = 10;
			tail.setPayAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		//	결제 금액
			dataSize = 7;
			tail.setCancelPayCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	// 결제 취소 건수
			dataSize = 10;
			tail.setCancelPayAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	// 결제 취소 금액
			dataSize = 7;
			tail.setRefundCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		// 환불 건수
			dataSize = 10;
			tail.setRefundAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		// 환불 금액
			dataSize = 7;
			tail.setTransferCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));		// 이체 건수
			dataSize = 10;
			tail.setTransferAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));	// 이체 금액
		}
		return tail;
	}
	*/
}
