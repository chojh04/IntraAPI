package kr.co.kpcard.backoffice.repository.approval.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;


/**
 * KPC_APPROVAL 테이블
 * 승인 요청 정보 맵퍼
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Mapper
public interface KpcApprovalMapper {

	public static final String CREATE_KPC_APPROVAL_SEQ = "SELECT NVL(MAX(SEQ),0) + 1 SEQ FROM KPC_APPROVAL";
	@Select(CREATE_KPC_APPROVAL_SEQ)
	public Long createKpcApprovalSeq();
	
	public static final String CREATE_KPC_APPROVAL= "INSERT INTO KPC_APPROVAL( "
			+ " 	 SEQ "
			+ " 	,CONTENT_SEQ "
			+ " 	,WORK_TYPE "
			+ " 	,REQ_TYPE "
			+ " 	,STATUS "
			+ " 	,REF_ID "
			+ " 	,REF_TITLE "
			+ " 	,DESCRIPTION "
			+ " 	,KEYWORD "
			+ " 	,REQ_EMP_ID "
			+ " 	,REQ_DT "
			+ " 	,REQ_MEMO "
			+ " 	,APPR_EMP_ID "
			+ " 	,APPR_DT "
			+ " 	,APPR_MEMO "
			+ " 	,CREATE_DT "
			+ " 	,UPDATE_DT "
			+ " ) VALUES ( "
			+ " 	 #{apprInfo.seq} "
			+ " 	,#{apprInfo.contentSeq} "
			+ " 	,#{apprInfo.workType} "
			+ " 	,#{apprInfo.reqType} "
			+ " 	,#{apprInfo.status} "
			+ " 	,#{apprInfo.refId, jdbcType=VARCHAR} "
			+ " 	,#{apprInfo.refTitle, jdbcType=VARCHAR} "
			+ " 	,#{apprInfo.description, jdbcType=VARCHAR} "
			+ " 	,#{apprInfo.keyword, jdbcType=VARCHAR} "
			+ " 	,#{apprInfo.reqEmpId} "
			+ " 	,SYSDATE "
			+ " 	,#{apprInfo.reqMemo, jdbcType=VARCHAR} "
			+ " 	,#{apprInfo.apprEmpId} "
			+ " 	,#{apprInfo.apprDate, jdbcType=DATE} "
			+ " 	,#{apprInfo.apprMemo, jdbcType=VARCHAR} "
			+ " 	,SYSDATE "
			+ " 	,SYSDATE "
			+ " )";
	@Insert(CREATE_KPC_APPROVAL)
	public void createKpcApproval(@Param("apprInfo")Approval apprInfo);

	public static final String READ_KPC_APPROVAL = "<script>"
			+ " SELECT "
			+ " 	 SEQ "
			+ " 	,CONTENT_SEQ as contentSeq "
			+ "		,WORK_TYPE as workType "
			+ "		,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=WORK_TYPE) as workTypeName "
			+ "		,REQ_TYPE as reqType "
			+ "		,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=REQ_TYPE) as reqTypeName "
			+ "		,STATUS "
			+ "		,REF_ID as refId "
			+ "		,REF_TITLE as refTitle "
			+ "		,DESCRIPTION "
			+ "		,KEYWORD "
			+ "		,REQ_EMP_ID as reqEmpId "
			+ "		,(SELECT NAME FROM KPC_EMPLOYE WHERE EMP_ID = REQ_EMP_ID) as reqEmpName "
			+ "		,REQ_DT as reqDate "
			+ "		,REQ_MEMO as reqMemo "
			+ "		,APPR_EMP_ID as apprEmpId "
			+ "		,(SELECT NAME FROM KPC_EMPLOYE WHERE EMP_ID = APPR_EMP_ID) as apprEmpName "
			+ "		,APPR_DT as apprDate "
			+ "		,APPR_MEMO as apprMemo "
			+ "		,CREATE_DT as createDate "
			+ "		,UPDATE_DT as updateDate "
			+ " FROM "
			+ "		KPC_APPROVAL "
			+ " WHERE "
			+ " 	seq = #{seq} "
			+"</script>";
	@Select(READ_KPC_APPROVAL)
	public Approval readKpcApproval(@Param("seq") Long seq);
	
	public static final String READ_KPC_APPROVAL_BY_CONTENT_SEQ = "<script>"
			+ " SELECT "
			+ " 	 SEQ "
			+ " 	,CONTENT_SEQ as contentSeq "
			+ "		,WORK_TYPE as workType "
			+ "		,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=WORK_TYPE) as workTypeName "
			+ "		,REQ_TYPE as reqType "
			+ "		,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=REQ_TYPE) as reqTypeName "
			+ "		,STATUS "
			+ "		,REF_ID as refId "
			+ "		,REF_TITLE as refTitle "
			+ "		,DESCRIPTION "
			+ "		,KEYWORD "
			+ "		,REQ_EMP_ID as reqEmpId "
			+ "		,(SELECT NAME FROM KPC_EMPLOYE WHERE EMP_ID = REQ_EMP_ID) as reqEmpName "
			+ "		,REQ_DT as reqDate "
			+ "		,REQ_MEMO as reqMemo "
			+ "		,APPR_EMP_ID as apprEmpId "
			+ "		,(SELECT NAME FROM KPC_EMPLOYE WHERE EMP_ID = APPR_EMP_ID) as apprEmpName "
			+ "		,APPR_DT as apprDate "
			+ "		,APPR_MEMO as apprMemo "
			+ "		,CREATE_DT as createDate "
			+ "		,UPDATE_DT as updateDate "
			+ " FROM "
			+ "		KPC_APPROVAL "
			+ " WHERE "
			+ " 	content_seq = #{contentSeq} "
			+"</script>";
	@Select(READ_KPC_APPROVAL_BY_CONTENT_SEQ)
	public Approval readKpcApprovalByContentSeq(@Param("contentSeq") Long contentSeq);
	
	public static final String UPDATE_KPC_APPROVAL = "<script>"
			+ " UPDATE KPC_APPROVAL SET "
			+ " 	STATUS = #{apprInfo.status} "
			+ " 	<if test='apprInfo.description!=\"\" and apprInfo.description != null'>,DESCRIPTION = #{apprInfo.description}</if>"
			+ " 	,APPR_DT = SYSDATE "
			+ " 	,APPR_MEMO = #{apprInfo.apprMemo, jdbcType=VARCHAR}"
			+ " 	,UPDATE_DT = SYSDATE "
			+ " WHERE SEQ = #{apprInfo.seq} "
			+ " 	AND REQ_EMP_ID = #{apprInfo.reqEmpId} "
			+"</script>";
	@Update(UPDATE_KPC_APPROVAL)
	public long updateKpcApproval(@Param("apprInfo")Approval apprInfo);

	public static final String UPDATE_KPC_APPROVAL_BY_REQUEST_CANCEL = "<script>"
			+ " UPDATE KPC_APPROVAL SET "
			+ "  STATUS = 'ARST-0004' "
			+ " ,DESCRIPTION = #{apprInfo.description ,jdbcType=VARCHAR} "
			+ " ,UPDATE_DT = SYSDATE "
			+ " WHERE SEQ = #{apprInfo.seq} "
			+ " AND REQ_EMP_ID = #{apprInfo.reqEmpId} "
			+"</script>";
	@Update(UPDATE_KPC_APPROVAL_BY_REQUEST_CANCEL)
	public void updateKpcApprovalByRequestCancel(@Param("apprInfo")Approval apprInfo);

	public static final String UPDATE_KPC_APPROVAL_REQUEST = "<script>"
			+ " UPDATE KPC_APPROVAL SET "
			+ "  REF_TITLE = #{apprInfo.refTitle} "
			+ " ,KEYWORD = #{apprInfo.keyword} "
			+ " ,DESCRIPTION = #{apprInfo.description} "
			+ " ,APPR_EMP_ID = #{apprInfo.apprEmpId} "
			+ " ,REQ_MEMO = #{apprInfo.reqMemo, jdbcType=VARCHAR}"
			+ " ,UPDATE_DT = SYSDATE "
			+ " WHERE SEQ = #{apprInfo.seq} "
			+ " AND REQ_EMP_ID = #{apprInfo.reqEmpId} "
			+"</script>";
	@Update(UPDATE_KPC_APPROVAL_REQUEST)
	public void updateKpcApprovalRequest(@Param("apprInfo")Approval apprInfo);
	
	public static final String EXIST_APPROVAL_FOR_REF_ID = " SELECT "
			+ "	case when count(ref_id) > 0 then 1 else 0 end as result "
			+ "	FROM "
			+ "		KPC_APPROVAL "
			+ "	WHERE "
			+ "	status = 'ARST-0001' "
			+ "	AND REF_ID = #{refId} ";
	@Select(EXIST_APPROVAL_FOR_REF_ID)
	public boolean existApprovalRequestForRefId(@Param("refId")String refId);
	

	public static final String INSERT_APPROVAL = 
						" MERGE INTO KPC_APPROVAL APPR "
					  + "	USING DUAL "
					  + "		ON (APPR.seq = #{apprInfo.seq}) "
					  + "	WHEN MATCHED THEN "
					  + "		UPDATE SET "
					  + "			APPR.DESCRIPTION = #{apprInfo.description, jdbcType=VARCHAR}"
					  + "			, APPR.KEYWORD = #{apprInfo.keyword, jdbcType=VARCHAR}"
					  + "			, APPR.REQ_MEMO = #{apprInfo.reqMemo, jdbcType=VARCHAR}"
					  + "			, APPR.APPR_EMP_ID = #{apprInfo.apprEmpId}"
					  + "			, APPR.UPDATE_DT = SYSDATE"
					  + "	WHEN NOT MATCHED THEN "
					  + "		INSERT ( SEQ"
					  + "				,CONTENT_SEQ "
					  + "				,WORK_TYPE "
					  + "				,REQ_TYPE "
					  + "				,STATUS "
					  + "				,REF_ID "
					  + "				,REF_TITLE "
					  + "				,DESCRIPTION "
					  + " 				,KEYWORD "
					  + " 				,REQ_EMP_ID "
					  + " 				,REQ_DT "
					  + " 				,REQ_MEMO "
					  + " 				,APPR_EMP_ID "
					  + " 				,APPR_DT "
					  + " 				,APPR_MEMO "
					  + " 				,CREATE_DT "
					  + " 				,UPDATE_DT "
					  + "			) VALUES ( "
					  + " 	 			#{apprInfo.seq} "
					  + " 				,#{apprInfo.contentSeq} "
					  + " 				,#{apprInfo.workType} "
					  + " 				,#{apprInfo.reqType} "
					  + " 				,#{apprInfo.status} "
					  + " 				,#{apprInfo.refId, jdbcType=VARCHAR} "
					  + " 				,#{apprInfo.refTitle, jdbcType=VARCHAR} "
					  + " 				,#{apprInfo.description, jdbcType=VARCHAR} "
					  + " 				,#{apprInfo.keyword, jdbcType=VARCHAR} "
					  + " 				,#{apprInfo.reqEmpId} "
					  + " 				,SYSDATE "
					  + " 				,#{apprInfo.reqMemo, jdbcType=VARCHAR} "
					  + " 				,#{apprInfo.apprEmpId} "
					  + " 				,#{apprInfo.apprDate, jdbcType=DATE} "
					  + " 				,#{apprInfo.apprMemo, jdbcType=VARCHAR} "
					  + " 				,SYSDATE "
					  + " 				,SYSDATE "
					  + " 			)";					  
	@Insert(INSERT_APPROVAL)
	public long insertApprovalInfo(@Param("apprInfo") Approval apprInfo);
	
	public static final String INSERT_APPROVAL_CONTENT = 
						"MERGE INTO KPC_APPROVAL_CONTENT"
				     + "	USING DUAL"
				     + "		ON (SEQ = #{apprContent.seq})"
				     + "	WHEN MATCHED THEN"
				     + "		UPDATE SET"				     				 
					 + " 		   	PREVIOUS_CONTENT = #{apprContent.previousContent, jdbcType=CLOB}"
					 + "		 	,CONTENT = #{apprContent.content, jdbcType=CLOB}"
					 + "	WHEN NOT MATCHED THEN"
					 + "		INSERT ( SEQ "
					 + " 				,WORK_TYPE "
					 + "			 	,CREATE_DT "
					 + "			 	,PREVIOUS_CONTENT "
					 + "			 	,CONTENT "
					 + " 		) VALUES ( "
					 + " 	 			#{apprContent.seq} "
					 + " 				,#{apprContent.workType} "
					 + " 				,SYSDATE "
					 + " 				,#{apprContent.previousContent, jdbcType=CLOB} "
					 + " 				,#{apprContent.content, jdbcType=CLOB}"
					 + "		) ";
	@Insert(INSERT_APPROVAL_CONTENT)
	public long insertApprovaContentlInfo(@Param("apprContent") ApprovalContent apprContent);
	
	
}
