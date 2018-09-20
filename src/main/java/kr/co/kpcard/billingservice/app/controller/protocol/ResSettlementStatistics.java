package kr.co.kpcard.billingservice.app.controller.protocol;

import java.util.List;

import kr.co.kpcard.billingservice.app.repository.settlement.statistics.GsSettlementStatistics;

public class ResSettlementStatistics extends ResBody {
	
	public List<GsSettlementStatistics> resultList;
	
	public ResSettlementStatistics()
	{	
		super(RESULT_CODE_SERVER_ERROR, RESULT_MSG_SERVER_ERROR);
	}
	
	public ResSettlementStatistics(String code, String message){
		super(code, message);
	}

	public void reset()
	{
		super.setCode(RESULT_CODE_SERVER_ERROR);
		super.setMessage(RESULT_MSG_SERVER_ERROR);
	}

	public List<GsSettlementStatistics> getResultList() {
		return resultList;
	}

	public void setResultList(List<GsSettlementStatistics> resultList) {
		this.resultList = resultList;
	}

	@Override
	public String toString() {
		return "ResSettlement [getCode()=" + getCode() + ", getMessage()=" + getMessage() + ", toStringForLog()="
				+ toStringForLog() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
