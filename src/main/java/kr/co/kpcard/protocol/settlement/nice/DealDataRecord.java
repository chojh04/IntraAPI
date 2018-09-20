package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class DealDataRecord implements RecordInterface{

	private final static int LEN_DIVIDER = 1;
	private final static int LEN_POS_NO = 15;
	private final static int LEN_SALE_DATE = 8;
	private final static int LEN_SALE_TIME = 15;
	private final static int LEN_DEAL_NO = 15;
	private final static int LEN_DEAL_DIVIDER = 15;
	private final static int LEN_CARD_NO = 15;
	private final static int LEN_ORIGIN_DEAL_DATE = 15;
	private final static int LEN_APPROVAL_NO = 15;
	private final static int LEN_DEAL_AMOUNT = 15;
	private final static int LEN_VAT = 15;
	private final static int LEN_TAX = 15;
	private final static int LEN_IPP_PERIOD = 2;
	private final static int LEN_INPUT_DIVIDER = 1;
	private final static int LEN_SELLER_CODE = 4;
	private final static int LEN_BUYER_CODE = 4;
	private final static int LEN_MERCHANT_NO = 15;
	private final static int LEN_MERCHANT_CORP_NO = 10;
	private final static int LEN_DEAL_TYPE = 1;
	private final static int LEN_FOREIGN_CARD_TYPE = 1;
	private final static int LEN_CHECK_CARD_TYPE = 1;
	private final static int LEN_FILLER = 0;
	
	private String divider; // record 구분 : 1 ('D') 데이타 레코드 
	private String posNo; // 단말기 번호 : 15 
	private String saleDate; // 매출(승인) 일자 : 8 (yyyymmdd) 
	private String saleTime; // 매출(승인) 시간 : 6 (hhmmss) 
	private String dealNo; // 거래 일련 번호 : 12 
	private String dealDivider; // 승인/취소 구분 : 2 ('CA' : 승인 , 'CC' : 취소)
	private String cardNo; // 카드 번호 : 20 
	private String originDealDate; // 원 거래 일자 : 8 (yyyymmdd , 취소일 경우에 원거래 승인일자 / 승인일 경우에 거래 일자와 동일) 
	private String approvalNo; // 승인 번호 : 10 
	private String dealAmount; // 거래 금액 : 12 (매출 합계 금액 좌측에 '0'을 채움) 
	private String vat; // 봉사료/수수 : 9 (좌측에 '0'을 채움) 
	private String tax; // 세액 : 9 (좌측에 '0'을 채움)
	private String ippPeriod; // 할부기간 : 2 ('00' : 일시불 , 그 외에는 할부 개월 수 예 : 3개월 03 ) 
	private String inputDivider; // 입력 구분 : 1 ('S' : SWIPE , 'K' : KEYIN ) 
	private String sellerCode; // 발급사 코드 : 4 
	private String buyerCode; // 매입사 코드 : 4 
	private String merchantNo; // 카드사 가맹점 번호 : 15 
	private String merchantCorpNo; // 가맹점 사업자 번호 : 10 
	private String dealType; // 거래 종류  : 1 ('C' : 단말기 거래, 'T' : 전화 승인, 'P' : 온라인 PG 거래 ) 
	private String foreignCardType; // 해외발급 카드 여부 : 1 ('Y' : 해외 발급 카드 , 'N' : 국내 카드 ) 
	private String checkCardType; // 체크 카드 여부 : 1 ('V' : 체크카드, 'N' : 일반 신용카드) 
	private String filler; // FILLER 
	
	public DealDataRecord(){}
	
	public DealDataRecord(byte[] record)
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
			
			this.posNo = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_POS_NO);
			
			this.saleDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO 
					, LEN_SALE_DATE);
			
			this.saleTime = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					, LEN_SALE_TIME);
			
			this.dealNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME
					, LEN_DEAL_NO);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO
					, LEN_DEAL_DIVIDER);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					, LEN_CARD_NO);
			
			this.originDealDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO
					, LEN_ORIGIN_DEAL_DATE);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE
					, LEN_APPROVAL_NO);
			
			this.dealAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					, LEN_DEAL_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT
					, LEN_VAT);
			
			this.tax = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT
					, LEN_TAX);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_TAX
					, LEN_IPP_PERIOD);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_TAX
					, LEN_IPP_PERIOD);
			
			this.inputDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD
					, LEN_INPUT_DIVIDER);
			
			this.sellerCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER
					, LEN_SELLER_CODE);
			
			this.buyerCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					, LEN_BUYER_CODE);
			
			this.merchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE
					, LEN_MERCHANT_NO);
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE + LEN_MERCHANT_NO
					, LEN_MERCHANT_CORP_NO);
			
			this.dealType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE + LEN_MERCHANT_NO + LEN_MERCHANT_CORP_NO
					, LEN_DEAL_TYPE);
			
			this.foreignCardType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE + LEN_MERCHANT_NO + LEN_MERCHANT_CORP_NO
					+ LEN_DEAL_TYPE
					, LEN_FOREIGN_CARD_TYPE);
			
			this.checkCardType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE + LEN_MERCHANT_NO + LEN_MERCHANT_CORP_NO
					+ LEN_DEAL_TYPE + LEN_FOREIGN_CARD_TYPE
					, LEN_CHECK_CARD_TYPE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_DEAL_NO + LEN_DEAL_DIVIDER
					+ LEN_CARD_NO + LEN_ORIGIN_DEAL_DATE + LEN_APPROVAL_NO
					+ LEN_DEAL_AMOUNT + LEN_VAT + LEN_VAT 
					+ LEN_IPP_PERIOD + LEN_INPUT_DIVIDER + LEN_SELLER_CODE
					+ LEN_BUYER_CODE + LEN_MERCHANT_NO + LEN_MERCHANT_CORP_NO
					+ LEN_DEAL_TYPE + LEN_FOREIGN_CARD_TYPE + LEN_CHECK_CARD_TYPE
					, LEN_FILLER);
			
			result =true;	
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			
			return false;
		}
		
		return result;
	}

	@Override
	public int validate() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	public String getDivider() {
		return divider;
	}
	public String getPosNo() {
		return posNo;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public String getDealNo() {
		return dealNo;
	}
	public String getDealDivider() {
		return dealDivider;
	}
	public String getCardNo() {
		return cardNo;
	}
	public String getOriginDealDate() {
		return originDealDate;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public String getVat() {
		return vat;
	}
	public String getTax() {
		return tax;
	}
	public String getIppPeriod() {
		return ippPeriod;
	}
	public String getInputDivider() {
		return inputDivider;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public String getBuyerCode() {
		return buyerCode;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public String getMerchantCorpNo() {
		return merchantCorpNo;
	}
	public String getDealType() {
		return dealType;
	}
	public String getForeignCardType() {
		return foreignCardType;
	}
	public String getCheckCardType() {
		return checkCardType;
	}
	public String getFiller() {
		return filler;
	}


	@Override
	public String toString() 
	{
		return String.format(
				"DealDataRecord [divider=%s, posNo=%s, saleDate=%s, saleTime=%s, dealNo=%s, dealDivider=%s, cardNo=%s, originDealDate=%s, approvalNo=%s, dealAmount=%s, vat=%s, tax=%s, ippPeriod=%s, inputDivider=%s, sellerCode=%s, buyerCode=%s, merchantNo=%s, merchantCorpNo=%s, dealType=%s, foreignCardType=%s, checkCardType=%s, filler=%s]",
				divider, posNo, saleDate, saleTime, dealNo, dealDivider, cardNo, originDealDate, approvalNo, dealAmount,
				vat, tax, ippPeriod, inputDivider, sellerCode, buyerCode, merchantNo, merchantCorpNo, dealType,
				foreignCardType, checkCardType, filler);
	}

	
	
	
	
}
