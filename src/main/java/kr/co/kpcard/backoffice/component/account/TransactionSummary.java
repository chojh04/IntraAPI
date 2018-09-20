/**
 * 
 */
package kr.co.kpcard.backoffice.component.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 거래내역 조회 Summary
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel("TransactionSummary")
public class TransactionSummary {
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
	 * 거래구분
	 */
	@ApiModelProperty(notes = "거래구분(TRNT-0001: 충전, TRNT-0002: 결제, TRNT-0003: 환불)")
	private String transactionType;
		
	/**
	 * 거래건수
	 */
	@ApiModelProperty(notes = "거래건수", required=true)
	private long	transactionCount;
	
	/**
	 * 거래금액
	 */
	@ApiModelProperty(notes = "거래금액", required=true)
	private long	transactionAmount;
	
	/**
	 * 거래취소건수
	 */
	@ApiModelProperty(notes = "거래취소건수", required=true)
	private long	cancelTransactionCount;
	
	/**
	 * 거래취소금액
	 */
	@ApiModelProperty(notes = "거래취소금액", required=true)
	private long	cancelTransactionAmount;

	/**
	 * 실거래금액
	 */
	@ApiModelProperty(notes = "총거래금액(거래금액-거래취소금액)", required=true)
	private long	transactionSum;

	/**
	 * 결과 개수(전체)
	 */
	@ApiModelProperty(notes = "결과 개수", required=true)
	private int	count;

}
