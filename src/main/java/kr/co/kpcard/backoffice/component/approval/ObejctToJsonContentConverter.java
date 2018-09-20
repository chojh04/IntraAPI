package kr.co.kpcard.backoffice.component.approval;

import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.approval.content.ChargeCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.KconCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.MerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.ProductBrochureContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantSVCBillingContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantServiceContent;
import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
import kr.co.kpcard.backoffice.service.coupon.model.ProductBrochure;
import kr.co.kpcard.backoffice.service.coupon.protocol.KConDetail;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResAdminCoupon;

/**
 * Object 데이터를 객체화(Object > Json String) 시키는 변환 컨버터
 * Created by @author : MinWook on 2018. 6. 14.
 *
 */
public abstract class ObejctToJsonContentConverter {

	/**
	 * 대표 거래처 정보 타입 변환
	 * @param merchant
	 * @return  String 
	 */
	public static String setMerchantJsonContent(Merchant merchant) {
		Gson gson = new Gson();
		
		MerchantContent content = new MerchantContent();
		content.setMerchant(merchant);
		
		return gson.toJson(content);
	}
	
	/**
	 * 일반 거래처 정보 타입 변환
	 * @param merchant
	 * @return  String 
	 */
	public static String setSubMerchantJsonContent(SubMerchant subMerchant) {
		Gson gson = new Gson();
		
		SubMerchantContent content = new SubMerchantContent();
		content.setSubMerchant(subMerchant);
		
		return gson.toJson(content);
	}

	/**
	 * 거래처 서비스 정보 타입 변환
	 * @param merchant
	 * @return  String 
	 */
	public static String setSubMerchantServiceJsonContent(SubMerchantService service) {
		Gson gson = new Gson();
		
		SubMerchantServiceContent content = new SubMerchantServiceContent();
		content.setService(service);
		
		return gson.toJson(content);
	}
	
	/**
	 * 서비스 정산 정보 타입 변환
	 * @param merchant
	 * @return  String 
	 */
	public static String setSubMerchantSVCBillingJsonContent(SubMerchantSVCBilling billing) {
		Gson gson = new Gson();
		
		SubMerchantSVCBillingContent content = new SubMerchantSVCBillingContent();
		content.setBilling(billing);
		
		return gson.toJson(content);
	}

	/**
	 * 충전권 정보 타입 변환 Object > JSON String
	 * @param coupon
	 * @return
	 */
	public static String setChargeCouponJsonContent(ResAdminCoupon coupon) {
		Gson gson = new Gson();
		
		ChargeCouponContent content = new ChargeCouponContent(coupon);
		
		return gson.toJson(content);
	}
	
	/**
	 * KCON 상품 정보 타입 변환
	 * @param brochure
	 * @return
	 */
	public static String setProductBrochureJsonContent(ProductBrochure brochure) {
		Gson gson = new Gson();
		
		ProductBrochureContent content = new ProductBrochureContent();
		content.setBrochure(brochure);
		
		return gson.toJson(content);
	}
	
	/**
	 * KCON 쿠폰 정보 타입 변환
	 * @param kconDetail
	 * @return
	 */
	public static String setKconCouponJsonContent(KConDetail kconDetail) {
		Gson gson = new Gson();
		
		KconCouponContent content = new KconCouponContent(kconDetail);
		
		return gson.toJson(content);
	}
}
