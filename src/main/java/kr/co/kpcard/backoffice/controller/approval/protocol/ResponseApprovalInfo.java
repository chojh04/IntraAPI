package kr.co.kpcard.backoffice.controller.approval.protocol;

import kr.co.kpcard.backoffice.component.util.DateUtil;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalForList;
import lombok.Getter;
import lombok.Setter;

/**
 * 승인 정보 응답 객체
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Getter
@Setter
public class ResponseApprovalInfo {

	/**
	 * 요청 번호(PK)
	 */
	private Long seq;
	
	/**
	 * 요청 정보 번호
	 */
	private Long contentSeq;
	
	/**
	 * 업무 유형
	 */
	private String workType;
	private String workTypeName;
	
	/**
	 * 처리 구분
	 */
	private String reqType;
	private String reqTypeName;
	
	/**
	 * 진행 상태
	 */
	private String status;
	private String statusName;
	
	/**
	 * 참조 대상 ID
	 */
	private String refId;
	
	/**
	 * 참조 대상 제목
	 */
	private String refTitle;
	
	/**
	 * 요청 정보 설명
	 */
	private String description;
	
	/**
	 * 신청자 사번
	 */
	private String reqEmpId;
	
	/**
	 * 신청자 이름
	 */
	private String reqEmpName;
	
	/**
	 * 신청일
	 */
	private String reqDate;
	
	/**
	 * 승인 요청 사유
	 */
	private String reqMemo;
	
	/**
	 * 승인자 사번
	 */
	private String apprEmpId;
	
	/**
	 * 승인자 사번
	 */
	private String apprEmpName;
	
	/**
	 * 승인일
	 */
	private String apprDate;
	
	/**
	 * 요청 처리 사유
	 */
	private String apprMemo;
	
	public ResponseApprovalInfo() { }
	
	public ResponseApprovalInfo(ApprovalForList approval) {
		this.seq = approval.getSeq();
		this.contentSeq = approval.getContentSeq();
		this.workType = approval.getWorkType();
		this.workTypeName = approval.getWorkTypeName();
		this.reqType = approval.getReqType();
		this.reqTypeName = approval.getReqTypeName();
		this.status = approval.getStatus();
		this.statusName = approval.getStatusName();
		this.refId = approval.getRefId();
		this.refTitle = approval.getRefTitle();
		this.description = approval.getDescription();
		
		this.reqEmpId = approval.getReqEmpId();
		this.reqEmpName = approval.getReqEmpName();
		this.reqDate = DateUtil.convertDateToString(approval.getReqDate(), "YYYY-MM-dd kk:mm:ss");
		this.reqMemo = approval.getReqMemo();
		
		this.apprEmpId = approval.getApprEmpId();
		this.apprEmpName = approval.getApprEmpName();
		this.apprDate = DateUtil.convertDateToString(approval.getApprDate(), "YYYY-MM-dd kk:mm:ss");
		this.apprMemo = approval.getApprMemo();
	}
	
	public ResponseApprovalInfo(Approval approval) {
		this.seq = approval.getSeq();
		this.contentSeq = approval.getContentSeq();
		this.workType = approval.getWorkType();
		this.workTypeName = approval.getWorkTypeName();
		this.reqType = approval.getReqType();
		this.reqTypeName = approval.getReqTypeName();
		this.status = approval.getStatus();
		this.statusName = approval.getStatusName();
		this.refId = approval.getRefId();
		this.refTitle = approval.getRefTitle();
		this.description = approval.getDescription();
		
		this.reqEmpId = approval.getReqEmpId();
		this.reqEmpName = approval.getReqEmpName();
		this.reqDate = DateUtil.convertDateToString(approval.getReqDate(), "YYYY-MM-dd kk:mm:ss");
		this.reqMemo = approval.getReqMemo();
		
		this.apprEmpId = approval.getApprEmpId();
		this.apprEmpName = approval.getApprEmpName();
		this.apprDate = DateUtil.convertDateToString(approval.getApprDate(), "YYYY-MM-dd kk:mm:ss");
		this.apprMemo = approval.getApprMemo();
	}
}
