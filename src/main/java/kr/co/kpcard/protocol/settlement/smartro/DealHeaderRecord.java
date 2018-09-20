package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DealHeaderRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_CORP_NO = 10;
	protected final static int LEN_FILLER = 189;
	
	private String divider; // 레코더 구분 : 1 ("H") 
	private String corpNo; // 사업자 번호 : 10 
	private String filler; // filler : 189 
	
	public DealHeaderRecord(){}
	
	public DealHeaderRecord(byte[] record)
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
			
			this.corpNo = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CORP_NO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CORP_NO
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
	
	public String getCorpNo() 
	{
		return corpNo;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format("DealHeaderRecord [divider=%s, corpNo=%s, filler=%s]"
				, divider, corpNo, filler);
	}
	
	
}
