package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GsPointSettlementMapper {
	
public static final String INSERT_SQL =""
        +"<script>"
        +"INSERT ALL"
        +" <foreach collection='gsPointDataRecordList' item='data' index='index' separator='' close=''>"
        +"  INTO SETTLE_GSNPOINT"
        +"       VALUES"
        +"       ("
        +"         #{data.workDt}"
        //+"        ,(SELECT (NVL(MAX(TO_NUMBER(SEQ)), 0) + 1) + #{index} FROM SETTLE_GSNPOINT WHERE WORK_DT = #{data.workDt} )"
        +"		  ,(#{index			  } + 1) + #{startIdx} "	
        +"        ,#{data.divider    }"
        +"        ,#{data.seqNo      }"
        +"        ,#{data.cardNo     }"
        +"        ,#{data.storeCd    }"
        +"        ,#{data.dealDt     }"
        +"        ,#{data.dealDivider}"
        +"        ,#{data.usePoint   }"
        +"        ,#{data.approvalDt }"
        +"        ,#{data.approvalNo }"
        +"        ,#{data.origAplDt  }"
        +"        ,#{data.origAplNo  }"
        +"        ,SUBSTR(#{data.filler     } , 0, 20)"
        +"       )"
        +"</foreach>"
        +"SELECT * FROM DUAL"
        +"</script>";

	public static final String DELETE_SQL =""
		+"<script>"
		+"DELETE FROM SETTLE_GSNPOINT WHERE WORK_DT = #{workDt}"
		+"</script>";

	public static final String SELECT_SQL =""
			+"<script>"
            +"SELECT ROWNUM"
            +"      ,A.GIFT_NO"
            +"      ,E.EXTEND_NO cardNo"
            +"      ,'E1010' storeCd"
            +"      ,TO_CHAR(A.APLDATE, 'YYYYMMDDHH24MISS') dealDt"
            +"      ,DECODE(A.APLSTATUS, 'O1', '1', 'D1', '2', '0') dealDivider"
            +"      ,A.RC_AMT usePoint"
            +"      ,TO_CHAR(A.APLDATE, 'YYYYMMDD') approvalDt"
            +"      ,NVL(A.C_APLNO, ' ') approvalNo"  
            +"  FROM RC_APPROVAL A"
            +"     , GIFTEXTEND E"
            +" WHERE A.GIFT_NO = E.GIFT_NO"
            +"   AND A.APLONLINEID = 'gsrpoppoint'"          
            +"   AND A.SALESDATE = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"        
            +"   AND A.PAY_METHDO IN ('2', 'A2')"
            +" ORDER BY A.APLDATE"     			
			+"</script>";
	
	public static final String SETTLE_SQL =""
			+"<script>"
			+"INSERT INTO SETTLE_RESULT"
		    +" SELECT (SELECT NVL(MAX(TO_NUMBER(SEQ)), 0)   FROM SETTLE_RESULT WHERE WORK_DT = A.WORK_DT) + ROWNUM"
			+"	      , #{jobDivider}"
			+"	      , DEAL_NO"
			+"	      , APPROVAL_NO"
			+"	      , MERCHANTID"
			+"	      , ''"
			+"	      , DEAL_DIVIDER"
			+"	      , USE_POINT"
			+"	      , #{settleDate}"
			+"	      , SEQ"
			+"        , CASE WHEN STL1 = 'STL-0001'"
			+"               THEN 'STS-0003'"
			+"               WHEN STL2 = 'STL-0002'"
	        +"               THEN 'STS-0004'"
			+"	             WHEN STL1 || STL2 || STL6 || STL7 || STL8 || STL9 IS NOT NULL"
			+"	             THEN 'STS-0002'"
			+"	             ELSE 'STS-0001'"
			+"	         END"
			+"        , CASE WHEN STL1 IS NOT NULL"
			+"               THEN STL1"
			+"               WHEN STL2 IS NOT NULL"
			+"               THEN STL2"
			+"               WHEN STL6 || STL7 || STL8 || STL9 IS NOT NULL"
			+"               THEN DECODE( STL6 , NULL , '' , STL6 || ',') || DECODE( STL7 , NULL , '' , STL7 || ',')"
			+"                 || DECODE( STL8 , NULL , '' , STL8 || ',') || DECODE( STL9 , NULL , '' , STL9 || ',')"
			+"               ELSE ''"
			+"        END"
			+"	      , SYSDATE"
			+"	      , APPROVAL_DT"
			+"	      , APPROVAL_TIME"
			+"	  FROM ("
			+"	      SELECT  DECODE( A.CARD_NO , NULL , 'STL-0001' , '') STL1"               
			+"	             ,DECODE( B.CARD_NO , NULL , 'STL-0002' , '') STL2"               
			+"	             ,DECODE( A.DEAL_DIVIDER , B.DEAL_DIVIDER , '' , 'STL-0006') STL6"               
			+"	             ,DECODE( A.CARD_NO , B.CARD_NO , '' , 'STL-0007') STL7"
			+"	             ,DECODE( A.USE_POINT, B.USE_POINT + (B.USE_POINT * 0.05), '' , 'STL-0008') STL8"
			+"	             ,DECODE( A.STORE_CD , B.STORE_CD , '' , 'STL-0009') STL9"
			+"               ,B.DEAL_NO DEAL_NO"
			+"               , NVL(A.APPROVAL_NO , B.APPROVAL_NO) APPROVAL_NO"
			+"               , B.MERCHANTID"
			+"               , NVL(A.WORK_DT , B.DEAL_DT) WORK_DT"
			+"               , A.SEQ"		   
			+"               , B.DEAL_DIVIDER"		   
			+"               , NVL(A.USE_POINT,B.USE_POINT) USE_POINT"	
	        +"               , NVL(B.APPROVAL_DT,A.APPROVAL_DT) APPROVAL_DT"		   
	        +"               , NVL(B.APPROVAL_TIME,'000000') APPROVAL_TIME"					
			+"	         FROM ("
			+"	                SELECT *"
			+"	                  FROM SETTLE_GSNPOINT"
			+"	                 WHERE APPROVAL_DT = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"	              ) A"
			+"	         FULL OUTER JOIN"
			+"	         ("
			+"	           SELECT ROWNUM"
			+"	                 ,A.GIFT_NO"
			+"	                 ,E.EXTEND_NO CARD_NO"
			+"	                 ,'E1010' STORE_CD"
			+"	                 ,TO_CHAR(A.APLDATE, 'YYYYMMDDHH24MISS') DEAL_DT"
			+"	                 ,DECODE(A.APLSTATUS, 'O1', '1', 'D1', '2', '0') DEAL_DIVIDER"
			+"	                 ,A.RC_AMT USE_POINT"
			+"	                 ,TO_CHAR(A.APLDATE, 'YYYYMMDD') APPROVAL_DT"
			+"	                 ,TO_CHAR(A.APLDATE, 'HHMISS') APPROVAL_TIME"
			+"	                 ,NVL(A.C_APLNO, ' ') APPROVAL_NO"
			+"	                 ,A.APLORDERNO DEAL_NO"
			+"	                 ,A.APLONLINEID MERCHANTID"
			+"	             FROM RC_APPROVAL A"
			+"	                , GIFTEXTEND E"
			+"	            WHERE A.GIFT_NO = E.GIFT_NO"
			+"	              AND A.APLONLINEID = 'gsrpoppoint'"
			+"	              AND A.SALESDATE = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"	              AND A.PAY_METHDO IN ('2', 'A2')"
			+"	           ) B"
			+"	        ON A.APPROVAL_DT = B.APPROVAL_DT"
			+"	       AND A.APPROVAL_NO = B.APPROVAL_NO"
			+"         AND A.DEAL_DIVIDER = B.DEAL_DIVIDER"
			+"	     ) A			"
			+"</script>";
	
	
	public static final String SELECT_SETTLE_SQL = 
			"SELECT DECODE( A.CARD_NO , NULL , 'STL-0001' , '') STL1"               
			+"	             ,DECODE( B.CARD_NO , NULL , 'STL-0002' , '') STL2"               
			+"	             ,DECODE( A.DEAL_DIVIDER , B.DEAL_DIVIDER , '' , 'STL-0006') STL6"               
			+"	             ,DECODE( A.CARD_NO , B.CARD_NO , '' , 'STL-0007') STL7"
			+"	             ,DECODE( A.USE_POINT, B.USE_POINT + (B.USE_POINT * 0.05), '' , 'STL-0008') STL8"
			+"	             ,DECODE( A.STORE_CD , B.STORE_CD , '' , 'STL-0009') STL9"
			+"               ,B.DEAL_NO DEAL_NO"
			+"               , NVL(A.APPROVAL_NO , B.APPROVAL_NO) APPROVAL_NO"
			+"               , B.MERCHANTID"
			+"               , NVL(A.WORK_DT , B.DEAL_DT) WORK_DT"
			+"               , A.SEQ"		   
			+"               , B.DEAL_DIVIDER"		   
			+"               , NVL(A.USE_POINT,B.USE_POINT) USE_POINT"	
	        +"               , NVL(B.APPROVAL_DT,A.APPROVAL_DT) APPROVAL_DT"		   
	        +"               , NVL(B.APPROVAL_TIME,'000000') APPROVAL_TIME"					
			+"	         FROM ("
			+"	                SELECT *"
			+"	                  FROM SETTLE_GSNPOINT"
			+"	                 WHERE APPROVAL_DT = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"	              ) A"
			+"	         FULL OUTER JOIN"
			+"	         ("
			+"	           SELECT ROWNUM"
			+"	                 ,A.GIFT_NO"
			+"	                 ,E.EXTEND_NO CARD_NO"
			+"	                 ,'E1010' STORE_CD"
			+"	                 ,TO_CHAR(A.APLDATE, 'YYYYMMDDHH24MISS') DEAL_DT"
			+"	                 ,DECODE(A.APLSTATUS, 'O1', '1', 'D1', '2', '0') DEAL_DIVIDER"
			+"	                 ,A.RC_AMT USE_POINT"
			+"	                 ,TO_CHAR(A.APLDATE, 'YYYYMMDD') APPROVAL_DT"
			+"	                 ,TO_CHAR(A.APLDATE, 'HHMISS') APPROVAL_TIME"
			+"	                 ,NVL(A.C_APLNO, ' ') APPROVAL_NO"
			+"	                 ,A.APLORDERNO DEAL_NO"
			+"	                 ,A.APLONLINEID MERCHANTID"
			+"	             FROM RC_APPROVAL A"
			+"	                , GIFTEXTEND E"
			+"	            WHERE A.GIFT_NO = E.GIFT_NO"
			+"	              AND A.APLONLINEID = 'gsrpoppoint'"
			+"	              AND A.SALESDATE = TO_CHAR(TO_DATE(#{settleDate}) - 1 , 'YYYYMMDD')"
			+"	              AND A.PAY_METHDO IN ('2', 'A2')"
			+"	           ) B"
			+"	        ON A.APPROVAL_DT = B.APPROVAL_DT"
			+"	       AND A.APPROVAL_NO = B.APPROVAL_NO"
			+"         AND A.DEAL_DIVIDER = B.DEAL_DIVIDER";
		
	public static final String INSERT_SETTLE_SQL="<script>"
												+" INSERT ALL"
												+" <foreach collection='gsSettleResult' item='data' index='index' separator='' close=''>"												
												+ " INTO SETTLE_RESULT VALUES"
												+ "	("
												+ " #{data.seq}"
												+ " ,#{data.jobDivider}"
												+ " ,#{data.orderNo, jdbcType=VARCHAR}"
												+ " ,#{data.approvalNo}"
												+ " ,#{data.svcConnId, jdbcType=VARCHAR}"
												+ " ,#{data.dealType}"
												+ " ,#{data.dealDivider, jdbcType=VARCHAR}"
												+ " ,#{data.orderAmt}"
												+ " ,#{data.workDt}"
												+ " ,#{data.settleSeq, jdbcType=VARCHAR}"
												+ " ,#{data.status}"
												+ " ,#{data.statusDesc}"
												+ " ,#{data.createDt}"
												+ " ,#{data.approvalDt}"
												+ " ,#{data.approvalTime}"												
												+ " ) "
										        +"</foreach>"
										        +" SELECT * FROM DUAL"
										        +"</script>";
	
	@Resource(name="approvalDataSource")
	@Insert(INSERT_SQL)
	public int insert(@Param("gsPointDataRecordList") List<GsPointSettlement> gsPointDataRecordList, @Param("startIdx") int startIdx);
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_SQL)
	public int delete(String workDt);

	@Resource(name="approvalDataSource")
	@Select(SELECT_SQL)
	public List<GsPointSettlement> select(String settleDate);
	
	@Resource(name="approvalDataSource")
	@Insert(SETTLE_SQL)
	public int settlement(@Param("jobDivider") String jobDivider,@Param("settleDate") String settleDate);
	
	
	@Resource(name="approvalDataScource")
	@Select(SELECT_SETTLE_SQL)
	public List<GsSettlementSelect> settlementSelect(@Param("jobDivider") String jobDivider, @Param("otisOnline") String otisOnline, @Param("settleDate") String settleDate);
	
	@Resource(name="approvalDataScource")
	@Insert(INSERT_SETTLE_SQL)
	public int settlementInsert(@Param("gsSettleResult") List<GsSettlementResult> gsSettleResult);
	
	
			
}
