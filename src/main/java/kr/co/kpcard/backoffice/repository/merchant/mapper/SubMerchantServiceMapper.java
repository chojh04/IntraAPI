package kr.co.kpcard.backoffice.repository.merchant.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
/**
 * SUBMERCHANT_SERVICE 테이블을 조회하는 맵퍼
 * Created by @author : MinWook on 2018. 7. 2.
 *
 */
@Mapper
public interface SubMerchantServiceMapper {

	public static final String CREATE_SERVICE = "INSERT INTO KPC_ADMIN.SUBMERCHANT_SERVICE "
            + "       ( "
            + "          service_id "          
            + "         ,submerchant_id "      
            + "         ,name "
            + "         ,category "
            + "         ,type "
            + "         ,use_flag "
            + "         ,del_flag "
            + "         ,svc_conn_id "
            + "         ,svc_conn_pw "
            + "         ,sale_divider "
            + "         ,create_dt "
            + "         ,create_desc "
            + "         ,create_adm_id "
            + "       ) "
            + " VALUES "
            + "       ( "
            + "          #{service.serviceId} "      
            + "         ,#{service.subMerchantId} "      
            + "         ,#{service.name} "
            + "         ,#{service.category} "
            + "         ,#{service.type} "
            + "         ,#{service.useFlag} "
            + "         ,'N' "
            + "         ,#{service.svcConnId} "
            + "         ,#{service.svcConnPw} "
            + "         ,#{service.saleDivider} "
            + "         ,SYSDATE"
            + "         ,#{service.createDesc} "
            + "         ,#{service.createAdmId} "
            + "       ) ";
	@Insert(value=CREATE_SERVICE)
	public void createService(@Param(value="service") SubMerchantService service);
	
	public static final String READ_SERVICE = " SELECT "
			+ " 	 service_id serviceId "
			+ " 	,submerchant_id subMerchantId "
			+ " 	,name "
			+ " 	,category "
			+ " 	,type "
			+ " 	,use_flag useflag "
			+ " 	,del_flag delflag "
			+ " 	,svc_auth_type svcAuthType "
			+ " 	,svc_conn_id svcConnId "
			+ " 	,svc_conn_pw svcConnPw "
			+ " 	,sale_category saleCategory "
			+ " 	,sale_divider saleDivider "
			+ " 	,create_dt createDate "
			+ " 	,create_desc createDesc "
			+ " 	,create_adm_id createAdmId "
			+ " 	,update_dt updateDate "
			+ " 	,update_desc updateDesc "
			+ " 	,update_adm_id updateAdmId "
			+ " 	,svc_alias svcAlias "
			+ " 	,frc_flag frcFlag "
			+ " 	,frc_desc frcDesc "
			+ " from "
			+ " 	submerchant_service "
			+ " where  "
			+ " 	service_id = #{serviceId} ";
	@Select(READ_SERVICE)
	public SubMerchantService readService(@Param(value="serviceId") String serviceId);

	public static final String UPDATE_SERVICE = "update submerchant_service set "
			+ "  name = #{service.name} "
			+ " ,category = #{service.category} "
			+ " ,type = #{service.type} "
			+ " ,use_flag = #{service.useFlag} "
			+ " ,svc_auth_type = #{service.svcAuthType, jdbcType=VARCHAR} "
			+ " ,svc_conn_id = #{service.svcConnId} "
			+ " ,svc_conn_pw = #{service.svcConnPw} "
			+ " ,sale_category = #{service.saleCategory, jdbcType=VARCHAR} "
			+ " ,sale_divider = #{service.saleDivider} "
			+ " ,update_dt = SYSDATE "
			+ " ,update_desc = #{service.updateDesc} "
			+ " ,update_adm_id = #{service.updateAdmId} "
			+ " ,svc_alias = #{service.svcAlias, jdbcType=VARCHAR} "
			+ " ,frc_desc = #{service.frcDesc, jdbcType=VARCHAR} "
            + " where "
            + "		service_id = #{service.serviceId} ";
	@Update(UPDATE_SERVICE)
	public void updateService(@Param(value="service") SubMerchantService service);
	
	public static final String EXIST_SUB_MERCHANT_SERVICE = " SELECT "
			+ "	case when count(service_id) > 0 then 1 else 0 end result "
			+ "	from "
			+ "		submerchant_service "
			+ "	where "
			+ "	service_id = #{serviceId} "
			+ "	and del_flag = 'N' ";
	@Select(EXIST_SUB_MERCHANT_SERVICE)
	public boolean existSubMerchantService(@Param("serviceId")String serviceId);
	
}
