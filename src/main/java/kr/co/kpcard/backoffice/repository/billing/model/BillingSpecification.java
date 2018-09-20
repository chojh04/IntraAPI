package kr.co.kpcard.backoffice.repository.billing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @author chris
 * 정산명세서 내역 객체  
 */

@Getter
@Setter
@ApiModel(value="BillingSpecification", description="정산명세서")
public class BillingSpecification {
	
	
	@ApiModelProperty(notes = "일련번호", required=true)
	private String seq;
	/**
	 * 거래처 아이디
	 */	
	@ApiModelProperty(notes = "일반거래처ID", required=true)
	private String submerchantId;
	/**
	 * 거래처명
	 */
	@ApiModelProperty(notes = "일반거래처명", required=true)
	private String submerchantName;
	/**
	 * 서비스 아이디
	 */
	@ApiModelProperty(notes = "서비스ID", required=true)
	private String serviceId;
	/**
	 * 서비스 연동 아이디
	 */
	@ApiModelProperty(notes = "서비스연결ID", required=true)
	private String serviceConnId;
	
	/**
	 * 서비스명 
	 */
	@ApiModelProperty(notes = "서비스명", required=true)
	private String serviceName;
	
	/**
	 * 정산 아이디
	 */	
	@ApiModelProperty(notes = "정산ID", required=true)
	private String billingId;
	/**
	 * 정산명
	 */
	@ApiModelProperty(notes = "정산명", required=true)
	private String billingName;
	/**
	 * 거래구분(충전, 결제, 환불)
	 */
	@ApiModelProperty(notes = "거래구분", required=true)
	private String type;
	@ApiModelProperty(notes = "거래명칭", required=true)
	private String typeName;
	
	/**
	 * 승인금액
	 */
	@ApiModelProperty(notes = "승인금액", required=true)
	private long amount;
	/**
	 * 취소금액
	 */
	@ApiModelProperty(notes = "취소금액", required=true)
	private long cancelAmount;
	/**
	 * 총금액(승인금액 - 취소금액)
	 */
	@ApiModelProperty(notes = "총금액(승인금액 - 취소금액)", required=true)
	private long totalAmount;
	/**
	 * 거래건수(승인건수 + 취소검수)
	 */
	@ApiModelProperty(notes = "거래건수(승인건수 + 취소검수)", required=true)
	private long cnt;
	/**
	 * 수수료금액
	 */
	@ApiModelProperty(notes = "수수료금액", required=true)
	private long commision;
	/**
	 * 부가세 금액
	 */
	@ApiModelProperty(notes = "부가세 금액", required=true)
	private long tax;
	/**
	 * 수수료합계
	 */
	@ApiModelProperty(notes = "수수료합계", required=true)
	private long commTotal;
	/**
	 * 정산금액
	 */
	@ApiModelProperty(notes = "정산금액", required=true)
	private long billingAmount;
	/**
	 * 정산기간(from)
	 */
	@ApiModelProperty(notes = "정산기간(from)", required=true)
	private String approvalDtMin;
	/**
	 * 정산기간(to)
	 */
	@ApiModelProperty(notes = "정산기간(to)", required=true)
	private String approvalDtMax;
	/**
	 * 정산일자(정산지급일)
	 */
	@ApiModelProperty(notes = "정산일자(정산지급일)", required=true)
	private String billingDt;
	/**
	 * 부가세타입(부가세포함, 부가세별도)
	 */
	@ApiModelProperty(notes = "부가세타입(부가세포함, 부가세별도)", required=true)
	private String taxTypeName;
	/**
	 * 부가세 타입코드
	 */
	@ApiModelProperty(notes = "부가세 타입코드", required=true)
	private String merchantTaxType;
	/**
	 * 수수료구분(비율정산, 건당)
	 */
	@ApiModelProperty(notes = "수수료구분(비율정산, 건당)", required=true)
	private String commTypeName;
	/**
	 * 수수료구분코드
	 */
	@ApiModelProperty(notes = "수수료구분코드", required=true)
	private String merchantCommType;
	/**
	 * 수수료율(금액)
	 */
	@ApiModelProperty(notes = "수수료율(금액)", required=true)
	private String merchantCommision;
	/**
	 * 은행코드
	 */
	@ApiModelProperty(notes = "은행코드", required=true)
	private String bankCd;
	@ApiModelProperty(notes = "은행명", required=true)
	private String bankName;
	/**
	 * 입금계좌번호
	 */
	@ApiModelProperty(notes = "입금계좌번호", required=true)
	private String bankAccNo;
	/**
	 * 예금주명
	 */
	@ApiModelProperty(notes = "예금주명", required=true)
	private String bankHolder;
	/**
	 * 정산구분(건별, 총액)
	 */
	@ApiModelProperty(notes = "정산구분(건별, 총액)", required=true)
	private String billingCommType;
	private String billingCommTypeCd;
	/**
	 * 정산구분(1, 7, 15, 31)
	 */
	@ApiModelProperty(notes = "정산구분(1, 7, 15, 31)", required=true)
	private String billingDuration;
	/**
	 * 정산구분코드
	 */
	@ApiModelProperty(notes = "정산구분코드", required=true)
	private String billingDurationType;
	
	/**
	 * 할인금액
	 */
	@ApiModelProperty(notes = "할인금액", required=true)
	private long dcAmount;
	
	/**
	 * 결제금액
	 */
	@ApiModelProperty(notes = "결제금액", required=true)
	private long payAmount;
	
	/**
	 * GS대사 불일치 금액
	 */
	@ApiModelProperty(notes = "GS대사 불일치 금액", required=true)
	private long notMatchedAmount;
	
	/**
	 * GS 일별 차액
	 */
	@ApiModelProperty(notes = "GS 일별 차액", required=true)
	private long differenceAmount;
	/**
	 *  
	 */
	@ApiModelProperty(notes = "상태", required=true)
	private String status;
	
	@ApiModelProperty(notes = "승인건수", required=true)
	private long approvalCnt;
	
	@ApiModelProperty(notes = "취소건수", required=true)
	private long cancelCnt;
}	
