package kr.co.kpcard.backoffice.controller.approval.protocol;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestCancelForm {
	@ApiModelProperty(value="승인취소 할 승인요청 Seq", position=0, required=true)
	@ApiParam(defaultValue = "",value="승인요청 Seq")
	private long apprSeq;
	
	@ApiModelProperty(value="승인자 ID", position=0, required=true)
	@ApiParam(defaultValue = "",value="승인자 ID")
	private String empId;		
}
