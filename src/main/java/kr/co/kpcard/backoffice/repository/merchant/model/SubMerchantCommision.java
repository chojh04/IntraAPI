package kr.co.kpcard.backoffice.repository.merchant.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class SubMerchantCommision {
	
	/**
	 * 수수료 ID
	 */
	private Long id;
	
	/**
	 * 서비스 정산 ID
	 */
	private String serviceBillingId;
	
	/**
	 * 수수료 적용 시작일
	 */
	private String billingStartDate;
	/**
	 * 수수료 적용 종료일
	 */
	private String billingEndDate;
	
	/**
	 * 정산일
	 */
	private String billingDate;
	/**
	 * 정산주기
	 */
	private String billingDuration; 
	
	/**
	 * 정산작업용 수수료 구분(건별/총액)
	 */
	private String billingCommisionType;
	
	/**
	 * 거래처 수수료 타입
	 */
	private String merchantCommisionType;
	/**
	 * 거래처 수수료
	 */
	private BigDecimal merchantCommision;
	/**
	 * 거래처 부가세 타입
	 */
	private String merchantTaxType;
	/**
	 * 거래처 부가세
	 */
	private BigDecimal merchantTax;
	
	/**
	 * KPC 수수료 타입
	 */
	private String kpcCommisionType;
	/**
	 * KPC 수수료
	 */
	private BigDecimal kpcCommision;
	/**
	 * KPC 부가세 타입
	 */
	private String kpcTaxType;
	/**
	 * KPC 부가세
	 */
	private BigDecimal kpcTax;
	
	/**
	 * 추가 수수료타입01
	 */
	private String addCommisionType01;
	/**
	 * 추가 수수료01
	 */
	private BigDecimal addCommision01;
	/**
	 * 추가 부가세 타입01
	 */
	private String addTaxType01;
	/**
	 * 추가 부가세01
	 */
	private BigDecimal addTax01;
	
	/**
	 * 추가 수수료타입02
	 */
	private String addCommisionType02;
	/**
	 * 추가 수수료02
	 */
	private BigDecimal addCommision02;
	/**
	 * 추가 부가세 타입02
	 */
	private String addTaxType02;
	/**
	 * 추가 부가세02
	 */
	private BigDecimal addTax02;
		
	/**
	 * 생성일
	 */
	private Date createDate;
	/**
	 * 생성자 사번
	 */
	private String createAdmId;

	/**
	 * 수정일
	 */
	private Date updateDate;
	/**
	 * 수정자 사번
	 */
	private String updateAdmId;
	
}
