package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.EmployeeList;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetEmployees {
	private EmployeeSummary employeeSummary;
	private List<EmployeeList> employeeList;
}
