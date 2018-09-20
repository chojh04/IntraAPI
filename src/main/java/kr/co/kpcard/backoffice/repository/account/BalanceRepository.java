/**
 * 
 */
package kr.co.kpcard.backoffice.repository.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.BalanceSummary;
import kr.co.kpcard.backoffice.component.account.TransactionDetail;
import kr.co.kpcard.backoffice.component.account.TransactionSummary;
import kr.co.kpcard.backoffice.repository.account.mapper.BalanceMapper;

/**
 * 카드잔액관리 리포지토리
 * @author chris
 *
 */
@Repository
public class BalanceRepository {
	private static Logger logger = LoggerFactory.getLogger(BalanceRepository.class);

	@Autowired
	private BalanceMapper balanceMapper;
	
	/**
	 * 잔액 조회(기간별 Sum)
	 * @param startDate
	 * @param endDate
	 * @return 잔액요약정보
	 */
	public BalanceSummary getBalanceSummary(String startDate, String endDate) {
		BalanceSummary	balanceSummary;
		
		balanceSummary = balanceMapper.getBalanceSummary(startDate, endDate);
		balanceSummary.setStartDate(startDate);
		balanceSummary.setEndDate(endDate);
		logger.debug("get balance summary : startDate = {}, endDate = {}, count = {}", startDate, endDate, balanceSummary.getCount());

		return balanceSummary;
	}
	
	/**
	 * 잔액 일자별 리스트
	 * @param startDate
	 * @param endDate
	 * @param sort 정렬(ASC, DESC)
	 * @param offset
	 * @param limit
	 * @return 일자별 리스트
	 */
	public List<BalanceDetail> getBalanceList(String startDate, String endDate, String sort, long offset, long limit) {
		List<BalanceDetail>	balanceList;
		
		balanceList = balanceMapper.getDailyBalanceList(startDate, endDate, sort, offset, limit);
		logger.debug("get balance list : startDate = {}, endDate = {}, count = {}", startDate, endDate, balanceList.size());
		
		return balanceList;
	}

	/**
	 * 거래내역 조회(기간별, 트랜잭션별 Sum)
	 * @param startDate
	 * @param endDate
	 * @param transactionType
	 * @return 거래내역 요약정보
	 */
	public TransactionSummary getTransactionSummary(String startDate, String endDate, String transactionType) {
		TransactionSummary	transactionSummary;
		
		transactionSummary = balanceMapper.getTransactionSummary(startDate, endDate, transactionType);
		transactionSummary.setStartDate(startDate);
		transactionSummary.setEndDate(endDate);
		transactionSummary.setTransactionType(transactionType);
		logger.debug("get transaction summary : startDate = {}, endDate = {}, transactionType = {}, count = {}", startDate, endDate, transactionType, transactionSummary.getCount());

		return transactionSummary;
	}
	
	/**
	 * 거래별 내역 리스트
	 * @param startDate
	 * @param endDate
	 * @param transactionType
	 * @param offset
	 * @param limit
	 * @return list of TransactionDetail
	 */
	public List<TransactionDetail> getTransactionList(String startDate, String endDate, String transactionType, long offset, long limit) {
		List<TransactionDetail>	transactionList;
		
		transactionList = balanceMapper.getTransactionList(startDate, endDate, transactionType, offset, limit);
		logger.debug("get transaction list : startDate = {}, endDate = {}, transactionType = {}, count = {}", startDate, endDate, transactionType, transactionList.size());

		return transactionList;
	}

}
