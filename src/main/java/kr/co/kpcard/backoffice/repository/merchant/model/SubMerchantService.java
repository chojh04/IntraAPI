package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Data;

/**
 * KPC_ADMIN 데이터베이스
 * SUBMERCHANT_SERVICE 테이블 모델 
 * Created by @author : MinWook on 2018. 7. 4.
 *
 */
@Data
public class SubMerchantService {
	
	/**	서비스 ID */
	private String serviceId;
	/**	거래처 ID */
	private String subMerchantId;
	
	/**	서비스 명 */
	private String name;
	/**	서비스 카테고리 */
	private String category;
	/**	서비스 타입 */
	private String type;
	/**	사용 여부 */
	private String useFlag;
	/**	삭제 여부 */
	private String delFlag;
	
	/**	서비스 연동 인증 타입 */
	private String svcAuthType;
	/**	서비스 연동 아이디 */
	private String svcConnId;
	/**	서비스 연동 패스워드 */
	private String svcConnPw;
	
	/**	매출 카테고리 */
	private String saleCategory;
	/**	매출 구분 */
	private String saleDivider;
	
	/**	등록 일시 */
	private Date createDate;
	/**	등록 사유 */
	private String createDesc;
	/**	등록자 아이디 */
	private String createAdmId;
	
	/**	수정 일시 */
	private Date updateDate;
	/**	수정 사유 */
	private String updateDesc;
	/**	수정자 아이디 */
	private String updateAdmId;
	
	/**	거래처 및 온라인결제 매핑코드(MERT/OPAY) */
	private String svcAlias;
	
	/**	직가맹점 여부 */
	private String frcFlag;
	/**	직가맹점 변경사유 */
	private String frcDesc;

}
