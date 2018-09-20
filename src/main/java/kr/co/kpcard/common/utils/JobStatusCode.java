package kr.co.kpcard.common.utils;

public interface JobStatusCode {
	
	public static final int LOOPCNT = 1000;
	public static final String GSPOP = "GSPOP";
	public static final String GSPOINT = "GSPOINT";
	public static final String GSSM = "GSSM";
	public static final String GSRETAIL = "GSRETAIL";
	public static final String POPCARD = "POPCARD";
	public static final String HAPPYMONEY = "HAPPYMONEY";
	public static final String HAPPYCASH = "HAPPYCASH";
	public static final String GSHB = "GSHB";
	
	
	public static final String JOB_WORKING_CODE = "100";
	public static final String JOB_SETTLE_INS_END_CODE = "200";
	public static final String JOB_FILE_UPLOAD_START_CODE = "300";
	public static final String JOB_FILE_UPLOAD_END_CODE = "400";
	public static final String JOB_FILE_DOWNLOAD_START_CODE = "500";
	public static final String JOB_FILE_DOWNLOAD_END_CODE = "600";
	public static final String JOB_DATA_NOTFOUND_CODE = "700";
	public static final String JOB_END_CODE     = "800";
	public static final String JOB_ERROR_CODE   = "999";
	
	public static final String JOB_SETTLE_COMPARE_START   = "130";
	public static final String JOB_SETTLE_COMPARE_END   = "149";
	
	public static final String JOB_SETTLE_FAIL_RE_COMPARE_START   = "150";
	public static final String JOB_SETTLE_FAIL_RE_COMPARE_END   = "169";
	public static final String JOB_SETTLE_CANCEL_DATA_SUCCESS_START   = "151";
	public static final String JOB_SETTLE_CANCEL_DATA_SUCCESS_END   = "168";	
	
	public static final String JOB_WORKING_MSG = "배치 작업(대사) 시작";
	public static final String JOB_SETTLE_INS_END_MSG = "대사 대상 추가 종료";
	public static final String JOB_DATA_NOTFOUND_MSG = "대사 자료 없음";
	public static final String JOB_END_MSG     = "배치 작업(대사) 종료";
	
	public static final String JOB_FILE_DOWNLOAD_START_MSG  = "파일 다운로드 시작";
	public static final String JOB_FILE_DOWNLOAD_END_MSG  = "파일 다운로드 종료";
	
	public static final String JOB_FILE_UPLOAD_START_MSG = "파일 업로드 시작";
	public static final String JOB_FILE_UPLOAD_END_MSG = "파일 업로드 종료";
	
	public static final String JOB_SETTLE_COMPARE_START_MSG   = "대사 비교 작업 시작";
	public static final String JOB_SETTLE_COMPARE_END_MSG   = "대사 비교 작업 종료";
	
	public static final String JOB_SETTLE_FAIL_RE_COMPARE_START_MSG   = "대사 비교 재작업 시작";
	public static final String JOB_SETTLE_FAIL_RE_COMPARE_END_MSG   = "대사 비교 재작업 종료";	
	
	public static final String JOB_SETTLE_CANCEL_DATA_SUCCESS_START_MSG   = "망취소 데이터 성공 처리 시작";
	public static final String JOB_SETTLE_CANCEL_DATA_SUCCESS_END_MSG   = "망취소 데이터 성공 처리 종료";

}
