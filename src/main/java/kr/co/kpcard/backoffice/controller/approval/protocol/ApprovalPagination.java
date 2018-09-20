package kr.co.kpcard.backoffice.controller.approval.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 목록 페이징 객체
 * Created by @author : MinWook on 2018. 6. 18.
 *
 */
@Getter
@Setter
public class ApprovalPagination {
	
	private String workType;
	private String reqType;
	private String status;
	private String keyword;
	
	private String reqEmpName;
	private String apprEmpName;
	
	private String searchDateOrderType;
	private String orderType;
	
	private String loginEmpId;
	
	private String searchDateType;
	private String startDate;
	private String endDate;
	
	private long offset;
	private long limit;
	
}
