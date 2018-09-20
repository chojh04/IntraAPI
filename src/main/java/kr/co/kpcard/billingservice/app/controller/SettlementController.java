package kr.co.kpcard.billingservice.app.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kpcard.billingservice.app.controller.protocol.ResSettlement;
import kr.co.kpcard.billingservice.app.controller.protocol.ResSettlementStatistics;
import kr.co.kpcard.billingservice.app.controller.protocol.ResSettlementStatisticsErrorDetail;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;
import kr.co.kpcard.billingservice.app.service.settlement.GsPointSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSettlementStatisticsService;
import kr.co.kpcard.billingservice.app.service.settlement.GsSmSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.GshbSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.HappyCashSettlementService;
import kr.co.kpcard.billingservice.app.service.settlement.SettlementJobHistoryService;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.JobStatusCode;

@Controller
public class SettlementController implements ResultCode{
	
	private static Logger logger = LoggerFactory.getLogger(SettlementController.class);
	
	private static final String URI_V1_SETTLEMENT = "/billingService/v1/settlement";
	private static final String URI_V1_SETTLEMENT_UPLOAD = "/billingService/v1/settlement/upload";
	private static final String URI_V1_SETTLEMENT_DOWNLOAD = "/billingService/v1/settlement/download";
	private static final String URI_V1_SETTLEMENT_GS_STATISTICS = "/billingService/v1/settlement/gsStatistics";
	private static final String URI_V1_SETTLEMENT_HM_STATISTICS = "/billingService/v1/settlement/hmStatistics";
	private static final String URI_V1_SETTLEMENT_GS_ERROR_DETAIL = "/billingService/v1/settlement/gsStatistics/errorDetail";
	private static final String URI_V1_SETTLEMENT_HM_ERROR_DETAIL = "/billingService/v1/settlement/hmStatistics/errorDetail";
	private static final String URI_V1_SETTLEMENT_GS_ERROR_DETAIL_ALL = "/billingService/v1/settlement/gsStatistics/errorDetailAll";
	private static final String URI_V1_SETTLEMENT_GS_INCONSISTENCY= "/billingService/v1/settlement/gsStatistics/inconsistency";
	private static final String URI_V1_SETTLEMENT_HM_INCONSISTENCY= "/billingService/v1/settlement/hmStatistics/inconsistency";
	
	@Autowired
	GsPointSettlementService gsPointSettlementService;
	@Autowired
	GsSettlementService gsDailySettlementService;
	@Autowired
	GsSettlementStatisticsService gsSettlementStatisticsService;
	@Autowired
	GsSmSettlementService gsSmSettlementService;
	@Autowired
	GshbSettlementService gshbSettlementService;
	@Autowired
	HappyCashSettlementService happyCashSettlementService;
	/**
	 * GS대사 자료 비교
	 * @param jobDivider
	 * @param settleDate
	 * @return
	 */
	@RequestMapping(value = URI_V1_SETTLEMENT, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResSettlement postGsSettlement(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="settleDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String settleDate // 대사일자
			){
		ResSettlement resSettlement = new ResSettlement();
		resSettlement.setCode(ResultCode.RESULT_CODE_SUCCESS);
		resSettlement.setMessage(ResultCode.RESULT_MSG_SUCCESS);
		try{
			logger.info("JOB_DIVIDER:"+jobDivider);
			Date workDate = DateUtil.createDate(settleDate,DateUtil.DATE_FORMAT);
			int year = DateUtil.getYear(workDate);
			int month = DateUtil.getMonth(workDate);
			int day = DateUtil.getDay(workDate);
			if (JobStatusCode.GSPOP.equals(jobDivider)){
				gsDailySettlementService.settlement(year, month, day);
			} else if (JobStatusCode.GSSM.equals(jobDivider)){
				gsSmSettlementService.settlement(year, month, day);		
			} else if (JobStatusCode.GSPOINT.equals(jobDivider)){
				gsPointSettlementService.gsPointDailySettlement(year, month, day);
			} else if (JobStatusCode.GSHB.equals(jobDivider)){
				gshbSettlementService.settlement(year, month, day);
			} else {
				resSettlement.setCode(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
				resSettlement.setMessage(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
			}
		} catch(Exception e){
			logger.error("postGsSettlement {}" , e);
			resSettlement.reset();
		}
		return resSettlement;
	}	
	
	/**
	 * KPC -> GS대사 자료 upload
	 * @param jobDivider
	 * @param settleDate
	 * @return
	 */
	@RequestMapping(value = URI_V1_SETTLEMENT_UPLOAD, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResSettlement postSettlementUpload(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="settleDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String settleDate // 대사일자
			){
		ResSettlement resSettlement = new ResSettlement();
		resSettlement.setCode(ResultCode.RESULT_CODE_SUCCESS);
		resSettlement.setMessage(ResultCode.RESULT_MSG_SUCCESS);
		Date workDate = DateUtil.createDate(settleDate,DateUtil.DATE_FORMAT);
		int year = DateUtil.getYear(workDate);
		int month = DateUtil.getMonth(workDate);
		int day = DateUtil.getDay(workDate);
		if (JobStatusCode.GSPOP.equals(jobDivider)){
			gsDailySettlementService.upload(year, month, day);
		} else if (JobStatusCode.GSSM.equals(jobDivider)){
			gsSmSettlementService.upload(year, month, day);		
		} else if (JobStatusCode.GSPOINT.equals(jobDivider)){
			gsPointSettlementService.gsPointDailySettlementUpload(year, month, day);
		} else if (JobStatusCode.GSHB.equals(jobDivider)){
			gshbSettlementService.upload(year, month, day);
		} else if(JobStatusCode.HAPPYCASH.equals(jobDivider)){
			happyCashSettlementService.upload(year, month, day);
		} else {
			resSettlement.setCode(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
			resSettlement.setMessage(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
		}
		return resSettlement;
	}
	
	/**
	 * GS 대사 자료 KPC DB insert
	 * @param jobDivider
	 * @param settleDate
	 * @return
	 */
	@RequestMapping(value = URI_V1_SETTLEMENT_DOWNLOAD, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResSettlement postSettlementDownload(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="settleDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String settleDate // 대사일자
			){
		ResSettlement resSettlement = new ResSettlement();
		resSettlement.setCode(ResultCode.RESULT_CODE_SUCCESS);
		resSettlement.setMessage(ResultCode.RESULT_MSG_SUCCESS);
		Date workDate = DateUtil.createDate(settleDate,DateUtil.DATE_FORMAT);
		int year = DateUtil.getYear(workDate);
		int month = DateUtil.getMonth(workDate);
		int day = DateUtil.getDay(workDate);
		if (JobStatusCode.GSPOP.equals(jobDivider)){
			gsDailySettlementService.download(year, month, day);
		} else if (JobStatusCode.GSSM.equals(jobDivider)){
			gsSmSettlementService.download(year, month, day);		
		} else if (JobStatusCode.GSPOINT.equals(jobDivider)){
			gsPointSettlementService.gsPointDailySettlementDownload(year, month, day);
		} else if (JobStatusCode.GSHB.equals(jobDivider)){
			gshbSettlementService.download(year, month, day);
		} else {
			resSettlement.setCode(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
			resSettlement.setMessage(ResultCode.RESULT_CODE_NONE_REQ_PARAM);
		}
		return resSettlement;
	}	
	
	@RequestMapping(value = URI_V1_SETTLEMENT_GS_STATISTICS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatistics getGsSettlementStatistics(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="startDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String startDate, // 대사일자
			@RequestParam(value="endDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String endDate // 대사일자
			){
		return gsSettlementStatisticsService.getGsStatisticsSelect(jobDivider, startDate , endDate);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_HM_STATISTICS, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatistics getHmSettlementStatistics(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="startDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String startDate, // 대사일자
			@RequestParam(value="endDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String endDate // 대사일자
			){
		return gsSettlementStatisticsService.getHmStatisticsSelect(jobDivider, startDate , endDate);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_GS_ERROR_DETAIL, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatisticsErrorDetail getGsSettlementErrorDetail(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="workDt", required=true) @DateTimeFormat(pattern="yyyyMMdd") String workDt, // 대사일자
			@RequestParam(value="offset", required=true) int offset, // 대사일자
			@RequestParam(value="limit", required=true) int limit // 대사일자
			){
		return gsSettlementStatisticsService.getGsStatisticsErrorDetail(jobDivider, workDt, offset, limit);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_HM_ERROR_DETAIL, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatisticsErrorDetail getHmSettlementErrorDetail(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="workDt", required=true) @DateTimeFormat(pattern="yyyyMMdd") String workDt, // 대사일자
			@RequestParam(value="offset", required=true) int offset, // 대사일자
			@RequestParam(value="limit", required=true) int limit // 대사일자
			){
		return gsSettlementStatisticsService.getHmStatisticsErrorDetail(jobDivider, workDt, offset, limit);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_GS_ERROR_DETAIL_ALL, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatisticsErrorDetail getSettlementErrorDetailAll(
			@RequestParam(value="jobDivider", required=true) String jobDivider, // 업무구분
			@RequestParam(value="workDt", required=true) @DateTimeFormat(pattern="yyyyMMdd") String workDt // 대사일자
			){
		return gsSettlementStatisticsService.getGsStatisticsErrorDetailAll(jobDivider, workDt);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_GS_INCONSISTENCY, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatisticsErrorDetail gsInconsistencySelect(
			@RequestParam(value="jobDivider") String jobDivider, // 업무구분
			@RequestParam(value="startDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String startDate, // 대사일자
			@RequestParam(value="endDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String endDate, // 대사일자
			@RequestParam(value="dateType") String dateType, // 날짜타입
			@RequestParam(value="dealType") String dealType, // 처리구분  
			@RequestParam(value="dealDivider") String dealDivider, // 승인/취소 구분
			@RequestParam(value="orderNo") String orderNo, // 주문번호
			@RequestParam(value="offset", required=true) int offset, // 대사일자
			@RequestParam(value="limit", required=true) int limit, // 대사일자
			@RequestParam(value="isExcel", defaultValue="N" ) String isExcel // 엑셀출력 여부
			){
		return gsSettlementStatisticsService.gsInconsistencySelect(jobDivider, startDate,endDate,dateType, dealType, dealDivider, orderNo,offset,limit,isExcel);
	}
	
	@RequestMapping(value = URI_V1_SETTLEMENT_HM_INCONSISTENCY, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResSettlementStatisticsErrorDetail hmInconsistencySelect(
			@RequestParam(value="jobDivider") String jobDivider, // 업무구분
			@RequestParam(value="startDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String startDate, // 대사일자
			@RequestParam(value="endDate", required=true) @DateTimeFormat(pattern="yyyyMMdd") String endDate, // 대사일자
			@RequestParam(value="dateType") String dateType, // 날짜타입
			@RequestParam(value="dealType") String dealType, // 처리구분  
			@RequestParam(value="orderNo") String orderNo, // 주문번호
			@RequestParam(value="offset", required=true) int offset, // 대사일자
			@RequestParam(value="limit", required=true) int limit // 대사일자			
			){
		return gsSettlementStatisticsService.hmInconsistencySelect(jobDivider, startDate,endDate,dealType, orderNo,offset,limit);
	}	
	
}
