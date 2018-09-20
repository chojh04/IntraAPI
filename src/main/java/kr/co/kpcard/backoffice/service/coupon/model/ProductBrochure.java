package kr.co.kpcard.backoffice.service.coupon.model;

import java.util.Date;

import kr.co.kpcard.backoffice.service.coupon.protocol.ResultGetKConBrochure;
import lombok.Getter;
import lombok.Setter;

/**
 * KCON 상품 클래스
 * Created by @author : MinWook on 2018. 7. 16.
 *
 */
@Getter
@Setter
public class ProductBrochure {
	private String prodId;
	private String title;
	private String type;
	private String typeName;
	private String seller;
	private String usage;
	private int amount; 
	private String prefix; 
	private int couponNoLength;
	private String status;
	private String statusName;
	private int expireDays;
	private String expireDaysType;
	private String expireDaysTypeName;
	private int maxUseCnt;
	private String typeDetail;
	private String typeDetailName;
	private String delFlag;
	private String prodCd;
	private String createId;
	private String createName;
	private Date createDate;
	private Date updateDate;
	
	public ProductBrochure() { }
	
	public ProductBrochure(ResultGetKConBrochure resultKConBrochure) {
		this.prodId = resultKConBrochure.getProductId();
		this.title = resultKConBrochure.getTitle();
		this.type = resultKConBrochure.getType();
		this.typeName = resultKConBrochure.getTypeName();
		this.seller = resultKConBrochure.getSeller();
		this.usage = resultKConBrochure.getUsage();
		this.amount = (int) resultKConBrochure.getAmount();
		this.prefix = resultKConBrochure.getPrefix();
		this.couponNoLength = resultKConBrochure.getCouponNoLength();
		this.status = resultKConBrochure.getStatus();
		this.statusName = resultKConBrochure.getStatusName();
		this.expireDays = resultKConBrochure.getExpireDays();
		this.expireDaysType = resultKConBrochure.getExpireDaysType();
		this.expireDaysTypeName = resultKConBrochure.getExpireDaysTypeName();
		this.maxUseCnt = resultKConBrochure.getMaxUseCount();
		this.typeDetail = resultKConBrochure.getTypeDetail();
		this.typeDetailName = resultKConBrochure.getTypeDetailName();
		this.delFlag = resultKConBrochure.getDelFlag();
		this.createId = resultKConBrochure.getRegister();
		this.createDate = resultKConBrochure.getCreateDate();
		this.updateDate = resultKConBrochure.getUpdateDate();
	}
	
}
