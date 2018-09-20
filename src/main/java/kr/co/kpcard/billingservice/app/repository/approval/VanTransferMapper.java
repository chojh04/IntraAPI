package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

public interface VanTransferMapper {
	
	public static final String INSERT_VAN_TRANSFER = "INSERT INTO KPC_APPROVAL.VAN_TRANSFER "
			+ " ("
			+ "		WORK_DATE, TRANSFER_TYPE, TRANSFER_DATE, TRANS_DATE, DEMAND_DATE, DEPOSIT_DATE, RETURN_DATE, "
			+ "		CARD_NO, APPROVAL_NO, PAY_AMOUNT, VAT, SERVICE_CHARGE, PURCHASE, MERCHANT_NO, CARD_RETURN_CD, "
			+ "		VAN_RETURN_CD, VAN_KIND, REG_DATETIME"
			+ "	)"
			+ "VALUES ("
			+ "			#{workDate}, #{transferType}, #{transferDate}, #{transDate}, #{demandDate}, #{depositDate}, #{returnDate, jdbcType=CHAR},"
			+ "			#{cardNumber}, #{approvalNumber}, #{payAmount}, #{vat}, #{serviceCharge}, #{purchase}, #{merchantNumber}, "
			+ "			#{cardReturnCode}, #{vanReturnCode, jdbcType=CHAR}, #{vanKind}, #{regDateTime}"
			+ ")"; 	

	public static final String DELETE_VAN_TRANSFER = "DELETE FROM KPC_APPROVAL.VAN_TRANSFER WHERE TRANS_DATE BETWEEN #{transStartDate} AND #{transEndDate} AND VAN_KIND=#{vanKind}";
	
	public static final String SELECT_VAN_TRANSFER = "SELECT * FROM KPC_APPROVAL.VAN_TRANSFER WHERE TRANS_DATE BETWEEN #{transStartDate} AND #{transEndDate} AND VAN_KIND=#{vanKind}";
	
	@Resource(name="approvalDataSource")
	@Insert(INSERT_VAN_TRANSFER)
	public int insertVanTransferRecord(VanTransfer vanTransfer);	
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_VAN_TRANSFER)
	public  int deleteDailyVanTransferRecord(@Param(value="transStartDate") String transStartDate,
											 @Param(value="transEndDate") String transEndDate, 
											 @Param(value="vanKind") String vanKind);
	
	@Resource(name="approvalDataSource")
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
	public List<VanTransfer> selectVanTransferRecord(@Param(value="transStartDate") String transStartDate,
													 @Param(value="transEndDate") String transEndDate, 
													 @Param(value="vanKind") String vanKind);
}
