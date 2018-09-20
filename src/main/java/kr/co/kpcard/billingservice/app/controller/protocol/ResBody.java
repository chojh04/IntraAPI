package kr.co.kpcard.billingservice.app.controller.protocol;

public class ResBody implements ResultCode{

	// 응답 코드 
	private String code;
	
	// 응답 메세지 
	private String message;
	
	
	public ResBody(){}
	
	public ResBody(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	public String getCode() 
	{
		return code;
	}
	
	public void setCode(String code) 
	{
		this.code = code;
	}
	
	public String getMessage() 
	{
		return message;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	public String toStringForLog()
	{
		return String.format("code : %s, message : %s", 
				code, message);
	}
	
}
