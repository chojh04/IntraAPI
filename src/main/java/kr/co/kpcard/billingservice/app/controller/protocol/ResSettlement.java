package kr.co.kpcard.billingservice.app.controller.protocol;

public class ResSettlement extends ResBody {

	public ResSettlement()
	{	
		super(RESULT_CODE_SERVER_ERROR, RESULT_MSG_SERVER_ERROR);
	}
	
	public ResSettlement(String code, String message){
		super(code, message);
	}

	public void reset()
	{
		super.setCode(RESULT_CODE_SERVER_ERROR);
		super.setMessage(RESULT_MSG_SERVER_ERROR);
	}

	@Override
	public String toString() {
		return "ResSettlement [getCode()=" + getCode() + ", getMessage()=" + getMessage() + ", toStringForLog()="
				+ toStringForLog() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
