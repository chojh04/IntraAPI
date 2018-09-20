/**
 * 
 */
package kr.co.kpcard.backoffice.repository.account;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.BalanceSummary;
import kr.co.kpcard.backoffice.component.account.TransactionDetail;
import kr.co.kpcard.backoffice.component.account.TransactionSummary;
import kr.co.kpcard.billingservice.app.BillingServiceApplication;

/**
 * 카드 잔액조회 Repository JUnit Test
 * @author chris
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingServiceApplication.class)
public class BalanceRepositoryTests {
	private Logger logger = LoggerFactory.getLogger(BalanceRepositoryTests.class);

	@Autowired
	BalanceRepository	balanceRepository;
	
	/**
	 * 일별 잔액조회 쿼리 테스트
	 */
	@Test
	public void testGetBalanceList() {
		List<BalanceDetail> list = balanceRepository.getBalanceList("20180801", "20180831", "ASC", 0, 10);
		
		int count = list.size();
		int idx = 0;
		logger.info("balance list count : {}", count);
		for(BalanceDetail detail : list){
			logger.debug("[{}/{}] balanceDate = {}, prevBalance = {}, curBalance = {}", ++idx, count, detail.getBalanceDate(), detail.getPrevBalance(), detail.getBalance());
		}
		assertEquals(list.size(), 10);
	}

	/**
	 * 잔액조회 요약정보
	 */
	@Test
	public void testGetBalanceSummary() {
		BalanceSummary balanceSummary = balanceRepository.getBalanceSummary("20180801", "20180831");
		
		logger.debug("balance summary : count = {}, prevBalance = {}, balance = {}", balanceSummary.getCount(), balanceSummary.getPrevBalance(), balanceSummary.getBalance());
		assertEquals(balanceSummary.getCount(), 19);
	}
	
	/**
	 * 기간별, 거래별 트랜잭션 쿼리 테스트
	 */
	@Test
	public void testGetTransactionList() {
		List<TransactionDetail> list = balanceRepository.getTransactionList("20180801", "20180831", "TRNT-0001", 0, 10);
		
		int count = list.size();
		int idx = 0;
		logger.info("transaction list count : {}", count);
		for(TransactionDetail detail : list){
			logger.debug("[{}/{}] merchantId = {}, merchantName = {}, TransactionAmount = {}", ++idx, count, detail.getMerchantId(), detail.getServiceId(), detail.getTransactionSum());
		}
		assertEquals(list.size(), 6);
	}
	
	@Test
	public void testGetTransactionSummary() {
		TransactionSummary transactionSummary = balanceRepository.getTransactionSummary("20180801", "20180831", "TRNT-0001");
		
		logger.debug("transaction summary : count = {}", transactionSummary.getCount());
		assertEquals(transactionSummary.getCount(), 6);
	}
	
}
