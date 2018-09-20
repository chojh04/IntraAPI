package kr.co.kpcard.backoffice.controller.system.protocol;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmployeePostEmployee {
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
	private String createId;
}
