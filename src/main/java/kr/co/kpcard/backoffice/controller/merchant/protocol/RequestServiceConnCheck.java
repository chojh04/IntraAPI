package kr.co.kpcard.backoffice.controller.merchant.protocol;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestServiceConnCheck {
	@ApiParam(value="서비스 ID", required=true)
	private String serviceId;
	
	@ApiParam(value="서비스연동 ID", required=true)
	private String svcConnId;		
}
