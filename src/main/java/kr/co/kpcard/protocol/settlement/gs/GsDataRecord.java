package kr.co.kpcard.protocol.settlement.gs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementService;
import kr.co.kpcard.common.utils.StringUtil;

public class GsDataRecord implements RecordInterface {
	
	private Logger logger = LoggerFactory.getLogger(GsDataRecord.class);
	
	public final static String DATA_DIVIDER = "D";

	protected final static int LEN_SIZE 				= 240;
	
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_SALE_DATE 			= 8;
	protected final static int LEN_STORE_CODE 			= 6;
	
	protected final static int LEN_DEAL_NO 				= 32;
	protected final static int LEN_APPROVAL_NO 			= 30;
	protected final static int LEN_APPROVAL_DATE 		= 8;
	protected final static int LEN_APPROVAL_TIME 		= 6;
	
	protected final static int LEN_CARD_NO 				= 40;

	protected final static int LEN_ORDER_AMOUNT 		= 10;
	protected final static int LEN_DEAL_AMOUNT 			= 10;
	protected final static int LEN_DEAL_TYPE 			= 3;
	protected final static int LEN_DEAL_DIVIDER 		= 1;
	
	protected final static int LEN_PAY_TYPE      		= 1;
	
	protected final static int LEN_CREDIT_CORP 			= 5;
	protected final static int LEN_CREDIT_APPROVAL_NO 	= 20;
	protected final static int LEN_CREDIT_APPROVAL_DATE = 8;
	
	protected final static int LEN_ORDER_NO 			= 20;
	protected final static int LEN_VAN_DIVIDER 			= 5;
	protected final static int LEN_GS_POINT 			= 10;
	protected final static int LEN_FILLER 				= 16;
	
	
	private String divider;           // 레코드 구  : 1 
	private String saleDate;          // 영업 일자 : 8 
	private String storeCode;         // 점포 코드 : 6 
	private String dealNo;            // 주문(거래) 번호 : 32
	private String approvalNo;        // 승인 번호 : 30 
	private String approvalDate;      // 승인일자 : 8 
	private String approvalTime;      // 승인시간 : 6
	private String cardNo;            // 카드(바코드) 번호 : 40  
	private String orderAmount;       // 요청 금액 : 10
	private String dealAmount;        // 요청 금액 : 10
	private String dealType;          // 처리 구분 : 3 (ACT:활성화, CHR:재충전, PAY:결제, REF:잔액환불, TRN:잔액이체) 
	private String dealDivider;       // 승인/취소 : 1 (0:승인, 9:취소)
	private String payType;           // 결제수단 , 0: 현금 , 1:신용카드
	private String creditCorp;        // 신용카드사 : 5
	private String creditApprovalNo;  //신용카드 결제 승인 번호 : 20 
	private String creditApprovalDate;//신용카드 결제 승인 일자 : 8
	private String orderNo;           //주문번호 : 20 
	private String vanDivider;        //VAN사 구분 : 5
	private String gsPoint;           // GS & Point : 10 
	private String filler;// FILLER : 16
	
	
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getDivider() {
		return divider;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public String getDealNo() {
		return dealNo;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public String getApprovalTime() {
		return approvalTime;
	}
	public String getCardNo() {
		return cardNo;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public String getDealType() {
		return dealType;
	}
	public String getDealDivider() {
		return dealDivider;
	}
	public String getCreditCorp() {
		return creditCorp;
	}
	public String getCreditApprovalNo() {
		return creditApprovalNo;
	}
	public String getCreditApprovalDate() {
		return creditApprovalDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getVanDivider() {
		return vanDivider;
	}
	public String getGsPoint() {
		return gsPoint;
	}
	public String getFiller() {
		return filler;
	}
	
	public void setDivider(String divider) {
		this.divider = divider;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public void setDealDivider(String dealDivider) {
		this.dealDivider = dealDivider;
	}
	public void setCreditCorp(String creditCorp) {
		this.creditCorp = creditCorp;
	}
	public void setCreditApprovalNo(String creditApprovalNo) {
		this.creditApprovalNo = creditApprovalNo;
	}
	public void setCreditApprovalDate(String creditApprovalDate) {
		this.creditApprovalDate = creditApprovalDate;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setVanDivider(String vanDivider) {
		this.vanDivider = vanDivider;
	}
	public void setGsPoint(String gsPoint) {
		this.gsPoint = gsPoint;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Override
	public String toString() {
		return String.format(
				"GSSMData [divider=%s, saleDate=%s, storeCode=%s, dealNo=%s, approvalNo=%s, approvalDate=%s, approvalTime=%s, cardNo=%s, dealAmount=%s, dealType=%s, dealDivider=%s, creditCorp=%s, creditApprovalNo=%s, creditApprovalDate=%s, orderNo=%s, vanDivider=%s, gsPoint=%s, filler=%s]",
				divider, saleDate, storeCode, dealNo, approvalNo, approvalDate, approvalTime, cardNo, dealAmount,
				dealType, dealDivider, creditCorp, creditApprovalNo, creditApprovalDate, orderNo, vanDivider, gsPoint,
				filler);
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
			this.orderAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO
					, LEN_ORDER_AMOUNT);
			this.dealAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT
					, LEN_DEAL_AMOUNT);		
			this.dealType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT 
					, LEN_DEAL_TYPE);
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE
					, LEN_DEAL_DIVIDER);
			this.creditCorp = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER
					, LEN_CREDIT_CORP);
			this.creditApprovalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP
					, LEN_CREDIT_APPROVAL_NO);
			this.creditApprovalDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP + LEN_CREDIT_APPROVAL_NO
					, LEN_CREDIT_APPROVAL_DATE);
			this.orderNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP + LEN_CREDIT_APPROVAL_NO + LEN_CREDIT_APPROVAL_DATE
					, LEN_ORDER_NO);
			this.vanDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP + LEN_CREDIT_APPROVAL_NO + LEN_CREDIT_APPROVAL_DATE 
					+ LEN_ORDER_NO
					, LEN_VAN_DIVIDER);
			this.gsPoint = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP + LEN_CREDIT_APPROVAL_NO + LEN_CREDIT_APPROVAL_DATE 
					+ LEN_ORDER_NO + LEN_VAN_DIVIDER
					, LEN_GS_POINT);
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SALE_DATE + LEN_STORE_CODE
					+ LEN_DEAL_NO + LEN_APPROVAL_NO + LEN_APPROVAL_DATE
					+ LEN_APPROVAL_TIME  + LEN_CARD_NO + LEN_ORDER_AMOUNT 
					+ LEN_DEAL_AMOUNT + LEN_DEAL_TYPE + LEN_DEAL_DIVIDER 
					+ LEN_CREDIT_CORP + LEN_CREDIT_APPROVAL_NO + LEN_CREDIT_APPROVAL_DATE 
					+ LEN_ORDER_NO + LEN_VAN_DIVIDER + LEN_GS_POINT
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
			sb.append(StringUtil.padLeft(this.orderAmount, "0", LEN_ORDER_AMOUNT));
			sb.append(StringUtil.padLeft(this.dealAmount, "0", LEN_DEAL_AMOUNT));
			sb.append(StringUtil.padRight(this.dealType, LEN_DEAL_TYPE));
			sb.append(StringUtil.padRight(this.dealDivider, LEN_DEAL_DIVIDER));
			sb.append(StringUtil.padRight(this.payType, LEN_PAY_TYPE));
			sb.append(StringUtil.padRight(this.creditCorp, LEN_CREDIT_CORP));
			sb.append(StringUtil.padRight(this.creditApprovalNo, LEN_CREDIT_APPROVAL_NO));
			sb.append(StringUtil.padRight(this.creditApprovalDate, LEN_CREDIT_APPROVAL_DATE));
			sb.append(StringUtil.padRight(this.orderNo, LEN_ORDER_NO));
			sb.append(StringUtil.padRight(this.vanDivider, LEN_VAN_DIVIDER));
			sb.append(StringUtil.padRight(this.gsPoint, LEN_GS_POINT));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			sb.reverse();
		}
		
		return sb.toString();
	}
	
	
	
	/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer	sb = new StringBuffer();
		
		;
		sb.append(recordType);
		sb.append(StringUtils.rightPad(salesDate, 8));
		sb.append(StringUtils.rightPad(branchCode, 6));
		sb.append(StringUtils.rightPad(orderNo, 32));
		sb.append(StringUtils.rightPad(admitNo, 30));
		sb.append(StringUtils.rightPad(admitDate, 8));
		sb.append(StringUtils.rightPad(admitTime, 6));
		sb.append(StringUtils.rightPad(cardNo, 40));
		sb.append(StringUtils.leftPad(String.format("%d", orderAmount), 10, '0'));
		sb.append(StringUtils.leftPad(String.format("%d", payAmount), 10, '0'));
		sb.append(StringUtils.rightPad(transType, 3));
		sb.append(StringUtils.rightPad(admitType, 1));
		sb.append(StringUtils.rightPad(payOption1, 1));
		sb.append(StringUtils.rightPad(payOption2, 5));
		sb.append(StringUtils.rightPad(payOption3, 20));
		sb.append(StringUtils.rightPad(payOption4, 8));
		sb.append(StringUtils.rightPad(payOption5, 20));
		sb.append(StringUtils.rightPad(payOption6, 5));
		sb.append(StringUtils.rightPad(payOption7, 10));
		sb.append(StringUtils.rightPad("", FILLER_SIZE));

		return sb.toString();
	}

	public static GSSMData parseData(String dataString) {
		GSSMData	data = null;
		int				dataSize = 0;
		int				curPos = 0;
		
		if ( dataString != null &&
			 dataString.length() >= RECORD_SIZE ) {
			data = new GSSMData();
			curPos = 1;
			dataSize = 8;
			data.setSalesDate(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 6;
			data.setBranchCode(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 32;
			data.setOrderNo(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 30;
			data.setAdmitNo(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 8;
			data.setAdmitDate(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 6;
			data.setAdmitTime(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 40;
			data.setCardNo(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 10;
			data.setOrderAmount(Integer.parseInt(dataString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 10;
			data.setPayAmount(Integer.parseInt(dataString.substring(curPos, curPos+=dataSize).trim()));
			dataSize = 3;
			data.setTransType(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 1;
			data.setAdmitType(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 1;
			data.setPayOption1(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 5;
			data.setPayOption2(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 20;
			data.setPayOption3(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 8;
			data.setPayOption4(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 20;
			data.setPayOption5(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 5;
			data.setPayOption6(dataString.substring(curPos, curPos+=dataSize).trim());
			dataSize = 10;
			data.setPayOption7(dataString.substring(curPos, curPos+=dataSize).trim());
		}
		return data;
	}
	*/
}
