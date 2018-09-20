package kr.co.kpcard.protocol.settlement.nice;

import ch.qos.logback.classic.Logger;
import kr.co.kpcard.common.utils.StringUtil;

public class DepositDataRecord implements RecordInterface{

	protected final static int LEN_SIZE = 165;
	protected final static int LEN_DIVIDER = 2;
	protected final static int LEN_CUR_CODE = 3;
	protected final static int LEN_CUR_IDX = 1;
	protected final static int LEN_SALE_DATE = 6;
	protected final static int LEN_RECEIPT_DATE = 6;
	protected final static int LEN_CARD_NO = 19;
	protected final static int LEN_IPP_PERIOD = 2;
	protected final static int LEN_SALE_AMOUNT = 10;
	protected final static int LEN_RET_CODE_CARD_CORP = 4;
	protected final static int LEN_RET_CODE_VAN_CORP = 2;
	protected final static int LEN_PERSONAL_ID = 13;
	protected final static int LEN_NAME_KOR = 10;
	protected final static int LEN_NAME_ENG = 12;
	protected final static int LEN_OFFICE_TEL_NUM = 28;
	protected final static int LEN_HOME_TEL_NUM = 20;
	protected final static int LEN_APPROVAL_NO = 10;
	protected final static int LEN_FILLER_01 = 4;
	protected final static int LEN_FILLER_02 = 13;
	
	
	private String divider ; // 레코드 구분 : 2 (60-일반매입 정상 / 61-일반매입 반송 / 62-일반매입 보류 / 63-일반매입 보류해제 /64-취소매입 반송 / 65-취소매입 보류 / 66-취소매입 보류해제 /67-취소매입 정상)
	private String currencyCode; // 통화 코드 : 3 ("410") 
	private String currencyIndex; // 통화 지수 : 1 ("0")
	private String saleDate;//매출일자(취소일자) : 6 
	private String receiptDate;//접수일자 : 6 
	private String cardNo; // 카드번호 : 19 
	private String ippPeriod; // 할부기간 : 2 
	private String saleAmount;//판매금액 : 10 
	private String returnCodeOfCardCorp; // 카드사 반송코드 : 4 
	private String returnCodeOfVanCorp; // 나이스 반송코드 : 2 
	private String personalId; // 주민등록번호 : 13 
	private String nameKor; // 한글 성명 : 10 
	private String nameEng; // 영문 성명 : 12 
	private String officeTelNum; // 직장 전화 번호 : 28 
	private String homeTelNum; // 자택전화번호 : 20 
	private String approvalNo; // 승인번호 : 10 
	private String filler01; // filler01 : 4 
	private String filler02; // filler02 : 13 
	
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
			
			this.returnCodeOfVanCorp = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					, LEN_RET_CODE_VAN_CORP);
			
			this.personalId = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP
					, LEN_PERSONAL_ID);
			
			this.nameKor = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID
					, LEN_NAME_KOR);
			
			this.nameEng = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					, LEN_NAME_ENG);
			
			this.officeTelNum = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					+ LEN_NAME_ENG
					, LEN_OFFICE_TEL_NUM);
			
			this.homeTelNum = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					+ LEN_NAME_ENG + LEN_OFFICE_TEL_NUM 
					, LEN_HOME_TEL_NUM);
			
			this.approvalNo = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					+ LEN_NAME_ENG + LEN_OFFICE_TEL_NUM + LEN_HOME_TEL_NUM
					, LEN_APPROVAL_NO);
			
			this.filler01 = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					+ LEN_NAME_ENG + LEN_OFFICE_TEL_NUM + LEN_HOME_TEL_NUM
					+ LEN_APPROVAL_NO
					, LEN_FILLER_01);
			
			this.filler02 = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CUR_CODE + LEN_CUR_IDX
					+ LEN_SALE_DATE + LEN_RECEIPT_DATE + LEN_CARD_NO
					+ LEN_IPP_PERIOD + LEN_SALE_AMOUNT + LEN_RET_CODE_CARD_CORP
					+ LEN_RET_CODE_VAN_CORP + LEN_PERSONAL_ID + LEN_NAME_KOR
					+ LEN_NAME_ENG + LEN_OFFICE_TEL_NUM + LEN_HOME_TEL_NUM
					+ LEN_APPROVAL_NO + LEN_FILLER_01
					, LEN_FILLER_02);
			
			result = true;
		}
		catch (Exception e) {
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
	public String getReturnCodeOfVanCorp() 
	{
		return returnCodeOfVanCorp;
	}
	public String getPersonalId() 
	{
		return personalId;
	}
	public String getNameKor() {
		return nameKor;
	}
	public String getNameEng() {
		return nameEng;
	}
	public String getOfficeTelNum() {
		return officeTelNum;
	}
	public String getHomeTelNum() {
		return homeTelNum;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public String getFiller01() {
		return filler01;
	}
	public String getFiller02() {
		return filler02;
	}
}
