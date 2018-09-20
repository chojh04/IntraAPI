/**
 * 
 */
package kr.co.kpcard.erp.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.BillingServiceApplication;
import kr.co.kpcard.erp.repository.model.ADocument;

/**
 * 회계전표처리 테스트 JUnit
 * @author chris
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingServiceApplication.class)
public class AccountErpRepositoryTests {
	private Logger logger = LoggerFactory.getLogger(AccountErpRepositoryTests.class);

	@Autowired
	private AccountErpRepository	accountErpRepository;
	
	/**
	 * 특정전표 조회 테스트
	 */
	@Test
	public void testGetDocument() {
		ADocument docu = accountErpRepository.selectDocument("DM201305310001", "63");
		
		logger.debug("rowId = {}, rowNo = {}, cdPartner = {}", docu.getRowIdentifier(), docu.getRowNo(), docu.getCdPartner());
		assertEquals(docu.getCdPartner(), "01041");
	}

	/**
	 * 전표리스트 조회 테스트
	 */
	@Test
	public void testGetDocumentList() {
		List<ADocument> list = accountErpRepository.selectDocumentList("DM201305310001");
		
		logger.debug("rowId = {}, count = {}", "DM201305310001", list.size());
		assertEquals(list.size(), 1020);
	}
	
	/**
	 * 단순 테스트(integer return)
	 */
	@Test
	public void testQuery() {
		int rtn = accountErpRepository.selectDual();
		
		logger.debug("rtn = {}", rtn);
		assertEquals(rtn, 0);
	}
	
}
