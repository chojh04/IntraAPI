package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.math.BigDecimal;
import java.util.Date;

import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * 거래처 응답 객체 (MerchantBasic + MerchantDetail)
 * Created by @author : MinWook on 2018. 6. 4.
 *
 */
@Getter
@Setter
public class ResponseMerchant {

	/* 아래부턴 거래처 Basic 정보 */

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
	
	/* 아래부턴 거래처 Detail 정보 */
	
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

	/** merchantId로 승인 요청 중인가?
	 *  true면 추가 승인요청 못해야함.
	 */
	private boolean isApprovalRequesting;
	
	public ResponseMerchant() { }

	public ResponseMerchant(Merchant basic, MerchantDetail detail) {

		//아래부터 거래처 Basic 정보	
		this.merchantId = basic.getMerchantId();
		this.erpCode = basic.getErpCode();
		this.name = basic.getName();
		this.nameEng = basic.getNameEng();
		this.alias = basic.getAlias();
		this.aliasEng = basic.getAliasEng();
		this.useFlag = basic.getUseFlag();
		this.delFlag = basic.getDelFlag();
		this.createDate = basic.getCreateDate();
		this.updateDate = basic.getUpdateDate();
		this.createDesc = basic.getCreateDesc();
		this.updateDesc = basic.getUpdateDesc();
					
		//아래부터 거래처 Detail 정보	
		this.bizKind = detail.getBizKind();
		this.bizCond = detail.getBizCond();
		this.bizGrp = detail.getBizGrp();
		
		this.corpRegNo = detail.getCorpRegNo();
		this.bizRegNo = detail.getBizRegNo();
		
		this.openDate = detail.getOpenDate();
		this.closeDate = detail.getCloseDate();
		this.closedFlag = detail.getClosedFlag();
		
		this.capitalAmount = detail.getCapitalAmount();
		this.employeeCnt = detail.getEmployeeCnt();
		this.storeCnt = detail.getStoreCnt();
		
		this.avgSaleMonth = detail.getAvgSaleMonth();
		this.avgSaleYear = detail.getAvgSaleYear();
		
		this.ceoName = detail.getCeoName();
		this.zipCode = detail.getZipCode();
		this.address = detail.getAddress();
		this.addressDetail = detail.getAddressDetail();
		
		this.tel = detail.getTel();
		this.telSub = detail.getTelSub();
		this.fax = detail.getFax();
		this.faxSub = detail.getFaxSub();
	}
	
}
