package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositHeaderRecord implements RecordInterface{

	protected final static int LEN_SIZE 				= 200;
	protected final static int LEN_DIVIDER 				= 2;
	protected final static int LEN_CREATE_DATE 			= 6;
	protected final static int LEN_CORP_NO		 		= 10;
	protected final static int LEN_MERCHANT_CORP_NO 	= 10;
	protected final static int LEN_RECEIPT_START_DATE 	= 6;
	protected final static int LEN_RECEIPT_END_DATE 	= 6;
	protected final static int LEN_DEPOSIT_DATE 		= 6;
	protected final static int LEN_MERCHANT_NO 			= 15;
	protected final static int LEN_CURRENCY_CODE 		= 3;
	protected final static int LEN_SERVICE_DIVIDER 		= 3;
	protected final static int LEN_SALE_START_DATE 		= 6;
	protected final static int LEN_SALE_END_DATE 		= 6;
	protected final static int LEN_BILLING_DATE 		= 6;
	protected final static int LEN_PURCHASER_CODE 		= 4;
	protected final static int LEN_FILLER 				= 80;
	
	private String divider; // 레코드 구분 : 2 ("52") 
	private String createDate; // 파일작성일 : 6 
	private String corpNo; // 사업자번호 : 10 
	private String merchantCorpNo; // 가맹점 사업자번호 : 10 
	private String receiptStartDate; // 접수 기간 (시작일) : 6 
	private String receiptEndDate; // 접수기간 (종료일) : 6  
	private String depositDate; // 지급 일자 : 6 
	private String merchantNo; // 주 가맹점 번호 : 15 
	private String currencyCode; // 통화코드 
	private String serviceDivider; // 서비스 구분 
	private String saleStartDate; // 매출일자 (시작일) : 6 
	private String saleEndDate; // 매출일자 (종료일) : 6 
	private String billingDate; // 청구일자 : 6 
	private String purchaserCode; // 매입카드사 기관코드 : 4 
	private String filler; // filler : 111 
	
	public DepositHeaderRecord(){}
	
	public DepositHeaderRecord(byte[] record)
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
			
			this.corpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE
					, LEN_CORP_NO);
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO
					, LEN_MERCHANT_CORP_NO);
			
			this.receiptStartDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					, LEN_RECEIPT_START_DATE);
			
			this.receiptEndDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE
					, LEN_RECEIPT_END_DATE);
			
			this.depositDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE
					, LEN_DEPOSIT_DATE);
			
			this.merchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_MERCHANT_NO);
			
			this.currencyCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			this.saleStartDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			this.saleEndDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			this.billingDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			
			this.purchaserCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CORP_NO + LEN_CREATE_DATE
					+ LEN_RECEIPT_START_DATE + LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_CREATE_DATE);
			
			
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
	public String getCorpNo() 
	{
		return corpNo;
	}
	public String getMerchantCorpNo() 
	{
		return merchantCorpNo;
	}
	public String getReceiptStartDate()
	{
		return receiptStartDate;
	}
	public String getReceiptEndDate() 
	{
		return receiptEndDate;
	}
	public String getDepositDate() 
	{
		return depositDate;
	}
	public String getMerchantNo() 
	{
		return merchantNo;
	}
	public String getCurrencyCode() 
	{
		return currencyCode;
	}
	public String getServiceDivider()
	{
		return serviceDivider;
	}
	public String getSaleStartDate() 
	{
		return saleStartDate;
	}
	public String getSaleEndDate()
	{
		return saleEndDate;
	}
	public String getBillingDate() 
	{
		return billingDate;
	}
	public String getPurchaserCode() 
	{
		return purchaserCode;
	}
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"DepositHeaderRecord [divider=%s, createDate=%s, corpNo=%s"
				+ ", merchantCorpNo=%s, receiptStartDate=%s, receiptEndDate=%s"
				+ ", depositDate=%s, merchantNo=%s, currencyCode=%s"
				+ ", serviceDivider=%s, saleStartDate=%s, saleEndDate=%s"
				+ ", demandDate=%s, purchaserCode=%s, filler=%s]",
				divider, createDate, corpNo
				, merchantCorpNo, receiptStartDate, receiptEndDate
				, depositDate, merchantNo, currencyCode
				, serviceDivider, saleStartDate, saleEndDate
				, billingDate, purchaserCode, filler);
	}
	
	
	
	
}
