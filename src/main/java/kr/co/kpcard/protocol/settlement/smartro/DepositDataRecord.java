package kr.co.kpcard.protocol.settlement.smartro;

import kr.co.kpcard.common.utils.StringUtil;
import kr.co.kpcard.protocol.settlement.nice.RecordInterface;

public class DepositDataRecord implements RecordInterface{

	protected final static int LEN_SIZE = 200;
	protected final static int LEN_DIVIDER = 1;
	protected final static int LEN_CUR_CODE = 2; 
	protected final static int LEN_CUR_IDX = 1; 
	protected final static int LEN_SALE_DATE = 6;
	protected final static int LEN_RECEIPT_DATE = 6;
	protected final static int LEN_CARD_NO = 20;
	protected final static int LEN_IPP_PERIOD = 2;
	protected final static int LEN_SALE_AMOUNT = 10;
	protected final static int LEN_RET_CODE_CARD_CORP = 4;
	protected final static int LEN_RET_CODE_SMATRO_CORP = 2;
	protected final static int LEN_PERSONAL_ID = 13;
	protected final static int LEN_NAME_KOR = 10;
	protected final static int LEN_NAME_ENG = 10;
	protected final static int LEN_VAT_PER_DEAL = 10;
	protected final static int LEN_VAT_PER_FREE = 10;
	protected final static int LEN_DEPOSIT_AMOUNT = 10;
	protected final static int LEN_HOME_TEL_NUM = 20;
	protected final static int LEN_APPROVAL_NO = 8;
	protected final static int LEN_REF_NO = 6;
	protected final static int LEN_FILLER = 47;
	
	private String divider ;  // 레코드 구분 : 2 
	private String currencyCode;  // 통화코드  : 3
	private String currencyIndex; // 통화지수 : 1
	private String saleDate;     // 매출(취소)일자 : 6 
	private String receiptDate;  // 접수(취소) 일자 : 6 
	private String cardNo;    // 카드 번호 : 20 
	private String ippPeriod; // 할부기간 : 2
	private String saleAmount;// 신용판매 금액(거래금액) : 10 
	private String returnCodeOfCardCorp; // (카드사)반송사유코드 : 4 
	private String returnCodeOSamartoCorp; // 표준(스마트로) 반송사유코드 : 2 
	private String personalId; // 주민등록 번호 : 13 
	private String nameKor; // 성명(한글) : 10
	private String nameEng;  // 성명(영문) : 10
	private String vatPerDeal; // 기본 건별 수수료 :10 
	private String vatPerFree; // 무이자 수수료 :10 
	private String depositAmount; // 입금액 : 10 
	private String homeTelNum; // 자택전화번호 : 20 
	private String approvalNo; // 승인 번호 : 8 
	private String referenceNo; // 참조 번호 : 6 
	private String filler; // filler : 47 
	
	
	public DepositDataRecord(){}
	
	public DepositDataRecord(byte[] record)
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
			
			this.saleDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					, LEN_SALE_DATE);
			
			this.receiptDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE
					, LEN_RECEIPT_DATE);
			
			this.receiptDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE
					, LEN_RECEIPT_DATE);
			
			this.cardNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE
					, LEN_CARD_NO);
			
			this.ippPeriod = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					, LEN_IPP_PERIOD);
			
			this.saleAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD
					, LEN_SALE_AMOUNT);
			
			this.returnCodeOfCardCorp = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT
					, LEN_RET_CODE_CARD_CORP);
			
			this.returnCodeOSamartoCorp = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP
					, LEN_RET_CODE_SMATRO_CORP);
			
			this.personalId = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					, LEN_PERSONAL_ID);
			
			this.nameKor = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID
					, LEN_NAME_KOR);
			
			this.nameEng = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR
					, LEN_NAME_ENG);
			
			this.vatPerDeal = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					, LEN_VAT_PER_DEAL);
			
			this.vatPerFree = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL
					, LEN_VAT_PER_FREE);
			
			this.depositAmount = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL + LEN_VAT_PER_FREE
					, LEN_DEPOSIT_AMOUNT);
			
			this.homeTelNum = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL + LEN_VAT_PER_FREE + LEN_DEPOSIT_AMOUNT
					, LEN_HOME_TEL_NUM);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL + LEN_VAT_PER_FREE + LEN_DEPOSIT_AMOUNT
					+ LEN_HOME_TEL_NUM
					, LEN_APPROVAL_NO);
			
			this.referenceNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL + LEN_VAT_PER_FREE + LEN_DEPOSIT_AMOUNT
					+ LEN_HOME_TEL_NUM + LEN_APPROVAL_NO
					, LEN_REF_NO);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT 
					+ LEN_RET_CODE_CARD_CORP + LEN_RET_CODE_SMATRO_CORP
					+ LEN_PERSONAL_ID + LEN_NAME_KOR + LEN_NAME_ENG
					+ LEN_VAT_PER_DEAL + LEN_VAT_PER_FREE + LEN_DEPOSIT_AMOUNT
					+ LEN_HOME_TEL_NUM + LEN_APPROVAL_NO + LEN_REF_NO
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
	
	public String getCurrencyCode() 
	{
		return currencyCode;
	}
	
	public String getCurrencyIndex() 
	{
		return currencyIndex;
	}
	
	public String getSaleDate() 
	{
		return saleDate;
	}
	
	public String getReceiptDate() 
	{
		return receiptDate;
	}
	
	public String getCardNo() 
	{
		return cardNo;
	}
	
	public String getIppPeriod() 
	{
		return ippPeriod;
	}
	
	public String getSaleAmount() 
	{
		return saleAmount;
	}
	
	public String getReturnCodeOfCardCorp() 
	{
		return returnCodeOfCardCorp;
	}
	
	public String getReturnCodeOSamartoCorp() 
	{
		return returnCodeOSamartoCorp;
	}
	
	public String getPersonalId() 
	{
		return personalId;
	}
	
	public String getNameKor() 
	{
		return nameKor;
	}
	
	public String getNameEng()
	{
		return nameEng;
	}
	
	public String getVatPerDeal() 
	{
		return vatPerDeal;
	}
	
	public String getVatPerFree() 
	{
		return vatPerFree;
	}
	
	public String getDepositAmount() 
	{
		return depositAmount;
	}
	
	public String getHomeTelNum()
	{
		return homeTelNum;
	}
	
	public String getApprovalNo() 
	{
		return approvalNo;
	}
	
	public String getReferenceNo() 
	{
		return referenceNo;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"DepositDataRecord [divider=%s, currencyCode=%s, currencyIndex=%s"
				+ ", saleDate=%s, receiptDate=%s, cardNo=%s"
				+ ", ippPeriod=%s, saleAmount=%s, returnCodeOfCardCorp=%s"
				+ ", returnCodeOSamartoCorp=%s, personalId=%s, nameKor=%s"
				+ ", nameEng=%s, vatPerDeal=%s, vatPerFree=%s"
				+ ", depositAmount=%s, homeTelNum=%s, approvalNo=%s"
				+ ", referenceNo=%s, filler=%s]"
				, divider, currencyCode, currencyIndex
				, saleDate, receiptDate, cardNo, ippPeriod
				, saleAmount, returnCodeOfCardCorp, returnCodeOSamartoCorp
				, personalId, nameKor, nameEng
				, vatPerDeal, vatPerFree, depositAmount
				, homeTelNum, approvalNo, referenceNo
				, filler);
	}
	
	
}
