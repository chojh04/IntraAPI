package kr.co.kpcard.backoffice.component.approval;

import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.approval.content.ChargeCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.KconCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.MerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.ProductBrochureContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantSVCBillingContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantServiceContent;

/**
 * Json 데이터를 객체화(Json String > Object) 시키는 변환 컨버터
 * Created by @author : MinWook on 2018. 6. 12.
 *
 */
public abstract class JsonContentToObjectConverter {
	
	/**
	 * 대표 거래처 정보 타입 변환
	 * @param content
	 * @return MerchantContent Object
	 */
	public static MerchantContent convertMerchantContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, MerchantContent.class);
	}

	/**
	 * 일반 거래처 정보 타입 변환
	 * @param content
	 * @return SubMerchantContent Object
	 */
	public static SubMerchantContent convertSubMerchantContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, SubMerchantContent.class);
	}
	
	/**
	 * 거래처 서비스 정보 타입 변환
	 * @param content
	 * @return SubMerchantServiceContent Object
	 */
	public static SubMerchantServiceContent convertSubMerchantServiceContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, SubMerchantServiceContent.class);
	}
	
	/**
	 * 서비스 정산 정보 타입 변환
	 * @param content
	 * @return SubMerchantSVCBillingContent Object
	 */
	public static SubMerchantSVCBillingContent convertSubMerchantSVCBillingContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, SubMerchantSVCBillingContent.class);
	}
	

	/**
	 * 충전권 정보 타입 변환 JSON -> Object
	 * @param content
	 * @return
	 */
	public static ChargeCouponContent convertChargeCouponContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, ChargeCouponContent.class);
	}
	
	/**
	 * KCON 상품 정보 타입 변환
	 * @param content
	 * @return
	 */
	public static ProductBrochureContent convertKconProductBrochureContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, ProductBrochureContent.class);
	}
	
	/**
	 * KCON 쿠폰 정보 타입 변환
	 * @param content
	 * @return
	 */
	public static KconCouponContent convertKconCouponContent(String content) {
		Gson gson = new Gson();
		return gson.fromJson(content, KconCouponContent.class);
	}
	
	
	
	
}
