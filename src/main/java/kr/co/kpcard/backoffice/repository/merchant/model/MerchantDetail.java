package kr.co.kpcard.backoffice.repository.merchant.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * KPC_ADMIN 테이터베이스 
 * MERCHANT_DEATAIL 테이블 모델 
 * Created by @author : MinWook on 2018. 6. 1.
 *
 */
@Getter
@Setter
public class MerchantDetail {
	
	/** 거래처 아이디 */
	private String merchantId;
	
	/** 업종 */
	private String bizKind;
	
	/** 업태 */
	private String bizCond;

	/** 가맹점 구분(개인/법인) */
	private String bizGrp;

	/** 법인번호 */
	private String corpRegNo;
	/** 사업자 번호 */
	private String bizRegNo;
	
	/** 개업일 */
	private String openDate;
	/** 폐업일 */
	private String closeDate;
	/** 폐업 여부 */
	private String closedFlag;
	
	/** 자본금 */
	private String capitalAmount;
	
	/** 직원 수 */
	private String employeeCnt;
	/** 매장 수 */
	private String storeCnt;
	
	/** 월 평균 매출 */
	private BigDecimal avgSaleMonth;
	/** 연 평균 매출 */
	private BigDecimal avgSaleYear;
	
	/** 대표 성명 */
	private String ceoName;
	
	/** 우편 번호 */
	private String zipCode;
	/** 주소 */
	private String address;
	/** 상세주소 */
	private String addressDetail;
	
	/** 연락처 */
	private String tel;
	/** 보조 연락처 */
	private String telSub;
	/** 팩스번호 */
	private String fax;
	/** 보조 팩스 번호 */
	private String faxSub;
		
}
