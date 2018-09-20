package kr.co.kpcard.backoffice.service.approval;


import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalDetail;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfo;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.approval.mapper.KpcApprovalMapper;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.approval.model.CardBalanceRefund;
import kr.co.kpcard.backoffice.service.billing.BillingService;
import kr.co.kpcard.backoffice.service.card.CardService;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultMakeBrochure;

@Service
@Transactional("backOfficeTransactionManager")
public class ApprovalService {
	private final Logger logger = LoggerFactory.getLogger(ApprovalService.class);
	
	
	@Autowired
	NewApprovalRepository newApprovalRepository;
	
	@Autowired
	KpcApprovalMapper kpcApprovalMapper;

	@Autowired
	NewApprovalService newApprovalService;

	@Autowired
	CardService cardService;
	
	@Autowired
	BillingService billingService;
	
	/**1.
	 * appraval Controller - 승인 정보 등록
	 * @param apprInfo
	 * @param content
	 * @return
	 */
	public boolean insertApprovalInfo(Approval apprInfo, String content){
		ApprovalContent apprContent = new ApprovalContent();		
		boolean result = false;
		long resultCount = 0;
		try{			
			if(apprInfo.getSeq()==0){
				apprInfo.setSeq(newApprovalRepository.getApprovalSeq());
				apprInfo.setContentSeq(newApprovalRepository.getApprovalContentSeq());				
			}
			
			apprContent.setSeq(apprInfo.getContentSeq());
			apprContent.setWorkType(apprInfo.getWorkType());
			apprContent.setContent(content);
			logger.info("seq : "+apprInfo.getSeq()+"/ conSeq : "+apprInfo.getContentSeq()+"/ workType : "+apprInfo.getWorkType());
			
			
			resultCount = newApprovalRepository.insertApproval(apprInfo, apprContent);
			logger.info("resultCount : "+resultCount);
			
			if(resultCount>=3)
			{
				result = true;
			}else
			{
				throw new FailureMessageException("Service Error","Service Error");
			}
		}catch(FailureMessageException fe){
			logger.error("Service Error : "+fe.getCustomMsg());
			throw fe;
		}
		catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}
		return result;
	}
	

	/**2.
	 * appraval Controller - 승인 정보 조회
	 * @param apprInfo
	 * @param content
	 * @return
	 */
	public ResponseApprovalDetail getApprovalDetailInfo(long seq)
	{
		logger.info("getApprovalDetailInfo Param : [seq : "+seq+"]");
		ResponseApprovalDetail responseApprovalDetail = new ResponseApprovalDetail();
		try
		{
			//승인 정보 조회
			Approval apprInfo = newApprovalRepository.readApprovalInfo(seq);
			
			if (apprInfo != null)
			{
				//승인 등록요청 정보 조회
				ApprovalContent apprContent = newApprovalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				responseApprovalDetail.setApprovalInfo(new ResponseApprovalInfo(apprInfo));
				responseApprovalDetail.setContent(apprContent.getContent());				
				if(apprContent.getPreviousContent()!=null)
				{
					responseApprovalDetail.setPreviousContent(apprContent.getPreviousContent());
				}
			}else
			{
				throw new FailureMessageException("Service Error", "apprSequanceNumber '"+seq+"' Not Matching Data");
			}
		}catch(FailureMessageException fe){
			logger.error("Service Error : "+fe.getCustomMsg());
			throw fe;
		}
		catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}		
		return responseApprovalDetail;				
	}
	
	
	/**3.
	 * 승인요청 정보에 대한 승인처리
	 * @param approvalList
	 * @param apprEmpId
	 * @return
	 */
	public boolean approveApprovalInfo(List<Approval> approvalList, String apprEmpId){
		logger.info("acceptApprovalInfo Service Start : request[approvalList.Size : "+approvalList.size()+", apprEmpId : "+apprEmpId+"]");
		boolean result = false;
		
		try {
			for (Approval approvalEachValue : approvalList) {

				//승인 요청 정보 조회
				Approval apprInfo = newApprovalRepository.readApprovalInfo(approvalEachValue.getSeq());
				apprInfo.setApprEmpId(apprEmpId);
				
				if (apprInfo != null && SystemCodeConstant.ARST_0001.equals(apprInfo.getStatus())) {					
					logger.info("apprInfo.getWorkType() : "+apprInfo.getWorkType());				
					if(SystemCodeConstant.AWRK_0006.equals(apprInfo.getWorkType()))
					{							
						result = billingService.approveBillingApproval(apprInfo);						
					}
					else if(SystemCodeConstant.AWRK_0011.equals(apprInfo.getWorkType()))
					{
						result = cardService.approvePopCardRefundApproval(apprInfo);
					}					
					else if(SystemCodeConstant.AWRK_0012.equals(apprInfo.getWorkType())) 
					{
						result = cardService.approvePopCardRestirctApproval(apprInfo);
					}
					else {
						throw new FailureMessageException("Service Error","승인요청 처리에 필요한 [업무유형] 코드가 없습니다.");
					}
					
					if(result){
						apprInfo.setStatus(SystemCodeConstant.ARST_0002);
						long resultCount = newApprovalRepository.approveApproval(apprInfo);
						if(resultCount>0){
							
						}else{
							throw new FailureMessageException("Service Error","승인정보 업데이트 중 오류가 발생하였습니다.");
						}
					}else{
						throw new FailureMessageException("Service Error","승인요청 처리중 오류가 발생하였습니다.");
					}					
				}
				else {
					throw new FailureMessageException("Service Error", "승인하려는 요청 정보가 잘못 되었습니다.");
				}
			}			
		}catch(FailureMessageException fe){
			logger.error("Service Error : "+fe.getCustomMsg());
			throw fe;
		}
		catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}
		logger.info("acceptApprovalInfo Service End");
		return result;
	}	
	

	/**3.
	 * 승인요청 정보에 대한 승인처리
	 * @param approvalList
	 * @param apprEmpId
	 * @return
	 */
	public boolean cancelApprovalInfo(long apprSeq, String apprEmpId){
		logger.info("acceptApprovalInfo Service Start : request[approvalList : "+apprSeq+", apprEmpId : "+apprEmpId+"]");
		boolean result = false;
		long resultCount = 0;
		try {
			logger.info("seq : "+apprSeq);
			//승인 정보 조회
			Approval apprInfo = newApprovalRepository.readApprovalInfo(apprSeq);
			
			apprInfo.setStatus(SystemCodeConstant.ARST_0004);
			resultCount += kpcApprovalMapper.updateKpcApproval(apprInfo);
			logger.info("resultCount : "+resultCount);
			
			if(resultCount>0)
			{
				result = true;
			}else
			{
				throw new FailureMessageException("Service Error","Service Error");
			}					
		}catch(FailureMessageException fe){
			logger.error("Service Error : "+fe.getCustomMsg());
			throw fe;
		}
		catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}
		logger.info("acceptApprovalInfo Service End");
		return result;
	}	
}
