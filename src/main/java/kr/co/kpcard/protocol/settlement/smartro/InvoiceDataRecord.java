package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;

public class InvoiceDataRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_ISSUER_DIVIDER = 4;
	protected final static int LEN_NO = 6; 
	protected final static int LEN_LAYER_DIVIDER = 3; 
	protected final static int LEN_CARD_NO = 20;
	protected final static int LEN_EXPIRE_DATE = 4;
	protected final static int LEN_CVV = 3; 
	protected final static int LEN_SALE_DATE = 8;
	protected final static int LEN_ORIGIN_SALE_DATE = 8;
	protected final static int LEN_APPROVAL_NO = 10;
	protected final static int LEN_CUR_CODE = 3;
	protected final static int LEN_CUR_IDX = 1;
	protected final static int LEN_SALE_AMOUNT = 9;
	protected final static int LEN_VAT = 9;
	protected final static int LEN_TOTAL_SALE_AMOUNT = 9;
	protected final static int LEN_IPP_PERIOD = 2;
	protected final static int LEN_POS_NO = 10;
	protected final static int LEN_DEAL_NO = 4;
	protected final static int LEN_DEAL_DIVIDER = 2;
	protected final static int LEN_REF_NO = 14;
	protected final static int LEN_INPUT_TYPE = 1;
	protected final static int LEN_OIL_CODE = 4;
	protected final static int LEN_MERCHANT_USE_AREA = 20;
	protected final static int LEN_CUSTOMER_INFO = 48;
	protected final static int LEN_FILLER = 48;
	
	private String divider; // 레코드 구분 :2 
	private String issuerCode; // 발급사 기관 코드 :4 
	private String no; // 레코드 번호 :6
	private String layerDivider;  // 층구분 : 3 ("000")
	private String cardNo; // 카드 번호 : 20 
	private String expireDate; // 유효기간 : 4 
	private String cvv; // cvv :3 
	private String saleDate; // 매출(취소) 일자 : 6
	private String originSaleDate; // 당초 매출 일자 : 6
	private String approvalNo; // 승인번호 : 10 
	private String currencyCode; // 통화코드 : 3 
	private String currencyIndex; // 통화지수 : 1 
	private String saleAmount; // 순매출액 : 9 
	private String vat; // 봉사료 : 9 
	private String totalSaleAmount; // 매출액 : 9 
	private String ippPeriod; // 할부기간 : 2 
	private String posNo; // 매장번호 : 10 
	private String dealNo; // 거래번호 : 4 (매출전표에 인쇄되는 거래 번호) 
	private String dealDivider; // 거래구분 : 2 (00:정상 / 01:통판/ 02:무이자)
	private String referenceNo; // 참조 번호 : 14 
	private String inputType; // 거래 형태 : 1 (1스와이프, 2:키인/3:전화등록) 
	private String oilCode; // 유종 코드 : 4
	private String merchantUseArea; // 가맹점 사용 정보 : 20 
	private String customerInfo; // 고객 정보 : 48
	private String filler;
	
	
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
			
			this.currencyCode = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CUR_CODE);
			
			this.currencyIndex = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE
					, LEN_CUR_IDX);
			
			this.saleAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					, LEN_SALE_AMOUNT);
			
			this.vat = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT
					, LEN_VAT);
			
			this.totalSaleAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT
					, LEN_TOTAL_SALE_AMOUNT);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					, LEN_IPP_PERIOD);
			
			this.posNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD
					, LEN_POS_NO);
			
			this.dealNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO
					, LEN_DEAL_NO);
			
			this.dealDivider = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					, LEN_DEAL_DIVIDER);
			
			this.referenceNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER
					, LEN_REF_NO);
			
			this.inputType = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER + LEN_REF_NO
					, LEN_INPUT_TYPE);
			
			this.oilCode = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER + LEN_REF_NO + LEN_INPUT_TYPE
					, LEN_OIL_CODE);
			
			this.merchantUseArea = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER + LEN_REF_NO + LEN_INPUT_TYPE
					+ LEN_OIL_CODE
					, LEN_MERCHANT_USE_AREA);
			
			this.customerInfo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER + LEN_REF_NO + LEN_INPUT_TYPE
					+ LEN_OIL_CODE + LEN_MERCHANT_USE_AREA
					, LEN_CUSTOMER_INFO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_AMOUNT + LEN_VAT + LEN_TOTAL_SALE_AMOUNT
					+ LEN_IPP_PERIOD + LEN_POS_NO + LEN_DEAL_NO
					+ LEN_DEAL_DIVIDER + LEN_REF_NO + LEN_INPUT_TYPE
					+ LEN_OIL_CODE + LEN_MERCHANT_USE_AREA + LEN_CUSTOMER_INFO
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
	
	public String getIssuerCode() 
	{
		return issuerCode;
	}
	
	public String getNo() 
	{
		return no;
	}
	
	public String getLayerDivider() 
	{
		return layerDivider;
	}
	
	public String getCardNo() 
	{
		return cardNo;
	}
	
	public String getExpireDate() 
	{
		return expireDate;
	}
	
	public String getCvv() 
	{
		return cvv;
	}
	
	public String getSaleDate() 
	{
		return saleDate;
	}
	
	public String getOriginSaleDate() 
	{
		return originSaleDate;
	}
	
	public String getApprovalNo() 
	{
		return approvalNo;
	}
	
	public String getCurrencyCode() 
	{
		return currencyCode;
	}
	
	public String getCurrencyIndex() 
	{
		return currencyIndex;
	}
	
	public String getSaleAmount() 
	{
		return saleAmount;
	}
	
	public String getVat()
	{
		return vat;
	}
	
	public String getTotalSaleAmount() 
	{
		return totalSaleAmount;
	}
	
	public String getIppPeriod() 
	{
		return ippPeriod;
	}
	
	public String getPosNo() 
	{
		return posNo;
	}
	
	public String getDealNo() 
	{
		return dealNo;
	}
	
	public String getDealDivider() 
	{
		return dealDivider;
	}
	
	public String getReferenceNo() 
	{
		return referenceNo;
	}
	
	public String getInputType() 
	{
		return inputType;
	}
	
	public String getOilCode() 
	{
		return oilCode;
	}
	
	public String getMerchantUseArea() 
	{
		return merchantUseArea;
	}
	
	public String getCustomerInfo() 
	{
		return customerInfo;
	}
	
	public String getFiller()
	{
		return filler;
	}
	
	@Override
	public String toString() 
	{
		return String.format(
				"InvoiceDataRecord [divider=%s, issuerCode=%s, no=%s"
				+ ", layerDivider=%s, cardNo=%s, expireDate=%s"
				+ ", cvv=%s, saleDate=%s, originSaleDate=%s"
				+ ", approvalNo=%s, currencyCode=%s, currencyIndex=%s"
				+ ", saleAmount=%s, vat=%s, totalSaleAmount=%s"
				+ ", ippPeriod=%s, posNo=%s, dealNo=%s"
				+ ", dealDivider=%s, referenceNo=%s, inputType=%s"
				+ ", oilCode=%s, merchantUseArea=%s, customerInfo=%s]"
				, divider, issuerCode, no
				, layerDivider, cardNo, expireDate
				, cvv, saleDate, originSaleDate
				, approvalNo, currencyCode, currencyIndex
				, saleAmount, vat, totalSaleAmount
				, ippPeriod, posNo, dealNo
				, dealDivider, referenceNo, inputType
				, oilCode, merchantUseArea, customerInfo);
	}
	
	
	
}
