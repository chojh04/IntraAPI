package kr.co.kpcard.billingservice.app.repository.settlement.popcharge;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HappyCashSettlementMapper {

	public static final String SELECT_SQL ="<script>"
		+" SELECT A.o_admitdate AS transDate"
		+"      , D.GIFT_NO giftNo"
		+"      , A.o_t_money chargeAmt"
		+"	    , DECODE(A.o_result, 'O1', '0', 'D1', '9') AS aType"
		+"	    , ( SELECT MAX(GIFT_CHECK)"
		+"		     FROM GIFTCREATE"
		+"		    WHERE GIFT_NO = D.GIFT_NO ) giftCheck"
		+"  FROM ORDERLIST A"
		+"     , ORDERLIST_POS B"
		+"     , MOBILE_BARCODE_ORD_LOG C"
		+"     , GIFTEXTEND D"
		+" WHERE A.TRANSID    = B.TRANSID"
		+"   AND A.M_ONO      = C.ORDER_NO"
		+"   AND C.BARCODE_NO = D.EXTEND_NO"
		+"   AND A.m_id       = 'mobilepophm'"
		+"   AND A.o_date     = TO_CHAR(TO_DATE(#{workDt}) -1,'YYYYMMDD')"
	    +" UNION ALL"
		// 2017-06-22일 DB 이관 이전 데이터
		+" SELECT TO_CHAR(CHARGE_DT,'YYYYMMDD') AS transDate"
		+"      , GIFT_NO giftNo"
		+"      , CHARGE_AMT chargeAmt"
		+"      , DECODE(STATUS_CD,'01','0','02','9') AS aType"
		+"      , ("              
		+"        SELECT MAX(GIFT_CHECK)"           
		+"        FROM GIFTCREATE"           
		+"        WHERE GIFT_NO = HCASH_HISTORY.GIFT_NO ) giftCheck"           
		+"  FROM HCASH_HISTORY"             
		+" WHERE CHARGE_METHOD = 'GS'"            
		+"   AND STATUS_CD IN ('01','02')"            
		+"<![CDATA["            
		+"   AND CHARGE_DT >= TRUNC(TO_DATE(#{workDt}) -1)"
		+"   AND CHARGE_DT < TRUNC(TO_DATE(#{workDt}))"			
		+"]]>"            
		+"</script>";

	@Resource(name="approvalDataSource")
	@Select(SELECT_SQL)
	public List<HappyCashSettlement> select(String workDt);	
	
}
