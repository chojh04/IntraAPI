package kr.co.kpcard.billingservice.app.controller.protocol;

import java.util.List;

import kr.co.kpcard.billingservice.app.repository.settlement.common.Summary;
import kr.co.kpcard.billingservice.app.repository.settlement.statistics.GsSettlementErrorDetail;

public class ResSettlementStatisticsErrorDetail extends ResBody {
	
	public List<GsSettlementErrorDetail> resultList;
	
	public Summary summary;
	
	public ResSettlementStatisticsErrorDetail()
	{	
		super(RESULT_CODE_SERVER_ERROR, RESULT_MSG_SERVER_ERROR);
	}
	
	public ResSettlementStatisticsErrorDetail(String code, String message){
		super(code, message);
	}

	public void reset()
	{
		super.setCode(RESULT_CODE_SERVER_ERROR);
		super.setMessage(RESULT_MSG_SERVER_ERROR);
	}

	public List<GsSettlementErrorDetail> getResultList() {
		return resultList;
	}

	public void setResultList(List<GsSettlementErrorDetail> resultList) {
		this.resultList = resultList;
	}
	
	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "ResSettlement [getCode()=" + getCode() + ", getMessage()=" + getMessage() + ", toStringForLog()="
				+ toStringForLog() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
