package kr.co.kpcard.backoffice.service.card;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.component.ListSummary;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalForm;
import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefund;
import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefundList;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.approval.model.CardBalanceRefund;
import kr.co.kpcard.backoffice.repository.card.CardRepository;
import kr.co.kpcard.backoffice.repository.card.mapper.BalanceRefund;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResultMakeBrochure;

@Service
public class CardService {
	private Logger logger = LoggerFactory.getLogger(CardService.class);
	
	@Autowired
	NewApprovalRepository newApprovalRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	private RestTemplate rest;


	/**5.
	 * 팝카드 사용제한 해제 Service
	 * @param apprInfo
	 * @param empId
	 */
	public boolean approvePopCardRestirctApproval(Approval apprInfo){
		Gson gson =  new Gson();
		boolean result = false;
		try{
			logger.info("post cardApi restrict");
			//승인 컨텐츠 조회
			ApprovalContent approvalContent = newApprovalRepository.readApprovalContent(apprInfo.getContentSeq());
			ReqRestrictCardInfo reqCardInfo = gson.fromJson(approvalContent.getContent(), ReqRestrictCardInfo.class);
			
			URI url = UriComponentsBuilder
					.fromHttpUrl("http://"+reqCardInfo.getApiUrl())
					.queryParam("cardNumber", reqCardInfo.getCardNumber())
					.queryParam("insertAdminId", reqCardInfo.getInsertAdminId())
					.queryParam("giftNo", reqCardInfo.getGiftNo())
					.queryParam("restrictYN", reqCardInfo.getRestrictYN())
					.queryParam("restrictDesc", reqCardInfo.getRestrictDesc())
					.build().toUri();
			logger.info("url post : "+url);			
			
			ResultMakeBrochure response = rest.postForObject(url, null, ResultMakeBrochure.class);
			logger.info("restrictPopCard http CardService");
			if ("K000".equals(response.getCode())) {
				result = true;
			}
		}
		catch(Exception e){
			logger.error("Error : "+e.toString());
		}
		return result;
	}
	
	
	/**6.
	 * 카드 잔액환불 승인처리 후 환불 가능상태 등록 Service
	 * @param apprInfo
	 * @param content
	 * @return
	 */
	public boolean approvePopCardRefundApproval(Approval apprInfo){
		Gson gson = new Gson();
		long resultCount = 0;
		boolean result = false;
		try{
			logger.info("apprInfo.getContentSeq() : "+apprInfo.getContentSeq());
			ApprovalContent apprContent = newApprovalRepository.readApprovalContent(apprInfo.getContentSeq());
			String content = apprContent.getContent();
					
			ReqBalanceRefundInfo reqBalanceRefundInfo = gson.fromJson(content, ReqBalanceRefundInfo.class);
			CardBalanceRefund cardBalanceRefund = new CardBalanceRefund(apprInfo, reqBalanceRefundInfo);
			
			//팝카드 잔액 환불가능 상태 등록
			resultCount = newApprovalRepository.insertCardRefundInfo(cardBalanceRefund);
			logger.info("approvePopCardRefundApproval resultCount : "+resultCount);
			if(resultCount>0){
				result = true;
			}			
		}catch(Exception e){
			logger.error("insertAdditionalInfo Failed : "+e.toString());
			logger.error("insertAdditionalInfo Failed info 'appSeq:'"+apprInfo.getSeq());
			return false;
		}
		return result;
	}
	
	//팝카드 잔액환불 신청내역 조회
	public ResGetApproveCardRefund getApproveCardRefund(long req){
		ResGetApproveCardRefund resGetApproveCardRefund = new ResGetApproveCardRefund();
		try{				
			
			BalanceRefund result = cardRepository.getCardRefund(req);				
			resGetApproveCardRefund.setResult(result);
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}
		return resGetApproveCardRefund;
	}
	
	//팝카드 잔액환불 신청내역 리스트 조회
	public ResGetApproveCardRefundList getApproveCardRefundList(RequestCardBalnaceRefundList req){
		ResGetApproveCardRefundList resGetApproveCardRefund = new ResGetApproveCardRefundList();
		try{			
			int totalCount = cardRepository.getCardRefundListCount(req);
			ListSummary summary = new ListSummary(totalCount, req.getOffset(), req.getLimit());
			resGetApproveCardRefund.setSummary(summary);
			logger.info("totalCount : "+totalCount);
			
			if(totalCount>0)
			{
				List<BalanceRefund> refundList = cardRepository.getCardRefundList(req);				
				resGetApproveCardRefund.setResultList(refundList);
			}			
			
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			throw e;
		}
		return resGetApproveCardRefund;
	}
	
	//팝카드 잔액환불 처리
	public boolean cardBalnaceRefundApprove(RequestCardBalnaceRefund req){
		Gson gson =  new Gson();
		boolean result = false;
		long resultCount = 0;

		try{
			for(BalanceRefund balanceRefund : req.getRefundList()){			
								
				BalanceRefund refundInfo = cardRepository.getCardRefund(balanceRefund.getSeq());
			
				Approval approval = newApprovalRepository.readApprovalInfo(refundInfo.getApprSeq());
				ApprovalContent approvalContent = newApprovalRepository.readApprovalContent(approval.getContentSeq());
				
				ReqBalanceRefundInfo reqBalanceRefundInfo = gson.fromJson(approvalContent.getContent(), ReqBalanceRefundInfo.class);			
				logger.info("content : "+approvalContent.getContent());
	
				URI url = UriComponentsBuilder
						.fromHttpUrl("http://"+reqBalanceRefundInfo.getApiUrl())				
						.queryParam("cardNumber", reqBalanceRefundInfo.getCardNumber())
						.build().toUri();
				logger.info("url post : "+url);		
				
				ResultMakeBrochure response = rest.postForObject(url, null, ResultMakeBrochure.class);
				logger.info("approvePopCardRefund http CardService : response.getCode() : "+response.getCode());
				
				if ("K000".equals(response.getCode())) {
					
					CardBalanceRefund cardBalanceRefund = new CardBalanceRefund(approval.getSeq(), req.getEmpId());
					//팝카드 잔액 환불완료 상태 변경
					resultCount = newApprovalRepository.insertCardRefundInfo(cardBalanceRefund);
					if(resultCount>0){
						result =true;
					}
				}
				/*
				CardBalanceRefund cardBalanceRefund = new CardBalanceRefund(refundInfo.getApprSeq(), req.getEmpId());
				//팝카드 잔액 환불완료 상태 변경
				resultCount = newApprovalRepository.insertCardRefundInfo(cardBalanceRefund);
				if(resultCount>0){
					result =true;
				}
				*/
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
	
	//팝카드 잔액환불 거절
	public boolean cardBalnaceRefundReject(RequestCardBalnaceRefund req){
		Gson gson =  new Gson();
		boolean result = false;
		long resultCount = 0;

		try{
			for(BalanceRefund balanceRefund : req.getRefundList()){
				logger.info("balanceRefund Seq : "+balanceRefund.getSeq());
				BalanceRefund refundInfo = cardRepository.getCardRefund(balanceRefund.getSeq());
				CardBalanceRefund cardBalanceRefund = new CardBalanceRefund(refundInfo.getApprSeq(), req.getEmpId(), req.getReqMemo());
				//팝카드 잔액 환불 상태 변경
				resultCount = newApprovalRepository.insertCardRefundInfo(cardBalanceRefund);
				if(resultCount>0){
					result =true;
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
		return result;
	}	
}
