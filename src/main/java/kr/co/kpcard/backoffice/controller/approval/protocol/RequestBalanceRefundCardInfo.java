package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBalanceRefundCardInfo {
	private String customerTel;
	private String customerName;
	private String refundCommision;
	private String giftNo;
	private String refundBankCode;
	private String refundDesc;
	private String cardNumber;
	private String apprEmpId;
	private String refundBankAccountNo;
	private String reqEmpId;
	private String refundBankHolder;
	private String balance;
	private String reqMemo;
	
	
	public String toString(){
		return String.format("Request Param[customerTel:%s, customerName:%s, refundCommision:%s,"
							+ " giftNo:%s,"
							+ " refundBankCode:%s,"
							+ " refundDesc:%s,"
							+ " cardNumber:%s,"
							+ " apprEmpId:%s,"
							+ " refundBankAccountNo:%s,"
							+ " reqEmpId:%s,"
							+ " refundBankHolder:%s,"
							+ " balance:%s,",
							customerTel, customerName, refundCommision, giftNo, refundBankCode,
							refundDesc, cardNumber, apprEmpId,refundBankAccountNo,reqEmpId,refundBankHolder,balance);
	}
}
