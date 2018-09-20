package kr.co.kpcard.protocol.settlement.nice;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceDataRecord implements RecordInterface{

	
	protected final static int LEN_SIZE = 150;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_POS_NO = 15;
	protected final static int LEN_SALE_DATE = 8; //매출일자
	protected final static int LEN_SALE_TIEM = 6; //매출시간
	protected final static int LEN_DEAL_DIVIDER = 2; //AA:매입청구, AC: 매입취소 청구
	protected final static int LEN_CARD_NO = 16; //카드번호
	protected final static int LEN_APPROVAL_NO = 8; //승인번호
	protected final static int LEN_DEAL_AMOUNT = 12; //거래금액
	protected final static int LEN_VAT = 9;//봉사료
	protected final static int LEN_TAX = 9; //세액
	protected final static int LEN_IPP_PERIOD = 2;//할부기간
	protected final static int LEN_INPUT_TYPE = 1;//Keyin/Swipe S:SWIPE, K:KEYIN
	protected final static int LEN_PURCHASER_CODE = 4;//매입사 코드
	protected final static int LEN_MERCHANT_NO = 15; //가맹점번호
	protected final static int LEN_MERCHANT_CORP_NO = 10; //가맹점 사업자번호
	protected final static int LEN_BILLING_DATE = 8; //YYYYMMDD (VAN사에서 카드사로 매입청구 일자)
	protected final static int LEN_DEAL_TYPE = 1; //C-단말기 거래, T-전화승인 등록, P-온라인P/G 거래
	protected final static int LEN_FILLER = 9;
	
	
	private String divider; // 레코드 구분 : 1 
	private String posNo;// 단말기번호 : 15 
	private String saleDate;// 매출 일자 : 8 
	private String saleTime;// 매출 시간 : 6 
	private String dealDivider; // 거래 유형(구분) : 2 (AA:매입청구, AC: 매입취소 청구) 
	private String cardNo;//카드번호 : 16 
	private String approvalNo;//승인번호 : 8 
	private String dealAmount;//거래금액 : 12 
	private String vat;// 봉사 : 9 (부가세)  
	private String tax;//세액 ㅣ 9 
	private String ippPeriod;//할부기간 : 2 
	private String inputType;// 입력 구분 : 1 ( S:SWIPE, K:KEYIN ) 
	private String purchaserCode;//매입사코드 : 4 
	private String merchantNo;//가맹점번호 : 15 
	private String merchantCorpNo;//가맹점사업자번호 : 10 
	private String billingDate;// 청구일자 : 8 ( YYYYMMDD , VAN사에서 카드사로 매입청구 일자)
	private String dealType;//거래종류구분 : 1 ( C-단말기 거래, T-전화승인 등록, P-온라인P/G 거래 ) 
	private String filler; // filler 
	
	
	public InvoiceDataRecord(){}
	
	public InvoiceDataRecord(byte[] record)
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
					, LEN_SALE_TIEM);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM
					, LEN_DEAL_DIVIDER);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER
					, LEN_CARD_NO);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					, LEN_APPROVAL_NO);
			
			this.dealAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO
					, LEN_DEAL_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT
					, LEN_VAT);
			
			this.tax = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					, LEN_TAX);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX
					, LEN_IPP_PERIOD);
			
			this.inputType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD
					, LEN_INPUT_TYPE);
			
			this.purchaserCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					, LEN_PURCHASER_CODE);
			
			this.merchantNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					+ LEN_PURCHASER_CODE
					, LEN_MERCHANT_NO);
			
			this.merchantCorpNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					+ LEN_PURCHASER_CODE + LEN_MERCHANT_NO
					, LEN_MERCHANT_CORP_NO);
			
			this.billingDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					+ LEN_PURCHASER_CODE + LEN_MERCHANT_NO
					+ LEN_MERCHANT_CORP_NO
					, LEN_BILLING_DATE);
			
			this.dealType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					+ LEN_PURCHASER_CODE + LEN_MERCHANT_NO
					+ LEN_MERCHANT_CORP_NO + LEN_BILLING_DATE
					, LEN_DEAL_TYPE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_POS_NO + LEN_SALE_DATE 
					+ LEN_SALE_TIEM + LEN_DEAL_DIVIDER + LEN_CARD_NO
					+ LEN_APPROVAL_NO + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_IPP_PERIOD + LEN_INPUT_TYPE
					+ LEN_PURCHASER_CODE + LEN_MERCHANT_NO
					+ LEN_MERCHANT_CORP_NO + LEN_BILLING_DATE
					+ LEN_DEAL_TYPE
					, LEN_FILLER);
			
			result = true;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

	@Override
	public int validate() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getDivider() 
	{
		return divider;
	}

	public String getPosNo() 
	{
		return posNo;
	}

	public String getSaleDate() 
	{
		return saleDate;
	}

	public String getSaleTime() 
	{
		return saleTime;
	}

	public String getDealDivider() 
	{
		return dealDivider;
	}

	public String getCardNo() 
	{
		return cardNo;
	}

	public String getApprovalNo() 
	{
		return approvalNo;
	}

	public String getDealAmount() 
	{
		return dealAmount;
	}

	public String getVat() 
	{
		return vat;
	}

	public String getTax() 
	{
		return tax;
	}

	public String getIppPeriod() 
	{
		return ippPeriod;
	}

	public String getInputType() 
	{
		return inputType;
	}

	public String getPurchaserCode() 
	{
		return purchaserCode;
	}

	public String getMerchantNo() 
	{
		return merchantNo;
	}

	public String getMerchantCorpNo() 
	{
		return merchantCorpNo;
	}

	public String getBillingDate() 
	{
		return billingDate;
	}

	public String getDealType() 
	{
		return dealType;
	}
	
	public String getFiller()
	{
		return filler;
	}
	
}
