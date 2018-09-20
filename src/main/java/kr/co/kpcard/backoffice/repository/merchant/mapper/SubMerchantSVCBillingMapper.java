package kr.co.kpcard.backoffice.repository.merchant.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;

public interface SubMerchantSVCBillingMapper {

	public static final String CREATE_SERVICE_BILLING = "insert into submerchant_service_billing "
            + "        ( "
            + "           service_billing_id "
            + "          ,service_id "
            + "          ,name "
            + "          ,code "
            + "          ,divider "
            + "          ,bank_nm "
            + "          ,bank_acc_no "
            + "          ,bank_holder "
            + "          ,mng_nm "
            + "          ,mng_tel "
            + "          ,mng_email "
            + "          ,kpc_mng_nm "
            + "          ,kpc_mng_tel "
            + "          ,kpc_mng_email "
            + "          ,del_flag "
            + "          ,create_dt "
            + "          ,create_desc "
            + "          ,create_adm_id"
            + "        ) "
            + " VALUES "
            + "        ( "
            + "           #{billing.serviceBillingId} "
            + "          ,#{billing.serviceId} "
            + "          ,#{billing.name} "
            + "          ,#{billing.code} "
            + "          ,#{billing.divider} "
            + "          ,#{billing.bankName} "
            + "          ,#{billing.bankAccountNo} "
            + "          ,#{billing.bankHolder} "
            + "          ,#{billing.managerName} "
            + "          ,#{billing.managerTel} "
            + "          ,#{billing.managerEmail} "
            + "          ,#{billing.kpcManagerName} "
            + "          ,#{billing.kpcManagerTel} "
            + "          ,#{billing.kpcManagerEmail} "
            + "          ,'N' "
            + "          ,SYSDATE "
            + "          ,#{billing.createDesc} "
            + "          ,#{billing.createAdmId}"
            + "        ) ";
	@Insert(CREATE_SERVICE_BILLING)
	public void createServiceBilling(@Param("billing")SubMerchantSVCBilling billing);
	
	public static final String READ_SERVICE_BILLING = "select "
			+ "  service_billing_id serviceBillingId "
			+ " ,service_id serviceId "
			+ " ,name "
			+ " ,code "
			+ " ,type "
			+ " ,divider "
			+ " ,bank_nm bankName "
			+ " ,bank_acc_no bankAccountNo "
			+ " ,vr_bank_nm vrBankName "
			+ " ,vr_bank_acc_no vrBankAccountNo "
			+ " ,bank_holder bankHolder "
			+ " ,share_rate shareRate "
			+ " ,unpaid_amt unpaidAmount "
			+ " ,limits_amt limitsAmount "
			+ " ,credit_setting_amt creditSettingAmount "
			+ " ,mng_nm managerName "
			+ " ,mng_tel managerTel "
			+ " ,mng_email managerEmail "
			+ " ,kpc_mng_nm kpcManagerName "
			+ " ,kpc_mng_tel kpcManagerTel "
			+ " ,kpc_mng_email kpcManagerEmail "
			+ " ,del_flag delFlag "
			+ " ,create_dt createDate "
			+ " ,create_desc createDesc "
			+ " ,create_adm_id createAdmId "
			+ " ,update_dt updateDate "
			+ " ,update_desc updateDesc "
			+ " ,update_adm_id updateAdmId "
			+ " from "
			+ "		submerchant_service_billing "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId}";
	@Select(READ_SERVICE_BILLING)
	public SubMerchantSVCBilling readServiceBilling(String serviceBillingId);
	
	public static final String READ_SERVICE_BILLING_BY_SERVICE_ID = "select "
			+ "  service_billing_id serviceBillingId "
			+ " ,service_id serviceId "
			+ " ,name "
			+ " ,code "
			+ " ,type "
			+ " ,divider "
			+ " ,bank_nm bankName "
			+ " ,bank_acc_no bankAccountNo "
			+ " ,vr_bank_nm vrBankName "
			+ " ,vr_bank_acc_no vrBankAccountNo "
			+ " ,bank_holder bankHolder "
			+ " ,share_rate shareRate "
			+ " ,unpaid_amt unpaidAmount "
			+ " ,limits_amt limitsAmount "
			+ " ,credit_setting_amt creditSettingAmount "
			+ " ,mng_nm managerName "
			+ " ,mng_tel managerTel "
			+ " ,mng_email managerEmail "
			+ " ,kpc_mng_nm kpcManagerName "
			+ " ,kpc_mng_tel kpcManagerTel "
			+ " ,kpc_mng_email kpcManagerEmail "
			+ " ,del_flag delFlag "
			+ " ,create_dt createDate "
			+ " ,create_desc createDesc "
			+ " ,create_adm_id createAdmId "
			+ " ,update_dt updateDate "
			+ " ,update_desc updateDesc "
			+ " ,update_adm_id updateAdmId "
			+ " from "
			+ "		submerchant_service_billing "
			+ " where "
			+ " 	service_id = #{serviceId}";
	@Select(READ_SERVICE_BILLING_BY_SERVICE_ID)
	public SubMerchantSVCBilling readServiceBillingByServiceId(String serviceId);
	
	public static final String EXIST_SUB_MERCHANT_SVC_BILLING = " SELECT "
			+ "	case when count(service_billing_id) > 0 then 1 else 0 end result "
			+ "	from "
			+ "		submerchant_service_billing "
			+ "	where "
			+ "	service_billing_id = #{serviceBillingId} "
			+ "	and del_flag = 'N' ";
	@Select(EXIST_SUB_MERCHANT_SVC_BILLING)
	public boolean existSubMerchantSVCBilling(@Param("serviceBillingId")String serviceBillingId);
	
	public static final String UPDATE_SERVICE_BILLING = "update submerchant_service_billing set"
            + "  name = #{billing.name} " 
            + " ,code = #{billing.code} " 
            + " ,divider = #{billing.divider} " 
            + " ,bank_nm = #{billing.bankName} " 
            + " ,bank_acc_no = #{billing.bankAccountNo} " 
            + " ,bank_holder = #{billing.bankHolder} " 
            + " ,mng_nm = #{billing.managerName} " 
            + "	,mng_tel = #{billing.managerTel} "
            + " ,mng_email = #{billing.managerEmail, jdbcType=VARCHAR} " 
            + " ,kpc_mng_nm = #{billing.kpcManagerName}"
            + " ,kpc_mng_tel = #{billing.kpcManagerTel}"
            + " ,kpc_mng_email = #{billing.kpcManagerEmail}"
            + "	,update_dt = SYSDATE "
            + " where "
            + "		service_billing_id = #{billing.serviceBillingId} ";
	@Update(UPDATE_SERVICE_BILLING)
	public void updateServiceBilling(@Param("billing")SubMerchantSVCBilling billing);
	
	public static final String EXIST_BILLING_BY_SERVICE = " SELECT "
			+ "	case when count(service_billing_id) > 0 then 1 else 0 end as result "
			+ "	FROM "
			+ "		submerchant_service_billing "
			+ "	WHERE "
			+ "	service_id = #{serviceId} "
			+ "	AND del_flag = 'N'";
			
	@Select(EXIST_BILLING_BY_SERVICE)
	public boolean existBillingByService(@Param("serviceId")String serviceId);

	
	
	
}
