/**
 * 
 */
package kr.co.kpcard.erp.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.kpcard.erp.repository.model.ADocument;

/**
 * 회계전표 Mapper
 * @author chris
 *
 */
@Mapper
public interface AccountErpMapper {
	/**
	 * 테스트 쿼리
	 */
	public static final String SELECT_TEST = "<script>"
			+ "SELECT AMT "
			+ "  FROM FI_ADOCU "
	        + " WHERE 1=1"
	        + "   AND ROW_ID = \'DM201711141535\' "
	        + "</script>";

	/**
	 * 전표조회
	 * @param rowId
	 * @param rowNo
	 * @return
	 */
	@Select(SELECT_TEST)
	public int selectDual();
	
	/**
	 * 전표조회 쿼리
	 */
	public static final String SELECT_FI_A_DOCUMENT = "<script>"
			+ "SELECT row_id as rowIdentifier, "
			+ " row_no as rowNo, "
			+ " no_tax as noTax, "
			+ " cd_pc as cdPc, "
			+ " cd_wdept as cdWdept, "
			+ " no_docu as noDocu, "
			+ " no_doline as noDoline, "
			+ " cd_company as cdCompany, "
			+ " id_write as idWrite, "
			+ " cd_docu as cdDocu, "
			+ " dt_acct as dtAcct, "
			+ " st_docu as stDocu, "
			+ " tp_drcr as tpDrcr, "
			+ " cd_acct as cdAcct, "
			+ " amt as amt, "
			+ " cd_partner as cdPartner, "
			+ " nm_partner as nmPartner "
			+ "  FROM FI_ADOCU "
	        + " WHERE 1=1"
	        + " <if test='rowId != \"\" '> "
	        + "   AND ROW_ID = #{rowId} "
	        + " </if> "
	        + " <if test='rowNo != \"\" '> "
	        + "   AND ROW_NO = #{rowNo} "
	        + " </if> "
			+ "</script>";
			
	/**
	 * 전표조회
	 * @param rowId
	 * @param rowNo
	 * @return
	 */
	@Select(SELECT_FI_A_DOCUMENT)
	public ADocument selectADocuemnt(@Param(value="rowId") String rowId, @Param(value="rowNo") String rowNo);
	
	/**
	 * 전표리스트 조회 쿼리
	 */
	public static final String SELECT_FI_A_DOCUMENT_LIST = "<script>"
			+ "SELECT row_id as rowIdentifier, "
			+ " row_no as rowNo, "
			+ " no_tax as noTax, "
			+ " cd_pc as cdPc, "
			+ " cd_wdept as cdWdept, "
			+ " no_docu as noDocu, "
			+ " no_doline as noDoline, "
			+ " cd_company as cdCompany, "
			+ " id_write as idWrite, "
			+ " cd_docu as cdDocu, "
			+ " dt_acct as dtAcct, "
			+ " st_docu as stDocu, "
			+ " tp_drcr as tpDrcr, "
			+ " cd_acct as cdAcct, "
			+ " amt as amt, "
			+ " cd_partner as cdPartner, "
			+ " nm_partner as nmPartner "
			+ "  FROM FI_ADOCU "
	        + " WHERE 1=1"
	        + " <if test='rowId != \"\" '> "
	        + "   AND ROW_ID = #{rowId} "
	        + " </if> "
			+ "</script>";
		
	/**
	 * 전표리스트 조회
	 * @param rowId
	 * @return
	 */
	@Select(SELECT_FI_A_DOCUMENT_LIST)
	public List<ADocument> selectADocuemntList(@Param(value="rowId") String rowId);

}
