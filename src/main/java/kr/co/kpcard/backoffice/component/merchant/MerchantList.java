package kr.co.kpcard.backoffice.component.merchant;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantList {
	private String submerchantId;
	private String gubun;
	private String parentId;
	private String childId;
	private String name;
	private String alias;
	private String useFlag;
	private Date createDate;
	private String updateDt;
	private String childCnt;
	private String linkUrl;
	public void setValues(String submerchantId, String gubun,
			String parentId, String childId, String name, String alias,
			String useFlag, Date createDate, String updateDt, String childCnt,
			String linkUrl) {
		this.submerchantId = submerchantId;
		this.gubun = gubun;
		this.parentId = parentId;
		this.childId = childId;
		this.name = name;
		this.alias = alias;
		this.useFlag = useFlag;
		this.createDate = createDate;
		this.updateDt = updateDt;
		this.childCnt = childCnt;
		this.linkUrl = linkUrl;
	}
	
}
