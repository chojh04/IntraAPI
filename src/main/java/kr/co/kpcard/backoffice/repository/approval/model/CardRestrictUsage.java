package kr.co.kpcard.backoffice.repository.approval.model;

import kr.co.kpcard.backoffice.controller.approval.protocol.RequestRestrictCardInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRestrictUsage {
	private String giftNo;			//관리번호
	private String cardNumber;		//카드번호
	private String insertAdminId; 	//등록자ID
	private String restrictYN;		//제한여부(Y/N)
	private String restrictDesc;	//제한 사유
	
	public CardRestrictUsage(RequestRestrictCardInfo request) {
		this.giftNo = request.getGiftNo();
		this.cardNumber = request.getCardNumber();
		this.insertAdminId = request.getInsertAdminId();
		this.restrictYN = request.getRestrictYN();
		this.restrictDesc = request.getRestrictDesc();
	}
}
