package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSubMerchant {
	private String parentId;
	private String submerchantId;
	private String name;
	private String alias;
	private String ceoName;
	private String openDate;
	private String bizRegNo;
	private String corpRegNo;
	private String bizKind;
	private String bizCond;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String bizGrp;
	private String tel;
	private String fax;
	private String taxCustName;
	private String taxTel;
	private String taxFax;
	private String taxPhone;
	private String taxEmail;
	private String bankNm;
	private String bankAccNo;
	private String bankHolder;
	private String salesNm;
	private String salesTel;
	private String billingNm;
	private String billingTel;
	private String kpcSalesNm;
	private String kpcSalesTel;
	private String kpcBillingNm;
	private String kpcBillingTel;
	private String agentId;
	private String agentPw;
	private String encAgentPw; 
	private String useFlag;
	private String urlHome;
}
