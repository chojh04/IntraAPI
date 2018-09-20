package kr.co.kpcard.backoffice.repository.system.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import kr.co.kpcard.backoffice.repository.system.model.Login;

@Mapper
public interface LoginMapper {
	//로그인 가능여부 조회

	public static final String IS_LOGIN_ABLE = " SELECT EMP_ID, NAME, POSITION, EMAIL, PHONE "
											 + " FROM KPC_EMPLOYE"
											 + " WHERE EMAIL = #{employeeId} AND PASSWORD = #{password}";

	//로그인 내역 등록
	public static final String INSERT_LOGIN_LOG = "	INSERT INTO KPC_LOGIN_HST"
										        + "		("
										        + "		  SEQ"
										        + "		 ,SYSTEM_DIVIDER"
										        + "		 ,EMP_ID"
										        + "      ,LOGIN_DT"
										        + "		 ,LOGIN_STATUS"
										        + "      ,LOGIN_IP"
										        + "    	)"
										        + " VALUES ("
										        + "			(SELECT NVL(MAX(SEQ),0) + 1 FROM KPC_LOGIN_HST)"
										        + "   	   	,#{systemDivider}"
										        + "     	,#{employeeId}"
										        + "     	,SYSDATE"
										        + "      	,#{loginStatus}"
										        + "      	,#{loginIp}"
										        + "    	   )";
	
	
	//계정 상태 조회
	public static final String IS_LOGIN_STATUS = " WITH TMP AS ("
											   + " 				SELECT MAX(SEQ) SEQ"
											   + "        			  ,EMP_ID"
											   + " 				FROM KPC_LOGIN_HST"
											   + "  			WHERE LOGIN_STATUS   = 'LOGIN-0001'"
											   + "  				AND EMP_ID         = #{empId}"
											   + "      			AND SYSTEM_DIVIDER = #{systemDivider}" 
											   + "     			GROUP BY EMP_ID"
											   + "   			)"
											   + "    SELECT COUNT(1) cnt"
											   + "    FROM KPC_LOGIN_HST A"
											   + "        ,TMP B"
											   + "	  WHERE A.LOGIN_STATUS   = 'LOGIN-0002'"
											   + "			AND A.EMP_ID         = B.EMP_ID"
											   + "			AND A.EMP_ID         = #{empId}"
											   + "			AND A.SYSTEM_DIVIDER = #{systemDivider}"
											   + "			AND A.SEQ            > B.SEQ        ";
	
	@Select(value=IS_LOGIN_ABLE)
	@Results(value={
		@Result(property="empId", column="emp_id"),
		@Result(property="name", column="name"),
		@Result(property="position", column="position"),
		@Result(property="email", column="email"),
		@Result(property="phone", column="phone")
	})
	public Login isLoginable(@Param(value="employeeId") String employeeId, @Param(value="password") String password);
	
	@Insert(value=INSERT_LOGIN_LOG)
	public int loginHst(@Param(value="systemDivider") String systemDivider, @Param(value="employeeId") String employeeId
			, @Param(value="loginStatus") String loginStatus, @Param(value="loginIp") String loginIp);
	
	@Select(value=IS_LOGIN_STATUS)
	public int getLoginHstStatus(@Param(value="empId") String emp_id, @Param(value="systemDivider") String systemDivider);	
}
