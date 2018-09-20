package kr.co.kpcard.billingservice.app.repository.approval;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface GsRetailPaymentMapper 
{
	/*
	" DELETE FROM  HAPPY_MISC.GSRETAIL_PAYMENT	\n" +
	        "	    WHERE  CUR_DATE = ?					\n" +
	        "	      AND  MERCHANT_ID = ? 				\n";
	*/
	
	public static final String INSERT_PAYMENT_SQL = "INSERT INTO KPC_APPROVAL.GSRETAIL_PAYMENT "
												  + "("
												  + "	MERCHANT_ID, WORK_DATE, BRANCH_CODE, ORDER_NO, ADMIT_NO, "
												  + "	ADMIT_DATE, ADMIT_TIME, BARCODE_NO, ORDER_AMOUNT, PAY_AMOUNT, "
												  + "	PAY_TYPE, CUR_DATE, INSERT_DATE, PAY_OPTION1, PAY_OPTION2, PAY_OPTION3, "
												  + "	PAY_OPTION4, PAY_OPTION5, PAY_OPTION6, PAY_OPTION7"
												  + ") "
												  + "VALUES ( "
												  + "	#{merchantId}, #{workDate}, #{branchCode}, #{orderNumber}, #{admitNumber}, "
												  + "	#{admitDate}, #{admitTime}, #{barcodeNumber}, #{orderAmount}, #{payAmount},"
												  + "	#{payType},#{currentDate},#{insertDate},#{payOption1},#{payOption2},#{payOption3},"
												  + "	#{payOption4},#{payOption5},#{payOption6},#{payOption7}"
												  + ")";
	
	
	public static final String DELETE_PAYMENT_SQL = "DELETE FROM KPC_APPROVAL.GSRETAIL_PAYMENT WHERE CUR_DATE BETWEEN #{currentStartDate} AND #{currentEndDate} AND MERCHANT_ID = #{merchantId}";
	
	public static final String SELECT_PAYMENT_SQL = "SELECT * FROM KPC_APPROVAL.GSRETAIL_PAYMENT WHERE CUR_DATE BETWEEN #{currentStartDate} AND #{currentEndDate} AND MERCHANT_ID = #{merchantId}";
	
	@Resource(name="approvalDataSource")
	@Insert(INSERT_PAYMENT_SQL)	
	public int insertPaymentRecord(GsRetailPayment gsRetailPayment);
	
	@Resource(name="approvalDataSource")
	@Delete(DELETE_PAYMENT_SQL)
	public int deleteDailyPaymentRecord(@Param(value="currentStartDate") String currentStartDate, 
										@Param(value="currentEndDate") String currentEndDate,
										@Param(value="merchantId") String merchantId);
	
	@Resource(name="approvalDataSource")
	@Select(SELECT_PAYMENT_SQL)
	@Results(value={
			@Result(property="merchantId", column="MERCHANT_ID"),
			@Result(property="workDate", column="WORK_DATE"),
			@Result(property="branchCode", column="BRANCH_CODE"),
			@Result(property="orderNumber", column="ORDER_NO"),
			@Result(property="admitNumber", column="ADMIT_NO"),
			@Result(property="admitDate", column="ADMIT_DATE"),
			@Result(property="admitTime", column="ADMIT_TIME"),
			@Result(property="barcodeNumber", column="BARCODE_NO"),
			@Result(property="orderAmount", column="ORDER_AMOUNT"),
			@Result(property="payAmount", column="PAY_AMOUNT"),
			@Result(property="payType", column="PAY_TYPE"),
			@Result(property="currentDate", column="CUR_DATE"),
			@Result(property="insertDate", column="INSERT_DATE"),
			@Result(property="payOption1", column="PAY_OPTION1"),
			@Result(property="payOption2", column="PAY_OPTION2"),
			@Result(property="payOption3", column="PAY_OPTION3"),
			@Result(property="payOption4", column="PAY_OPTION4"),
			@Result(property="payOption5", column="PAY_OPTION5"),
			@Result(property="payOption6", column="PAY_OPTION6"),
			@Result(property="payOption7", column="PAY_OPTION7")
		})
	public List<GsRetailPayment> selectPaymentRecord(@Param(value="currentStartDate") String currentStartDate, 
													 @Param(value="currentEndDate") String currentEndDate,
													 @Param(value="merchantId") String merchantId);
	
	
}
