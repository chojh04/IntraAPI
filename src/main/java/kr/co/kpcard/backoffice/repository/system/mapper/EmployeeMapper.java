package kr.co.kpcard.backoffice.repository.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.controller.system.protocol.RequestEmployee;
import kr.co.kpcard.backoffice.repository.system.model.Employee;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeAuth;
import kr.co.kpcard.backoffice.repository.system.model.EmployeeList;
import kr.co.kpcard.backoffice.repository.system.model.KpcEmpMenuInt;
import kr.co.kpcard.backoffice.repository.system.model.SendSmsPhone;

@Mapper
public interface EmployeeMapper {
	//회원 리스트 수 조회
	public static final String GET_EMPLOYEE_LIST_COUNT = "<script>"
													   + " SELECT " 
									                   + " 		COUNT(1) AS CNT  " 
									                   + " FROM KPC_EMPLOYE A "
									                   + " WHERE 1 = 1"
									                   + " 		<if test='division!=\"\" and division!=null'> AND A.DIVISION_ID = #{division} </if>"
									                   + " 		<if test='name!=\"\" and name!=null'> AND A.NAME LIKE '%'||#{name}||'%'</if>"
									                   + " 		<if test='teamId!=\"\" and teamId!=null'> AND A.TEAM_ID = #{teamId} </if>"
													   + " </script>";

	//직원 리스트 조회
	public static final String GET_EMPLOYEE_LIST = "<script>"												
												 + " SELECT * FROM" 
										         + "	(SELECT ROWNUM AS RNUM, A.*"
										         + "		FROM"
										         + "		(SELECT A.EMP_ID"
										         + "	   			,A.DIVISION_ID"
										         + "		        ,D.name divisionName"
										         + "       			,A.TEAM_ID" 
										         + "		        ,B.name teamName"
										         + "       			,A.NAME"
										         + "       			,A.POSITION"
										         + "       			,C.name positionName" 
										         + "		FROM KPC_EMPLOYE A" 
										         + "        	,KPC_ADMIN_CODE B"
										         + "        	,KPC_ADMIN_CODE C"
										         + "        	,KPC_ADMIN_CODE D"
										         + "		WHERE 1=1"
										         + "			AND A.position     = C.CODE(+)"    
										         + "			AND A.team_id      = B.CODE(+)"    
										         + "			AND A.division_id  = D.CODE(+)"
										         + "			<if test='division!=\"\" and division!=null'> AND A.DIVISION_ID = #{division} </if>"
										         + "			<if test='name!=\"\" and name!=null'> AND A.NAME LIKE '%'||#{name}||'%' </if>"
										         + "			<if test='teamId!=\"\" and teamId!=null'> AND A.TEAM_ID = #{teamId} </if>"        
										         + "	    ORDER BY EMP_ID DESC) A "
										         + "	WHERE ROWNUM &lt;= (#{limit}+#{offset})"
										         + "	ORDER BY ROWNUM ASC )"
										         + " WHERE RNUM &gt; #{offset} "
												 + " </script>";	
	//직원정보 조회
	public static final String GET_EMPLOYEE = " <script>"
											+ " SELECT A.emp_id" 
						              		+ "		 ,A.division_id" 
						              		+ "		 ,B.name divisionName" 
						              		+ "		 ,A.team_id" 
						              		+ "      ,C.name teamName"
						              		+ "      ,A.name"
						              		+ "      ,A.password"
						              		+ "      ,A.position"
						              		+ "      ,C.name positionName"
						              		+ "      ,A.birth_dt"
						              		+ "      ,A.gender"
						              		+ "      ,FNC_ADMIN_CODE_TO_NAME(A.gender) genderName"
						              		+ "      ,A.phone"
						              		+ "      ,A.email"
						              		+ "      ,A.use_flag"
						              		+ "      ,A.hire_dt"
						              		+ "      ,A.resign_dt"
						              		+ " FROM KPC_EMPLOYE A"
						              		+ "		 ,KPC_ADMIN_CODE B"
						              		+ "      ,KPC_ADMIN_CODE C"
						              		+ "      ,KPC_ADMIN_CODE D"
						              		+ " WHERE A.division_id = B.code(+)"
						              		+ "		AND A.team_id     = C.code(+)"
						              		+ "   	AND A.position    = D.code(+)"
						              		+ "   	<if test='employeeId!=\"\" and employeeId!=null'>AND A.EMP_ID=#{employeeId}</if>"
						              		+ "   	<if test='name!=\"\" and name!=null'>AND A.NAME LIKE '%'||#{name}||'%'</if>"
						              		+ "   	<if test='email!=\"\" and email!=null'>AND A.TEAM_ID=#{email}</if>"
						              		+ " </script>";
	
	//직원정보 등록
	public static final String INSERT_EMPLOYEE = " INSERT INTO KPC_EMPLOYE ( EMP_ID, DIVISION_ID, TEAM_ID, NAME,"
                  						      + "						   PASSWORD, POSITION, BIRTH_DT, GENDER,"
                  						      + "						   PHONE, EMAIL, USE_FLAG, HIRE_DT,CREATE_ID)" 
                  						      + "				  VALUES ( #{employeeId}, #{divisionId}, #{teamId}, #{name},"
                  						      + "			#{password}, #{position}, #{birthDate}, #{gender},"
                  						      + "						   #{phone}, #{email}, 'Y', #{enteringDate} , #{createId})";
	
	//회원정보 수정
	public static final String UPDATE_EMPOYEE = " UPDATE KPC_EMPLOYE"
              								 + " 	SET division_id  = #{divisionId}	"// --부서 ID"
              								 + " 	   ,team_id = #{teamId}   			"// --팀 ID"
              								 + "	   ,name = #{name}   				"// --이름"
              								 + "	   ,position = #{position}   		"// --직위"
              								 + "	   ,birth_dt = #{birthDate}   		"// --생년월일"
              								 + "	   ,gender = #{gender}   			"// --성별"
              								 + "	   ,phone = #{phone}   				"// --휴대전화"
              								 + "	   ,email = #{email}   				"// --이메일"
              								 + "	   ,hire_dt = #{enteringDate}   	"// --입사일자"
              								 + "	   ,resign_dt = #{hireDate}   		"// --퇴사일자"
              								 + "	   ,update_id = #{updateId}   		"// --변경자"
              								 + "	   ,update_dt = SYSDATE"
              								 + "	WHERE emp_id = #{employeeId}";
	
	//비밀번호 변경
	public static final String UPDATE_PASSWORD = " UPDATE KPC_EMPLOYE"
              								   + "		SET password   = #{newPassword}"  
              								   + "		   ,update_id  = #{employeeId}"
              								   + "		   ,update_dt  = SYSDATE"
              								   + "		WHERE emp_id   = #{employeeId}"
              								   + "			AND password = #{oldPassword}";
	
	//회원정보 삭제
	public static final String DELETE_EMPLOYEE = " DELETE FROM KPC_EMPLOYE WHERE emp_id = #{employeeId}";
	
	//SMS발송 번호 조회
	public static final String GET_SMS_PHONE_NUMBER = " SELECT PHONE recieverPhone"
													+ "		 , NAME recieverName"
										            + "		 , ("
										            + "    		 SELECT PHONE" 
										            + "    		 FROM KPC_EMPLOYE"
										            + "   		 WHERE EMP_ID = #{senderId}"     
										            + " 	   ) senderPhone"
										            + "		 , ("
										            + "    		 SELECT name"
										            + " 	     FROM KPC_EMPLOYE"
										            + "		   	 WHERE EMP_ID = #{senderId}"     
										            + "		   ) senderName"     
										            + " 	 , ("
										            + "    		 SELECT phone"
										            + "   		 FROM KPC_EMPLOYE"
										            + "    		 WHERE EMP_ID = '060402'"     
										            + "    	   )  managerPhone"
										            + "	FROM KPC_EMPLOYE"
										            + "	WHERE EMP_ID = #{recieverId}";

	//직원 리스트 수 조회
	@Select(value=GET_EMPLOYEE_LIST_COUNT)
	public int getEmployeesListCount(@Param(value="name") String name, @Param(value="division") String division, @Param(value="teamId") String teamId);
	
	//직원 리스트 조회
	@Select(value=GET_EMPLOYEE_LIST)
	@Results(value={
		@Result(property="rnum", column="RNUM"),
		@Result(property="employeeId", column="emp_id"),
		@Result(property="divisionId", column="division_id"),
		@Result(property="divisionName", column="divisionName"),
		@Result(property="teamId", column="team_id"),
		@Result(property="teamName", column="teamName"),
		@Result(property="name", column="name"),
		@Result(property="position", column="position"),
		@Result(property="positionName", column="positionName")		
	})
	public List<EmployeeList> getEmployeeList(@Param(value="name") String name,
											  @Param(value="division") String division,
											  @Param(value="teamId") String teamId,
											  @Param(value="offset") long offset,
											  @Param(value="limit") long limit);	
	//직원 정보 조회
	@Select(value=GET_EMPLOYEE)
	@Results(value={
			@Result(property="employeeId", column="emp_id"),
			@Result(property="divisionId", column="division_id"),
			@Result(property="divisionName", column="divisionName"),
			@Result(property="teamId", column="team_id"),
			@Result(property="teamName", column="teamName"),
			@Result(property="name", column="name"),
			@Result(property="position", column="position"),
			@Result(property="positionName", column="positionName"),
			@Result(property="birthDate", column="birth_dt"),
			@Result(property="gender", column="gender"),
			@Result(property="genderName", column="genderName"),
			@Result(property="phone", column="phone"),
			@Result(property="email", column="email"),			
			@Result(property="enteringDate", column="hire_dt"),
			@Result(property="leaveDate", column="resign_dt")
		})
	public Employee getEmployee(@Param(value="employeeId") String employeeId,@Param(value="name") String name,@Param(value="email") String email);
	
	@Insert(value=INSERT_EMPLOYEE)
	public void postEmployee(RequestEmployee requestEmployee);
	
	@Update(value=UPDATE_EMPOYEE)
	public long putEmployee(RequestEmployee requestEmployee);
	
	@Update(value=UPDATE_PASSWORD)
	public long putPassword(@Param(value="employeeId")String employeeId, @Param(value="oldPassword") String oldPassword, @Param(value="newPassword") String newPassword);
	
	@Delete(value=DELETE_EMPLOYEE)
	public long deleteEmployee(String employeeId);

	//회원권한 조회
	public static final String READ_EMPLOYEE_AUTH_LIST = "<script>" 
												 + " SELECT B.name 			"// --메뉴명"               
									             + "	   ,A.emp_menu_id   "// --사용자별 메뉴 ID SEQ" 
									             + "	   ,A.emp_id        "// --직원 ID"              
									             + " 	   ,B.menu_id       "// --MENU ID"             
									             + "       ,A.ins_flag      "// --등록 가능여부"        
									             + " 	   ,A.upd_flag      "// --수정 가능여부"        
									             + "	   ,A.del_flag      "// --삭제 가능여부"        
									             + "	   ,A.sel_flag      "// --조회 가능여부"        
									             + "	   ,A.appr_flag     "// --승인 가능여부"        
									             + " FROM KPC_EMP_MENU_INT A"                     
									             + "	 ,KPC_MENU B"                             
									             + " WHERE A.menu_id(+) = B.menu_id"               
									             + " 	AND B.del_Flag   = '0'"
									             + "	AND A.emp_id(+)  = #{empId}"
									             + " 	AND B.name    LIKE '%' || #{menuName} || '%'"
									             + "	<if test='parMenuId!=\"\" and parMenuId!=null'> START WITH B.par_menu_id = #{parMenuId} CONNECT BY PRIOR B.menu_id = B.par_menu_id </if>"
									             + "	<if test='parMenuId==\"\" or parMenuId==null'> AND B.par_menu_id IS NULL ORDER BY B.menu_id </if>"
									             + "</script>";
	@Select(value=READ_EMPLOYEE_AUTH_LIST)
	@Results(value={
			@Result(property="name", column="name"),
			@Result(property="employeeMenuId", column="emp_menu_id"),
			@Result(property="employeeId", column="emp_id"),
			@Result(property="menuId", column="menu_id"),
			@Result(property="insFlag", column="ins_flag"),
			@Result(property="updFlag", column="upd_flag"),
			@Result(property="delFlag", column="del_flag"),
			@Result(property="selFlag", column="sel_flag"),
			@Result(property="apprFlag", column="appr_flag")
			})
	public List<EmployeeAuth> readEmployeeAuthList(@Param(value="empId") String empId, @Param(value="menuName") String menuName, @Param(value="parMenuId") String perMenuId);
	
	//직원 메뉴 권한 등록
	public static final String CREATE_EMPLOYEE_AUTH = "<script>"
											        + "		INSERT INTO KPC_EMP_MENU_INT "
											        + "     	("
											        + "   	    		 emp_menu_id   "// --사용자별 메뉴 ID"
											        + "    	    		,emp_id        "// --직원 ID"
											        + "		    	    ,menu_id       "// --MENU ID"
											        + "		    	    ,ins_flag      "// --등록 가능여부"
											        + "		    	    ,upd_flag      "// --수정 가능여부"
											        + "		    	    ,del_flag      "// --삭제 가능여부"
											        + "			   	    ,sel_flag      "// --조회 가능여부"
											        + "			        ,appr_flag     "// --승인 가능여부"
											        + "		    	    ,create_id     "// --생성자"
											        + "		    	    ,create_dt     "// --생성일" 
											        + "      			)"
											        + "      	VALUES ("
											        + "   	     		SEQ_EMP_MENU_ID.nextval      "// --사용자별 메뉴 ID"  
											        + "		    	    ,#{empId} 		"// --직원 ID"
											        + "		    	    ,#{menuId} 		"// --MENU ID"
											        + " 			    ,#{insFlag, jdbcType=VARCHAR} 	"// --등록 가능여부"
											        + " 	    		,#{updFlag, jdbcType=VARCHAR} 	"// --수정 가능여부"
											        + "    	    		,#{delFlag, jdbcType=VARCHAR} 	"// --삭제 가능여부"
											        + "    	    		,#{selFlag, jdbcType=VARCHAR} 	"// --조회 가능여부"
											        + "    	    		,#{apprFlag, jdbcType=VARCHAR} 	"// --승인 가능여부"
											        + "    	    		,#{createId} 	"// --생성자"
											        + "    	    		,SYSDATE        "// --생성일"    
											        + "      			)"
													+ "</script>";
	@Insert(value=CREATE_EMPLOYEE_AUTH)
	public void createEmployeeAuth(KpcEmpMenuInt kpcEmpMenuInt);
	
	//직원 메뉴 권한 조회
	public static final String READ_EMPLOYEE_AUTH = "<script>" 
												 + " SELECT  			"               
									             + "	    emp_menu_id as empMenuId  "// 사용자별 메뉴 ID SEQ" 
									             + "	   ,emp_id as empId       "// 직원 ID"              
									             + " 	   ,menu_id as menuId      "// 메뉴 ID"             
									             + "       ,ins_flag as insFlag     "// 등록 권한"        
									             + " 	   ,upd_flag as updFlag     "// 수정 권한"        
									             + "	   ,del_flag as delFlag     "// 삭제 권한"        
									             + "	   ,sel_flag as selFlag     "// 조회 권한"        
									             + "	   ,appr_flag as apprFlag     "// 승인 권한"
									             + "	   ,create_id as createId     "// 생성자 사번"
									             + "	   ,create_dt as createDate     "// 생성일"
									             + "	   ,update_id as updateId     "// 수정자 사번"
									             + "	   ,update_dt as updateDate     "// 수정일"
									             + " FROM "
									             + " KPC_EMP_MENU_INT "                     
									             + " WHERE "               
									             + "	emp_id = #{empId} "
									             + "	and menu_id = #{menuId} "
									             + "</script>";
	@Select(value=READ_EMPLOYEE_AUTH)
	public KpcEmpMenuInt readEmployeeAuth(@Param("empId")String empId, @Param("menuId")String menuId); 
	
	public static final String READ_EMPLOYEE_APPROVAL_AUTH_LIST = "<script>"
			+ " SELECT "
			+ "		empMenu.menu_id "
			+ " FROM "
			+ " 	kpc_emp_menu_int empMenu, kpc_menu menu "
			+ " WHERE "
			+ " 	empMenu.menu_id = menu.menu_id "
			+ " 	AND empMenu.emp_id = #{loginEmpId} "
			+ " 	AND menu.del_flag = '0' "
			+ " 	AND empMenu.menu_id LIKE 'MENU-%' "
			+ " 	AND empMenu.appr_flag = '1' "
			+ "</script>"; 
	@Select(READ_EMPLOYEE_APPROVAL_AUTH_LIST)
	public List<String> readEmployeeApprovalAuthList(@Param("loginEmpId")String loginEmpId);
	
	//직원 메뉴 권한 수정
	public static final String UPDATE_EMPLOYEE_AUTH = "<script>"
											        + "UPDATE KPC_EMP_MENU_INT SET "
											        + "  ins_flag = #{insFlag, jdbcType=VARCHAR}   "// --등록 가능여부"
											        + " ,upd_flag = #{updFlag, jdbcType=VARCHAR}   "// --수정 가능여부"
											        + " ,del_flag = #{delFlag, jdbcType=VARCHAR}   "// --삭제 가능여부"
											        + " ,sel_flag = #{selFlag, jdbcType=VARCHAR}   "// --조회 가능여부"
											        + " ,appr_flag = #{apprFlag, jdbcType=VARCHAR}   "// --승인 가능여부"
											        + " ,update_id = #{updateId}            "// --변경자"
											        + " ,update_dt  = SYSDATE 	"// --변경일"
										            + " WHERE "               
										            + "	emp_id = #{empId} "
										            + "	and menu_id = #{menuId} "
													+ "</script>";
	@Update(value=UPDATE_EMPLOYEE_AUTH)
	public void updateEmployeeAuth(KpcEmpMenuInt kpcEmpMenuInt);
	
	@Select(value=GET_SMS_PHONE_NUMBER)
	@Results(value={
			@Result(property="recieverPhone", column="recieverPhone"),
			@Result(property="recieverName", column="recieverName"),
			@Result(property="senderPhone", column="senderPhone"),
			@Result(property="senderName", column="senderName"),
			@Result(property="managerPhone", column="managerPhone")
		})
	public SendSmsPhone getSendSmsPhone(@Param(value="recieverId") String recieverId, @Param(value="senderId") String senderId);

 	
	//회원권한 조회
	public static final String GET_EMPLOYEE_AUTH = "<script>" 
												 + " SELECT B.name 			"// --메뉴명"               
									             + "	   ,A.emp_menu_id   "// --사용자별 메뉴 ID SEQ" 
									             + "	   ,A.emp_id        "// --직원 ID"              
									             + " 	   ,B.menu_id       "// --MENU ID"             
									             + "       ,A.ins_flag      "// --등록 가능여부"        
									             + " 	   ,A.upd_flag      "// --수정 가능여부"        
									             + "	   ,A.del_flag      "// --삭제 가능여부"        
									             + "	   ,A.sel_flag      "// --조회 가능여부"        
									             //+ "	   ,A.appr_flag     "// --승인 가능여부"        
									             + " FROM KPC_EMP_MENU_INT A"                     
									             + "	 ,KPC_MENU B"                             
									             + " WHERE A.menu_id(+) = B.menu_id"               
									             + " 	AND B.del_Flag   = '0'"
									             + "	AND A.emp_id(+)  = #{employeeId}"
									             + " 	AND B.name    LIKE '%' || #{menuName} || '%'"
									             + "	<if test='parMenuId!=\"\" and parMenuId!=null'> START WITH B.par_menu_id = #{parMenuId} CONNECT BY PRIOR B.menu_id = B.par_menu_id </if>"
									             + "	<if test='parMenuId==\"\" or parMenuId==null'> AND B.par_menu_id IS NULL ORDER BY B.menu_id </if>"
									             + "</script>";
	
	//직원 메뉴별 권한 등록
	public static final String INSERT_EMPLOYEE_AUTH = "<script>"
													+ "MERGE"                                                                               
											        + "		INTO KPC_EMP_MENU_INT A"
											        + "    	USING ("
											        + "    			SELECT #{employeeId} emp_id		"// --직원 ID"
											        + "           		  ,#{menuId} menu_id " 
											        + "      		FROM DUAL "
											        + "			  ) B"
											        + "		ON ( "
											        + "			 A.menu_id = B.menu_id"
											        + "			 AND A.emp_id  = B.emp_id "
											        + "		   )"
											        + "    	WHEN MATCHED THEN"
											        + "  	  	UPDATE SET A.ins_flag   = DECODE(#{insFlag}, ins_flag, ins_flag, #{insFlag})   "// --등록 가능여부"
											        + " 	       		  ,A.upd_flag   = DECODE(#{updFlag}, upd_flag, upd_flag, #{updFlag})   "// --수정 가능여부"
											        + "     	       	  ,A.del_flag   = DECODE(#{delFlag}, del_flag, del_flag, #{delFlag})   "// --삭제 가능여부"
											        + "         	      ,A.sel_flag   = DECODE(#{selFlag}, sel_flag, sel_flag, #{selFlag})   "// --조회 가능여부"
											        //+ "             	  ,A.appr_flag  = DECODE(#{apprFlag}, appr_flag, appr_flag, #{apprFlag})   "// --승인 가능여부"
											        + "               	  ,A.update_id  = #{updateId}            "// --변경자"
											        + "               	  ,A.update_dt  = SYSDATE                                 "// --변경일" 
											        + "		WHEN NOT MATCHED THEN"
											        + "     	INSERT ("
											        + "   	    		 A.emp_menu_id   "// --사용자별 메뉴 ID"
											        + "    	    		,A.emp_id        "// --직원 ID"
											        + "		    	    ,A.menu_id       "// --MENU ID"
											        + "		    	    ,A.ins_flag      "// --등록 가능여부"
											        + "		    	    ,A.upd_flag      "// --수정 가능여부"
											        + "		    	    ,A.del_flag      "// --삭제 가능여부"
											        + "			   	    ,A.sel_flag      "// --조회 가능여부"
											        //+ "			        ,A.appr_flag     "// --승인 가능여부"
											        + "		    	    ,A.create_id     "// --생성자"
											        + "		    	    ,A.create_dt     "// --생성일"  
											        + "      			)"
											        + "      	VALUES ("
											        + "   	     		SEQ_EMP_MENU_ID.nextval      "// --사용자별 메뉴 ID"  
											        + "		    	    ,#{employeeId} 		"// --직원 ID"
											        + "		    	    ,#{menuId} 		"// --MENU ID"
											        + " 			    ,#{insFlag} 	"// --등록 가능여부"
											        + " 	    		,#{updFlag} 	"// --수정 가능여부"
											        + "    	    		,#{delFlag} 	"// --삭제 가능여부"
											        + "    	    		,#{selFlag} 	"// --조회 가능여부"
											       // + "    	    		,#{apprFlag} 	"// --승인 가능여부"
											        + "    	    		,#{createId} 	"// --생성자"
											        + "    	    		,SYSDATE        "// --생성일"    
											        + "      			)"
													+ "</script>";
	
		
	@Select(value=GET_EMPLOYEE_AUTH)
	@Results(
			value={
			@Result(property="name", column="name"),
			@Result(property="employeeMenuId", column="emp_menu_id"),
			@Result(property="employeeId", column="emp_id"),
			@Result(property="menuId", column="menu_id"),
			@Result(property="insFlag", column="ins_flag"),
			@Result(property="updFlag", column="upd_flag"),
			@Result(property="delFlag", column="del_flag"),
			@Result(property="selFlag", column="sel_flag"),
			//@Result(property="apprFlag", column="appr_flag")
 			})
	public List<EmployeeAuth> getEmployeeAuth(@Param(value="employeeId") String employeeId, @Param(value="menuName") String menuName, @Param(value="parMenuId") String perMenuId);
	
 	@Insert(value=INSERT_EMPLOYEE_AUTH)
	public void postEmployeeAuth(EmployeeAuth employeeAuth);
	
	
}
