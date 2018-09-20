package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceTraillerRecord implements RecordInterface{

	private final static int LEN_SIZE = 150;
	private final static int LEN_DIVIDER = 1;
	private final static int LEN_TOTAL_DEAL_COUNT = 6; 
	private final static int LEN_FILLER = 143;
	
	private String divider; // 레코드 구분 : 1
	private String totalDealCount ; // 전체 거래 건 수  : 6 
	private String filler; // filler; 
	
	
	public InvoiceTraillerRecord(){}
	
	public InvoiceTraillerRecord(byte[] record)
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
			
			this.totalDealCount = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_TOTAL_DEAL_COUNT);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER +LEN_TOTAL_DEAL_COUNT
					, LEN_FILLER);
		
			result = true;
		}
		catch (Exception e) 
		{
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

	public String getTotalDealCount() 
	{
		return totalDealCount;
	}

	public String getFiller() 
	{
		return filler;
	}

	
}
