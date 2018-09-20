package kr.co.kpcard.backoffice.repository.system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeList {
	private String rnum;
	private String employeeId; 
	private String divisionId;   
	private String divisionName;   
	private String teamId;
	private String teamName;
	private String name;
	private String position;
	private String positionName;

	public String getLinkUrl(){
		 return "/employees/emplyee?employeeId="+this.employeeId+"&name=&email=";
	}
}

