package kr.co.kpcard.backoffice.repository.approval.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 요청에 대한 승인자 객체
 * Created by @author : MinWook on 2018. 5. 30.
 *
 */
@Getter
@Setter
public class Approver {

	/** 사번 */
	private String empId;
	/** 이름 */
	private String name;
	/** 이메일 */
	private String email;
	/** 메뉴 Id */
	private String menuId;
	/** 메뉴 Id */
	private String menuName;
}
