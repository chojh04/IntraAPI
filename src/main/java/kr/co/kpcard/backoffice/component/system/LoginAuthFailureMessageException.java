package kr.co.kpcard.backoffice.component.system;

import lombok.Getter;

@Getter
public class LoginAuthFailureMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private boolean auth;
	private String customMsg;
	
	public LoginAuthFailureMessageException(String message, boolean auth, String customMsg)
    {
        super(message);
        this.message = message;
        this.auth = auth;
        this.customMsg = customMsg;
    }
}
