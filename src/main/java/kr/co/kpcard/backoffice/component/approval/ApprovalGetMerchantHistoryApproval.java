package kr.co.kpcard.backoffice.component.approval;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class ApprovalGetMerchantHistoryApproval {
	private String seq;
	private String tmpSeq;
	private String name;
	private String status;
	private String statusNm;
	private String typeCode;
	private String typeCodeNm;
	private String createDt;
	private String updateDt;
	private String reqEmpId;
	private String nameReqEmpNm;
	private String apprEmpId;
	private String apprEmpNm;
	public void setValues(String seq, String tmpSeq,
			String name, String status, String statusNm, String typeCode,
			String typeCodeNm, String createDt, String updateDt,
			String reqEmpId, String nameReqEmpNm, String apprEmpId,
			String apprEmpNm) {
		this.seq = seq;
		this.tmpSeq = tmpSeq;
		this.name = name;
		this.status = status;
		this.statusNm = statusNm;
		this.typeCode = typeCode;
		this.typeCodeNm = typeCodeNm;
		this.createDt = createDt;
		this.updateDt = updateDt;
		this.reqEmpId = reqEmpId;
		this.nameReqEmpNm = nameReqEmpNm;
		this.apprEmpId = apprEmpId;
		this.apprEmpNm = apprEmpNm;
	}
	
}
