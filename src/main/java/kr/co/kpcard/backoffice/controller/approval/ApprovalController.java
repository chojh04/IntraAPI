package kr.co.kpcard.backoffice.controller.approval;

import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestCancelForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalDetail;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.service.approval.ApprovalService;


@RestController
@RequestMapping("/approval")
public class ApprovalController {
	
	private final Logger logger = LoggerFactory.getLogger(ApprovalController.class);

	@Autowired
	ApprovalService approvalService;

	/**
	 * 승인 요청 (신규)
	 * @param requestBody
	 * @return
	 */

	@ApiOperation(value = "승인요청 (신규 등록)", nickname = "승인요청 등록 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value="/request/approval", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseString> setAppralvalInfo(@RequestBody RequestApprovalInfo requestBody)
	{
		logger.info("Post requestBody Seq: " +requestBody.getSeq()+"requestBody conSeq : "+ requestBody.getContentSeq());
		logger.info(requestBody.toString());
		ResponseString response = new ResponseString();
		try{				
			//Paramter Check
			if(requestBody.getWorkType() == null || requestBody.getWorkType().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : workType");
			}else if(requestBody.getRefTitle() == null || requestBody.getRefTitle().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : refTitle");
			}else if(requestBody.getKeyword() == null || requestBody.getKeyword().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : keyword");
			}else if(requestBody.getReqEmpId() == null || requestBody.getReqEmpId().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : reqEmpId");
			}else if(requestBody.getReqMemo() == null || requestBody.getReqMemo().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : reqMemo");
			}else if(requestBody.getApprEmpId() == null || requestBody.getApprEmpId().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : apprEmpId");
			}else if(requestBody.getContentData() == null || requestBody.getContentData().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : content");
			}	
			
			//승인 필수 정보 Set		
			Approval apprInfo = new Approval();
			apprInfo.setDefaultValue(requestBody);
				
			approvalService.insertApprovalInfo(apprInfo, requestBody.getContentData());
			
		}catch(FailureMessageException fe){			
			logger.error("Error : "+ fe.getCustomMsg());
			response.setMessage(fe.getCustomMsg());
			return new ResponseEntity<ResponseString>(response, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			logger.error("Error : "+ e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		response.setMessage("Sucsess");
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}	
	
	/**
	 * 승인 요청 (수정)
	 * @param requestBody
	 * @return
	 */
	@ApiOperation(value = "승인요청 (수정 등록)", nickname = "승인요청 수정 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value="/request/approval", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ResponseString> modifyAppralvalInfo(@RequestBody RequestApprovalInfo requestBody)
	{
		logger.info("Put requestBody Seq: " +requestBody.getSeq()+"requestBody conSeq : "+ requestBody.getContentSeq());
		ResponseString response = new ResponseString();
		try{				
			//Paramter Check
			if(requestBody.getSeq() == null || requestBody.getSeq() == 0 || requestBody.getSeq().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : seq");
			}if(requestBody.getContentSeq() == null || requestBody.getContentSeq() == 0 || requestBody.getContentSeq().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : contentSeq");
			}else if(requestBody.getWorkType() == null || requestBody.getWorkType().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : workType");
			}else if(requestBody.getRefTitle() == null || requestBody.getRefTitle().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : refTitle");
			}else if(requestBody.getKeyword() == null || requestBody.getKeyword().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : keyword");
			}else if(requestBody.getReqEmpId() == null || requestBody.getReqEmpId().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : reqEmpId");
			}else if(requestBody.getReqMemo() == null || requestBody.getReqMemo().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : reqMemo");
			}else if(requestBody.getApprEmpId() == null || requestBody.getApprEmpId().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : apprEmpId");
			}else if(requestBody.getContentData() == null || requestBody.getContentData().equals("")){
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : content");
			}	
			
			//승인 필수 정보 Set		
			Approval apprInfo = new Approval();
			apprInfo.setDefaultValue(requestBody);
				
			approvalService.insertApprovalInfo(apprInfo, requestBody.getContentData());
			
		}catch(FailureMessageException fe){			
			logger.error("Request Parameter Error : "+ fe.getCustomMsg());
			response.setMessage(fe.getCustomMsg());
			return new ResponseEntity<ResponseString>(response, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			logger.error("Error : "+ e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		response.setMessage("Sucsess");
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}	
	
	/**
	 * 승인 요청 정보 조회 
	 * @param seq
	 * @return
	 */
	@ApiOperation(value = "승인요청 정보 조회", nickname = "승인요청 정보 조회 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value="/request/approvalInfo", method=RequestMethod.GET)
	public ResponseEntity<ResponseApprovalDetail> getApprovalInfo(
			@ApiParam(name = "seq", value = "승인요청 Seq", defaultValue = "0", required = true)
			@RequestParam(value="seq", required=true, defaultValue="0") Long seq)
	{
		logger.info("getApprovalInfo Controller RequestParam[seq : "+seq+"]");
		ResponseApprovalDetail response = new ResponseApprovalDetail();
		try
		{
			if(seq > 0)
			{				
				response = approvalService.getApprovalDetailInfo(seq);						
			}
			else{			
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : seq");
			}
		}catch(FailureMessageException fe){			
			logger.error("Request Parameter Error : "+ fe.getCustomMsg());
			response.setMessage(fe.getCustomMsg());
			return new ResponseEntity<ResponseApprovalDetail>(response, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			logger.error("Error : "+ e.toString());
			return new ResponseEntity<ResponseApprovalDetail>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}				
		return new ResponseEntity<ResponseApprovalDetail>(response, HttpStatus.OK);
	}
	

	/**
	 * 승인 요청 승인
	 * @param seq
	 * @return
	 */
	@ApiOperation(value = "승인요청 승인", nickname = "승인요청 승인 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value="/approve/approvalInfo", method=RequestMethod.POST)
	public ResponseEntity<ResponseString> approveApprovalInfo(@RequestBody RequestApprovalForm requestBody)
	{
		logger.info("approveApprovalInfo Controller RequestParam[approvalInfo Size : "+requestBody.getApprovalList().size()+", EmpId : "+requestBody.getEmpId()+"]");
		ResponseString response = new ResponseString();
		try
		{
			if(requestBody.getApprovalList().size() > 0)
			{				
				boolean result = approvalService.approveApprovalInfo(requestBody.getApprovalList(), requestBody.getEmpId());				
			}
			else{			
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : ApprovalList");
			}
		}catch(FailureMessageException fe){			
			logger.error("Request Parameter Error : "+ fe.getCustomMsg());
			response.setMessage(fe.getCustomMsg());
			return new ResponseEntity<ResponseString>(response, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			logger.error("Error : "+ e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}				
		response.setMessage("Success");
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}
	
	/**
	 * 승인 요청 취소
	 * @param seq
	 * @return
	 */
	@ApiOperation(value = "승인요청 취소", nickname = "승인요청 취소 API")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems") })
	@RequestMapping(value="/cancel/approvalInfo", method=RequestMethod.POST)
	public ResponseEntity<ResponseString> cancelApprovalInfo(@RequestBody RequestCancelForm requestBody)
	{
		logger.info("approveApprovalInfo Controller RequestParam[approvalInfo Size : "+requestBody.getApprSeq()+", EmpId : "+requestBody.getEmpId()+"]");
		ResponseString response = new ResponseString();
		try
		{
			if(requestBody.getApprSeq()>0)
			{				
				boolean result = approvalService.cancelApprovalInfo(requestBody.getApprSeq(), requestBody.getEmpId());				
			}
			else{			
				throw new FailureMessageException("Bad Request", "Not Vaild Parameter : ApprovalSeq");
			}
		}catch(FailureMessageException fe){			
			logger.error("Request Parameter Error : "+ fe.getCustomMsg());
			response.setMessage(fe.getCustomMsg());
			return new ResponseEntity<ResponseString>(response, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			logger.error("Error : "+ e.toString());
			response.setMessage(e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}				
		response.setMessage("Success");
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}
}
