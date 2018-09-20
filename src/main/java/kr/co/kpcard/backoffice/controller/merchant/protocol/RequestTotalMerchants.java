package kr.co.kpcard.backoffice.controller.merchant.protocol;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTotalMerchants {
	@ApiParam(value="페이지당 노출 수", required=true)
	private int limit;
	
	@ApiParam(value="페이지 번호", required=true)
	private int offset;
	
	@ApiParam(value="페이지 번호", required=false)
	private String name;
	
	@ApiParam(value="대표거래처 ID", required=false)
	private String representId;
	
	@ApiParam(value="서브가맹점 별칭", required=false)
	private String alias;
	
	@ApiParam(value="사용 여부", required=false)
	private String status;
	
	@ApiParam(value="사업자 번호", required=false)
	private String bizRegNo;
}
