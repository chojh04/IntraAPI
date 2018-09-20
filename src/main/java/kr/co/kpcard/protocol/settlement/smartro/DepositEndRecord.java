package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositEndRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_COUNT = 6;
	protected final static int LEN_FILLER = 192;
	
	private String	divider; // 레코드 구분 : 2  ("54") 
	private String count; // total rec 수 : 6 (카드사의 개수를 나타내며 start record와 end record 사이의 header or total record 의 수를 Set 함. 
	private String filler; // filler : 192 
	
	public DepositEndRecord(){}
	
	public DepositEndRecord(byte[] record)
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
		return String.format("DepositEndRecord [divider=%s, count=%s, filler=%s]"
				, divider, count, filler);
	}
	
	
}
