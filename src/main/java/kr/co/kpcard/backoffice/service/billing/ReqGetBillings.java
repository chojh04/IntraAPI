package kr.co.kpcard.backoffice.service.billing;

import kr.co.kpcard.backoffice.controller.billing.protocol.RequestGetBillings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqGetBillings {
	int offset;
	int limit;
	String merchantId; 
	String serviceId;
	String serviceType;
	String dateType;
	String startDate;
	String endDate;
	String billingDuration;
	String excelAllFlag;
	String adjustType;
	public ReqGetBillings(RequestGetBillings requestBody){		
		this.offset = requestBody.getOffset();
		this.limit = requestBody.getLimit();
		this.merchantId = requestBody.getMerchantId();
		this.serviceId = requestBody.getServiceId();
		this.serviceType = requestBody.getServiceType();
		this.dateType = requestBody.getDateType();
		this.startDate = requestBody.getStartDate();
		this.endDate = requestBody.getEndDate();
		this.billingDuration = requestBody.getBillingDuration();
		this.excelAllFlag = requestBody.getExcelAllFlag();
		this.adjustType = requestBody.getAdjustType();
	}
	
	
}
