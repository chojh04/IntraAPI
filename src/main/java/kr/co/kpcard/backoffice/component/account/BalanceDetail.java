/**
 * 
 */
package kr.co.kpcard.backoffice.component.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 일자별 카드잔액 상세정보
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel("BalanceDetail")
public class BalanceDetail {
	/**
	 * 대상일자
	 */
	@ApiModelProperty(notes = "대상일자", required=true)
	private String	balanceDate;
	
	/**
	 * 이전 잔액
	 */
	@ApiModelProperty(notes = "이전 잔액", required=true)
	private long	prevBalance;
	
	/**
	 * 현재 잔액
	 */
	@ApiModelProperty(notes = "현재 잔액", required=true)
	private long	balance;
	
	/**
	 * 충전건수
	 */
	@ApiModelProperty(notes = "충전건수", required=true)
	private long	chargeCount;

	/**
	 * 충전금액
	 */
	@ApiModelProperty(notes = "충전금액", required=true)
	private long	chargeAmount;
	
	/**
	 * 충전취소건수
	 */
	@ApiModelProperty(notes = "충전취소건수", required=true)
	private long	cancelChargeCount;
	
	/**
	 * 충전취소금액
	 */
	@ApiModelProperty(notes = "충전취소금액", required=true)
	private long	cancelChargeAmount;
	
	/**
	 * 충전합계
	 */
	@ApiModelProperty(notes = "충전합계", required=true)
	private long	chargeSum;
	/**
	 * 결제건수
	 */
	@ApiModelProperty(notes = "결제건수", required=true)
	private long	payCount;

	/**
	 * 결제금액
	 */
	@ApiModelProperty(notes = "결제금액", required=true)
	private long	payAmount;
	
	/**
	 * 결제취소건수
	 */
	@ApiModelProperty(notes = "결제취소건수", required=true)
	private long	cancelPayCount;
	
	/**
	 * 결제취소금액
	 */
	@ApiModelProperty(notes = "결제취소금액", required=true)
	private long	cancelPayAmount;
	
	/**
	 * 결제합계
	 */
	@ApiModelProperty(notes = "결제합계", required=true)
	private long	paySum;
	
	/**
	 * 환불건수
	 */
	@ApiModelProperty(notes = "환불건수", required=true)
	private long	refundCount;

	/**
	 * 환불합계
	 */
	@ApiModelProperty(notes = "환불합계", required=true)
	private long	refundAmount;

}
