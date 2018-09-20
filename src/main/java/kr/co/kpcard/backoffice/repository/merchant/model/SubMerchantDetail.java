package kr.co.kpcard.backoffice.repository.merchant.model;

import lombok.Data;

/**
 * KPC_ADMIN 데이터베이스
 * SUBMERCHANT_DETAIL 테이블 모델 
 * Created by @author : MinWook on 2018. 7. 2.
 *
 */
@Data
public class SubMerchantDetail {
	
	/** 일반 거래처 아이디 **/
	private String subMerchantId;
	
	/** 업종 **/
	private String bizKind;
	/** 업태 **/
	private String bizCond;
	
	/** 법인 번호 **/
	private String corpRegNo;
	/** 사업자 번호 **/
	private String bizRegNo;
	
	/** 담당자명 **/
	private String custName;
	/** 담당자 연락처 **/
	private String custTel;
	/** 대표 이름 */
	private String ceoName;
	
	/** 거래처 구분(개인/법인) */
	private String type;
	
	/** 개업일 */
	private String openDate;
	
	/** 은행명 */
	private String bankName;
	/** 은행 계좌번호 */
	private String bankAccountNo;
	/** 은행 예금주 */
	private String bankHolder;
	
	/** 우편번호 */
	private String zipCode;
	/** 주소 */
	private String address;
	/** 상세주소 */
	private String addressDetail;

	/** 연락처 */
	private String tel;
	/** 팩스번호 */
	private String fax;
	
	/** 세금계산서 담당자 **/
	private String taxCustName;
	/** 세금계산서 발행 Email **/
	private String taxEmail;
	/** 세금계산서 연락처 **/
	private String taxTel;
	/** 세금계산서 핸드폰**/
	private String taxPhone;
	/** 세금계산서 팩스 **/
	private String taxFax;
	
	/** 영업 담당자 이름 **/
	private String salesName;
	/** 영업 담당자 연락처 **/
	private String salesTel;
	/** 정산 담당자 이름 **/
	private String billingName;
	/** 정산 담당자 연락처 **/
	private String billingTel;
	
	/** KPC 영업담당자 이름 **/
	private String kpcSalesName;
	/** KPC 영업담당자 연락처 **/
	private String kpcSalesTel;
	/** KPC 정산담당자 이름 **/
	private String kpcBillingName;
	/** KPC 정산담당자 연락처 **/
	private String kpcBillingTel;
	
	/** URL 1번 **/
	private String url01;
	/** URL 2번 **/
	private String url02;
	/** 홈페이지 URL **/
	private String urlHome;
	
}
