package kr.co.kpcard.backoffice.repository.approval;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import kr.co.kpcard.backoffice.component.KpCardTransactionDefinition;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.controller.approval.protocol.ApprovalPagination;
import kr.co.kpcard.backoffice.repository.approval.mapper.KpcApprovalContentMapper;
import kr.co.kpcard.backoffice.repository.approval.mapper.KpcApprovalHstMapper;
import kr.co.kpcard.backoffice.repository.approval.mapper.KpcApprovalMapper;
import kr.co.kpcard.backoffice.repository.approval.mapper.NewApprovalMapper;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalForList;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalHistory;
import kr.co.kpcard.backoffice.repository.approval.model.Approver;
import kr.co.kpcard.backoffice.repository.approval.model.CardBalanceRefund;

/**
 * 승인 관련 레파지토리
 * Created by @author : MinWook on 2018. 6. 4.
 *
 */
@Repository
public class NewApprovalRepository {
	
	private final Logger logger = LoggerFactory.getLogger(NewApprovalRepository.class);
		
	@Autowired
	@Qualifier("backOfficeTransactionManager")
	private DataSourceTransactionManager backOfficeTransactionManager;
	
	//새로 만든 맵퍼
	@Autowired
	private NewApprovalMapper approvalMapper;
	
	@Autowired
	private KpcApprovalContentMapper kpcApprovalContentMapper;

	@Autowired
	private KpcApprovalMapper kpcApprovalMapper;
	
	@Autowired
	private KpcApprovalHstMapper kpcApprovalHstMapper;
	/**
	 * 승인 요청(Content) 등록
	 * @param apprContent 거래처 정보
	 * @return
	 */
	public Long createApprovalContent(ApprovalContent apprContent) {
		//승인 요청 정보 번호 생성
		Long apprContentSeq = kpcApprovalContentMapper.createKpcApprovalContentSeq();
		apprContent.setSeq(apprContentSeq);
		
		//승인 요청 정보 등록
		kpcApprovalContentMapper.createKpcApprovalContent(apprContent);

		return apprContentSeq;
	}
	
	/**
	 * 승인 요청(Information) 등록
	 * @param apprInfo
	 * @return
	 */
	public Long createApprovalInfo(Approval apprInfo) {
		//승인 요청 번호 생성
		Long apprSeq = kpcApprovalMapper.createKpcApprovalSeq();
		apprInfo.setSeq(apprSeq);
		
		//승인 요청 등록
		kpcApprovalMapper.createKpcApproval(apprInfo);

		return apprSeq;
	}
	
	/**
	 * 승인 요청 등록 할 때 이력(Information History) 등록
	 * @param apprSeq
	 * @param reqEmpId
	 */
	public void createKpcApprovalHistoryByRequestor(Long apprSeq, String reqEmpId) {
		kpcApprovalHstMapper.createKpcApprovalHistoryByRequestor(apprSeq, reqEmpId);
	}

	/**
	 * 승인 요청 처리 할 때 이력(Information History) 등록
	 * @param apprSeq
	 * @param apprEmpId
	 * @param status
	 */
	public void createKpcApprovalHistoryByApprover(Long apprSeq, String apprEmpId, String status) {
		kpcApprovalHstMapper.createKpcApprovalHistoryByApprover(apprSeq, apprEmpId, status);
	}
	
	/**
	 * 승인 신청내역 목록 건수(count) 조회 
	 * @param pagination 승인 페이징 객체 
	 * @return
	 */
	public int readApprovalRequestListCount(ApprovalPagination pagination) {
		return approvalMapper.readApprovalRequestListCount(pagination);
	}

	/**
	 * 승인 신청내역 목록(list) 조회
	 * @param pagination 승인 페이징 객체 
	 */
	public List<ApprovalForList> readApprovalRequestList(ApprovalPagination pagination) {
		return approvalMapper.readApprovalRequestList(pagination);
	}
	
	/**
	 * Excel 파일생성을 위한 승인 신청내역 목록(list) 조회
	 * @param pagination 승인 페이징 객체 
	 */
	public List<ApprovalForList> readApprovalRequestListForExcel(ApprovalPagination pagination) {
		return approvalMapper.readApprovalRequestListForExcel(pagination);
	}
	
	/**
	 * 승인 결제내역 목록 건수(count) 조회 
	 * @param pagination 승인 페이징 객체 
	 * @param availableApprWorkTypeList 승인 가능한 업무유형 리스트
	 * @return
	 */
	public int readApprovalAnswerListCount(ApprovalPagination pagination, List<String> availableApprWorkTypeList) {
		return approvalMapper.readApprovalAnswerListCount(pagination, availableApprWorkTypeList);
	}
	
	/**
	 * 승인 결제내역 목록(list) 조회
	 * @param pagination 승인 페이징 객체 
	 * @param availableApprWorkTypeList 승인 가능한 업무유형 리스트
	 */
	public List<ApprovalForList> readApprovalAnswerList(ApprovalPagination pagination, List<String> availableApprWorkTypeList) {
		return approvalMapper.readApprovalAnswerList(pagination, availableApprWorkTypeList);
	}
	
	/**
	 * Excel 파일생성을 위한 승인 결제내역 목록(list) 조회
	 * @param pagination 승인 페이징 객체 
	 * @param availableApprWorkTypeList 승인 가능한 업무유형 리스트
	 */
	public List<ApprovalForList> readApprovalAnswerListForExcel(ApprovalPagination pagination, List<String> availableApprWorkTypeList) {
		return approvalMapper.readApprovalAnswerListForExcel(pagination, availableApprWorkTypeList);
	}
	
	public int readApprovalNotiListCount(String loginEmpId, List<String> availableApprWorkTypeList) {
		return approvalMapper.readApprovalNotiListCount(loginEmpId, availableApprWorkTypeList);
	}
	
	public List<ApprovalForList> readApprovalNotiList(ApprovalPagination pagination, List<String> availableApprWorkTypeList) {
		return approvalMapper.readApprovalNotiList(pagination, availableApprWorkTypeList);
	}

	/**
	 * 승인 요청 정보 상세 조회
	 * @param seq
	 * @return
	 */
	public Approval readApprovalInfo(Long seq) {
		return kpcApprovalMapper.readKpcApproval(seq);
	}
	
	/**
	 * 승인 요청 정보 상세 조회
	 * @param seq
	 * @return
	 */
	public Approval readApprovalInfoByContentSeq(Long contentSeq) {
		return kpcApprovalMapper.readKpcApprovalByContentSeq(contentSeq);
	}
	

	/**
	 * 승인 요청 정보 이력 목록 조회
	 * @param apprSeq
	 * @return
	 */
	public List<ApprovalHistory> readApprovalHistoryList(Long apprSeq) {
		return kpcApprovalHstMapper.readKpcApprovalHistoryList(apprSeq);
	}
	
	/**
	 * 승인 요청 정보 Content 조회
	 * @param seq
	 * @return
	 */
	public ApprovalContent readApprovalContent(Long seq) {
		return kpcApprovalContentMapper.readKpcApprovalContent(seq);
	}

	/**
	 * 승인요청(승인대기) Info가 있는지 확인
	 * @param refId
	 * @return
	 */
	public boolean existApprovalRequestForRefId(String refId) {
		return kpcApprovalMapper.existApprovalRequestForRefId(refId);
	}

	/**
	 * 승인 정보 수정
	 * @param approval
	 */
	public void updateApprovalInfo(Approval approval) {
		kpcApprovalMapper.updateKpcApproval(approval);
	}
	
	/**
	 * 승인 요청 취소
	 * @param approval
	 */
	public void cancelApprovalInfo(Approval approval) {
		kpcApprovalMapper.updateKpcApprovalByRequestCancel(approval);
	}
	
	/**
	 * 승인 요청 중인 정보 수정
	 * @param approval
	 */
	public void updateApprovalRequestInfo(Approval approval) {
		kpcApprovalMapper.updateKpcApprovalRequest(approval);
	}
	
	/**
	 * 승인 요청 컨텐츠 수정
	 * @param apprContent
	 */
	public void updateApprovalContent(ApprovalContent apprContent) {
		kpcApprovalContentMapper.updateApprovalContent(apprContent);
	}
	
	/**
	 * 메뉴에 해당하는 승인자 목록 조회
	 * @param menuId
	 * @param loginEmpId 로그인한 사용자 아이디
	 * @return
	 */
	public List<Approver> readApproverList(String menuId, String loginEmpId) {
		return approvalMapper.readApproverListByMenuId(menuId, loginEmpId);
	}
	////////////////////////////////////
	//승인 정보 등록
	public long insertApproval(Approval apprInfo, ApprovalContent apprContent){
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		long resultCount = 0;
		try{
			//승인 정보등록
			resultCount += kpcApprovalMapper.insertApprovalInfo(apprInfo);			 
			//승인 후 처리될 data 등록
			resultCount += kpcApprovalMapper.insertApprovaContentlInfo(apprContent);			
			//승인 history등록			
			resultCount += kpcApprovalHstMapper.createKpcApprovalHistoryByRequestor(apprInfo.getSeq(), apprInfo.getReqEmpId());
					
			if(resultCount==3){
				backOfficeTransactionManager.commit(txStatus);
			}else{				
				backOfficeTransactionManager.rollback(txStatus);
				resultCount=0;
			}			
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			backOfficeTransactionManager.rollback(txStatus);
			return 0;
		}	
		return resultCount;
	}	
	//승인 정보 등록
	public long cancelApproval(Approval apprInfo){
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		long resultCount = 0;
		try{
			//승인 정보등록
			resultCount += kpcApprovalMapper.insertApprovalInfo(apprInfo);			 
			
			if(resultCount==1){
				backOfficeTransactionManager.commit(txStatus);
			}else{				
				backOfficeTransactionManager.rollback(txStatus);
				resultCount=0;
			}			
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			backOfficeTransactionManager.rollback(txStatus);
			return 0;
		}	
		return resultCount;
	}	
	//승인 요청 번호 생성
	public long getApprovalSeq(){
		return kpcApprovalMapper.createKpcApprovalSeq();
	}
	//승인 후 처리될 data 등록 번호 생성
	public long getApprovalContentSeq(){
		return kpcApprovalContentMapper.createKpcApprovalContentSeq();
	}
	//승인요청 승인
	public long approveApproval(Approval approval){
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		long resultCount = 0;
		try
		{
			resultCount += kpcApprovalHstMapper.createKpcApprovalHistoryByApprover(approval.getSeq(), approval.getApprEmpId(), approval.getStatus());
			resultCount += kpcApprovalMapper.updateKpcApproval(approval);			
			
			if(resultCount==2){
				backOfficeTransactionManager.commit(txStatus);
			}else{
				backOfficeTransactionManager.rollback(txStatus);
				resultCount=0;
			}
		}catch(Exception e){
			logger.error("Error : "+e.toString());
			backOfficeTransactionManager.rollback(txStatus);
			return 0;
		}
		return resultCount;		
	}
	
	//카드잔액환불 환불 내역 정보 등록
	public long insertCardRefundInfo(CardBalanceRefund cardBalanceRefund){		
		long resultCount = 0;
		try
		{
			resultCount += kpcApprovalHstMapper.insertCardRefundInfo(cardBalanceRefund);
			
		}catch(Exception e){
			logger.error("Error : "+e.toString());			
			return 0;
		}
		return resultCount;			
	}
	
	
	//////////////////////////////////////
}
