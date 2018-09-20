package kr.co.kpcard.backoffice.service.coupon;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kr.co.kpcard.backoffice.service.coupon.model.ProductBrochure;
import kr.co.kpcard.backoffice.service.coupon.protocol.KConDetail;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResAdminCoupon;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResPutAdminCoupon;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultCouponRestrict;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultGetKConBrochure;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultKConDetail;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultMakeBrochure;

@Service
public class CouponService {

	@Autowired
	private Environment environment; 
	
	@Autowired
	private RestTemplate rest;
	
	/**
	 * 충전권 조회
	 * @param couponNo
	 * @return
	 */
	public ResAdminCoupon readChargeCoupon(String couponNo) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("payment.server.baseUrl")+"/admin/v1/coupon")
				.queryParam("couponType", "100") //100은 충전권 //200은 교환권 인데 충전권만 사용 중.
				.queryParam("couponNo", couponNo)
				.build().toUri();
		
		ResAdminCoupon result = rest.getForObject(url, ResAdminCoupon.class);
		
		if ("K000".equals(result.getCode())) {
			return result;
		}
		else {
			throw new RuntimeException("충전권을 조회하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 충전권 연장
	 * @param couponNo
	 * @param endDate
	 */
	public void extendChargeCoupon(String couponNo, String endDate) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("payment.server.baseUrl")+"/admin/v1/coupon")
				.queryParam("actionType", "300") //100 : 사용제한, 200 : 사용해제, 300 : 연장
				.queryParam("couponNo", couponNo)
				//.queryParam("startDa te", startDate)
				.queryParam("expireDt", endDate)
				.build().toUri();
		
		ResponseEntity<ResPutAdminCoupon> result = rest.exchange(url, HttpMethod.PUT, null, ResPutAdminCoupon.class);
		
		if (!"K000".equals(result.getBody().getCode())) {
			throw new RuntimeException("충전권을 연장하는 중 오류가 발생하였습니다.");
		}
		
	}
	
	/**
	 * 충전권 사용해제
	 * @param couponNo
	 */
	public void unRestrictionChargeCoupon(String couponNo) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("payment.server.baseUrl")+"/admin/v1/coupon")
				.queryParam("actionType", "200") //100 : 사용제한, 200 : 사용해제, 300 : 연장
				.queryParam("couponNo", couponNo)
				.build().toUri();
		
		ResponseEntity<ResPutAdminCoupon> result = rest.exchange(url, HttpMethod.PUT, null, ResPutAdminCoupon.class);
		
		if (!"K000".equals(result.getBody().getCode())) {
			throw new RuntimeException("충전권을 사용해제 하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * KCON 상품 등록
	 * @param brochure
	 * @return
	 */
	public String createKconProductBrochure(ProductBrochure brochure) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/brochures/brochure")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("title", brochure.getTitle())
				.queryParam("type", brochure.getType())
				.queryParam("seller", brochure.getSeller())
				.queryParam("usage", brochure.getUsage())
				.queryParam("amount", brochure.getAmount())
				.queryParam("prefix", "")
				.queryParam("couponLength", brochure.getCouponNoLength())
				.queryParam("status", brochure.getStatus())
				.queryParam("expireDays", brochure.getExpireDays())
				.queryParam("maxUseCount", brochure.getMaxUseCnt())
				.queryParam("typeDetail", brochure.getTypeDetail())
				.queryParam("expireDaysType", brochure.getExpireDaysType())
				.queryParam("prodCode", 0)
				.queryParam("register", brochure.getCreateId())
				.build().toUri();
		ResultMakeBrochure result = rest.postForObject(url, null, ResultMakeBrochure.class);
		
		if ("K000".equals(result.getCode())) {
			return result.getProductId();
		}
		else {
			throw new RuntimeException("KCON 상품을 등록하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * KCON 상품 조회
	 * @param productId
	 * @return
	 */
	public ProductBrochure readProductBrochure(String productId) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/brochures/brochure")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("productId", productId)
				.build().toUri();
		
		ResultGetKConBrochure result = rest.getForObject(url, ResultGetKConBrochure.class);
		
		if ("K000".equals(result.getCode())) {
			return new ProductBrochure(result);
		}
		else {
			throw new RuntimeException("KCON 상품을 조회하는 중 오류가 발생하였습니다.");
		}
	}

	/**
	 * KCON 상품 수정
	 * @param brochure
	 */
	public void updateProductBrochure(ProductBrochure brochure) {
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/brochures/brochure")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("productId", brochure.getProdId())
				.queryParam("title", brochure.getTitle())
				.queryParam("expireDays", brochure.getExpireDays())
				.queryParam("expireDaysType", brochure.getExpireDaysType())
				.build().toUri();
		
		
		ResponseEntity<ResultMakeBrochure> result = rest.exchange(url, HttpMethod.PUT, null, ResultMakeBrochure.class);
		
		if (!"K000".equals(result.getBody().getCode())) { // not equals입니다.
			throw new RuntimeException("KCON 상품을 수정하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * KCON 상품 삭제
	 * @param productId
	 */
	public void deleteProductBrochure(String productId) {
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/brochures/brochure")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("productId", productId)
				.build().toUri();
		
		ResponseEntity<ResultMakeBrochure> result = rest.exchange(url, HttpMethod.DELETE, null, ResultMakeBrochure.class);
		
		if (!"K000".equals(result.getBody().getCode())) { // not equals입니다.
			throw new RuntimeException("KCON 상품을 삭제하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * KCON 쿠폰 조회
	 * @param couponNo
	 * @return
	 */
	public KConDetail readKconCoupon(String couponNo) {
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/infos")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("couponNo", couponNo)
				.build().toUri();
		
		ResultKConDetail result = rest.getForObject(url, ResultKConDetail.class);
		
		if ("K000".equals(result.getCode())) {
			KConDetail kconDetail = result.getList().stream().findFirst().get();
			return kconDetail;
		}
		else {
			throw new RuntimeException("KCON 쿠폰을 조회하는 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * KCON 쿠폰 연장
	 * @param couponNo
	 * @param startDate
	 * @param endDate
	 */
	public void extendKconCoupon(String couponNo, String startDate, String endDate) {
		
		//쿠폰 API에서 리스트로 받고 있으므로... 
		List<String> couponList = Arrays.asList(couponNo);
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/extend-date")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("couponList", String.join(",", couponList))
				.queryParam("startDate", startDate)
				.queryParam("extendDate", endDate)
				.build().toUri();
		ResultMakeBrochure result = rest.postForObject(url, null, ResultMakeBrochure.class);
		
		if (!"K000".equals(result.getCode())) {
			throw new RuntimeException("KCON 쿠폰을 연장하는 중 오류가 발생하였습니다.");
		}
	}
	
	public void unRestrictionKconCoupon(String couponNo, String restrictFlag) {
		
		//쿠폰 API에서 리스트로 받고 있으므로... 
		List<String> couponList = Arrays.asList(couponNo);
		
		URI url = UriComponentsBuilder
				.fromHttpUrl("http://"+environment.getRequiredProperty("coupon.server.baseUrl")+"/kcons/restrict")
				.queryParam("merchantId", environment.getRequiredProperty("coupon.server.kconID"))
				.queryParam("merchantPw", environment.getRequiredProperty("coupon.server.kconPW"))
				.queryParam("couponList", String.join(",", couponList))
				.queryParam("restrict", restrictFlag)
				.build().toUri();
		ResultCouponRestrict result = rest.postForObject(url, null, ResultCouponRestrict.class);
		
		if (!"K000".equals(result.getCode())) {
			throw new RuntimeException("KCON 쿠폰을 사용해제 하는 중 오류가 발생하였습니다.");
		}
	}
	
}
