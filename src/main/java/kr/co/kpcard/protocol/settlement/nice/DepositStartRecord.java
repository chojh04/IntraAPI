package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositStartRecord implements RecordInterface{

	private final static int LEN_SIZE 					= 165;
	private final static int LEN_DIVIDER 				= 2;
	private final static int LEN_CREATE_DATE 			= 6;
	private final static int LEN_MERCHANT_CORP_NO 		= 10;
	private final static int LEN_VAN_CORP_NO 			= 10;
	private final static int LEN_FILLER 				= 137;
	
	private String divider; 		// 레코드 구분 : 2 ("51") 
	private String createDate; 		// 파일작성일 :6 
	private String merchantCorpNo; 	// 유통점 사업자 번호 : 10 
	private String vanCorpNo; 		// 나이스 사업자 번호 :10 
	private String filler; 			// filler : 137 
	
	
	public DepositStartRecord(){}
	
	public DepositStartRecord(byte[] record)
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
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CREATE_DATE);
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE
					, LEN_MERCHANT_CORP_NO);
			
			this.vanCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_MERCHANT_CORP_NO
					, LEN_VAN_CORP_NO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_MERCHANT_CORP_NO
					+ LEN_VAN_CORP_NO
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
	public int validate() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getMerchantCorpNo() 
	{
		return merchantCorpNo;
	}
	
	public String getVanCorpNo() 
	{
		return vanCorpNo;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString()
	{
		return String.format(
				"DepositStartRecord [divider=%s, createDate=%s, merchantCorpNo=%s"
				+ ", vanCorpNo=%s, filler=%s]"
				, divider, createDate, merchantCorpNo
				, vanCorpNo, filler);
	}
	
	
	
}
