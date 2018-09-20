package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApprovalDetailInfo {

	/**
	 * 승인 요청 정보
	 */
	private ResponseApprovalInfo approvalInfo;
	
	/**
	 * 승인 요청 내용 백업 데이터(거래처,서비스,경비 등의 데이터)
	 * [수정],[삭제] 승인 요청에 대한 백업 컨텐츠
	 */
	private Object previousContent;

	/**
	 * 승인 요청 내용 데이터(거래처,서비스,경비 등의 데이터)
	 */
	private Object content;

}
