package kr.co.kpcard.backoffice.component.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSalementParam {
	private String merchantId;
	private String serviceId;
	private String approvalStatus;
	private String saleMethod;
	private String prodType;
	private String orderNo;
	private String approvalNo;
	private String svcConnId;
	private String amount;
	private String startDate;
	private String endDate;
	private String merchantNm;
	private String serviceNm;
	private int offset;
	private int limit;
	private String excelAllFlag;
	public void setValues(String merchantId, String serviceId,
			String approvalStatus, String saleMethod, String prodType,
			String orderNo, String approvalNo, String svcConnId, String amount,
			String startDate, String endDate, String merchantNm,
			String serviceNm, int offset, int limit, String excelAllFlag) {
		this.merchantId = merchantId;
		this.serviceId = serviceId;
		this.approvalStatus = approvalStatus;
		this.saleMethod = saleMethod;
		this.prodType = prodType;
		this.orderNo = orderNo;
		this.approvalNo = approvalNo;
		this.svcConnId = svcConnId;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.merchantNm = merchantNm;
		this.serviceNm = serviceNm;
		this.offset = offset;
		this.limit = limit;
		this.excelAllFlag = excelAllFlag;
	}
	
}
