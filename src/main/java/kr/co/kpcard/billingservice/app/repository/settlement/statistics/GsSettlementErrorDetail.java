package kr.co.kpcard.billingservice.app.repository.settlement.statistics;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GsSettlementErrorDetail {
	private String jobDivider;
    private String saleDt;
    private String storeCd;
    private String storeNm;
    private String dealType;
    private String dealDivider;
    private String dealNo;
    private String approvalNo;
    private String approvalDt;
    private String approvalTime;
    private String dealAmt;
    private String cardNo;
    private String statusNm;
    private String statusDesc;
    private String cancelMemo;
    
}
