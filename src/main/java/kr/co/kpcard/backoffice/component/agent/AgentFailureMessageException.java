package kr.co.kpcard.backoffice.component.agent;

import lombok.Getter;

@Getter
public class AgentFailureMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private String customMsg;
	
	public AgentFailureMessageException(String message, String customMsg)
    {
        super(message);
        this.message = message;
        this.customMsg = customMsg;
    }

	
	
}
