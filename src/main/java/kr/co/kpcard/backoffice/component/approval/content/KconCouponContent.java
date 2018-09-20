package kr.co.kpcard.backoffice.component.approval.content;

import kr.co.kpcard.backoffice.service.coupon.protocol.KConDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KconCouponContent {
	
	private KConDetail coupon;
	
	public KconCouponContent() {}

	public KconCouponContent(KConDetail coupon) {
		this.coupon = coupon;
	}
}
