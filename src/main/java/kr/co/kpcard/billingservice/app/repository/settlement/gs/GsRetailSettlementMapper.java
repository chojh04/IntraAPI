package kr.co.kpcard.billingservice.app.repository.settlement.gs;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GsRetailSettlementMapper {

	public static final String INSERT_SQL = "<script>"
			                                         + "INSERT ALL                       "
			                                         + " <foreach collection='gsRetailDataRecordList' item='data' index='index' separator='' close=''>"
			                                         + "  INTO SETTLE_GSRETAIL"
	                                                 + "       VALUES                    "
	                                                 + "       (                         "
	                                                 + "         #{data.workDt     }     "
	                                                 //+ "        ,(SELECT (NVL(MAX(TO_NUMBER(SEQ)), 0) + 1) + #{index} FROM SETTLE_POPCHARGE WHERE WORK_DT = #{data.workDt} ) "
	                                                 + "		,(#{index	    	} + 1) + #{startIdx} "	
	                                                 + "        ,#{data.divider     }    "
	                                                 + "        ,#{data.saleDt      }    "
	                                                 + "        ,#{data.storeCd     }    "
	                                                 + "        ,#{data.dealNo      }    "
	                                                 + "        ,#{data.approvalNo  }    "
	                                                 + "        ,#{data.approvalDt  }    "
	                                                 + "        ,#{data.approvalTime}    "
	                                                 + "        ,#{data.cardNo      }    "
	                                                 + "        ,#{data.dealAmt     }    "
	                                                 + "        ,#{data.paymentAmt  }    "
	                                                 + "        ,#{data.balance     }    "
	                                                 + "        ,#{data.dealDivider }    "
	                                                 + "        ,#{data.responseCd  }    "
	                                                 + "        ,SYSDATE                 "    	                                                 
	                                                 + "       )                         "	                                                 
	                                                 + " </foreach>"
	                                                 + "SELECT * FROM DUAL"
	                                                 + "</script> ";
	
	public static final String DELETE_SQL = ""
			+ "<script>                    "
			+ "DELETE FROM SETTLE_GSRETAIL WHERE WORK_DT = #{workDt} "
			+ "</script>";	

	@Resource(name="approvalDataSource")
	@Insert(INSERT_SQL)
	public int insertSettlement(@Param("gsRetailDataRecordList") List<GsRetailSettlement> gsRetailDataRecordList, @Param("startIdx") int startIdx);

	@Resource(name="approvalDataSource")
	@Delete(DELETE_SQL)
	public int delete(String workDt);		
	
}
