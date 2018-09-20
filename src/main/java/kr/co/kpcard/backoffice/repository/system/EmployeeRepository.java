package kr.co.kpcard.backoffice.repository.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.backoffice.repository.system.mapper.EmployeeMapper;
import kr.co.kpcard.backoffice.repository.system.model.Employee;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeAuth;
import kr.co.kpcard.backoffice.repository.system.model.KpcEmpMenuInt;

@Repository
public class EmployeeRepository {
	
	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 직원 정보 조회
	 * @param empId
	 * @param name
	 * @param email
	 * @return
	 */
	public Employee readEmployee(String empId, String name, String email) {
		return employeeMapper.getEmployee(empId, name, email);
	}
	
	/**
	 * 직원 메뉴 권한 등록
	 * @param kpcEmpMenuInt
	 */
	public void createEmployeeAuth(KpcEmpMenuInt kpcEmpMenuInt) {
		employeeMapper.createEmployeeAuth(kpcEmpMenuInt);
	}

	/**
	 * 직원 메뉴 권한 조회
	 * @param empId
	 * @param menuId
	 */
	public KpcEmpMenuInt readEmployeeAuth(String empId, String menuId) {
		return employeeMapper.readEmployeeAuth(empId, menuId);
	}
	
	/**
	 * 로그인한 사용자가 승인 처리가 가능한 메뉴 조회
	 * @param loginEmpId
	 * @return
	 */
	public List<String> readEmployeeApprovalAuthList(String loginEmpId) {
		return employeeMapper.readEmployeeApprovalAuthList(loginEmpId);
	}
	
	/**
	 * 직원 메뉴 권한 수정
	 * @param kpcEmpMenuInt
	 */
	public void updateEmployeeAuth(KpcEmpMenuInt kpcEmpMenuInt) {
		employeeMapper.updateEmployeeAuth(kpcEmpMenuInt);
	}
	
	/**
	 * 직원 메뉴별 권한 조회
	 * @param empId
	 * @param menuName
	 * @param perMenuId
	 * @return
	 */
	public List<EmployeeAuth> readEmployeeAuthList(String empId, String menuName, String perMenuId) {
		return employeeMapper.readEmployeeAuthList(empId, menuName, perMenuId);
	}

}
