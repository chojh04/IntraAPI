package kr.co.kpcard.backoffice.repository.billing.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.service.billing.RegistBillingContent;
import kr.co.kpcard.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingEnrollment {
	private String seq;
	private String billingApprSeq;
	private String status;
	private String txnCd;
	private String txnName;
	private String serviceId;
	private String submerchantId;
	private String submerchantName;
	private String billingId;
	private String billingName;
	private String bankCd;
	private String bankName;
	private String bankHolder;
	private String bankAccNo;
	private String startDt;
	private String endDt;
	private String billingDt;
	private String billingDay;
	private String billingDurationCd;
	private String billingDuration;
	private String billingCommTypeCd;
	private String billingCommType;
	private String merchantTaxTypeCd;
	private String merchantTaxType;
	private String merchantCommTypeCd;
	private String merchantCommType;
	private String merchantCommision;
	private long approvalCnt;
	private long cancelCnt;
	private long approvalAmount;
	private long cancelAmount;
	private long notMatchedAmount;
	private long differenceAmount;
	private long etcAmount;
	private long adjustAmount;
	private long totApprovalAmount;
	private long payAmount;
	private long discountAmount;
	private long commisionAmount;
	private long taxAmount;
	private long totCommisionAmount;
	private long billingAmount;
	private String descMemo;
	private Date createDt;
	private Date updateDt;
	private String serviceConnId;
	

	public BillingEnrollment(){}
	
	public BillingEnrollment(RegistBillingContent registBillingContent, BillingSpecification billingSpecification) {
		super();
		this.billingApprSeq = registBillingContent.getSeq();
		this.status = SystemCodeConstant.PROC_0010;
		this.txnCd = billingSpecification.getType();
		this.txnName = billingSpecification.getTypeName();
		this.serviceId = billingSpecification.getServiceId();
		this.submerchantId = billingSpecification.getSubmerchantId();
		this.submerchantName = billingSpecification.getSubmerchantName();
		this.billingId = billingSpecification.getBillingId();
		this.billingName = billingSpecification.getBillingName();
		this.bankCd = billingSpecification.getBankCd();
		this.bankName = billingSpecification.getBankName();
		this.bankHolder = billingSpecification.getBankHolder();
		this.bankAccNo = billingSpecification.getBankAccNo();
		this.startDt = billingSpecification.getApprovalDtMin();
		this.endDt = billingSpecification.getApprovalDtMax();
		this.billingDt = billingSpecification.getBillingDt();
		this.billingDurationCd = billingSpecification.getBillingDurationType();
		this.billingDuration = billingSpecification.getBillingDuration();
		this.billingCommTypeCd = billingSpecification.getBillingCommTypeCd();
		this.billingCommType = billingSpecification.getBillingCommType();
		this.merchantTaxTypeCd = billingSpecification.getMerchantTaxType();
		this.merchantTaxType = billingSpecification.getTaxTypeName();
		this.merchantCommTypeCd = billingSpecification.getMerchantCommType();
		this.merchantCommType = billingSpecification.getCommTypeName();
		this.merchantCommision = registBillingContent.getCommRatio();
		this.approvalCnt = billingSpecification.getApprovalCnt();
		this.cancelCnt = billingSpecification.getCancelCnt();
		this.approvalAmount = registBillingContent.getAmount();
		this.cancelAmount = registBillingContent.getCancelAmount();		
		this.payAmount = registBillingContent.getPayAmount();
		this.discountAmount = registBillingContent.getDcAmount();
		this.commisionAmount = registBillingContent.getCommTotal();
		this.taxAmount = registBillingContent.getTax();				
		this.serviceConnId = billingSpecification.getServiceConnId();
	}
	
	
}	
