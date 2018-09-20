package kr.co.kpcard.backoffice.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployee;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployeeAuth;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployeePostEmployee;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployeePutEmployee;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployeePutPassword;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseEmployeeGetEmployee;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseEmployeeGetEmployeeAuth;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseEmployeeGetEmployees;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseEmployeeGetSendSMSPhoneNum;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import kr.co.kpcard.backoffice.repository.system.model.Employee;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeAuth;
import kr.co.kpcard.backoffice.repository.system.model.KpcEmpMenuInt;
import kr.co.kpcard.backoffice.repository.system.model.SendSmsPhone;
import kr.co.kpcard.backoffice.service.system.EmployeeService;
import kr.co.kpcard.backoffice.service.system.ResGetEmployees;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;

@RestController
public class EmployeeController  implements ResultCode{
	
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;	
	
	private static final String EMPLOYEES = "/employees";
	private static final String EMPLOYEE = "/employees/employee";
	private static final String EMPLOYEE_PASSWORD = EMPLOYEE+"/password";
	private static final String EMPLOYEE_AUTHS = EMPLOYEES+"/employeeAuths";
	private static final String EMPLOYEE_AUTH = EMPLOYEES+"/employeeAuth";
	private static final String EMPLOYEE_PHONE_NUMBER = EMPLOYEES+"/sendSmsPhoneNo";
	
	/**
	 * 1.직원 리스트 조회
	 * @param offset 페이지
	 * @param limit 노출갯수
	 * @param name 이름
	 * @param division 부서
	 * @param team 팀
	 * @return
	 */
	@RequestMapping(value = EMPLOYEES, method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEmployeeGetEmployees getEmployees(
			@RequestParam(value="offset", required=true, defaultValue="-1") Long offset,
			@RequestParam(value="limit", required=true, defaultValue="0") Long limit,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="division", required=false, defaultValue="") String division,
			@RequestParam(value="team", required=false, defaultValue="") String teamId
			)
	{
		ResponseEmployeeGetEmployees responseGetEmployees = new ResponseEmployeeGetEmployees();
		try
		{			
			//parameter validate
			if(offset<0)
			{
				responseGetEmployees.setMessage("Not Validate Parameter 'offset' requstValue="+offset);
				return responseGetEmployees;
			}
			else if(limit<1)
			{
				responseGetEmployees.setMessage("Not Validate Parameter 'limit' requstValue="+limit);
				return responseGetEmployees;
			}
			/*
			else if("".equals(name))
			{
				responseEmployees.setMessage("Not Validate Parameter 'name' requstValue="+name);
				return responseEmployees;
			}
			else if("".equals(division))
			{
				responseEmployees.setMessage("Not Validate Parameter 'division' requstValue="+division);
				return responseEmployees;
			}
			else if("".equals(team))
			{
				responseEmployees.setMessage("Not Validate Parameter 'team' requstValue="+team);
				return responseEmployees;
			}
			*/
			//직원 리스트 조회
			ResGetEmployees result = employeeService.getEmployees(limit, offset, name, division, teamId);
						
			//조건에 맞는 직원 리스트가 없는경우
			if(result==null || result.getEmployeeSummary().getCount()<1){
				responseGetEmployees.setMessage("Not Employee List 'Count' : "+result.getEmployeeSummary().getCount());
			}else{
				responseGetEmployees.setSummary(result.getEmployeeSummary());
				responseGetEmployees.setResultList(result.getEmployeeList());
			}
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			responseGetEmployees.setMessage("Exception : "+ e.toString());
			return responseGetEmployees;
		}
		return responseGetEmployees;
	}
	
	/**
	 * 2.직원 정보 조회
	 * @param employeeId 직원 사번
	 * @param email 이메일
	 * @param name 이름
	 * @return
	 */
	@RequestMapping(value = EMPLOYEE, method=RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEmployeeGetEmployee getEmployee(
			@RequestParam(value="employeeId", required=false, defaultValue="") String employeeId,
			@RequestParam(value="email", required=false, defaultValue="") String email,
			@RequestParam(value="name", required=false, defaultValue="") String name
			)
	{
		ResponseEmployeeGetEmployee responseGetEmployee = new ResponseEmployeeGetEmployee();
		try
		{			
			//parameter validate
			if("".equals(employeeId) && "".equals(email) && "".equals(name))
			{
				responseGetEmployee.setMessage("Not Validate Parameter");
				return responseGetEmployee;
			}			
			
			//직원 정보 조회
			Employee employee = employeeService.getEmployee(employeeId, name, email);
			
			//조건에 맞는 직원 정보가 없을 경우
			if(employee == null){
				responseGetEmployee.setMessage("Not Employee Data");
			}else{
				responseGetEmployee.setResultValue(employee.getEmployeeId()
												  ,employee.getDivisionId()
												  ,employee.getDivisionName()
												  ,employee.getTeamId()
												  ,employee.getTeamName()
												  ,employee.getName()
												  ,employee.getPosition()
												  ,employee.getPositionName()
												  ,employee.getBirthDate()
												  ,employee.getGender()
												  ,employee.getGenderName()
												  ,employee.getPhone()
												  ,employee.getEmail()
												  ,employee.getLeaveDate()
												  ,employee.getEnteringDate()
												  );
			}						
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			responseGetEmployee.setMessage("Exception : "+ e.toString());
			return responseGetEmployee;
		}
		return responseGetEmployee;
	}
	
	/**
	 * 3. 직원 등록
	 * @param employeeId 직웢 사번
	 * @param divisionId 부서 Code
	 * @param teamId 팀 Code
	 * @param name 이름
	 * @param password 비밀번호
	 * @param position 직급
	 * @param birthDate 생년월일 (YYYYMMDD)
	 * @param gender 성별 (0,1)
	 * @param phone 핸드폰번호
	 * @param email 이메일
	 * @param enteringDate 가입일자 (YYYY/MM/DD HH:ii:ss)
	 * @param createId 등록자 사번
	 * @return
	 */
	@RequestMapping(value=EMPLOYEE, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> postEmployee(
			@RequestBody RequestEmployeePostEmployee requestEmployeePostEmployee
			)
	{		
		ResponseString response = new ResponseString();
		String returnMessage="";		
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;	
		
		logger.info("employeeId"+ requestEmployeePostEmployee.getEmployeeId());
		try{
			//parameter validate
			boolean validate = true;
		
			if("".equals(requestEmployeePostEmployee.getEmployeeId()))
			{
				returnMessage="Not Validate Parameter 'employeeId' requstValue="+requestEmployeePostEmployee.getEmployeeId();			
				validate=false;
			}
			else if("".equals(requestEmployeePostEmployee.getDivisionId()))
			{
				returnMessage="Not Validate Parameter 'divisionId' requstValue="+requestEmployeePostEmployee.getDivisionId();				
				validate=false;
			}
			else if("".equals(requestEmployeePostEmployee.getEnteringDate()))
			{
				returnMessage="Not Validate Parameter 'enteringDate' requstValue="+requestEmployeePostEmployee.getEnteringDate();				
				validate=false;
			}
			else if("".equals(requestEmployeePostEmployee.getCreateId()))
			{
				returnMessage="Not Validate Parameter 'createId' requstValue="+requestEmployeePostEmployee.getCreateId();				
				validate=false;
			}
			
			if(!validate){
				response.setMessage(returnMessage);
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = employeeService.postEmployee(new RequestEmployee(requestEmployeePostEmployee.getEmployeeId(), 
																			  requestEmployeePostEmployee.getDivisionId(),
																			  requestEmployeePostEmployee.getTeamId(),
																			  requestEmployeePostEmployee.getName(),
																			  requestEmployeePostEmployee.getPassword(),
																			  requestEmployeePostEmployee.getPosition(),
																			  requestEmployeePostEmployee.getBirthDate(),
																			  requestEmployeePostEmployee.getGender(),
																			  requestEmployeePostEmployee.getPhone(),
																			  requestEmployeePostEmployee.getEmail(),
																			  requestEmployeePostEmployee.getEnteringDate(), "",
																			  requestEmployeePostEmployee.getCreateId(), "")
																	);
			if(result){
				returnMessage="Employee Name '"+requestEmployeePostEmployee.getName()+"' saved.";
				httpStatus=HttpStatus.CREATED;
			}
			else if(!result){
				returnMessage="Employee Name '"+requestEmployeePostEmployee.getName()+"' not saved.";
				httpStatus=HttpStatus.BAD_REQUEST;
			}
			response.setMessage(returnMessage);
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, httpStatus);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 4. 직원 정보 수정
	 * @param employeeId
	 * @param divisionId
	 * @param teamId
	 * @param name
	 * @param password
	 * @param position
	 * @param birthDate
	 * @param gender
	 * @param phone
	 * @param email
	 * @param enteringDate
	 * @param updateId
	 * @return
	 */
	@RequestMapping(value=EMPLOYEE, method=RequestMethod.PUT, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> putEmployee(
			@RequestBody RequestEmployeePutEmployee requestPutEmployee
			)
	{	
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;		
		try{
			//parameter validate
			if("".equals(requestPutEmployee.getEmployeeId()))
			{
				response.setMessage("Not Validate Parameter 'employeeId' requstValue="+requestPutEmployee.getEmployeeId());				
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestPutEmployee.getDivisionId()))
			{
				response.setMessage("Not Validate Parameter 'divisionId' requstValue="+requestPutEmployee.getDivisionId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestPutEmployee.getEnteringDate()))
			{
				response.setMessage("Not Validate Parameter 'enteringDate' requstValue="+requestPutEmployee.getEnteringDate());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestPutEmployee.getUpdateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+requestPutEmployee.getUpdateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = employeeService.putEmployee(new RequestEmployee(	requestPutEmployee.getEmployeeId(),
																				requestPutEmployee.getDivisionId(),
																				requestPutEmployee.getTeamId(),
																				requestPutEmployee.getName(), 
																				requestPutEmployee.getPassword(),
																				requestPutEmployee.getPosition(),
																				requestPutEmployee.getBirthDate(),
																				requestPutEmployee.getGender(),
																				requestPutEmployee.getPhone(),
																				requestPutEmployee.getEmail(),
																				requestPutEmployee.getEnteringDate(),
																				"", "",
																				requestPutEmployee.getUpdateId())
																			);
			
			if(result){
				response.setMessage("Employee Name '"+requestPutEmployee.getName()+"' update.");
				httpStatus=HttpStatus.CREATED;
			}
			else if(!result){
				response.setMessage("Employee Name '"+requestPutEmployee.getName()+"' not update.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
			
				
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 5. 직원 비밀번호 변경
	 * @param employeeId
	 * @param beforePassword
	 * @param newPassword
	 * @return String
	 */
	@RequestMapping(value=EMPLOYEE_PASSWORD, method=RequestMethod.PUT, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> putPassword(@RequestBody RequestEmployeePutPassword reqPutPassword)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST; 
		try{
			//parameter validate
			if("".equals(reqPutPassword.getEmployeeId()))
			{
				response.setMessage("Not Validate Parameter 'employeeId' requstValue="+reqPutPassword.getEmployeeId());				
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(reqPutPassword.getBeforePassword()))
			{
				response.setMessage("Not Validate Parameter 'BeforePassword' requstValue="+reqPutPassword.getBeforePassword());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(reqPutPassword.getNewPassword()))
			{
				response.setMessage("Not Validate Parameter 'NewPassword' requstValue="+reqPutPassword.getNewPassword());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = employeeService.putPassword(reqPutPassword.getEmployeeId(), reqPutPassword.getBeforePassword(), reqPutPassword.getNewPassword());
			
			if(result){
				httpStatus = HttpStatus.CREATED;
				response.setMessage("employeId : "+reqPutPassword.getEmployeeId()+" password Change Successed");
			}else{
				response.setMessage("employeId : "+reqPutPassword.getEmployeeId()+" password Change Failed");
			}
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 6. 직원 정보 삭제
	 * @param employeeId
	 * @return 
	 */
	@RequestMapping(value=EMPLOYEE, method=RequestMethod.DELETE, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> deleteEmployee(
			@RequestParam(value="employeeId", required=true, defaultValue="") String employeeId)
	{
		ResponseString response= new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(employeeId))
			{
				response.setMessage("Not Validate Parameter 'employeeId' requstValue="+employeeId);
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = employeeService.deleteEmployee(employeeId);
			
			if(result){
				response.setMessage("Employee ID '"+employeeId+"' delete successed.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result){
				response.setMessage("Employee ID '"+employeeId+"' delete failed.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<ResponseString>(response, httpStatus);
		}		
		return new ResponseEntity<ResponseString>(response, httpStatus);				
	}
	
	/**
	 * 직원 메뉴별 권한 조회
	 * @param employeeId
	 * @param menuName
	 * @param parMenuId
	 * @return
	 */
	@GetMapping("/employee/auth-list")
	public ResponseEntity<List<EmployeeAuth>> readEmployeeAuthList(
			@RequestParam(value="empId") String empId,
			@RequestParam(value="menuName", required=false) String menuName,
			@RequestParam(value="parMenuId", required=false) String parMenuId) {

		List<EmployeeAuth> employeeAuth = employeeService.readEmployeeAuthList(empId, menuName, parMenuId);
		
		return ResponseEntity.ok(employeeAuth);
	}
	
	/**
	 * 직원 메뉴별 권한 수정
	 * @param form
	 * @return
	 */
	@PostMapping("/employee/auth")
	public ResponseEntity<Void> updateEmployeeAuth(@RequestBody List<KpcEmpMenuInt> form ) {
		
		employeeService.updateEmployeeAuth(form);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * 9. SMS 발송 핸드폰번호 조회
	 * @param recieverId
	 * @param senderId
	 * @return
	 */
	@RequestMapping(value=EMPLOYEE_PHONE_NUMBER, method=RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEmployeeGetSendSMSPhoneNum GetSendSMSPhoneNum(
			@RequestParam(value="recieverId", required=true, defaultValue="") String recieverId,
			@RequestParam(value="senderId", required=true, defaultValue="") String senderId
			)
	{
		ResponseEmployeeGetSendSMSPhoneNum responseGetSendSMSPhoneNum = new ResponseEmployeeGetSendSMSPhoneNum();
		try{
			if("".equals(recieverId))
			{
				responseGetSendSMSPhoneNum.setMessage("Not Validate Parameter 'employeeId' requstValue="+recieverId);
				return responseGetSendSMSPhoneNum;
			}
			else if("".equals(senderId))
			{
				responseGetSendSMSPhoneNum.setMessage("Not Validate Parameter 'divisionId' requstValue="+senderId);
				return responseGetSendSMSPhoneNum;
			}
			
			SendSmsPhone sendSmsPhone = employeeService.getSendSmsPhone(recieverId, senderId);
			if(sendSmsPhone==null)
			{
				responseGetSendSMSPhoneNum.setMessage("Not Matched data requestValue['recieverId' : "+recieverId+", 'senderId' : "+senderId);
				return responseGetSendSMSPhoneNum;
			}
			else
			{
				responseGetSendSMSPhoneNum.setMessage("Success!");
				responseGetSendSMSPhoneNum.setPhoneNum(sendSmsPhone.getRecieverPhone(), sendSmsPhone.getRecieverName(), sendSmsPhone.getSenderPhone(), sendSmsPhone.getSenderName(), sendSmsPhone.getManagerPhone());				
			}
			
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responseGetSendSMSPhoneNum.setMessage("Exception : "+ e.toString());
			return responseGetSendSMSPhoneNum;
		}
		return responseGetSendSMSPhoneNum;
	}
	
	
	/**
 * 7. 직원 메뉴별 권한 조회
	 * @param employeeId
	 * @param menuName
	 * @param parMenuId
	 * @return
	 */
@RequestMapping(value=EMPLOYEE_AUTHS, method=RequestMethod.GET, produces="application/json")
public @ResponseBody ResponseEmployeeGetEmployeeAuth getEmployeeAuth(
		@RequestParam(value="empId", required=false, defaultValue="") String employeeId,
		@RequestParam(value="menuName", required=false, defaultValue="") String menuName,
		@RequestParam(value="parMenuId", required=false, defaultValue="") String parMenuId
		)	
{
	ResponseEmployeeGetEmployeeAuth responseGetEmployeeAuth = new ResponseEmployeeGetEmployeeAuth();
	try{
		/*
		if("".equals(employeeId))
		{
			responseGetEmployeeAuth.setMessage("Not Validate Parameter 'employeeId' requstValue="+employeeId);
			return responseGetEmployeeAuth;
		}
		else if("".equals(menuName))
		{
			responseGetEmployeeAuth.setMessage("Not Validate Parameter 'menuName' requstValue="+menuName);
			return responseGetEmployeeAuth;
		}
		*/
		List<EmployeeAuth> employeeAuth = employeeService.getEmployeeAuth(employeeId, menuName, parMenuId);
		
		if(employeeAuth==null){
			responseGetEmployeeAuth.setMessage("Not Employee Auth Data");
		}
		else{				
			responseGetEmployeeAuth.setResultList(employeeAuth);
		}			
	}catch(Exception e)
	{
		logger.error("Exception : "+ e.toString());
		responseGetEmployeeAuth.setMessage("Exception : "+ e.toString());
		return responseGetEmployeeAuth;
	}
	return responseGetEmployeeAuth;

	}

	/**
 * 8. 직원 메뉴별 권한 등록
 * @param employeeId
 * @param menuId
 * @param insFlag
 * @param updFlag
 * @param delFlag
 * @param selFlag
 * @param apprFlag
 * @param createId
 * @param updateId
 * @return
	 */
@RequestMapping(value=EMPLOYEE_AUTH, method=RequestMethod.POST, produces="application/json")
public @ResponseBody ResponseEntity<ResponseString> postEmployeeAuth(
		@RequestBody List<RequestEmployeeAuth> requestParam	
		)
{
	ResponseString response = new ResponseString();		
	HttpStatus httpStatus = HttpStatus.FORBIDDEN;
	boolean validate = true;

	int resultCount=0;
	try{			
		for(RequestEmployeeAuth eachValue :  requestParam){
			if("".equals(eachValue.getEmpId()))
			{
				response.setMessage("Not Validate Parameter 'employeeId' requstValue="+eachValue.getEmpId());
				validate = false;
			}
			else if("".equals(eachValue.getMenuId()))
			{
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+eachValue.getMenuId());
				validate = false;
			}
			else if("".equals(eachValue.getInsFlag()) 
					|| "".equals(eachValue.getUpdFlag())
					|| "".equals(eachValue.getDelFlag())
					|| "".equals(eachValue.getSelFlag())
					|| "".equals(eachValue.getApprFlag())
				   )
			{
				response.setMessage("Not Validate Parameter 'flag' requstValue[insFlag:"+eachValue.getInsFlag()+", updFlag:"+eachValue.getUpdFlag()+", delFlag:"+eachValue.getDelFlag()+", selFlag:"+eachValue.getSelFlag()+", apprFlag:"+eachValue.getApprFlag()+"]");
				validate = false;
			}
			else if("".equals(eachValue.getCreateId()) || "".equals(eachValue.getUpdateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' or 'updateId' requstValue[createId:"+eachValue.getCreateId()+", updateId:"+eachValue.getUpdateId()+"]");
				validate = false;
			}
			logger.info("requstValue[insFlag:"+eachValue.getInsFlag()+", updFlag:"+eachValue.getUpdFlag()+", delFlag:"+eachValue.getDelFlag()+", selFlag:"+eachValue.getSelFlag()+", apprFlag:"+eachValue.getApprFlag()+"]");
			
			if(!validate) return new ResponseEntity<ResponseString>(response, httpStatus);
			/*
			boolean result =  employeeService.postEmployeeAuth(new EmployeeAuth(eachValue.getEmpId(),
																		eachValue.getMenuId(),
																		eachValue.getInsFlag(),
																		eachValue.getUpdFlag(),
																		eachValue.getDelFlag(),
																		eachValue.getSelFlag(),
																		"0",
																		eachValue.getCreateId(),
																		eachValue.getUpdateId())
											  		);
				*/			
			boolean result =  employeeService.postEmployeeAuth(new EmployeeAuth(eachValue.getEmpId(),
					eachValue.getMenuId(),
					eachValue.getInsFlag(),
					eachValue.getUpdFlag(),
					eachValue.getDelFlag(),
					eachValue.getSelFlag(),
					"0",
					eachValue.getCreateId(),
					eachValue.getUpdateId())
		  		);
			if(result) resultCount++;
		}
		
		if(resultCount>0){
			response.setMessage("EmployeeAuth employeeId '"+requestParam.get(0).getEmpId()+"' insert.");
			httpStatus = HttpStatus.CREATED;
		
		}else{
			response.setMessage("EmployeeAuth employeeId '"+requestParam.get(0).getEmpId()+"' not insert.");
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		
	}catch(Exception e)
	{
		logger.error("Exception : "+ e.toString());
		response.setMessage("Exception : "+ e.toString());
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	return new ResponseEntity<ResponseString>(response, httpStatus);		
	}
	
}
