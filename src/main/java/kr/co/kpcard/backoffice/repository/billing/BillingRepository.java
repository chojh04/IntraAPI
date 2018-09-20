package kr.co.kpcard.backoffice.repository.billing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.backoffice.repository.billing.mapper.BillingMapper;
import kr.co.kpcard.backoffice.repository.billing.model.BillingEnrollment;
import kr.co.kpcard.backoffice.repository.billing.model.BillingSpecification;
import kr.co.kpcard.billingservice.app.repository.settlement.gs.GsSettlementMapper;


@Repository
public class BillingRepository {
	@Autowired
	BillingMapper billingMapper;
	
	@Autowired
	GsSettlementMapper gsSettlementMapper;
	
	//예비 정산명세서 Count
	public int getbillingsByCount(String merchantId, String serviceId, String serviceType, String dateType, String startDate, String endDate, String billingDuration, String adjustType){
		int resultCount = 0;
		resultCount = billingMapper.getBillingsByCount(merchantId
														,serviceId
														,serviceType
														,dateType
														,startDate
														,endDate
														,billingDuration
														,adjustType);
		return resultCount;
	}
	
	//예비 정산명세서 리스트 조회
	public List<BillingSpecification> getBillings(int offset, int limit, String merchantId, String serviceId, String serviceType, String dateType, String startDate, String endDate, String billingDuration, String excelAllFlag, String adjustType){
		List<BillingSpecification> resultList = billingMapper.getBillings(offset
																		,limit																				
																		,merchantId
																		,serviceId
																		,serviceType
																		,dateType
																		,startDate
																		,endDate
																		,billingDuration
																		,excelAllFlag
																		,adjustType);
		return resultList;
	}
	
	//예비 정산명세서 조회
	public BillingSpecification getBiliing(String seq){
		BillingSpecification resultData = billingMapper.getBiliing(seq);
		
		return resultData;
	}
	
	
	//정산명세서 Count
	public int getRegBillingsCount(String serviceId, String merchantId,String serviceType, String dateType,	String startDate, String endDate, String status, String billingType){
		int resultCount = 0;
		resultCount = billingMapper.getRegBillingsCount(serviceId, merchantId, serviceType, dateType, startDate, endDate, status, billingType);
		return resultCount;
	}
	
	
	//정산명세서 리스트 조회
	public List<BillingEnrollment> getRegBillings(int offset, int limit, String serviceId, String merchantId,String serviceType, String dateType,	String startDate, String endDate, String status, String billingType, String excelAllFalg){
		List<BillingEnrollment> resultList = billingMapper.getRegBillings(offset, limit, serviceId, merchantId, serviceType, dateType, startDate, endDate, status, billingType, excelAllFalg);
		return resultList;
	}
	
	//정산명세서 리스트 조회
		public BillingEnrollment getRegBilling(String seq){
			BillingEnrollment result = billingMapper.getRegBilling(seq);
			return result;
		}
	
		
	//정산명세서 Seq조회
	public String getRegBillingSeq(String nowDate){
		return billingMapper.getRegBillingSeq(nowDate);
	}		
	
	
	//정산명세서 등록
	public boolean regBilling(BillingEnrollment billingEnrollment){
		boolean result = false;
		int resultCount = billingMapper.regBilling(billingEnrollment);
		if(resultCount>0){
			result=true;
		}
		return result;
	}
	
	
	//GS대사 불일치 금액 조회
	public long getGsNotMatchedAmount(String serviceConnId, String startDt, String endDt, String dealType){		
		return gsSettlementMapper.getGsNotMatchedAmount(serviceConnId, startDt, endDt, dealType);
	}
	
	
}
