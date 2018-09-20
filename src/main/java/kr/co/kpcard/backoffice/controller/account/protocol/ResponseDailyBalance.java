/**
 * 
 */
package kr.co.kpcard.backoffice.controller.account.protocol;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.BalanceSummary;
import lombok.Getter;
import lombok.Setter;

/**
 * 1일 잔액리스트 응답
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel(value="ResponseDailyBalance", description="카드잔액조회 응답")
public class ResponseDailyBalance {
	
	@ApiModelProperty(notes = "결과요약", required=true)
	private BalanceSummary summary;
	
	@ApiModelProperty(notes = "일자별잔액리스트")
	private List<BalanceDetail> resultList;
	
	@ApiModelProperty(notes = "페이징 offset", required=true)
	private long offset;

	@ApiModelProperty(notes = "페이징 limit", required=true)
	private long limit;
}
