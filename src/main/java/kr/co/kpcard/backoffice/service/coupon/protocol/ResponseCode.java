package kr.co.kpcard.backoffice.service.coupon.protocol;

public interface ResponseCode {

	public final static String RES_CODE_SERVER_ERROR = "K999";
	public final static String RES_MSG_SERVER_ERROR = "서버 에러";
	
	public final static String RES_CODE_CHECK_SERVICE = "K800";
	public final static String RES_MSG_CHECK_SERVICE = "서비스 점검 중";
	
	
	public final static String RES_CODE_SUCCESS = "K000";
	public final static String RES_MSG_SUCCESS = "성공";
	
	public final static String RES_CODE_INVALID_REQUEST = "K001";
	public final static String RES_MSG_INVALID_REQUEST = "유효하지 않은 요청입니다.(요청 파라미터 확인 필요)";
		
	public final static String RES_CODE_INVALID_COUPON = "K100";
	public final static String RES_MSG_INVALID_COUPON = "유효하지 않은 쿠폰 입니다.";
	
	public final static String RES_CODE_USED_COUPON = "K102";
	public final static String RES_MSG_USED_COUPON = "이미 사용된 쿠폰입니다.";
	
	public final static String RES_CODE_EXPIRED_COUPON = "K103";
	public final static String RES_MSG_EXPIRED_COUPON = "유효기간 만료 쿠폰입니다.";
	
	public final static String RES_CODE_USED_LIMIT_COUPON = "K104";
	public final static String RES_MSG_USED_LIMIT_COUPON = "사용 제한 쿠폰입니다.";
	
	public final static String RES_CODE_FAILED_ISSUE_COUPON = "K105";
	public final static String RES_MSG_FAILED_ISSUE_COUPON = "발권 실패입니다.";
	
	public final static String RES_CODE_DUPLICATED_ORDERNO = "K106";
	public final static String RES_MSG_DUPLICATED_ORDERNO = "주문번호 중복입니다.";
	
	public final static String RES_CODE_FAIL_TO_CANCEL = "K200";
	public final static String RES_MSG_FAIL_TO_CANCEL = "판매 취소가 실패하였습니다.";
	
	public final static String RES_CODE_INVALID_CANCEL = "K201";
	public final static String RES_MSG_INVALID_CANCEL = "판매 건이 없습니다.";
	
	public final static String RES_CODE_INVALID_CANCELTYPE = "K203";
	public final static String RES_MSG_INVALID_CANCELTYPE = "취소 코드 오류.";
	
	public final static String RES_CODE_INVALID_PINNO = "K205";
	public final static String RES_MSG_INVALID_PINNO = "쿠폰번호 불일치.";
	
	
	
}
