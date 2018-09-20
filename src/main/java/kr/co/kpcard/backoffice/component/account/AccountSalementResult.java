package kr.co.kpcard.backoffice.component.account;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountSalementResult {
	private String seq;
	private String summSeq;
	private String submerchantId;
	private String submerchantNm;
	private String serviceId;
	private String serviceNm;
	private String serviceType;
	private String serviceCategory;
	private String svcConnId;
	private String dealDivider;
	private String dealDt;
	private String approvalDt;
	private String approvalStatusNm;
	private String approvalStatus;
	private String approvalNo;
	private String orderNo;
	private String prodType;
	private String prodTypeNm;
	private String prodCd;
	private String prodNm;
	private String saleCnt;
	private String saleAmt;
	private String saleType;
	private String saleMethod;
	private String saleMethodName;
	private String storeCd;
	private String storeNm;
	private String posNo;
	private String cpId;
	private String cpUserId;
	private String cpUserIp;
	private String merchantCommType;
	private String merchantCommision;
	private String merchantTaxType;
	private String merchantTax;
	private String kpcCommType;
	private String kpcCommision;
	private String kpcTax_type;
	private String kpcTax;
	private String addCommType01;
	private String addCommision01;
	private String addTaxType01;
	private String addTax01;
	private String addCommType02;
	private String addCommision02;
	private String addTaxType02;
	private String addTax02;
	private String billingSum;
	private String desc01;
	private String desc02;
	public void setValues(String seq, String summSeq, String submerchantId,
			String submerchantNm, String serviceId, String serviceNm,
			String serviceType, String serviceCategory, String svcConnId,
			String dealDivider, String dealDt, String approvalDt,
			String approvalStatusNm, String approvalStatus, String approvalNo,
			String orderNo, String prodType, String prodTypeNm, String prodCd,
			String prodNm, String saleCnt, String saleAmt, String saleType,
			String saleMethod, String saleMethodName, String storeCd,
			String storeNm, String posNo, String cpId, String cpUserId,
			String cpUserIp, String merchantCommType, String merchantCommision,
			String merchantTaxType, String merchantTax, String kpcCommType,
			String kpcCommision, String kpcTax_type, String kpcTax,
			String addCommType01, String addCommision01, String addTaxType01,
			String addTax01, String addCommType02, String addCommision02,
			String addTaxType02, String addTax02, String billingSum,
			String desc01, String desc02) {
		this.seq = seq;
		this.summSeq = summSeq;
		this.submerchantId = submerchantId;
		this.submerchantNm = submerchantNm;
		this.serviceId = serviceId;
		this.serviceNm = serviceNm;
		this.serviceType = serviceType;
		this.serviceCategory = serviceCategory;
		this.svcConnId = svcConnId;
		this.dealDivider = dealDivider;
		this.dealDt = dealDt;
		this.approvalDt = approvalDt;
		this.approvalStatusNm = approvalStatusNm;
		this.approvalStatus = approvalStatus;
		this.approvalNo = approvalNo;
		this.orderNo = orderNo;
		this.prodType = prodType;
		this.prodTypeNm = prodTypeNm;
		this.prodCd = prodCd;
		this.prodNm = prodNm;
		this.saleCnt = saleCnt;
		this.saleAmt = saleAmt;
		this.saleType = saleType;
		this.saleMethod = saleMethod;
		this.saleMethodName = saleMethodName;
		this.storeCd = storeCd;
		this.storeNm = storeNm;
		this.posNo = posNo;
		this.cpId = cpId;
		this.cpUserId = cpUserId;
		this.cpUserIp = cpUserIp;
		this.merchantCommType = merchantCommType;
		this.merchantCommision = merchantCommision;
		this.merchantTaxType = merchantTaxType;
		this.merchantTax = merchantTax;
		this.kpcCommType = kpcCommType;
		this.kpcCommision = kpcCommision;
		this.kpcTax_type = kpcTax_type;
		this.kpcTax = kpcTax;
		this.addCommType01 = addCommType01;
		this.addCommision01 = addCommision01;
		this.addTaxType01 = addTaxType01;
		this.addTax01 = addTax01;
		this.addCommType02 = addCommType02;
		this.addCommision02 = addCommision02;
		this.addTaxType02 = addTaxType02;
		this.addTax02 = addTax02;
		this.billingSum = billingSum;
		this.desc01 = desc01;
		this.desc02 = desc02;
	}
}
