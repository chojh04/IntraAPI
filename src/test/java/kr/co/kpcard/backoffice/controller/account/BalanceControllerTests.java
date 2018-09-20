/**
 * 
 */
package kr.co.kpcard.backoffice.controller.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.backoffice.controller.account.BalanceController;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseDailyBalance;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseTransaction;
import kr.co.kpcard.backoffice.repository.account.BalanceRepositoryTests;
import kr.co.kpcard.billingservice.app.BillingServiceApplication;

/**
 * 카드 잔액조회 Controller JUnit Test
 * @author chris
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingServiceApplication.class)
public class BalanceControllerTests {
	private Logger logger = LoggerFactory.getLogger(BalanceRepositoryTests.class);

	@Autowired
	BalanceController	balanceController;
	
	/**
	 * BalanceController.getDailyBalanceList() controller 테스트
	 */
	@Test
	public void testGetDailyBalanceList() {
		ResponseEntity<ResponseDailyBalance> response = balanceController.getDailyBalanceList(null, null, "20180801", "20180831", "ASC", 10, 0);
		ResponseDailyBalance responseDailyBalance = response.getBody();
		
		// 전체 개수 체크
		logger.debug("Total Count : {}", responseDailyBalance.getSummary().getCount());
		assertEquals(responseDailyBalance.getSummary().getCount(), 19);
		
		// 리스트 개수 체크
		logger.debug("List Count : {}", responseDailyBalance.getResultList().size());
		assertEquals(responseDailyBalance.getResultList().size(), 10);
	}

	/**
	 * BalanceController.getTransactionSummary() controller 테스트
	 */
	@Test
	public void testGetTransactionSummary() {
		ResponseEntity<ResponseTransaction> response = balanceController.getTransactionSummary(null, null, "20180801", "20180831", "TRNT-0001", 10, 0);
		ResponseTransaction responseTransaction = response.getBody();
		
		// 전체 개수 체크
		logger.debug("Total Count : {}", responseTransaction.getSummary().getCount());
		assertEquals(responseTransaction.getSummary().getCount(), 6);
		
		// 리스트 개수 체크
		logger.debug("List Count : {}", responseTransaction.getResultList().size());
		assertEquals(responseTransaction.getResultList().size(), 6);
	}
}
