package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceEndRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_CUR_CODE = 3;
	protected final static int LEN_SEGMENT_COUNT = 6; 
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
	protected final static int LEN_IPP_CANCEL_COUNT = 7;
	protected final static int LEN_IPP_CANCEL_AMOUNT = 12;
	protected final static int LEN_FILLER = 65;
	
	private String divider; // 레코드 구분 : 2 
	private String currencyCode;  // 통화코드 : 3 
	private String segmentCount; // 파일 세그먼트 수 : 6 
	private String saleCount; // 일반건수 합계 : 7 
	private String saleOriginAmount; // 일반순매출액 합계 : 12 
	private String vat; // 일반봉사료 합계 : 12 
	private String saleAmount; // 일반매출액 합계 : 12 
	private String cancelCount; // 취소일반건수 합계 : 7 
	private String cancelOriginAmount; // 취소일반 순매출액 합계 : 12 
	private String cancelVat; // 취소일반봉사료 합계 : 12 
	private String cancelAmount; // 취소일반매출액 : 12 
	private String ippCount; // 할부건수 합계 : 7 
	private String ippAmount; // 할부매출액 합계 : 12 
	private String ippCancelCount; // 취소할부 건수 합계 : 7 
	private String ippCancelAmount; // 취소 할부매출액 합계 : 12
	private String filler; // filler : 65 
	
	public InvoiceEndRecord(){}
	
	public InvoiceEndRecord(byte[] record)
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
			
			this.segmentCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE 
					, LEN_SEGMENT_COUNT);
			
			this.saleCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					, LEN_SALE_COUNT);
			
			this.saleOriginAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT
					, LEN_SALE_ORIGIN_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT
					, LEN_VAT);
			
			this.saleAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					, LEN_SALE_AMOUNT);
			
			this.cancelCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT
					, LEN_CANCEL_COUNT);
			
			this.cancelOriginAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT
					, LEN_CANCEL_ORIGIN_AMOUNT);
			
			this.cancelVat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					, LEN_CANCEL_VAT);
			
			this.cancelAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT
					, LEN_CANCEL_AMOUNT);
			
			this.ippCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT + LEN_CANCEL_AMOUNT
					, LEN_IPP_COUNT);
			
			this.ippAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT
					, LEN_IPP_AMOUNT);
			
			this.ippCancelCount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT
					+ LEN_IPP_AMOUNT
					, LEN_IPP_CANCEL_COUNT);
			
			this.ippCancelAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT
					+ LEN_IPP_AMOUNT + LEN_IPP_CANCEL_COUNT
					, LEN_IPP_CANCEL_AMOUNT);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_SEGMENT_COUNT
					+ LEN_SALE_COUNT + LEN_SALE_ORIGIN_AMOUNT + LEN_VAT
					+ LEN_SALE_AMOUNT + LEN_CANCEL_COUNT + LEN_CANCEL_ORIGIN_AMOUNT
					+ LEN_CANCEL_VAT + LEN_CANCEL_AMOUNT + LEN_IPP_COUNT
					+ LEN_IPP_AMOUNT + LEN_IPP_CANCEL_COUNT + LEN_IPP_CANCEL_AMOUNT
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
	public String getSegmentCount() {
		return segmentCount;
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
	public String getIppCancelCount() {
		return ippCancelCount;
	}
	public String getIppCancelAmount() {
		return ippCancelAmount;
	}
	public String getFiller() {
		return filler;
	}
	
	
	
	
	
}
