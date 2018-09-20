package kr.co.kpcard.billingservice.app.repository.legacy;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface HmVanTransMapper {

	public static final String INSERT_VAN_TRANS = "INSERT INTO HAPPY_MISC.VAN_TRANS "
									+ " ("
									+ "		IDX, WORK_DATE, TRANS_TYPE, TRANS_DATE, TRANS_TIME, ORIG_TRANS_DATE, "
									+ "		CARD_NO, APPROVAL_NO, PAY_AMOUNT, VAT, SERVICE_CHARGE, ISSURE, PURCHASE, MERCHANT_NO, "
									+ "		TERMINAL_NO, KEYIN_SWIPE, VAN_RES_CD, VAN_KIND, REG_DATETIME"
									+ " )"
									+ "VALUES ("
									+ "			#{idx}, #{workDate}, #{transType}, #{transDate}, #{transTime}, #{origTransDate}, "
									+ "			#{cardNumber}, #{approvalNumber}, #{payAmount}, #{vat}, #{serviceCharge}, #{issure}, "
									+ "			#{purchase}, #{merchatNumber}, #{terminalNumber}, #{keyinSwipe}, #{vanResCode}, #{vanKind}, #{regDatetime}"
									+ ")"; 	
	
	public static final String DELETE_VAN_TRANS = "DELETE FROM HAPPY_MISC.VAN_TRANS WHERE TRANS_DATE BETWEEN #{transStartDate} AND #{transEndDate} AND VAN_KIND=#{vanKind}";
	
	public static final String SELECT_VAN_TRANS = "SELECT * FROM HAPPY_MISC.VAN_TRANS WHERE TRANS_DATE BETWEEN #{transStartDate} AND #{transEndDate} AND VAN_KIND=#{vanKind}";
	
	@Resource(name="legacyDataSource")
	@Insert(INSERT_VAN_TRANS)
	public int insertHmVanTransRecord(HmVanTrans hmVanTrans);	
	
	@Resource(name="legacyDataSource")
	@Delete(DELETE_VAN_TRANS)
	public int deleteDailyHmVanTransRecord(@Param(value="transStartDate") String transStartDate, 
										   @Param(value="transEndDate") String transEndDate,
										   @Param(value="vanKind") String vanKind);
	
	@Resource(name="legacyDataSource")
	@Select(SELECT_VAN_TRANS)
	@Results(value={
			@Result(property="idx", column="IDX"),
			@Result(property="workDate", column="WORK_DATE"),
			@Result(property="transType", column="TRANS_TYPE"),
			@Result(property="transDate", column="TRANS_DATE"),
			@Result(property="transTime", column="TRANS_TIME"),
			@Result(property="origTransDate", column="ORIG_TRANS_DATE"),
			@Result(property="cardNumber", column="CARD_NO"),
			@Result(property="approvalNumber", column="APPROVAL_NO"),
			@Result(property="payAmount", column="PAY_AMOUNT"),
			@Result(property="vat", column="VAT"),
			@Result(property="serviceCharge", column="SERVICE_CHARGE"),
			@Result(property="issure", column="ISSURE"),
			@Result(property="purchase", column="PURCHASE"),
			@Result(property="merchatNumber", column="MERCHANT_NO"),
			@Result(property="terminalNumber", column="TERMINAL_NO"),
			@Result(property="keyinSwipe", column="KEYIN_SWIPE"),
			@Result(property="vanResCode", column="VAN_RES_CD"),
			@Result(property="vanKind", column="VAN_KIND"),
			@Result(property="regDatetime", column="REG_DATETIME")
	})
	public List<HmVanTrans> seleteHmVanTransRecord(@Param(value="transStartDate") String transStartDate, 
												   @Param(value="transEndDate") String transEndDate,
												   @Param(value="vanKind") String vanKind);
	
}
