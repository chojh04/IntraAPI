package kr.co.kpcard.backoffice.service.billing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistBillingContent {
	private String seq;		//예비명세서 seq
	private long amount;//거래금액
	private long cancelAmount;//취소금액
	private long notMatchedAmount; //불일치금액	
	private long differenceAmount;//일별 차액
	private long etcAmount; //기타금액
	private long adjustmentAmount;//조정금액
	private long totTransAmount; //총 거래금액
	private long payAmount;//결제금액	
	private long dcAmount;//할인금액
	private long commision; //수수료
	private long tax;		//부가세
	private long commTotal;//수수료+부가세
	private long billingAmount;//정산금액
	private String commRatio;//정산수수료
	private String descMemo;//메모	 
}
