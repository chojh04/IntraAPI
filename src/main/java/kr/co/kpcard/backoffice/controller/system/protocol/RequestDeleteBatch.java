package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDeleteBatch {
	private String seq;
	private String reqId;			
}
