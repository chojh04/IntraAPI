package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Data;

/**
 * 거래처 서비스 [등록], [수정] 승인 요청 폼
 * Created by @author : MinWook on 2018. 7. 5.
 *
 */
@Data
public class RequestSubMerchantServiceForm {
	
	/** 일반거래처 아이디 */
	private String subMerchantId;
	
	/** 서비스 아이디 */
	private String serviceId;
	
	/**	서비스명 */
	private String serviceName;
	/**	서비스 카테고리 */
	private String category;
	/**	서비스 타입 */
	private String type;
	/**	매출 구분 */
	private String saleDivider;
	/**	사용 구분 */
	private String useFlag;
	/** 서비스 연동 아이디 */
	private String svcConnId;
	/** 서비스 연동 아이디 패스워드 */
	private String svcConnPw;
	
	/**	생성 사유 */
	private String createDesc;
	/**	생성자 사번 */
	private String createAdmId;
	
	/**	수정 사유 */
	private String updateDesc;
	/**	수정자 아이디 */
	private String updateAdmId;
	
	/**	승인신청자 사번 */
	private String reqEmpId;
	/**	승인요청 사유 */
	private String reqMemo;
	/**	승인자 사번 */
	private String apprEmpId;

}
