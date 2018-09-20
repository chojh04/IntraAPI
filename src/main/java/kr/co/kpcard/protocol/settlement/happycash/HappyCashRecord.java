package kr.co.kpcard.protocol.settlement.happycash;

import kr.co.kpcard.common.utils.StringUtil;

public class HappyCashRecord implements RecordInterface {
	
	public final static String HEADER_DIVIDER = "H";

	protected final static int LEN_SIZE 			= 150;
	protected final static int LEN_DIVIDER 			= 1;
	protected final static int LEN_CREATE_DATE 		= 8;
	protected final static int LEN_SEND_DATE 		= 8;
	protected final static int LEN_FILLER 			= 133;
	
	private String divider;
	private String createDate;
	private String sendDate;
	private String filler;
	
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getSendDate() 
	{
		return sendDate;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format("PopChargeHeader [divider=%s, createDate=%s, sendDate=%s, filler=%s]"
				, divider, createDate, sendDate, filler);
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
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CREATE_DATE);
			
			this.sendDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE
					, LEN_SEND_DATE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_SEND_DATE
					, LEN_FILLER);
			
			result = true;
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		
			return false;
		}
		
		return result;
	}

	@Override
	public String makeRecordData() {
		// TODO Auto-generated method stub
		return "";
	}
	

	
	
	/*
	public static PopChargeHeader parseHeader(String headerString) {
		PopChargeHeader	header = null;
		
		if ( headerString != null &&
			 headerString.length() == RECORD_SIZE-1 ) {
			header = new PopChargeHeader();
			header.setCreateDate(headerString.substring(1, 9));
			header.setSendDate(headerString.substring(9, 17));
		}
		return header;
	}
	*/
}
