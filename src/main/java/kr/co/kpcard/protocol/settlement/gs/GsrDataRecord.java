package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsrDataRecord implements RecordInterface {

	public final static String DATA_DIVIDER = "D";
	
	public static final String	RES_CODE_SUCCESS 			= "0000";
	public static final String	RES_CODE_DISCORD_PAY 		= "0001";
	public static final String	RES_CODE_DISCORD_CANCEL 	= "0002";
	public static final String	RES_CODE_DISCORD_BARCODE	= "0003";
	
	
	protected final static int LEN_SIZE 				= 150;
	
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_SALE_DATE 			= 8;
	protected final static int LEN_STORE_CODE 			= 6;
	
	protected final static int LEN_DEAL_NO 				= 32;
	protected final static int LEN_APPROVAL_NO 			= 30;
	protected final static int LEN_APPROVAL_DATE 		= 8;
	protected final static int LEN_APPROVAL_TIME 		= 6;
	
	protected final static int LEN_CARD_NO 				= 20;

	protected final static int LEN_DEAL_AMOUNT 			= 10;
	protected final static int LEN_PAY_AMOUNT 			= 10;
	protected final static int LEN_BALANCE 				= 10;
	protected final static int LEN_DEAL_DIVIDER 		= 1;
	protected final static int LEN_RES_CODE 			= 4;
	protected final static int LEN_FILLER 				= 18;
	
	
	private String divider;
	private String saleDate; // 영업일 
	private String storeCode; 
	private String dealNo; 
	private String approvalNo;
	private String approvalDate;
	private String approvalTime;
	private String cardNo;
	private String dealAmount;
	private String paymentAmount;
	private String balance;
	private String dealDivider; // 거래 구분 ('0' : 승인 , '9' :취소 )
	private String responseCode; // ('0000' :  성공 , '0001' : )
	private String filler;
	
	public String getFiller()
	{
		return filler;
	}
	
	public void setFiller(String filler )
	{
		this.filler = filler;
	}
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getSaleDate() 
	{
		return saleDate;
	}
	
	public String getStoreCode() 
	{
		return storeCode;
	}
	
	public String getDealNo() 
	{
		return dealNo;
	}
	
	public String getApprovalNo() 
	{
		return approvalNo;
	}
	
	public String getApprovalDate() 
	{
		return approvalDate;
	}
	
	public String getApprovalTime() 
	{
		return approvalTime;
	}
	
	public String getCardNo() 
	{
		return cardNo;
	}
	
	public String getDealAmount() 
	{
		return dealAmount;
	}
	
	public String getPaymentAmount() 
	{
		return paymentAmount;
	}
	
	public String getBalance() 
	{
		return balance;
	}
	
	public String getDealDivider() 
	{
		return dealDivider;
	}
	
	public String getResponseCode() 
	{
		return responseCode;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"GSRetailData [divider=%s, saleDate=%s, storeCode=%s"
				+ ", dealNo=%s, approvalNo=%s, approvalDate=%s, approvalTime=%s"
				+ ", cardNo=%s, dealAmount=%s, paymentAmount=%s"
				+ ", balance=%s, dealDivider=%s, responseCode=%s]",
				divider, saleDate, storeCode
				, dealNo, approvalNo
				, approvalDate, approvalTime
				, cardNo, dealAmount, paymentAmount
				, balance, dealDivider, responseCode);
	}

	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try 
		{
			this.divider = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.saleDate = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_SALE_DATE);
			
			this.storeCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE
					, LEN_STORE_CODE);
			
			this.dealNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					, LEN_DEAL_NO);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO
					, LEN_APPROVAL_NO);
			
			this.approvalDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO
					, LEN_APPROVAL_DATE);
			
			this.approvalTime = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					, LEN_APPROVAL_TIME);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME
					, LEN_CARD_NO);
			
			this.dealAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME + LEN_CARD_NO
					, LEN_DEAL_AMOUNT);
			
			this.paymentAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME + LEN_CARD_NO + LEN_DEAL_AMOUNT
					, LEN_PAY_AMOUNT);
			
			this.balance = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME + LEN_CARD_NO + LEN_DEAL_AMOUNT
					+ LEN_PAY_AMOUNT
					, LEN_BALANCE);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME + LEN_CARD_NO + LEN_DEAL_AMOUNT
					+ LEN_PAY_AMOUNT + LEN_BALANCE
					, LEN_DEAL_DIVIDER);
			
			this.responseCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME + LEN_CARD_NO + LEN_DEAL_AMOUNT
					+ LEN_PAY_AMOUNT + LEN_BALANCE + LEN_DEAL_DIVIDER
					, LEN_RES_CODE);
				
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		
			return false;
		}
		
		return result;
	}

	@Override
	public String makeRecordData() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		try 
		{	
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padRight(this.saleDate, LEN_SALE_DATE));
			sb.append(StringUtil.padRight(this.storeCode, LEN_STORE_CODE));
			sb.append(StringUtil.padRight(this.dealNo, LEN_DEAL_NO));
			sb.append(StringUtil.padRight(this.approvalNo, LEN_APPROVAL_NO));
			sb.append(StringUtil.padRight(this.approvalDate, LEN_APPROVAL_DATE));
			sb.append(StringUtil.padRight(this.approvalTime, LEN_APPROVAL_TIME));
			sb.append(StringUtil.padRight(this.cardNo, LEN_CARD_NO));
			sb.append(StringUtil.padLeft(this.dealAmount, "0", LEN_DEAL_AMOUNT));
			sb.append(StringUtil.padLeft(this.paymentAmount, "0", LEN_PAY_AMOUNT));
			sb.append(StringUtil.padLeft(this.balance, "0", LEN_BALANCE));
			sb.append(StringUtil.padRight(this.dealDivider, LEN_DEAL_DIVIDER));
			sb.append(StringUtil.padRight(this.responseCode, LEN_RES_CODE));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			sb.reverse();
		}
		return sb.toString();
	}
	
	
}
