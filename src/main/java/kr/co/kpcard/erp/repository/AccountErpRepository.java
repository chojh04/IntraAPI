/**
 * 
 */
package kr.co.kpcard.erp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.erp.repository.mapper.AccountErpMapper;
import kr.co.kpcard.erp.repository.model.ADocument;

/**
 * 회계처리 Repository
 * @author chris
 *
 */
@Repository
public class AccountErpRepository {
	
	@Autowired
	private AccountErpMapper		accountErpMapper;
	
	/**
	 * Select 테스트
	 * @return
	 */
	public int selectDual() {
		int			rtn;
		
		rtn = accountErpMapper.selectDual();
		return rtn;
	}

	/**
	 * 전표가져오기
	 * @param rowId 전표번호
	 * @param rowNo 전표넘버(Sub번호)
	 * @return
	 */
	public ADocument selectDocument(String rowId, String rowNo) {
		ADocument	docu = null;
		
		docu = accountErpMapper.selectADocuemnt(rowId, rowNo);
		
		return docu;
	}

	/**
	 * 전표 리스트 가져오기
	 * @param rowId 전표번호
	 * @return
	 */
	public List<ADocument> selectDocumentList(String rowId) {
		List<ADocument>	list = null;
		
		list = accountErpMapper.selectADocuemntList(rowId);
		
		return list;
	}
}
