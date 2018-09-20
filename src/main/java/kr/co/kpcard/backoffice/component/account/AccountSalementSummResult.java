package kr.co.kpcard.backoffice.component.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSalementSummResult {
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
	public void setValues(String submerchantNm, String serviceNm,
			String svcConnId, String approvalStatus, String approvalStatusNm,
			String prodNm, String prodCd, String prodTypeNm, String dealDt,
			String saleAmt, String saleCnt, String cnt) {
		this.submerchantNm = submerchantNm;
		this.serviceNm = serviceNm;
		this.svcConnId = svcConnId;
		this.approvalStatus = approvalStatus;
		this.approvalStatusNm = approvalStatusNm;
		this.prodNm = prodNm;
		this.prodCd = prodCd;
		this.prodTypeNm = prodTypeNm;
		this.dealDt = dealDt;
		this.saleAmt = saleAmt;
		this.saleCnt = saleCnt;
		this.cnt = cnt;
	}
	
}
