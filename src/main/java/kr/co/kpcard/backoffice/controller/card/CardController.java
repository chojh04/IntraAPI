package kr.co.kpcard.backoffice.controller.card;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kpcard.backoffice.controller.approval.protocol.ApprovalPagination;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfoList;
import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefund;
import kr.co.kpcard.backoffice.controller.card.protocol.RequestCardBalnaceRefundList;
import kr.co.kpcard.backoffice.controller.card.protocol.ResponseCardBalnaceRefund;
import kr.co.kpcard.backoffice.controller.card.protocol.ResponseCardBalnaceRefundList;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import kr.co.kpcard.backoffice.service.card.CardService;
import kr.co.kpcard.backoffice.service.card.ResGetApproveCardRefund;
import kr.co.kpcard.backoffice.service.card.ResGetApproveCardRefundList;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;

@RestController
public class CardController implements ResultCode{	
	private static Logger logger = LoggerFactory.getLogger(CardController.class);
	
	@Autowired
	CardService cardService;
	
	/**
	 * 카드 잔액 환불내역 조회
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="/card/approveCardBalnaceRefund", method=RequestMethod.GET)
	public ResponseEntity<ResponseCardBalnaceRefund> getCardBalnaceRefund(@RequestParam(value="seq", defaultValue="0") long seq) 
	{
		logger.info("getCardBalnaceRefund : "+seq);
		ResponseCardBalnaceRefund response = new ResponseCardBalnaceRefund();
		try{		
			ResGetApproveCardRefund approveCardRefund = cardService.getApproveCardRefund(seq);
			response.setResultList(approveCardRefund.getResult());
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			return new ResponseEntity<ResponseCardBalnaceRefund>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseCardBalnaceRefund>(response, HttpStatus.OK);
	}
	
	
	
	/**
	 * 카드 잔액 환불내역 리스트조회
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="/card/approveCardBalnaceRefunds", method=RequestMethod.GET)
	public ResponseEntity<ResponseCardBalnaceRefundList> getCardBalnaceRefundList(@ModelAttribute RequestCardBalnaceRefundList requestBody) 
	{
		logger.info("getCardBalnaceRefundList : "+requestBody.toString());
		ResponseCardBalnaceRefundList response = new ResponseCardBalnaceRefundList();
		try{		
			ResGetApproveCardRefundList approveCardRefund = cardService.getApproveCardRefundList(requestBody);
			response.setSummary(approveCardRefund.getSummary());
			response.setResultList(approveCardRefund.getResultList());
		}catch(Exception e){
			logger.error("Error : "+e.toString());
		}
		return new ResponseEntity<ResponseCardBalnaceRefundList>(response, HttpStatus.OK);
	}
	
	
	/**
	 * 카드 잔액환불 환불처리
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="/card/CardBalnaceRefund/approve", method=RequestMethod.POST)
	public ResponseEntity<ResponseString> cardBalnaceRefundApprove(@RequestBody RequestCardBalnaceRefund requestBody) 
	{
		logger.info("requestBody"+requestBody.getRefundList().size());
		ResponseString response = new ResponseString();
		try{		
			boolean result = cardService.cardBalnaceRefundApprove(requestBody);			
			if(result){
				response.setMessage("Success");
			}else{
				response.setMessage("Failed");
				return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}
	

	/**
	 * 카드 잔액환불 환불반려
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="/card/CardBalnaceRefund/reject", method=RequestMethod.POST)
	public ResponseEntity<ResponseString> cardBalnaceRefundReject(@RequestBody RequestCardBalnaceRefund requestBody) 
	{
		logger.info("requestBody"+requestBody.getReqMemo());
		ResponseString response = new ResponseString();
		try{		
			boolean result = cardService.cardBalnaceRefundReject(requestBody);			
			if(result){
				response.setMessage("Success");
			}else{
				response.setMessage("Failed");
				return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}
	
}
