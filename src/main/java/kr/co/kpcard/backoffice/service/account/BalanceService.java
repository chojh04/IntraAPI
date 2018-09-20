/**
 * 
 */
package kr.co.kpcard.backoffice.service.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.backoffice.component.account.BalanceDetail;
import kr.co.kpcard.backoffice.component.account.BalanceSummary;
import kr.co.kpcard.backoffice.component.account.TransactionDetail;
import kr.co.kpcard.backoffice.component.account.TransactionSummary;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseDailyBalance;
import kr.co.kpcard.backoffice.controller.account.protocol.ResponseTransaction;
import kr.co.kpcard.backoffice.repository.account.BalanceRepository;

/**
 * 카드잔액관리 서비스
 * @author chris
 *
 */
@Service
public class BalanceService {
	private static Logger logger = LoggerFactory.getLogger(BalanceService.class);

	@Autowired
	private BalanceRepository	balanceRepository;

	/**
	 * 일자별 잔액리스트 조회
	 * @param startDate
	 * @param endDate
	 * @param sort 정렬순서 ASC, DESC
	 * @param offset
	 * @param limit
	 * @return 잔액 상세정보
	 */
	public ResponseDailyBalance getDailyBalance(String startDate, String endDate, String sort, long offset, long limit) {
		ResponseDailyBalance	responseDailyBalance = new ResponseDailyBalance();
		
		BalanceSummary balanceSummary = balanceRepository.getBalanceSummary(startDate, endDate);
		List<BalanceDetail>	balanceList = balanceRepository.getBalanceList(startDate, endDate, sort, offset, limit);
		
		logger.debug("get daily balance : startDate = {}, endDate = {}, count = {}", startDate, endDate, balanceSummary.getCount());
		responseDailyBalance.setSummary(balanceSummary);
		responseDailyBalance.setResultList(balanceList);
		responseDailyBalance.setOffset(offset);
		responseDailyBalance.setLimit(limit);
		
		return responseDailyBalance;
	}

	/**
	 * 기간별 트랜잭션 합계 조회 
	 * @param startDate
	 * @param endDate
	 * @param transactionType 거래구분(TRNT-0001: 충전, TRNT-0002: 결제, TRNT-0003: 환불)
	 * @param offset
	 * @param limit
	 * @return 트랜잭션 종합정보
	 */
	public ResponseTransaction getTransactionSummary(String startDate, String endDate, String transactionType, long offset, long limit) {
		
		ResponseTransaction responseTransaction= new ResponseTransaction();
		
		TransactionSummary transactionSummary = balanceRepository.getTransactionSummary(startDate, endDate, transactionType);
		List<TransactionDetail>	transactionList = balanceRepository.getTransactionList(startDate, endDate, transactionType, offset, limit);
		
		logger.debug("get transaction summary : startDate = {}, endDate = {}, transactionType = {}, count = {}", startDate, endDate, transactionType, transactionSummary.getCount());
		responseTransaction.setSummary(transactionSummary);
		responseTransaction.setResultList(transactionList);
		responseTransaction.setOffset(offset);
		responseTransaction.setLimit(limit);
		
		return responseTransaction;
	}

}
