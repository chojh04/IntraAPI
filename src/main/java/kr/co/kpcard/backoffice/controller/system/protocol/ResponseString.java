package kr.co.kpcard.backoffice.controller.system.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseString {	
	@ApiModelProperty(value="return Message", position=0, required=true)
	private String message;	
}
