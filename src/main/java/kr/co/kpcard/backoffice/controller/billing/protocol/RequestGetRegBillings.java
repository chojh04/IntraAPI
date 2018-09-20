package kr.co.kpcard.backoffice.controller.billing.protocol;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import kr.co.kpcard.backoffice.component.billing.BillingSpecificationList;
import kr.co.kpcard.backoffice.component.billing.BillingSpecificationSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestGetRegBillings {
	@ApiModelProperty(value="거래처 ID", position=0, required=false)
	@ApiParam(defaultValue = "MC-000054",value="거래처 ID")
	private String merchantId = "";
	
	@ApiModelProperty(value="거래처 서비스 ID", position=1, required=false)
	@ApiParam(defaultValue="SB-000303", value="거래처 서비스 ID")
	private String serviceId = "";
	
	@ApiModelProperty(value="거래 구분(전체, 충전, 결제, 환불)", position=2, required=false)
	@ApiParam(defaultValue="", value="거래 구분(전체, 충전, 결제, 환불)",allowableValues="TRNT-0001,TRNT-0002,TRNT-0003")
	private String serviceType = ""; 
	
	@ApiModelProperty(position=3, required=false, notes="")
	@ApiParam(defaultValue="term", value="일자구분(정산기간, 정산지급일)", allowableValues="term, date")
	private String dateType = "";
	
	@ApiModelProperty(value="조회 시작일(yyyyMMdd)", position=4, required=true, example="20180801")
	@ApiParam(defaultValue="20180801", value="정산 시작일")
	private String startDate;
	
	@ApiModelProperty(value="조회 종료일(yyyyMMdd)", position=5, required=true, example="20180831")
	@ApiParam(defaultValue="20180831", value="정산 종료일")
	private String endDate;
	
	@ApiModelProperty(value="정산 방식", example="", position=6, required=false)
	@ApiParam(defaultValue="", value="전체, 입금, 송금", allowableValues="TRNT-0001,TRNT-0002")
	private String billingType = ""; // code : BILC(전체, 1, 7, 15, 30(31))	
	
	@ApiModelProperty(position=7, required=false, example="0")
	@ApiParam(defaultValue="", value="진행상태", allowableValues="PROC-0010,PROC-0040,PROC-0050")
	private String status;
	
	@ApiModelProperty(position=7, required=true, example="0")
	@ApiParam(defaultValue="0", value="엑셀 출력 여부")
	private String excelAllFlag;
	
	@ApiModelProperty(value="페이지당 index", position=8, required=true, example="0")
	@ApiParam(defaultValue="0", value="페이지 index")
	private int offset;
	
	@ApiModelProperty(value="페이지당 노출 수", position=9, required=true)
	@ApiParam(defaultValue="5", allowableValues="5,10,20")
	private int limit;	
	
	public String toString(){
		return String.format("RequestParam["
							+ " merchantId : %s"
							+ " ,serviceId : %s"
							+ " ,serviceType : %s"
							+ " ,dateType : %s"
							+ " ,startDate : %s"
							+ " ,endDate : %s"
							+ " ,billingType : %s"
							+ " ,excelAllFlag : %s"
							+ " ,offset : %s"
							+ " ,limit : %s]"
							,		 
							merchantId
							, serviceId
							, serviceType
							, dateType
							, startDate
							, endDate
							, billingType
							, excelAllFlag
							, offset
							, limit);					
	}
}
