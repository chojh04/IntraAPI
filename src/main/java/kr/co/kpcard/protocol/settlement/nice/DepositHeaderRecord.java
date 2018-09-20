package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositHeaderRecord implements RecordInterface{

	private final static int LEN_SIZE 					= 165;
	private final static int LEN_DIVIDER 				= 2;
	private final static int LEN_CREATE_DATE 			= 6;
	private final static int LEN_CARD_CORP_NO 			= 10;
	private final static int LEN_MERCHANT_CORP_NO 		= 10;
	private final static int LEN_RECEIPT_START_DATE 	= 6;
	private final static int LEN_RECEIPT_END_DATE 		= 6;
	private final static int LEN_DEPOSIT_DATE 			= 6;
	private final static int LEN_MERCHANT_NO 			= 15;
	private final static int LEN_CURRENCY_CODE 			= 3;
	private final static int LEN_SERVICE_DIVIDER 		= 3;
	private final static int LEN_SALE_START_DATE 		= 6;
	private final static int LEN_SALE_END_DATE 			= 6;
	private final static int LEN_BILLING_DATE 			= 6;
	private final static int LEN_FILLER 				= 80;
	
	
	private String divider; 			// 레코드 구분 : 2 ("52") 
	private String createDate; 			// 파일작성일 : 6 
	private String cardCorpNo; 			// 카드사 사업자번호 : 10 
	private String merchantCorpNo; 		//가맹점 사업자 번호 : 10 
	private String receiptStartDate; 	// 접수기간(시작일) : 6 
	private String receiptEndDate; 		// 접수기간(종료일) : 6 
	private String depositDate; 		// 입금 일자 : 6 
	private String merchantNo; 			// 가맹점 번호 : 15
	private String currencyCode; 		//통화코드 : 3 
	private String serviceDivider; 		// 서비스 구분 : 3 ("EDI" or "HDC" ) 
	private String saleStartDate; 		// 매출일자(시작일) : 6 
	private String saleEndDate; 		// 매출일자(종료일) : 6 
	private String billingDate; 		// 청구일자 : 6 (나이스에서 카드사로 청구일) 
	private String filler; 				// filler : 80 
	
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
			
			this.cardCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE
					, LEN_CARD_CORP_NO);
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					, LEN_MERCHANT_CORP_NO);
			
			this.receiptStartDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO
					, LEN_RECEIPT_START_DATE);
			
			this.receiptEndDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					, LEN_RECEIPT_END_DATE);
			
			this.depositDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE
					, LEN_DEPOSIT_DATE);
			
			this.merchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					, LEN_MERCHANT_NO);
			
			this.currencyCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO
					, LEN_CURRENCY_CODE);
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO + LEN_CURRENCY_CODE
					, LEN_SERVICE_DIVIDER);
			
			this.saleStartDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO + LEN_CURRENCY_CODE
					+ LEN_SERVICE_DIVIDER
					, LEN_SALE_START_DATE);
			
			this.saleEndDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO + LEN_CURRENCY_CODE
					+ LEN_SERVICE_DIVIDER + LEN_SALE_START_DATE
					, LEN_SALE_END_DATE);
			
			this.billingDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO + LEN_CURRENCY_CODE
					+ LEN_SERVICE_DIVIDER
					+ LEN_SALE_START_DATE + LEN_SALE_END_DATE
					, LEN_BILLING_DATE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_CARD_CORP_NO
					+ LEN_MERCHANT_CORP_NO + LEN_RECEIPT_START_DATE
					+ LEN_RECEIPT_END_DATE + LEN_DEPOSIT_DATE
					+ LEN_MERCHANT_NO + LEN_CURRENCY_CODE
					+ LEN_SERVICE_DIVIDER
					+ LEN_SALE_START_DATE + LEN_SALE_END_DATE
					+ LEN_BILLING_DATE
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
	
	public String getCardCorpNo() 
	{
		return cardCorpNo;
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
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"DepositHeaderRecord [divider=%s, createDate=%s, cardCorpNo=%s"
				+ ", merchantCorpNo=%s, receiptStartDate=%s, receiptEndDate=%s"
				+ ", depositDate=%s, merchantNo=%s, currencyCode=%s"
				+ ", serviceDivider=%s, saleStartDate=%s, saleEndDate=%s"
				+ ", billingDate=%s, filler=%s]",
				divider, createDate, cardCorpNo
				, merchantCorpNo, receiptStartDate, receiptEndDate
				, depositDate, merchantNo, currencyCode
				, serviceDivider, saleStartDate, saleEndDate
				, billingDate, filler);
	}
	
}
