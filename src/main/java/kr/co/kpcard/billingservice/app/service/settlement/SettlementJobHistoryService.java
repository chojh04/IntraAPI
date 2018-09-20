package kr.co.kpcard.billingservice.app.service.settlement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.settlement.common.SettlementJobHistory;
import kr.co.kpcard.billingservice.app.repository.settlement.common.SettlementJobHistoryMapper;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettleResultCount;

@Service
public class SettlementJobHistoryService {
	
	@Autowired
	private SettlementJobHistoryMapper settlementJobHistoryMapper;
	
	public int jobStart(String svcconnId, String date,String status, String statusDesc)
	{
		SettlementJobHistory settlementJobHistory = new SettlementJobHistory();
		int seq = settlementJobHistoryMapper.selectJobMaxSeq();
		settlementJobHistory.setSvcconnId(svcconnId);
		settlementJobHistory.setWorkDt(date);
		settlementJobHistory.setSeq(seq);
		settlementJobHistory.setStatus(status);
		settlementJobHistory.setStatusDesc(statusDesc);
		return settlementJobHistoryMapper.jobStart(settlementJobHistory);
	}
	
	public int jobUpdate(String svcconnId, String date,String status, String statusDesc)
	{
		SettlementJobHistory settlementJobHistory = new SettlementJobHistory();
		int seq = settlementJobHistoryMapper.selectJobMaxSeq();
		settlementJobHistory.setSvcconnId(svcconnId);
		settlementJobHistory.setWorkDt(date);
		settlementJobHistory.setSeq(seq);
		settlementJobHistory.setStatus(status);
		settlementJobHistory.setStatusDesc(statusDesc);
		return settlementJobHistoryMapper.jobUpdate(settlementJobHistory);		
	}
	
	public int jobUpdateStatus(String svcconnId,String date,String status, String statusDesc)
	{
		SettlementJobHistory settlementJobHistory = new SettlementJobHistory();
		int seq = settlementJobHistoryMapper.selectJobMaxSeq();
		settlementJobHistory.setSvcconnId(svcconnId);
		settlementJobHistory.setWorkDt(date);
		settlementJobHistory.setSeq(seq);
		settlementJobHistory.setStatus(status);
		settlementJobHistory.setStatusDesc(statusDesc);
		return settlementJobHistoryMapper.jobUpdateStatus(settlementJobHistory);			
	}
	
	public int jobInsertSettleResultCount( GsSettleResultCount resultCount) {
		return settlementJobHistoryMapper.jobInsertSettleResult(resultCount);		
	}
}
