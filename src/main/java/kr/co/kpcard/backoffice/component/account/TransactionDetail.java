/**
 * 
 */
package kr.co.kpcard.backoffice.component.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 거래내역 상세정보
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel("TransactionDetail")
public class TransactionDetail {
	/**
	 * 거래처아이디
	 */
	@ApiModelProperty(notes = "거래처아이디", required=true)
	private String	merchantId;
	
	/**
	 * 거래처명
	 */
	@ApiModelProperty(notes = "거래처명", required=true)
	private String	merchantName;
	
	/**
	 * 서비스아이디
	 */
	@ApiModelProperty(notes = "서비스아이디", required=true)
	private String	serviceId;
	
	/**
	 * 서비스명
	 */
	@ApiModelProperty(notes = "서비스명", required=true)
	private String	serviceName;
	
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
}
