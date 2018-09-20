package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.List;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultCouponRestrict extends ResBody {

	private List<String> couponNoList;
    private boolean isRestricted;
    
}
