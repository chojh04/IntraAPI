package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsPointTailRecord implements RecordInterface
{
	public final static String TAIL_DIVIDER = "T";
	
	protected final static int LEN_SIZE 				= 200;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_TOTAL_COUNT 			= 10;
	protected final static int LEN_FILLER	 			= 189;
	
	private String divider;
	private String totalCount; 
	private String filler;
	
	
	
	
	public void setRecrd(String divider, String totalCount, String filler) {
		this.divider = divider;
		this.totalCount = totalCount;
		this.filler = filler;
	}

	public String getDivider() 
	{
		return divider;
	}
	
	public String getTotalCount() 
	{
		return totalCount;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	
	@Override
	public String toString() {
		return String.format("GsPointTailRecord [divider=%s, totalCount=%s, filler=%s]"
				, divider, totalCount, filler);
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
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_TOTAL_COUNT
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
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padLeft(this.totalCount, "0", LEN_TOTAL_COUNT));
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
