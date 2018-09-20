package kr.co.kpcard.backoffice.service.system;

import kr.co.kpcard.backoffice.repository.system.model.Login;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResIsLoginable {
	private String loginStatus;
	private Login login;
}
