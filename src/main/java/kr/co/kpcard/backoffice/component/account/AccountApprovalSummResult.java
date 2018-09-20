package kr.co.kpcard.backoffice.component.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountApprovalSummResult {
	private String submerchantNm;
	private String serviceNm;
	private String svcConnId;
	private String dealDivider;
	private String dealDt;
	private String approvalStatusNm;
	private String approvalStatus;
	private String cardCdName;
	private String cardCd;
	private String payTypeName;
	private String payType;
	private String cnt;
	private String amount;
	private String commision;
	public void setValues(String submerchantNm, String serviceNm,
			String svcConnId, String dealDivider, String dealDt,
			String approvalStatusNm, String approvalStatus, String cardCdName,
			String cardCd, String payTypeName, String payType, String cnt,
			String amount, String commision) {
		this.submerchantNm = submerchantNm;
		this.serviceNm = serviceNm;
		this.svcConnId = svcConnId;
		this.dealDivider = dealDivider;
		this.dealDt = dealDt;
		this.approvalStatusNm = approvalStatusNm;
		this.approvalStatus = approvalStatus;
		this.cardCdName = cardCdName;
		this.cardCd = cardCd;
		this.payTypeName = payTypeName;
		this.payType = payType;
		this.cnt = cnt;
		this.amount = amount;
		this.commision = commision;
	}
	
}
