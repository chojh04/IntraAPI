package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsPointHeaderRecord implements RecordInterface {

	public final static String HEADER_DIVIDER = "H";
	
	protected final static int LEN_SIZE 				= 200;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_DEAL_DATE 			= 8;
	protected final static int LEN_CREATE_DATE 			= 14;
	protected final static int LEN_FILLER 				= 177;
	
	private String divider;		// 레코드 구분 
	private String dealDate; 	// 거래일 
	private String createDate;	// 생성일 
	private String filler; 		// 필러 
	
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getDealDate() 
	{
		return dealDate;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	public void setRecord(String divider, String dealDate 
			, String createDate, String filler)
	{
		this.divider = divider;
		this.dealDate = dealDate;
		this.createDate = createDate;
		this.filler = filler;
	}
	
	@Override
	public String toString()
	{
		return String.format("GsPointHeaderRecord [divider=%s, dealDate=%s, createDate=%s, filler=%s]"
				, divider, dealDate, createDate, filler);
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
			
			this.dealDate = StringUtil.copyToString(record
					, LEN_DIVIDER
					, LEN_DEAL_DATE);
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE
					, LEN_CREATE_DATE);
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE + LEN_CREATE_DATE
					, LEN_FILLER);
			
			result = true;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			result = false;
		}
		
		return result;
	}

	@Override
	public String makeRecordData() 
	{
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padRight(this.dealDate, LEN_DEAL_DATE));
			sb.append(StringUtil.padRight(this.createDate, LEN_CREATE_DATE));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			sb.delete(0, sb.length());
		}
		
		return sb.toString();
	} 
	
}
