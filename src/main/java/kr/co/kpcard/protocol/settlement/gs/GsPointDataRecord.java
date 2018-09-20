package kr.co.kpcard.protocol.settlement.gs;

import kr.co.kpcard.common.utils.StringUtil;

public class GsPointDataRecord implements RecordInterface {
	
	public final static String DATA_DIVIDER = "D";
	
	protected final static int LEN_SIZE 				= 200;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_SEQ_NO 				= 10;
	protected final static int LEN_CARD_NO 				= 16;
	protected final static int LEN_STORE_CODE 			= 5;
	protected final static int LEN_DEAL_DATE 			= 14;
	protected final static int LEN_DEAL_DIVIDER 		= 1;
	protected final static int LEN_USE_POINT 			= 10;
	protected final static int LEN_APPROVAL_DATE 		= 8;
	protected final static int LEN_APPROVAL_NO 			= 10;
	protected final static int LEN_ORIGIN_APPROVAL_DATE = 8;
	protected final static int LEN_ORIGIN_APPROVAL_NO 	= 10;
	protected final static int LEN_FILLER 				= 107;

	
	private String divider; 			// 레코드 구분 
	private String seqNo; 				// 레코드 시퀀스 번호  
	private String cardNo; 				// 멤버쉽 팝 카드 번호 
	private String storeCode; 			// 점포 코드 
	private String dealDate;  			// 거래일  
	private String dealDivider; 		// 거래 구분 ( '1' : 사용 , '2' : 취소) 
	private String usePoint; 			// 포인트 사용 금액 
	private String approvalDate; 		// 승인 날짜  
	private String approvalNo; 			// 승인 번호  
	private String orignApprovalDate; 	// 원 승인 날짜  
	private String orignApprovalNo;		// 원 승인 번호  
	private String filler; 				// filler 
	
	
	public String getDivider() 
	{
		return divider;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public String getDealDate() {
		return dealDate;
	}
	public String getDealDivider() {
		return dealDivider;
	}
	public String getUsePoint() {
		return usePoint;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public String getOrignApprovalDate() {
		return orignApprovalDate;
	}
	public String getOrignApprovalNo() {
		return orignApprovalNo;
	}
	
	public String getFiller() 
	{
		return filler;
	}
	
	public void setRecord(String divider, String seqNo, String cardNo 
			, String storeCode, String dealDate, String dealDivider
			, String usePoint, String approvalDate, String approvalNo
			, String originApprovalDate, String originApprovalNo
			, String filler)
	{
		this.divider = divider;
		this.seqNo = seqNo;
		this.cardNo = cardNo;
		
		this.storeCode = storeCode;
		this.dealDate = dealDate;
		this.dealDivider = dealDivider;
		
		this.usePoint = usePoint;
		this.approvalDate = approvalDate;
		this.approvalNo = approvalNo;
		
		this.orignApprovalDate = originApprovalDate;
		this.orignApprovalNo = originApprovalNo;
		this.filler = filler;
	}
	
	@Override
	public String toString() 
	{
		return String.format(
				"GsPointDataRecord [divider=%s, seqNo=%s"
				+ ", cardNo=%s, storeCode=%s, dealDate=%s"
				+ ", dealDivider=%s, usePoint=%s, approvalDate=%s"
				+ ", approvalNo=%s, orignApprovalDate=%s, orignApprovalNo=%s"
				+ ", filler=%s]",
				divider, seqNo, cardNo
				, storeCode, dealDate, dealDivider
				, usePoint, approvalDate, approvalNo,
				orignApprovalDate, orignApprovalNo, filler);
	}
	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		boolean result = true;
		
		try 
		{
			this.divider = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.seqNo = StringUtil.copyToString(record
					, LEN_DIVIDER
					, LEN_SEQ_NO);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO
					, LEN_CARD_NO);
			
			this.storeCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO
					, LEN_STORE_CODE);
			
			this.dealDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					, LEN_DEAL_DATE);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE
					, LEN_DEAL_DIVIDER);
			
			this.usePoint = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER
					, LEN_USE_POINT);
			
			this.approvalDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER + LEN_USE_POINT
					, LEN_APPROVAL_DATE);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER + LEN_USE_POINT
					+ LEN_APPROVAL_DATE
					, LEN_APPROVAL_NO);
			
			this.orignApprovalDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER + LEN_USE_POINT
					+ LEN_APPROVAL_DATE + LEN_APPROVAL_NO
					, LEN_ORIGIN_APPROVAL_DATE);
			
			this.orignApprovalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER + LEN_USE_POINT
					+ LEN_APPROVAL_DATE + LEN_APPROVAL_NO + LEN_ORIGIN_APPROVAL_DATE
					, LEN_ORIGIN_APPROVAL_NO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEQ_NO + LEN_CARD_NO + LEN_STORE_CODE
					+ LEN_DEAL_DATE + LEN_DEAL_DIVIDER + LEN_USE_POINT
					+ LEN_APPROVAL_DATE + LEN_APPROVAL_NO + LEN_ORIGIN_APPROVAL_DATE
					+ LEN_ORIGIN_APPROVAL_NO
					, LEN_FILLER);
			
		} 
		catch (Exception e) 
		{
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
			sb.append(StringUtil.padLeft(this.seqNo, "0", LEN_SEQ_NO));
			sb.append(StringUtil.padRight(this.cardNo, LEN_CARD_NO));
			sb.append(StringUtil.padRight(this.storeCode, LEN_STORE_CODE));
			sb.append(StringUtil.padRight(this.dealDate, LEN_DEAL_DATE));
			sb.append(StringUtil.padRight(this.dealDivider, LEN_DEAL_DIVIDER));
			sb.append(StringUtil.padLeft(this.usePoint, "0", LEN_USE_POINT));
			sb.append(StringUtil.padRight(this.approvalDate, LEN_APPROVAL_DATE));
			sb.append(StringUtil.padRight(this.approvalNo, LEN_APPROVAL_NO));
			sb.append(StringUtil.padRight(this.orignApprovalDate, LEN_ORIGIN_APPROVAL_DATE));
			sb.append(StringUtil.padRight(this.orignApprovalNo, LEN_ORIGIN_APPROVAL_NO));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			sb.delete(0, sb.length());
		}
		
		return sb.toString();
	}	
	
	
}
