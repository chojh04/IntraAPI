package kr.co.kpcard.backoffice.repository.merchant.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;

/**
 * SUBMERCHANT 테이블을 조회하는 맵퍼
 * 추후 여러 데이터들을 조합하여 조회하는 맵퍼를 SubMerchantMapper로 만들기 위해,
 * 일반 거래처 테이블의 기본정보를 조회하는 맵퍼라고 표현하기 위해 Basic이란 단어 사용
 * Created by @author : MinWook on 2018. 7. 3.
 *
 */
@Mapper
public interface SubMerchantBasicMapper {
	
	public static final String CREATE_SUB_MERCHANT = "INSERT INTO KPC_ADMIN.SUBMERCHANT "
            + "        	(submerchant_id "
            + "         ,depth "
            + "         ,parent_id "
            + "         ,name "
            + "         ,alias "
            + "         ,use_flag "
            + "         ,del_flag "
            + "         ,agent_id "
            + "         ,agent_pw "
            + "         ,create_dt "
            + "         ,create_desc "
            + "        	) "
            + " VALUES ( "
            + "         #{subMerchant.subMerchantId} "
            + "         ,(SELECT NVL(MAX(subMerchant.depth),0) + 1 "
            + "             FROM submerchant "
            + "            WHERE parent_id = #{subMerchant.parentId} "
            + "          ) "
            + "         ,#{subMerchant.parentId} "
            + "         ,#{subMerchant.name} "
            + "         ,#{subMerchant.alias} "
            + "         ,#{subMerchant.useFlag} "
            + "         ,'N' "
            + "         ,#{subMerchant.agentId} "
            + "         ,#{subMerchant.agentPw} "
            + "         ,SYSDATE "
            + "         ,'신규등록' "
            + "        ) ";
	@Insert(CREATE_SUB_MERCHANT)
	public void createSubMerchant(@Param("subMerchant")SubMerchant subMerchant);
	
	public static final String READ_SUB_MERCHANT = "SELECT"
			+ " submerchant_id subMerchantId "
			+ " ,erp_code erpCode"
			+ " ,depth "
			+ " ,parent_id parentId "
			+ " ,child_id childId "
			+ " ,name "
			+ " ,name_eng nameEng "
			+ " ,alias "
			+ " ,alias_eng aliasEng "
			+ " ,use_flag useFlag "
			+ " ,reuse_flag reuseFlag "
			+ " ,del_flag delFlag "
			+ " ,pg_id pgId "
			+ " ,pg_pw pgPw"
			+ " ,agent_id agentId "
			+ " ,agent_pw agentPw "
			+ " ,create_dt createDate "
			+ " ,create_desc createDesc "
			+ " ,update_dt updateDate "
			+ " ,update_desc updateDesc "
			+ " from submerchant "
			+ " where submerchant_id = #{subMerchantId}";
	@Select(READ_SUB_MERCHANT)
	public SubMerchant readSubMerchant(@Param(value="subMerchantId")String subMerchantId);
	
	public static final String UPDATE_SUB_MERCHANT = "update submerchant set"
			+ "  erp_code = #{subMerchant.erpCode, jdbcType=VARCHAR} "
			+ " ,depth = #{subMerchant.depth, jdbcType=VARCHAR} "
			+ " ,parent_id = #{subMerchant.parentId, jdbcType=VARCHAR} "
			+ " ,child_id = #{subMerchant.childId, jdbcType=VARCHAR} "
			+ " ,name = #{subMerchant.name, jdbcType=VARCHAR} "
			+ " ,name_eng = #{subMerchant.nameEng, jdbcType=VARCHAR} "
			+ " ,alias = #{subMerchant.alias, jdbcType=VARCHAR} "
			+ " ,alias_eng = #{subMerchant.aliasEng, jdbcType=VARCHAR} "
			+ " ,use_flag = #{subMerchant.useFlag, jdbcType=VARCHAR} "
			+ " ,reuse_flag = #{subMerchant.reUseFlag, jdbcType=VARCHAR} "
			+ " ,pg_id = #{subMerchant.pgId, jdbcType=VARCHAR} "
			+ " ,pg_pw = #{subMerchant.pgPw, jdbcType=VARCHAR} "
			+ " ,agent_id = #{subMerchant.agentId, jdbcType=VARCHAR} "
			+ " ,agent_pw = #{subMerchant.agentPw, jdbcType=VARCHAR} "
			+ " ,update_dt = SYSDATE "
			+ " ,update_desc = #{subMerchant.updateDesc, jdbcType=VARCHAR} "
			+ "	where submerchant_id = #{subMerchant.subMerchantId} ";
	@Update(UPDATE_SUB_MERCHANT)
	public void updateSubMerchant(@Param(value="subMerchant")SubMerchant subMerchantBasic);
	
	public static final String EXIST_SUB_MERCHANT = " SELECT "
			+ "	case when count(submerchant_id) > 0 then 1 else 0 end result "
			+ "	from "
			+ "		submerchant "
			+ "	where "
			+ "	submerchant_id = #{subMerchantId} "
			+ "	and del_flag = 'N' ";
	@Select(EXIST_SUB_MERCHANT)
	public boolean existSubMerchant(@Param("subMerchantId")String subMerchantId);
}
