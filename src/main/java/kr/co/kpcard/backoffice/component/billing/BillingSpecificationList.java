package kr.co.kpcard.backoffice.component.billing;

import kr.co.kpcard.backoffice.repository.billing.model.BillingSpecification;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chris
 * 정산명세서 내역 객체
 * BillingSpecification와 동일 : 일부 Property 이름만 상이함 (통합 필요)
 */
@Getter
@Setter
public class BillingSpecificationList {
	/**
	 * 거래처 아이디
	 */	
	private String submerchantId;
	/**
	 * 거래처명
	 */
	private String name;
	
	/**
	 * 서비스 아이디
	 */
	private String serviceId;
	
	/**
	 * 서비스명 
	 */
	private String serviceName;
	
	/**
	 * 거래구분(충전, 결제, 환불)
	 */
	private String typeName;
	
	/**
	 * 승인금액
	 */
	private String amount;
	/**
	 * 취소금액
	 */
	private String cancelAmount;
	/**
	 * 총금액(승인금액 - 취소금액)
	 */
	private long totalAmount;
	/**
	 * 거래건수(승인건수 + 취소검수)
	 */
	private String cnt;
	/**
	 * 수수료금액
	 */
	private String commision;
	/**
	 * 부가세 굼액
	 */
	private String tax;
	/**
	 * 수수료합계
	 */
	private String commTotal;
	/**
	 * 정산금액
	 */
	private String billingAmount;
	/**
	 * 정산기간(from)
	 */
	private String approvalDtMin;
	/**
	 * 정산기간(to)
	 */
	private String approvalDtMax;
	/**
	 * 정산일자(정산지급일)
	 */
	private String billingDt;
	/**
	 * 부가세타입(부가세포함, 부가세별도)
	 */
	private String taxTypeName;
	/**
	 * 부가세 타입코드
	 */
	private String merchantTaxType;
	/**
	 * 수수료구부(비율정산, 건당)
	 */
	private String commTypeName;
	/**
	 * 수수료구분코드
	 */
	private String merchantCommType;
	/**
	 * 수수료율(금액)
	 */
	private String merchantCommision;
	/**
	 * 입금계좌명
	 */
	private String bankNm;
	/**
	 * 입금계좌번호
	 */
	private String bankAccNo;
	/**
	 * 예금주명
	 */
	private String bankHolder;
	/**
	 * 정산구분(건별, 총액)
	 */
	private String billingCommType;
	/**
	 * 정산구분(1, 7, 15, 31)
	 */
	private String billingDuration;
	/**
	 * 정산구분코드
	 */
	private String billingDurationType;
	
	/**
	 * 할인금액
	 */
	private long dcAmount;
	
	/**
	 * 결제금액
	 */
	private long payAmount;
/*
	public BillingSpecificationList(BillingSpecification spec) {
		this.submerchantId = spec.getSubmerchant_id();
		this.name = spec.getName();
		this.serviceId = spec.getService_id();
		this.serviceName = spec.getServiceName();
		this.typeName = spec.getTypeName();
		this.amount = spec.getAmount();
		this.cancelAmount = spec.getCancelAmount();
		this.totalAmount = spec.getTotalAmount();
		this.cnt = spec.getCnt();
		this.commision = spec.getCommision();
		this.tax = spec.getTax();
		this.commTotal = spec.getCommTotal();
		this.billingAmount = spec.getBillingAmount();
		this.approvalDtMin = spec.getApprovalDtMin();
		this.approvalDtMax = spec.getApprovalDtMax();
		this.billingDt = spec.getBillingDt();
		this.taxTypeName = spec.getTaxTypeName();
		this.merchantTaxType = spec.getMerchant_tax_type();
		this.commTypeName = spec.getCommTypeName();
		this.merchantCommType = spec.getMerchant_comm_type();
		this.merchantCommision = spec.getMerchant_commision();
		this.bankNm = spec.getBank_nm();
		this.bankAccNo = spec.getBank_acc_no();
		this.bankHolder = spec.getBank_holder();
		this.billingCommType = spec.getBilling_comm_type();
		this.billingDuration = spec.getBilling_duration();
		this.billingDurationType = spec.getBilling_duration_type();
		this.dcAmount = spec.getDcAmount();
		this.payAmount = (spec.getDcAmount()>0)? (spec.getTotalAmount()-spec.getDcAmount()) : spec.getTotalAmount();
	}
	*/
	public void setValues(String submerchantId, String name,
			String serviceId, String serviceName, String typeName,
			String amount, String cancelAmount, long totalAmount, String cnt,
			String commision, String tax, String commTotal,
			String billingAmount, String approvalDtMin, String approvalDtMax,
			String billingDt, String taxTypeName, String merchantTaxType,
			String commTypeName, String merchantCommType,
			String merchantCommision, String bankNm, String bankAccNo,
			String bankHolder, String billingCommType, String billingDuration, String billingDurationType) {
		this.submerchantId = submerchantId;
		this.name = name;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.typeName = typeName;
		this.amount = amount;
		this.cancelAmount = cancelAmount;
		this.totalAmount = totalAmount;
		this.cnt = cnt;
		this.commision = commision;
		this.tax = tax;
		this.commTotal = commTotal;
		this.billingAmount = billingAmount;
		this.approvalDtMin = approvalDtMin;
		this.approvalDtMax = approvalDtMax;
		this.billingDt = billingDt;
		this.taxTypeName = taxTypeName;
		this.merchantTaxType = merchantTaxType;
		this.commTypeName = commTypeName;
		this.merchantCommType = merchantCommType;
		this.merchantCommision = merchantCommision;
		this.bankNm = bankNm;
		this.bankAccNo = bankAccNo;
		this.bankHolder = bankHolder;
		this.billingCommType = billingCommType;
		this.billingDuration = billingDuration;
		this.billingDurationType = billingDurationType;
	}
	
}
