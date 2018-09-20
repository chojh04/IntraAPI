package kr.co.kpcard.backoffice.repository.account.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSalement {
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
	private Date approvalDt;
	private String approvalStatus;
	private String approvalStatusNm;
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
	
	public String getApprovalDt()
	{		
		SimpleDateFormat transForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return transForm.format(this.approvalDt); 
	}
}
