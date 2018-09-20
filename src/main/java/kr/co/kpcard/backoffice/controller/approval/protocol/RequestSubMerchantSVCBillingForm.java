package kr.co.kpcard.backoffice.controller.approval.protocol;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestSubMerchantSVCBillingForm {

	/**
	 * 정산 아이디
	 */
	private String serviceBillingId;
	/**
	 * 서비스 ID
	 */
	private String serviceId;
	/**
	 * 정산명
	 */
	private String name;
	
	/**
	 * 은행 코드.(DB 컬럼은 bankName) 
	 */
	private String bankCode;
	/**
	 * 은행 계좌번호
	 */
	private String bankAccountNo;
	/**
	 * 은행 예금주
	 */
	private String bankHolder;
	
	
	/**
	 * 담당자
	 */
	private String managerName;
	/**
	 * 담당자 연락처
	 */
	private String managerTel;
	/**
	 * 담당자 이메일
	 */
	private String managerEmail;
	
	/**
	 * KPC 담당자
	 */
	private String kpcManagerName;
	/**
	 * KPC 담당자 연락처
	 */
	private String kpcManagerTel;
	/**
	 * KPC 담당자 이메일
	 */
	private String kpcManagerEmail;
	
	/**
	 * 정산코드(선입금, 사용분정산..)
	 */
	private String code;
	/**
	 * 정산구분(입금/출금)
	 */
	private String divider;
	
	
	// 정산수수료 관리 --------------------------------------
	
	/**
	 * 수수료 ID 
	 */
	private Long commisionId;
	
	/**
	 * 정산일
	 */
	private String billingDate;
	/**
	 * 정산주기
	 */
	private String billingDuration; 

	/**
	 * 거래처 수수료 타입(비율/건당)
	 */
	private String merchantCommisionType;
	
	/**
	 * 거래처 수수료
	 */
	private BigDecimal merchantCommision;
	
	/**
	 * 수수료 적용 시작일
	 */
	private String billingStartDate;
	
	/**
	 * 정산작업용 수수료 구분(건별/총액)
	 */
	private String billingCommisionType;
	
	/**
	 * 거래처 부가세 타입
	 */
	private String merchantTaxType;
	
	/**
	 * 이전 수수료 정보의 적용 종료일자 
	 */
	private String beforeBillingEndDate;
	
	//----------승인정보--------------
	/**	승인신청자 사번 */
	private String reqEmpId;
	/**	승인요청 사유 */
	private String reqMemo;
	/**	승인자 사번 */
	private String apprEmpId;
	
}
