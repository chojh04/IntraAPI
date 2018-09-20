package kr.co.kpcard.backoffice.repository.billing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.kpcard.backoffice.repository.billing.model.BillingEnrollment;
import kr.co.kpcard.backoffice.repository.billing.model.BillingSpecification;

/**
 * @author chris
 * 정산명세서 Mapper
 */
@Mapper
public interface BillingMapper {
	/**
	 * 정산명세서 Count Query
	 */
	public static final String GET_BILLINGS_BY_COUNT= "<script> "
			+ " SELECT COUNT(1) cnt                            \n"
			+ "   FROM (                                       \n"
            +"        SELECT "
            + "				BA.SUBMERCHANT_ID ,\n"
            + "				BA.SERVICE_ID ,\n"
            + "				BA.SUBMERCHANT_NM submerchantName ,\n"
            + "				BA.SERVICE_NM serviceName ,\n"
            + "				BA.TXN_CD type ,							\n"
            + "				BA.TXN_NM typeName ,						\n"
            + "				BA.APPROVAL_SUM amount ,					\n"
            + "				BA.CANCEL_SUM cancelAmount ,					\n"
            + "				BA.TOT_SUM totalAmount ,					\n"
            + "				BA.TOT_CNT cnt ,							\n"
            + "				BA.COMM_SUM commision ,						\n"
            + "				BA.TAX_SUM tax ,							\n"
            + "				BA.TOT_COMM_SUM commTotal ,					\n"
            + "				BA.TOT_BILLING_AMT billingAmount ,				\n"
            + "				BA.START_DT approvalDtMin ,						\n"
            + "				BA.END_DT approvalDtMax ,						\n"
            + "				BA.BILLING_DT billingDt ,						\n"
            + "				BA.MERCHANT_TAX_TYPE taxTypeName ,				\n"
            + "				BA.MERCHANT_TAX_TYPE_CD merchant_tax_type ,\n"
            + "				BA.MERCHANT_COMM_TYPE commTypeName ,\n"
            + "				BA.MERCHANT_COMM_TYPE_CD merchant_comm_type ,\n"
            + "				BA.MERCHANT_COMMISION ,\n"
            + "				BA.BANK_NM ,									\n"
            + "				BA.BANK_ACC_NO ,							\n"
            + "				BA.BANK_HOLDER ,							\n"
            + "				BA.BILLING_DURATION ,\n"
            + "				BA.BILLING_DURATION_CD billing_duration_type ,\n"
            + "				BA.BILLING_COMM_TYPE ,						\n"
            + "				NVL(BA.DC_AMT_SUM,0) dcAmount,				\n"
            + "				BE.STATUS									\n"
			+ "        FROM  KPC_ADMIN.BILLING_APPROVAL BA, 			\n"
			+ " 			 KPC_ADMIN.BILLING_ENROLLMENT BE			\n"
			+ "       WHERE 1=1                                        \n"
			+ "    	AND BA.SEQ=BE.BILLING_APPR_SEQ(+)   "
	        + "    	AND BA.SERVICE_ID=BE.SERVICE_ID(+)   "
	        + "		AND BA.BILLING_DT=BE.BILLING_DT(+)"
			+ "     <if test='dateType == \"DATE-0001\" '> "
	        + "	        AND END_DT &gt;= #{startDate} and END_DT &lt;= #{endDate} "
	        + "     </if> "
			+ "     <if test='dateType == \"DATE-0002\" '> "
	        + "	        AND BILLING_DT &gt;= #{startDate} and BILLING_DT &lt;= #{endDate} "
	        + "     </if> "
	        + " 	<if test='merchantId!=null and merchantId!=\"\"'>  AND BA.SUBMERCHANT_ID =  #{merchantId, jdbcType=VARCHAR}\n </if> 			   "
	        + " 	<if test='serviceId!=null and serviceId!=\"\"'>  AND BA.SERVICE_ID =  #{serviceId, jdbcType=VARCHAR} \n </if> 			       "
	        + " 	<if test='serviceType!=null and serviceType!=\"\"'>  AND BA.TXN_CD =  #{serviceType, jdbcType=VARCHAR} \n </if> 				   "
	        + "		<if test='billingDuration != \"\" and billingDuration != null'>  AND BA.BILLING_DURATION_CD =  #{billingDuration}\n </if>		"
	        + "		<if test='adjustType == \"Y\"'>  AND BE.STATUS IS NULL							\n </if>		"
	        + "		<if test='adjustType == \"N\"'>  AND BE.STATUS IS NOT NULL						\n </if>		"
			+ "   ) \n"
			+ " </script> ";
	
	/**
	 * 
	 * @param merchantId 거래처 아이디
	 * @param serviceId 서비스 아이디
	 * @param serviceType 거래구분
	 * @param dateType 일자구분(정산기간 : DATE-0001, 정산지급일 : DATE-0002)
	 * @param startDate 조회이랒(From)
	 * @param endDate 조회일자(To)
	 * @param billingDuration 정산주기
	 * @return 레코드 갯수
	 */
	@Select(GET_BILLINGS_BY_COUNT)
	public int getBillingsByCount(@Param(value="merchantId") String merchantId, @Param(value="serviceId") String serviceId, @Param(value="serviceType") String serviceType, 
			@Param(value="dateType") String dateType, @Param(value="startDate") String startDate, @Param(value="endDate") String endDate, 
			@Param(value="billingDuration") String billingDuration, @Param(value="adjustType") String adjustType);

	
	/**
	 * 정산명세서 Query
	 */
	public static final String GET_BILLINGS = "<script> " +
            " SELECT * FROM                                            \n"  
            + "  (SELECT ROWNUM AS RNUM, A.*                           \n"
            + "      FROM (                                            \n"
            +"        SELECT "
            + "				BA.SEQ,"
            + "				BA.SUBMERCHANT_ID submerchantId,			\n"
            + "				BA.SUBMERCHANT_NM submerchantName ,			\n"
            + "				BA.SERVICE_ID serviceId,					\n"
            + "				BA.SERVICE_NM serviceName ,					\n"
            + "				BA.SVC_CONN_ID serviceConnId,				\n"
            + "				BA.BILLING_ID billingId,					\n"
            + "				BA.BILLING_NM billingName ,					\n"
            + "				BA.TXN_CD type ,							\n"
            + "				BA.TXN_NM typeName ,						\n"
            + "				BA.APPROVAL_SUM amount ,					\n"
            + "				BA.CANCEL_SUM cancelAmount ,					\n"
            + "				BA.TOT_SUM totalAmount ,					\n"
            + "				BA.TOT_CNT cnt ,							\n"
            + "				BA.COMM_SUM commision ,						\n"
            + "				BA.TAX_SUM tax ,							\n"
            + "				BA.TOT_COMM_SUM commTotal ,					\n"
            + "				BA.TOT_BILLING_AMT billingAmount ,				\n"
            + "				BA.START_DT approvalDtMin ,						\n"
            + "				BA.END_DT approvalDtMax ,						\n"
            + "				BA.BILLING_DT billingDt ,						\n"
            + "				DECODE(BA.DC_AMT_SUM, null , BA.APPROVAL_SUM, (BA.APPROVAL_SUM-BA.DC_AMT_SUM)) payAmount ,\n"
            + "				BA.MERCHANT_TAX_TYPE taxTypeName ,				\n"
            + "				BA.MERCHANT_TAX_TYPE_CD merchantTaxType 		,\n"
            + "				BA.MERCHANT_COMM_TYPE commTypeName 				,\n"
            + "				BA.MERCHANT_COMM_TYPE_CD merchantCommType 			,\n"
            + "				BA.MERCHANT_COMMISION merchantCommision 		,\n"
            + "				BA.BANK_CD bankCd,									\n"
            + "				BA.BANK_NM bankName,									\n"
            + "				BA.BANK_ACC_NO bankAccNo,							\n"
            + "				BA.BANK_HOLDER bankHolder,							\n"
            + "				BA.BILLING_DURATION billingDuration,\n"
            + "				BA.BILLING_DURATION_CD billingDurationType ,\n"
            + "				BA.BILLING_COMM_TYPE billingCommType,						\n"
            + "				BA.BILLING_COMM_TYPE billingCommTypeCd,						\n"
            + "				NVL(BA.DC_AMT_SUM,0) dcAmount,				\n"
            + "				BA.APPROVAL_CNT approvalCnt,				\n"
            + "				BA.CANCEL_CNT cancelCnt,				\n"
            + "				DECODE(BE.STATUS, null, 'Y', 'N') status	 \n"
			+ "        FROM  KPC_ADMIN.BILLING_APPROVAL BA, 			\n"
			+ " 				KPC_ADMIN.BILLING_ENROLLMENT BE			\n"
			+ "       WHERE 1=1                                        \n"
			+ "    	AND BA.SEQ=BE.BILLING_APPR_SEQ(+)   "
	        + "    	AND BA.SERVICE_ID=BE.SERVICE_ID(+)   "
	        + "		AND BA.BILLING_DT=BE.BILLING_DT(+)"
			+ "     <if test='dateType == \"DATE-0001\" '> "
	        + "	        AND END_DT &gt;= #{startDate} and END_DT &lt;= #{endDate} "
	        + "     </if> "
			+ "     <if test='dateType == \"DATE-0002\" '> "
	        + "	        AND BILLING_DT &gt;= #{startDate} and BILLING_DT &lt;= #{endDate} "
	        + "     </if> "
	        + " 	<if test='merchantId!=null and merchantId!=\"\"'>  AND BA.SUBMERCHANT_ID =  #{merchantId, jdbcType=VARCHAR}\n </if> 			   "
	        + " 	<if test='serviceId!=null and serviceId!=\"\"'>  AND BA.SERVICE_ID =  #{serviceId, jdbcType=VARCHAR} \n </if> 			       "
	        + " 	<if test='serviceType!=null and serviceType!=\"\"'>  AND BA.TXN_CD =  #{serviceType, jdbcType=VARCHAR} \n </if> 				   "
	        + "		<if test='billingDuration != \"\" and billingDuration != null'>  AND BA.BILLING_DURATION_CD =  #{billingDuration}\n </if>		"
	        + "		<if test='adjustType == \"Y\"'>  AND BE.STATUS IS NULL							\n </if>		"
	        + "		<if test='adjustType == \"N\"'>  AND BE.STATUS IS NOT NULL						\n </if>		"
	        + "		ORDER BY SEQ DESC"
	        + "      ) A  \n"
	        + "		<if test=\"excelAllFlag!=1\">"
	        + "			WHERE ROWNUM &lt;= #{limit} + #{offset} )  \n"
	        + "		WHERE RNUM &gt; #{offset}  \n "
	        + "		</if>"
	        + "		<if test=\"excelAllFlag==1\"> ) </if>"
	        + " </script> ";
	
	/**
	 * 
	 * @param offset offset
	 * @param limit limit
	 * @param merchantId 거래처 아이디
	 * @param serviceId 서비스 아이디
	 * @param serviceType 거래구분
	 * @param dateType 일자구분(정산기간 : DATE-0001, 정산지급일 : DATE-0002)
	 * @param startDate 조회이랒(From)
	 * @param endDate 조회일자(To)
	 * @param billingDuration 정산주기
	 * @return 정산명세서 리스트
	 */
	@Select(GET_BILLINGS)
	public List<BillingSpecification> getBillings(@Param(value="offset") int offset, @Param(value="limit") int limit, 
			@Param(value="merchantId") String merchantId, @Param(value="serviceId") String serviceId, @Param(value="serviceType") String serviceType, 
			@Param(value="dateType") String dateType, @Param(value="startDate") String startDate, @Param(value="endDate") String endDate, 
			@Param(value="billingDuration") String billingDuration, @Param(value="excelAllFlag") String excelAllFlag, @Param(value="adjustType") String adjustType); 
	
	public static final String GET_BILLING = 			
            "        SELECT BA.SEQ,"
            + "				BA.SUBMERCHANT_ID submerchantId,			\n"
            + "				BA.SUBMERCHANT_NM submerchantName ,			\n"
            + "				BA.SERVICE_ID serviceId,					\n"
            + "				BA.SERVICE_NM serviceName ,					\n"
            + "				BA.SVC_CONN_ID serviceConnId,				\n"
            + "				BA.BILLING_ID billingId,					\n"
            + "				BA.BILLING_NM billingName ,					\n"
            + "				BA.TXN_CD type ,							\n"
            + "				BA.TXN_NM typeName ,						\n"
            + "				BA.APPROVAL_SUM amount ,					\n"
            + "				BA.CANCEL_SUM cancelAmount ,					\n"
            + "				BA.TOT_SUM totalAmount ,					\n"
            + "				BA.TOT_CNT cnt ,							\n"
            + "				BA.COMM_SUM commision ,						\n"
            + "				BA.TAX_SUM tax ,							\n"
            + "				BA.TOT_COMM_SUM commTotal ,					\n"
            + "				BA.TOT_BILLING_AMT billingAmount ,				\n"
            + "				BA.START_DT approvalDtMin ,						\n"
            + "				BA.END_DT approvalDtMax ,						\n"
            + "				BA.BILLING_DT billingDt ,						\n"
            + "				DECODE(BA.DC_AMT_SUM, null , BA.APPROVAL_SUM, (BA.APPROVAL_SUM-BA.DC_AMT_SUM)) payAmount ,\n"
            + "				BA.MERCHANT_TAX_TYPE taxTypeName ,				\n"
            + "				BA.MERCHANT_TAX_TYPE_CD merchantTaxType 		,\n"
            + "				BA.MERCHANT_COMM_TYPE commTypeName 				,\n"
            + "				BA.MERCHANT_COMM_TYPE_CD merchantCommType 			,\n"
            + "				BA.MERCHANT_COMMISION merchantCommision 		,\n"
            + "				BA.BANK_CD bankCd,									\n"
            + "				BA.BANK_NM bankName,									\n"
            + "				BA.BANK_ACC_NO bankAccNo,							\n"
            + "				BA.BANK_HOLDER bankHolder,							\n"
            + "				BA.BILLING_DURATION billingDuration,\n"
            + "				BA.BILLING_DURATION_CD billingDurationType ,\n"
            + "				BA.BILLING_COMM_TYPE billingCommType,						\n"
            + "				BA.BILLING_COMM_TYPE billingCommTypeCd,						\n"
            + "				NVL(BA.DC_AMT_SUM,0) dcAmount,				\n"
            + "				BA.APPROVAL_CNT approvalCnt,				\n"
            + "				BA.CANCEL_CNT cancelCnt,				\n"
            + "				DECODE(BE.STATUS, null, 'Y', 'N') status	 \n"
			+ "        FROM  KPC_ADMIN.BILLING_APPROVAL BA, 			\n"
			+ " 				KPC_ADMIN.BILLING_ENROLLMENT BE			\n"
			+ "       WHERE 1=1                                        \n"
			+ "		AND BA.SEQ = #{seq}"
			+ "    	AND BA.SEQ=BE.BILLING_APPR_SEQ(+)   "
	        + "    	AND BA.SERVICE_ID=BE.SERVICE_ID(+)   "
	        + "		AND BA.BILLING_DT=BE.BILLING_DT(+)	"
	        ;
	@Select(GET_BILLING)
	public BillingSpecification getBiliing(String seq);
	
	
	public static final String GET_REG_BILLINGS_COUNT = "<script>"
													+ " SELECT "
													+ " COUNT(1) AS CNT "
													+ " FROM BILLING_ENROLLMENT "
													+ " WHERE 1=1 "
													+ " <if test='serviceId != \"\" and status != null'> AND SERVICE_ID =  #{serviceId}  </if> "
													+ " <if test='merchantId != \"\" and status != null'> AND SUBMERCHANT_ID =  #{merchantId}  </if> "
													+ " <if test='serviceType != \"\" and status != null'> AND TNX_CD =  #{serviceType}  </if> "
													+ " <if test='billingType != \"\" and status != null'> AND TNX_CD =  #{billingType}  </if> "
													+ " <if test='dateType==\"term\"'> AND START_DT &gt;= #{startDate} AND END_DT &lt;= #{endDate} </if> "
													+ " <if test='dateType==\"date\"'> AND BILLING_DT BETWEEN #{startDate} AND #{endDate} </if> "
											        + " <if test='status != \"\" and status != null '> AND STATUS =  #{status}  </if> "
													+ " </script>";
	@Select(GET_REG_BILLINGS_COUNT)
	public int getRegBillingsCount(@Param(value="serviceId") String serviceId, 
								  @Param(value="merchantId") String merchantId,
								  @Param(value="serviceType") String serviceType,
								  @Param(value="dateType") String dateType,
								  @Param(value="startDate") String startDate,
								  @Param(value="endDate") String endDate,
								  @Param(value="status") String status,
								  @Param(value="billingType") String billingType);
	
	
	
	public static final String GET_REG_BILLINGS = "<script> "
			+ " SELECT * FROM "
			+ " (SELECT ROWNUM AS RNUM, BE.* "
			+ " 	FROM "
			+ "			(SELECT SEQ seq"
			+ "					,E.TXN_CD txnCd"
			+ "					,E.SVC_CONN_ID svcConnId"
			+ "					,E.SUBMERCHANT_ID submerchantId"
			+ "					,S.NAME submerchantName"
			+ "					,E.SERVICE_ID serviceId"
			+ "					,SS.NAME serviceName"
			+ "					,E.BANK_CD bankCd"
			+ "					,E.BANK_NM bankNm"
			+ "					,E.BANK_HOLDER bankHolder"
			+ "					,E.BANK_ACC_NO bankAccNo"
			+ "					,E.START_DT startDt"
			+ "					,E.END_DT endDt"
			+ "					,E.BILLING_DT billingDt"
			+ "					,E.COMM_SUM commSum"
			+ "					,E.TAX_SUM taxSum"
			+ "					,E.BILLING_AMT billingAmt"
			+ "					,E.APPROVAL_SUM approvalSum"
			+ "					,E.CANCEL_SUM cancelSum"
			+ "					,E.CREATE_DT createDt"
			+ "					,E.DC_AMT_SUM dcAmtSum"
			+ "					,E.STATUS status"
			+ "					,E.ETC_AMOUNT etcAmount"
			+ "					,E.ADJUST_AMOUNT adjustAmount"
			+ "					,E.DESC_MEMO descMemo"
			+ "					,E.BILLING_APPR_SEQ billingApprSeq"
			+ "					,E.UPDATE_DT updateDt"
			+ "					,E.DIFFERENCE_AMOUNT differenceAmount"
			+ "					,E.NOT_MATCHED_AMOUNT notMatchedAmount"
			+ "					,E.PAY_AMOUNT	payAmount"
			+ "			FROM BILLING_ENROLLMENT E"
			+ "			WHERE 1=1 "
			+ " 			<if test='serviceId != \"\" and status != null'> AND SERVICE_ID =  #{serviceId}  </if> "
			+ " 			<if test='merchantId != \"\" and status != null'> AND SUBMERCHANT_ID =  #{merchantId}  </if> "
			+ " 			<if test='serviceType != \"\" and status != null'> AND TNX_CD =  #{serviceType}  </if> "
			+ " 			<if test='billingType != \"\" and status != null'> AND TNX_CD =  #{billingType}  </if> "
			+ " 			<if test='dateType==\"term\"'> AND START_DT &gt;= #{startDate} AND END_DT &lt;= #{endDate} </if> "
			+ " 			<if test='dateType==\"date\"'> AND BILLING_DT BETWEEN #{startDate} AND #{endDate} </if> "
	        + " 			<if test='status != \"\" and status != null '> AND STATUS =  #{status}  </if> "
	        + "				ORDER BY E.CREATE_DT DESC"
	        + "			) BE "
	        + "		<if test='excelAllFlag != \"1\" '> WHERE ROWNUM &lt;= #{limit} + #{offset} </if>"
	        + "	) "
	        + "	<if test='excelAllFlag != \"1\" '> WHERE RNUM &gt; #{offset} </if>"
			+ " </script>";
	@Select(GET_REG_BILLINGS)
	public List<BillingEnrollment> getRegBillings(@Param(value="offset") int offset,
													@Param(value="limit") int limit, 
													@Param(value="serviceId") String serviceId, 
													@Param(value="merchantId") String merchantId,
													@Param(value="serviceType") String serviceType,
													@Param(value="dateType") String dateType,
													@Param(value="startDate") String startDate,
													@Param(value="endDate") String endDate,
													@Param(value="status") String status,
													@Param(value="billingType") String billingType,
													@Param(value="excelAllFlag") String excelAllFlag); 
	
	
	public static final String GET_REG_BILLING = 
												 "				SELECT SEQ seq"
												+ "					,E.TXN_CD txnCd"
												+ "					,E.SVC_CONN_ID svcConnId"
												+ "					,E.SUBMERCHANT_ID submerchantId"
												+ "					,S.NAME submerchantName"
												+ "					,E.SERVICE_ID serviceId"
												+ "					,SS.NAME serviceName"
												+ "					,E.BANK_CD bankCd"
												+ "					,E.BANK_NM bankNm"
												+ "					,E.BANK_HOLDER bankHolder"
												+ "					,E.BANK_ACC_NO bankAccNo"
												+ "					,E.START_DT startDt"
												+ "					,E.END_DT endDt"
												+ "					,E.BILLING_DT billingDt"
												+ "					,E.COMM_SUM commSum"
												+ "					,E.TAX_SUM taxSum"
												+ "					,E.BILLING_AMT billingAmt"
												+ "					,E.APPROVAL_SUM approvalSum"
												+ "					,E.CANCEL_SUM cancelSum"
												+ "					,E.CREATE_DT createDt"
												+ "					,E.DC_AMT_SUM dcAmtSum"
												+ "					,E.STATUS status"
												+ "					,E.ETC_AMOUNT etcAmount"
												+ "					,E.ADJUST_AMOUNT adjustAmount"
												+ "					,E.DESC_MEMO descMemo"
												+ "					,E.BILLING_APPR_SEQ billingApprSeq"
												+ "					,E.UPDATE_DT updateDt"
												+ "					,E.DIFFERENCE_AMOUNT differenceAmount"
												+ "					,E.NOT_MATCHED_AMOUNT notMatchedAmount"
												+ "					,E.PAY_AMOUNT	payAmount"
												+ "			FROM BILLING_ENROLLMENT E"
												+ "				, SUBMERCHANT_SERVICE SS"
												+ "				, SUBMERCHANT S"
												+ "			WHERE 1=1 "
												+ "				AND E.SUBMERCHANT_ID = S.SUBMERCHANT_ID"
												+ "				AND E.SERVICE_ID = SS.SERVICE_ID"
												+ "				AND E.SEQ = #{seq}";		
							
	@Select(GET_REG_BILLING)
	public BillingEnrollment getRegBilling(@Param(value="seq") String seq); 
	
	
	
	public static final String REG_BILLING = "INSERT INTO BILLING_ENROLLMENT"
			+ "  (SEQ, BILLING_APPR_SEQ, STATUS, TXN_CD, TXN_NM, SERVICE_ID, SUBMERCHANT_ID, SUBMERCHANT_NM, BILLING_ID"
			+ "		, BILLING_NM, BANK_CD, BANK_NM, BANK_HOLDER, BANK_ACC_NO, START_DT, END_DT, BILLING_DT, BILLING_DURATION_CD"
			+ "		, BILLING_DURATION, BILLING_COMM_TYPE_CD, BILLING_COMM_TYPE, MERCHANT_TAX_TYPE_CD, MERCHANT_TAX_TYPE"
			+ "		, MERCHANT_COMM_TYPE_CD, MERCHANT_COMM_TYPE, MERCHANT_COMMISION, APPROVAL_CNT, CANCEL_CNT, APPROVAL_AMOUNT"
			+ "		, CANCEL_AMOUNT, NOT_MATCHED_AMOUNT, DIFFERENCE_AMOUNT, ETC_AMOUNT, ADJUST_AMOUNT, TOT_APPROVAL_AMOUNT"
			+ "		, PAY_AMOUNT, DISCOUNT_AMOUNT, COMMISION_AMOUNT, TAX_AMOUNT, TOT_COMMISION_AMOUNT, BILLING_AMOUNT, DESC_MEMO"
			+ "		, CREATE_DT, SERVICE_CONN_ID"
			+ "	 )"
			+ "	 VALUES"
			+ "		(#{be.seq}, #{be.billingApprSeq}, #{be.status}, #{be.txnCd}, #{be.txnName}, #{be.serviceId}, #{be.submerchantId}, #{be.submerchantName}, #{be.billingId}"
			+ "		, #{be.billingName}, #{be.bankCd}, #{be.bankName}, #{be.bankHolder}, #{be.bankAccNo}, #{be.startDt}, #{be.endDt}, #{be.billingDt}, #{be.billingDurationCd}"
			+ "		, #{be.billingDuration}, #{be.billingCommTypeCd}, #{be.billingCommType}, #{be.merchantTaxTypeCd}, #{be.merchantTaxType}"
			+ "		, #{be.merchantCommTypeCd}, #{be.merchantCommType}, #{be.merchantCommision}, #{be.approvalCnt}, #{be.cancelCnt}, #{be.approvalAmount}"
			+ "		, #{be.cancelAmount}, #{be.notMatchedAmount}, #{be.differenceAmount}, #{be.etcAmount}, #{be.adjustAmount}, #{be.totApprovalAmount}"
			+ "		, #{be.payAmount}, #{be.discountAmount}, #{be.commisionAmount}, #{be.taxAmount}, #{be.totCommisionAmount}, #{be.billingAmount}, #{be.descMemo, jdbcType=VARCHAR}"
			+ "		, SYSDATE, #{be.serviceConnId})";
	@Insert(REG_BILLING)
	public int regBilling(@Param(value="be") BillingEnrollment be);
	
	
	public static final String GET_REG_BILLING_SEQ = "SELECT (#{nowDate}||LPAD(NVL((SUBSTR(MAX(seq),-4)+1),1),10,0)) seq FROM BILLING_ENROLLMENT WHERE SEQ LIKE #{nowDate}||'%'";
	@Select(GET_REG_BILLING_SEQ)
	public String getRegBillingSeq(@Param(value="nowDate") String nowDate);	        
}
