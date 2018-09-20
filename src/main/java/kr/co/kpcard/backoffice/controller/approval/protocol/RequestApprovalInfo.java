package kr.co.kpcard.backoffice.controller.approval.protocol;

import io.swagger.annotations.ApiModelProperty;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.component.util.DateUtil;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalForList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestApprovalInfo {	
	@ApiModelProperty(value = "승인 요청 번호", position = 0, dataType="long", required=false, hidden = false)
	private Integer seq = 0;  		//승인 요청 번호	 
	@ApiModelProperty(value = "승인 요청 타입", position = 1, dataType="String", required=false, hidden = false, example="AWRK-0012")
	private String workType;  	//승인 요청 구분
	@ApiModelProperty(value = "승인 요청 Title", position = 2, dataType="String", required=false, hidden = false, example="API request Test-승인요청 등록")
	private String refTitle;  	//승인 요청 제목
	@ApiModelProperty(value = "승인 요청자 ID(사번)", position = 3, dataType="String", required=true, hidden = false, example="160502")
    private String reqEmpId;  	//승인 요청자
	@ApiModelProperty(value = "승인자 ID(사번)", position = 4, dataType="String", required=true, hidden = false, example="160502")
    private String apprEmpId; 	//승인자
	@ApiModelProperty(value = "승인 요청 유형", position = 5, dataType="String", required=false, hidden = false, example=SystemCodeConstant.AREQ_0001)
    private String reqType=SystemCodeConstant.AREQ_0001;  	
	@ApiModelProperty(value= "승인요청 사유", position = 6, dataType="String", required=false, hidden = false, example="승인요청 API Default Request TEST")
    private String reqMemo; 	//요청 사유
	@ApiModelProperty(value = "검색 키워드", position = 7, dataType="String", required=false, hidden = false, example="API,TEST")
    private String keyword;	//승인내역 검색을 위한 key
	@ApiModelProperty(value = "중복승인 Check Key", position = 8, dataType="String", required=false, hidden = false, example="API")
    private String refId;		//중복승인 check를 위한 key
	@ApiModelProperty(value = "승인 요청 정보(JSON String)", position = 9, dataType="String", required=false, example="{\"giftNo\": \"FPS216683\", \"cardNumber\": \"1019195205905270\", \"restrictYN\": \"Y\", \"apiUrl\": \"192.168.5.119:8086/KpcLegacyApiService/admin/v1/card/restrict\", \"insertAdminId\": \"20180622\", \"restrictDesc\": \"API Default Request\"}")
    private String contentData; //승인 후 처리될 data(JsonString)
	@ApiModelProperty(value = "message", position = 10, dataType="long", required=false, hidden = false, example="0")
    private Integer contentSeq = 0; 	//승인 후 처리될 data 등록 번호
}
