package kr.co.kpcard.billingservice.app.repository.settlement.hm;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class HappyMoneySettlement implements Serializable{
	
	private static final long serialVersionUID = 7424330876420430470L;
	
	private String dealDate;
	private String dealTime;
	private String orderNo;
	private String approvalNo;
	private String kind;        // 지불 구분 1: 상품권, 결제 2: 해피캐시 결제 3: 모바일팝 결제
	private String status;      // 상태 0: 결제 1: 취소 
	private long   amount;
	
}
