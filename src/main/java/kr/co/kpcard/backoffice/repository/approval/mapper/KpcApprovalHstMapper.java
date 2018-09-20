package kr.co.kpcard.backoffice.repository.approval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import kr.co.kpcard.backoffice.repository.approval.model.ApprovalHistory;
import kr.co.kpcard.backoffice.repository.approval.model.CardBalanceRefund;

/**
 * KPC_APPROVAL_HST 테이블
 * 승인 요청 정보 이력 맵퍼
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Mapper
public interface KpcApprovalHstMapper {
	
	public static final String CREATE_KPC_APPROVAL_HST_BY_REQUESTOR = "<script>"
			+ " INSERT INTO KPC_APPROVAL_HST "
			+ " SELECT (SELECT NVL(MAX(SEQ),0) + 1 FROM KPC_APPROVAL_HST) "
			+ " 	, SEQ "
			+ " 	, CONTENT_SEQ "
			+ " 	, WORK_TYPE "
			+ " 	, REQ_TYPE "
			+ " 	, STATUS "
			+ "		, REF_ID "
			+ "		, REF_TITLE "
			+ "		, DESCRIPTION "
			+ "		, KEYWORD "
			+ "		, REQ_EMP_ID "
			+ "		, REQ_DT "
			+ "		, REQ_MEMO "
			+ "		, APPR_EMP_ID "
			+ "		, APPR_DT "
			+ "		, APPR_MEMO "
			+ "		, CREATE_DT "
			+ "		, UPDATE_DT "
			+ "	FROM KPC_APPROVAL "
			+ "	WHERE SEQ = #{apprSeq} "
			+ " <if test='reqEmpId != null '> "
			+ "		AND REQ_EMP_ID = #{reqEmpId}"
	        + " </if> "
        	+ "</script>";
	@Insert(CREATE_KPC_APPROVAL_HST_BY_REQUESTOR)
	public long createKpcApprovalHistoryByRequestor(@Param(value="apprSeq") Long apprSeq, @Param(value="reqEmpId") String reqEmpId);
				
	public static final String CREATE_KPC_APPROVAL_HST_BY_APPROVER = "<script>"
			+ "INSERT INTO KPC_APPROVAL_HST "
			+ "	SELECT (SELECT NVL(MAX(SEQ),0) + 1 FROM KPC_APPROVAL_HST) "
			+ " 	, SEQ "
			+ " 	, CONTENT_SEQ "
			+ " 	, WORK_TYPE "
			+ " 	, REQ_TYPE "
			+ "		, #{status} "
			+ "		, REF_ID "
			+ "		, REF_TITLE "
			+ "		, DESCRIPTION "
			+ "		, KEYWORD "
			+ "		, REQ_EMP_ID "
			+ "		, REQ_DT "
			+ "		, REQ_MEMO "
			+ "		, APPR_EMP_ID "
			+ "		, APPR_DT "
			+ "		, APPR_MEMO "
			+ "		, CREATE_DT "
			+ "		, UPDATE_DT "
			+ "	FROM KPC_APPROVAL "
			+ "	WHERE SEQ = #{apprSeq} "
			+ "		AND APPR_EMP_ID = #{apprEmpId} "
			+ "</script>";
	
	@Insert(CREATE_KPC_APPROVAL_HST_BY_APPROVER)
	public long createKpcApprovalHistoryByApprover(@Param(value="apprSeq")Long apprSeq, @Param(value="apprEmpId")String apprEmpId, @Param("status")String status);
	
	public static final String READ_KPC_APPROVAL_HISTORY_LIST = "<script>"
			+ " SELECT "
			+ " 	 SEQ "
			+ " 	,APPR_SEQ as apprSeq "
			+ " 	,CONTENT_SEQ as contentSeq "
			+ "		,WORK_TYPE as workType "
			+ "		,REQ_TYPE as reqType "
			+ "		,STATUS "
			+ "		,REF_ID as refId "
			+ "		,REF_TITLE as refTitle "
			+ "		,DESCRIPTION "
			+ "		,KEYWORD "
			+ "		,REQ_EMP_ID as reqEmpId "
			+ "		,REQ_DT as reqDate "
			+ "		,REQ_MEMO as reqMemo "
			+ "		,APPR_EMP_ID as apprEmpId "
			+ "		,APPR_DT as apprDate "
			+ "		,APPR_MEMO as apprMemo "
			+ "		,CREATE_DT as createDate "
			+ "		,UPDATE_DT as updateDate "
			+ " FROM "
			+ "		KPC_APPROVAL_HST "
			+ " WHERE "
			+ " 	apprSeq = #{seq} "
			+"</script>";
	@Select(READ_KPC_APPROVAL_HISTORY_LIST)
	public List<ApprovalHistory> readKpcApprovalHistoryList(@Param("apprSeq")Long apprSeq); 
	
	public static final String INSERT_CARD_BALANCE_REFUND = 
												"<script>"
												+ " MERGE INTO KPC_BALANCE_REFUND BR "
												+ "		USING DUAL "
												+ "		ON (BR.APPR_SEQ = #{cardBalanceRefund.apprSeq}) "
												+ "	WHEN MATCHED THEN "
												+ "		UPDATE SET "
												+ "			BR.STATUS=#{cardBalanceRefund.status} "
												+ "		<if test='cardBalanceRefund.cardNumber!=null and cardBalanceRefund.cardNumber!=\"\"'>	,BR.CARD_NO=#{cardBalanceRefund.cardNumber} </if>"
												+ "		<if test='cardBalanceRefund.refundCommision!=null and cardBalanceRefund.refundCommision!=\"\"'>	,BR.REFUND_COMMISION=#{cardBalanceRefund.refundCommision} </if>"
												+ "		<if test='cardBalanceRefund.customerName!=null and cardBalanceRefund.customerName!=\"\"'>	,BR.CUST_NM=#{cardBalanceRefund.customerName} </if>"
												+ "		<if test='cardBalanceRefund.customerTel!=null and cardBalanceRefund.customerTel!=\"\"'>	,BR.CUST_TEL=#{cardBalanceRefund.customerTel} </if>"
												+ "		<if test='cardBalanceRefund.refundBankCode!=null and cardBalanceRefund.refundBankCode!=\"\"'>	,BR.REFUND_BANK_CD=#{cardBalanceRefund.refundBankCode} </if>"
												+ "		<if test='cardBalanceRefund.refundBankkAccountNo!=null and cardBalanceRefund.refundBankkAccountNo!=\"\"'>	,BR.REFUND_BANK_ACC_NO=#{cardBalanceRefund.refundBankkAccountNo} </if>"
												+ "		<if test='cardBalanceRefund.refundBanHolder!=null and cardBalanceRefund.refundBanHolder!=\"\"'>	,BR.REFUND_BANK_HOLDER=#{cardBalanceRefund.refundBanHolder} </if>"
												+ "		<if test='cardBalanceRefund.refundDesc!=null and cardBalanceRefund.refundDesc!=\"\"'>	,BR.REFUND_DESC=#{cardBalanceRefund.refundDesc} </if>"
												+ "		<if test='cardBalanceRefund.receptionDt!=null'> ,BR.RECEPTION_DT=#{cardBalanceRefund.receptionDt} </if>"
												+ "		<if test='cardBalanceRefund.refundDt!=null'> ,BR.REFUND_DT=#{cardBalanceRefund.refundDt} </if>"
												+ "		<if test='cardBalanceRefund.approvalDt!=null'> ,BR.APPROVAL_DT=#{cardBalanceRefund.approvalDt} </if>"
												+ "		<if test='cardBalanceRefund.apprEmpId!=null and cardBalanceRefund.apprEmpId!=\"\"'> ,BR.APPR_ID=#{cardBalanceRefund.apprEmpId} </if>"
												+ "	WHEN NOT MATCHED THEN"
												+ "		INSERT ( SEQ"
												+ "				,APPR_SEQ"
												+ "				,CARD_NO"
												+ "				,STATUS"
												+ "				,BALANCE"
												+ "				,REFUND_COMMISION"
												+ "				,CUST_NM"
												+ "				,CUST_TEL"
												+ "				,REFUND_BANK_CD"
												+ "				,REFUND_BANK_ACC_NO"
												+ "				,REFUND_BANK_HOLDER"
												+ "				,REFUND_DESC"
												+ "				,RECEPTION_DT"
												+ "				,APPROVAL_DT"
												+ "				,REFUND_DT"
												+ "			) VALUES ( #{seq}"
												+ "				,#{cardBalanceRefund.apprSeq}"
												+ "				,#{cardBalanceRefund.cardNumber, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.status}"
												+ "				,#{cardBalanceRefund.balance, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.refundCommision, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.customerName, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.customerTel, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.refundBankCode, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.refundBankkAccountNo, jdbcType=VARCHAR}"
												+ "				,#{cardBalanceRefund.refundBanHolder, jdbcType=VARCHAR}"
												+ "				,''"
												+ "				,#{cardBalanceRefund.receptionDt, jdbcType=DATE}"
												+ "				,#{cardBalanceRefund.approvalDt, jdbcType=DATE}"
												+ "				,#{cardBalanceRefund.refundDt, jdbcType=DATE}"
												+ "			)"
											  + "</script>";
	@Insert(INSERT_CARD_BALANCE_REFUND)
	@SelectKey(statement="SELECT NVL(MAX(SEQ)+1, 1) seq FROM KPC_BALANCE_REFUND", keyProperty="seq", resultType=Integer.class, before=true)
	public long insertCardRefundInfo(@Param("cardBalanceRefund") CardBalanceRefund cardBalanceRefund);
	
}
