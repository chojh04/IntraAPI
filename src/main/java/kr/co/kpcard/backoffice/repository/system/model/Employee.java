package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
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
	private String useFlag;
	private String leaveDate;
	private String enteringDate;
}
