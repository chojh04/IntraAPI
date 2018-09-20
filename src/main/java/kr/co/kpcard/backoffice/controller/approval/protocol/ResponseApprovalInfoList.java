package kr.co.kpcard.backoffice.controller.approval.protocol;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 신청,결제 내역 목록 응답 객체
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Getter
@Setter
public class ResponseApprovalInfoList {
	
	private List<ResponseApprovalInfo> resultList;
	private int count;

}
