package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * 일반 거래처 [등록], [수정] 승인 요청 폼
 * Created by @author : MinWook on 2018. 7. 2.
 *
 */
@Getter
@Setter
public class RequestSubMerchantForm {

	/** 일반 거래처 아이디 **/
	private String subMerchantId;
	
	/** 상위 거래처 아이디 **/
	private String parentId;
	
	/** 거래처명 **/
	private String name;
	/** 거래처 약칭 **/
	private String alias;
	/** 대표자 성명 **/
	private String ceoName;
	/** 개업일 **/
	private String openDate;
	/** 사업자 번호 **/
	private String bizRegNo;
	/** 법인 번호 **/
	private String corpRegNo;
	/** 업종 **/
	private String bizKind;
	/** 업태 **/
	private String bizCond;
	/** 거래처 구분 **/
	private String type;
	/** 우편번호 **/
	private String zipCode;
	/** 주소 **/
	private String address;
	/** 상세주소 **/
	private String addressDetail;
	
	/** 연락처 **/
	private String tel;
	/** 팩스 번호 **/
	private String fax;
	
	/** 세금계산서 담당자 **/
	private String taxCustName;
	/** 세금계산서 연락처 **/
	private String taxTel;
	/** 세금계산서 팩스 **/
	private String taxFax;
	/** 세금계산서 핸드폰**/
	private String taxPhone;
	/** 세금계산서 발행 Email **/
	private String taxEmail;
	
	/** 은행명 **/
	private String bankName;
	/** 은행 계좌번호 **/
	private String bankAccountNo;
	/** 은행 예금주 **/
	private String bankHolder;
	
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
	
	/** 관리자 ID **/
	private String agentId;
	/** 관리자 비밀번호 **/
	private String agentPw;
	
	/** 사용여부 **/
	private String useFlag;
	/** 홈페이지 URL **/
	private String urlHome;
	
	//아래부터 승인 요청 정보
	
	/** 신청자 사번 */
	private String reqEmpId;
	
	/** 승인자 사번 */
	private String apprEmpId;
	
	/** 요청 사유 */
	private String reqMemo;

	
}
