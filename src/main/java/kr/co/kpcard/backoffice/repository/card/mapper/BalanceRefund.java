package kr.co.kpcard.backoffice.repository.card.mapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceRefund {
	private long seq;
	private long apprSeq;
	private String cardNumber;
	private String status;
	private String statusName;
	private String balance;
	private String refundCommision;
	private String customerName;
	private String customerTel;
	private String BankCd;
	private String BankName;
	private String bankAccNo;
	private String bankHolder;
	private String refundDesc;
	private String apprDesc;
	private String orderNo;
	private String approvalNo;
	private String approvalStatus;
	private Date receptionDt;
	private Date approvalDt;
	private Date refundDt;
}
