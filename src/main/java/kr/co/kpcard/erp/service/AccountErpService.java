package kr.co.kpcard.erp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.erp.repository.AccountErpRepository;
import kr.co.kpcard.erp.repository.model.ADocument;

/**
 * 회계처리 Service
 * @author chris
 *
 */
@Service
public class AccountErpService {

	private static Logger logger = LoggerFactory.getLogger(AccountErpService.class);

	@Autowired
	private AccountErpRepository	accountErpRepository;
	
	/**
	 * 전표 가져오기
	 * @param rowId
	 * @param rowNo
	 * @return
	 */
	public ADocument getDocument(String rowId, String rowNo) {
		ADocument	docu;
		
		docu = accountErpRepository.selectDocument(rowId, rowNo);
		if ( docu != null ) {
			logger.debug("FI_ADOCU : rowId = {}, rowNo = {}", docu.getRowIdentifier(), docu.getRowNo());
		}
		
		return docu;
	}

	/**
	 * 전표리스트 가져오기
	 * @param rowId
	 * @return
	 */
	public List<ADocument> getDocumentList(String rowId) {
		List<ADocument>	list;
		
		list = accountErpRepository.selectDocumentList(rowId);
		if ( list != null && !list.isEmpty() ) {
			int		idx = 0;
			
			for ( ADocument docu : list) {
				logger.debug("FI_ADOCU : rowId = {}, rowNo = {} [{} / {}]", docu.getRowIdentifier(), docu.getRowNo(), ++idx, list.size());
			}
		}
		
		return list;
	}
}
