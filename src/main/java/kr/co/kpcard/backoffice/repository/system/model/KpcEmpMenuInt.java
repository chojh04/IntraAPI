package kr.co.kpcard.backoffice.repository.system.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * KPC_ADMIN 데이터베이스
 * KPC_EMP_MENU_INT 테이블의 모델
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Getter
@Setter
public class KpcEmpMenuInt {

	/**
	 * 사용자별 메뉴 ID 
	 */
	private String empMenuId;
	
	/**
	 * 직원 ID
	 */
	private String empId;
	
	/**
	 * 메뉴 ID
	 */
	private String menuId;
	
	/**
	 * 등록 권한
	 */
	private String insFlag;
	
	/**
	 * 수정 권한
	 */
	private String updFlag;
	
	/**
	 * 삭제 권한
	 */
	private String delFlag;
	
	/**
	 * 조회 권한
	 */
	private String selFlag;
	
	/**
	 * 승인 권한
	 */
	private String apprFlag;
	
	/**
	 * 생성자 
	 */
	private String createId;
	
	/**
	 * 생성일
	 */
	private Date createDate;

	/**
	 * 수정자
	 */
	private String updateId;
	
	/**
	 * 수정일
	 */
	private Date updateDate;

}
