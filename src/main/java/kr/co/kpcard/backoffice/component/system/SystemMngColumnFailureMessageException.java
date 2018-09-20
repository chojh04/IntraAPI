package kr.co.kpcard.backoffice.component.system;

import lombok.Getter;

@Getter
public class SystemMngColumnFailureMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private boolean status;
	private String customMsg;
	
	public SystemMngColumnFailureMessageException(boolean status, String customMsg)
    {        
        this.status = status;        
        this.customMsg = customMsg;
    }	
}
