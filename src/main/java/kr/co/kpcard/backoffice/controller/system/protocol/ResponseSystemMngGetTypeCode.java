package kr.co.kpcard.backoffice.controller.system.protocol;

import java.util.List;

import kr.co.kpcard.backoffice.repository.system.model.SystemMngTypeCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSystemMngGetTypeCode {
	private String message;
	private List<SystemMngTypeCode> resultList;	
}
