package kr.co.kpcard.backoffice.component.approval;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalGetKConApproval {
	private String seq;
	private String tmpSeq;
	private String status;
	private String statusNm;
	private String typeCode;
	private String typeCodeNm;
	private String createDt;
	private String updateDt;
	private String typeNm;
	private String typeDetailNm;
	private String title;
	private String reqEmpId;
	private String nameReqEmpNm;
	private String apprEmpId;
	private String apprEmpNm;
	public void setValues(String seq, String tmpSeq, String status,
			String statusNm, String typeCode, String typeCodeNm,
			String createDt, String updateDt, String typeNm,
			String typeDetailNm, String title, String reqEmpId,
			String nameReqEmpNm, String apprEmpId, String apprEmpNm) {
		this.seq = seq;
		this.tmpSeq = tmpSeq;
		this.status = status;
		this.statusNm = statusNm;
		this.typeCode = typeCode;
		this.typeCodeNm = typeCodeNm;
		this.createDt = createDt;
		this.updateDt = updateDt;
		this.typeNm = typeNm;
		this.typeDetailNm = typeDetailNm;
		this.title = title;
		this.reqEmpId = reqEmpId;
		this.nameReqEmpNm = nameReqEmpNm;
		this.apprEmpId = apprEmpId;
		this.apprEmpNm = apprEmpNm;
	}
	
}
