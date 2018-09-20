package kr.co.kpcard.billingservice.app.repository.settlement.common;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettleResultCount;

@Mapper
public interface SettlementJobHistoryMapper {
	public static final String INSERT_SQL = "<script>"
			                              + "INSERT INTO SETTLE_JOB_HST    "
			                              + "            (                "
			                              + "               SEQ           "
			                              + "              ,SVC_CONN_ID   "
			                              + "              ,WORK_DT       "
			                              + "              ,STATUS        "
			                              + "              ,STATUS_DESC   "
			                              + "              ,CREATE_DT     " 
			                              + "            )                " 
			                              + "            VALUES           " 
			                              + "            (                "
			                              + "               #{seq}        "
			                              + "              ,#{svcconnId } "
			                              + "              ,#{workDt    } "
			                              + "              ,#{status    } "
			                              + "              ,#{statusDesc} "
			                              + "              ,SYSDATE       "                        
			                              + "            )                "
			                              + "</script>";
	
	public static final String UPDATE_SQL = "<script>"
			                              + "UPDATE SETTLE_JOB_HST                "
			                              + "   SET SVC_CONN_ID  = #{svcconnId } "
			                              + "      ,WORK_DT      = #{workDt    } "
			                              + "      ,STATUS       = #{status    } "
			                              + "      ,STATUS_DESC  = #{statusDesc} "
			                              + " WHERE SEQ          = #{seq}        "
			                              + "</script>"
			                              ;
	
	public static final String UPDATE_STATUS_SQL = "<script>"
			                                     + "UPDATE SETTLE_JOB_HST                "
			                                     + "   SET STATUS       = #{status    } "
			                                     + "      ,STATUS_DESC  = #{statusDesc} "
			                                     + " WHERE SEQ          = #{seq}        "
			                                     + "</script>"
			                                     ;
	
	public static final String SELECT_MAX_SEQ_SQL = "<script>"
			                                      + "SELECT NVL(MAX(TO_NUMBER(SEQ)), 0) + 1 FROM  SETTLE_JOB_HST "
			                                      + "</script>"
			                                      ;
	
	public static final String INSERT_RESULT_COUNT = " MERGE INTO SETTLE_RESULT_COUNT"
												   + "	USING DUAL ON (WORK_DT=#{workDt} AND JOB_DIVIDER=#{jobDivider})"
												   + "	WHEN MATCHED THEN"
												   + "	UPDATE SET "
												   + "	MATCH_CNT=#{sts1}"
												   + "	,DISMATCH_CNT=#{sts2}"
												   + "	,KPC_ONLY_CNT=#{sts3}"
												   + "	,GS_ONLY_CNT=#{sts4}"
												   + "	,HM_ONLY_CNT=#{sts5}"
												   + "	,MATCH_AMT=#{stsAmt1}"
												   + "	,DISMATCH_AMT=#{stsAmt2}"
												   + "	,KPC_ONLY_AMT=#{stsAmt3}"
												   + "	,GS_ONLY_AMT=#{stsAmt4}"
												   + "	,HM_ONLY_AMT=#{stsAmt5}"
												   + "  ,CREATE_DT=SYSDATE"
												   + " WHEN NOT MATCHED THEN"
												   + "	INSERT VALUES(#{jobDivider}, #{workDt}, #{sts1}, #{sts2}, #{sts3}, #{sts4}, #{sts5}, SYSDATE, #{stsAmt1}, #{stsAmt2}, #{stsAmt3}, #{stsAmt4}, #{stsAmt5})";
	
	@Resource(name="approvalDataSource")
	@Insert(INSERT_SQL)
	public int jobStart(SettlementJobHistory SettlementJobHistory);
	
	@Resource(name="approvalDataSource")
	@Update(UPDATE_SQL)
	public int jobUpdate(SettlementJobHistory SettlementJobHistory);
	
	@Resource(name="approvalDataSource")
	@Update(UPDATE_STATUS_SQL)
	public int jobUpdateStatus(SettlementJobHistory SettlementJobHistory);
	
	@Resource(name="approvalDataSource")
	@Select(SELECT_MAX_SEQ_SQL)
	public int selectJobMaxSeq();	
	
	@Resource(name="approvalDataSource")
	@Update(INSERT_RESULT_COUNT)
	public int jobInsertSettleResult(GsSettleResultCount resultCount);
			
}
