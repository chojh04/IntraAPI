package kr.co.kpcard.backoffice.repository.approval.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalKconApproval {
	private String seq;
	private String tmp_seq;
	private String status;
	private String status_nm;
	private String type_code;
	private String type_code_nm;
	private String create_dt;
	private String update_dt;
	private String type_nm;
	private String type_detail_nm;
	private String title;
	private String req_emp_id;
	private String req_emp_nm;
	private String appr_emp_id;
	private String appr_emp_nm;
}