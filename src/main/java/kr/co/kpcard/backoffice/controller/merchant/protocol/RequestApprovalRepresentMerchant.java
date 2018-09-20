package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 대기 거래처 요청 폼
 * @author MinWook
 * @date 2018-05-17
 */
@Getter
@Setter
public class RequestApprovalRepresentMerchant {
	private String merchantId;
	private String name;
	private String alias;
	private String ceoName;
	private String openDate;
	private String corpRegNo;
	private String bizRegNo;
	private String bizKind;
	private String bizCond;
	private String bizGrp;
	private String useFlag;
	private String zipCode;
	private String address;
	private String addressDetail;
	private String tel;
	private String fax;
	private String createDesc;
	private String reqEmpId;
	private String apprEmpId;
	private String menuId;
	
	
	/**
	 * setValues
	 * @param merchantId
	 * @param name
	 * @param alias
	 * @param ceoName
	 * @param openDate
	 * @param corpRegNo
	 * @param bizRegNo
	 * @param bizKind
	 * @param bizCond
	 * @param bizGrp
	 * @param useFlag
	 * @param zipCode
	 * @param address
	 * @param addressDetail
	 * @param tel
	 * @param fax
	 * @param createDesc
	 * @param reqEmpId
	 * @param apprEmpId
	 * @param menuId
	 */
	public void setValues(
			String merchantId, String name, String alias, String ceoName, String openDate, String corpRegNo, 
			String bizRegNo,  String bizKind, String bizCond, String bizGrp,
			String useFlag, String zipCode, String address, String addressDetail,
			String tel, String fax, String createDesc, String reqEmpId, String apprEmpId, String menuId
			) {
		
		this.merchantId = merchantId;
		this.name = name;
		this.alias = alias;
		this.ceoName = ceoName;
		this.openDate = openDate;
		this.corpRegNo = corpRegNo;
		this.bizRegNo = bizRegNo;
		this.bizKind = bizKind;
		this.bizCond = bizCond;
		this.bizGrp = bizGrp;
		this.useFlag = useFlag;
		this.zipCode = zipCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.tel = tel;
		this.fax = fax;
		this.createDesc = createDesc;
		this.reqEmpId = reqEmpId;
		this.apprEmpId = apprEmpId;
		this.menuId = menuId;
	}
}
