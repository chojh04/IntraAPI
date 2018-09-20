package kr.co.kpcard.protocol.settlement.nice;



public class DepositEndRecord implements RecordInterface{

	
	private final static int LEN_SIZE = 165;
	private final static int LEN_DIVIDER = 2;
	private final static int LEN_TOTAL_COUNT = 6;
	private final static int LEN_FILLER = 157;
	
	private String divider; // 레코드 구분 : 2 ("54") 
	private String totalCount ;  // total 레코드 수 : 3 (segment 수 or record 총수) 
	private String filler; // filler : 157
	
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
			
		}
		catch (Exception e) {
			// TODO: handle exception
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
		return String.format("DepositEndRecord [divider=%s, totalCount=%s, filler=%s]"
				, divider, totalCount, filler);
	}
	
	
	
}
