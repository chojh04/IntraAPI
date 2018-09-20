package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Data;

@Data
public class RequestCouponForm {
	
	private String couponNo;
	private String endDate;
	
	//승인 정보
	/**	승인신청자 사번 */
	private String reqEmpId;
	/**	승인요청 사유 */
	private String reqMemo;
	/**	승인자 사번 */
	private String apprEmpId;
}
