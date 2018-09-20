package kr.co.kpcard.backoffice.component.merchant;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepresentList {
	private String merchantId;
	private String gubun;
	private String name;
	private Date createDate;
	private String bizKind;
	private String bizCond;
	private String bizGrp;
	private String corpRegNo;
	private String alias;
	private String useFlag;
	private String updateDt;
	private String childCnt;
	private String linkUrl;
	public void setValues(String merchantId, String gubun, String name,
			Date createDate, String bizKind, String bizCond, String bizGrp,
			String corpRegNo, String alias, String useFlag, String updateDt,
			String childCnt, String linkUrl) {
		this.merchantId = merchantId;
		this.gubun = gubun;
		this.name = name;
		this.createDate = createDate;
		this.bizKind = bizKind;
		this.bizCond = bizCond;
		this.bizGrp = bizGrp;
		this.corpRegNo = corpRegNo;
		this.alias = alias;
		this.useFlag = useFlag;
		this.updateDt = updateDt;
		this.childCnt = childCnt;
		this.linkUrl = linkUrl;
	}
	
}
