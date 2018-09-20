package kr.co.kpcard.backoffice.repository.approval.model;

import java.util.Date;

import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Approval {
	
	
	private Long seq;	
	private Long contentSeq;
	
	private String workType;	
	private String workTypeName;
	
	private String reqType = SystemCodeConstant.AREQ_0001;
	private String reqTypeName;
	
	private String status;
	private String statusName;
	
	private String refId;	
	private String refTitle;
	
	private String description;
	private String keyword;
	
	private String reqEmpId;
	private String reqEmpName;
	
	private Date reqDate;
	private String reqMemo;
	
	private String apprEmpId;
	private String apprEmpName;
	
	private Date apprDate;
	private String apprMemo;
	
	private Date createDate;
	private Date updateDate;

	public void setDefaultValue(RequestApprovalInfo req)
	{
		this.workType = req.getWorkType();	
		this.refTitle = req.getRefTitle();
		this.keyword = req.getKeyword();
		this.reqEmpId = req.getReqEmpId();
		this.reqMemo = req.getReqMemo();
		this.apprEmpId = req.getApprEmpId();
		this.refId = req.getRefId();
		this.seq = (req.getSeq()!=null && !req.getSeq().equals("") && req.getSeq()>0 )? (long)req.getSeq() : 0 ;
		this.contentSeq = (req.getContentSeq()!=null && !req.getContentSeq().equals("") && req.getContentSeq()>0 )? (long)req.getContentSeq() : 0 ;
		this.reqType = req.getReqType();
		this.setStatus(SystemCodeConstant.ARST_0001);
	}
}
