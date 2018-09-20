package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.List;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultCouponExtendDate extends ResBody {

	private List<String> couponNo;
    private boolean isExtendDate;

}
