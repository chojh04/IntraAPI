package kr.co.kpcard.backoffice.repository.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSalementSumm {
	private String submerchantNm;
	private String serviceNm;
	private String svcConnId;
	private String approvalStatus;
	private String approvalStatusNm;
	private String prodNm;
	private String prodCd;
	private String prodTypeNm;
	private String dealDt;
	private String saleAmt;
	private String saleCnt;
	private String cnt;
}
