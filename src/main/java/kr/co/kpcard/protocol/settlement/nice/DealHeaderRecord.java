package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class DealHeaderRecord implements RecordInterface{

	private final static int LEN_SIZE 					= 165;
	private final static int LEN_DIVIDER 				= 1;
	private final static int LEN_SERVICE_DIVIDER 		= 3;
	private final static int LEN_CREATE_DATE 			= 8;
	private final static int LEN_FILLER 				= 153;
	
	private String divider; // record구분 : 1 ('H') 
	private String serviceDivider; // 서비스 구분 : 3 ('TLF') 거래 내역 
	private String createDate; // 파일 작성일 : 8 (yyyymmdd) 
	private String filler; // filler : 153 
	
	
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
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_SERVICE_DIVIDER);
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER
					, LEN_CREATE_DATE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER + LEN_CREATE_DATE
					, LEN_FILLER);
			
			result = true;
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
	
	public String getServiceDivider() 
	{
		return serviceDivider;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString()
	{
		return String.format("DealHeaderRecord [divider=%s, serviceDivider=%s, createDate=%s, filler=%s]"
				, divider, serviceDivider, createDate, filler);
	}
	
	
	
	
}
