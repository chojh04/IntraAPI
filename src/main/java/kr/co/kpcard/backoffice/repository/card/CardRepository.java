package kr.co.kpcard.backoffice.repository.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefundList;
import kr.co.kpcard.backoffice.repository.card.mapper.BalanceRefund;
import kr.co.kpcard.backoffice.repository.card.mapper.CardMapper;

@Repository
public class CardRepository {
	@Autowired
	CardMapper cardMapper;	
	
	public int getCardRefundListCount(RequestCardBalnaceRefundList req){
		int totalCount = cardMapper.getCardRefundListCount(req);
		return totalCount;
	}
	
	public List<BalanceRefund> getCardRefundList(RequestCardBalnaceRefundList req){
		List<BalanceRefund> resultList = cardMapper.getCardRefundList(req);
		return resultList;
	}
	
	public BalanceRefund getCardRefund(long req){
		BalanceRefund result = cardMapper.getCardRefund(req);
		return result;
	}
}
