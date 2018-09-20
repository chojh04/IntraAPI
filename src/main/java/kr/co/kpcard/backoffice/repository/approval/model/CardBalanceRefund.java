package kr.co.kpcard.backoffice.repository.approval.model;

import java.util.Date;

import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestRestrictCardInfo;
import kr.co.kpcard.backoffice.service.card.ReqBalanceRefundInfo;
import kr.co.kpcard.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardBalanceRefund {
	private long apprSeq;
	private String cardNumber;
	private String status;
	private String balance;
	private String refundCommision;
	private String customerName;
	private String customerTel;
	private String refundBankCode;
	private String refundBankkAccountNo;
	private String refundBanHolder;
	private String refundDesc;
	private String apprEmpId;
	private Date receptionDt;	
	private Date refundDt;
	private Date approvalDt;	

	//잔액환불 요청 승인완료 후 환불 가능상태 등록 RequestData
	public CardBalanceRefund(Approval apprInfo, ReqBalanceRefundInfo reqBalanceRefundInfo){			
		this.apprSeq = apprInfo.getSeq();
		this.cardNumber = reqBalanceRefundInfo.getCardNumber();		
		this.balance = reqBalanceRefundInfo.getBalance();
		this.refundCommision = reqBalanceRefundInfo.getRefundCommision();
		this.customerName = reqBalanceRefundInfo.getCustomerName();
		this.customerTel = reqBalanceRefundInfo.getCustomerTel();
		this.refundBankCode = reqBalanceRefundInfo.getRefundBankCode();
		this.refundBankkAccountNo = reqBalanceRefundInfo.getRefundBankAccountNo();
		this.refundBanHolder = reqBalanceRefundInfo.getRefundBankHolder();
		this.refundDesc = apprInfo.getReqMemo();
		this.status = SystemCodeConstant.PROC_0010;
		this.receptionDt = apprInfo.getReqDate();
		this.approvalDt = DateUtil.createDate();
	}
	
	public CardBalanceRefund(long apprSeq, String apprEmpId){			
		this.apprSeq = apprSeq;
		this.apprEmpId = apprEmpId;
		this.status = SystemCodeConstant.PROC_0040;		
		this.refundDt = DateUtil.createDate();
	}
	
	public CardBalanceRefund(long apprSeq, String apprEmpId, String refundDesc){			
		this.apprSeq = apprSeq;
		this.apprEmpId = apprEmpId;
		this.refundDesc = refundDesc;
		this.status = SystemCodeConstant.PROC_0050;		
	}
}
