package kr.co.kpcard.backoffice.service.coupon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ProductBrochureForm {
	
	private String merchantId;
	private String merchantPw;
	
	private String title;
	private String type;
	private String seller;
	private String usage;
	private int amount; 
	private String prefix; 
	private int couponLength;
	private String status;
	private int expireDays;
	private int maxUseCnt;
	private String typeDetail;
	private String expireDaysType;
	private String prodCode;
	private String register;
	
	public ProductBrochureForm() { }
	
	public ProductBrochureForm(ProductBrochure brochure) {
		this.title = brochure.getTitle();
		this.type = brochure.getType();
		this.seller = brochure.getSeller();
		this.usage = brochure.getUsage();
		this.amount = brochure.getAmount();
		this.prefix = brochure.getPrefix();
		this.couponLength = brochure.getCouponNoLength();
		this.status = brochure.getStatus();
		this.expireDays = brochure.getExpireDays();
		this.maxUseCnt = brochure.getMaxUseCnt();
		this.typeDetail = brochure.getTypeDetail();
		this.expireDaysType = brochure.getExpireDaysType();
		this.prodCode = brochure.getProdCd();
		this.register = brochure.getCreateId();
	}
	
	
}
