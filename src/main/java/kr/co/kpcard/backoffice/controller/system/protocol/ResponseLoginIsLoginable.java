package kr.co.kpcard.backoffice.controller.system.protocol;

import kr.co.kpcard.backoffice.repository.system.model.Login;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLoginIsLoginable {
	private String message;	
	private String empId;
	private String name;
	private String position;
	private String email;
	private String phone;
	
	public void setLogin(String empId, String name, String position, String email, String phone)
	{
		this.empId = empId;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phone = phone;
	}
	
}
