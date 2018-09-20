package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class DealTrailerRecord implements RecordInterface{

	private final static int LEN_SIZE 			= 165;
	private final static int LEN_DIVIDER 		= 1;
	private final static int LEN_TOTAL_COUNT 	= 6;
	private final static int LEN_FILLER 		= 158;
	
	private String divider; 	// 레코더 구분 : 1 ('T' : trailer Record ) 
	private String totalCount; 	// 총 건수 : 6 
	private String filler; 		// filler : 158 
	
	public DealTrailerRecord(){}
	
	public DealTrailerRecord(byte[] record)
	{
		this.pasre(record);
	}
	
	
	@Override
	public boolean pasre(byte[] record)
	{
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
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

	@Override
	public int validate() {
		// TODO Auto-generated method stub
		return 0;
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
	public String toString() 
	{
		return String.format("DealTrailerRecord [divider=%s, totalCount=%s, filler=%s]"
				, divider, totalCount, filler);
	}
	
	
	
}
