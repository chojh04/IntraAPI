package kr.co.kpcard.backoffice.component.system;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({"status", "message"})
public class SystemMngTableColumnRequestResponse {

	private String message;
	private boolean status;	
	
	public SystemMngTableColumnRequestResponse(boolean status, String message)
	{
		this.status = status;
		this.message = message;
	}
	
	
	public void setAuthAndMessage(boolean status, String message)
	{
		this.status = status;
		this.message = message;
	}
	
}
