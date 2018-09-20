package kr.co.kpcard.backoffice.service.card;


import kr.co.kpcard.backoffice.controller.approval.protocol.RequestBalanceRefundCardInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqBalanceRefundInfo {
	private String apiUrl;
	private String customerTel;
	private String customerName;
	private String refundCommision;
	private String giftNo;
	private String refundBankCode;
	private String refundDesc;
	private String cardNumber;	
	private String refundBankAccountNo;	
	private String refundBankHolder;
	private String balance;
	
	public ReqBalanceRefundInfo(RequestBalanceRefundCardInfo request) {
		this.customerTel = request.getCustomerTel();
		this.customerName = request.getCustomerName();
		this.refundCommision = request.getRefundCommision();
		this.giftNo = request.getGiftNo();
		this.refundBankCode = request.getRefundBankCode();
		this.refundDesc = request.getRefundDesc();
		this.cardNumber = request.getCardNumber();
		this.refundBankAccountNo = request.getRefundBankAccountNo();
		this.refundBankHolder = request.getRefundBankHolder();
		this.balance = request.getBalance();		
	}
}
