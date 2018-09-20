package kr.co.kpcard.backoffice.controller.system.protocol;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmployeePutPassword {
	private String employeeId;
	private String beforePassword;
	private String newPassword;
}
