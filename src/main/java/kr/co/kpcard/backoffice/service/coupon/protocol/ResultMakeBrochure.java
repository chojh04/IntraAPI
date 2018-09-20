package kr.co.kpcard.backoffice.service.coupon.protocol;

import kr.co.kpcard.billingservice.app.controller.protocol.ResBody;
import lombok.Getter;
import lombok.Setter;

/**
 * Kcon 쿠폰 등록 응답 클래스
 * Created by @author : MinWook on 2018. 7. 17.
 *
 */
@Getter
@Setter
public class ResultMakeBrochure extends ResBody {
    private String productId;
    private boolean isRegisted;
}
