package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceTotalRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_CUR_CODE = 3;
	protected final static int LEN_CREATE_DATE = 6;
	protected final static int LEN_SALE_COUNT = 7;
	protected final static int LEN_SALE_ORIGIN_AMOUNT = 12;
	protected final static int LEN_VAT = 12;
	protected final static int LEN_SALE_AMOUNT = 12;
	protected final static int LEN_CANCEL_COUNT = 7; 
	protected final static int LEN_CANCEL_ORIGIN_AMOUNT = 12;
	protected final static int LEN_CANCEL_VAT = 12;
	protected final static int LEN_CANCEL_AMOUNT = 12;
	protected final static int LEN_IPP_COUNT = 7;
	protected final static int LEN_IPP_AMOUNT = 12;
	protected final static int LEN_CANCEL_IPP_COUNT = 7;
	protected final static int LEN_CANCEL_IPP_AMOUNT = 12;
	protected final static int LEN_FILLER = 65;
	
	private String divider; // 레코드 구분 : 2 ("30" 청구용 ) 
	private String currencyCode; // 통화코드  : 3 
	private String createDate; // 파일작성일 : 6 
	
	private String saleCount; // 일반건수 합계 : 7
	private String saleOriginAmount; // 일반순매출액 합계 : 12  
	private String vat; // 일반 봉사료 합계 : 12  
	private String saleAmount; // 일반매출액 합계 : 12 
	private String cancelCount; // 취소 일반 건수 : 7 
	private String cancelOriginAmount; // 취소 일반순매출액 합계 : 12 
	private String cancelVat; // 취소일반봉사료 합계 : 12 
	private String cancelAmount; // 취소 일반 매출액 합계 12 

	private String ippCount; // 할부 건수 합계 :7 
	private String ippAmount; // 할부매출액 합계 : 12 
	private String cancelIppCount; // 취소 할부건수 합계 : 7 
	private String cancelIppAmount; // 취소 할부매출액 합계 : 12 
	private String filler; // filler : 65 
	
	public InvoiceTotalRecord(){}
	
	public InvoiceTotalRecord(byte[] record)
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
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE
					, LEN_CREATE_DATE);
			
			this.saleCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					, LEN_SALE_COUNT);
			
			this.saleOriginAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT
					, LEN_SALE_ORIGIN_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT
					, LEN_VAT);
			
			this.saleAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					, LEN_SALE_AMOUNT);
			
			this.cancelCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT
					, LEN_CANCEL_COUNT);
			
			this.cancelOriginAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT
					, LEN_CANCEL_ORIGIN_AMOUNT);
			
			this.cancelVat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					, LEN_VAT);
			
			this.cancelAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT
					, LEN_CANCEL_AMOUNT);
			
			this.ippCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT + LEN_CANCEL_AMOUNT
					, LEN_IPP_COUNT);
			
			this.ippAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT
					, LEN_IPP_AMOUNT);
			
			this.cancelIppCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT + LEN_IPP_AMOUNT
					, LEN_CANCEL_IPP_COUNT);
			
			this.cancelIppAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT + LEN_IPP_AMOUNT
					+ LEN_CANCEL_IPP_COUNT
					, LEN_CANCEL_IPP_AMOUNT);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CREATE_DATE
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT + LEN_IPP_AMOUNT
					+ LEN_CANCEL_IPP_COUNT + LEN_CANCEL_IPP_AMOUNT
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
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public String getSaleCount() {
		return saleCount;
	}
	
	public String getSaleOriginAmount() {
		return saleOriginAmount;
	}
	
	public String getVat() {
		return vat;
	}
	public String getSaleAmount() {
		return saleAmount;
	}
	public String getCancelCount() {
		return cancelCount;
	}
	public String getCancelOriginAmount() {
		return cancelOriginAmount;
	}
	public String getCancelVat() {
		return cancelVat;
	}
	public String getCancelAmount() {
		return cancelAmount;
	}
	public String getIppCount() {
		return ippCount;
	}
	public String getIppAmount() {
		return ippAmount;
	}
	public String getCancelIppCount() {
		return cancelIppCount;
	}
	public String getCancelIppAmount() {
		return cancelIppAmount;
	}
	public String getFiller() {
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"InvoiceTotalRecord [divider=%s, currencyCode=%s, createDate=%s"
				+ ", saleCount=%s, saleOriginAmount=%s, vat=%s"
				+ ", saleAmount=%s, cancelCount=%s, cancelOriginAmount=%s"
				+ ", cancelVat=%s, cancelAmount=%s, ippCount=%s"
				+ ", ippAmount=%s, cancelIppCount=%s, cancelIppAmount=%s"
				+ ", filler=%s]",
				divider, currencyCode, createDate
				, saleCount, saleOriginAmount, vat
				, saleAmount, cancelCount, cancelOriginAmount
				, cancelVat, cancelAmount, ippCount
				, ippAmount, cancelIppCount, cancelIppAmount
				, filler);
	}
	
	
	
}
