package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsPhone {
	private String recieverPhone;   
	private String recieverName; 
	private String senderPhone;  
	private String senderName;           
	private String managerPhone;
}
