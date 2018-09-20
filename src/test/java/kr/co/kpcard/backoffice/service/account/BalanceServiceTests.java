package kr.co.kpcard.backoffice.service.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.backoffice.controller.account.protocol.ResponseDailyBalance;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseTransaction;
import kr.co.kpcard.backoffice.repository.account.BalanceRepositoryTests;
import kr.co.kpcard.billingservice.app.BillingServiceApplication;

/**
 * 카드 잔액조회 Service JUnit Test
 * @author chris
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingServiceApplication.class)
public class BalanceServiceTests {
	private Logger logger = LoggerFactory.getLogger(BalanceRepositoryTests.class);

	@Autowired
	BalanceService	balanceService;
	
	/**
	 * 잔액리스트 조회서비스 테스트
	 */
	@Test
	public void testGetBalance() {
		ResponseDailyBalance response = balanceService.getDailyBalance("20180801", "20180831", "ASC", 0, 10);
		logger.debug("daily balance list : total count = {}, list count = {}", response.getSummary().getCount(), response.getResultList().size());
		
		assertEquals(response.getSummary().getCount(), 100);
		assertEquals(response.getResultList().size(), 10);
	}

	/**
	 * 거래내역(합계) 조회서비스 테스트
	 */
	@Test
	public void testGetTransactionSummary() {
		ResponseTransaction response = balanceService.getTransactionSummary("20180801", "20180831", "TRNT-0001", 0, 10);
		logger.debug("transaction list : total count = {}, list count = {}", response.getSummary().getCount(), response.getResultList().size());
		
		assertEquals(response.getSummary().getCount(), 6);
		assertEquals(response.getResultList().size(), 6);
	}
}
