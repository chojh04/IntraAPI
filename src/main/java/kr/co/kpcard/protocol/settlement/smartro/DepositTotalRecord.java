package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DepositTotalRecord implements RecordInterface {

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_RECEIPT_COUNT = 6;
	protected final static int LEN_RECEIPT_AMOUNT = 12;
	protected final static int LEN_RETURN_COUNT = 6; 
	protected final static int LEN_RETURN_AMOUNT = 12; 
	protected final static int LEN_DEFER_COUNT = 6;
	protected final static int LEN_DEFER_AMOUNT = 12;
	protected final static int LEN_DEFER_CLEAR_COUNT = 6; 
	protected final static int LEN_DEFER_CLEAR_AMOUNT = 12;
	protected final static int LEN_TOTAL_COUNT = 6; 
	protected final static int LEN_TOTAL_AMOUNT = 12;
	protected final static int LEN_TOTAL_VAT = 12;
	protected final static int LEN_DEPOSIT_AMOUNT = 12;
	protected final static int LEN_ACCOUNT_NO = 12;
	protected final static int LEN_FILLER = 64;
	
	private String divider; // 레코드 구분 : 2 ("53" 반송용 )
	private String receiptCount; // 접수 건수 : 6
	private String receiptAmount; // 접수 금액 :12 
	private String returnCount; // 반송총건수 : 6 
	private String returnAmount; // 본송총금액 : 12 
	private String deferCount; // 보류 총 건수 : 6 
	private String deferAmount; // 보류총금액 : 12 
	private String deferClearCount; // 보류해제총건수 : 6 
	private String deferClearAmount; // 보류해제총금액 : 12 
	private String totalCount; // 합계건수 : 6  (합게 건수 = 접수건수 + 보류해제총건수 )
	private String totalAmount; // 합계금액 : 12 ( 합계 금액 = (접수금액 + 보류해제총금액) - (반송총금액 + 보류총금액) ) 
	private String totalVat; // 수수료 합계 : 12 
	private String depositAmount; // 입금액 : 12 ( 입금액 = 합계 금액 - 수수료 합계 )`
	private String accountNo; // 결제 계좌 번호 : 12 
	private String filler; // filler : 64 
	
	public DepositTotalRecord(){}
	
	public DepositTotalRecord(byte[] record)
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
			
			this.receiptCount = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_RECEIPT_COUNT);
			
			this.receiptAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT
					, + LEN_RECEIPT_AMOUNT);
			
			this.returnCount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT
					, LEN_RETURN_COUNT);
			
			this.returnAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT
					, LEN_RETURN_AMOUNT);
			
			this.deferCount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT
					, LEN_DEFER_COUNT);
			
			this.deferAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					, LEN_DEFER_AMOUNT);
			
			this.deferClearCount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT
					, LEN_DEFER_CLEAR_COUNT);
			
			this.deferClearAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT
					, LEN_DEFER_CLEAR_AMOUNT);
			
			this.totalCount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					, LEN_TOTAL_COUNT);
			
			this.totalAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					+ LEN_TOTAL_COUNT
					, LEN_TOTAL_AMOUNT);
			
			this.totalVat = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					+ LEN_TOTAL_COUNT + LEN_TOTAL_AMOUNT
					, LEN_TOTAL_VAT);
			
			this.depositAmount = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					+ LEN_TOTAL_COUNT + LEN_TOTAL_AMOUNT + LEN_TOTAL_VAT 
					, LEN_DEPOSIT_AMOUNT);
			
			this.accountNo = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					+ LEN_TOTAL_COUNT + LEN_TOTAL_AMOUNT + LEN_TOTAL_VAT
					+ LEN_DEPOSIT_AMOUNT
					, LEN_ACCOUNT_NO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER  + LEN_RECEIPT_COUNT + LEN_RECEIPT_AMOUNT 
					+ LEN_RETURN_COUNT + LEN_RETURN_AMOUNT + LEN_DEFER_COUNT
					+ LEN_DEFER_AMOUNT + LEN_DEFER_CLEAR_COUNT + LEN_DEFER_CLEAR_AMOUNT
					+ LEN_TOTAL_COUNT + LEN_TOTAL_AMOUNT + LEN_TOTAL_VAT
					+ LEN_DEPOSIT_AMOUNT + LEN_ACCOUNT_NO
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
	public String getReceiptCount() 
	{
		return receiptCount;
	}
	public String getReceiptAmount()
	{
		return receiptAmount;
	}
	public String getReturnCount() 
	{
		return returnCount;
	}
	public String getReturnAmount() 
	{
		return returnAmount;
	}
	public String getDeferCount() 
	{
		return deferCount;
	}
	public String getDeferAmount() 
	{
		return deferAmount;
	}
	public String getDeferClearCount() 
	{
		return deferClearCount;
	}
	public String getDeferClearAmount() 
	{
		return deferClearAmount;
	}
	public String getTotalCount() 
	{
		return totalCount;
	}
	public String getTotalAmount() 
	{
		return totalAmount;
	}
	public String getTotalVat() 
	{
		return totalVat;
	}
	public String getDepositAmount() 
	{
		return depositAmount;
	}
	public String getAccountNo() 
	{
		return accountNo;
	}
	public String getFiller() 
	{
		return filler;
	}
	
	@Override
	public String toString() 
	{
		return String.format(
				"DepositTotalRecord [divider=%s, receiptCount=%s, receiptAmount=%s"
				+ ", returnCount=%s, returnAmount=%s, deferCount=%s"
				+ ", deferAmount=%s, deferClearCount=%s, deferClearAmount=%s"
				+ ", totalCount=%s, totalAmount=%s, totalVat=%s"
				+ ", depositAmount=%s, accountNo=%s, filler=%s]",
				divider, receiptCount, receiptAmount
				, returnCount, returnAmount, deferCount
				, deferAmount,deferClearCount, deferClearAmount
				, totalCount, totalAmount, totalVat
				, depositAmount, accountNo, filler);
	}
	
	
}
