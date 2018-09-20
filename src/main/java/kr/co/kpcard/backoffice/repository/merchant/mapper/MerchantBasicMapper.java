package kr.co.kpcard.backoffice.repository.merchant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;

/**
 * MERCHANT 테이블을 조회하는 맵퍼
 * 추후 여러 데이터들을 조합하여 조회하는 맵퍼를 MerchantMapper로 만들기 위해,
 * 거래처 테이블의 기본정보를 조회하는 맵퍼라고 표현하기 위해 Basic이란 단어 사용
 * Created by @author : MinWook on 2018. 6. 1.
 *
 */
@Mapper
public interface MerchantBasicMapper {

	public static final String READ_MERCHANT = "SELECT"
			+ 	"	 MERCHANT_ID as merchantId "
			+ 	"	,ERP_CODE as erpCode "
			+ 	"	,NAME "
			+ 	"	,NAME_ENG as nameEng "
			+ 	"	,ALIAS "
			+ 	"	,ALIAS_ENG as aliasEng "
			+ 	"	,USE_FLAG useFlag "
			+ 	"	,DEL_FLAG as delFlag "
			+ 	"	,CREATE_DT as createDate "
			+ 	"	,CREATE_DESC as createDesc "
			+ 	"	,UPDATE_DT as updateDate "
			+ 	"	,UPDATE_DESC as updateDesc "
			+ 	"FROM MERCHANT "
			+   "WHERE MERCHANT_ID = #{merchantId} ";
	@Select(READ_MERCHANT)
	public Merchant readMerchant(@Param(value="merchantId")String merchantId);

	public static final String UPDATE_MERCHANT_BASIC = "UPDATE MERCHANT SET "
			+ "  ERP_CODE = #{merchantBasic.erpCode, jdbcType=VARCHAR} "
			+ " ,NAME = #{merchantBasic.name, jdbcType=VARCHAR} "
			+ " ,NAME_ENG = #{merchantBasic.nameEng, jdbcType=VARCHAR} "
			+ " ,ALIAS = #{merchantBasic.alias, jdbcType=VARCHAR} "
			+ " ,ALIAS_ENG = #{merchantBasic.aliasEng, jdbcType=VARCHAR} "
			+ " ,USE_FLAG = #{merchantBasic.useFlag, jdbcType=VARCHAR} "
			+ " ,UPDATE_DT = SYSDATE "
			+ " ,UPDATE_DESC = #{merchantBasic.updateDesc, jdbcType=VARCHAR} "
			+ "	WHERE MERCHANT_ID = #{merchantBasic.merchantId} ";
	@Update(UPDATE_MERCHANT_BASIC)
	public void updateMerchant(@Param(value="merchantBasic")Merchant merchantBasic);

	public static final String EXIST_MERCHANT = " SELECT "
			+ "	case when count(merchant_id) > 0 then 1 else 0 end result "
			+ "	FROM "
			+ "		MERCHANT "
			+ "	WHERE "
			+ "	merchant_id = #{merchantId} "
			+ "	AND del_flag = 'N' ";
	@Select(EXIST_MERCHANT)
	public boolean existMerchant(@Param("merchantId")String merchantId);
	
}
