package kr.co.kpcard.backoffice.repository.approval.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * KPC_ADMIN 데이터베이스
 * KPC_APPROVAL_CONTENT 테이블 모델
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Getter
@Setter
public class ApprovalContent {
	
	/**
	 * 요청 정보 번호(PK)
	 */
	private Long seq;
	
	/**
	 * 업무 유형
	 */
	
	private String workType;
	
	/**
	 * 생성일자
	 */
	private Date createDate;
	
	private String previousContent;
	
	private String content;
}
