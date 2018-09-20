package kr.co.kpcard.backoffice.repository.system.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employees {
	private EmployeeSummary employeeSummary;
	private List<EmployeeList> employeeList;
	
}
