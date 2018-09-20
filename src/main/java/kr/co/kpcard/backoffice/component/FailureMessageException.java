package kr.co.kpcard.backoffice.component;

import lombok.Getter;

@Getter
public class FailureMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message; 
	private String customMsg;
	
	public FailureMessageException(String message, String customMsg)
    {
        super(message);
        this.message = message;
        this.customMsg = customMsg;
    }

	
	
}
