package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantGetMerchant {
	private String submerchantId;
	private String erpCode;
	private String depth;
	private String parentId;
	private String parentName;
	private String childd;
	private String name;
	private String nameEng;
	private String alias;
	private String aliasEng;
	private String useFlag;
	private String useFlagName;
	private String reuseFlag;
	private String delFlag;
	private String pgId;
	private String pgPw;
	private String agentId;
	private String agentPw;
	private String bizKind;
	private String bizCond;
	private String corpRegNo;
	private String bizRegNo;
	private String custNm;
	private String custTel;
	private String ceoName;
	private String bizGrp;
	private String bizGrpName;
	private String bankCd;
	private String bankNm;
	private String bankAccNo;
	private String bankHolder;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String tel;
	private String fax;
	private String taxCustNm;
	private String taxEmail;
	private String taxTel;
	private String taxPhone;
	private String taxFax;
	private String salesNm;
	private String salesTel;
	private String billingNm;
	private String billingTel;
	private String kpcSalesNm;
	private String kpcSalesTel;
	private String kpcBillingNm;
	private String kpcBillingTel;
	private String url01;
	private String url02;
	private String urlHome;
	private String openDate;
	private String path;
	public void setValues(String submerchantId, String erpCode,
			String depth, String parentId, String parentName, String childd,
			String name, String nameEng, String alias, String aliasEng,
			String useFlag, String useFlagName, String reuseFlag,
			String delFlag, String pgId, String pgPw, String agentId,
			String agentPw, String bizKind, String bizCond, String corpRegNo,
			String bizRegNo, String custNm, String custTel, String ceoName,
			String bizGrp, String bizGrpName, String bankCd, String bankNm,
			String bankAccNo, String bankHolder, String zipcode,
			String address, String addressDetail, String tel, String fax,
			String taxCustNm, String taxEmail, String taxTel, String taxPhone,
			String taxFax, String salesNm, String salesTel, String billingNm,
			String billingTel, String kpcSalesNm, String kpcSalesTel,
			String kpcBillingNm, String kpcBillingTel, String url01,
			String url02, String urlHome, String openDate, String path) {
		this.submerchantId = submerchantId;
		this.erpCode = erpCode;
		this.depth = depth;
		this.parentId = parentId;
		this.parentName = parentName;
		this.childd = childd;
		this.name = name;
		this.nameEng = nameEng;
		this.alias = alias;
		this.aliasEng = aliasEng;
		this.useFlag = useFlag;
		this.useFlagName = useFlagName;
		this.reuseFlag = reuseFlag;
		this.delFlag = delFlag;
		this.pgId = pgId;
		this.pgPw = pgPw;
		this.agentId = agentId;
		this.agentPw = agentPw;
		this.bizKind = bizKind;
		this.bizCond = bizCond;
		this.corpRegNo = corpRegNo;
		this.bizRegNo = bizRegNo;
		this.custNm = custNm;
		this.custTel = custTel;
		this.ceoName = ceoName;
		this.bizGrp = bizGrp;
		this.bizGrpName = bizGrpName;
		this.bankCd = bankCd;
		this.bankNm = bankNm;
		this.bankAccNo = bankAccNo;
		this.bankHolder = bankHolder;
		this.zipcode = zipcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.tel = tel;
		this.fax = fax;
		this.taxCustNm = taxCustNm;
		this.taxEmail = taxEmail;
		this.taxTel = taxTel;
		this.taxPhone = taxPhone;
		this.taxFax = taxFax;
		this.salesNm = salesNm;
		this.salesTel = salesTel;
		this.billingNm = billingNm;
		this.billingTel = billingTel;
		this.kpcSalesNm = kpcSalesNm;
		this.kpcSalesTel = kpcSalesTel;
		this.kpcBillingNm = kpcBillingNm;
		this.kpcBillingTel = kpcBillingTel;
		this.url01 = url01;
		this.url02 = url02;
		this.urlHome = urlHome;
		this.openDate = openDate;
		this.path = path;
	}
	
}
