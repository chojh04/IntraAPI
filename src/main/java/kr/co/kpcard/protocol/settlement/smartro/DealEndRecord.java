package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DealEndRecord implements RecordInterface {

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_CORP_COUNT = 3;
	protected final static int LEN_TOTAL_DEAL_COUNT = 9;
	protected final static int LEN_FILLER = 187;
	
	private String divider; // 레코드 구분 : 1 ("E") 
	private String corpCount ;  // 사업자 수 : 3 (Total Record 수 ) 
	private String totalDealCount ;  // 총 건수 : 9 (Data Record  총 건수) 
	private String filler; // filler : 187
	
	
	public DealEndRecord(){}
	
	public DealEndRecord(byte[] record)
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
			
			this.corpCount = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CORP_COUNT);
			
			this.totalDealCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CORP_COUNT
					, LEN_TOTAL_DEAL_COUNT);
			
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
	
	public String getDivider() {
		return divider;
	}

	public String getCorpCount() {
		return corpCount;
	}

	public String getTotalDealCount() {
		return totalDealCount;
	}

	public String getFiller() {
		return filler;
	}
   
	@Override
	public String toString() 
	{
		return String.format("DealEndRecord [divider=%s, corpCount=%s"
				+ ", totalDealCount=%s, filler=%s]", divider, corpCount
				, totalDealCount, filler);
	}
	
	
}
