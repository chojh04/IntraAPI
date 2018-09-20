package kr.co.kpcard.backoffice.controller.approval.protocol;

import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalDetail extends ResponseString{

	/**
	 * 승인 요청 정보
	 */
	private ResponseApprovalInfo approvalInfo;
	
	/**
	 * 승인 요청 내용 백업 데이터(거래처,서비스,경비 등의 데이터)
	 * [수정],[삭제] 승인 요청에 대한 백업 컨텐츠
	 */
	private String previousContent;

	/**
	 * 승인 요청 내용 데이터(거래처,서비스,경비 등의 데이터)
	 */
	private String content;

}
