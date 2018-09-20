package kr.co.kpcard.backoffice.component.agent;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({"auth", "message"})
public class AgentAuthBadRequestResponse {

	private boolean auth;
	private String message;
	
	
	public AgentAuthBadRequestResponse(boolean auth, String message)
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
