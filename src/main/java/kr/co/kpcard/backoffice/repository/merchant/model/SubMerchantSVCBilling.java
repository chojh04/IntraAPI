package kr.co.kpcard.backoffice.repository.merchant.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * KPC_ADMIN 데이터베이스
 * SUBMERCHANT_SERVICE_BILLING 테이블 모델 
 * Created by @author : MinWook on 2018. 7. 6.
 *
 */
@Data
public class SubMerchantSVCBilling {

	/**
	 * 정산 아이디
	 */
	private String serviceBillingId;
	
	/**
	 * 정산 수수료 정보
	 */
	private SubMerchantCommision commision;
	
	/**
	 * 서비스 ID
	 */
	private String serviceId;
	/**
	 * 정산명
	 */
	private String name;
	/**
	 * 정산코드(선입금, 사용분정산..)
	 */
	private String code;
	/**
	 * 정산타입(거래일/영업일)
	 */
	private String type;
	/**
	 * 정산구분(입금/출금)
	 */
	private String divider;

	/**
	 * 은행명
	 */
	private String bankName;
	/**
	 * 은행 계좌번호
	 */
	private String bankAccountNo;
	/**
	 * 은행 예금주
	 */
	private String bankHolder;
	/**
	 * 가상 은행명
	 */
	private String vrBankName;
	/**
	 * 가상 계좌번호
	 */
	private String vrBankAccountNo;
	
	/**
	 * 당사 배분율
	 */
	private Long shareRate;
	/**
	 * 미수금
	 */
	private BigDecimal unpaidAmount;
	/**
	 * 한도 제한금
	 */
	private BigDecimal limitsAmount;
	/**
	 * 여신 설정 금액
	 */
	private BigDecimal creditSettingAmount; 
	
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
	 * 삭제 여부
	 */
	private String delFlag;

	/**
	 * 등록일
	 */
	private Date createDate;
	/**
	 * 등록 사유
	 */
	private String createDesc;
	/**
	 * 등록자 사번
	 */
	private String createAdmId;

	/**
	 * 수정일
	 */
	private Date updateDate;
	/**
	 * 수정 사유
	 */
	private String updateDesc;
	/**
	 * 수정자 사번
	 */
	private String updateAdmId;
}
