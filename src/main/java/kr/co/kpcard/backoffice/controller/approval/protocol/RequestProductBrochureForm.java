package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Data;

/**
 * Kcon [등록], [수정] 승인 요청 폼
 * Created by @author : MinWook on 2018. 7. 17.
 *
 */
@Data
public class RequestProductBrochureForm {
	
	//쿠폰 정보
	private String merchantId;
	private String merchantPassword;

	private String productId;
	private String title; 
	private String typeDetail;
	private String typeCode;
	private int amount;
	private int expireDays;
	private String expireDaysType;
	private String seller;
	private String usage;
	private int couponLength;
	private String status; 

	
	//승인 정보
	/**	승인신청자 사번 */
	private String reqEmpId;
	/**	승인요청 사유 */
	private String reqMemo;
	/**	승인자 사번 */
	private String apprEmpId;
}
