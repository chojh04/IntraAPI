/**
 * 
 */
package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.TransactionDetail;
import kr.co.kpcard.backoffice.component.account.TransactionSummary;
import lombok.Getter;
import lombok.Setter;

/**
 * 거래별 합계 조회
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel(value="ResponseTransactionSummary", description="카드 거래별 합계")
public class ResponseTransaction {

	@ApiModelProperty(notes = "조회내역요약", required=true)
	private TransactionSummary summary;
	
	@ApiModelProperty(notes = "일자별잔액리스트")
	private List<TransactionDetail> resultList;
	
	@ApiModelProperty(notes = "페이징 offset", required=true)
	private long offset;

	@ApiModelProperty(notes = "페이징 limit", required=true)
	private long limit;

}
