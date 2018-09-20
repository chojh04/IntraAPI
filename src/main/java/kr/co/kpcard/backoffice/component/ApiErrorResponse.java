package kr.co.kpcard.backoffice.component;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({"code", "message"})
public class ApiErrorResponse {

	private String code;
	private String message;
	
	
	public ApiErrorResponse(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	
	public void setCodeAndMessage(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
}
