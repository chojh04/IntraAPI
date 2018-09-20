package kr.co.kpcard.backoffice.controller.system.protocol;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmployee {
	private String employeeId;
	private String divisionId;
	private String teamId;
	private String name;
	private String password;
	private String position;
	private String birthDate;
	private String gender;
	private String phone;
	private String email;
	private String enteringDate;
	private String hireDate;
	private String createId;
	private String updateId;
	
	public RequestEmployee(String employeeId, String divisionId, String teamId, String name, String password,
			String position, String birthDate, String gender, String phone, String email, String enteringDate,
			String hireDate, String createId, String updateId) {		
		this.employeeId = employeeId;
		this.divisionId = divisionId;
		this.teamId = teamId;
		this.name = name;
		this.password = password;
		this.position = position;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.enteringDate = enteringDate;
		this.hireDate = hireDate;
		this.createId = createId;
		this.updateId = updateId;
	}
	
	
}
