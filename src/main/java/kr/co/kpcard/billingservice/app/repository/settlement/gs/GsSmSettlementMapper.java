package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GsSmSettlementMapper {
	
public static final String INSERT_SQL = ""
        + "<script>                    "
        + "INSERT ALL                  "
        + " <foreach collection='gsSmDataRecordList' item='data' index='index' separator='' close=''>"
        + "  INTO SETTLE_GSSM"
        + "       VALUES               "
        + "       (                    "
        + "         #{data.workDt}     "
        //+ "        ,(SELECT (NVL(MAX(TO_NUMBER(SEQ)), 0) + 1) + #{index} FROM SETTLE_GSSM WHERE WORK_DT = #{data.workDt} ) "
        +"		  ,(#{index			  } + 1) + #{startIdx} "	
        + "        ,#{data.divider     }"
        + "        ,#{data.saleDt      }"
        + "        ,#{data.storeCd     }"
        + "        ,#{data.dealNo      }"
        + "        ,#{data.approvalNo  }"
        + "        ,#{data.approvalDt  }"
        + "        ,#{data.approvalTime}"
        + "        ,#{data.cardNo      }"
        + "        ,#{data.dealAmt     }"
        + "        ,#{data.dealType    }"
        + "        ,#{data.dealDivider }"
        + "        ,#{data.creditCorp  }"
        + "        ,#{data.creditAplNo }"
        + "        ,#{data.creditAplDt }"
        + "        ,#{data.orderNo     }"
        + "        ,#{data.vanDivider  }"
        + "        ,#{data.gsPoint     }"
        + "        ,#{data.filler      }"              
        + "       )                    "
        + "</foreach>                  "
        + "SELECT * FROM DUAL          "
        + "</script>";

public static final String DELETE_SQL = ""
		+ "<script>                    "
		+ "DELETE FROM SETTLE_GSSM WHERE WORK_DT = #{workDt} "
		+ "</script>";

	@Resource(name="approvalDataSource")
	@Insert(INSERT_SQL)
	public int insert(@Param("gsSmDataRecordList") List<GsSmSettlement> gsSmDataRecordList, @Param("startIdx") int startIdx);
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_SQL)
	public int delete(String workDt);		
			
}
