package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsrTailRecord implements RecordInterface {

	public final static String TAIL_DIVIDER = "T";
	
	protected final static int LEN_SIZE 				= 240;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_TOTAL_COUNT 			= 7;
	protected final static int LEN_PAYMENT_COUNT 		= 7;
	protected final static int LEN_PAYMENT_AMOUNT 		= 10;
	protected final static int LEN_CANCEL_COUNT 		= 7;
	protected final static int LEN_CANCEL_AMOUNT 		= 10;
	protected final static int LEN_FILLER 				= 108;
	
	
	private String divider;
	private String totalCount;
	private String paymentCount;
	private String paymentAmount;
	private String cancelCount;
	private String cancelAmount;
	private String filler;
	
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getTotalCount() 
	{
		return totalCount;
	}
	
	public String getPaymentCount() 
	{
		return paymentCount;
	}
	
	public String getPaymentAmount() 
	{
		return paymentAmount;
	}
	
	public String getCancelCount() 
	{
		return cancelCount;
	}
	
	public String getCancelAmount()
	{
		return cancelAmount;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() {
		return String.format(
				"GSRetailTail [divider=%s, totalCount=%s, paymentCount=%s, paymentAmount=%s, cancelCount=%s, cancelAmount=%s, filler=%s]",
				divider, totalCount, paymentCount, paymentAmount, cancelCount, cancelAmount, filler);
	}

	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		try 
		{
			this.divider = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.totalCount = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_TOTAL_COUNT);
			
			this.paymentCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT
					, LEN_PAYMENT_COUNT);
			
			this.paymentAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT + LEN_PAYMENT_COUNT
					, LEN_PAYMENT_AMOUNT);
			
			this.cancelCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT + LEN_PAYMENT_COUNT
					+ LEN_PAYMENT_AMOUNT
					, LEN_CANCEL_COUNT);
			
			this.cancelAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT + LEN_PAYMENT_COUNT
					+ LEN_PAYMENT_AMOUNT + LEN_CANCEL_COUNT
					, LEN_CANCEL_AMOUNT);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT + LEN_PAYMENT_COUNT
					+ LEN_PAYMENT_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_AMOUNT
					, LEN_FILLER);
			
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		
		
		}
		
		return result;
	}

	@Override
	public String makeRecordData() {
		// TODO Auto-generated method stub
		return "";
	}
	
	
	
	/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer	sb = new StringBuffer();
		
		sb.append(recordType);
		sb.append(StringUtils.leftPad(String.format("%d", totalCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", payCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", payAmount), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", cancelCount), 7, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", cancelAmount), 10, '0'));
		sb.append(StringUtils.rightPad("", FILLER_SIZE));
		
		return sb.toString();
	}	
	
	public static GSRetailTail parseTail(String tailString) {
		GSRetailTail	tail = null;
		int				dataSize = 0;
		int				curPos = 0;

		if ( tailString != null &&
			 tailString.length() == RECORD_SIZE-1 ) {
			tail = new GSRetailTail();
			curPos = 1;
			dataSize = 7;
			tail.setTotalCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 7;
			tail.setPayCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 10;
			tail.setPayAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 7;
			tail.setCancelCount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 10;
			tail.setCancelAmount(Integer.parseInt(tailString.substring(curPos, curPos+=dataSize).trim()));
		}
		return tail;
	}
	*/
}
