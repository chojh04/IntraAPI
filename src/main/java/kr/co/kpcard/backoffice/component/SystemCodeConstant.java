package kr.co.kpcard.backoffice.component;

/**
 * 백오피스 시스템 코드
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
public class SystemCodeConstant {

	
	//ARST-승인 상태구분-------------------------------
	/** Approval Status(승인 상태구분 - 최종 승인대기) */
	public static final String ARST_0001 = "ARST-0001";
	/** Approval Status(승인 상태구분 - 최종 승인완료) */
	public static final String ARST_0002 = "ARST-0002";
	/** Approval Status(승인 상태구분 - 최종 승인반려) */
	public static final String ARST_0003 = "ARST-0003";
	/** Approval Status(승인 상태구분 - 승인 요청취소) */
	public static final String ARST_0004 = "ARST-0004";
	/** Approval Status(승인 상태구분 - 1차 승인대기) */
	public static final String ARST_0005 = "ARST-0005";
	/** Approval Status(승인 상태구분 - 1차 승인완료) */
	public static final String ARST_0006 = "ARST-0006";
	/** Approval Status(승인 상태구분 - 1차 승인반려) */
	public static final String ARST_0007 = "ARST-0007";
	
	//AWRK-승인 업무구분-------------------------------
	/** Work Type(승인 업무구분 - 대표 거래처) */
	public static final String AWRK_0001 = "AWRK-0001";
	/** Work Type(승인 업무구분 - 일반 거래처) */
	public static final String AWRK_0002 = "AWRK-0002";
	/** Work Type(승인 업무구분 - 서비스 정보) */
	public static final String AWRK_0003 = "AWRK-0003";
	/** Work Type(승인 업무구분 - 정산 정보) */
	public static final String AWRK_0004 = "AWRK-0004";
	/** Work Type(승인 업무구분 - 충전권) */
	public static final String AWRK_0005 = "AWRK-0005";
	/** Work Type(승인 업무구분 - 정산명세서) */
	public static final String AWRK_0006 = "AWRK-0006";
	/** Work Type(승인 업무구분 - 경비) */
	public static final String AWRK_0007 = "AWRK-0007";
	/** Work Type(승인 업무구분 - 정산 수수료 정보) */
	public static final String AWRK_0008 = "AWRK-0008";
	/** Work Type(승인 업무구분 - KCON 상품) */
	public static final String AWRK_0009 = "AWRK-0009";
	/** Work Type(승인 업무구분 - KCON 쿠폰) */
	public static final String AWRK_0010 = "AWRK-0010";
	/** Work Type(승인 업무구분 - POP 잔액환불) */
	public static final String AWRK_0011 = "AWRK-0011";
	/** Work Type(승인 업무구분 - POP 사용제한해제) */
	public static final String AWRK_0012 = "AWRK-0012";

	//AREQ-승인 처리구분--------------------------------
	/** Request Type(승인 처리구분 - 등록 승인 요청) */
	public static final String AREQ_0001 = "AREQ-0001";
	/** Request Type(승인 처리구분 - 수정 승인 요청) */
	public static final String AREQ_0002 = "AREQ-0002";
	/** Request Type(승인 처리구분 - 삭제 승인 요청) */
	public static final String AREQ_0003 = "AREQ-0003";
	/** Request Type(승인 처리구분 - 연장 승인 요청) */
	public static final String AREQ_0004 = "AREQ-0004";
	/** Request Type(승인 처리구분 - 사용해제 승인 요청) */
	public static final String AREQ_0005 = "AREQ-0005";
	
	
	//MENU-메뉴 ID--------------------------------
	/** MENU-ID (거래처 관리) */
	public static final String MENU_0001 = "MENU-0001";
	/** MENU-ID (카드 관리) */
	public static final String MENU_0002 = "MENU-0002";
	/** MENU-ID (쿠폰 관리) */
	public static final String MENU_0003 = "MENU-0003";
	/** MENU-ID (매출 관리) */
	public static final String MENU_0004 = "MENU-0004";
	/** MENU-ID (대사 관리) */
	public static final String MENU_0005 = "MENU-0005";
	/** MENU-ID (정산 내역 관리) */
	public static final String MENU_0006 = "MENU-0006";
	/** MENU-ID (회계 등록 관리) */
	public static final String MENU_0007 = "MENU-0007";
	/** MENU-ID (시스템 관리) */
	public static final String MENU_0008 = "MENU-0008";
	/** MENU-ID (승인 관리) */
	public static final String MENU_0009 = "MENU-0009";
	
	
	/** 쿠폰 상태 타입 - 발행 */
	public static final String CONS_0001 = "CONS-0001";
	/** 쿠폰 상태 타입 - 발행 전체취소 */
	public static final String CONS_0002 = "CONS-0002";
	/** 쿠폰 상태 타입 - 발행 망취소 */
	public static final String CONS_0003 = "CONS-0003";
	
	/*프로세스 진행 상태*/
	/*신청*/
	public static final String PROC_0010 = "PROC-0010";
	/*대기*/
	public static final String PROC_0020 = "PROC-0020";
	/*취소*/
	public static final String PROC_0030 = "PROC-0030";
	/*완료*/
	public static final String PROC_0040 = "PROC-0040";
	/*불가*/
	public static final String PROC_0050 = "PROC-0050";
	
	
	
}
