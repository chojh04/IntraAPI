package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsHeaderRecord implements RecordInterface {
	
	public final static String HEADER_DIVIDER = "H";
	
	protected final static int LEN_SIZE 			= 240;
	protected final static int LEN_DIVIDER 			= 1;
	protected final static int LEN_CREATE_DATE		= 8;
	protected final static int LEN_SEND_DATE 		= 8;
	protected final static int LEN_FILLER 			= 223;
	
	private String divider;   		// 레고드 구분 : 1 
	private String createDate;   	// 생성 일자 : 8 
	private String sendDate;     	// 전송 일자 : 8
	private String filler;       	// 필러 : 223 
	
	
	public GsHeaderRecord() {
	}

	public GsHeaderRecord(String divider, String createDate, String sendDate, String filler) {
		this.divider = divider;
		this.createDate = createDate;
		this.sendDate = sendDate;
		this.filler = filler;
	}

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
		return String.format("GSSMHeader [divider=%s, createDate=%s, sendDate=%s, filler=%s]"
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
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padRight(this.createDate, LEN_CREATE_DATE));
			sb.append(StringUtil.padRight(this.sendDate, LEN_SEND_DATE));
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
		sb.append(StringUtils.rightPad(createDate, 8));
		sb.append(StringUtils.rightPad(sendDate, 8));
		sb.append(StringUtils.rightPad("", FILLER_SIZE));

		return sb.toString();
	}

	public static GSSMHeader parseHeader(String headerString) {
		GSSMHeader	header = null;
		
		if ( headerString != null &&
			 headerString.length() == RECORD_SIZE-1 ) {
			header = new GSSMHeader();
			header.setCreateDate(headerString.substring(1, 9));
			header.setSendDate(headerString.substring(9, 17));
		}
		return header;
	}
	*/
}
