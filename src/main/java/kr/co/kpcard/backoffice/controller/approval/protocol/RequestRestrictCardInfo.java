package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRestrictCardInfo {
	private String giftNo;
	private String cardNumber;
	private String insertAdminId; 
	private String restrictYN;	
	private String restrictDesc;
	
	private String apprEmpId;
	private String reqEmpId;
	private String reqMemo;
	
	
	public String toString(){
		return String.format("Request Param[giftNo:%s, cardNumber:%s, insertAdminID:%s, restirctYN:%s,"
							+ " restirctDesc:%s,"
							+ " apprEmpId:%s,"
							+ " reqEmpId:%s,"
							+ " reqMemo:%s",
							giftNo, cardNumber, insertAdminId, restrictYN, restrictDesc, apprEmpId, reqEmpId, reqMemo);
	}
}
