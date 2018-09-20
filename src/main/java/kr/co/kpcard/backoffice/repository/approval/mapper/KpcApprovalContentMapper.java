package kr.co.kpcard.backoffice.repository.approval.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;

/**
 * KPC_APPROVAL_CONTENT 테이블
 * 승인 요청 내용 맵퍼
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Mapper
public interface KpcApprovalContentMapper {
	
	public static final String CREATE_KPC_APPROVAL_CONTENT_SEQ = "SELECT NVL(MAX(SEQ),0) + 1 SEQ FROM KPC_APPROVAL_CONTENT";
	@Select(CREATE_KPC_APPROVAL_CONTENT_SEQ)
	public Long createKpcApprovalContentSeq();
	
	public static final String CREATE_KPC_APPROVAL_CONTENT = "INSERT INTO KPC_APPROVAL_CONTENT ( "
			+ " 	 SEQ "
			+ " 	,WORK_TYPE "
			+ " 	,CREATE_DT "
			+ " 	,PREVIOUS_CONTENT "
			+ " 	,CONTENT "
			+ " ) VALUES ( "
			+ " 	 #{apprContent.seq} "
			+ " 	,#{apprContent.workType} "
			+ " 	,SYSDATE "
			+ " 	,#{apprContent.previousContent, jdbcType=CLOB} "
			+ " 	,#{apprContent.content, jdbcType=CLOB} "
			+ " )";
	@Insert(CREATE_KPC_APPROVAL_CONTENT)
	public void createKpcApprovalContent(@Param("apprContent")ApprovalContent apprContent);

	public static final String READ_KPC_APPROVAL_CONTENT = "<script>"
			+ " SELECT "
			+ " 	 SEQ "
			+ "		,WORK_TYPE as workType "
			+ "		,CREATE_DT as createDate "
			+ "		,PREVIOUS_CONTENT as previousContent "
			+ "		,content "
			+ " FROM "
			+ "		KPC_APPROVAL_CONTENT "
			+ " WHERE "
			+ " 	SEQ = #{seq} "
			+"</script>";
	@Select(READ_KPC_APPROVAL_CONTENT)
	public ApprovalContent readKpcApprovalContent(@Param("seq")Long seq); 
	
	public static final String UPDATE_KPC_APPROVAL_CONTENT = "<script>"
			+ " UPDATE KPC_APPROVAL_CONTENT SET "
			+ "  PREVIOUS_CONTENT = #{apprContent.previousContent, jdbcType=CLOB} "
			+ " ,CONTENT = #{apprContent.content} "
			+ " WHERE SEQ = #{apprContent.seq} "
			+ " AND WORK_TYPE = #{apprContent.workType} "
			+"</script>";
	@Update(UPDATE_KPC_APPROVAL_CONTENT)
	public void updateApprovalContent(@Param("apprContent")ApprovalContent apprContent);
	
	
}
