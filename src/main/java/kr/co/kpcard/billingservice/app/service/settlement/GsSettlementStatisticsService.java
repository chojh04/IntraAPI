package kr.co.kpcard.billingservice.app.service.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnl.brts.service.BrtsBarCodeLib;

import kr.co.kpcard.billingservice.app.controller.protocol.ResSettlementStatistics;
import kr.co.kpcard.billingservice.app.controller.protocol.ResSettlementStatisticsErrorDetail;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;
import kr.co.kpcard.billingservice.app.repository.settlement.statistics.GsSettlementErrorDetail;
import kr.co.kpcard.billingservice.app.repository.settlement.statistics.GsSettlementStatistics;
import kr.co.kpcard.billingservice.app.repository.settlement.statistics.GsSettlementStatisticsMapper;
import kr.co.kpcard.common.utils.JobStatusCode;

@Service
public class GsSettlementStatisticsService {

	private Logger logger = LoggerFactory.getLogger(GsSettlementStatisticsService.class);
	
	@Autowired
	GsSettlementStatisticsMapper gsSettlementStatisticsMapper;	
	
	public ResSettlementStatistics getGsStatisticsSelect(String jobDivider, String startDate ,String endDate){
		ResSettlementStatistics resSettlementStatistics = new ResSettlementStatistics();
		List<GsSettlementStatistics> gsStatisticsList = null;
		try{
			resSettlementStatistics.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatistics.setMessage(ResultCode.RESULT_MSG_SUCCESS);		
			if(JobStatusCode.GSPOP.equals(jobDivider) || JobStatusCode.GSSM.equals(jobDivider) || JobStatusCode.GSHB.equals(jobDivider)){
				gsStatisticsList = gsSettlementStatisticsMapper.gsStatisticsSelect(jobDivider, startDate, endDate);
			} else if(JobStatusCode.GSPOINT.equals(jobDivider)){
				gsStatisticsList = gsSettlementStatisticsMapper.gspointStatisticsSelect(jobDivider, startDate, endDate);
			} else if(JobStatusCode.HAPPYMONEY.equals(jobDivider)){
				gsStatisticsList = gsSettlementStatisticsMapper.hmStatisticsSelect(jobDivider, startDate, endDate);
			} else{
				resSettlementStatistics.setCode(ResultCode.RESULT_CODE_NOT_VAILD_PARAM);
				resSettlementStatistics.setMessage(ResultCode.RESULT_MSG_NOT_VAILD_PARAM);;
			}
			logger.debug("gsStatisticsList : {}" , gsStatisticsList.size());
			resSettlementStatistics.setResultList(gsStatisticsList);
		}catch(Exception e){
			logger.error("getGsStatisticsSelect {} " , e);
			resSettlementStatistics.reset();
		}
		return resSettlementStatistics;
		
	}
	
	public ResSettlementStatistics getHmStatisticsSelect(String jobDivider, String startDate ,String endDate){
		ResSettlementStatistics resSettlementStatistics = new ResSettlementStatistics();
		List<GsSettlementStatistics> gsStatisticsList = null;
		try{
			resSettlementStatistics.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatistics.setMessage(ResultCode.RESULT_MSG_SUCCESS);		
			if(JobStatusCode.HAPPYMONEY.equals(jobDivider)){
				gsStatisticsList = gsSettlementStatisticsMapper.hmStatisticsSelect(jobDivider, startDate, endDate);
			}
			logger.debug("gsStatisticsList : {}" , gsStatisticsList.size());
			resSettlementStatistics.setResultList(gsStatisticsList);
		}catch(Exception e){
			logger.error("getGsStatisticsSelect {} " , e);
			resSettlementStatistics.reset();
		}
		return resSettlementStatistics;
		
	}	

	public ResSettlementStatisticsErrorDetail getGsStatisticsErrorDetail(String jobDivider, String workDt,int offset,int limit) {
		ResSettlementStatisticsErrorDetail resSettlementStatisticsErrorDetail = new ResSettlementStatisticsErrorDetail();
		try{
			resSettlementStatisticsErrorDetail.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatisticsErrorDetail.setMessage(ResultCode.RESULT_MSG_SUCCESS);
			List<GsSettlementErrorDetail> list = new ArrayList<GsSettlementErrorDetail>();
			if(JobStatusCode.GSSM.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsSmErrorDetailSelectTotlCnt(jobDivider, workDt, offset, limit));
				list = gsSettlementStatisticsMapper.gsSmErrorDetailSelect(jobDivider, workDt, offset, limit);
			}else if(JobStatusCode.GSPOP.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsPopErrorDetailSelectTotlCnt(jobDivider, workDt, offset, limit));
				list = gsSettlementStatisticsMapper.gsPopErrorDetailSelect(jobDivider, workDt, offset, limit);
			}else if(JobStatusCode.GSHB.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gshbErrorDetailSelectTotlCnt(jobDivider, workDt, offset, limit));
				list = gsSettlementStatisticsMapper.gshbErrorDetailSelect(jobDivider, workDt, offset, limit);
			}else if(JobStatusCode.GSPOINT.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsPointErrorDetailSelectTotlCnt(jobDivider, workDt, offset, limit));
				list = gsSettlementStatisticsMapper.gsPointErrorDetailSelect(jobDivider, workDt, offset, limit);
			}
			
			list.forEach(gsSettlementErrorDetail -> {
				try{
					if(gsSettlementErrorDetail.getCardNo() != null && !"".equals(gsSettlementErrorDetail.getCardNo())){
						gsSettlementErrorDetail.setCardNo(BrtsBarCodeLib.EncodeByLen(gsSettlementErrorDetail.getCardNo(), 9));	
					}
				} catch(Exception e){
					logger.error("encode error : {}" , gsSettlementErrorDetail.getCardNo());
				}
			});
			resSettlementStatisticsErrorDetail.setResultList(list);
			logger.debug("resSettlementStatisticsErrorDetail : {} " , resSettlementStatisticsErrorDetail.toString());
		}catch (Exception e){
			logger.error("getGsStatisticsErrorDetail {} " , e);
			resSettlementStatisticsErrorDetail.reset();
		}
		return resSettlementStatisticsErrorDetail;
	}
	
	public ResSettlementStatisticsErrorDetail getHmStatisticsErrorDetail(String jobDivider, String workDt,int offset,int limit) {
		ResSettlementStatisticsErrorDetail resSettlementStatisticsErrorDetail = new ResSettlementStatisticsErrorDetail();
		try{
			resSettlementStatisticsErrorDetail.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatisticsErrorDetail.setMessage(ResultCode.RESULT_MSG_SUCCESS);
			List<GsSettlementErrorDetail> list = new ArrayList<GsSettlementErrorDetail>();
			if(JobStatusCode.HAPPYMONEY.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.hmErrorDetailSelectTotlCnt(jobDivider, workDt, offset, limit));
				list = gsSettlementStatisticsMapper.hmErrorDetailSelect(jobDivider, workDt, offset, limit);
			}
			resSettlementStatisticsErrorDetail.setResultList(list);
			logger.debug("resSettlementStatisticsErrorDetail : {} " , resSettlementStatisticsErrorDetail.toString());
		}catch (Exception e){
			logger.error("getGsStatisticsErrorDetail {} " , e);
			resSettlementStatisticsErrorDetail.reset();
		}
		return resSettlementStatisticsErrorDetail;
	}

	public ResSettlementStatisticsErrorDetail getGsStatisticsErrorDetailAll(String jobDivider, String workDt) {
		ResSettlementStatisticsErrorDetail resSettlementStatisticsErrorDetail = new ResSettlementStatisticsErrorDetail();
		try{
			resSettlementStatisticsErrorDetail.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatisticsErrorDetail.setMessage(ResultCode.RESULT_MSG_SUCCESS);
			List<GsSettlementErrorDetail> list = gsSettlementStatisticsMapper.gsErrorDetailSelectAll(jobDivider, workDt);
			list.forEach(gsSettlementErrorDetail -> {
				gsSettlementErrorDetail.setCardNo(BrtsBarCodeLib.EncodeByLen(gsSettlementErrorDetail.getCardNo(), 9));	
			});
			resSettlementStatisticsErrorDetail.setResultList(list);			
		}catch (Exception e){
			logger.error("getGsStatisticsErrorDetail {} " , e);
			resSettlementStatisticsErrorDetail.reset();
		}
		return resSettlementStatisticsErrorDetail;
	}

	public ResSettlementStatisticsErrorDetail gsInconsistencySelect(String jobDivider, String  startDate,String endDate, 
			String dateType, String dealType , String dealDivider, String orderNo,int offset,int limit, String isExcel){
		ResSettlementStatisticsErrorDetail resSettlementStatisticsErrorDetail = new ResSettlementStatisticsErrorDetail();
		try{

			resSettlementStatisticsErrorDetail.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatisticsErrorDetail.setMessage(ResultCode.RESULT_MSG_SUCCESS);
			List<GsSettlementErrorDetail> list = new ArrayList<GsSettlementErrorDetail>();
			if(JobStatusCode.GSPOP.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsPopInconsistencySelectTotlCnt(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset, limit));
				list = gsSettlementStatisticsMapper.gsPopInconsistencySelect(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset,limit,isExcel);
			}else if(JobStatusCode.GSSM.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsSmInconsistencySelectTotlCnt(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset, limit));
				list = gsSettlementStatisticsMapper.gsSmInconsistencySelect(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset,limit,isExcel);
			}else if(JobStatusCode.GSPOINT.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gsPointInconsistencySelectTotlCnt(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset, limit));
				list = gsSettlementStatisticsMapper.gsPointInconsistencySelect(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset,limit,isExcel);
			}else if(JobStatusCode.GSHB.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.gshbInconsistencySelectTotlCnt(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset, limit));
				list = gsSettlementStatisticsMapper.gshbInconsistencySelect(jobDivider, startDate, endDate, dateType, dealType, dealDivider, orderNo,offset,limit,isExcel);
			}
			list.forEach(gsSettlementErrorDetail -> {
				try{
					if(gsSettlementErrorDetail.getCardNo() != null && !"".equals(gsSettlementErrorDetail.getCardNo())){
						gsSettlementErrorDetail.setCardNo(BrtsBarCodeLib.EncodeByLen(gsSettlementErrorDetail.getCardNo(), 9));	
					}
				} catch(Exception e){
					logger.error("encode error : {}" , gsSettlementErrorDetail.getCardNo());
				}
			});			
			resSettlementStatisticsErrorDetail.setResultList(list);
		}catch (Exception e){
			logger.error("getGsStatisticsErrorDetail {} " , e);
			resSettlementStatisticsErrorDetail.reset();
		}
		return resSettlementStatisticsErrorDetail;
	}
	public ResSettlementStatisticsErrorDetail hmInconsistencySelect(String jobDivider, String  startDate,String endDate, String dealType , String orderNo,int offset,int limit) {
		ResSettlementStatisticsErrorDetail resSettlementStatisticsErrorDetail = new ResSettlementStatisticsErrorDetail();
		try{
			
			resSettlementStatisticsErrorDetail.setCode(ResultCode.RESULT_CODE_SUCCESS);
			resSettlementStatisticsErrorDetail.setMessage(ResultCode.RESULT_MSG_SUCCESS);
			List<GsSettlementErrorDetail> list = new ArrayList<GsSettlementErrorDetail>();
			if(JobStatusCode.HAPPYMONEY.equals(jobDivider)){
				resSettlementStatisticsErrorDetail.setSummary(gsSettlementStatisticsMapper.hmInconsistencySelectTotlCnt(jobDivider, startDate, endDate, dealType, orderNo,offset, limit));
				list = gsSettlementStatisticsMapper.hmInconsistencySelect(jobDivider, startDate, endDate, dealType, orderNo,offset,limit);
			}
			resSettlementStatisticsErrorDetail.setResultList(list);
		}catch (Exception e){
			logger.error("getGsStatisticsErrorDetail {} " , e);
			resSettlementStatisticsErrorDetail.reset();
		}
		return resSettlementStatisticsErrorDetail;
	}

	
}
