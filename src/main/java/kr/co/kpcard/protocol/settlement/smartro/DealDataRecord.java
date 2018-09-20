package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class DealDataRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_NO = 6; 
	protected final static int LEN_SERVICE_DIVIDER = 1;
	protected final static int LEN_SALE_DATE = 8;
	protected final static int LEN_SALE_TIME = 6;
	protected final static int LEN_ISSUER_CODE = 4;
	protected final static int LEN_PURCHASER_CODE = 4;
	protected final static int LEN_DEAL_CODE = 2;
	protected final static int LEN_CARD_NO = 20;
	protected final static int LEN_APPROVAL_NO = 12;
	protected final static int LEN_IPP_PERIOD = 2;
	protected final static int LEN_DEAL_AMOUNT = 9;
	protected final static int LEN_VAT = 9;
	protected final static int LEN_TAX = 9;
	protected final static int LEN_SMARTRO_RES_CODE = 2;
	protected final static int LEN_ORIGIN_DEAL_DATE = 8;
	protected final static int LEN_POS_NO = 10;
	protected final static int LEN_OIL_CODE = 4;
	protected final static int LEN_RES_MSG01 = 30;
	protected final static int LEN_RES_MSG02 = 30;
	protected final static int LEN_MERCHANT_USE_AREA = 20;
	protected final static int LEN_FILLER = 3;
	
	// 카드 유효기간은 카드사 정책에 따라 보관하지 않음 
	// 카드 번호는 6바이트 마스킹 처리. 
	
	private String divier; // 레고 구분 : 1 ("D") 
	private String no;// 레코더 번호 : 6 
	private String serviceDivider;//서비스 구분 : 1 ("M":신용카드 / "I":IC카드 / "B":보너스카드 / "E":전자상품권 / "C":현금영수증 / "D":직불 )
	private String saleDate;//매출일자 : 8 
	private String saleTime;//매출시간: 6 
	private String issuerCode;//발급사코드 : 4 
	private String purchaserCode;//매입사코드 : 4 
	private String dealCode;//거래코드 :2 
	private String cardNo;//카드번호 : 20 
	private String approvalNo;//승인번호 : 12 
	private String ippPeriod;//할부기간 : 2 
	private String dealAmount;//거래금액 : 9 
	private String vat;//봉사료 : 9 
	private String tax;//세금 : 9 
	private String smartroResCode;//스마트로 응답코드 : 2 
	private String originDealDate;//원거래일자 : 8 
	private String posNo;//단말기번호 : 10 
	private String oilCode;//유종코드 :4 
	private String resMsg01;//응답메세지1 : 30 
	private String resMsg02;//응답메세지2 : 30 
	private String merchantUseArea;//가맹점 사용영역 : 20 
	private String filler; //filler : 3 
	
	
	public DealDataRecord(){}
	
	public DealDataRecord(byte[] record)
	{
		this.pasre(record);
	}
	
	@Override
	public boolean pasre(byte[] record) 
	{
		// TODO Auto-generated method stub
		boolean result =false;
		
		try 
		{
			this.divier = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.no = StringUtil.copyToString(record
					, LEN_DIVIDER
					, LEN_NO);
			
			this.serviceDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO
					, LEN_SERVICE_DIVIDER);
			
			this.saleDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER
					, LEN_SALE_DATE);

			this.saleTime = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE
					, LEN_SALE_TIME);

			this.issuerCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME
					, LEN_ISSUER_CODE);

			this.purchaserCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE
					, LEN_PURCHASER_CODE);
			
			this.dealCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					, LEN_DEAL_CODE);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE
					, LEN_CARD_NO);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO
					, LEN_APPROVAL_NO);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					, LEN_IPP_PERIOD);
			
			this.dealAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD
					, LEN_DEAL_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT
					, LEN_VAT);
			
			this.tax = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					, LEN_TAX);
			
			this.smartroResCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX
					, LEN_SMARTRO_RES_CODE);
			
			this.originDealDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE
					, LEN_ORIGIN_DEAL_DATE);
			
			this.posNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					, LEN_POS_NO);
			
			this.oilCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					+ LEN_POS_NO
					, LEN_OIL_CODE);
			
			this.resMsg01 = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					+ LEN_POS_NO + LEN_OIL_CODE
					, LEN_RES_MSG01);
			
			this.resMsg02 = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					+ LEN_POS_NO + LEN_OIL_CODE + LEN_RES_MSG01
					, LEN_RES_MSG02);
			
			this.merchantUseArea = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					+ LEN_POS_NO + LEN_OIL_CODE + LEN_RES_MSG01
					+ LEN_RES_MSG02
					, LEN_MERCHANT_USE_AREA);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_NO + LEN_SERVICE_DIVIDER + LEN_SALE_DATE 
					+ LEN_SALE_TIME + LEN_ISSUER_CODE + LEN_PURCHASER_CODE
					+ LEN_DEAL_CODE + LEN_CARD_NO + LEN_APPROVAL_NO
					+ LEN_IPP_PERIOD + LEN_DEAL_AMOUNT + LEN_VAT
					+ LEN_TAX + LEN_SMARTRO_RES_CODE + LEN_ORIGIN_DEAL_DATE
					+ LEN_POS_NO + LEN_OIL_CODE + LEN_RES_MSG01
					+ LEN_RES_MSG02 + LEN_MERCHANT_USE_AREA
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
	
	
	public String getDivier() 
	{
		return divier;
	}
	
	public String getNo() 
	{
		return no;
	}
	
	public String getServiceDivider() 
	{
		return serviceDivider;
	}
	
	public String getSaleDate() 
	{
		return saleDate;
	}
	
	public String getSaleTime() 
	{
		return saleTime;
	}
	
	public String getIssuerCode() 
	{
		return issuerCode;
	}
	
	public String getPurchaserCode() 
	{
		return purchaserCode;
	}
	
	public String getDealCode() 
	{
		return dealCode;
	}
	
	public String getCardNo()
	{
		return cardNo;
	}
	
	public String getApprovalNo()
	{
		return approvalNo;
	}
	
	public String getIppPeriod() 
	{
		return ippPeriod;
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
	
	public String getSmartroResCode() 
	{
		return smartroResCode;
	}
	
	public String getOriginDealDate() 
	{
		return originDealDate;
	}
	
	public String getPosNo() 
	{
		return posNo;
	}
	
	public String getOilCode() 
	{
		return oilCode;
	}
	
	public String getResMsg01() 
	{
		return resMsg01;
	}
	
	public String getResMsg02() 
	{
		return resMsg02;
	}
	
	public String getMerchantUseArea() 
	{
		return merchantUseArea;
	}
	
	public String getFiller() 
	{
		return filler;
	}
	
	@Override
	public String toString() 
	{
		return String.format(
				"DealDataRecord [divier=%s, no=%s, serviceDivider=%s"
				+ ", saleDate=%s, saleTime=%s, issuerCode=%s"
				+ ", purchaserCode=%s, dealCode=%s, cardNo=%s"
				+ ", approvalNo=%s, ippPeriod=%s, dealAmount=%s"
				+ ", vat=%s, tax=%s, smartroResCode=%s, originDealDate=%s"
				+ ", posNo=%s, oilCode=%s, resMsg01=%s"
				+ ", resMsg02=%s, merchantUseArea=%s, filler=%s]",
				divier, no, serviceDivider
				, saleDate, saleTime, issuerCode
				, purchaserCode, dealCode, cardNo
				, approvalNo, ippPeriod, dealAmount
				, vat, tax, smartroResCode
				, originDealDate, posNo, oilCode
				, resMsg01, resMsg02, merchantUseArea
				, filler);
	}

	
	
	
}
