package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositStartRecord implements RecordInterface{

	
	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_CREATE_DATE = 6; 
	protected final static int LEN_MERCHANT_CORP_NO = 10;
	protected final static int LEN_VAN_CORP_NO = 10;
	protected final static int LEN_SERVICE_DIVIDER = 3;
	protected final static int LEN_FILLER = 169;
	
	private String divider; // 레코드 구분 : 2 
	private String createDate; // 파일 작성일 : 6  
	private String merchantCorpNo; // 거래사업자등록번호 : 10 
	private String vanCorpNo; // van사업자등록번호 : 10
	private String serviceDivider; // 서비스 구분 : 3 ("DDC") 
	private String filler; // filler; 
	
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
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_MERCHANT_CORP_NO 
					+ LEN_VAN_CORP_NO
					, LEN_SERVICE_DIVIDER);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER + LEN_MERCHANT_CORP_NO 
					+ LEN_VAN_CORP_NO + LEN_SERVICE_DIVIDER
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
	
	public String getServiceDivider() 
	{
		return serviceDivider;
	}
	
	public String getFiller() 
	{
		return filler;
	}
	
	@Override
	public String toString() 
	{
		return String.format(
				"DepositStartRecord [divider=%s, createDate=%s"
				+ ", merchantCorpNo=%s, vanCorpNo=%s"
				+ ", serviceDivider=%s, filler=%s]"
				, divider, createDate
				, merchantCorpNo, vanCorpNo
				, serviceDivider, filler);
	}
	
}
