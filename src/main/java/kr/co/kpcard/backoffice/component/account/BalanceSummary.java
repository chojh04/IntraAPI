/**
 * 
 */
package kr.co.kpcard.backoffice.component.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 카드잔액 조회 Summary
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel("BalanceSummary")
public class BalanceSummary {
	/**
	 * 조회 시작일자
	 */
	@ApiModelProperty(notes = "조회 시작일자", required=true)
	private String	startDate;
	
	/**
	 * 조회 마지막일자
	 */
	@ApiModelProperty(notes = "조회 마지막일자", required=true)
	private String	endDate;
	
	/**
	 * 이전 잔액(시작일자)
	 */
	@ApiModelProperty(notes = "이전 잔액(시작일자)", required=true)
	private long	prevBalance;
	
	/**
	 * 현재 잔액(마지막일자)
	 */
	@ApiModelProperty(notes = "현재 잔액(마지막일자)", required=true)
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

	/**
	 * 결과 개수(전체)
	 */
	@ApiModelProperty(notes = "결과 개수", required=true)
	private int	count;

}
