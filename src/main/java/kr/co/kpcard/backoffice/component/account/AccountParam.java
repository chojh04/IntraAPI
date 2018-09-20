package kr.co.kpcard.backoffice.component.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.qos.logback.classic.Logger;
import lombok.Getter;

@Getter
public class AccountParam {
	private String merchantId;
	private String serviceId;
	private String approvalStatus;
	private List<String> approvalStatusList;
	private String orderNo;
	private String approvalNo;
	private String dateType;
	private String svcConnId;
	private String startDate;
	private String endDate;
	private String merchantNm;
	private String serviceNm;
	private int offset;
	private int limit;
	private String sort;
	private String clause;
	private String payMethod;
	private List<String> payMethods;
	private String saleDivider;
	private String cardType;
	private List<String> cardTypeList;
	private String excelAllFlag;
	
	public void setValues(String merchantId, String serviceId,
			String approvalStatus, String orderNo, String approvalNo,
			String dateType, String svcConnId, String startDate,
			String endDate, String merchantNm, String serviceNm, int offset,
			int limit, String sort, String clause, String payMethod,
			String saleDivider, String cardType, String excelAllFlag) {
		this.merchantId = merchantId;
		this.serviceId = serviceId;
		this.approvalStatus = approvalStatus;
		this.orderNo = orderNo;
		this.approvalNo = approvalNo;
		this.dateType = dateType;
		this.svcConnId = svcConnId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.merchantNm = merchantNm;
		this.serviceNm = serviceNm;
		this.offset = offset;
		this.limit = limit;
		this.sort = sort;
		this.clause = clause;
		this.payMethod = payMethod;
		this.saleDivider = saleDivider;		
		this.excelAllFlag = excelAllFlag;	
		this.cardType = cardType;
		if(!"".equals(cardType))setCardTypeList(cardType);
		if(!"".equals(approvalStatus))setApprovalStatusList(approvalStatus);
		if(!"".equals(payMethod))setPayMethods(payMethod);
		
	}
	
	public String toStringForLog() {
		return "{[\"merchantId\":\"" + merchantId + "\", serviceId\":\""
				+ serviceId + "\", approvalStatus\":\"" + approvalStatus
				+ "\", orderNo\":\"" + orderNo + "\", approvalNo\":\""
				+ approvalNo + "\", dateType\":\"" + dateType
				+ "\", svcConnId\":\"" + svcConnId + "\", startDate\":\""
				+ startDate + "\", endDate\":\"" + endDate
				+ "\", merchantNm\":\"" + merchantNm + "\", serviceNm\":\""
				+ serviceNm + "\", offset\":\"" + offset + "\", limit\":\""
				+ limit + "\", sort\":\"" + sort + "\", clause\":\"" + clause
				+ "\", payMethod\":\"" + payMethod + "\", saleDivider\":\""
				+ saleDivider + "\", cardType\":\"" + cardType
				+ "\", excelAllFlag\":\"" + excelAllFlag + "]}}";
	}

	//카드 타입 set
	public void setCardTypeList(String reqCardType)
	{
		String[] splitStr = reqCardType.split(",");		
		List<String> typeList = new ArrayList<String>();
	
		for(String val : splitStr)
		{
			if((!"".equals(val) && !"all".equals(val)))
			{
				System.out.println("val : "+val);
				typeList.add(val);
			}else if("all".equals(val)){				
				this.cardType = val;
				return;
			}
		}		
		this.cardTypeList = typeList;
	}	
	
	//지불수단 set
	public void setApprovalStatusList(String reqApprovalStatus)
	{
		String[] splitStr = reqApprovalStatus.split(",");		
		List<String> typeList = new ArrayList<String>();
	
		for(String val : splitStr)
		{
			if((!"".equals(val) && !"all".equals(val)))
			{
				System.out.println("statusval : "+val);
				typeList.add(val);
			}else if("all".equals(val)){
				typeList.add(val);
				this.approvalStatus = val;
				return;
			}
		}		
		this.approvalStatusList = typeList;
	}	
	
	
	public void setPayMethods(String payMethod)
	{
		String[] splitStr = payMethod.split(",");		
		List<String> typeList = new ArrayList<String>();
	
		for(String val : splitStr)
		{
			if((!"".equals(val) && !"all".equals(val)))
			{
				System.out.println("val : "+val);
				typeList.add(val);
			}else if("all".equals(val)){
				typeList.add(val);
				this.payMethod = val;
				return;
			}
		}		
		this.payMethods = typeList;
	}
}
