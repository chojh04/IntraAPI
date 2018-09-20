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

import kr.co.kpcard.backoffice.repository.system.model.SystemMngAuthMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngCommonCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuAuth;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuUrl;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistory;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTableColumn;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTypeCode;

@Mapper
public interface SystemMngMapper {

	//메뉴 조회
	public static final String SYSTEM_MNG_GET_MENU = "<script>"
													+ " SELECT * "
													+ " FROM ( SELECT menu_id "	// 메뉴ID
													+ "		 		,name "  	// 메뉴명
													+ "		 		,par_menu_id " 	// 상위 메뉴id
													+ "		 		,(SELECT B.name "  
			 										+ "  				FROM KPC_MENU B "
			 										+ "					WHERE A.par_menu_id = B.menu_id "  
			 									    + "               	AND A.del_flag = '0' "  
			 									    + "		   		 ) parMenuName "
			 									    + "				,menu_url "
			 									    + "		 		,create_id "
			 									    + "		 		,update_id "
			 									    + "		 		,update_dt "
			 									    + "		 		,create_dt "
			 									    + "		 		,SUBSTR(menu_id, INSTR(menu_id, '-',1) + 1) ORDERNUMBER "
			 									    + "		  	FROM KPC_MENU A "
			 									    + "		  	WHERE del_flag = '0' "
			 									    + "			<if test='name!=\"\" and name!=null'> AND name = '%'||#{name}||'%' </if>"
			 									    + "			<if test='menuId!=\"\" and menuId!=null'> AND menu_id = '%'||#{menuId}||'%' </if>"
			 									    + "			<if test='parMenuId!=\"\" and parMenuId!=null'> AND par_menu_id = '%'||#{parMenuId}||'%' </if>"
			 									    + "			<if test='selType==1'> AND par_menu_id IS NULL </if>"
			 									    + "			<if test='selType==2'> AND par_menu_id IS NOT NULL </if>"
			 									    + " 	) "
			 									    + " ORDER BY menu_id , ORDERNUMBER "
													+ "</script>";
	
	//메뉴 생성
	public static final String INSERT_MENU = " INSERT INTO "
										   + "	KPC_ADMIN.KPC_MENU ("
										   + "		                   menu_id               "// -- 1.MENU ID"
										   + "		                  ,name                  "// -- 2.이름"
										   + "		                  ,par_menu_id           "// -- 3.상위 MENU ID"
										   + "		                  ,menu_url              "// -- 4.메뉴 URL"
										   + "		                  ,del_flag              "// -- 4.삭제여부"
										   + "		                  ,create_id             "// -- 5.생성자"
										   + "		                  ,create_dt             "// -- 6.생성일"
										   + " 						) VALUES ("
										   + "		                	#{menuId}    "// -- 1.MENU ID"
										   + "			                ,#{name}      "// -- 2.이름"
										   + "			                ,#{parMenuId} "// -- 3.상위 MENU ID"
										   + "			                ,#{menuUrl}   "// -- 4.메뉴 URL"
										   + "			                ,'0'                   "// -- 5.삭제여부"
										   + "			                ,#{createId}  "// -- 6.생성자"
										   + "			                ,SYSDATE               "// -- 7.생성일"
										   + "			            )";
	
	//신규메뉴생성 내역 등록
	public static final String INSERT_MENU_HST = " INSERT INTO KPC_ADMIN.KPC_MENU_HST ("
									           + "    	 menu_id               "// -- 1.MENU ID"
									           + "       ,desc_text             "// -- 2.내용"
									           + "       ,create_id             "// -- 3.생성자"
									           + "       ,create_dt             "// -- 4.삭제여부"
									           + "     ) VALUES ("
									           + "        #{menuId}     "// -- 1.MENU ID"
									           + "       ,#{desc}      "// -- 2.내용"
									           + "       ,#{createId}  "// -- 5.생성자"
									           + "       ,SYSDATE               "// -- 6.생성일"
									           + "     )";
	
	
	//메뉴정보 수정
	public static final String UPDATE_MENU = " UPDATE KPC_ADMIN.KPC_MENU SET "
										   + "			 name = #{name}   	 "// -- 1.MENU ID"      
									       + "          ,menu_url = #{menuUrl}   "// -- 2.상위 MENU ID" 
									       + "			,update_id = #{updateId}  "// -- 3.수정자"
									       + "          ,update_dt = SYSDATE      "// -- 4.수정일"
									       + " 		WHERE menu_id = #{menuId}";
	
	
	//메뉴 삭제
	public static final String DELETE_MENU = "UPDATE KPC_ADMIN.KPC_MENU SET del_flag = '1' WHERE menu_id = #{menuId}";
	

	
	//유저 대메뉴 권한 조회
	public static final String SELECT_MENU_AUTH = " SELECT LEVEL"
											    + "      ,B.menu_id"//"// -- MENU ID
											    + "      ,B.name"//"// -- 메뉴명
											    + "      ,B.menu_url"
											    + "      ,B.par_menu_id"//"// -- 사용자별 메뉴 ID SEQ
											    + "      ,A.ins_flag"//"// -- 등록 가능여부
											    + "      ,A.upd_flag"//"// -- 수정 가능여부
											    + "      ,A.del_flag"//"// -- 삭제 가능여부
											    + "      ,A.sel_flag"//"// -- 조회 가능여부
											    + "      ,A.appr_flag"//"// -- 승인 가능여부
											    + "   FROM KPC_EMP_MENU_INT A"
											    + "       ,KPC_MENU B"
											    + "   WHERE A.menu_id(+) = B.menu_id"
											    + "		AND B.del_Flag   = '0'"
											    + "		AND A.sel_flag   = '1'"
											    + "		AND A.emp_id(+) = #{employeeId}"
											    + " START WITH B.par_menu_id IS NULL"
											    + " CONNECT BY PRIOR B.menu_id = B.par_menu_id";
	
	
	//유저 서브 메뉴권한 조회
	public static final String SELECT_SUB_MENU_AUTH = "SELECT C.url_id"      
													+ "	     ,C.name"	                              
													+ "	     ,C.url"
													+ "	     ,C.par_menu_id"
													+ "	     ,A.sel_flag"
													+ "	     ,A.ins_flag"
													+ "	     ,A.upd_flag"
													+ "	     ,A.del_flag"
													+ "	     ,A.appr_flag"
													+ "	FROM KPC_EMP_MENU_INT A"//"// -- 사용자 권한
													+ "	   , KPC_MENU         B"//"// -- 사용자 권한 메뉴 
													+ "	   , KPC_MENU_URL     C"
													+ "	WHERE A.emp_id       = #{employeeId}"
													+ "	 AND A.menu_id       = B.menu_id"
													+ "	 AND B.del_flag      = '0'"
													+ "	 AND A.sel_flag      = '1'"
													+ "	 AND B.menu_id       = C.par_menu_id";
    
        
    //서브메뉴 등록
	public static final String INSERT_SUB_MENU = " INSERT INTO KPC_ADMIN.KPC_MENU_URL ("
											   + "					                   url_id                "// -- 1.seq"
											   + "					                  ,par_menu_id           "// -- 2.MENU ID"
											   + "					                  ,url                   "// -- 3.URL"
											   + "					                  ,name                  "// -- 4.이름"
											   + "					                  ,create_id             "// -- 5.생성자"
											   + "					                  ,create_dt             "// -- 6.생성일"
											   + "					               ) VALUES ("
											   + "					                   (SELECT NVL(MAX(to_number(url_id)),0) + 1 FROM KPC_MENU_URL) "// -- 1.SEQ"
											   + "					                  ,#{parMenuId} "// -- 2.MENU ID"
											   + "					                  ,#{url}       "// -- 3.URL"
											   + "					                  ,#{name}      "// -- 4.이름"
											   + "					                  ,#{createId}  "// -- 5.생성자"
											   + "					                  ,SYSDATE                     "// -- 6.생성일"
											   + "					               )";
    
    

	//서브메뉴 수정
	public static final String UPDATE_SUB_MENU = " UPDATE KPC_ADMIN.KPC_MENU_URL SET url = #{url}       	   "// -- 1.URL"
											   + "		        				   ,name      = #{name}      "// -- 2.이름  "
											   + "		        				   ,update_id = #{updateId}  "// -- 3.생성자"
											   + "	     	       				   ,update_dt = SYSDATE                    "// -- 4.수정일"
											   + "	        			  WHERE url_id     = #{urlId}";
												   
	
	//서브메뉴 정보 조회
	public static final String SELECT_SUB_MENU = " SELECT A.url_id           "// -- URL ID"
											   + "     , A.par_menu_id      "// -- 상위메뉴 ID"
											   + "     , B.name parMenuName "// -- 상위메뉴명"
											   + "     , A.url              "// -- URL 주소"
											   + " 	   , A.name             "// -- URL 명"
											   + "     , A.create_id        "// -- 생성자"
											   + "     , A.create_dt        "// -- 생성일"
											   + "     , A.update_id        "// -- 변경자"
											   + " 	   , A.update_dt        "// -- 변경일"
											   + "     FROM KPC_MENU_URL A"
											   + "	  	   ,KPC_MENU B"
											   + " WHERE A.par_menu_id = #{parMenuId}"
											   + "   AND A.par_menu_id = B.menu_id"
											   + "   AND B.del_flag    = '0'";
	
	//서브메뉴 삭제
	public static final String DELETE_SUB_MENU = "DELETE KPC_ADMIN.KPC_MENU_URL WHERE url_id=#{urlId}";
	
	
	//일반 코드 조회
	public static final String SELECT_COMMON_CODE = " <script>"
												  + " SELECT type        typeCode"//   "// -- 타입      
												  + "		,code        code"//"// -- 코드      
												  + "       ,name        codeName"//"// -- 이름     
												  + "       ,desc_text   descText"//"// -- 설명      
												  + "       ,create_id   createId"//"// -- 생성자    
												  + "       ,create_dt   createDt"//"// -- 생성일    
												  + "       ,update_id   updateId"//"// -- 변경자    
												  + "       ,update_dt   updateDt"//   "// -- 변경일    
												  + "   FROM KPC_ADMIN_CODE"                       
												  + "   WHERE 1 = 1"
												  + " <if test='typeCode != null and typeCode != \"\"'> AND type=#{typeCode} </if>"
												  + " <if test='code != null and code != \"\"'> AND code=#{code} </if>"
												  + " <if test='codeName != null and codeName != \"\"'> AND name=#{codeName} </if>"												  
												  + " ORDER BY type,code"
												  + " </script>";
		
	//타입 코드 조회
	public static final String SELECT_TYPE_CODE = " <script>"
												+ " SELECT CODE        typeCode,"
												+ "		   NAME        codeName"//   "// -- 타입         
												+ "		FROM KPC_ADMIN_CODE"                       
												+ "		WHERE "
												+ "		TYPE='MAST' "
												+ " <if test='typeCode!=\"\" and typeCode!=null'> AND code=#{typeCode}</if>"
												+ " </script>";
	
	
	//일반코드 등록
	public static String INSERT_COMMON_CODE = " MERGE INTO KPC_ADMIN_CODE A"                                                                                 
									        + "		USING ("
									        + "				SELECT #{typeCode} typeCode"
									        + "   	              ,#{code} code"
									        + "             FROM DUAL"
									        + "  	      ) B"
									        + "     ON (A.type = B.typeCode"
									        + "     AND  A.code = B.code)"
									        + "   	WHEN MATCHED THEN"
									        + "		UPDATE SET"
									        + "             name       = #{codeName}"//   "// -- 이름
									        + "				,desc_text  = #{descText}   "// -- 설명"
									        + "				,update_id  = #{createId}   "// -- 변경자"
									        + "				,update_dt  = SYSDATE       "// -- 변경일" 
									        + "		WHEN NOT MATCHED THEN"
									        + "     INSERT ("
									        + "               type                      "// -- 타입"
									        + "              ,code                      "// -- 코드"
									        + "              ,name                      "// -- 이름"
									        + "              ,desc_text                 "// -- 설명"
									        + "              ,create_id                 "// -- 생성자"
									        + "              ,create_dt                 "// -- 생성일"
									        + "            )"
									        + "      VALUES ("
									        + "               #{typeCode}"
									        + "              ,#{code}"
									        + "              ,#{codeName}"
									        + "              ,#{descText}"
									        + "              ,#{createId}"
									        + "              ,SYSDATE "
									        + "				)";
	
	//관리자 코드 삭제
	public static final String DELETE_CODE = " DELETE KPC_ADMIN_CODE WHERE type = #{typeCode} AND code = #{code}"; 
	

	//시스템 사용내역 조회
	public static final String SELECT_SYSTEM_HST_COUNT = " <script>"												 + ""
												 + "	SELECT COUNT(*) FROM ("
												 + " 	SELECT menu_id menuId"  
                       							 + " 	   		,type typeCode"
                       							 + "	   		,FNC_ADMIN_CODE_TO_NAME(type) typeName"
                       							 + "	   		,desc1 desc1"  
                       							 + "	   		,desc2 desc2"  
                       							 + "	   		,desc3 desc3" 
                       							 + "	   		,reg_id regId"  
                       							 + "		   	,reg_dt regDt"
                       							 + " 	FROM KPC_ADMIN.KPC_EMP_ACT_HST"
                       							 + " 	WHERE 1 = 1"
                       							 + " 	<if test='menuId!=\"\"'> AND menu_id=#{menuId}</if>"
												 + " 	<if test='desc1!=\"\"'> AND desc1=#{desc1}</if>"
												 + " 	<if test='desc2!=\"\"'> AND desc2=#{desc2}</if>"
												 + " 	<if test='desc3!=\"\"'> AND desc3=#{desc3}</if>"
												 + "	) HST "												 
                       							 + " </script>";
	
	
	//시스템 사용내역 조회
	public static final String SELECT_SYSTEM_HST = " <script>"
												 + "SELECT * FROM (	"
												 + "	SELECT ROWNUM RNUM, HST.* FROM ("
												 + " 	SELECT menu_id menuId"  
                       							 + " 	   		,type typeCode"
                       							 + "	   		,FNC_ADMIN_CODE_TO_NAME(type) typeName"
                       							 + "	   		,desc1 desc1"  
                       							 + "	   		,desc2 desc2"  
                       							 + "	   		,desc3 desc3" 
                       							 + "	   		,reg_id regId"  
                       							 + "		   	,reg_dt regDt"
                       							 + " 	FROM KPC_ADMIN.KPC_EMP_ACT_HST"
                       							 + " 	WHERE 1 = 1"
                       							 + " 	<if test='menuId!=\"\"'> AND menu_id=#{menuId}</if>"
												 + " 	<if test='desc1!=\"\"'> AND desc1=#{desc1}</if>"
												 + " 	<if test='desc2!=\"\"'> AND desc2=#{desc2}</if>"
												 + " 	<if test='desc3!=\"\"'> AND desc3=#{desc3}</if>"
												 + "	ORDER BY regDt DESC "
												 + "	) HST "
												 + "	WHERE ROWNUM &lt;= (#{start}+#{length} )"
												 + ") WHERE RNUM &gt; #{start}"
                       							 + " </script>";
	/*
	//시스템 사용내역 조회
	public static final String SELECT_SYSTEM_HST = " <script>"
												 + " 	SELECT menu_id menuId"  
                       							 + " 	   		,type typeCode"
                       							 + "	   		,FNC_ADMIN_CODE_TO_NAME(type) typeName"
                       							 + "	   		,desc1 desc1"  
                       							 + "	   		,desc2 desc2"  
                       							 + "	   		,desc3 desc3" 
                       							 + "	   		,reg_id regId"  
                       							 + "		   	,reg_dt regDt"
                       							 + " 	FROM KPC_ADMIN.KPC_EMP_ACT_HST"
                       							 + " 	WHERE 1 = 1"
                       							 + " 	<if test='menuId!=\"\"'> AND menu_id=#{menuId}</if>"
												 + " 	<if test='desc1!=\"\"'> AND desc1=#{desc1}</if>"
												 + " 	<if test='desc2!=\"\"'> AND desc2=#{desc2}</if>"
												 + " 	<if test='desc3!=\"\"'> AND desc3=#{desc3}</if>"
												 + "	ORDER BY regDt DESC"
                       							 + " </script>";
	*/
	//시스템 사용내역 등록
    public static final String INSERT_SYSTEM_HST = " INSERT INTO KPC_EMP_ACT_HST ("
												 + "			                  seq"
												 + "							 ,menu_id"
												 + "			                 ,type"
												 + "			                 ,desc1"
												 + "			                 ,desc2"
												 + "			                 ,desc3"
												 + "			                 ,reg_id"
												 + "			                 ,reg_dt"
												 + "			          )"
												 + "			          VALUES ("
												 + "			             	  	(SELECT NVL(MAX(seq) ,0) + 1 FROM KPC_EMP_ACT_HST)"
												 + "					              	,#{menuId}"
												 + "					                ,#{typeCode}"
												 + "					                ,#{desc1}"
												 + "					                ,#{desc2}"
												 + "					                ,#{desc3}"
												 + "					                ,#{regId}"
												 + "					                ,SYSDATE"
												 + "						      )";
    
    //등록 배치 수 조회
    public static final String SELECT_BATCH_COUNT = " <script>"
	    										  + " SELECT count(1) CNT"
							                      + "		FROM KPC_ADMIN.KPC_BATCH_HST"
							                      + " WHERE 1 = 1"
							                      + " <if test='reqId!=\"\"'> AND req_id=#{reqId}</if>"
							                      + " <if test='status!=\"\"'> AND status=#{status}</if>"
							                      + " <if test='startDate!=\"\" and startDate!=null and endDate!=null and endDate!=\"\"'> AND start_dt &gt;= #{startDate} AND start_dt &lt;= TO_DATE(#{endDate}||'235959' , 'YYYY-MM-DD hh24:mi:ss')</if>"
							                      + " </script>";
    
    //등록 배치 조회
    public static final String SELECT_BATCH_LIST = " <script>"
		    										+ " SELECT * " 
											        + "	FROM ("
											        + "       	SELECT ROWNUM AS RNUM, A.* " 
											        + "		    FROM ("
											        + "					SELECT seq"
											        + "		             	  ,req_id"
											        + "						  ,status"
											        + "						  ,FNC_ADMIN_CODE_TO_NAME(status) statusName"
											        + " 					  ,content"
											        + "		                  ,err_msg"
											        + "	                      ,start_dt"
											        + "	                      ,end_dt"
											        + "                       ,'' file_path"
											        + "                 FROM KPC_ADMIN.KPC_BATCH_HST"
											        + "    	  	  	    WHERE 1 = 1"
										            + "							<if test='reqId!=\"\" and reqId!=null'> AND req_id=#{reqId}</if>"
							                        + "							<if test='status!=\"\" and status!=null'> AND status=#{status}</if>"
							                        + "							<if test='startDate!=\"\" and startDate!=null and endDate!=null and endDate!=\"\"'> AND start_dt &gt;= #{startDate} AND start_dt &lt;= TO_DATE(#{endDate}||'235959' , 'YYYY-MM-DD hh24:mi:ss')</if>"
							                        + "               		  	ORDER BY start_dt DESC"
							                        + "					  ) A "
								                    + "			WHERE ROWNUM &lt;=#{limit}+#{offset}"
								                    + "	) WHERE RNUM &gt; #{offset}" 
								                    + " </script>";
    
    //배치 고유번호 생성
    public static final String CREATE_BATCH_ID = " SELECT NVL(MAX(seq),0) + 1 AS batchId FROM KPC_BATCH_HST";
    
    //배치 등록
    public static final String INSERT_BATCH = " INSERT INTO KPC_BATCH_HST ("
											+ "			    		        seq"	
											+ "					            ,req_id"
											+ "					            ,status"
											+ "					            ,file_path"
											+ "					            ,content"
											+ "					            ,err_msg"
											+ "					            ,start_dt"
											+ "					            ,end_dt"
											+ "					          )"
											+ "	  			   	   VALUES ("
											+ "	          					#{batchId}"
											+ "	         					,#{reqId}"
											+ "	         					,#{status}"
											+ "	         					,#{filePath}"
											+ "	         					,#{content}"
											+ "	         					,#{errMsg}"
											+ "	         					,SYSDATE"
											+ "	         					,SYSDATE"
											+ "	 						  )";
    
    //배치 정보 조회
    public static final String SELECT_BATCH = " SELECT seq"
								            + "    	, req_id"
								            + "    	, status"
								            + "     , FNC_ADMIN_CODE_TO_NAME(status) statusName"
								            + "		, content"
								            + "     , err_msg"
								            + "     , start_dt"
								            + "     , end_dt"
								            + "     , file_path"
								            + " FROM KPC_ADMIN.KPC_BATCH_HST"
								            + "	WHERE 1 = 1"
								            + "	AND seq = #{seq}"
								            + " AND req_id = #{reqId}";
    
    
    //배치 삭제
    public static final String DELETE_BATCH = " DELETE FROM KPC_ADMIN.KPC_BATCH_HST" 
									        + "			WHERE seq = #{seq}"
									        + "		    AND req_id = #{reqId}";
    
    //배치 정보 수정
    public static final String UPDATE_BATCH = " UPDATE KPC_BATCH_HST"
								            + " SET status = DECODE(status, #{status}, status, #{status})"
								            + " 	,err_msg = DECODE(err_msg, #{errMsg}, err_msg, #{errMsg})"
								            + "		,end_dt = SYSDATE"
								            + " WHERE seq = #{batchId}"
								            + " AND req_id = #{reqId}";
    
    
    //직원별 노출정보 제한 등록
    public static final String INSERT_COLUMN_INFO_RESTRICT = " MERGE"                                                                               
													       + "   INTO KPC_MENU_EMP_TABLE A"
													       + "   USING ("
													       + "  		SELECT #{employeeId} emp_id          "// -- 직원 ID"
													       + "           	  ,#{menuId} menu_id         "// --" 
													       + "	              ,#{tableId} table_id         "// --" 
													       + "	  	    FROM DUAL ) B"
													       + "   ON ( A.menu_id  = B.menu_id"
													       + "      AND A.emp_id   = B.emp_id" 
													       + "      AND A.table_id = B.table_id)"
													       + "   WHEN MATCHED THEN"
													       + "   UPDATE SET A.DESC_TEXT   = #{descText}"  
													       + "             ,A.UPDATE_ID  = #{createId}"          
													       + "             ,A.UPDATE_DT  = SYSDATE"                                  
													       + "   WHEN NOT MATCHED THEN"
													       + "   INSERT ("
													       + "	     	  A.MENU_ID"
													       + " 	    	 ,A.EMP_ID"
													       + " 	    	 ,A.TABLE_ID"
													       + " 	    	 ,A.DESC_TEXT"
													       + " 	    	 ,A.CREATE_ID"
													       + " 	    	 ,A.CREATE_DT"
													       + " 	    	 ,A.UPDATE_ID"
													       + " 	    	 ,A.UPDATE_DT"
													       + " 	    	 )"
													       + "    VALUES ("
													       + "	          #{menuId}"
													       + "         	 ,#{employeeId}"
													       + "           ,#{tableId}"
													       + "           ,#{descText}"
													       + "	         ,#{createId}"
													       + " 	    	 ,SYSDATE"                         
													       + " 	    	 ,#{createId}"
													       + " 	    	 ,SYSDATE"                         
													       + "   		 )";
    
    //직원별 노출정보 조회
    public static final String SELECT_COLUMN_INFO_RESTRICT = " SELECT MENU_ID"
														   + "     , EMP_ID"
														   + "     , TABLE_ID"
														   + "     , DESC_TEXT"
														   + " FROM KPC_MENU_EMP_TABLE"
														   + " WHERE MENU_ID  = #{menuId}"
														   + "	AND EMP_ID   = #{employeeId}"
														   + "  AND TABLE_ID = #{tableId} ";
    
   
    //메뉴 리스트 조회
    @Select(value=SYSTEM_MNG_GET_MENU)
    @Results(value={
    	@Result(property="menuId", column="menu_id"),
    	@Result(property="name", column="name"),
    	@Result(property="parMenuId", column="par_menu_id"),
    	@Result(property="parMenuName", column="parMenuName"),
    	@Result(property="menuUrl", column="menu_url"),
    	@Result(property="createId", column="create_id"),
    	@Result(property="createDt", column="create_dt"),
    	@Result(property="updateId", column="update_id"),
    	@Result(property="updateDt", column="update_dt")
    })
    public List<SystemMngMenu> getSystemMngMenu(@Param(value="name") String name, 
    											@Param(value="menuId") String menuId,
    											@Param(value="parMenuId") String parMenuId,
    											@Param(value="selType") String selType);
    
    //메뉴 정보 등록
    @Insert(value=INSERT_MENU)
    public void postSystemMngMenu(@Param(value="menuId") String menuId,
    							  @Param(value="name") String name, 
    							  @Param(value="parMenuId") String parMenuId,
    							  @Param(value="menuUrl") String menuUrl,
    							  @Param(value="createId") String createId);
    
    //메뉴 수정,등록 내역 등록
    @Insert(value=INSERT_MENU_HST)
    public void postSystemMngMenuHst(@Param(value="menuId") String menuId,
    								 @Param(value="name") String name,
    								 @Param(value="menuUrl") String menuUrl,
    								 @Param(value="createId") String createId,
    								 @Param(value="desc") String desc);
    
    //메뉴 수정
    @Update(value=UPDATE_MENU)
    public long putSystemMngMenu(@Param(value="menuId") String menuId,
    							 @Param(value="name") String name,
    							 @Param(value="parMenuId") String parMenuId,
    							 @Param(value="menuUrl") String menuUrl,
    							 @Param(value="updateId") String updateId);
    
    //메뉴삭제
    @Delete(value=DELETE_MENU)
    public long deleteSystemMngMenu(@Param(value="menuId") String menuId);
    
    //메뉴 권한 조회
    @Select(value=SELECT_MENU_AUTH)
    @Results(value={
    	@Result(property="level", column="level"),
    	@Result(property="menuId", column="menu_id"),
    	@Result(property="title", column="name"),
    	@Result(property="routerLink", column="menu_url"),
    	@Result(property="parMenuId", column="par_menu_id"),
    	@Result(property="insFlag", column="ins_flag"),
    	@Result(property="updFlag", column="upd_flag"),
    	@Result(property="delFlag", column="del_flag"),
    	@Result(property="selFlag", column="sel_flag"),
    	@Result(property="apprFlag", column="appr_flag")    	
    })
    public List<SystemMngAuthMenu> getSystemMngAuthMenu(@Param(value="employeeId") String employeeId);
    
    //서브메뉴 권한 조회
    @Select(value=SELECT_SUB_MENU_AUTH)
    @Results(value={
    	@Result(property="urlId", column="url_id"),
    	@Result(property="name", column="name"),
    	@Result(property="url", column="url"),
    	@Result(property="parMenuId", column="par_menu_id"),
    	@Result(property="selFlag", column="sel_flag"),
    	@Result(property="insFlag", column="ins_flag"),
    	@Result(property="updFlag", column="upd_flag"),
    	@Result(property="delFlag", column="del_flag"),
    	@Result(property="apprFlag", column="appr_flag")
    })
    public List<SystemMngSubMenuAuth> getSystemMngSubMenuAuth(@Param(value="employeeId") String employeeId);
    
    //서브메뉴 등록
    @Insert(value=INSERT_SUB_MENU)
    public void postSystemMngSubMenuUrl(@Param(value="parMenuId") String parMenuId,
    									@Param(value="name") String name,
    									@Param(value="url") String url,
    									@Param(value="createId") String createId);
    
    //서브메뉴 수정
    @Update(value=UPDATE_SUB_MENU)
    public long putSystemMngSubMenuUrl(@Param(value="urlId") String urlId,
    								   @Param(value="name") String name,
    								   @Param(value="url") String url,
    								   @Param(value="updateId") String updateId);
    
    //서브메뉴 삭제
    @Delete(value=DELETE_SUB_MENU)
    public long deleteSystemMngSubMenuUrl(@Param(value="urlId") String urlId);
    
    //서브메뉴 정보 조회
    @Select(value=SELECT_SUB_MENU)
    @Results(value={
    	@Result(property="urlId", column="url_id"),
    	@Result(property="parMenuId", column="par_menu_id"),
    	@Result(property="parMenuName", column="parMenuName"),
    	@Result(property="url", column="url"),
    	@Result(property="name", column="name"),
    	@Result(property="createId", column="create_id"),
    	@Result(property="createDt", column="create_dt"),
    	@Result(property="updateId", column="update_id"),
    	@Result(property="updateDt", column="update_dt")    	
    })
    public List<SystemMngSubMenuUrl> getSystemMngSubMenuUrl(@Param(value="parMenuId") String parMenuId);
    
    //일반 코드 조회
    @Select(value=SELECT_COMMON_CODE)
    @Results(value={
    	@Result(property="typeCode", column="typeCode"),
    	@Result(property="code", column="code"),
    	@Result(property="codeName", column="codeName"),
    	@Result(property="descText", column="descText"),
    	@Result(property="createId", column="createId"),
    	@Result(property="createDt", column="createDt"),
    	@Result(property="updateId", column="updateId"),
    	@Result(property="updateDt", column="updateDt")
    })
    public List<SystemMngCommonCode> getSystemMngCommonCodeForMenuAuth();
    
    //일반 코드 조회
    @Select(value=SELECT_COMMON_CODE)
    @Results(value={
    	@Result(property="typeCode", column="typeCode"),
    	@Result(property="code", column="code"),
    	@Result(property="codeName", column="codeName"),
    	@Result(property="descText", column="descText"),
    	@Result(property="createId", column="createId"),
    	@Result(property="createDt", column="createDt"),
    	@Result(property="updateId", column="updateId"),
    	@Result(property="updateDt", column="updateDt")
    })
    public List<SystemMngCommonCode> getSystemMngCommonCode(@Param(value="typeCode") String typeCode,
    														@Param(value="code") String code,
    														@Param(value="codeName") String codeName);
    
    //타입 코드 조회
    @Select(value=SELECT_TYPE_CODE)
    @Results(value={
		@Result(property="typeCode", column="typeCode")
    })
    public List<SystemMngTypeCode> getSystemMngTypeCode(@Param(value="typeCode") String typeCode);
    
    //일반 코드 등록
    @Insert(value=INSERT_COMMON_CODE)
    public void postSystemMngCommonCode(@Param(value="typeCode") String typeCode,
    									@Param(value="code") String code, 
    									@Param(value="codeName") String codeName,
    									@Param(value="descText") String descText,
    									@Param(value="createId") String createId);
    
    public static final String READ_SYSTEM_CODE_BY_CODE = "SELECT "
    		+ "  TYPE as typeCode"
    		+ " ,CODE"
    		+ " ,NAME as codeName"
    		+ " ,DESC_TEXT as descText"
    		+ " ,CREATE_ID as createId"
    		+ " ,CREATE_DT as createDt"
    		+ " ,UPDATE_ID as updateId"
    		+ " ,UPDATE_DT as updateDt"
    		+ " FROM KPC_ADMIN_CODE "
    		+ " WHERE CODE = #{code} ";
    @Select(READ_SYSTEM_CODE_BY_CODE)
    public SystemMngTypeCode readSystemCodeByCode(@Param("code") String code);
    
    
    //일반코드 삭제
    @Delete(value=DELETE_CODE)
    public long deleteSystemMngCommonCode(@Param(value="typeCode") String typeCode, 
    									  @Param(value="code") String code);
    
    //관리자 수정 내역 조회
    @Select(value=SELECT_SYSTEM_HST)
    @Results(value={
    	@Result(property="menuId", column="menuId"),
    	@Result(property="typeCode", column="typeCode"),
    	@Result(property="typeName", column="typeName"),
    	@Result(property="desc1", column="desc1"),
    	@Result(property="desc2", column="desc2"),
    	@Result(property="desc3", column="desc3"),
    	@Result(property="regId", column="regId"),
    	@Result(property="regDt", column="regDt")
    })
    public List<SystemMngSystemHistory> getSystemMngSystemHistory(@Param(value="menuId") String menuId,
    															  @Param(value="typeCode") String typeCode,
    															  @Param(value="desc1") String desc1,
    															  @Param(value="desc2") String desc2,
    															  @Param(value="desc3") String desc3,
    															  @Param(value="start") String start,
    															  @Param(value="length") String length);
    
    //관리자 수정내역 등록
    @Insert(value=INSERT_SYSTEM_HST)
    public void postSystemMngSystemHistory(@Param(value="menuId") String menuId,
										  @Param(value="typeCode") String typeCode,
										  @Param(value="desc1") String desc1,
										  @Param(value="desc2") String desc2,
										  @Param(value="desc3") String desc3,
										  @Param(value="regId") String regId);
    
    //배치 등록 수 조회
    @Select(value=SELECT_BATCH_COUNT)
    public long getSystemMngBatchCount(@Param(value="limit") Long limit,
    									@Param(value="offset") Long offset,
    									@Param(value="reqId") String reqId, 
    									@Param(value="startDate") String startDate,
    									@Param(value="endDate") String endDate,
    									@Param(value="status") String status);
    
    //배치정보 조회
    @Select(value=SELECT_BATCH_LIST)
    @Results(value={
    	@Result(property="seq", column="seq"),
    	@Result(property="reqId", column="req_id"),
    	@Result(property="status", column="status"),
    	@Result(property="statusName", column="statusName"),
    	@Result(property="content", column="content"),
    	@Result(property="errMsg", column="err_msg"),
    	@Result(property="startDt", column="start_dt"),
    	@Result(property="endDt", column="end_dt"),    	
    	@Result(property="filePath", column="file_path")
    })
    public List<SystemMngBatch> getSystemMngBatchs(@Param(value="limit") Long limit,
													@Param(value="offset") Long offset,
													@Param(value="reqId") String reqId, 
													@Param(value="startDate") String startDate,
													@Param(value="endDate") String endDate,
													@Param(value="status") String status);
    
    //배치고유번호 조회
    @Select(value=CREATE_BATCH_ID)
    public long getBatchNumber();
    
    //배치 정보 등록
    @Insert(value=INSERT_BATCH)
    public long postSystemMngBatch(@Param(value="batchId") long batchId,
    								@Param(value="reqId") String reqId,
    								@Param(value="status") String status,
    								@Param(value="filePath") String filePath,
    								@Param(value="content") String content,
    								@Param(value="errMsg") String errMsg);
    
    //배치정보 수정
    @Update(value=UPDATE_BATCH)
    public long putSystemMngBatch(@Param(value="batchId") long batchId,
    							  @Param(value="reqId") String reqId,
    							  @Param(value="status") String status,
    							  @Param(value="errMsg") String errMsg);
    
  //배치정보 조회
    @Select(value=SELECT_BATCH)
    @Results(value={
    	@Result(property="seq", column="seq"),
    	@Result(property="reqId", column="req_id"),
    	@Result(property="status", column="status"),
    	@Result(property="statusName", column="statusName"),
    	@Result(property="content", column="content"),
    	@Result(property="errMsg", column="err_msg"),
    	@Result(property="startDt", column="start_dt"),
    	@Result(property="endDt", column="end_dt"),    	
    	@Result(property="filePath", column="file_path")
    })
    public SystemMngBatch getSystemMngBatch(@Param(value="seq") String seq,
    									    @Param(value="reqId") String reqId);
    
    //뱌치정보 삭제
    @Delete(value=DELETE_BATCH)
    public long deleteSystemMngBatch(@Param(value="seq") String seq,
    								@Param(value="reqId") String reqId);
    
    @Insert(value=INSERT_COLUMN_INFO_RESTRICT)
    public void postSystemMngTableColumn(@Param(value="menuId") String menuId, 
    									 @Param(value="employeeId") String employeeId,
    									 @Param(value="tableId") String tableId,
    									 @Param(value="descText") String descText,
    									 @Param(value="createId") String createId);
    
    @Select(value=SELECT_COLUMN_INFO_RESTRICT)
    @Results(value={
    	@Result(property="menuId", column="menu_id"),
    	@Result(property="employeeId", column="emp_id"),
    	@Result(property="tableId", column="table_id"),
    	@Result(property="descText", column="desc_text")
    })
    public SystemMngTableColumn getSystemMngTableColumn(@Param(value="menuId") String menuId,
    														  @Param(value="employeeId") String employeeId, 
    														  @Param(value="tableId") String tableId);
    
    @Select(value=SELECT_SYSTEM_HST_COUNT)
    public int getSystemMngSystemHistoryCount(@Param(value="menuId") String menuId,
			  									@Param(value="typeCode") String typeCode,
			  									@Param(value="desc1") String desc1,
			  									@Param(value="desc2") String desc2,
			  									@Param(value="desc3") String desc3);
}
