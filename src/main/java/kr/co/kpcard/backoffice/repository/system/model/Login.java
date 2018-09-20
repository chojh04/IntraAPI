package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
	private String empId;
	private String name;
	private String position;
	private String email;
	private String phone;
}
