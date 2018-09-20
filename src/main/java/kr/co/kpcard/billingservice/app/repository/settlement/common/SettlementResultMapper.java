package kr.co.kpcard.billingservice.app.repository.settlement.common;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SettlementResultMapper {
	
	public static final String SETTLE_FAIL_LIST=""
			+"SELECT WORK_DT workDt"
			+"  FROM SETTLE_RESULT"
			+" WHERE JOB_DIVIDER = #{jobDivider}"
			+"   AND WORK_DT BETWEEN TO_CHAR(TO_DATE(#{workDt}) -8,'YYYYMMDD') AND TO_CHAR(TO_DATE(#{workDt}) -1,'YYYYMMDD')"
			+"   AND STATUS IN ('STS-0002', 'STS-0003', 'STS-0004')"
			+"   AND SETTLE_SEQ IS NOT NULL"
			+" GROUP BY WORK_DT"			
			;
	
	public static final String SETTLE_GS_SELECT =""
		+"SELECT JOB_DIVIDER AS jobDivider"
		+"     , WORK_DT     AS workDt"
		+"     , STS0001_AMT AS agreeAmt"
		+"     , STS0001_CNT AS agreeCnt"
		+"     , STS0002_AMT AS errorAmt"
		+"     , STS0002_CNT AS errorCnt"
		+"     , STS0003_AMT AS kpcOnlyAmt"
		+"     , STS0003_CNT AS kpcOnlyCnt"
		+"     , STS0004_AMT AS gsOnlyAmt"
		+"     , STS0004_CNT AS gsOnlyCnt"
		+"     , CASE WHEN STS0002_CNT + STS0003_CNT + STS0004_CNT = 0"
		+"            THEN '불일치없음'"
		+"            ELSE '불일치존재'"
		+"        END compareResult"
		+"  FROM ("
		+"        SELECT A.JOB_DIVIDER"
		+"             , A.STATUS"
		+"             , A.WORK_DT"
		+"             , SUM(B.DEAL_AMT) SUM_DEAL_AMT"
		+"             , COUNT(B.DEAL_AMT) CNT"
		+"          FROM SETTLE_RESULT A"
		+"             , SETTLE_GS B"
		+"         WHERE A.JOB_DIVIDER = #{jobDivider}"
		+"           AND A.JOB_DIVIDER = B.JOB_DIVIDER"     
		+"           AND A.SETTLE_SEQ  = B.SEQ"
		+"           AND A.WORK_DT     = B.WORK_DT"
		+"           AND A.WORK_DT    >= #{workDt} AND A.WORK_DT <= #{workDt}"     
		+"           AND A.STATUS     IN ('STS-0001', 'STS-0002' , 'STS-0003' , 'STS-0004')"
		+"         GROUP BY A.JOB_DIVIDER , A.WORK_DT , A.STATUS"
		+"        )"
		+"  PIVOT ("
		+"           SUM(SUM_DEAL_AMT) AS AMT , SUM(CNT) AS CNT"
		+"           FOR STATUS IN ('STS-0001' AS STS0001 , 'STS-0002' AS STS0002 , 'STS-0003' AS STS0003 , 'STS-0004' AS STS0004)"
		+"  )";
	
	public static final String SETTLE_GSNPOINT_SELECT =""
			+"SELECT JOB_DIVIDER AS jobDivider"
			+"     , WORK_DT     AS workDt"
			+"     , STS0001_AMT AS agreeAmt"
			+"     , STS0001_CNT AS agreeCnt"
			+"     , STS0002_AMT AS errorAmt"
			+"     , STS0002_CNT AS errorCnt"
			+"     , STS0003_AMT AS kpcOnlyAmt"
			+"     , STS0003_CNT AS kpcOnlyCnt"
			+"     , STS0004_AMT AS gsOnlyAmt"
			+"     , STS0004_CNT AS gsOnlyCnt"
			+"     , CASE WHEN STS0002_CNT + STS0003_CNT + STS0004_CNT = 0"
			+"            THEN '불일치없음'"
			+"            ELSE '불일치존재'"
			+"        END compareResult"
			+"  FROM ("
			+"        SELECT A.JOB_DIVIDER"
			+"             , A.STATUS"
			+"             , A.WORK_DT"
			+"             , SUM(B.USE_POINT) SUM_DEAL_AMT"
			+"             , COUNT(B.USE_POINT) CNT"
			+"          FROM SETTLE_RESULT A"
			+"             , SETTLE_GSNPOINT B"
			+"         WHERE A.JOB_DIVIDER = #{jobDivider}"
			+"           AND A.WORK_DT    >= #{workDt} AND A.WORK_DT <= #{workDt}"     
			+"           AND A.SETTLE_SEQ  = B.SEQ"
			+"           AND A.WORK_DT     = B.WORK_DT"
			+"           AND A.STATUS     IN ('STS-0001', 'STS-0002' , 'STS-0003' , 'STS-0004')"
			+"         GROUP BY A.JOB_DIVIDER , A.WORK_DT , A.STATUS"
			+"        )"
			+"  PIVOT ("
			+"           SUM(SUM_DEAL_AMT) AS AMT , SUM(CNT) AS CNT"
			+"           FOR STATUS IN ('STS-0001' AS STS0001 , 'STS-0002' AS STS0002 , 'STS-0003' AS STS0003 , 'STS-0004' AS STS0004)"
			+"  )";
	
	public static final String UPDATE_SETTLE_CANCEL_DATA_TO_SUCCESS = ""
			+" MERGE INTO KPC_APPROVAL.SETTLE_RESULT T"
			+" USING"
			+" ("
			+"     SELECT distinct A.SEQ"
			+"          , A.JOB_DIVIDER"
			+"          , A.ORDER_NO"
			+"          , A.APPROVAL_NO"
			+"          , A.DEAL_TYPE"
			+"          , A.DEAL_DIVIDER"
			+"          , A.ORDER_AMT"
			+"          , A.WORK_DT"
			+"          , A.APPROVAL_DT"
			+"          , A.APPROVAL_TIME"
			+"     FROM KPC_APPROVAL.SETTLE_RESULT A"
			+"         ,KPC_APPROVAL.SETTLE_RESULT B"
			+"     WHERE A.WORK_DT = #{settleDate}"
			+"     AND A.STATUS = 'STS-0003' AND B.STATUS = 'STS-0003'"
			+"     AND A.JOB_DIVIDER IN ('GSPOP', 'GSSM') AND A.JOB_DIVIDER = B.JOB_DIVIDER"
			+"     AND A.ORDER_AMT = B.ORDER_AMT"
			+"     AND A.DEAL_TYPE = B.DEAL_TYPE"
			+"     AND A.ORDER_NO = B.ORDER_NO"
			+"     AND A.DEAL_DIVIDER = '0' AND B.DEAL_DIVIDER = '9'"
			+" ) S"
			+" ON (T.ORDER_NO = S.ORDER_NO)"
			+" WHEN MATCHED THEN"
			+" UPDATE"
			+"    SET T.STATUS      = 'STS-0001'"
			+"       ,T.STATUS_DESC = NULL"
			+"";	
	
	public static final String SELECT_FAIL_WORK_DT="SELECT JOB_DIVIDER, WORK_DT "
												 + " FROM SETTLE_RESULT_COUNT "
												 + " WHERE WORK_DT>=TO_CHAR(TO_DATE(#{workDt})-7,'YYYYMMDD') "
												 + " AND (DISMATCH_CNT>0 OR KPC_ONLY_CNT>0 OR GS_ONLY_CNT > 0 OR HM_ONLY_CNT > 0)";
	
	@Resource(name="approvalDataSource")
	@Select(SETTLE_FAIL_LIST)		
	public List<SettlementResult> selectSettlementFailList(@Param("jobDivider")String jobDivier,@Param("workDt")String workDt);
	
	@Resource(name="approvalDataSource")
	@Select(SETTLE_GS_SELECT)		
	public List<SettlementResult> selectGsSettlementResultList(@Param("jobDivider")String jobDivier,@Param("workDt")String workDt);
	
	@Resource(name="approvalDataSource")
	@Select(SETTLE_GSNPOINT_SELECT)		
	public List<SettlementResult> selectGsNpointSettlementResultList(@Param("jobDivider")String jobDivier,@Param("workDt")String workDt);
	
	@Resource(name="approvalDataSource")
	@Update(UPDATE_SETTLE_CANCEL_DATA_TO_SUCCESS)	
	public void updateSettleCancelDataToSuccess(@Param("settleDate") String settleDate);	
	
	@Resource(name="approvalDataSource")
	@Select(SELECT_FAIL_WORK_DT)
	public List<SettlementWorkDate> getSettlementReWorkDate(@Param("workDt") String workDt); 
}
