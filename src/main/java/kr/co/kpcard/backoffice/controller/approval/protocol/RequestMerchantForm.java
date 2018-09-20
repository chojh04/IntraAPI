package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * 대표 거래처 [등록],[수정] 승인 요청 폼
 * Created by @author : MinWook on 2018. 5. 28.
 *
 */
@Getter
@Setter
public class RequestMerchantForm {
	
	
	//아래부터 거래처 기본 정보
	
	/** 거래처 아이디 */
	private String merchantId;
	
	/** 거래처명 */
	private String name;
	
	/** 거래처 약칭 */
	private String alias;
	
	/** 사용여부 */
	private String useFlag;
	
	//아래부터 거래처 상세 정보
	
	/** 업종 */
	private String bizKind;
	
	/** 업태 */
	private String bizCond;

	/** 거래처구분 */
	private String bizGrp;

	/** 법인등록번호 */
	private String corpRegNo;

	/** 사업자등록번호 */
	private String bizRegNo;
	
	/** 개업일 */
	private String openDate;
	
	/** 대표 이름 */
	private String ceoName;

	/** 우편번호 */
	private String zipCode;
	
	/** 주소 */
	private String address;
	/** 상세 주소 */
	
	private String addressDetail;
	/** 연락처 */
	
	private String tel;
	/** 팩스번호 */
	private String fax;
	
	//아래부터 승인 요청 정보
	
	/** 신청자 사번 */
	private String reqEmpId;
	
	/** 승인자 사번 */
	private String apprEmpId;
	
	/** 요청 사유 */
	private String reqMemo;

}
