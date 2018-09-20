package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEmployeeGetEmployee {
	private String message;
	private String employeeId;
	private String divisionId;
	private String divisionName;
	private String teamId;
	private String teamName;
	private String name;
	private String position;
	private String positionName;
	private String birthDate;
	private String gender;
	private String genderName;
	private String phone;
	private String email;
	private String leaveDate;
	private String enteringDate;
	
	public void setResultValue(String employeeId, String divisionId, String divisionName, String teamId,
			String teamName, String name, String position, String positionName, String birthDate, String gender,
			String genderName, String phone, String email, String leaveDate, String enteringDate) {
		this.employeeId = employeeId;
		this.divisionId = divisionId;
		this.divisionName = divisionName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.name = name;
		this.position = position;
		this.positionName = positionName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.genderName = genderName;
		this.phone = phone;
		this.email = email;
		this.leaveDate = leaveDate;
		this.enteringDate = enteringDate;
	}
}
