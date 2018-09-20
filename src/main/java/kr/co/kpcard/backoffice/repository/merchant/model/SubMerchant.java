package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Data;

/**
 * KPC_ADMIN 데이터베이스
 * SUBMERCHANT 테이블 모델 
 * Created by @author : MinWook on 2018. 7. 2.
 *
 */
@Data
public class SubMerchant {

	/** 일반 거래처 아이디 **/
	private String subMerchantId;
	
	/** ERP 코드 **/
	private String erpCode;
	
	/** 거래처 depth **/
	private String depth;
	
	/** 상위 거래처 아이디 **/
	private String parentId; 
	/** 하위 거래처 아이디 **/
	private String childId;
	
	/** 거래처 명 **/
	private String name;
	/** 거래처 영문명 **/
	private String nameEng; 
	/** 거래처 약칭 **/
	private String alias;
	/** 거래처 약칭 영문명 **/
	private String aliasEng;
	
	/** 사용 여부 **/
	private String useFlag;
	/** 재사용 여부 **/
	private String reUseFlag;
	/** 삭제 여부 **/
	private String delFlag;
	
	/** PG 아이디 **/
	private String pgId;
	/** PG 비밀번호 **/
	private String pgPw;
	
	/** 관리자 아이디 **/
	private String agentId;
	/** 관리자 비밀번호 **/
	private String agentPw;

	/** 등록 사유 **/
	private String createDesc;
	/** 등록일시 **/
	private Date createDate;
	/** 수정 사유 **/
	private String updateDesc;
	/** 수정 일시 **/
	private Date updateDate;
	
	/**	일반 거래처 상세정보 */
	private SubMerchantDetail detail;
	
}
