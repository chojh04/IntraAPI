package kr.co.kpcard.billingservice.app.repository.settlement.hm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.kpcard.common.utils.JobStatusCode;

@Mapper
public interface HappyMoneySettlementMapper {
	
	public static final String INSERT_SQL =""
	        +"<script>"
	        +"INSERT ALL"
	        +" <foreach collection='hmDataRecordList' item='data' index='index' separator='' close=''>"
	        +"  INTO SETTLE_HM"
	        +"       VALUES"
	        +"       ("
	        +"         #{workDt  }"
	        +"        ,(SELECT (NVL(MAX(SEQ), 0) + 1) + #{index} FROM SETTLE_HM WHERE WORK_DT = #{workDt} )"
	        +"        ,#{data.dealDate  }"
	        +"        ,#{data.dealTime  }"
	        +"        ,#{data.orderNo   }"
	        +"        ,#{data.approvalNo}"
	        +"        ,#{data.kind      }"
	        +"        ,#{data.status    }"
	        +"        ,NULL"
	        +"        ,#{data.amount    }"            
	        +"        ,SYSDATE"            
	        +"       )"
	        +"</foreach>"
	        +"SELECT * FROM DUAL"
	        +"</script>";
	
	public static final String DELETE_SQL =""
			+"<script>"
			+"DELETE FROM SETTLE_HM WHERE WORK_DT = #{workDt}"
			+"</script>";
	
	public static final String DELETE_SETTLE_SQL =""
			+"<script>"
			+"DELETE FROM SETTLE_RESULT WHERE WORK_DT = #{settleDate} AND JOB_DIVIDER = #{jobDivider}"
			+"</script>";
	
	public static final String SETTLE_SQL =""
			+"<script>"
			+" INSERT INTO SETTLE_RESULT"
			+" SELECT (SELECT NVL(MAX(TO_NUMBER(SEQ)), 0)   FROM SETTLE_RESULT WHERE WORK_DT = A.WORK_DT) + ROWNUM"
			+"      , '"+ JobStatusCode.HAPPYMONEY+"'"
			+"      , ORDER_NO"
			+"      , APPROVAL_NO"
			+"		, MERCHANTID"
			+"		, DEAL_TYPE"
			+"		, DEAL_DIVIDER"
			+"		, DEAL_AMT"
			+"		, A.WORK_DT"
			+"		, SEQ"
			+"		, CASE WHEN STL1 = 'STL-0001'"
			+"			       THEN 'STS-0003'"
			+"			       WHEN STL10 = 'STL-0010'"
			+"			       THEN 'STS-0005'" // HM에만 존재
			+"			       WHEN STL1 || STL10 || STL3 ||  STL5  IS NOT NULL"
			+"			       THEN 'STS-0002'"
			+"			       ELSE 'STS-0001'"
			+"			    END"
			+"		, CASE WHEN STL1 IS NOT NULL"
			+"			       THEN STL1"
			+"			       WHEN STL10 IS NOT NULL"
			+"			       THEN STL10"
			+"			       WHEN STL3 ||  STL5  IS NOT NULL"
			+"			       THEN DECODE( STL3 , NULL , '' , STL3 || ',') || DECODE( STL5 , NULL , '' , STL5 || ',')"
			+"			       ELSE ''"
			+"			   END"
			+"			, SYSDATE"
			+"			, APPROVAL_DT"
			+"			, APPROVAL_TIME"
			+" FROM ("
			+"      SELECT DECODE( A.ORDER_NO , NULL , 'STL-0001' , '') STL1"
			+"           , DECODE( B.M_ONO , NULL , 'STL-0010' , '') STL10"
			+"           , DECODE( A.APPROVAL_NO , B.O_ADMITNO, '' , 'STL-0003' ) STL3"
			+"           , DECODE( A.AMOUNT , B.O_T_MONEY , '' , 'STL-0005') STL5"
			+"           , NVL(A.ORDER_NO,B.M_ONO) ORDER_NO"
			+"           , NVL(A.APPROVAL_NO , B.O_ADMITNO) APPROVAL_NO"
			+"           , '' MERCHANTID"
			+"           , NVL(A.WORK_DT , B.O_DATE) WORK_DT"
			+"           , A.SEQ"
			+"           , NVL(B.O_RESULT ,  A.STATUS) DEAL_TYPE"
			+"           , '' DEAL_DIVIDER"
			+"           , NVL(A.AMOUNT,B.O_T_MONEY) DEAL_AMT"
			+"           , NVL(B.O_ADMITDATE,'') APPROVAL_DT"
			+"           , '' APPROVAL_TIME"
			+"        FROM (SELECT *"
			+"                FROM SETTLE_HM"
			+"               WHERE DEAL_DT = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"            )A"
			+"        FULL OUTER JOIN"
			+"        ( SELECT *"
			+"            FROM ORDERLIST B"
			+"           WHERE B.O_DATE = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"             AND B.M_ID = 'happymoney_pop'"
			+"        ) B"
			+"          ON A.DEAL_DT = B.O_DATE"
			+"         AND A.ORDER_NO = B.M_ONO"
			+"         AND A.STATUS = B.O_RESULT"
			+"    ) A"
			+" </script>";	

	@Resource(name="approvalDataSource")
	@Insert(INSERT_SQL)
	public int insert(@Param("hmDataRecordList") List<HappyMoneySettlement> hmDataRecordList,@Param("workDt") String workDt);
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_SQL)
	public int delete(String workDt);
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_SETTLE_SQL)	
	public void deleteSettlement(@Param("jobDivider") String jobDivider,@Param("settleDate")String settleDate);
	
	@Resource(name="approvalDataSource")
	@Insert(SETTLE_SQL)	
	public int settlement(@Param("settleDate")String settleDate);	
			
}
