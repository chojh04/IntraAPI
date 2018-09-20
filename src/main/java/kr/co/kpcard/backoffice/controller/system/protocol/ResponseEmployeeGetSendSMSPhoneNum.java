package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEmployeeGetSendSMSPhoneNum {
	private String recieverPhone;
    private String recieverName;
    private String senderPhone;
    private String senderName;
    private String managerPhone;
    private String message;
    
	public void setPhoneNum(String recieverPhone, String recieverName, String senderPhone,
			String senderName, String managerPhone) {
		this.recieverPhone = recieverPhone;
		this.recieverName = recieverName;
		this.senderPhone = senderPhone;
		this.senderName = senderName;
		this.managerPhone = managerPhone;
	}
    
}
