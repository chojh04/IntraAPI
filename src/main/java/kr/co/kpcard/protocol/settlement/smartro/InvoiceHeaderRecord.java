package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceHeaderRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_CUR_CODE = 3;
	protected final static int LEN_CREATE_DATE = 6; 
	protected final static int LEN_MERCHANT_NO = 10;
	protected final static int LEN_STORE_DIVDER = 2;
	protected final static int LEN_CARD_CORP_NO = 10;
	protected final static int LEN_SALE_START_DATE= 6; 
	protected final static int LEN_SALE_END_DATE= 6; 
	protected final static int LEN_SERVICE_DIVDER = 3; 
	protected final static int LEN_EXTEND_MERCHANT_NO = 15;
	protected final static int LEN_PURCHASER_CODE = 4; 
	protected final static int LEN_FILLER = 133;
	
	private String divider; // 레코더 구분 : 2 ("10", 청구)
	private String currencyCode; // 통화코드 : 3 (410:원화, 840:미국달)
 	private String createDate; // 파일작성일 : 6
	private String merchantNo; // 가맹점 번호 :10
	private String storeDivider; // 점별 구분 : 2
	private String cardCorpNo; //카드사 사업자 번호 :10 
	private String saleStartDate; // 매출일자 시작일 : 6 (yyyyMMdd) 
	private String saleEndDate; // 매출일자 종료일 : 6 (yyyyMMdd) 
	private String serviceDivider; // 서비스 구분 : 3 (EDI/EDC) 
	private String extendMerchantNo; // 확장 가맹점 번호 : 15 
	private String purchaserCode; // 매입사 기관 코드 : 4
	private String filler; // filler : 133
	
	
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
			
			this.currencyCode = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CUR_CODE);
			
			this.merchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE
					, LEN_MERCHANT_NO);
			
			this.storeDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					, LEN_STORE_DIVDER);
			
			this.cardCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER
					, LEN_CARD_CORP_NO);
			
			this.saleStartDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO
					, LEN_SALE_START_DATE);
			
			this.saleEndDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO + LEN_SALE_START_DATE
					, LEN_SALE_END_DATE);
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO + LEN_SALE_START_DATE
					+ LEN_SALE_END_DATE
					, LEN_SERVICE_DIVDER);
			
			this.extendMerchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO + LEN_SALE_START_DATE
					+ LEN_SALE_END_DATE + LEN_SERVICE_DIVDER
					, LEN_EXTEND_MERCHANT_NO);
			
			this.purchaserCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO + LEN_SALE_START_DATE
					+ LEN_SALE_END_DATE + LEN_SERVICE_DIVDER + LEN_EXTEND_MERCHANT_NO
					, LEN_PURCHASER_CODE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_MERCHANT_NO
					+ LEN_STORE_DIVDER + LEN_CARD_CORP_NO + LEN_SALE_START_DATE
					+ LEN_SALE_END_DATE + LEN_SERVICE_DIVDER + LEN_EXTEND_MERCHANT_NO
					+ LEN_PURCHASER_CODE
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
	

	public String getDivider() {
		return divider;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public String getStoreDivider() {
		return storeDivider;
	}

	public String getCardCorpNo() {
		return cardCorpNo;
	}

	public String getSaleStartDate() {
		return saleStartDate;
	}

	public String getSaleEndDate() {
		return saleEndDate;
	}

	public String getServiceDivider() {
		return serviceDivider;
	}

	public String getExtendMerchantNo() {
		return extendMerchantNo;
	}

	public String getPurchaserCode() {
		return purchaserCode;
	}

	public String getFiller() {
		return filler;
	}

	@Override
	public String toString() {
		return String.format(
				"InvoiceHeaderRecord [divider=%s, currencyCode=%s, createDate=%s, merchantNo=%s, storeDivider=%s, cardCorpNo=%s, saleStartDate=%s, saleEndDate=%s, serviceDivider=%s, extendMerchantNo=%s, purchaserCode=%s, filler=%s]",
				divider, currencyCode, createDate, merchantNo, storeDivider, cardCorpNo, saleStartDate, saleEndDate,
				serviceDivider, extendMerchantNo, purchaserCode, filler);
	}
	
	
	
	
	
}
