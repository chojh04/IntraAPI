/**
 * 
 */
package kr.co.kpcard.backoffice.repository.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.BalanceSummary;
import kr.co.kpcard.backoffice.component.account.TransactionDetail;
import kr.co.kpcard.backoffice.component.account.TransactionSummary;

/**
 * 잔액조회 Mapper
 * @author chris
 *
 */
@Mapper
public interface BalanceMapper {

	public static final String SELECT_CARD_BALANCE_SUBQUERY = ""
			+ "SELECT DEAL_DT,"
			+ "       CHARGE_CNT, "
			+ "       CHARGE_AMT, "
			+ "       CHARGE_CANCEL_CNT, "
			+ "       CHARGE_CANCEL_AMT, "
			+ "       CHARGE_SUM, "
			+ "       PAY_CNT, "
			+ "       PAY_AMT, "
			+ "       PAY_CANCEL_CNT, "
			+ "       PAY_CANCEL_AMT, "
			+ "       PAY_SUM, "
			+ "       REFUND_CNT, "
			+ "       REFUND_AMT, "
			+ "       REFUND_CANCEL_CNT, "
			+ "       REFUND_CANCEL_AMT, "
			+ "       REFUND_SUM, "
			+ "       PREV_BALANCE, "
			+ "       BALANCE, "
			+ "       FIRST_VALUE(PREV_BALANCE) OVER(ORDER BY DEAL_DT) AS FIRST_BALANCE, "
			+ "       LAST_VALUE(BALANCE) OVER(ORDER BY DEAL_DT ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS LAST_BALANCE "
			+ "  FROM KPC_ADMIN.TXN_APPROVAL_DAY "
			+ " WHERE DEAL_DT BETWEEN #{startDate} AND #{endDate} ";
//			+ " ORDER BY DEAL_DT";

	/**
	 * 잔액 Summary 조회 Query
	 */
	public static final String SELECT_CARD_BALANCE_SUMMARY = "<script>"
			+ "SELECT COUNT(1) AS COUNT, "
			+ "       MIN(FIRST_BALANCE) AS prevBalance, "
			+ "       MAX(LAST_BALANCE) AS balance, "
			+ "       SUM(CHARGE_CNT) AS chargeCount, "
			+ "       SUM(CHARGE_AMT) AS chargeAmount, "
			+ "       SUM(CHARGE_CANCEL_CNT) AS cancelChargeCount, "
			+ "       SUM(CHARGE_CANCEL_AMT) AS cancelChargeAmount, "
			+ "       SUM(CHARGE_SUM) AS chargeSum, "
			+ "       SUM(PAY_CNT) AS payCount, "
			+ "       SUM(PAY_AMT) AS payAmount, "
			+ "       SUM(PAY_CANCEL_CNT) AS cancelPayCount, "
			+ "       SUM(PAY_CANCEL_AMT) AS cancelPayAmount, "
			+ "       SUM(PAY_SUM) AS paySum, "
			+ "       SUM(REFUND_CNT-REFUND_CANCEL_CNT) AS refundCount, "
			+ "       SUM(REFUND_AMT-REFUND_CANCEL_AMT) AS refundAmount "
			+ "  FROM ( "
			+ SELECT_CARD_BALANCE_SUBQUERY
			+ ") "
			+ "</script>";
	
	/**
	 * 카드 잔액 Summary 조회 
	 * @param startDate
	 * @param endDate
	 * @return BalanceSummary 
	 */
	@Select(SELECT_CARD_BALANCE_SUMMARY)
	public BalanceSummary getBalanceSummary(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate);
	
	
	/**
	 * 일자별 잔액 조회 Query
	 */
	public static final String SELECT_DAILY_BALANCE_LIST = "<script>"
			+ "SELECT "
			+ "		  DEAL_DT AS balanceDate,"
			+ "       PREV_BALANCE AS prevBalance, "
			+ "       BALANCE AS balance, "
			+ "       CHARGE_CNT AS chargeCount, "
			+ "       CHARGE_AMT AS chargeAmount, "
			+ "       CHARGE_CANCEL_CNT AS cancelChargeCount, "
			+ "       CHARGE_CANCEL_AMT AS cancelChargeAmount, "
			+ "       CHARGE_SUM AS chargeSum, "
			+ "       PAY_CNT AS payCount, "
			+ "       PAY_AMT AS payAmount, "
			+ "       PAY_CANCEL_CNT AS cancelPayCount, "
			+ "       PAY_CANCEL_AMT AS cancelPayAmount, "
			+ "       PAY_SUM AS paySum, "
			+ "       REFUND_CNT-REFUND_CANCEL_CNT AS refundCount, "
			+ "       REFUND_AMT-REFUND_CANCEL_AMT AS refundAmount "
			+ "  FROM ( "
			+ "       SELECT ROWNUM AS RNUM, X.* "
			+ "         FROM ( "
			+ SELECT_CARD_BALANCE_SUBQUERY	// Sub Query
    		+ "<if test='sort==\"ASC\"'> ORDER BY DEAL_DT ASC</if>"
    		+ "<if test='sort==\"DESC\"'> ORDER BY DEAL_DT DESC</if>"
    		+ "<if test='sort==\"\"'> ORDER BY DEAL_DT ASC</if>"
            + "              ) X "
            + "        WHERE ROWNUM &lt;= #{offset}+#{limit} "
            + "       ) Z "
            + " WHERE RNUM &gt; #{offset} "
			+ "</script>";
	
	/**
	 * 일별 잔액내역 조회
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param offset 페이징 offset
	 * @param limit 페이징 limit
	 * @return list of BalanceDetail
	 */
	@Select(SELECT_DAILY_BALANCE_LIST)
	public List<BalanceDetail> getDailyBalanceList(
			@Param(value="startDate") String startDate, 
			@Param(value="endDate") String endDate,
			@Param(value="sort") String sort,
			@Param(value="offset") long offset,
			@Param(value="limit") long limit
			);
	
	/**
	 * 거래내역 Summary 조회 Query
	 * 
	 * <매핑테이블>
	 * 트랜잭션 유형     구분코드      코드명
	 * 충전     충전승인 CHST-0001    충전
	 *                 CHST-0004    선물하기취소
	 *                 CHST-0005    선물받기
	 *                 CHST-0008    잔액이체취소
	 *                 CHST-0009    잔액대상
	 *         승인취소 CHST-0002    충전취소
	 *                 CHST-0003    선물하기
	 *                 CHST-0006    선물받기취소
	 *                 CHST-0007    잔액이체
	 *                 CHST-0010    잔액대상취소
	 * 결제     결제승인 PAYT-0001    결제
	 *         승인취소 PAYT-0002    결제취소
	 * 환불     환불승인 PAYT-0003    환불
	 *         승인취소 PAYT-0004    환불취소
	 */
	public static final String SELECT_TRANSACTION_SUBQUERY = ""
			+ "SELECT SUBMERCHANT_ID, "
			+ "       SUBMERCHANT_NM, "
			+ "       SERVICE_ID, "
			+ "       SERVICE_NM, "
			+ "       SVC_CONN_ID, "
			+ "       TXN_TYPE,  "
			+ "       SUM(TXN_CNT1) AS TXN_CNT1, "
			+ "       SUM(TXN_AMT1) AS TXN_AMT1, "
			+ "       SUM(TXN_CNT2) AS TXN_CNT2, "
			+ "       SUM(TXN_AMT2) AS TXN_AMT2, "
			+ "       SUM(TXN_AMT1-TXN_AMT2) AS TXN_SUM "
			+ "  FROM ( "
			+ "       SELECT SUBMERCHANT_ID, SUBMERCHANT_NM, SERVICE_ID, SERVICE_NM, SVC_CONN_ID, TXN_TYPE, TXN_STATUS,  "
			+ "              SUM(TXN_CNT) AS TXN_CNT, SUM(TXN_AMT) AS TXN_AMT, "
			+ "              SUM(DECODE(TXN_STATUS, 'CHST-0001', TXN_CNT, 'CHST-0004', TXN_CNT, 'CHST-0005', TXN_CNT, 'CHST-0008', TXN_CNT, 'CHST-0009', TXN_CNT, 'PAYT-0001', TXN_CNT, 'PAYT-0003', TXN_CNT, 0)) TXN_CNT1, " // 승인건수
			+ "              SUM(DECODE(TXN_STATUS, 'CHST-0001', TXN_AMT, 'CHST-0004', TXN_CNT, 'CHST-0005', TXN_CNT, 'CHST-0008', TXN_CNT, 'CHST-0009', TXN_CNT, 'PAYT-0001', TXN_AMT, 'PAYT-0003', TXN_AMT, 0)) TXN_AMT1, " // 승인금액
			+ "              SUM(DECODE(TXN_STATUS, 'CHST-0002', TXN_CNT, 'CHST-0003', TXN_CNT, 'CHST-0006', TXN_CNT, 'CHST-0007', TXN_CNT, 'CHST-0010', TXN_CNT, 'PAYT-0002', TXN_CNT, 0)) TXN_CNT2, " // 취소건수
			+ "              SUM(DECODE(TXN_STATUS, 'CHST-0002', TXN_AMT, 'CHST-0003', TXN_CNT, 'CHST-0006', TXN_CNT, 'CHST-0007', TXN_CNT, 'CHST-0010', TXN_CNT, 'PAYT-0002', TXN_AMT, 0)) TXN_AMT2 " // 취소금액
			+ "         FROM TXN_APPROVAL_SUMM "
			+ "        WHERE 1=1 "
			+ "          AND DEAL_DT BETWEEN #{startDate} AND #{endDate} "
			+ " <if test='transactionType != \"\" '> "
			+ "          AND TXN_TYPE = #{transactionType} "
		    + " </if> "
			+ "        GROUP BY SUBMERCHANT_ID, SUBMERCHANT_NM, SERVICE_ID, SERVICE_NM, SVC_CONN_ID, TXN_TYPE, TXN_STATUS "
			+ "       ) "
			+ " GROUP BY SUBMERCHANT_ID, SUBMERCHANT_NM, SERVICE_ID, SERVICE_NM, SVC_CONN_ID, TXN_TYPE "
			+ " ORDER BY SUBMERCHANT_ID, SERVICE_ID, SVC_CONN_ID";
	
	/**
	 * 거래내역 Summary 조회 Query
	 */
	public static final String SELECT_TRANSACTION_SUMMARY = "<script>"
			+ "SELECT COUNT(1) AS COUNT, "
//			+ "       TXN_TYPE AS transactionType, "
			+ "       SUM(TXN_CNT1) AS transactionCount, "
			+ "       SUM(TXN_AMT1) AS transactionAmount, "
			+ "       SUM(TXN_CNT2) AS cancelTransactionCount, "
			+ "       SUM(TXN_AMT2) AS cancelTransactionAmount, "
			+ "       SUM(TXN_SUM) AS transactionSum "
			+ "  FROM ( "
			+ SELECT_TRANSACTION_SUBQUERY
			+ "       ) "
//			+ " GROUP BY TXN_TYPE "
			+ "</script>";
	
	/**
	 * 거래내역 Summary 조회 
	 * @param startDate
	 * @param endDate
	 * @param transactionType 거래구분(TRNT-0001: 충전, TRNT-0002: 결제, TRNT-0003: 환불)
	 * @return TransactionSummary 
	 */
	@Select(SELECT_TRANSACTION_SUMMARY)
	public TransactionSummary getTransactionSummary(@Param(value="startDate") String startDate, @Param(value="endDate") String endDate, @Param(value="transactionType") String transactionType);
	
	
	/**
	 * 거래내역 합계 리스트 조회 Query
	 */
	public static final String SELECT_TRANSACTION_LIST = "<script>"
			+ "SELECT "
			+ "       SUBMERCHANT_ID AS merchantId, "
			+ "       SUBMERCHANT_NM AS merchantName, "
			+ "       SERVICE_ID AS serviceId, "
			+ "       SERVICE_NM AS serviceName, "
			+ "       SVC_CONN_ID, "
			+ "       TXN_TYPE, "
			+ "       TXN_CNT1 AS transactionCount, "
			+ "       TXN_AMT1 AS transactionAmount, "
			+ "       TXN_CNT2 AS cancelTransactionCount, "
			+ "       TXN_AMT2 AS cancelTransactionAmount, "
			+ "       TXN_SUM AS transactionSum "
			+ "  FROM ( "
			+ "       SELECT ROWNUM AS RNUM, X.* "
			+ "         FROM ( "
			+ SELECT_TRANSACTION_SUBQUERY
            + "              ) X "
            + "        WHERE ROWNUM &lt;= #{offset}+#{limit} "
            + "       ) Z "
            + " WHERE RNUM &gt; #{offset} "
			+ "</script>";
	
	/**
	 * 거래내역 합계 리스트 
	 * @param startDate
	 * @param endDate
	 * @param transactionType 거래구분(TRNT-0001: 충전, TRNT-0002: 결제, TRNT-0003: 환불)
	 * @param offset 페이징 offset
	 * @param limit 페이징 limit
	 * @return list of TransactionDetail
	 */
	@Select(SELECT_TRANSACTION_LIST)
	public List<TransactionDetail> getTransactionList(
			@Param(value="startDate") String startDate, 
			@Param(value="endDate") String endDate,
			@Param(value="transactionType") String transactiontype,
			@Param(value="offset") long offset,
			@Param(value="limit") long limit
			);

}
