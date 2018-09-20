package kr.co.kpcard.backoffice.component.system;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({"auth", "message"})
public class LoginAuthBadRequestResponse {

	private boolean auth;
	private String message;
	
	
	public LoginAuthBadRequestResponse(boolean auth, String message)
	{
		this.auth = auth;
		this.message = message;
	}
	
	
	public void setAuthAndMessage(boolean auth, String message)
	{
		this.auth = auth;
		this.message = message;
	}
	
}
