package kr.co.kpcard.backoffice.controller.card.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCardBalnaceRefundList {
	
	private String cardNumber;
	private String customerName;
	private String dateType;	
	private String startDate;
	private String endDate;
	private String dateOrderType;	
	private String dateOrderDesc;	
	private String procStatus;
	private String excelAllFlag;
	private int offset;
	private int limit;
		
	public String toString(){
		return String.format("RequestParam : [cardNumber:'%s'"
										 + ", customerName:'%s'"
										 + ", dateType:'%s'"
										 + ", startDate : %s"
										 + ", endDate:'%s'"
										 + ", dateOrderType:'%s'"
										 + ", dateOrderDesc:'%s'"
										 + ", status:'%s'"
										 + ", offset:'%s'"
										 + ", limit:'%s']",
										 cardNumber
										 , customerName
										 , dateType
										 , startDate
										 , endDate
										 , dateOrderType
										 , dateOrderDesc
										 , procStatus
										 , offset
										 , limit);
	}
}
