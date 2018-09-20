package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.EmployeeList;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEmployeeGetEmployees {
	private String message;
	private EmployeeSummary summary;
	private List<EmployeeList> resultList;
}
