package kr.co.kpcard.backoffice.repository.approval.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@Deprecated
public interface ApprovalMapper {
/*
	public static final String POST_APPROVAL_BY_INSERT_KPC_EL_APPR_HST = "INSERT INTO KPC_EL_APPR_HST "
			+ "	SELECT (SELECT NVL(MAX(SEQ),0) + 1 FROM KPC_EL_APPR_HST) "
			+ "		, SEQ "
			+ "		, TYPE_CODE "
			+ "		, TMP_SEQ "
			+ "		, MENU_NAME "
			+ "		, REQ_EMP_ID "
			+ "		, APPR_EMP_ID "
			+ "		, STATUS "
			+ "		, APPR_DESC "
			+ "		, CREATE_ID "
			+ "		, CREATE_DT "
			+ "		, UPDATE_ID "
			+ "		, UPDATE_DT "
			+ "	FROM KPC_EL_APPR "
			+ "	WHERE SEQ = #{seq} "
			+ "		AND APPR_EMP_ID = #{empId} ";
	@Insert(POST_APPROVAL_BY_INSERT_KPC_EL_APPR_HST)
	public int postApprovalByInsertKpcElApprHst(@Param(value="seq") long seq, @Param(value="empId") String empId);
	
	public static final String  POST_APPROVAL_BY_UPDATE_KPC_EL_APPR = "UPDATE KPC_EL_APPR "
			+ "		SET STATUS = 'ARST-0002' "
			+ "	WHERE SEQ = #{seq} "
			+ "		AND APPR_EMP_ID = #{empId} ";
	@Update(POST_APPROVAL_BY_UPDATE_KPC_EL_APPR)
	public int postApprovalByUpdateKpcElAppr(@Param(value="seq") long seq, @Param(value="empId") String empId);
	
	public static final String  POST_APPROVAL_BY_REJECT_KPC_EL_APPR = "UPDATE KPC_EL_APPR "
			+ "		SET STATUS      = 'ARST-0003' "
			+ "			, APPR_DESC   = #{desc}"
			+ "	WHERE SEQ = #{seq} "
			+ "		AND APPR_EMP_ID = #{empId} ";
	@Update(POST_APPROVAL_BY_REJECT_KPC_EL_APPR)
	public int postApprovalByRejectKpcElAppr(@Param(value="seq") long seq, @Param(value="empId") String empId,
			@Param(value="desc") String desc);
	
	
	public static final String GET_KCON_APPROVALS_HISTORY_BY_COUNT = "<script> "
			+ " SELECT COUNT(1) count "
            + " FROM KPC_EL_APPR A "
            + " 	,KPC_KCON_TMP B "
            + " 	,KPC_EMPLOYE C "
            + " 	,KPC_EMPLOYE D "
            + "	WHERE A.TMP_SEQ       = B.SEQ "
            + "		AND A.TYPE_CODE  LIKE 'APPR-05%' "
            + "		AND A.REQ_EMP_ID    = C.EMP_ID "
            + "		AND A.APPR_EMP_ID   = D.EMP_ID "
            + "		AND A.CREATE_DT    &gt;= #{startDate} AND A.CREATE_DT &lt;= TO_DATE(#{endDate} || '235959' , 'YYYY-MM-DD hh24:mi:ss') "
            + " <if test='title != \"\" '> "
	        + " 	AND B.TITLE = '%' || #{title} || '%' "
	        + " </if> "        
	        + " <if test='typeCode != \"\" '> "
	        + " 	AND B.TYPE = '%' || #{typeCode} || '%' "
	        + " </if> "
	        + " <if test='typeDetail != \"\" '> "
	        + " 	AND B.TYPE_DETAIL = '%' || #{typeDetail} || '%' "
	        + " </if> "  
	        + " <if test='reqEmpName != \"\" '> "
	        + " 	AND D.NAME = '%' || #{reqEmpName} || '%' "
	        + " </if> "  
	        + " <if test='status != \"\" '> "
	        + " 	AND A.STATUS = '%' || #{status} || '%' "
	        + " </if> "  
	        + " <if test='historyType != \"\" and billingRegFlag == \"APPR-0500\" '> " 
            + " 	AND (A.TYPE_CODE = 'APPR-0500' AND A.STATUS  = 'ARST-0001') " 
            + " </if> "
            + " <if test='historyType != \"\" and billingRegFlag != \"APPR-0500\" '> " 
            + " 	AND (A.TYPE_CODE &lt;&gt; 'APPR-0500' AND A.STATUS  = 'ARST-0001') " 
            + " </if> "       
			+ "</script>";
	@Select(GET_KCON_APPROVALS_HISTORY_BY_COUNT)
	public int getKconApprovalsHistoryByCount(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate,
			@Param(value="title") String title, @Param(value="typeCode") String typeCode, @Param(value="typeDetail") String typeDetail,
			@Param(value="reqEmpName") String reqEmpName, @Param(value="status") String status, @Param(value="historyType") String historyType);
	
	public static final String GET_KCON_APPROVALS_HISTORY = "<script> "
			+ " SELECT Z.* "
			+ "		FROM ( "
			+ "			SELECT ROWNUM AS RNUM, X.* "
			+ "			FROM ( "
			+ "				SELECT A.SEQ "
			+ "					, A.TMP_SEQ "
			+ "					, A.STATUS "
			+ "					, FNC_ADMIN_CODE_TO_NAME(A.STATUS) STATUS_NM "
			+ "					, A.TYPE_CODE "
			+ "					, FNC_ADMIN_CODE_TO_NAME(A.TYPE_CODE) TYPE_CODE_NM "
			+ "					, A.CREATE_DT "
			+ "					, DECODE(A.STATUS , 'ARST-0001' , '' , TO_CHAR(A.UPDATE_DT,'YYYY-MM-DD')) UPDATE_DT "
			+ "					, FNC_ADMIN_CODE_TO_NAME(B.TYPE) TYPE_NM "
			+ "					, FNC_ADMIN_CODE_TO_NAME(B.TYPE_DETAIL) TYPE_DETAIL_NM "
			+ "					, B.TITLE "
			+ "					, A.REQ_EMP_ID "
			+ "					, C.NAME REQ_EMP_NM "
			+ "					, A.APPR_EMP_ID "
			+ "					, D.NAME APPR_EMP_NM "
			+ "				FROM KPC_EL_APPR A "
			+ "					,KPC_KCON_TMP B "
			+ "					,KPC_EMPLOYE C "
			+ "					,KPC_EMPLOYE D "
			+ "				WHERE A.TMP_SEQ = B.SEQ "
			+ "					AND A.TYPE_CODE LIKE 'APPR-05%' "
			+ "					AND A.REQ_EMP_ID   = C.EMP_ID "
			+ "					AND A.APPR_EMP_ID  = D.EMP_ID "
			+ "					AND A.CREATE_DT &gt;= #{startDate} AND A.CREATE_DT &lt;= TO_DATE(#{endDate} || '235959' , 'YYYY-MM-DD hh24:mi:ss') "
			+ " 			<if test='title != \"\" '> "
	        + " 				AND B.TITLE = '%' || #{title} || '%' "
	        + " 			</if> "        
	        + " 			<if test='typeCode != \"\" '> "
	        + " 				AND B.TYPE = '%' || #{typeCode} || '%' "
	        + " 			</if> "
	        + "				<if test='typeDetail != \"\" '> "
	        + " 				AND B.TYPE_DETAIL = '%' || #{typeDetail} || '%' "
	        + " 			</if> "  
	        + " 			<if test='reqEmpName != \"\" '> "
	        + "				 	AND D.NAME = '%' || #{reqEmpName} || '%' "
	        + "				 </if> "  
	        + "				 <if test='status != \"\" '> "
	        + "				 	AND A.STATUS = '%' || #{status} || '%' "
	        + "				 </if> "  
	        + "				 <if test='historyType != \"\" and billingRegFlag == \"APPR-0500\" '> " 
            + "				 	AND (A.TYPE_CODE = 'APPR-0500' AND A.STATUS  = 'ARST-0001') " 
            + "				 </if> "
            + "				 <if test='historyType != \"\" and billingRegFlag != \"APPR-0500\" '> " 
            + "				 	AND (A.TYPE_CODE &lt;&gt; 'APPR-0500' AND A.STATUS  = 'ARST-0001') " 
            + "				 </if> "
            + "				ORDER BY A.CREATE_DT DESC "
            + "			) X "
            + "		WHERE ROWNUM &lt;= (#{offset} + #{limit}) "
            + "		) Z "
            + "	WHERE RNUM &gt; #{offset}"
			+ "</script>";
	@Select(GET_KCON_APPROVALS_HISTORY)
	public List<ApprovalKconApproval> getKconApprovalHistory(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate,
			@Param(value="title") String title, @Param(value="typeCode") String typeCode, @Param(value="typeDetail") String typeDetail,
			@Param(value="reqEmpName") String reqEmpName, @Param(value="status") String status, @Param(value="historyType") String historyType, 
			@Param(value="limit") long limit, @Param(value="offset") long offset);
	
	public static final String GET_APPROVALS_BY_COUNT = "<script> "
			+ " SELECT COUNT(1) count "
			+ "	FROM KPC_EL_APPR A "
			+ "		,KPC_KCON_TMP B "
			+ "		,KPC_EMPLOYE C "
			+ "		,KPC_EMPLOYE D "
			+ "	WHERE A.TMP_SEQ = B.SEQ "
			+ "		AND A.REQ_EMP_ID = C.EMP_ID "
			+ "		AND A.APPR_EMP_ID  = D.EMP_ID "
			+ "		AND A.TYPE_CODE LIKE 'APPR-05%' "
			+ "		AND A.CREATE_DT &gt;= #{startDate} AND A.CREATE_DT &lt;= TO_DATE(#{endDate} || '235959' , 'YYYY-MM-DD hh24:mi:ss') "
			+ "		AND (A.APPR_EMP_ID = #{empId} OR A.REQ_EMP_ID = #{empId}) "
			+ " <if test='title != \"\" '> "
	        + " 	AND B.TITLE = '%' || #{title} || '%' "
	        + " </if> "        
	        + " <if test='typeCode != \"\" '> "
	        + " 	AND B.TYPE = '%' || #{typeCode} || '%' "
	        + " </if> "
	        + "	<if test='typeDetail != \"\" '> "
	        + " 	AND B.TYPE_DETAIL = '%' || #{typeDetail} || '%' "
	        + " </if> "  
	        + " <if test='reqEmpName != \"\" '> "
	        + "	 	AND D.NAME = '%' || #{reqEmpName} || '%' "
	        + " </if> "  
	        + " <if test='status != \"\" '> "
	        + "	 	AND A.STATUS = '%' || #{status} || '%' "
	        + " </if> " 
			+ " </script>";
	@Select(GET_APPROVALS_BY_COUNT)
	public int getApprovalsByCount(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate,
			@Param(value="title") String title, @Param(value="typeCode") String typeCode, @Param(value="typeDetail") String typeDetail,
			@Param(value="reqEmpName") String reqEmpName, @Param(value="status") String status,@Param(value="historyType") String historyType);
	
	public static final String GET_APPROVALS = "<script> "
			+ "	SELECT Z.* "
			+ "	FROM ( "
			+ "		SELECT ROWNUM AS RNUM, X.* "
			+ "		FROM ( "
			+ "			SELECT A.SEQ "
			+ "				, A.TMP_SEQ "
			+ "				, A.STATUS "
			+ "				, FNC_ADMIN_CODE_TO_NAME(A.STATUS) STATUS_NM "
			+ "				, A.TYPE_CODE "
			+ "				, FNC_ADMIN_CODE_TO_NAME(A.TYPE_CODE) TYPE_CODE_NM "
			+ "				, A.CREATE_DT "
			+ "				, DECODE(A.STATUS , 'ARST-0001' , '' , TO_CHAR(A.UPDATE_DT,'YYYY-MM-DD')) UPDATE_DT "
			+ "				, FNC_ADMIN_CODE_TO_NAME(B.TYPE) TYPE_NM "
			+ "				, FNC_ADMIN_CODE_TO_NAME(B.TYPE_DETAIL) TYPE_DETAIL_NM "
			+ "				, B.TITLE "
			+ "				, A.REQ_EMP_ID "
			+ "				, C.NAME REQ_EMP_NM "
			+ "				, A.APPR_EMP_ID "
			+ "				, D.NAME APPR_EMP_NM "
			+ "			FROM KPC_EL_APPR A "
			+ "				,KPC_KCON_TMP B "
			+ "				,KPC_EMPLOYE C "
			+ "				,KPC_EMPLOYE D "
			+ "			WHERE A.TMP_SEQ = B.SEQ "
			+ "				AND A.REQ_EMP_ID   = C.EMP_ID "
			+ "				AND A.APPR_EMP_ID  = D.EMP_ID "
			+ "				AND A.TYPE_CODE LIKE 'APPR-05%' "
			+ "				AND (A.APPR_EMP_ID = #{empId} OR A.REQ_EMP_ID = #{empId}) "
			+ "				AND A.CREATE_DT &gt;= #{startDate} AND A.CREATE_DT &lt;= TO_DATE(#{endDate} || '235959' , 'YYYY-MM-DD hh24:mi:ss') "
			+ " 		<if test='title != \"\" '> "
	        + " 			AND B.TITLE = '%' || #{title} || '%' "
	        + "			</if> "        
	        + "			<if test='typeCode != \"\" '> "
	        + " 			AND B.TYPE = '%' || #{typeCode} || '%' "
	        + "			</if> "
	        + "			<if test='typeDetail != \"\" '> "
	        + " 			AND B.TYPE_DETAIL = '%' || #{typeDetail} || '%' "
	        + "			</if> "  
	        + "			<if test='reqEmpName != \"\" '> "
	        + "			 	AND D.NAME = '%' || #{reqEmpName} || '%' "
	        + "			</if> "  
	        + "			<if test='status != \"\" '> "
	        + "	 			AND A.STATUS = '%' || #{status} || '%' "
	        + " 		</if> " 
			+ "			ORDER BY A.CREATE_DT DESC "
			+ "		) X "
			+ "		WHERE ROWNUM &lt;= (#{offset} + #{limit}) "
			+ "	) Z "
			+ "	WHERE RNUM &gt; #{offset} "
			+ " </script> ";
	@Select(GET_APPROVALS)
	public List<ApprovalKconApproval> getApprovals(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate,
			@Param(value="title") String title, @Param(value="typeCode") String typeCode, @Param(value="typeDetail") String typeDetail,
			@Param(value="reqEmpName") String reqEmpName, @Param(value="status") String status, @Param(value="historyType") String historyType, 
			@Param(value="limit") long limit, @Param(value="offset") long offset);
	
	public static final String GET_KCON_TMP = "SELECT A.SEQ "
			+ "		,A.MERCHANT_ID "
			+ "		,A.MERCHANT_PW "
			+ "		,A.PRODUCT_ID "
			+ "		,A.TITLE "
			+ "		,A.TYPE TYPE_CODE "
			+ "		,FNC_ADMIN_CODE_TO_NAME(A.TYPE) TYPE_CODE_NM "
			+ "		,A.TYPE_DETAIL "
			+ "		,FNC_ADMIN_CODE_TO_NAME(A.TYPE_DETAIL) TYPE_DETAIL_NM "
			+ "		,A.AMOUNT "
			+ "		,A.EXPIRE_DAYS "
			+ "		,A.EXPIRE_DAYS_TYPE "
			+ "		,A.SELLER "
			+ "		,A.USAGE "
			+ "		,A.COUPON_LENGTH "
			+ "		,A.STATUS "
			+ "		,A.REGISTER "
			+ "		,B.STATUS APPR_STATUS "
			+ "		,B.TYPE_CODE APPR_TYPE_CODE "
			+ "		,B.APPR_DESC "
			+ "	FROM KPC_KCON_TMP A "
			+ "		, KPC_EL_APPR B "
			+ "	WHERE A.SEQ = #{tmpSeq} "
			+ "		AND A.SEQ = B.TMP_SEQ "
			+ "		AND B.TYPE_CODE = 'APPR-0100' "
			+ "		AND (B.APPR_EMP_ID = #{empId} OR B.REQ_EMP_ID = #{empId}) ";
	@Select(GET_KCON_TMP)
	public ApprovalKconTmp getKConTmp(@Param(value="tmpSeq") String tmpSeq, @Param(value="empId") String empId);
	
	public static final String INSERT_KPC_KCON_TMP = "INSERT INTO KPC_KCON_TMP( "
			+ "		SEQ "
			+ "		,MERCHANT_ID "
			+ "		,MERCHANT_PW "
			+ "		,TITLE "
			+ "		,TYPE_DETAIL "
			+ "		,TYPE "
			+ "		,AMOUNT "
			+ "		,EXPIRE_DAYS "
			+ "		,EXPIRE_DAYS_TYPE "
			+ "		,SELLER "
			+ "		,USAGE "
			+ "		,COUPON_LENGTH "
			+ "		,STATUS "
			+ "		,REGISTER "
			+ "	) VALUES ( "
			+ "		#{tmpSeq} "
            + "     ,#{merchantId} "
            + "     ,#{merchantPw} "
            + "     ,#{title} "
            + "     ,#{typeDetail} "
            + "     ,#{typeCode} "
            + "     ,#{amount} "
            + "     ,#{expireDays} "
            + "     ,#{expireDaysType} "
            + "     ,#{seller} "
            + "     ,#{usage} "
            + "     ,#{couponLength} "
            + "     ,#{status} "
            + "     ,#{register} "
            + "	)";
	@Insert(INSERT_KPC_KCON_TMP)
	public int insertKpcKConTmp(@Param(value="tmpSeq") String tmpSeq, @Param(value="merchantId") String merchantId,
			@Param(value="merchantPw") String merchantPw, @Param(value="title") String title, @Param(value="typeDetail") String typeDetail,
			@Param(value="typeCode") String typeCode, @Param(value="amount") long amount, @Param(value="expireDays") long expireDays,
			@Param(value="expireDaysType") String expireDaysType, @Param(value="seller") String seller, @Param(value="usage") String usage,
			@Param(value="couponLength") String couponLength, @Param(value="status") String status, @Param(value="register") String register);
	
	public static final String GET_KCON_INSERT_BY_KPC_KCON_TMP_SEQ = "SELECT NVL(MAX(SEQ),0) + 1 SEQ FROM KPC_KCON_TMP";
	@Select(GET_KCON_INSERT_BY_KPC_KCON_TMP_SEQ)
	public String getKconTmpSeq();
	
	
	public static final String GET_KPC_EL_APPR_SEQ = "SELECT NVL(MAX(SEQ),0) + 1 SEQ FROM KPC_EL_APPR ";
	@Select(GET_KPC_EL_APPR_SEQ)
	public String getKpcElApprSeq();
	
	public static final String INSERT_KPC_EL_APPR = "INSERT INTO KPC_EL_APPR( "
			+ "		SEQ "
			+ "		, TYPE_CODE "
			+ "		, TMP_SEQ "
			+ "		, MENU_NAME "
			+ "		, REQ_EMP_ID "
			+ "		, APPR_EMP_ID "
			+ "		, STATUS "
			+ "		, APPR_DESC "
			+ "		, CREATE_ID "
			+ "		, CREATE_DT "
			+ "		, UPDATE_ID "
			+ "		, UPDATE_DT "
			+ "	) VALUES ( "
			+ "		#{apprSeq} "
			+ "		, #{typeCode} "
			+ "		, #{tmpSeq} "
			+ "		, #{menuName} "
			+ "		, #{register} "
			+ "		, #{apprEmpId} "
			+ "		, #{status}"
			+ "		, #{apprDesc} "
			+ "		, #{register} "
			+ "		, SYSDATE "
			+ "		, #{register} "
			+ "		, SYSDATE "
			+ "	) ";
	@Insert(INSERT_KPC_EL_APPR)
	public int insertKpcElAppr(@Param(value="apprSeq") String apprSeq, @Param(value="typeCode") String typeCode,
			@Param(value="tmpSeq") String tmpSeq, @Param(value="menuName") String menuName, @Param(value="register") String register,
			@Param(value="apprEmpId") String apprEmpId, @Param(value="status") String status, @Param(value="apprDesc") String apprDesc);
	
	public static final String INSERT_KPC_EL_APPR_HST = "<script>"
			+ " INSERT INTO KPC_EL_APPR_HST "
			+ " SELECT (SELECT NVL(MAX(SEQ),0) + 1 FROM KPC_EL_APPR_HST) "
			+ " 	, SEQ "
			+ " 	, TYPE_CODE "
			+ "		, TMP_SEQ "
			+ "		, MENU_NAME "
			+ "		, REQ_EMP_ID "
			+ "		, APPR_EMP_ID "
			+ "		, STATUS "
			+ "		, APPR_DESC "
			+ "		, CREATE_ID "
			+ "		, CREATE_DT "
			+ "		, UPDATE_ID "
			+ "		, UPDATE_DT "
			+ "	FROM KPC_EL_APPR "
			+ "	WHERE SEQ = #{apprSeq} "
			+ " <if test='register != \"\" '> "
			+ "		AND REQ_EMP_ID = #{register}"
	        + " </if> "  
			+ "</script>";
	@Insert(INSERT_KPC_EL_APPR_HST)
	public int insertKpcElApprHst(@Param(value="apprSeq") String apprSeq, @Param(value="register") String register);
	
	public static final String INSERT_KPC_EL_APPR_BY_KCON_PUBLISH_STOP = "INSERT INTO KPC_EL_APPR( "
			+ "		SEQ "
			+ "		, TYPE_CODE "
			+ "		, TMP_SEQ "
			+ "		, MENU_NAME "
			+ "		, REQ_EMP_ID "
			+ "		, APPR_EMP_ID "
			+ "		, STATUS "
			+ "		, APPR_DESC "
			+ "		, CREATE_ID "
			+ "		, CREATE_DT "
			+ "		, UPDATE_ID "
			+ "		, UPDATE_DT "
			+ "	) VALUES ( "
			+ "		#{apprSeq} "
			+ "		, DECODE(#{status}, '01' , 'APPR-0502', 'APPR-0503') "
			+ "		, #{tmpSeq} "
			+ "		, 'Kcon 발행중지/해제 승인요청' "
			+ "		, #{register} "
			+ "		, #{apprEmpId} "
			+ "		, 'ARST-0001'"
			+ "		, '' "
			+ "		, #{register} "
			+ "		, SYSDATE "
			+ "		, #{register} "
			+ "		, SYSDATE "
			+ "	) ";
	@Insert(INSERT_KPC_EL_APPR_BY_KCON_PUBLISH_STOP)
	public int insertKpcElApprByKConPublishStop(@Param(value="apprSeq") String apprSeq, @Param(value="tmpSeq") String tmpSeq, 
			@Param(value="register") String register, @Param(value="apprEmpId") String apprEmpId, @Param(value="status") String status);

	
	public static final String UPDATE_KPC_KCON_TMP = "UPDATE KPC_KCON_TMP "
			+ "		SET MERCHANT_ID = #{merchantId} "
			+ "			, MERCHANT_PW = #{merchantPw} "
			+ "			, TITLE = #{title} "
			+ "			, TYPE_DETAIL = #{typeDetail} "
			+ "			, TYPE = #{typeCode} "
			+ "			, AMOUNT = #{amount} "
			+ "			, EXPIRE_DAYS = #{expireDays} "
			+ "			, EXPIRE_DAYS_TYPE = #{expireDaysType} "
			+ "			, SELLER = #{seller} "
			+ "			, USAGE = #{usage} "
			+ "			, COUPON_LENGTH = #{couponLength} "
			+ "			, STATUS = #{status} "
			+ "			, REGISTER = #{register} "
			+ "	WHERE SEQ = #{tmpSeq} "
			+ "		AND '1' = ( SELECT '1' "
			+ "					FROM KPC_EL_APPR "
			+ "					WHERE SEQ = #{seq} "    
			+ "						AND REQ_EMP_ID = #{register}) ";
	@Update(UPDATE_KPC_KCON_TMP)
	public int updateKpcKConTmp(@Param(value="merchantId") String merchantId, @Param(value="merchantPw") String merchantPw,
			@Param(value="title") String title, @Param(value="typeDetail") String typeDetail, @Param(value="typeCode") String typeCode,
			@Param(value="amount") String amount, @Param(value="expireDays") String expireDays, @Param(value="expireDaysType") String expireDaysType,
			@Param(value="seller") String seller, @Param(value="usage") String usage, @Param(value="couponLength") String couponLength,
			@Param(value="status") String status, @Param(value="register") String register, @Param(value="tmpSeq") String tmpSeq,
			@Param(value="seq") String seq );
	
	public static final String UPDATE_KPC_EL_APPR = "UPDATE KPC_EL_APPR "
			+ " SET REQ_EMP_ID  = #{register} "
            + "		, APPR_EMP_ID = #{apprEmpId} "
            + "		, STATUS      = #{status} "
            + "		, CREATE_ID   = #{register} "
            + "		, CREATE_DT   = Sysdate "
            + "	WHERE SEQ = #{seq} "
            + " 	AND REQ_EMP_ID = #{register} ";
	@Update(UPDATE_KPC_EL_APPR)
	public int updateKpcElAppr(@Param(value="register") String register, @Param(value="apprEmpId") String apprEmpId,
			@Param(value="status") String status, @Param(value="seq") String seq);
	
*/}
