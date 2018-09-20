package kr.co.kpcard.backoffice.component.agent;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({"message"})
public class AgentFailureBadRequestResponse {

	private String message;
	
	
	public AgentFailureBadRequestResponse(String message)
	{
		this.message = message;
	}
	
	
	public void setAuthAndMessage(String message)
	{
		this.message = message;
	}
	
}
