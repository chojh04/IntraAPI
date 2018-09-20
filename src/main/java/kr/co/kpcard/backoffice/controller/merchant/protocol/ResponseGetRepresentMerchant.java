package kr.co.kpcard.backoffice.controller.merchant.protocol;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetRepresentMerchant {
	private String merchantId;
	private String erpCode;
	private String name;
	private String nameEng;
	private String alias;
	private String aliasEng;
	private Date createDate;
	private String createDesc;
	private Date updateDate;
	private String updateDesc;
	private String bizKind;
	private String bizCond;
	private String bizGrp;
	private String bizGrpName;
	private String bizRegNo;
	private String corpRegNo;
	private String openDate;
	private String closeDate;
	private String closedFlag;
	private int capitalAmount;
	private int empCount;
	private int storeCount;
	private int avgSaleMonth;
	private int avgSaleYear;
	private String ceoName;
	private String zipcode;
	private String address;
	private String addressDetail;
	private String tel;
	private String telSub;
	private String fax;
	private String faxSub;
	private String submerchantCnt;
	private String useFlag;
	private String useFlagName;
	public void setValue(String merchantId, String erpCode,
			String name, String nameEng, String alias, String aliasEng,
			Date createDate, String createDesc, Date updateDate,
			String updateDesc, String bizKind, String bizCond, String bizGrp,
			String bizGrpName, String bizRegNo, String corpRegNo,
			String openDate, String closeDate, String closedFlag,
			int capitalAmount, int empCount, int storeCount,
			int avgSaleMonth, int avgSaleYear, String ceoName,
			String zipcode, String address, String addressDetail, String tel,
			String telSub, String fax, String faxSub, String submerchantCnt,
			String useFlag, String useFlagName) {
		this.merchantId = merchantId;
		this.erpCode = erpCode;
		this.name = name;
		this.nameEng = nameEng;
		this.alias = alias;
		this.aliasEng = aliasEng;
		this.createDate = createDate;
		this.createDesc = createDesc;
		this.updateDate = updateDate;
		this.updateDesc = updateDesc;
		this.bizKind = bizKind;
		this.bizCond = bizCond;
		this.bizGrp = bizGrp;
		this.bizGrpName = bizGrpName;
		this.bizRegNo = bizRegNo;
		this.corpRegNo = corpRegNo;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.closedFlag = closedFlag;
		this.capitalAmount = capitalAmount;
		this.empCount = empCount;
		this.storeCount = storeCount;
		this.avgSaleMonth = avgSaleMonth;
		this.avgSaleYear = avgSaleYear;
		this.ceoName = ceoName;
		this.zipcode = zipcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.tel = tel;
		this.telSub = telSub;
		this.fax = fax;
		this.faxSub = faxSub;
		this.submerchantCnt = submerchantCnt;
		this.useFlag = useFlag;
		this.useFlagName = useFlagName;
	}
	
}
