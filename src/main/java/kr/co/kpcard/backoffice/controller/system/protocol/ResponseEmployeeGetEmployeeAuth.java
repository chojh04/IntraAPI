package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.EmployeeAuth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEmployeeGetEmployeeAuth {
	private String message;
	private List<EmployeeAuth> resultList;
}
