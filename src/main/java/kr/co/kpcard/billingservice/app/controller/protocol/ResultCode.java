package kr.co.kpcard.billingservice.app.controller.protocol;

public interface ResultCode 
{
	
	public static final String RESULT_CODE_SUCCESS = "200";
	public static final String RESULT_MSG_SUCCESS = "Success";
	
	public static final String RESULT_CODE_NO_RESERVE_MSG = "201";
	public static final String RESULT_MSG_NO_RESERVE_MSG = "Success Create Data";

	public static final String RESULT_CODE_GET_RESERVE_MSG_LIST = "210";
	public static final String RESULT_MSG_GET_RESERVE_MSG_LIST = "Fail Create Data";
	

	
	
	
	public static final String RESULT_CODE_NONE_REQ_PARAM = "401";
	public static final String RESULT_MSG_NONE_REQ_PARAM = "None Request Param";
	
	public static final String RESULT_CODE_NOT_VAILD_PARAM = "402";
	public static final String RESULT_MSG_NOT_VAILD_PARAM = "Not Vaild Request Param";
	
	public static final String RESULT_CODE_SERVER_ERROR = "500";
	public static final String RESULT_MSG_SERVER_ERROR = "Server Error";

}
