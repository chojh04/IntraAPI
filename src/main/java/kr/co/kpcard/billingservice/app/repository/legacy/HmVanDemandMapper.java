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


public interface HmVanDemandMapper {

	public static final String INSERT_VAN_DEMAND = "INSERT INTO HAPPY_MISC.VAN_DEMAND "
			+ "	("
			+ "		WORK_DATE, DEMAND_TYPE, DEMAND_DATE, TRANS_DATE, CARD_NO, APPROVAL_NO, PAY_AMOUNT, VAT, "
			+ "		SERVICE_CHARGE, PURCHASE, MERCHANT_NO, TERMINAL_NO, KEYIN_SWIPE, VAN_KIND, REG_DATETIME"
			+ "	)"
			+ "VALUES ("
			+ "		#{workDate}, #{demandType}, #{demandDate}, #{transDate}, #{cardNumber}, #{approvalNumber}, #{payAmount}, #{vat}, "
			+ "		#{serviceCharge}, #{purchase}, #{merchantNumber}, #{terminalNumber}, #{keyinSwipe}, #{vanKind}, #{regDateTime}"
			+ "	)"; 	

	public static final String DELETE_VAN_DEMAND = "DELETE FROM HAPPY_MISC.VAN_DEMAND WHERE DEMAND_DATE=#{demandStartDate} AND #{demandEndDate} VAN_KIND=#{vanKind}";

	public static final String SELECT_VAN_DEMAND = "SELECT * FROM HAPPY_MISC.VAN_DEMAND WHERE DEMAND_DATE=#{demandStartDate} AND #{demandEndDate}";
	
	@Resource(name="legacyDataSource")
	@Insert(INSERT_VAN_DEMAND)
	public int insertHmVanDemandRecord(HmVanDemand hmVanDemand);	
	
	@Resource(name="legacyDataSource")
	@Delete(DELETE_VAN_DEMAND)
	public int deleteDailyHmVanDemandRecord(@Param(value="demandStartDate") String demandStartDate, 
											@Param(value="demandEndDate") String demandEndDate,
										    @Param(value="vanKind") String vanKind);
	
	@Resource(name="legacyDataSource")
	@Select(SELECT_VAN_DEMAND)
	@Results(value={
			@Result(property="workDate", column="WORK_DATE"),
			@Result(property="demandType", column="DEMAND_TYPE"),
			@Result(property="demandDate", column="DEMAND_DATE"),
			@Result(property="transDate", column="TRANS_DATE"),
			@Result(property="cardNumber", column="CARD_NO"),
			@Result(property="approvalNumber", column="APPROVAL_NO"),
			@Result(property="payAmount", column="PAY_AMOUNT"),
			@Result(property="vat", column="VAT"),
			@Result(property="serviceCharge", column="SERVICE_CHARGE"),
			@Result(property="purchase", column="PURCHASE"),
			@Result(property="merchantNumber", column="MERCHANT_NO"),
			@Result(property="terminalNumber", column="TERMINAL_NO"),
			@Result(property="keyinSwipe", column="KEYIN_SWIPE"),
			@Result(property="vanKind", column="VAN_KIND"),
			@Result(property="regDateTime", column="REG_DATETIME"),				
		})
	public List<HmVanDemand> selectHmVanDemandRecord(@Param(value="demandStartDate") String demandStartDate, 
													 @Param(value="demandEndDate") String demandEndDate,
													 @Param(value="vanKind") String vanKind);
}
