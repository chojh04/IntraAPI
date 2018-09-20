package kr.co.kpcard.backoffice.controller.merchant.protocol;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMerchantCheck {
	@ApiParam(value="거래처 ID", required=true)
	private String merchantId;
	
	@ApiParam(value="사업자 번호", required=false)
	private String bizRegNo;
	
	@ApiParam(value="법인 번호", required=false)
	private String corpRegNo;		
}
