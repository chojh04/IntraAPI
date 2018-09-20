package kr.co.kpcard.backoffice.service.card;

import kr.co.kpcard.backoffice.controller.approval.protocol.RequestRestrictCardInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqRestrictCardInfo {
	private String apiUrl;
	private String giftNo;
	private String cardNumber;
	private String insertAdminId; 
	private String restrictYN;	
	private String restrictDesc;	
}
