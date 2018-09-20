package kr.co.kpcard.backoffice.repository.merchant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;

/**
 * MERCHANT_DETAIL 테이블을 조회하는 맵퍼
 * Created by @author : MinWook on 2018. 6. 1.
 *
 */
@Mapper
public interface MerchantDetailMapper {

	public static final String READ_MERCHANT_DETAIL = "SELECT"
			+ 	"	 MERCHANT_ID as merchantId "
			+ 	"	,BIZ_KIND as bizKind "
			+ 	"	,BIZ_COND as bizCond "
			+ 	"	,BIZ_GRP as bizGrp "
			+ 	"	,CORP_REG_NO as corpRegNo "
			+ 	"	,BIZ_REG_NO as bizRegNo "
			+ 	"	,OPEN_DT openDate "
			+ 	"	,CLOSE_DT as closeDate "
			+ 	"	,CLOSED_FLAG as closedFlag "
			+ 	"	,CAPITAL_AMT as capitalAmount "
			+ 	"	,EMP_CNT as employeeCnt "
			+ 	"	,STORE_CNT as storeCnt "
			+ 	"	,AVG_SALE_MONTH as avgSaleMonth "
			+ 	"	,AVG_SALE_YEAR as avgSaleYear "
			+ 	"	,CEO_NM as ceoName "
			+ 	"	,ZIPCODE as zipCode "
			+ 	"	,ADDRESS as address "
			+ 	"	,ADDRESS_DTL as addressDetail "
			+ 	"	,TEL as tel "
			+ 	"	,TEL_SUB as telSub "
			+ 	"	,FAX as fax "
			+ 	"	,FAX_SUB as faxSub "
			+ 	"FROM MERCHANT_DETAIL "
			+   "WHERE MERCHANT_ID = #{merchantId} ";
	@Select(READ_MERCHANT_DETAIL)
	public MerchantDetail readMerchant(@Param(value="merchantId")String merchantId);
	
	public static final String  UPDATE_MERCHANT_DETAIL = "UPDATE MERCHANT_DETAIL SET "
			+ "  BIZ_KIND = #{merchantDetail.bizKind, jdbcType=VARCHAR} "
			+ " ,BIZ_COND = #{merchantDetail.bizCond, jdbcType=VARCHAR} "
			+ " ,BIZ_GRP = #{merchantDetail.bizGrp, jdbcType=VARCHAR} "
			+ " ,CORP_REG_NO = #{merchantDetail.corpRegNo, jdbcType=VARCHAR} "
			+ " ,BIZ_REG_NO = #{merchantDetail.bizRegNo, jdbcType=VARCHAR} "
			+ " ,OPEN_DT = #{merchantDetail.openDate, jdbcType=VARCHAR} "
			+ " ,CLOSE_DT = #{merchantDetail.closeDate, jdbcType=VARCHAR} "
			+ " ,CLOSED_FLAG = #{merchantDetail.closedFlag, jdbcType=VARCHAR} "
			+ " ,CAPITAL_AMT = #{merchantDetail.capitalAmount, jdbcType=VARCHAR} "
			+ " ,EMP_CNT = #{merchantDetail.employeeCnt, jdbcType=VARCHAR} "
			+ " ,STORE_CNT = #{merchantDetail.storeCnt, jdbcType=VARCHAR} "
			+ " ,AVG_SALE_MONTH = #{merchantDetail.avgSaleMonth, jdbcType=VARCHAR} "
			+ " ,AVG_SALE_YEAR = #{merchantDetail.avgSaleYear, jdbcType=VARCHAR} "
			+ " ,CEO_NM = #{merchantDetail.ceoName, jdbcType=VARCHAR} "
			+ " ,ZIPCODE = #{merchantDetail.zipCode, jdbcType=VARCHAR} "
			+ " ,ADDRESS = #{merchantDetail.address, jdbcType=VARCHAR} "
			+ " ,ADDRESS_DTL = #{merchantDetail.addressDetail, jdbcType=VARCHAR} "
			+ " ,TEL = #{merchantDetail.tel, jdbcType=VARCHAR} "
			+ " ,TEL_SUB = #{merchantDetail.telSub, jdbcType=VARCHAR} "
			+ " ,FAX = #{merchantDetail.fax, jdbcType=VARCHAR} "
			+ " ,FAX_SUB = #{merchantDetail.faxSub, jdbcType=VARCHAR} "
			+ "	WHERE MERCHANT_ID = #{merchantId} ";
	@Update(UPDATE_MERCHANT_DETAIL)
	public void updateMerchantDetail(@Param(value="merchantId")String merchantId, @Param(value="merchantDetail")MerchantDetail merchantDetail);
	
}
