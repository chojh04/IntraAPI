package kr.co.kpcard.backoffice.component.agent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentBillingHistory {
	private String subMerchantNm;
	private String serviceId;
	private String serviceNm;
	private String startDate;
	private String endDate;
	private String billingDate;
	private String status;
	private long paymentCnt;
	private long paymentAmount;
	private long cancelCnt;
	private long cancelAmount;
	private long billingAmount;
	private long affiliateComm;
	private long affiliateVat;
	private long affiliateSumm;
	private long finalBillingAmount;
	
	public void setValues(String subMerchantNm, String serviceId,
			String serviceNm, String startDate, String endDate,
			String billingDate, String status, long paymentCnt,
			long paymentAmount, long cancelCnt, long cancelAmount,
			long billingAmount, long affiliateComm, long affiliateVat,
			long affiliateSumm, long finalBillingAmount) {
		this.subMerchantNm = subMerchantNm;
		this.serviceId = serviceId;
		this.serviceNm = serviceNm;
		this.startDate = startDate;
		this.endDate = endDate;
		this.billingDate = billingDate;
		this.status = status;
		this.paymentCnt = paymentCnt;
		this.paymentAmount = paymentAmount;
		this.cancelCnt = cancelCnt;
		this.cancelAmount = cancelAmount;
		this.billingAmount = billingAmount;
		this.affiliateComm = affiliateComm;
		this.affiliateVat = affiliateVat;
		this.affiliateSumm = affiliateSumm;
		this.finalBillingAmount = finalBillingAmount;
	}
	
	
}
