package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.Date;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultGetKConBrochure extends ResBody {
	private String productId;
	private String title;
	private String type;
	private String typeName;
	private String typeDetail;
	private String typeDetailName;
	private String status;
	private String statusName;
	private String seller;
	private String usage;
	private long amount;
	private String prefix;
	private int couponNoLength;
	private int maxUseCount;
	private String delFlag;
	private int expireDays;
	private String expireDaysType;
	private String expireDaysTypeName;
	private Date createDate;
	private Date updateDate;
	private String register;
	
	public ResultGetKConBrochure(){}
	
	public ResultGetKConBrochure(String code, String message) {
		super(code, message);
	}
	
	public ResultGetKConBrochure(String code, String message, String productId,
			String title, String type, String typeName, String typeDetail,
			String typeDetailName, String status, String statusName,
			String seller, String usage, long amount, String prefix,
			int couponNoLength, int maxUseCount, String delFlag,
			int expireDays, String expireDaysType, String expireDaysTypeName,
			Date createDate, Date updateDate, String register) {
		super(code, message);
		this.productId = productId;
		this.title = title;
		this.type = type;
		this.typeName = typeName;
		this.typeDetail = typeDetail;
		this.typeDetailName = typeDetailName;
		this.status = status;
		this.statusName = statusName;
		this.seller = seller;
		this.usage = usage;
		this.amount = amount;
		this.prefix = prefix;
		this.couponNoLength = couponNoLength;
		this.maxUseCount = maxUseCount;
		this.delFlag = delFlag;
		this.expireDays = expireDays;
		this.expireDaysType = expireDaysType;
		this.expireDaysTypeName = expireDaysTypeName;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.register = register;
	}
	public void setResultCode(String code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}
	public void setResult(String code, String message, String productId,
			String title, String type, String typeName, String typeDetail,
			String typeDetailName, String status, String statusName,
			String seller, String usage, long amount, String prefix,
			int couponNoLength, int maxUseCount, String delFlag,
			int expireDays, String expireDaysType, String expireDaysTypeName,
			Date createDate, Date updateDate, String register) {
		this.setCode(code);
		this.setMessage(message);
		this.productId = productId;
		this.title = title;
		this.type = type;
		this.typeName = typeName;
		this.typeDetail = typeDetail;
		this.typeDetailName = typeDetailName;
		this.status = status;
		this.statusName = statusName;
		this.seller = seller;
		this.usage = usage;
		this.amount = amount;
		this.prefix = prefix;
		this.couponNoLength = couponNoLength;
		this.maxUseCount = maxUseCount;
		this.delFlag = delFlag;
		this.expireDays = expireDays;
		this.expireDaysType = expireDaysType;
		this.expireDaysTypeName = expireDaysTypeName;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.register = register;
	}
}