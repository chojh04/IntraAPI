package kr.co.kpcard.backoffice.controller.approval.protocol;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestApprovalForm {
	@ApiModelProperty(value="승인 리스트", position=0, required=true)
	private List<Approval> approvalList;
	
	@ApiModelProperty(value="승인자 ID", position=0, required=true)
	@ApiParam(defaultValue = "",value="승인자 ID")
	private String empId;		
}
