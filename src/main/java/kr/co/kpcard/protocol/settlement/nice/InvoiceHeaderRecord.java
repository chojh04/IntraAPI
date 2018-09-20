package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceHeaderRecord implements RecordInterface{

	
	private final static int LEN_SIZE = 150;
	private final static int LEN_DIVIDER = 1;
	private final static int LEN_SERVICE_DIVIDER = 3;
	private final static int LEN_MERCHANT_CORP_NO = 10;
	private final static int LEN_CREATE_DATE = 8;
	private final static int LEN_FILLER = 0;
	
	private String divider; // 레코드 구분 : 1
	private String serviceDivider; // 서비스 구분 : 3 
	private String merchantCorpNo; // 거래처 사업자 번호 : 10 
	private String createDate; // 파일 생성일 : 8 
	private String filler; // filler 
	
	
	public InvoiceHeaderRecord(){}
	
	public InvoiceHeaderRecord(byte[] record)
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
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER
					, LEN_MERCHANT_CORP_NO);
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER
					+ LEN_MERCHANT_CORP_NO
					, LEN_CREATE_DATE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SERVICE_DIVIDER
					+ LEN_MERCHANT_CORP_NO + LEN_CREATE_DATE
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
	
	public String getServiceDivider() 
	{
		return serviceDivider;
	}
	
	public String getMerchantCorpNo() 
	{
		return merchantCorpNo;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getFiller() 
	{
		return filler;
	}
	
	
}
