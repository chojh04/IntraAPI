package kr.co.kpcard.billingservice.app.repository.legacy;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

public interface HmVanTransferMapper {

	public static final String INSERT_VAN_TRANSFER = "INSERT INTO HAPPY_MISC.VAN_TRANSFER "
			+ " ("
			+ "		WORK_DATE, TRANSFER_TYPE, TRANSFER_DATE, TRANS_DATE, DEMAND_DATE, DEPOSIT_DATE, RETURN_DATE, "
			+ "		CARD_NO, APPROVAL_NO, PAY_AMOUNT, VAT, SERVICE_CHARGE, PURCHASE, MERCHANT_NO, CARD_RETURN_CD, "
			+ "		VAN_RETURN_CD, VAN_KIND, REG_DATETIME"
			+ "	)"
			+ "VALUES ("
			+ "			#{workDate}, #{transferType}, #{transferDate}, #{transDate}, #{demandDate}, #{depositDate}, #{returnDate},"
			+ "			#{cardNumber}, #{approvalNumber}, #{payAmount}, #{vat}, #{serviceCharge}, #{purchase}, #{merchantNumber}, "
			+ "			#{cardReturnCode}, #{vanReturnCode}, #{vanKind}, #{regDateTime}"
			+ ")"; 	

	public static final String DELETE_VAN_TRANSFER = "DELETE FROM HAPPY_MISC.VAN_TRANSFER WHERE TRANSFER_DATE BETWEEN #{transferStartDate} AND #{transferEndDate} AND VAN_KIND=#{vanKind}";
	
	public static final String SELECT_VAN_TRANSFER = "SELECT * FROM HAPPY_MISC.VAN_TRANSFER WHERE TRANSFER_DATE BETWEEN #{transferStartDate} AND #{transferEndDate} AND VAN_KIND=#{vanKind}";
	
	@Resource(name="legacyDataSource")
	@Insert(INSERT_VAN_TRANSFER)
	public int insertHmVanTransferRecord(HmVanTransfer hmVanTransfer);	
	
	@Resource(name="legacyDataSource")
	@Delete(DELETE_VAN_TRANSFER)
	public  int deleteDailyHmVanTransferRecord(@Param(value="transferStartDate") String transferStartDate, 
											   @Param(value="transferEndDate") String transferEndDate,
											   @Param(value="vanKind") String vanKind);
	
	@Resource(name="legacyDataSource")
	@Select(SELECT_VAN_TRANSFER)
	@Results(value={
			@Result(property="workDate", column="WORK_DATE"),
			@Result(property="transferType", column="TRANSFER_TYPE"),
			@Result(property="transferDate", column="TRANSFER_DATE"),
			@Result(property="transDate", column="TRANS_DATE"),
			@Result(property="demandDate", column="DEMAND_DATE"),
			@Result(property="depositDate", column="DEPOSIT_DATE"),
			@Result(property="returnDate", column="RETURN_DATE"),
			@Result(property="cardNumber", column="CARD_NO"),
			@Result(property="approvalNumber", column="APPROVAL_NO"),
			@Result(property="payAmount", column="PAY_AMOUNT"),
			@Result(property="vat", column="VAT"),
			@Result(property="serviceCharge", column="SERVICE_CHARGE"),
			@Result(property="purchase", column="PURCHASE"),
			@Result(property="merchantNumber", column="MERCHANT_NO"),
			@Result(property="cardReturnCode", column="CARD_RETURN_CD"),
			@Result(property="vanReturnCode", column="VAN_RETURN_CD"),
			@Result(property="vanKind", column="VAN_KIND"),
			@Result(property="regDateTime", column="REG_DATETIME")
	})
	public List<HmVanTransfer> selectHmVanTransferRecord(@Param(value="transferStartDate") String transferStartDate, 
														 @Param(value="transferEndDate") String transferEndDate,
														 @Param(value="vanKind") String vanKind);
}
