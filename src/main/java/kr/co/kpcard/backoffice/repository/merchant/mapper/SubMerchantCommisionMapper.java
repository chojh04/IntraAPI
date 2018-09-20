package kr.co.kpcard.backoffice.repository.merchant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;

public interface SubMerchantCommisionMapper {
	
	public static final String CREATE_SUB_MERCHANT_COMMISION_ID = "SELECT NVL(MAX(commision_id),0) + 1 commision_id FROM submerchant_commision";
	@Select(CREATE_SUB_MERCHANT_COMMISION_ID)
	public Long createCommisionId();
	
	public static final String CREATE_SUB_MERCHANT_COMMISION = "insert into submerchant_commision "
            + "        ( "
            + "           commision_id "
            + "          ,service_billing_id "
            + "          ,billing_start_dt "
            + "          ,billing_comm_type "
            + "          ,billing_dt "
            + "          ,billing_duration "
            + "          ,merchant_comm_type "
            + "          ,merchant_commision "
            + "          ,merchant_tax_type "
            + "          ,merchant_tax "
            + "          ,kpc_comm_type "
            + "          ,kpc_tax_type "
            + "          ,add_comm_type01 "
            + "          ,add_tax_type01 "
            + "          ,add_comm_type02"
            + "          ,add_tax_type02"
            + "			 ,create_dt "
            + "			 ,create_adm_id "
            + "        ) "
            + " VALUES "
            + "        ( "
            + "           #{commision.id} "
            + "          ,#{commision.serviceBillingId} "
            + "          ,#{commision.billingStartDate} "
            + "          ,#{commision.billingCommisionType} "
            + "          ,#{commision.billingDate} "
            + "          ,#{commision.billingDuration} "
            + "          ,#{commision.merchantCommisionType} "
            + "          ,#{commision.merchantCommision} "
            + "          ,#{commision.merchantTaxType} "
            + "          ,#{commision.merchantTax, jdbcType=VARCHAR} "
            + "          ,#{commision.kpcCommisionType, jdbcType=VARCHAR} "
            + "          ,#{commision.kpcTaxType, jdbcType=VARCHAR} "
            + "          ,#{commision.addCommisionType01, jdbcType=VARCHAR} "
            + "          ,#{commision.addTaxType01, jdbcType=VARCHAR} "
            + "          ,#{commision.addCommisionType02, jdbcType=VARCHAR} "
            + "          ,#{commision.addTaxType02, jdbcType=VARCHAR} "
            + "          ,SYSDATE "
            + "          ,#{commision.createAdmId}"
            + "        ) ";
	@Insert(CREATE_SUB_MERCHANT_COMMISION)
	public void createSubMerchantCommision(@Param("commision")SubMerchantCommision commision);
	
	public static final String READ_SUB_MERCHANT_COMMISION_HISTORIES = " select "
			+ " 	 commision_id id "
			+ " 	,service_billing_id serviceBillingId "
			+ " 	,billing_start_dt billingStartDate "
			+ " 	,billing_end_dt billingEndDate "
			+ " 	,billing_comm_type billingCommisionType "
			+ " 	,billing_dt billingDate "
			+ " 	,billing_duration billingDuration "
			+ " 	,merchant_comm_type merchantCommisionType"
			+ " 	,merchant_commision merchantCommision "
			+ " 	,merchant_tax_type merchantTaxType "
			+ " 	,merchant_tax merchantTax "
			+ " 	,kpc_comm_type kpcCommType "
			+ " 	,kpc_commision kpcCommision "
			+ " 	,kpc_tax_type kpcTaxType "
			+ " 	,kpc_tax kpcTax "
			+ " 	,add_comm_type01 addCommisionType01 "
			+ " 	,add_commision01 addCommision01 "
			+ " 	,add_tax_type01 addTaxType01 "
			+ " 	,add_tax01 addTax01 "
			+ " 	,add_comm_type02 addCommisionType02 "
			+ " 	,add_commision02 addCommision02 "
			+ " 	,add_tax_type02 addTaxType02 "
			+ " 	,add_tax02 addTax02 "
			+ " 	,create_dt createDate "
			+ " 	,create_adm_id createAdmId"
			+ " 	,update_dt updateDate "
			+ " 	,update_adm_id updateAdmId"
			+ " from "
			+ "		submerchant_commision "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId} "
			+ " order by commision_id desc ";
	@Select(READ_SUB_MERCHANT_COMMISION_HISTORIES)
	public List<SubMerchantCommision> readSubMerchantCommisionHistories(String serviceBillingId);
	
	
	public static final String READ_SUB_MERCHANT_COMMISION = " select "
			+ " 	 commision_id id "
			+ " 	,service_billing_id serviceBillingId "
			+ " 	,billing_start_dt billingStartDate "
			+ " 	,billing_end_dt billingEndDate "
			+ " 	,billing_comm_type billingCommisionType "
			+ " 	,billing_dt billingDate "
			+ " 	,billing_duration billingDuration "
			+ " 	,merchant_comm_type merchantCommisionType"
			+ " 	,merchant_commision merchantCommision "
			+ " 	,merchant_tax_type merchantTaxType "
			+ " 	,merchant_tax merchantTax "
			+ " 	,kpc_comm_type kpcCommType "
			+ " 	,kpc_commision kpcCommision "
			+ " 	,kpc_tax_type kpcTaxType "
			+ " 	,kpc_tax kpcTax "
			+ " 	,add_comm_type01 addCommisionType01 "
			+ " 	,add_commision01 addCommision01 "
			+ " 	,add_tax_type01 addTaxType01 "
			+ " 	,add_tax01 addTax01 "
			+ " 	,add_comm_type02 addCommisionType02 "
			+ " 	,add_commision02 addCommision02 "
			+ " 	,add_tax_type02 addTaxType02 "
			+ " 	,add_tax02 addTax02 "
			+ " 	,create_dt createDate "
			+ " 	,create_adm_id createAdmId"
			+ " 	,update_dt updateDate "
			+ " 	,update_adm_id updateAdmId"
			+ " from "
			+ "		submerchant_commision "
			+ " where "
			+ " 	commision_id = #{commisionId} ";
	@Select(READ_SUB_MERCHANT_COMMISION)
	public SubMerchantCommision readSubMerchantCommision(Long commisionId);
	
	public static final String READ_SUB_MERCHANT_CURRENT_COMMISION_ID = " select "
			+ " 	 MAX(commision_id) id "
			+ " from "
			+ "		submerchant_commision "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId} "
			+ " 	and billing_end_dt = '21001231' ";
	@Select(READ_SUB_MERCHANT_CURRENT_COMMISION_ID)
	public Long readSubmerchantCurrentCommisionId(String serviceBillingId);
	
	public static final String READ_SUB_MERCHANT_SECOND_CURRENT_COMMISION_ID = " select "
			+ " 	 MAX(commision_id) id "
			+ " from "
			+ "		submerchant_commision "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId} "
			+ " 	and commision_id < (select MAX(commision_id) from submerchant_commision where service_billing_id = #{serviceBillingId}) ";
	@Select(READ_SUB_MERCHANT_SECOND_CURRENT_COMMISION_ID)
	public Long readSubmerchantSecondCurrentCommisionId(String serviceBillingId);
	
	public static final String READ_SUB_MERCHANT_TODAY_COMMISION = " select "
			+ " 	 commision_id id "
			+ " 	,service_billing_id serviceBillingId "
			+ " 	,billing_start_dt billingStartDate "
			+ " 	,billing_end_dt billingEndDate "
			+ " 	,billing_comm_type billingCommisionType "
			+ " 	,billing_dt billingDate "
			+ " 	,billing_duration billingDuration "
			+ " 	,merchant_comm_type merchantCommisionType"
			+ " 	,merchant_commision merchantCommision "
			+ " 	,merchant_tax_type merchantTaxType "
			+ " 	,merchant_tax merchantTax "
			+ " 	,kpc_comm_type kpcCommType "
			+ " 	,kpc_commision kpcCommision "
			+ " 	,kpc_tax_type kpcTaxType "
			+ " 	,kpc_tax kpcTax "
			+ " 	,add_comm_type01 addCommisionType01 "
			+ " 	,add_commision01 addCommision01 "
			+ " 	,add_tax_type01 addTaxType01 "
			+ " 	,add_tax01 addTax01 "
			+ " 	,add_comm_type02 addCommisionType02 "
			+ " 	,add_commision02 addCommision02 "
			+ " 	,add_tax_type02 addTaxType02 "
			+ " 	,add_tax02 addTax02 "
			+ " 	,create_dt createDate "
			+ " 	,create_adm_id createAdmId"
			+ " 	,update_dt updateDate "
			+ " 	,update_adm_id updateAdmId"
			+ " from "
			+ "		submerchant_commision "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId} "
			+ " 	and sysdate BETWEEN TO_DATE(billing_start_dt, 'YYYYMMDD') AND TO_DATE(billing_end_dt, 'YYYYMMDD') ";
	@Select(READ_SUB_MERCHANT_TODAY_COMMISION)
	public SubMerchantCommision readSubmerchantTodayCommision(String serviceBillingId);
	
	public static final String UPDATE_SUB_MERCHANT_COMMISION = " update submerchant_commision set "
			+ "  billing_comm_type = #{commision.billingCommisionType} "
			+ " ,billing_dt = #{commision.billingDate} "
			+ " ,billing_start_dt = #{commision.billingStartDate} "
			+ " ,billing_duration = #{commision.billingDuration} "
			+ " ,merchant_comm_type = #{commision.merchantCommisionType} "
			+ " ,merchant_commision = #{commision.merchantCommision} "
			+ " ,merchant_tax_type = #{commision.merchantTaxType} "
			+ " where "
			+ "		commision_id = #{commision.id}	";
		@Update(UPDATE_SUB_MERCHANT_COMMISION)
		public void updateSubMerchantCommision(@Param("commision")SubMerchantCommision commision);
		
	public static final String UPDATE_BILLING_COMMISION_BILLING_END_DATE = " update submerchant_commision set "
			+ " billing_end_dt = #{billingEndDate} "
			+ " where "
			+ " 	service_billing_id = #{serviceBillingId} "
			+ " 	and commision_id = #{commisionId} ";
	@Update(UPDATE_BILLING_COMMISION_BILLING_END_DATE)
	public void updateBillingCommisionBillingEndDate(
			@Param("serviceBillingId")String serviceBillingId, 
			@Param("commisionId")Long commisionId, 
			@Param("billingEndDate")String billingEndDate);
	
}
