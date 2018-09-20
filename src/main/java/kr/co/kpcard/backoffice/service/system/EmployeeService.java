package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import kr.co.kpcard.backoffice.component.KpCardTransactionDefinition;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployee;
import kr.co.kpcard.backoffice.repository.system.EmployeeRepository;
import kr.co.kpcard.backoffice.repository.system.mapper.EmployeeMapper;
import kr.co.kpcard.backoffice.repository.system.model.Employee;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeAuth;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeList;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeSummary;
import kr.co.kpcard.backoffice.repository.system.model.KpcEmpMenuInt;
import kr.co.kpcard.backoffice.repository.system.model.SendSmsPhone;
import kr.co.kpcard.common.utils.EncodeString;

@Service
public class EmployeeService {
	
	Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	@Qualifier("backOfficeTransactionManager")
	private DataSourceTransactionManager backOfficeTransactionManager;
	
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	EmployeeRepository employeeRepository; 
	

	
	EncodeString encodeString = new EncodeString();
	
	/**
	 * 직원 리스트 조회 서비스
	 * @param limit 페이지
	 * @param offset 리스트 수
	 * @param name 이름
	 * @param division 부서
	 * @param teamId 팀
	 * @return ResGetEmployees
	 */
	public ResGetEmployees getEmployees(long limit, long offset, String name, String division, String teamId)
	{
		ResGetEmployees resGetEmployees = new ResGetEmployees();
		
		long count = employeeMapper.getEmployeesListCount(name, division, teamId);
		resGetEmployees.setEmployeeSummary(new EmployeeSummary(count, limit, offset));		
		
		logger.info("count : "+ count);
		
		//조건에 맞는 회원이 없는경우 return
		if(count<1)	return resGetEmployees;
		
		List<EmployeeList> employeeList = employeeMapper.getEmployeeList(name, division, teamId, offset, limit);		
		resGetEmployees.setEmployeeList(employeeList);
		
		return resGetEmployees;		
	}
	
	/**
	 * 직원 정보 조회 서비스
	 * @param name 이름
	 * @param employeeId 아이디
	 * @param email 이메일
	 * @return Employee
	 */
	public Employee getEmployee(String employeeId, String name, String email)
	{
		Employee employee = employeeMapper.getEmployee(employeeId, name, email);
		
		return employee;		
	}
	
	/**
	 * 직원 정보 등록 서비스
	 * @return boolean
	 */
	public boolean postEmployee(RequestEmployee requestEmployee)
	{
		boolean result = false;
		
		try{
			String encodePwd = encodeString.encodeSha512(requestEmployee.getPassword());
			requestEmployee.setPassword(encodePwd);
			employeeMapper.postEmployee(requestEmployee);
			result = true;
		}
		catch(Exception e)
		{
			logger.error("Exception e"+e.toString());	
			result = false;
			return result;
		}
		return result;		
	}	
	
	/**
	 * 직원 정보 수정 서비스
	 * @return boolean
	 */
	public boolean putEmployee(RequestEmployee requestEmployee)
	{
		boolean result = false;
		
		try{
			requestEmployee.setPassword(encodeString.encodeSha512(requestEmployee.getPassword()));
			
			long updateResult = employeeMapper.putEmployee(requestEmployee);
			if(updateResult>0) result=true;		
		}
		catch(Exception e)
		{
			logger.error("Exception e"+e.toString());	
			result = false;
			return result;
		}
		return result;		
	}	
	
	/**
	 * 직원 비밀번호 변경 서비스
	 * @return boolean
	 */
	public boolean putPassword(String employeeId, String beforePassword, String newPassword)
	{
		boolean result = false;
		
		try{
			long updateResult = employeeMapper.putPassword(employeeId, encodeString.encodeSha512(beforePassword), encodeString.encodeSha512(newPassword));
			logger.info("Update Data Count : "+updateResult);
			if(updateResult>0) result=true;		
		}
		catch(Exception e)
		{
			logger.error("Exception e"+e.toString());	
			result = false;
			return result;
		}
		return result;		
	}	
	

	/**
	 * 직원 정보 삭제 서비스
	 * @return boolean
	 */
	public boolean deleteEmployee(String employeeId)
	{
		boolean result = false;
		
		try{
			long deleteResult = employeeMapper.deleteEmployee(employeeId);
			logger.info("Delete Data Count : "+deleteResult);
			if(deleteResult>0) result=true;		
		}
		catch(Exception e)
		{
			logger.error("Delete Error!!");
			result = false;
			return result;
		}
		return result;		
	}	
	
	
	/**
	 * 직원 메뉴 권한 조회 서비스
	 * @param empId
	 * @param menuId
	 * @param perMenuId
	 * @return
	 */
	public List<EmployeeAuth> readEmployeeAuthList(String empId, String menuName, String perMenuId)	{
		
		return employeeRepository.readEmployeeAuthList(empId, menuName, perMenuId);

	}
	
	/**
	 * 직원 메뉴별 권한 수정
	 * @param menuAuthList
	 */
	public void updateEmployeeAuth(List<KpcEmpMenuInt> menuAuthList) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createEmployeeAuth");
		
		try {
			for(KpcEmpMenuInt menuAuth : menuAuthList) {
				
				if(menuAuth.getEmpId() != null && menuAuth.getMenuId() != null) {
					//KPC_EMP_MENU_INT 테이블 조회
					KpcEmpMenuInt employeeMenuAuth = employeeRepository.readEmployeeAuth(menuAuth.getEmpId(), menuAuth.getMenuId());
					
					//조회되는 메뉴 권한이 없다면 권한 등록, 있다면 권한 수정 
					if (employeeMenuAuth == null) {
						employeeRepository.createEmployeeAuth(menuAuth);
					}
					else {
						employeeRepository.updateEmployeeAuth(menuAuth);
					}
				}
				else {
					//FIXME 공통 예외처리.
					throw new RuntimeException("직원 메뉴별 권한 수정에 필요한 사번, 메뉴ID가 없습니다.");
				}
			}
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("직원 메뉴별 권한 등록 중 오류 발생 : "+e.getMessage());
			throw e;
		}
	}
	
	/**
	 * SMS 발송번호 조회 서비스
	 * @param recieverId
	 * @param senderId
	 * @return
	 */
	public SendSmsPhone getSendSmsPhone(String recieverId, String senderId)
	{		
		SendSmsPhone sendSmsPhone = employeeMapper.getSendSmsPhone(recieverId,senderId);
		
		return sendSmsPhone;
	}
	 	
 	/**
 	 * 직원 메뉴 권한 조회 서비스(승인기능 적용되면 삭제 해야함)
	 * @param employeeId
 	 * @param menuId
 	 * @param perMenuId
	 * @return List<EmployeeAuth>
 	 */
	public List<EmployeeAuth> getEmployeeAuth(String employeeId, String menuName, String perMenuId)
	{			
		logger.info("insert dgetet");
		List<EmployeeAuth> employeeAuth = employeeMapper.getEmployeeAuth(employeeId, menuName, perMenuId);			
		return employeeAuth;
 	}

 	
 	/**
	 * 직원 메뉴 권한 등록 서비스(승인기능 적용되면 삭제 해야함)
	 * @param employeeAuth
	 * @return boolean
 	 */
	public boolean postEmployeeAuth(EmployeeAuth employeeAuth){
		boolean result = false;
		try{		
			//임시로set (real app_flag column 없음)
			employeeAuth.setApprFlag("0");
			employeeMapper.postEmployeeAuth(employeeAuth);
			result = true;
		}catch(Exception e){			
			logger.error("Insert Error!!"+e.toString());
			return result;
 		}
		return result;		
 	}
	
}
