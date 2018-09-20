package kr.co.kpcard.backoffice.repository.merchant.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * KPC_ADMIN 데이터베이스
 * MERCHANT 테이블 모델 
 * Created by @author : MinWook on 2018. 6. 1.
 *
 */
@Getter
@Setter
public class Merchant {

	/** 거래처 아이디 */
	private String merchantId;
	/** ERP 코드 */
	private String erpCode;
	
	/** 거래처명 */
	private String name;
	/** 거래처 영문명 */
	private String nameEng;
	
	/** 거래처 약칭 */
	private String alias;
	/** 거래처 약칭 영문명 */
	private String aliasEng;

	/** 사용 여부 */
	private String useFlag;
	/** 삭제 여부 */
	private String delFlag;
	
	/** 생성 일시 */
	private Date createDate;
	/** 갱신 일시 */
	private Date updateDate;
	/** 생성 사유 */
	private String createDesc;
	/** 갱신 사유 */
	private String updateDesc;
	
	/**
	 * 거래처 부가 정보 객체
	 */
	private MerchantDetail detail;
	
}
