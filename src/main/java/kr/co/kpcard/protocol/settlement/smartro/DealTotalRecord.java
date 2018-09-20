package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DealTotalRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_COUNT = 9;
	protected final static int LEN_FILLER = 190;
	
	private String	divider; // 레코드 구분 : 1 ("T") 
	private String count ; // 건수 : 9 
	private String filler; // filler : 190 
	
	public DealTotalRecord(){}
	
	public DealTotalRecord(byte[] record)
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
			
			this.count = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_COUNT);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_COUNT
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
	public int validate()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getDivider() 
	{
		return divider;
	}
	public String getCount() 
	{
		return count;
	}
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format("DealTotalRecord [recordType=%s, dataCount=%s, filler=%s]"
				, divider, count, filler);
	}
	
	
	
}
