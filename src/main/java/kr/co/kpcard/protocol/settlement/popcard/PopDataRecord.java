package kr.co.kpcard.protocol.settlement.popcard;

import kr.co.kpcard.common.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopDataRecord implements RecordInterface {
	
	protected final static int LEN_SIZE 				= 300;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_DEAL_DATE 			= 8;
	protected final static int LEN_ORDER_NO 			= 64;
	protected final static int LEN_APPROVAL_NO 			= 32;
	protected final static int LEN_DEAL_DIVIDER 		= 4;
	protected final static int LEN_CARD_NO 				= 16;
	protected final static int LEN_USE_DATE 			= 14;
	protected final static int LEN_AMOUNT 				= 10;
	protected final static int LEN_BALANCE 				= 10;
	protected final static int LEN_MERCHANT_CODE 		= 20;
	protected final static int LEN_MERCHANT_NAME 		= 100;
	protected final static int LEN_ON_OFF_DIVIDER 		= 3;
	protected final static int LEN_CARD_DIVIDER 		= 3;
	protected final static int LEN_FILLER 				= 15;
	
	private final Logger logger = LoggerFactory.getLogger(PopDataRecord.class);
	
	private String divider;
	private String dealDate; 
	private String orderNo;
	private String approvalNo;
	private String dealDivider;
	private String cardNo;
	private String useDate;
	private String amount;
	private String balance;
	private String merchantCode;
	private String merchantName;
	private String onOffLineDivier;
	private String cardDivider;
	private String filler;
	
	
	public String getDivider() 
	{
		return divider;
	}
	public String getDealDate() 
	{
		return dealDate;
	}
	public String getOrderNo() 
	{
		return orderNo;
	}
	public String getApprovalNo() 
	{
		return approvalNo;
	}
	public String getDealDivider() 
	{
		return dealDivider;
	}
	public String getCardNo() 
	{
		return cardNo;
	}
	public String getUseDate()
	{
		return useDate;
	}
	public String getAmount()
	{
		return amount;
	}
	public String getBalance() 
	{
		return balance;
	}
	public String getMerchantCode()
	{
		return merchantCode;
	}
	public String getMerchantName() 
	{
		return merchantName;
	}
	public String getOnOffLineDivier() 
	{
		return onOffLineDivier;
	}
	public String getCardDivider()
	{
		return cardDivider;
	}
	public String getFiller() 
	{
		return filler;
	}
	@Override
	public String toString() 
	{
		return String
				.format("PopDataRecord [divider=%s, dealDate=%s, orderNo=%s, approvalNo=%s, dealDivider=%s, cardNo=%s, useDate=%s, amount=%s, balance=%s, merchantCode=%s, merchantName=%s, onOffLineDivier=%s, cardDivider=%s, filler=%s]",
						divider, dealDate, orderNo, approvalNo, dealDivider,
						cardNo, useDate, amount, balance, merchantCode,
						merchantName, onOffLineDivier, cardDivider, filler);
	}
	
	public boolean parseToComma(String record){
		try {
			String[] recordArray = record.split(",");
			this.dealDate = recordArray[0];
			this.orderNo = recordArray[1];
			this.approvalNo = recordArray[2];
			this.dealDivider = recordArray[3];
			this.cardNo = recordArray[4];
			this.useDate = recordArray[5];
			this.amount = recordArray[6];
			this.balance = recordArray[7];
			this.merchantCode = recordArray[8];
			this.merchantName = recordArray[9];
			this.onOffLineDivier = recordArray[10];
			this.cardDivider = recordArray[11];
			
		}catch (Exception e){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean parse(byte[] record) 
	{
		// TODO Auto-generated method stub
		boolean result = false;
		
		try{
			
			this.dealDate = StringUtil.copyToString(record
					, 0
					, LEN_DEAL_DATE);
			
			this.orderNo = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE
					, LEN_ORDER_NO);
			
			this.approvalNo = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					, LEN_APPROVAL_NO);
			
			this.dealDivider = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO
					, LEN_DEAL_DIVIDER);
			
			this.cardNo = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER
					, LEN_CARD_NO);
			
			this.useDate = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					, LEN_USE_DATE);

			this.amount = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE
					, LEN_AMOUNT);
			
			this.balance = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE + LEN_AMOUNT
					, LEN_BALANCE);
			
			this.merchantCode = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE + LEN_AMOUNT + LEN_BALANCE
					, LEN_MERCHANT_CODE);
			
			this.merchantName = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE + LEN_AMOUNT + LEN_BALANCE
					+ LEN_MERCHANT_CODE
					, LEN_MERCHANT_NAME);
			
			this.onOffLineDivier = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE + LEN_AMOUNT + LEN_BALANCE
					+ LEN_MERCHANT_CODE + LEN_MERCHANT_NAME
					, LEN_ON_OFF_DIVIDER);
			
			this.cardDivider = StringUtil.copyToString(record
					, 0 + LEN_DEAL_DATE + LEN_ORDER_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_USE_DATE + LEN_AMOUNT + LEN_BALANCE
					+ LEN_MERCHANT_CODE + LEN_MERCHANT_NAME + LEN_ON_OFF_DIVIDER
					, LEN_CARD_DIVIDER);

			result = true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception : " + e.getMessage());
			return false;
		}
		
		return result;
	}
	
	
	
	public void setDivider(String divider) {
		this.divider = divider;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public void setDealDivider(String dealDivider) {
		this.dealDivider = dealDivider;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public void setOnOffLineDivier(String onOffLineDivier) {
		this.onOffLineDivier = onOffLineDivier;
	}
	public void setCardDivider(String cardDivider) {
		this.cardDivider = cardDivider;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	@Override
	public String makeRecordData() 
	{
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padRight(this.dealDate, LEN_DEAL_DATE));
			sb.append(StringUtil.padRight(this.orderNo, LEN_ORDER_NO));
			sb.append(StringUtil.padRight(this.approvalNo, LEN_APPROVAL_NO));
			sb.append(StringUtil.padRight(this.dealDivider, LEN_DEAL_DIVIDER));
			sb.append(StringUtil.padRight(this.cardNo, LEN_CARD_NO));
			sb.append(StringUtil.padRight(this.useDate, LEN_USE_DATE));
			sb.append(StringUtil.padRight(this.amount, LEN_AMOUNT));
			sb.append(StringUtil.padRight(this.balance, LEN_BALANCE));
			sb.append(StringUtil.padRight(this.merchantCode, LEN_MERCHANT_CODE));
			sb.append(StringUtil.padRight(this.merchantName, LEN_MERCHANT_NAME));
			sb.append(StringUtil.padRight(this.onOffLineDivier, LEN_ON_OFF_DIVIDER));
			sb.append(StringUtil.padRight(this.cardDivider, LEN_CARD_DIVIDER));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("Exception : " + e.getMessage());
			sb.delete(0, sb.length());
		}
		return sb.toString();
	} 
	
	
	
}
