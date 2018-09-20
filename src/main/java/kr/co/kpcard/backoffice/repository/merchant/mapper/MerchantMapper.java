package kr.co.kpcard.backoffice.repository.merchant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantBillingsList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantMerchantList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantRepresent;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantRepresentList;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantService;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantServiceList;

@Mapper
public interface MerchantMapper {
	public static final String GET_MERCHANTS = "<script>" +
			" SELECT * FROM " +
            " (SELECT ROWNUM AS RNUM, A.* " +
            " FROM "+
            " (SELECT A.MERCHANT_ID                                         " +
            "      , '대표' gubun                                           " +
            "      , A.NAME                                                " +
            "      , A.CREATE_DT                                           " +
            "      , B.BIZ_KIND                                            " +
            "      , B.BIZ_COND                                            " +
            "      , B.BIZ_GRP                                             " +
            "      , B.CORP_REG_NO                                         " +
            "      , A.ALIAS                                               " +
            "      , DECODE(A.use_flag ,'Y','사용','미사용') use_flag          " +
            "      , TO_CHAR(A.update_dt , 'YYYY-MM-DD') updateDt          " +
            "      , (SELECT count(1)                                      " +
            "          FROM KPC_ADMIN.SUBMERCHANT                          " +
            "         WHERE parent_id = A.merchant_id                      " + 
            "           AND del_flag  = 'N' ) submerchantCnt               " + 
            "  FROM KPC_ADMIN.MERCHANT A                                   " +
            "     , KPC_ADMIN.MERCHANT_DETAIL B                            " +
            " WHERE A.MERCHANT_ID = B.MERCHANT_ID                          " +
            "   AND A.del_flag    = 'N'                                    " +
            " <if test='name != \"\" and name != null'> 	   							   " +
            " 	AND A.NAME LIKE '%' || #{name} || '%'					   " +
            " </if>														   " +
            " <if test='merchantId != \"\" and merchantId != null'> 	   						   " +
            " 	AND A.MERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " +
            " </if>														   " +
            " <if test='alias != \"\" and alias != null'> 	   							   " +
            " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " +
            " </if>														   " +
            " <if test='status != \"\" and status != null'> 	   							   " +
            " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " +
            " </if>                                                        " +
            " <if test='bizRegNo != \"\" and bizRegNo!=null'> 	   						   " +
            " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " +
            " </if>														   " +
            " ORDER BY A.UPDATE_DT DESC ) A 							   " +
            " WHERE ROWNUM &lt;= #{limit} + #{offset} ) 					   " +
            " WHERE RNUM &gt; #{offset}									   " +
			"</script>";
	@Select(GET_MERCHANTS)
	public List<MerchantRepresentList> getRepresentList(@Param(value="limit") int limit, @Param(value="offset") int offset
			, @Param(value="name") String name, @Param(value="merchantId") String merchantId
			, @Param(value="alias") String alias, @Param(value="status") String status
			, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_MERCHANTS_BY_COUNT = "<script> "
			+ " SELECT COUNT(1) AS CNT "
			+ " FROM KPC_ADMIN.MERCHANT A, KPC_ADMIN.MERCHANT_DETAIL B "
			+ " WHERE A.del_flag = 'N' AND A.MERCHANT_ID = B.MERCHANT_ID "
			+ " <if test='name != \"\" and name!=null'> 	   							   " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%'					   " 
            + " </if>														   " 
            + " <if test='merchantId != \"\" and merchantId != null'> 	   						   " 
            + " 	AND A.MERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " 
            + " </if>														   " 
            + " <if test='alias != \"\" and alias != null'> 	   							   " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " 
            + " </if>														   " 
            + " <if test='status != \"\" and status != null'> 	   							   " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " 
            + " </if>                                                        " 
            + " <if test='bizRegNo != \"\" and status != null'> 	   						   " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " 
            + " </if>														   " 
			+ "</script>";
	@Select(GET_MERCHANTS_BY_COUNT)
	public int getMerchantsByCount(@Param(value="name") String name, @Param(value="merchantId") String merchantId
								, @Param(value="alias") String alias, @Param(value="status") String status
								, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_TOTAL_MERCHANTS_BY_COUNT = "<script>"
			+ " SELECT COUNT(1) AS CNT "
			+ " FROM KPC_ADMIN.MERCHANT A, KPC_ADMIN.MERCHANT_DETAIL B "
			+ " WHERE A.del_flag = 'N' AND A.MERCHANT_ID = B.MERCHANT_ID "
			+ " <if test='name != \"\" and name != null'> 	   							   " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%'					   " 
            + " </if>														   " 
            + " <if test='merchantId != \"\" and merchantId != null'> 	   						   " 
            + " 	AND A.MERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " 
            + " </if>														   " 
            + " <if test='alias != \"\" and alias != null'> 	   							   " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " 
            + " </if>														   " 
            + " <if test='status != \"\" and status != null'> 	   							   " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " 
            + " </if>                                                        " 
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> 	   						   " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " 
            + " </if>														   " 
			+ " </script>";
	@Select(GET_TOTAL_MERCHANTS_BY_COUNT)
	public int getTotalMerchantsByCount(@Param(value="name") String name, @Param(value="merchantId") String merchantId
								, @Param(value="alias") String alias, @Param(value="status") String status
								, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_TOTAL_MERCHANTS_BY_SUB_COUNT = "<script>"
			+ " SELECT COUNT(1) AS CNT "
			+ " FROM KPC_ADMIN.SUBMERCHANT A, KPC_ADMIN.SUBMERCHANT_DETAIL B ,KPC_ADMIN.MERCHANT_DETAIL C "
			+ " WHERE A.del_flag = 'N' AND A.SUBMERCHANT_ID = B.SUBMERCHANT_ID AND A.parent_id= C.MERCHANT_ID "
			+ " <if test='name != \"\" and name != null'> 	   							   " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%'					   " 
            + " </if>														   " 
            + " <if test='merchantId != \"\" and merchantId != null'> 	   						   " 
            + " 	AND A.SUBMERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " 
            + " </if>														   " 
            + " <if test='alias != \"\" and alias != null'> 	   							   " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " 
            + " </if>														   " 
            + " <if test='status != \"\" and status != null'> 	   							   " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " 
            + " </if>                                                        " 
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> 	   						   " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " 
            + " </if>														   " 
			+ "</script>";
	@Select(GET_TOTAL_MERCHANTS_BY_SUB_COUNT)
	public int getTotalMerchantsBySubCount(@Param(value="name") String name, @Param(value="merchantId") String merchantId
								, @Param(value="alias") String alias, @Param(value="status") String status
								, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_TOTAL_MERCHANTS = "<script>"
			+ " SELECT * FROM " 
            + " (SELECT ROWNUM AS RNUM, A.*" 
            + " FROM "
            + "     (   " 
            + "  SELECT A.MERCHANT_ID "
            + "       , '대표' gubun "
            + "       , A.NAME "
            + "       , A.CREATE_DT "
            + "       , B.BIZ_KIND "
            + "       , B.BIZ_COND "
            + "       , B.BIZ_GRP "
            + "       , B.CORP_REG_NO "
            + "       , A.ALIAS "
            + "       , DECODE(A.use_flag ,'Y','사용','미사용') use_flag "
            + "       , TO_CHAR(A.update_dt , 'YYYY-MM-DD') updateDt "
            + "       , (SELECT count(1) "
            + "           FROM KPC_ADMIN.SUBMERCHANT                     "
            + "          WHERE parent_id = A.merchant_id                 "
            + "            AND del_flag  = 'N' ) submerchantCnt          "
            + "   FROM KPC_ADMIN.MERCHANT A                              "
            + "      , KPC_ADMIN.MERCHANT_DETAIL B                       "
            + "  WHERE A.MERCHANT_ID = B.MERCHANT_ID                     "
            + "    AND A.del_flag    = 'N'                               "
            + " <if test='name != \"\" and name != null'> 	   							   " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%'					   " 
            + " </if>														   " 
            + " <if test='merchantId != \"\" and merchantId != null'> 	   						   " 
            + " 	AND A.MERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " 
            + " </if>														   " 
            + " <if test='alias != \"\" and alias != null'> 	   							   " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " 
            + " </if>														   " 
            + " <if test='status != \"\" and status != null'> 	   							   " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " 
            + " </if>                                                        " 
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> 	   						   " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " 
            + " </if>														   " 
            + " UNION ALL  "
            + " SELECT A.SUBMERCHANT_ID                                "
            + "      , '일반' gubun                                    "
            + "      , A.NAME                                          "
            + "      , A.CREATE_DT                                     "
            + "      , B.BIZ_KIND                                      "
            + "      , B.BIZ_COND                                      "
            + "      , '' BIZ_GRP                                      "
            + "      , B.CORP_REG_NO                                   "
            + "      , A.ALIAS                                         "
            + "      , DECODE(A.use_flag ,'Y','사용','미사용') use_flag"
            + "      , TO_CHAR(A.update_dt , 'YYYY-MM-DD') updateDt    "
            + "      , (SELECT count(1)                                "
            + "          FROM KPC_ADMIN.SUBMERCHANT                    "
            + "         WHERE parent_id = A.SUBMERCHANT_ID             "
            + "           AND del_flag  = 'N' ) submerchantCnt         "    
            + "   FROM KPC_ADMIN.SUBMERCHANT A                         "
            + "      , KPC_ADMIN.SUBMERCHANT_DETAIL B                  "
            + "      , KPC_ADMIN.MERCHANT_DETAIL C                     "
            + "  WHERE A.SUBMERCHANT_ID = B.SUBMERCHANT_ID             "
            + "    AND A.parent_id      = C.MERCHANT_ID                "
            + "    AND A.del_flag       = 'N'                          "
            + " <if test='name != \"\" and name != null'> 	   							   " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%'					   " 
            + " </if>														   " 
            + " <if test='merchantId != \"\" and merchantId != null'> 	   						   " 
            + " 	AND A.SUBMERCHANT_ID LIKE '%' || #{merchantId} || '%'		   " 
            + " </if>														   " 
            + " <if test='alias != \"\" and alias != null'> 	   							   " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%'					   " 
            + " </if>														   " 
            + " <if test='status != \"\" and status != null'> 	   							   " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%'				   " 
            + " </if>                                                        " 
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> 	   						   " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%'			   " 
            + " </if>	"
            + "ORDER BY updateDt desc													   " 
            + ") A "
            + "WHERE ROWNUM &lt;= #{limit} + #{offset} ) " 
            + "WHERE RNUM &gt; #{offset}	"  
			+ "</script>";
	@Select(GET_TOTAL_MERCHANTS)
	public List<MerchantRepresentList> getTotalMerchants(@Param(value="limit") int limit, @Param(value="offset") int offset
			, @Param(value="name") String name, @Param(value="merchantId") String merchantId
			, @Param(value="alias") String alias, @Param(value="status") String status
			, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_MERCHANT = "<script> "
			+ " SELECT MC.merchant_id                                      "
            + "      , MC.erp_code                                         "
            + "      , MC.name                                             "
            + "      , MC.name_eng                                         "
            + "      , MC.alias                                            "
            + "      , MC.alias_eng                                        "
            + "      , MC.create_dt                                        "
            + "      , MC.create_desc                                      "
            + "      , MC.update_dt                                        "
            + "      , MC.update_desc                                      "
            + "      , MCD.biz_kind                                        "
            + "      , MCD.biz_cond                                        "
            + "      , MCD.biz_grp                                         "
            + "      , FNC_ADMIN_CODE_TO_NAME(MCD.biz_grp) bizGrpName      "
            + "      , MCD.biz_reg_no                                      "
            + "      , MCD.corp_reg_no                                     "
            + "      , MCD.open_dt                                         "
            + "      , MCD.close_dt                                        "
            + "      , MCD.closed_flag                                     "
            + "      , MCD.capital_amt                                     "
            + "      , MCD.emp_cnt                                         "
            + "      , MCD.store_cnt                                       "
            + "      , MCD.avg_sale_month                                  "
            + "      , MCD.avg_sale_year                                   "
            + "      , MCD.ceo_nm                                          "
            + "      , MCD.zipcode                                         "
            + "      , MCD.address                                         "
            + "      , MCD.address_dtl                                     "
            + "      , MCD.tel                                             "
            + "      , MCD.tel_sub                                         "
            + "      , MCD.fax                                             "
            + "      , MCD.fax_sub                                         "
            + "      , DECODE(MC.use_flag ,'Y','사용','미사용') useFlagName"
            + "      , MC.use_flag                                         "
            + "      , (SELECT count(1)                                    "
            + "          FROM KPC_ADMIN.SUBMERCHANT                        "
            + "         WHERE parent_id = MC.merchant_id                   "
            + "           AND del_flag  = 'N') submerchantCnt              "
            + " FROM KPC_ADMIN.MERCHANT MC                                 "
            + "    , KPC_ADMIN.MERCHANT_DETAIL MCD                         "
            + " WHERE MC.MERCHANT_ID = MCD.MERCHANT_ID                     "
            + "   AND MC.del_flag    = 'N'                                 "
            + "   AND MC.MERCHANT_ID = #{merchantId}"
			+ " </script> ";
	@Select(GET_MERCHANT)
	public MerchantRepresent getMerchant(@Param(value="merchantId") String merchantId);
	
	public static final String GET_BZNO_CHECK = " SELECT count(1) cnt "
            + "  FROM KPC_ADMIN.MERCHANT A "
            + "     , KPC_ADMIN.MERCHANT_DETAIL B "
            + " WHERE A.del_flag    = 'N' "
            + "   AND A.merchant_id = B.merchant_id "
            + "   AND B.biz_reg_no  = #{bizRegNo} "
            + "   AND A.del_flag    = 'N' "
            + "   AND (#{merchantId} is null and 1 = 1 or #{merchantId} is not null and A.merchant_id <> NVL(#{merchantId},A.merchant_id)) ";
	@Select(GET_BZNO_CHECK)
	public int getBzNoCheck(@Param(value="bizRegNo") String bizRegNo, @Param(value="merchantId") String merchantId);
	
	public static final String GET_SVC_CONNID_CHECK = " SELECT count(1) cnt "
            + "  FROM KPC_ADMIN.submerchant_service "
            + " WHERE del_flag    = 'N' "
            + "   AND svc_conn_id  = #{svcConnId} "
            + "   AND (#{serviceId} is null and 1 = 1 or #{serviceId} is not null and service_id <> NVL(#{serviceId},service_id))";
	@Select(GET_SVC_CONNID_CHECK)
	public int getSvcConnIdCheck(@Param(value="svcConnId") String svcConnId, @Param(value="serviceId") String serviceId);
	
	public static final String GET_CORPNO_CHECK = " SELECT count(1) cnt "
            + "  FROM KPC_ADMIN.MERCHANT A "
            + "     , KPC_ADMIN.MERCHANT_DETAIL B "
            + " WHERE A.del_flag    = 'N' "
            + "   AND A.merchant_id = B.merchant_id "
            + "   AND B.corp_reg_no  = #{corpRegNo} "
            + "   AND A.del_flag    = 'N' "
            + "   AND (#{merchantId} is null and 1 = 1 or #{merchantId} is not null and A.merchant_id <> NVL(#{merchantId},A.merchant_id)) ";
	@Select(GET_CORPNO_CHECK)
	public int getCorpNoCheck(@Param(value="corpRegNo") String corpRegNo, @Param(value="merchantId") String merchantId);
	
	public static final String DELETE_MERCHANT_BY_BILLING = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING "
			+ " 	SET DEL_FLAG = 'Y' "
			+ "	WHERE SERVICE_ID IN ( "
			+ "		SELECT B.SERVICE_ID "
			+ "		FROM KPC_ADMIN.SUBMERCHANT A "
			+ " 	,KPC_ADMIN.SUBMERCHANT_SERVICE B "
			+ "		WHERE A.parent_id = #{merchantId} "
			+ "		AND A.submerchant_id = B.submerchant_id "
			+ "		)";                         
	@Update(DELETE_MERCHANT_BY_BILLING)
	public int deleteMerchantByBilling(@Param(value="merchantId") String merchantId);                                       
                                   
	public static final String DELETE_MERCHANT_BY_SERVICE = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE "
			+ " 	SET DEL_FLAG = 'Y' "
			+ " WHERE SUBMERCHANT_ID IN ( "
			+ "		SELECT submerchant_id "
			+ "		FROM KPC_ADMIN.SUBMERCHANT "
			+ "		WHERE parent_id = #{merchantId} "
			+ "		) ";                          
	@Update(DELETE_MERCHANT_BY_SERVICE)
	public int deleteMerchantByService(@Param(value="merchantId") String merchantId);  
	
	public static final String DELETE_MERCHANT_BY_SUBMERCHANT = "UPDATE KPC_ADMIN.SUBMERCHANT "
			+ " 	SET DEL_FLAG = 'Y' "
			+ " WHERE parent_id = #{merchantId} ";
	@Update(DELETE_MERCHANT_BY_SUBMERCHANT)
	public int deleteMerchantBySubMerchant(@Param(value="merchantId") String merchantId);  
	
	public static final String DELETE_MERCHANT_BY_MERCHANT = "UPDATE KPC_ADMIN.MERCHANT "
			+ " 	SET DEL_FLAG = 'Y' "
			+ " WHERE MERCHANT_ID = #{merchantId} ";
	@Update(DELETE_MERCHANT_BY_MERCHANT)
	public int deleteMerchantByMerchant(@Param(value="merchantId") String merchantId);
	
	public static final String INSERT_MERCHANT_BY_MERCHANT = "INSERT INTO KPC_ADMIN.MERCHANT "
	        + " 	( merchant_id "
	        + " 	, erp_code "
	        + " 	, name "
	        + " 	, name_eng "
	        + " 	, alias "
	        + " 	, alias_eng "
	        + " 	, use_flag "
	        + " 	, del_flag "
	        + " 	, create_dt "
	        + " 	, create_desc) "
	        + " VALUES (#{merchantId} "
	        + "		, '' "
            + " 	, #{name} "
            + " 	, '' "
            + " 	, #{alias} "
            + " 	, '' "
            + " 	, #{useFlag} "
            + " 	, 'N' "
            + " 	, SYSDATE "
            + " 	, #{createDesc}) ";
	@Insert(INSERT_MERCHANT_BY_MERCHANT)
	public int postRepresentMerchant(@Param(value="merchantId") String merchantId, @Param(value="name") String name, @Param(value="alias") String alias,
			@Param(value="useFlag") String useFlag, @Param(value="createDesc") String createDesc);

	public static final String INSERT_MERCHANT_BY_MERCHANT_DETAIL = "INSERT INTO KPC_ADMIN.MERCHANT_DETAIL "
            + "     ( MERCHANT_ID "
            + "     , BIZ_KIND "
            + "     , BIZ_COND "
            + "     , BIZ_GRP "
            + "     , CORP_REG_NO "
            + "     , BIZ_REG_NO "
            + "     , OPEN_DT "
            + "     , CEO_NM "
            + "     , ZIPCODE "
            + "     , ADDRESS "
            + "     , ADDRESS_DTL "
            + "     , TEL "
            + "     , FAX) "
            + " VALUES (#{merchantId} "
            + " 	, #{bizKind} "
            + "     , #{bizCond} "
            + "     , #{bizGrp} "
            + "     , #{corpRegNo} "
            + "     , #{bizRegNo} "
            + "     , #{openDate} "
            + "     , #{ceoName} "
            + "     , #{zipcode} "
            + "     , #{address} "
            + "     , #{addressDetail} "
            + "     , #{tel} "
            + "     , #{fax} ) ";
	@Insert(INSERT_MERCHANT_BY_MERCHANT_DETAIL)
	public int postRepresentMerchantDetail(@Param(value="merchantId") String merchantId, @Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond,
			@Param(value="bizGrp") String bizGrp, @Param(value="corpRegNo") String corpRegNo, @Param(value="bizRegNo") String bizRegNo,
			@Param(value="openDate") String openDate, @Param(value="ceoName") String ceoName, @Param(value="zipcode") String zipcode,
			@Param(value="address") String address, @Param(value="addressDetail") String addressDetail, @Param(value="tel") String tel,
			@Param(value="fax") String fax);
	
	public static final String CREATE_MERCHANT = "INSERT INTO KPC_ADMIN.MERCHANT "
			+ " 	( merchant_id "
			+ " 	, erp_code "
			+ " 	, name "
			+ " 	, name_eng "
			+ " 	, alias "
			+ " 	, alias_eng "
			+ " 	, use_flag "
			+ " 	, del_flag "
			+ " 	, create_dt "
			+ " 	, create_desc) "
			+ " VALUES (#{merchantId} "
			+ "		, '' "
			+ " 	, #{merchantBasic.name} "
			+ " 	, '' "
			+ " 	, #{merchantBasic.alias} "
			+ " 	, '' "
			+ " 	, #{merchantBasic.useFlag} "
			+ " 	, 'N' "
			+ " 	, SYSDATE "
			+ " 	, #{merchantBasic.createDesc}) ";
	@Insert(CREATE_MERCHANT)
	public int createMerchant(@Param(value="merchantId") String merchantId, @Param(value="merchantBasic") Merchant merchantBasic);	
	
	public static final String CREATE_MERCHANT_DETAIL = "INSERT INTO KPC_ADMIN.MERCHANT_DETAIL "
			+ "     ( MERCHANT_ID "
			+ "     , BIZ_KIND "
			+ "     , BIZ_COND "
			+ "     , BIZ_GRP "
			+ "     , CORP_REG_NO "
			+ "     , BIZ_REG_NO "
			+ "     , OPEN_DT "
			+ "     , CEO_NM "
			+ "     , ZIPCODE "
			+ "     , ADDRESS "
			+ "     , ADDRESS_DTL "
			+ "     , TEL "
			+ "     , FAX) "
			+ " VALUES (#{merchantId} "
			+ " 	, #{merchantDetail.bizKind, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.bizCond, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.bizGrp, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.corpRegNo, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.bizRegNo, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.openDate, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.ceoName, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.zipCode, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.address, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.addressDetail, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.tel, jdbcType=VARCHAR} "
			+ "     , #{merchantDetail.fax, jdbcType=VARCHAR} ) ";
	@Insert(CREATE_MERCHANT_DETAIL)
	public int createMerchantDetail(@Param(value="merchantId") String merchantId, @Param(value="merchantDetail") MerchantDetail merchantDetail);
		
	public static final String UPDATE_MERCHANT_BY_MERCHANT = "<script> "
			+ "	UPDATE KPC_ADMIN.MERCHANT SET "
			+ " 	UPDATE_DT    = SYSDATE "
			+ "		, ALIAS    = #{alias} "
			+ "		, use_flag = #{useFlag} "
			+ " <if test='name != \"\" and name != null'> " 
            + " 	, NAME = #{name} " 
            + " </if> "
            + " <if test='updateDesc != \"\" '> " 
            + " 	, UPDATE_DESC = #{updateDesc} " 
            + " </if> "
            + " WHERE MERCHANT_ID = #{merchantId} "
			+ " </script> ";
	@Update(UPDATE_MERCHANT_BY_MERCHANT)
	public int putRepresentMerchant(@Param(value="merchantId") String merchantId, @Param(value="name") String name, @Param(value="alias") String alias,
			@Param(value="useFlag") String useFlag, @Param(value="updateDesc") String updateDesc);
	
	public static final String UPDATE_MERCHANT_BY_MERCHANT_DETAIL = "<script> "
			+ " UPDATE KPC_ADMIN.MERCHANT_DETAIL SET "
			+ " 	MERCHANT_ID = MERCHANT_ID "
			+ " 	, ADDRESS_DTL = #{addressDetail} "
			+ "		, BIZ_GRP = #{bizGrp} "
			+ "		, FAX = #{fax} "
			+ " <if test='bizKind != \"\" '> " 
            + " 	, BIZ_KIND = #{bizKind} " 
            + " </if> "
            + " <if test='bizCond != \"\" '> " 
            + " 	, BIZ_COND = #{bizCond} " 
            + " </if> "
            + " <if test='corpRegNo != \"\" '> " 
            + " 	, CORP_REG_NO = #{corpRegNo} " 
            + " </if> "
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> " 
            + " 	, biz_reg_no = #{bizRegNo} " 
            + " </if> "
            + " <if test='openDate != \"\" '> " 
            + " 	, OPEN_DT = #{openDate} " 
            + " </if> "
            + " <if test='ceoName != \"\" '> " 
            + " 	, CEO_NM = #{ceoName} " 
            + " </if> "
            + " <if test='zipCode != \"\" '> " 
            + " 	, ZIPCODE = #{zipCode} " 
            + " </if> "
            + " <if test='address != \"\" '> " 
            + " 	, ADDRESS = #{address} " 
            + " </if> "
            + " <if test='tel != \"\" '> " 
            + " 	, TEL = #{tel} " 
            + " </if> "
            + " WHERE MERCHANT_ID = #{merchantId} "
			+ " </script> ";
	@Update(UPDATE_MERCHANT_BY_MERCHANT_DETAIL)
	public int putRepresentMerchantDetail(@Param(value="merchantId") String merchantId, @Param(value="addressDetail") String addressDetail, 
			@Param(value="bizGrp") String bizGrp,@Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond,
			@Param(value="corpRegNo") String corpRegNo, @Param(value="bizRegNo") String bizRegNo, @Param(value="openDate") String openDate,
			@Param(value="ceoName") String ceoName, @Param(value="zipCode") String zipcode, @Param(value="address") String address,  
			@Param(value="tel") String tel, @Param(value="fax") String fax); 
	
	public static final String UPDATE_MERCHANT_BY_MERCHANT_DETAIL_COUNT = "SELECT COUNT(1) CNT "
            + " 	FROM KPC_ADMIN.MERCHANT_DETAIL "
            + " WHERE MERCHANT_ID     = #{merchantId} "
            + "   	AND CEO_NM        = #{ceoName} "
            + "   	AND OPEN_DT       = #{openDate} "
            + "   	AND biz_reg_no    = #{bizRegNo} "
            + "   	AND CORP_REG_NO   = #{corpRegNo} "
            + "   	AND BIZ_KIND      = #{bizKind} "
            + "   	AND BIZ_COND      = #{bizCond} "
            + "   	AND ZIPCODE       = #{zipcode} "
            + "   	AND ADDRESS       = #{address} "
            + "   	AND ADDRESS_DTL   = #{addressDetail} "
            + "   	AND BIZ_GRP       = #{bizGrp} "
            + "   	AND TEL           = #{tel} "
            + "   	AND FAX           = #{fax} ";
	@Select(UPDATE_MERCHANT_BY_MERCHANT_DETAIL_COUNT)
	public int getRepresentMerchantDetailCount(@Param(value="merchantId") String merchantId, @Param(value="ceoName") String ceoName, 
			@Param(value="openDate") String openDate, @Param(value="bizRegNo") String bizRegNo, @Param(value="corpRegNo") String corpRegNo,
			@Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond, @Param(value="zipcode") String zipcode,
			@Param(value="address") String address, @Param(value="addressDetail") String addressDetail, @Param(value="bizGrp") String bizGrp,  
			@Param(value="tel") String tel, @Param(value="fax") String fax);
	
	public static final String UPDATE_MERCHANT_BY_SUBMERCHANT_DETAIL = "UPDATE KPC_ADMIN.SUBMERCHANT_DETAIL "
            + " 	SET  ceo_nm       = #{ceoName} "             
            + "     	,open_dt      = #{openDate} "     
            + "       	,biz_reg_no   = #{bizRegNo} "      
            + "       	,corp_reg_no  = #{corpRegNo} "     
            + "       	,biz_kind     = #{bizKind} "
            + "       	,biz_cond     = #{bizCond} "
            + "       	,zipcode      = #{zipcode} "
            + "       	,address      = #{address} "
            + "       	,address_dtl  = #{addressDetail} "
            + "       	,type         = #{bizGrp} "
            + "       	,tel          = #{tel} "
            + "       	,fax          = #{fax} "
            + " WHERE submerchant_id IN ( SELECT submerchant_id "
            + "                             FROM SUBMERCHANT "
            + "                            WHERE parent_id = #{merchantId}) ";
	@Update(UPDATE_MERCHANT_BY_SUBMERCHANT_DETAIL)
	public int putRepresentMerchantBySubMerchantDetail(@Param(value="merchantId") String merchantId, @Param(value="ceoName") String ceoName, 
			@Param(value="openDate") String openDate, @Param(value="bizRegNo") String bizRegNo, @Param(value="corpRegNo") String corpRegNo,
			@Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond, @Param(value="zipcode") String zipcode,
			@Param(value="address") String address, @Param(value="addressDetail") String addressDetail, @Param(value="bizGrp") String bizGrp,  
			@Param(value="tel") String tel, @Param(value="fax") String fax);
	
	public static final String INSERT_SUBMERCHANT_BY_SUBMERCHANT = "INSERT INTO KPC_ADMIN.SUBMERCHANT "
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
            + "         #{subMerchantId} "
            + "         ,(SELECT NVL(MAX(depth),0) + 1 "
            + "             FROM submerchant "
            + "            WHERE parent_id = #{parentId} "
            + "          ) "
            + "         ,#{parentId} "
            + "         ,#{name} "
            + "         ,#{alias} "
            + "         ,#{useFlag} "
            + "         ,'N' "
            + "         ,#{agentId} "
            + "         ,#{agentPw} "
            + "         ,SYSDATE "
            + "         ,'신규등록' "
            + "        ) ";
	@Insert(INSERT_SUBMERCHANT_BY_SUBMERCHANT)
	public int postSubMerchant(@Param(value="subMerchantId") String subMerchantId, @Param(value="parentId") String parentId,
					@Param(value="name") String name, @Param(value="alias") String alias, @Param(value="useFlag") String useFlag,
					@Param(value="agentId") String agentId, @Param(value="agentPw") String agentPw);
	
	public static final String INSERT_SUBMERCHANT_BY_SUBMERCHANT_DETAIL = "INSERT INTO KPC_ADMIN.SUBMERCHANT_DETAIL "
            + "        ( "
            + "           submerchant_id "
            + "          ,biz_kind "
            + "          ,biz_cond "
            + "          ,corp_reg_no "
            + "          ,biz_reg_no "
            + "          ,ceo_nm "
            + "          ,type "
            + "          ,open_dt "
            + "          ,bank_nm "
            + "          ,bank_acc_no "
            + "          ,bank_holder " 
            + "          ,zipcode "
            + "          ,address "
            + "          ,address_dtl "
            + "          ,tel "
            + "          ,fax "
            + "          ,tax_cust_nm "
            + "          ,tax_email "
            + "          ,tax_tel "
            + "          ,tax_phone "
            + "          ,tax_fax "
            + "          ,sales_nm "
            + "          ,sales_tel "
            + "          ,billing_nm "
            + "          ,billing_tel "
            + "          ,kpc_sales_nm "
            + "          ,kpc_sales_tel "
            + "          ,kpc_billing_nm "
            + "          ,kpc_billing_tel "
            + "          ,url_home "
            + "        ) "
            + " VALUES ( "
            + "           #{subMerchantId} "
            + "          ,#{bizKind} "
            + "          ,#{bizCond} "
            + "          ,#{corpRegNo} "
            + "          ,#{bizRegNo} "
            + "          ,#{ceoName} "
            + "          ,#{bizGrp} "
            + "          ,#{openDate} "
            + "          ,#{bankNm} "
            + "          ,#{bankAccNo} "
            + "          ,#{bankHolder} "
            + "          ,#{zipcode} "
            + "          ,#{address} "
            + "          ,#{addressDetail} "
            + "          ,#{tel} "
            + "          ,#{fax} "
            + "          ,#{taxCustName} "
            + "          ,#{taxEmail} "
            + "          ,#{taxTel} "
            + "          ,#{taxPhone} "
            + "          ,#{taxFax} "
            + "          ,#{salesNm} "
            + "          ,#{salesTel} "
            + "          ,#{billingNm} "
            + "          ,#{billingTel} "
            + "          ,#{kpcSalesNm} "
            + "          ,#{kpcSalesTel} "
            + "          ,#{kpcBillingNm} "
            + "          ,#{kpcBillingTel} "
            + "          ,#{urlHome} "
            + "        ) ";
	@Insert(INSERT_SUBMERCHANT_BY_SUBMERCHANT_DETAIL)
	public int postSubMerchantDetail(@Param(value="subMerchantId") String subMerchantId, @Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond, 
			@Param(value="corpRegNo") String corpRegNo, @Param(value="bizRegNo") String bizRegNo, @Param(value="ceoName") String ceoName,
			@Param(value="bizGrp") String bizGrp, @Param(value="openDate") String openDate, @Param(value="bankNm") String bankNm, 
			@Param(value="bankAccNo") String bankAccNo, @Param(value="bankHolder") String bankHolder, @Param(value="zipcode") String zipcode,
			@Param(value="address") String address, @Param(value="addressDetail") String addressDetail, @Param(value="tel") String tel,
			@Param(value="fax") String fax, @Param(value="taxCustName") String taxCustName, @Param(value="taxEmail") String taxEmail,
			@Param(value="taxTel") String taxTel, @Param(value="taxPhone") String taxPhone, @Param(value="taxFax") String taxFax,
			@Param(value="salesNm") String salesNm, @Param(value="salesTel") String salesTel, @Param(value="billingNm") String billingNm, 
			@Param(value="billingTel") String billingTel, @Param(value="kpcSalesNm") String kpcSalesNm, @Param(value="kpcSalesTel") String kpcSalesTel,
			@Param(value="kpcBillingNm") String kpcBillingNm, @Param(value="kpcBillingTel") String kpcBillingTel, @Param(value="urlHome") String urlHome);
	
	public static final String UPDATE_SUBMERCHANT_BY_SUBMERCHANT = "UPDATE KPC_ADMIN.SUBMERCHANT " 
            + " 	SET name             = #{name} "
            + "     	,alias           = #{alias} "
            + "      	,use_flag        = #{useFlag} "
            + "      	,agent_id        = #{agentId} "
            + "      	,agent_pw        = #{updateAgentPw} "
            + "      	,update_dt       = SYSDATE "
            + "      	,update_desc     = '수정' "
            + " WHERE submerchant_id  = #{subMerchantId}";
	@Update(UPDATE_SUBMERCHANT_BY_SUBMERCHANT)
	public int putSubMerchant(@Param(value="subMerchantId") String subMerchantId, @Param(value="name") String name,
			@Param(value="alias") String alias, @Param(value="useFlag") String useFlag, @Param(value="agentId") String agentId,
			@Param(value="updateAgentPw") String updateAgentPw);
	
	public static final String UPDATE_SUBMERCHANT_BY_SUBMERCHANT_DETAIL = "UPDATE KPC_ADMIN.SUBMERCHANT_DETAIL "
            + "   	SET biz_kind         = #{bizKind} "
            + "      	,biz_cond        = #{bizCond} "
            + "      	,corp_reg_no     = #{corpRegNo} "
            + "      	,biz_reg_no      = #{bizRegNo} "
            + "      	,ceo_nm          = #{ceoName} "
            + "      	,type            = #{bizGrp} "
            + "      	,open_dt         = #{openDate} "
            + "      	,bank_nm         = #{bankNm} "
            + "      	,bank_acc_no     = #{bankAccNo} "
            + "      	,bank_holder     = #{bankHolder} "
            + "      	,zipcode         = #{zipcode} "
            + "      	,address         = #{address} "
            + "      	,address_dtl     = #{addressDetail} "
            + "      	,tel             = #{tel} "
            + "      	,fax             = #{fax} "
            + "      	,tax_cust_nm     = #{taxCustName} "
            + "      	,tax_email       = #{taxEmail} "
            + "      	,tax_tel         = #{taxTel} "
            + "      	,tax_phone       = #{taxPhone} "
            + "     	,tax_fax         = #{taxFax} "
            + "      	,sales_nm        = #{salesNm} "
            + "      	,sales_tel       = #{salesTel} "
            + "      	,billing_nm      = #{billingNm} "
            + "      	,billing_tel     = #{billingTel} "
            + "      	,kpc_sales_nm    = #{kpcSalesNm} "
            + "      	,kpc_sales_tel   = #{kpcSalesTel} "
            + "      	,kpc_billing_nm  = #{kpcBillingNm} "
            + "      	,kpc_billing_tel = #{kpcBillingTel} "
            + "     	,url_home        = #{urlHome} "
            + " WHERE submerchant_id  	 = #{subMerchantId} ";
	@Update(UPDATE_SUBMERCHANT_BY_SUBMERCHANT_DETAIL)
	public int putSubMerchantDetail(@Param(value="subMerchantId") String subMerchantId, @Param(value="bizKind") String bizKind, @Param(value="bizCond") String bizCond, 
			@Param(value="corpRegNo") String corpRegNo, @Param(value="bizRegNo") String bizRegNo, @Param(value="ceoName") String ceoName,
			@Param(value="bizGrp") String bizGrp, @Param(value="openDate") String openDate, @Param(value="bankNm") String bankNm, 
			@Param(value="bankAccNo") String bankAccNo, @Param(value="bankHolder") String bankHolder, @Param(value="zipcode") String zipcode,
			@Param(value="address") String address, @Param(value="addressDetail") String addressDetail, @Param(value="tel") String tel,
			@Param(value="fax") String fax, @Param(value="taxCustName") String taxCustName, @Param(value="taxEmail") String taxEmail,
			@Param(value="taxTel") String taxTel, @Param(value="taxPhone") String taxPhone, @Param(value="taxFax") String taxFax,
			@Param(value="salesNm") String salesNm, @Param(value="salesTel") String salesTel, @Param(value="billingNm") String billingNm, 
			@Param(value="billingTel") String billingTel, @Param(value="kpcSalesNm") String kpcSalesNm, @Param(value="kpcSalesTel") String kpcSalesTel,
			@Param(value="kpcBillingNm") String kpcBillingNm, @Param(value="kpcBillingTel") String kpcBillingTel, @Param(value="urlHome") String urlHome);
	
	public static final String GET_SUBMERCHANTS_BY_COUNT = "<script> "
			+ " SELECT COUNT(1) AS CNT "
			+ " FROM KPC_ADMIN.SUBMERCHANT A, KPC_ADMIN.SUBMERCHANT_DETAIL B ,KPC_ADMIN.MERCHANT_DETAIL C "
			+ " WHERE A.del_flag = 'N' AND A.SUBMERCHANT_ID = B.SUBMERCHANT_ID AND A.parent_id= C.MERCHANT_ID "
			+ " <if test='name != \"\" and name != null'> " 
            + " 	AND A.NAME LIKE '%' || #{name} || '%' " 
            + " </if> "
            + " <if test='merchantId != \"\" and merchantId != null'> " 
            + " 	AND A.SUBMERCHANT_ID LIKE '%' || #{merchantId} || '%' " 
            + " </if> "
            + " <if test='alias != \"\" and alias != null'> " 
            + " 	AND A.ALIAS LIKE '%' || #{alias} || '%' " 
            + " </if> "
            + " <if test='childId != \"\" and childId != null'> " 
            + " 	AND A.PARENT_ID LIKE '%' || #{childId} || '%' " 
            + " </if> "
            + " <if test='status != \"\" and status != null'> " 
            + " 	AND A.use_flag LIKE '%' || #{status} || '%' " 
            + " </if> "
            + " <if test='bizRegNo != \"\" and bizRegNo != null'> " 
            + " 	AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%' " 
            + " </if> "
			+ " </script> ";
	@Select(GET_SUBMERCHANTS_BY_COUNT)
	public int getSubMerchantsByCount(@Param(value="name") String name, @Param(value="merchantId") String merchantId,
			@Param(value="alias") String alias, @Param(value="childId") String childId,
			@Param(value="status") String status, @Param(value="bizRegNo") String bizRegNo);
	
	public static final String GET_SUBMERCHANTS = "<script> "
			+ " SELECT * FROM "
			+ " 	(SELECT ROWNUM AS RNUM, A.* "
			+ "		FROM "
			+ "			(SELECT A.SUBMERCHANT_ID "
			+ "					, '일반' gubun "
			+ "					, A.PARENT_ID "
			+ "                 , A.CHILD_ID "
			+ "                 , A.NAME "
			+ "                 , A.ALIAS "
			+ "                 , DECODE(A.use_flag ,'Y','사용','미사용') use_flag "  
			+ " 		        , A.CREATE_DT "
			+ "                 , TO_CHAR(A.update_dt , 'YYYY-MM-DD') updateDt "
			+ "                 , (SELECT count(1) "
			+ "                     FROM SUBMERCHANT_SERVICE D "
			+ "                    WHERE D.submerchant_id = A.submerchant_id "   
			+ "                      AND D.del_flag       = 'N' ) serviceCnt "
			+ "			FROM KPC_ADMIN.SUBMERCHANT A "
			+ "				, KPC_ADMIN.SUBMERCHANT_DETAIL B "
			+ "				, KPC_ADMIN.MERCHANT_DETAIL C "
			+ "			WHERE A.SUBMERCHANT_ID = B.SUBMERCHANT_ID "
			+ "			  AND A.parent_id      = C.MERCHANT_ID "
			+ "           AND A.del_flag       = 'N' "
			+ " 		<if test='name != \"\" and name != null'> " 
            + " 			AND A.NAME LIKE '%' || #{name} || '%' " 
            + " 		</if> "
            + " 		<if test='merchantId != \"\" and merchantId != null'> " 
            + " 			AND A.SUBMERCHANT_ID LIKE '%' || #{merchantId} || '%' " 
            + " 		</if> "
            + " 		<if test='alias != \"\" and alias != null'> " 
            + " 			AND A.ALIAS LIKE '%' || #{alias} || '%' " 
            + " 		</if> "
            + " 		<if test='childId != \"\" and childId != null'> " 
            + " 			AND A.PARENT_ID LIKE '%' || #{childId} || '%' " 
            + " 		</if> "
            + " 		<if test='status != \"\" and status != null'> " 
            + " 			AND A.use_flag LIKE '%' || #{status} || '%' " 
            + " 		</if> "
            + " 		<if test='bizRegNo != \"\" and bizRegNo != null'> " 
            + " 			AND B.biz_reg_no LIKE '%' || #{bizRegNo} || '%' " 
            + " 		</if> "
            + "			ORDER BY A.update_dt DESC ) A "
            + "		WHERE ROWNUM &lt;= #{limit} + #{offset} ) "
            + " WHERE RNUM &gt; #{offset}"
			+ " </script> ";
	@Select(GET_SUBMERCHANTS)
	public List<MerchantMerchantList> getMerchants(@Param(value="limit") int limit, @Param(value="offset") int offset,
			@Param(value="name") String name, @Param(value="merchantId") String merchantId,
			@Param(value="alias") String alias, @Param(value="childId") String childId,
			@Param(value="status") String status, @Param(value="bizRegNo") String bizRegNo
			);
			
	
	public static final String GET_SUBMERCHANT = "SELECT "
	        + "        A.submerchant_id "
	        + "       ,A.erp_code "
	        + "       ,A.depth "
	        + "       ,A.parent_id "
	        + "       ,A.child_id "
	        + "       ,A.name "
	        + "       ,A.name_eng "
	        + "       ,A.alias "
	        + "       ,A.alias_eng "
	        + "       ,DECODE(A.use_flag ,'Y','사용','미사용') useFlagName "
	        + "       ,A.use_flag "
	        + "       ,A.reuse_flag "
	        + "       ,A.del_flag "
	        + "       ,A.pg_id "
	        + "       ,A.pg_pw "
	        + "       ,A.agent_id "
	        + "       ,A.agent_pw "
	        + "       ,B.biz_kind "
	        + "       ,B.biz_cond "
	        + "       ,B.corp_reg_no "
	        + "       ,B.biz_reg_no "
	        + "       ,B.cust_nm "
	        + "       ,B.cust_tel "
	        + "       ,B.ceo_nm "
	        + "       ,B.type "
	        + "       ,FNC_ADMIN_CODE_TO_NAME(B.type) biz_grp "
	        + "       ,B.bank_nm bankCd "
	        + "       ,FNC_ADMIN_CODE_TO_NAME(B.bank_nm) bankNm "
	        + "       ,B.bank_acc_no "
	        + "       ,B.bank_holder " 
	        + "       ,B.zipcode "
	        + "       ,B.address "
	        + "       ,B.address_dtl "
	        + "       ,B.tel "
	        + "       ,B.fax "
	        + "       ,B.tax_cust_nm "
	        + "       ,B.tax_email "
	        + "       ,B.tax_tel "
	        + "       ,B.tax_phone "
	        + "       ,B.tax_fax "
	        + "       ,B.sales_nm "
	        + "       ,B.sales_tel "
	        + "       ,B.billing_nm "
	        + "       ,B.billing_tel "
	        + "       ,B.kpc_sales_nm "
	        + "       ,B.kpc_sales_tel "
	        + "       ,B.kpc_billing_nm "
	        + "       ,B.kpc_billing_tel "
	        + "       ,B.url01 "
	        + "       ,B.url02 "
	        + "       ,B.url_home "
	        + "       ,C.name parentName "
	        + "       ,B.open_dt  openDt "
	        + "       ,C.name || ' -> '|| A.name  path "
	        + " FROM KPC_ADMIN.SUBMERCHANT A "
	        + "    , KPC_ADMIN.SUBMERCHANT_DETAIL B "
	        + "    , KPC_ADMIN.MERCHANT C "
	        + " WHERE A.del_flag       = 'N' "
	        + "   AND A.submerchant_id = B.submerchant_id "
	        + "   AND A.parent_id      = C.merchant_id "
	        + "   AND A.submerchant_id = #{subMerchantId} ";
	@Select(GET_SUBMERCHANT)
	public MerchantMerchant getSubMerchant(@Param(value="subMerchantId") String subMerchantId);
	
	public static final String DELETE_SUBMERCHANT_BY_BILLING = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING "
			+ " 	SET DEL_FLAG = 'Y' "
			+ " WHERE SERVICE_ID IN ( "
			+ "						SELECT B.SERVICE_ID "
			+ "						FROM KPC_ADMIN.SUBMERCHANT A "
			+ " 						,KPC_ADMIN.SUBMERCHANT_SERVICE B "
			+ "						WHERE A.submerchant_id = #{subMerchantId} "
			+ "							AND A.submerchant_id = B.submerchant_id "
			+ "						) ";
	@Update(DELETE_SUBMERCHANT_BY_BILLING)
	public int deleteSubMerchantByBilling(@Param(value="subMerchantId") String subMerchantId);
	
	public static final String DELETE_SUBMERCHANT_BY_SERVICE = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE "
			+ "		SET DEL_FLAG = 'Y' "
			+ "	WHERE SUBMERCHANT_ID IN ( "
			+ "							SELECT submerchant_id "
			+ "							FROM KPC_ADMIN.SUBMERCHANT "
			+ "							WHERE parent_id = #{subMerchantId} "
			+ "						) ";
	@Update(DELETE_SUBMERCHANT_BY_SERVICE)
	public int deleteSubMerchantByService(@Param(value="subMerchantId") String subMerchantId);
	
	public static final String DELETE_SUBMERCHANT_BY_SUBMERCHANT = "UPDATE KPC_ADMIN.SUBMERCHANT "
			+ "		SET DEL_FLAG = 'Y' "
			+ " WHERE SUBMERCHANT_ID = #{subMerchantId} ";
	@Update(DELETE_SUBMERCHANT_BY_SUBMERCHANT)
	public int deleteSubMerchantBySubMerchant(@Param(value="subMerchantId") String subMerchantId);
	
	public static final String READ_SUB_MERCHANT_PATH = "SELECT "
			+ " b.name || ' -> ' || a.name path "
			+ " from "
			+ " 	submerchant a, merchant b "
			+ " where "
			+ "		a.submerchant_id = #{subMerchantId} "
			+ "		and a.parent_id = b.merchant_id "
			+ " 	and a.del_flag = 'N' "
			+ " 	and b.del_flag = 'N' ";
	@Select(READ_SUB_MERCHANT_PATH)
	public String readSubMerchantPath(@Param(value="subMerchantId") String subMerchantId);
	
	public static final String GET_SERVICES_BY_COUNT = "<script> "
			+ " SELECT count(1) CNT "
			+ " FROM KPC_ADMIN.SUBMERCHANT_SERVICE A "
			+ " 	,KPC_ADMIN.SUBMERCHANT B "
			+ " 	,KPC_ADMIN.SUBMERCHANT_DETAIL C "
			+ " 	,KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING D "
			+ " WHERE A.submerchant_id = B.submerchant_id "
			+ " 	AND A.submerchant_id = C.submerchant_id "
			+ " 	AND A.service_id = D.service_id(+) "
			+ " 	AND A.del_flag = 'N' "
			+ " 	AND B.del_flag = 'N' "
			+ " 	AND D.del_flag(+) = 'N' "
			+ " 	<if test='merchantId != \"\" and merchantId != null'> " 
            + " 		AND A.submerchant_id = #{merchantId} " 
            + " 	</if> "
            + " 	<if test='serviceId != \"\" and serviceId != null'> " 
            + " 		AND A.service_id LIKE '%' ||  #{serviceId} || '%' " 
            + " 	</if> "
            + " 	<if test='svcConnId != \"\" and svcConnId != null'> " 
            + " 		AND A.SVC_CONN_ID LIKE '%' || #{svcConnId} || '%'" 
            + " 	</if> "
            + " 	<if test='name != \"\" and name != null'> " 
            + " 		AND A.name LIKE '%' || #{name} || '%' " 
            + " 	</if> "
            + " 	<if test='useFlag != \"\" and useFlag != null'> " 
            + " 		AND A.use_flag = #{useFlag} " 
            + " 	</if> "
            + " 	<if test='type != \"\" and type != null'> " 
            + " 		AND A.type = #{type} " 
            + " 	</if> "
            + " 	<if test='billingRegFlag != \"\" and billingRegFlag == \"N\" '> " 
            + " 		AND D.service_billing_id IS NULL " 
            + " 	</if> "
            + " 	<if test='billingRegFlag != \"\" and billingRegFlag == \"Y\" '> " 
            + " 		AND D.service_billing_id IS NOT NULL " 
            + " 	</if> "            
			+ " </script> ";
	@Select(GET_SERVICES_BY_COUNT)
	public int getServicesByCount(@Param(value="merchantId") String merchantId,
								  @Param(value="serviceId") String serviceId,
								  @Param(value="svcConnId") String svcConnId,
								  @Param(value="name") String name,
								  @Param(value="useFlag") String useFlag,
								  @Param(value="type") String type,
								  @Param(value="billingRegFlag") String billingRegFlag
								  );
	
	public static final String GET_SERVICES = "<script> "
			+ " SELECT Z.* "                       
	        + "  FROM ( "
	        + "         SELECT ROWNUM AS RNUM "   
	        + "              , X.* "
	        + "           FROM ( "
	        + "                  SELECT A.service_id "
	        + "                        ,A.submerchant_id "
	        + "                        ,A.name "
	        + "                        ,B.name merchantName "
	        + "                        ,FNC_ADMIN_CODE_TO_NAME(A.category) categoryName "
	        + "                        ,A.category "
	        + "                        ,FNC_ADMIN_CODE_TO_NAME(A.type) typeName "
	        + "                        ,A.type typeCode "
	        + "                        ,A.use_flag "
	        + "                        ,DECODE(A.use_flag ,'Y','사용','미사용') useFlagName "
	        + "                        ,D.name billingName "
	        + "                        ,D.service_billing_id "
	        + "                        ,A.SVC_CONN_ID "
	        + "                    FROM KPC_ADMIN.SUBMERCHANT_SERVICE A "
	        + "                        ,KPC_ADMIN.SUBMERCHANT B "
	        + "                        ,KPC_ADMIN.SUBMERCHANT_DETAIL C "
	        + "                        ,KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING D "
	        + "                   WHERE A.submerchant_id = B.submerchant_id "
	        + "                     AND A.submerchant_id = C.submerchant_id "
	        + "                     AND A.service_id     = D.service_id(+) "
	        + "                     AND A.del_flag         = 'N' "
	        + "                     AND B.del_flag         = 'N' "
	        + "                     AND D.del_flag(+)      = 'N' " 
	        + " 	<if test='merchantId != \"\" and merchantId != null'> " 
            + " 		AND A.submerchant_id = #{merchantId} " 
            + " 	</if> "
            + " 	<if test='serviceId != \"\" and serviceId != null'> " 
            + " 		AND A.service_id LIKE '%' ||  #{serviceId} || '%' " 
            + " 	</if> "
            + " 	<if test='svcConnId != \"\" and svcConnId != null'> " 
            + " 		AND A.SVC_CONN_ID LIKE '%' || #{svcConnId} || '%'" 
            + " 	</if> "
            + " 	<if test='name != \"\" and name != null'> " 
            + " 		AND A.name LIKE '%' || #{name} || '%' " 
            + " 	</if> "
            + " 	<if test='useFlag != \"\" and useFlag != null'> " 
            + " 		AND A.use_flag = #{useFlag} " 
            + " 	</if> "
            + " 	<if test='serviceType != \"\" and serviceType != null'> " 
            + " 		AND A.type = #{serviceType} " 
            + " 	</if> "
            + " 	<if test='merchantName != \"\" and merchantName != null'> " 
            + " 		AND B.name LIKE '%' || #{merchantName} || '%' " 
            + " 	</if> "
            + " 	<if test='billingRegFlag != \"\" and billingRegFlag == \"N\" '> " 
            + " 		AND D.service_billing_id IS NULL " 
            + " 	</if> "
            + " 	<if test='billingRegFlag != \"\" and billingRegFlag == \"Y\" '> " 
            + " 		AND D.service_billing_id IS NOT NULL " 
            + " 	</if> "
            + "		ORDER BY A.update_dt DESC ) X "
            + " 	WHERE ROWNUM &lt;= #{limit} + #{offset} ) Z "
            + " WHERE RNUM &gt; #{offset}"
			+ " </script> ";
	@Select(GET_SERVICES)
	public List<MerchantServiceList> getServices(@Param(value="limit") int limit,
												 @Param(value="offset") int offset, 
												 @Param(value="merchantId") String merchantId,
												 @Param(value="name") String name,
												 @Param(value="useFlag") String useFlag, 
												 @Param(value="serviceId") String serviceId,
												 @Param(value="svcConnId") String svcConnId,
												 @Param(value="serviceType") String serviceType,
												 @Param(value="merchantName") String merchantName, 
												 @Param(value="billingRegFlag") String billingRegFlag
												 );
	
	public static final String GET_SERVICE = "SELECT A.service_id "
	        + "       , A.submerchant_id "
	        + "       , A.name "
	        + "       , FNC_ADMIN_CODE_TO_NAME(A.category) categoryName "
	        + "       , A.category "
	        + "       , FNC_ADMIN_CODE_TO_NAME(A.type)     serviceTypeName "
	        + "       , A.type  serviceType "
	        + "       , FNC_ADMIN_CODE_TO_NAME(A.SALE_DIVIDER) saleDividerName "
	        + "       , A.SALE_DIVIDER saleDivider "
	        + "       , A.use_flag "
	        + "       , DECODE(A.use_flag ,'Y','사용','미사용') useFlagName "
	        + "       , A.svc_conn_id "
	        + "       , A.svc_conn_pw "
	        + "       , C.name || ' -> '|| B.name  path "
	        + "  FROM KPC_ADMIN.SUBMERCHANT_SERVICE A "
	        + "     , KPC_ADMIN.SUBMERCHANT B "
	        + "     , KPC_ADMIN.MERCHANT C "
	        + " WHERE A.SERVICE_ID     = #{serviceId} "
	        + "   AND A.submerchant_id = B.submerchant_id "
	        + "   AND B.parent_id      = C.merchant_id "
	        + "   AND A.del_flag       = 'N' "
	        + "   AND B.del_flag       = 'N' "
	        + "   AND C.del_flag       = 'N' ";
	@Select(GET_SERVICE)
	public MerchantService getService(@Param(value="serviceId") String serviceId);
	
	public static final String INSERT_SERVICE = "INSERT INTO KPC_ADMIN.SUBMERCHANT_SERVICE "
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
            + "          #{serviceId} "      
            + "         ,#{submerchantId} "      
            + "         ,#{serviceName} "
            + "         ,#{category} "
            + "         ,#{serviceType} "
            + "         ,#{useFlag} "
            + "         ,'N' "
            + "         ,#{svcConnId} "
            + "         ,#{svcConnPw} "
            + "         ,#{saleDivider} "
            + "         ,SYSDATE"
            + "         ,#{createDesc, jdbcType=VARCHAR} "
            + "         ,#{createAdmId, jdbcType=VARCHAR} "
            + "       ) ";
	@Insert(value=INSERT_SERVICE)
	public int postService(@Param(value="serviceId") String serviceId,
						  @Param(value="submerchantId") String submerchantId ,
						  @Param(value="serviceName") String serviceName,
						  @Param(value="category") String category,
						  @Param(value="serviceType") String serviceType,
						  @Param(value="useFlag") String useFlag,
						  @Param(value="svcConnId") String svcConnId,
						  @Param(value="svcConnPw") String svcConnPw,
						  @Param(value="saleDivider") String saleDivider,
						  @Param(value="createDesc") String createDesc,
						  @Param(value="createAdmId") String createAdmId);
	
	public static final String UPDATE_SERVICE = "<script> "
			+ " UPDATE SUBMERCHANT_SERVICE "
			+ " 	SET SERVICE_ID = SERVICE_ID "
			+ " 	<if test='submerchantId != \"\" and submerchantId != null'> " 
            + " 		, SUBMERCHANT_ID = #{submerchantId} " 
            + " 	</if> "
            + " 	<if test='category != \"\" and category!=null '> " 
            + " 		, CATEGORY = #{category} " 
            + " 	</if> "
            + " 	<if test='name != \"\" and name!=null '> " 
            + " 		, NAME = #{name} " 
            + " 	</if> "
            + " 	<if test='serviceType != \"\" and serviceType!=null'> " 
            + " 		, TYPE = #{serviceType} " 
            + " 	</if> "
            + " 	<if test='useFlag != \"\" and useFlag!=null'> " 
            + " 		, USE_FLAG = #{useFlag} " 
            + " 	</if> "
            + " 	<if test='saleDivider != \"\" and saleDivider!=null'> " 
            + " 		, SALE_DIVIDER = #{saleDivider} " 
            + " 	</if> "
            + " 	<if test='svcConnId != \"\" and svcConnId!=null'> " 
            + " 		, SVC_CONN_ID = #{svcConnId} " 
            + " 	</if> "
            + " 	<if test='svcConnPw != \"\" and svcConnPw!=null'> " 
            + " 		, SVC_CONN_PW = #{svcConnPw} " 
            + " 	</if> "
         // TODO : AGent 정보 임시 주석
//            + " 	<if test='agentId != \"\" '> " 
//            + " 		, agent_id = #{agentId} " 
//            + " 	</if> "
//            + " 	<if test='agentPw != \"\" '> " 
//            + " 		, agentPw = #{agentPw} " 
//            + " 	</if> "
            + " 	<if test='updateDesc != \"\" and updateDesc!=null '> " 
            + " 		, UPDATE_DESC = #{updateDesc} " 
            + " 	</if> "
            + " 	<if test='updateAdmId != \"\" and updateAdmId!=null '> " 
            + " 		, UPDATE_ADM_ID = #{updateAdmId} " 
            + " 	</if> "
            + "		, UPDATE_DT = SYSDATE "
            + " WHERE SERVICE_ID = #{serviceId}"
			+ " </script> ";
	@Update(UPDATE_SERVICE)
	public int putService(@Param(value="serviceId") String serviceId,
						  @Param(value="submerchantId") String submerchantId ,
						  @Param(value="name") String name,
						  @Param(value="category") String category,
						  @Param(value="serviceType") String serviceType,
						  @Param(value="useFlag") String useFlag,
						  @Param(value="svcConnId") String svcConnId,
						  @Param(value="svcConnPw") String svcConnPw,
						  @Param(value="saleDivider") String saleDivider, 
						  @Param(value="updateDesc") String updateDesc,
						  @Param(value="updateAdmId") String updateAdmId);
	
	public static final String DELETE_SERVICE_BY_BILLING = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING "
			+ "		SET del_flag   = 'Y' "
			+ " WHERE SERVICE_ID = #{serviceId}";
	@Update(DELETE_SERVICE_BY_BILLING)
	public int deleteServiceByBilling(String serviceId);
	
	public static final String DELETE_SERVICE_BY_SERVICE = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE "
			+ "		SET del_flag   = 'Y' "
			+ " WHERE SERVICE_ID = #{serviceId}";
	@Update(DELETE_SERVICE_BY_SERVICE)
	public int deleteService(String serviceId);
	
	public static final String INSERT_BILLING = "INSERT INTO KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING "
            + "        ( "
            + "           service_billing_id "
            + "          ,service_id "
            + "          ,name "
            + "          ,code "
            + "          ,divider "
            + "          ,apl_start_dt "
            + "          ,bank_nm "
            + "          ,bank_acc_no "
            + "          ,bank_holder "
            + "          ,billing_dt "
            + "          ,billing_duration "
            + "          ,merchant_comm_type "
            + "          ,merchant_commision "
            + "          ,merchant_tax_type "
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
            + "			 ,BILLING_COMM_TYPE "
            + "        ) "
            + " VALUES "
            + "        ( "
            + "           #{billingId} "
            + "          ,#{serviceId} "
            + "          ,#{name} "
            + "          ,#{code} "
            + "          ,#{divider} "
            + "          ,SYSDATE "
            + "          ,#{bankNm} "
            + "          ,#{bankAccNo} "
            + "          ,#{bankHolder} "
            + "          ,#{billingDt} "
            + "          ,#{billingDuration} "
            + "          ,#{merchantCommType} "
            + "          ,#{merchantCommision} "
            + "          ,#{merchantTaxType} "
            + "          ,#{billingNm} "
            + "          ,#{billingTel} "
            + "          ,#{billingEmail} "
            + "          ,#{kpcBillingNm} "
            + "          ,#{kpcBillingTel} "
            + "          ,#{kpcBillingEmail} "
            + "          ,'N' "
            + "          ,SYSDATE "
            + "          ,'신규' "
            + "          ,#{createAdmId}"
            + "			 ,#{billingCommType} "
            + "        ) ";
	@Insert(INSERT_BILLING)
	public int postBilling(@Param(value="billingId") String billingId, @Param(value="serviceId") String serviceId, @Param(value="name") String name, 
			@Param(value="bankNm") String bankNm, @Param(value="bankAccNo") String bankAccNo, @Param(value="bankHolder") String bankHolder,
			@Param(value="billingNm") String billingNm, @Param(value="billingTel") String billingTel, @Param(value="billingEmail") String billingEmail, 
			@Param(value="kpcBillingNm") String kpcBillingNm, @Param(value="kpcBillingTel") String kpcBillingTel, @Param(value="kpcBillingEmail") String kpcBillingEmail, 
			@Param(value="divider") String divider, @Param(value="code") String code, @Param(value="billingDuration") String billingDuration, 
			@Param(value="billingDt") String billingDt, @Param(value="merchantCommType") String merchantCommType, @Param(value="merchantTaxType") String merchantTaxType, 
			@Param(value="merchantCommision") String merchantCommision, @Param(value="createAdmId") String createAdmId, @Param(value="billingCommType") String billingCommType);
	
	
	public static final String UPDATE_BILLING_BY_BILLING = "<script> "
			+ " UPDATE SUBMERCHANT_SERVICE_BILLING "
			+ "		SET SERVICE_BILLING_ID = SERVICE_BILLING_ID "
			+ "			, UPDATE_DT = SYSDATE "
			+ " 	<if test='name != \"\" and name != null'> " 
            + " 		, name = #{name} " 
            + " 	</if> "
            + " 	<if test='code != \"\" and code != null'> " 
            + " 		, code = #{code} " 
            + " 	</if> "
            + " 	<if test='divider != \"\" and divider != null'> " 
            + " 		, divider = #{divider} " 
            + " 	</if> "
            + " 	<if test='billingDuration != \"\" and billingDuration != null'> " 
            + " 		, billing_duration = #{billingDuration} " 
            + " 	</if> "
            + " 	<if test='bankNm != \"\" and bankNm != null'> " 
            + " 		, bank_nm = #{bankNm} " 
            + " 	</if> "
            + " 	<if test='bankAccNo != \"\" and bankAccNo != null'> " 
            + " 		, bank_acc_no = #{bankAccNo} " 
            + " 	</if> "
            + " 	<if test='bankHolder != \"\" and bankHolder != null'> " 
            + " 		, bank_holder = #{bankHolder} " 
            + " 	</if> "
            + " 	<if test='billingNm != \"\" and billingNm != null'> " 
            + " 		, mng_nm = #{billingNm} " 
            + " 	</if> "
            + " 	<if test='billingEmail != \"\" and billingEmail != null'> " 
            + " 		, mng_email = #{billingEmail} " 
            + " 	</if>"
            + "		<if test='billingCommType != \"\" and billingCommType != null'>"
            + " 		, BILLING_COMM_TYPE=#{billingCommType}"
            + "		</if> "
            + "			, mng_tel = #{billingTel} "
            + "			, billing_dt = #{billingDt} "
            + "			, merchant_comm_type = #{merchantCommType}"
            //+ "			, merchant_commision = #{merchantCommision}"
            + "			, merchant_tax_type = #{merchantTaxType}"
            + "			, kpc_mng_nm = #{kpcBillingNm}"
            + "			, kpc_mng_tel = #{kpcBillingTel}"
            + "			, kpc_mng_email = #{kpcBillingEmail}"
            + "		WHERE SERVICE_BILLING_ID = #{serviceBillingId} "
			+ " </script>";
	@Update(UPDATE_BILLING_BY_BILLING)
	public int putBilling(@Param(value="serviceBillingId") String serviceBillingId, @Param(value="serviceId") String serviceId, @Param(value="name") String name, 
			@Param(value="code") String code, @Param(value="divider") String divider, @Param(value="billingDuration") String billingDuration,
			@Param(value="bankNm") String bankNm, @Param(value="bankAccNo") String bankAccNo, @Param(value="bankHolder") String bankHolder,
			@Param(value="billingNm") String billingNm, @Param(value="billingTel") String billingTel, @Param(value="billingEmail") String billingEmail, 
			@Param(value="kpcBillingNm") String kpcBillingNm, @Param(value="kpcBillingTel") String kpcBillingTel, @Param(value="kpcBillingEmail") String kpcBillingEmail,
			@Param(value="billingDt") String billingDt, @Param(value="merchantCommType") String merchantCommType, @Param(value="merchantTaxType") String merchantTaxType, 
			@Param(value="merchantCommision") String merchantCommision, @Param(value="billingCommType") String billingCommType);
	
	
	public static final String UPDATE_BILLING_BY_BIILING_HST = "INSERT INTO SUBMERCHANT_SVC_BILLING_HST "
            + "       ( "
            + "          hst_seq "
            + "         ,service_billing_id "
            + "         ,service_id "
            + "         ,name "
            + "         ,code "
            + "         ,type "
            + "         ,divider "
            + "         ,apl_start_dt "
            + "         ,apl_end_dt "
            + "         ,bank_nm "
            + "         ,bank_acc_no "          
            + "         ,vr_bank_nm "           
            + "         ,vr_bank_acc_no "       
            + "         ,bank_holder "          
            + "         ,share_rate "            
            + "         ,unpaid_amt "           
            + "         ,limits_amt "           
            + "         ,credit_setting_amt "   
            + "         ,billing_dt "           
            + "         ,billing_duration "     
            + "         ,merchant_comm_type "   
            + "         ,merchant_commision "   
            + "         ,merchant_tax_type "    
            + "         ,merchant_tax "         
            + "         ,kpc_comm_type "        
            + "         ,kpc_commision "        
            + "         ,kpc_tax_type "         
            + "         ,kpc_tax "              
            + "         ,add_comm_type01 "      
            + "         ,add_commision01 "      
            + "         ,add_tax_type01 "       
            + "         ,add_tax01 "            
            + "         ,add_comm_type02 "      
            + "         ,add_commision02 "      
            + "         ,add_tax_type02 "       
            + "         ,add_tax02 "            
            + "         ,mng_nm "               
            + "         ,mng_tel "              
            + "         ,mng_email "            
            + "         ,kpc_mng_nm "           
            + "         ,kpc_mng_tel "          
            + "         ,kpc_mng_email "        
            + "         ,del_flag "             
            + "         ,create_dt "            
            + "         ,create_desc "          
            + "         ,create_adm_id "        
            + "         ,update_dt "            
            + "         ,update_desc "          
            + "         ,update_adm_id"
            + "			,billing_comm_type "        
            + "       ) "
            + " SELECT (SELECT NVL(MAX(HST_SEQ) , 0) + 1 FROM SUBMERCHANT_SVC_BILLING_HST) " 
            + "       ,service_billing_id "
            + "       ,service_id "
            + "       ,name "
            + "       ,code "
            + "       ,type "
            + "       ,divider "
            + "       ,apl_start_dt "
            + "       ,#{aplEndDt} "
            + "       ,bank_nm " 
            + "       ,bank_acc_no " 
            + "       ,vr_bank_nm "
            + "       ,vr_bank_acc_no " 
            + "       ,bank_holder "
            + "       ,share_rate "
            + "       ,unpaid_amt "
            + "       ,limits_amt "
            + "       ,credit_setting_amt " 
            + "       ,billing_dt "
            + "       ,billing_duration " 
            + "       ,merchant_comm_type " 
            + "       ,#{merchantCommision} " 
            + "       ,merchant_tax_type "
            + "       ,merchant_tax "
            + "       ,kpc_comm_type "
            + "       ,kpc_commision "
            + "       ,kpc_tax_type "
            + "       ,kpc_tax "
            + "       ,add_comm_type01 " 
            + "       ,add_commision01 "
            + "       ,add_tax_type01 "
            + "       ,add_tax01 "
            + "       ,add_comm_type02 " 
            + "       ,add_commision02 "
            + "       ,add_tax_type02 "
            + "       ,add_tax02 "
            + "       ,mng_nm " 
            + "       ,mng_tel "
            + "       ,mng_email " 
            + "       ,kpc_mng_nm "
            + "       ,kpc_mng_tel "
            + "       ,kpc_mng_email " 
            + "       ,del_flag "
            + "       ,create_dt "
            + "       ,create_desc " 
            + "       ,create_adm_id " 
            + "       ,update_dt "
            + "       ,update_desc " 
            + "       ,update_adm_id"
            + "		  ,billing_comm_type "
            + "   FROM SUBMERCHANT_SERVICE_BILLING "  
            + "  WHERE SERVICE_BILLING_ID = #{serviceBillingId} ";
	@Insert(UPDATE_BILLING_BY_BIILING_HST)
	public int putBillingHst(@Param(value="aplEndDt") String aplEndDt, @Param(value="merchantCommision") String merchantCommision, 
			@Param(value="serviceBillingId") String serviceBillingId);
	
	public static final String GET_BILLING = "SELECT D.service_billing_id "                        
            + "      ,D.service_id "                                
            + "      ,D.name "                                      
            + "      ,D.code "                                      
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.code) codeName "     
            + "      ,D.divider "                                   
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.divider) dividerName "  
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.bank_nm) bankNm "  
            + "      ,D.bank_nm                         bankCd "
            + "      ,D.bank_acc_no "                               
            + "      ,D.vr_bank_nm "                                
            + "      ,D.vr_bank_acc_no "                            
            + "      ,D.bank_holder "
            + "      ,D.billing_dt "
            + "      ,D.billing_duration "
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.billing_duration) billingDurationName "
            + "      ,D.merchant_comm_type "
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.merchant_comm_type) merchantCommTypeName "
            + "      ,NVL(RTRIM(TO_CHAR(D.merchant_commision,'FM9999999999990.99'),'.'),0) merchant_commision "
            + "      ,D.merchant_tax_type "
            + "      ,FNC_ADMIN_CODE_TO_NAME(D.merchant_tax_type) merchantTaxTypeName "
            + "      ,D.merchant_tax "
            + "      ,D.mng_nm "
            + "      ,D.mng_tel "
            + "      ,D.mng_email "
            + "      ,d.kpc_mng_nm "
            + "      ,d.kpc_mng_tel "
            + "      ,d.kpc_mng_email "                                 
            + "      ,D.create_dt "
            + "      ,E.apl_end_dt "
            + "      ,RTRIM(TO_CHAR(E.merchant_commision,'FM9999999999990.99'),'.') expectedMerchantCommision "
            + "		 ,D.BILLING_COMM_TYPE billingCommType"
            + "		 ,FNC_ADMIN_CODE_TO_NAME(D.BILLING_COMM_TYPE) billingCommTypeName"
            + "  FROM KPC_ADMIN.SUBMERCHANT A "
            + "     , KPC_ADMIN.SUBMERCHANT_DETAIL B "
            + "     , KPC_ADMIN.SUBMERCHANT_SERVICE C "
            + "     , KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING D "
            + "     , ( " 
            + "          SELECT * "
            + "            FROM ( "
            + "                   SELECT B.hst_seq " 
            + "                         ,B.apl_end_dt "
            + "                         ,B.service_billing_id "
            + "                         ,B.merchant_commision "
            + "                     FROM KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING A "
            + "                         ,SUBMERCHANT_SVC_BILLING_HST B "
            + "                    WHERE A.service_billing_id = B.service_billing_id "             
            + "                      AND A.SERVICE_BILLING_ID = #{billingId} "
            + "                      AND B.apl_end_dt        >= TO_CHAR(SYSDATE , 'YYYYMMDD') "
            + "                    ORDER BY B.hst_seq DESC , B.apl_end_dt DESC "
            + "                 ) "
            + "           WHERE ROWNUM = 1 "
            + "        ) E   "
            + " WHERE A.submerchant_id     = B.submerchant_id "         
            + "   AND A.submerchant_id     = C.submerchant_id "        
            + "   AND C.service_id         = D.service_id "      
            + "   AND D.service_billing_id = E.service_billing_id(+) "       
            + "   AND A.del_flag           = 'N' "                      
            + "   AND C.del_flag           = 'N' "                     
            + "   AND D.del_flag           = 'N' "                     
            + "   AND D.SERVICE_BILLING_ID = #{billingId}";          
	@Select(GET_BILLING)
	public MerchantBillingsList getBilling(@Param(value="billingId") String billingId);
	
	public static final String DELETE_BILLING = "UPDATE KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING "
			+ "		SET DEL_FLAG = 'Y' "
			+ " WHERE SERVICE_BILLING_ID = #{billingId}";
	@Update(DELETE_BILLING)
	public int deleteBilling(@Param(value="billingId") String billingId);
	
	public static final String GET_BILLINGS_BY_COUNT = "<script> "
			+ " SELECT COUNT(1) CNT "
			+ " FROM KPC_ADMIN.SUBMERCHANT A "
			+ "		, KPC_ADMIN.SUBMERCHANT_DETAIL B "
			+ "		, KPC_ADMIN.SUBMERCHANT_SERVICE C "
			+ "		, KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING D "
			+ "	WHERE A.submerchant_id = B.submerchant_id "
			+ "		AND A.submerchant_id = C.submerchant_id "
			+ "		AND C.service_id     = D.service_id "
			+ "		AND A.del_flag       = 'N' "
			+ "		AND C.del_flag       = 'N' "
			+ "		AND D.del_flag       = 'N' "
			+ " 	<if test='serviceId != \"\" and serviceId != null'> " 
            + " 		AND D.SERVICE_ID LIKE '%' || #{serviceId} || '%' " 
            + " 	</if> "
			+ " </script> ";
	@Select(GET_BILLINGS_BY_COUNT)
	public int getBillingsByCount(@Param(value="serviceId") String serviceId);
	
	public static final String GET_BILLINGS_BY_BILLING = "<script> "
			+ "	SELECT * FROM "
			+ "	( "
			+ "		SELECT ROWNUM AS RNUM, A.*"
			+ "		FROM "
			+ " 	( "
			+ "			SELECT D.service_billing_id "
			+ "				,D.service_id "
			+ "				,D.name "
			+ "				,D.code "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.code) codeName "
			+ "				,D.divider "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.divider) dividerName "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.divider) bankNm "
			+ "				,D.bank_nm bankCd "
			+ "				,D.bank_acc_no "
			+ "				,D.vr_bank_nm "
			+ "				,D.vr_bank_acc_no "
			+ "				,D.bank_holder "
			+ "				,D.billing_dt "
			+ "				,D.billing_duration "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.billing_duration) billingDurationName "
			+ "				,D.merchant_comm_type "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.merchant_comm_type) merchantCommTypeName "
			+ "				,D.merchant_commision "
			+ "				,D.merchant_tax_type "
			+ "				,FNC_ADMIN_CODE_TO_NAME(D.merchant_tax_type) merchantTaxTypeName "
			+ "				,D.merchant_tax "
			+ "				,D.mng_nm "
			+ "				,D.mng_tel "
			+ "				,D.mng_email "
			+ "				,D.kpc_mng_nm "
			+ "				,D.kpc_mng_tel "
			+ "				,D.kpc_mng_email "
			+ "				,D.create_dt "
			+ "				,'' apl_end_dt "
			+ "				,'' expectedMerchantCommision"
			+ "				,D.BILLING_COMM_TYPE "
			+ "			FROM KPC_ADMIN.SUBMERCHANT A "
			+ "				, KPC_ADMIN.SUBMERCHANT_DETAIL B "
			+ "				, KPC_ADMIN.SUBMERCHANT_SERVICE C "
			+ "				, KPC_ADMIN.SUBMERCHANT_SERVICE_BILLING D "
			+ "			WHERE A.submerchant_id = B.submerchant_id "
			+ "				AND A.submerchant_id = C.submerchant_id "
			+ "				AND C.service_id     = D.service_id "
			+ "				AND A.del_flag       = 'N' "
			+ "				AND C.del_flag       = 'N' "
			+ "				AND D.del_flag       = 'N' "
			+ " 		<if test='serviceId != \"\" and serviceId != null'> " 
            + " 			AND D.SERVICE_ID LIKE '%' || #{serviceId} || '%' " 
            + " 		</if> "
            + "		) A "
            + "		WHERE ROWNUM &lt;= #{limit} + #{offset} "
            + "	) " 
            + "	WHERE RNUM &gt; #{offset} "
			+ " </script>";
	@Select(GET_BILLINGS_BY_BILLING)
	public List<MerchantBillingsList> getBillings(@Param(value="limit") int limit, @Param(value="offset") int offset, @Param(value="serviceId") String serviceId);
	
	public static final String GET_MERCHANT_ID = "SELECT 'PM-' || LPAD(SEQ_MERCHANT_ID.NEXTVAL, 6, '0') AS MERCHANT_ID FROM DUAL";
	@Select(GET_MERCHANT_ID)
	public String createMerchantId();
	
	public static final String GET_SUBMERCHANT_ID = "SELECT 'MC-' || LPAD(SEQ_SUBMERCHANT_ID.NEXTVAL, 6, '0') AS SUBMERCHANT_ID FROM DUAL";
	@Select(GET_SUBMERCHANT_ID)
	public String createSubMerchantId();
	
	public static final String GET_BILLING_ID = "SELECT 'MS-' || LPAD(SEQ_SERVICE_BILLING_ID.NEXTVAL, 6, '0') AS BILLING_ID FROM DUAL";
	@Select(GET_BILLING_ID)
	public String createBillingId();
	
	public static final String GET_SERVICE_ID = "SELECT 'SB-' || LPAD(SEQ_SERVICE_ID.NEXTVAL, 6, '0') AS SERVICE_ID FROM DUAL";
	@Select(GET_SERVICE_ID)
	public String createServiceId();
}
