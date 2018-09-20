package kr.co.kpcard.backoffice.controller.approval.protocol;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 요청 승인반려 폼
 * Created by @author : MinWook on 2018. 5. 25.
 *
 */
@Setter
@Getter
public class RequestApprovalRejectionForm {

	/** KPC_EL_APPR 테이블의 시퀀스 목록 **/
	private Set<Long> rejectApprSeqList;
	/** 사번 **/
	private String apprEmpId;
	/** 반려 사유 **/
	private String apprMemo;
	
}
