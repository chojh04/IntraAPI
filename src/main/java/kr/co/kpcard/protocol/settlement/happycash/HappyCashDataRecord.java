package kr.co.kpcard.protocol.settlement.happycash;

import kr.co.kpcard.common.utils.StringUtil;

public class HappyCashDataRecord implements RecordInterface {

	public final static String DATA_DIVIDER = "D";
	
	protected final static int LEN_SIZE 			= 150;
	protected final static int LEN_DIVIDER 			= 1;
	protected final static int LEN_DEAL_DATE 		= 8;
	protected final static int LEN_CARD_NO 			= 40;
	protected final static int LEN_CHARGE_AMOUNT 	= 10;
	protected final static int LEN_DEAL_DIVIDER 	= 1;
	protected final static int LEN_FILLER 			= 90;
	
	private String divider;
	private String dealDate;
	private String cardNo;
	private String chargeAmount;
	private String dealDivider;  // 거래 구분 ('0' :  승인, '9' : 취소 )
	private String filler;
	

	public void setDivider(String divider) {
		this.divider = divider;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public void setDealDivider(String dealDivider) {
		this.dealDivider = dealDivider;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getDivider() 
	{
		return divider;
	}
	
	public String getDealDate() 
	{
		return dealDate;
	}
	
	public String getCardNo() 
	{
		return cardNo;
	}
	
	public String getChargeAmount() 
	{
		return chargeAmount;
	}
	
	public String getDealDivider() 
	{
		return dealDivider;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() {
		return String.format(
				"PopChargeData [divider=%s, dealDate=%s"
				+ ", cardNo=%s, chargeAmount=%s"
				+ ", dealDivider=%s, filler=%s]",
				divider, dealDate
				, cardNo, chargeAmount
				, dealDivider, filler);
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
			
			this.dealDate = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_DEAL_DATE);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE
					, LEN_CARD_NO);
			
			this.chargeAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE + LEN_CARD_NO
					, LEN_CHARGE_AMOUNT);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE + LEN_CARD_NO
					+ LEN_CHARGE_AMOUNT
					, LEN_DEAL_DIVIDER);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_DEAL_DATE + LEN_CARD_NO
					+ LEN_CHARGE_AMOUNT + LEN_DEAL_DIVIDER
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
	public String makeRecordData() 
	{
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padRight(this.dealDate, LEN_DEAL_DATE));
			sb.append(StringUtil.padRight(this.cardNo,LEN_CARD_NO));
			sb.append(StringUtil.padLeft(this.chargeAmount, "0",LEN_CHARGE_AMOUNT));
			sb.append(StringUtil.padRight(this.dealDivider, LEN_DEAL_DIVIDER));
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
