package kr.co.kpcard.backoffice.controller.merchant.protocol;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestGetMetchants{
	@ApiParam(name="limit", value="페이지당 노출 수", required=true)
	private int limit;
	
	@ApiParam(name="offset", value="페이지 번호", required=true)
	private int offset;
	
	@ApiParam(name="name", value="서브가맹점 명")
	private String name;
	
	@ApiParam(name="merchantId", value="가맹점 아이디")
	private String merchantId;
	
	@ApiParam(name="alias", value="서브가맹점 별칭")
	private String alias;
	
	@ApiParam(name="depth", value="서브 가맹점 depth")
	private String depth;
	
	@ApiParam(name="childId", value="하위 서브가맹점 아이디")
	private String childId;
	
	@ApiParam(name="status", value="사용 여부")
	private String status;
	
	@ApiParam(name="bizRegNo", value="사업자 번호")
	private String bizRegNo;
}
