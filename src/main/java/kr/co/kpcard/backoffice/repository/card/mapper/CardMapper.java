package kr.co.kpcard.backoffice.repository.card.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.controller.approval.protocol.ApprovalPagination;
import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefundList;
import kr.co.kpcard.backoffice.repository.agent.model.AgentBillingCycle;
import kr.co.kpcard.backoffice.repository.agent.model.AgentBillingHistory;
import kr.co.kpcard.backoffice.repository.agent.model.AgentBillings;
import kr.co.kpcard.backoffice.repository.agent.model.AgentLoginInfo;
import kr.co.kpcard.backoffice.repository.agent.model.AgentMerchant;
import kr.co.kpcard.backoffice.repository.agent.model.AgentService;

@Mapper
public interface CardMapper {

	public static final String SELECT_CARD_REFUND_LIST_COUNT = 
			  										"<script>"
			  			+ "SELECT COUNT(*) AS cnt "
			  			+ "		FROM ("
						+ "			SELECT SEQ seq"
						+ "				,APPR_SEQ apprSeq"
						+ "				,CARD_NO cardNumber"
						+ "				,STATUS status"
						+ "				,BALANCE balance"
						+ "				,REFUND_COMMISION refundCommision"
						+ "				,CUST_NM customerName"
						+ "				,CUST_TEL customerTel"
						+ "				,REFUND_BANK_CD BankCd"
						+ "				,REFUND_BANK_ACC_NO bankAccNo"
						+ "				,REFUND_BANK_HOLDER bankHolder"
						+ "				,REFUND_DESC refundDesc"
						+ "				,ORDER_NO orderNo"
						+ "				,APPROVAL_NO approvalNo"
						+ "				,APPROVAL_STATUS approvalStatus"
						+ "				,RECEPTION_DT receptionDt"
						+ "				,APPROVAL_DT approvalDt"
						+ "				,REFUND_DT refundDt"
					    + "			FROM KPC_BALANCE_REFUND "
					    + "			WHERE 1=1"
					    + "			<if test='req.cardNumber!=null and req.cardNumber!=\"\"'> AND CARD_NO = #{req.cardNumber}</if>"
			    		+ "			<if test='req.customerName!=null and req.customerName!=\"\"'> AND CUST_NM = #{req.customerName}</if>"
			    		+ "			<if test='req.procStatus!=null and req.procStatus!=\"\"'> AND STATUS = #{req.procStatus}</if>"
			    		+ "			<if test='req.dateType==\"receptionDt\"'>"
			    		+ "				 AND RECEPTION_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
			    		+ "			</if>"
			    		+ "			<if test='req.dateType==\"approvalDt\"'>"
			    		+ "				 AND APPROVAL_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
			    		+ "			</if>"
			    		+ "			<if test='req.dateType==\"refundDt\"'>"
			    		+ "				 AND REFUND_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
			    		+ "			</if>"
			    		+ "			<if test='req.dateOrderType==\"receptionDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY RECEPTION_DT DESC</if>"
			    		+ "			<if test='req.dateOrderType==\"receptionDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY RECEPTION_DT ASC</if>"
			    		+ "			<if test='req.dateOrderType==\"approvalDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY APPROVAL_DT DESC</if>"
			    		+ "			<if test='req.dateOrderType==\"approvalDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY APPROVAL_DT ASC</if>"
			    		+ "			<if test='req.dateOrderType==\"refundDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY REFUND_DT DESC</if>"
			    		+ "			<if test='req.dateOrderType==\"refundDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY REFUND_DT ASC</if>"
					    + "		)"
						+"</script>";
	
	@Select(SELECT_CARD_REFUND_LIST_COUNT)
	public int getCardRefundListCount(@Param(value="req")RequestCardBalnaceRefundList req);
	
	public static final String SELECT_CARD_REFUND_LIST = 
				"<script>"
+ "SELECT * FROM 																																					"
+ "  (	SELECT ROWNUM AS RNUM, LIST.*"
+ "		FROM ("
+ "			SELECT SEQ seq"
+ "				,APPR_SEQ apprSeq"
+ "				,CARD_NO cardNumber"
+ "				,STATUS status"
+ "				,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=status) statusName"
+ "				,BALANCE balance"
+ "				,REFUND_COMMISION refundCommision"
+ "				,CUST_NM customerName"
+ "				,CUST_TEL customerTel"
+ "				,REFUND_BANK_CD BankCd"
+ "				,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=REFUND_BANK_CD) bankName"
+ "				,REFUND_BANK_ACC_NO bankAccNo"
+ "				,REFUND_BANK_HOLDER bankHolder"
+ "				,REFUND_DESC refundDesc"
+ "				,ORDER_NO orderNo"
+ "				,APPROVAL_NO approvalNo"
+ "				,APPROVAL_STATUS approvalStatus"
+ "				,RECEPTION_DT receptionDt"
+ "				,APPROVAL_DT approvalDt"
+ "				,REFUND_DT refundDt"
+ "			FROM KPC_BALANCE_REFUND "
+ "			WHERE 1=1"
+ "			<if test='req.cardNumber!=null and req.cardNumber!=\"\"'> AND CARD_NO = #{req.cardNumber}</if>"
+ "			<if test='req.customerName!=null and req.customerName!=\"\"'> AND CUST_NM = #{req.customerName}</if>"
+ "			<if test='req.procStatus!=null and req.procStatus!=\"\"'> AND STATUS = #{req.procStatus}</if>"
+ "			<if test='req.dateType==\"receptionDt\"'>"
+ "				 AND RECEPTION_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
+ "			</if>"
+ "			<if test='req.dateType==\"approvalDt\"'>"
+ "				 AND APPROVAL_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
+ "			</if>"
+ "			<if test='req.dateType==\"refundDt\"'>"
+ "				 AND REFUND_DT BETWEEN TO_DATE(#{req.startDate,jdbcType=VARCHAR}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{req.endDate,jdbcType=VARCHAR}||'235959', 'YYYYMMDDHH24MISS')"
+ "			</if>"
+ "			<if test='req.dateOrderType==\"receptionDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY RECEPTION_DT DESC</if>"
+ "			<if test='req.dateOrderType==\"receptionDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY RECEPTION_DT ASC</if>"
+ "			<if test='req.dateOrderType==\"approvalDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY APPROVAL_DT DESC</if>"
+ "			<if test='req.dateOrderType==\"approvalDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY APPROVAL_DT ASC</if>"
+ "			<if test='req.dateOrderType==\"refundDt\" and req.dateOrderDesc==\"DESC\"'> ORDER BY REFUND_DT DESC</if>"
+ "			<if test='req.dateOrderType==\"refundDt\" and req.dateOrderDesc==\"ASC\"'> ORDER BY REFUND_DT ASC</if>"
+ "		) LIST"
+ "		<if test='req.excelAllFlag!=1'>"
+" 			WHERE ROWNUM &lt;= #{req.offset,jdbcType=VARCHAR}+#{req.limit,jdbcType=VARCHAR}"
+ "		</if>"
+ " )"
+ "<if test='req.excelAllFlag!=1'>"
+"	WHERE RNUM &gt; #{req.offset,jdbcType=VARCHAR}"
+ "</if>" 
+"</script>";

@Select(SELECT_CARD_REFUND_LIST)
public List<BalanceRefund> getCardRefundList(@Param(value="req")RequestCardBalnaceRefundList req);


public static final String SELECT_CARD_REFUND = 
	"<script>"
+ "			SELECT BR.SEQ seq"
+ "				,BR.APPR_SEQ apprSeq"
+ "				,BR.CARD_NO cardNumber"
+ "				,BR.STATUS status"
+ "				,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=BR.STATUS) statusName"
+ "				,BR.BALANCE balance"
+ "				,BR.REFUND_COMMISION refundCommision"
+ "				,BR.CUST_NM customerName"
+ "				,BR.CUST_TEL customerTel"
+ "				,BR.REFUND_BANK_CD BankCd"
+ "				,(SELECT NAME FROM KPC_ADMIN_CODE WHERE CODE=BR.REFUND_BANK_CD) bankName"
+ "				,BR.REFUND_BANK_ACC_NO bankAccNo"
+ "				,BR.REFUND_BANK_HOLDER bankHolder"
+ "				,BR.REFUND_DESC refundDesc"
+ "				,APPR.REQ_MEMO apprDesc"
+ "				,BR.ORDER_NO orderNo"
+ "				,BR.APPROVAL_NO approvalNo"
+ "				,BR.APPROVAL_STATUS approvalStatus"
+ "				,BR.RECEPTION_DT receptionDt"
+ "				,BR.APPROVAL_DT approvalDt"
+ "				,BR.REFUND_DT refundDt"
+ "			FROM KPC_BALANCE_REFUND BR,"
+ "				 KPC_APPROVAL APPR "
+ "			WHERE BR.APPR_SEQ=APPR.SEQ"
+ "				AND BR.SEQ=#{seq}"
+"</script>";
@Select(SELECT_CARD_REFUND)
public BalanceRefund getCardRefund(@Param(value="seq") long seq);

}
