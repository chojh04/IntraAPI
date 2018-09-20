package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestApprovalCancellationForm {
	
	/**
	 * 취소할 승인 요청 번호(PK)
	 */
	private Long seq;
	
	/**
	 * 로그인 한 사용자의 사번
	 */
	private String loginEmpId;

}
