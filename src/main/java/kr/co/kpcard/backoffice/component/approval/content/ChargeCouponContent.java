package kr.co.kpcard.backoffice.component.approval.content;

import kr.co.kpcard.backoffice.service.coupon.protocol.ResAdminCoupon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeCouponContent {

	private ResAdminCoupon coupon;
	
	public ChargeCouponContent() { }
	
	public ChargeCouponContent(ResAdminCoupon coupon) {
		this.coupon = coupon;
	}
	
}
