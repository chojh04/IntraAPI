package kr.co.kpcard.backoffice.service.approval;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.component.KpCardTransactionDefinition;
import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.component.approval.JsonContentToObjectConverter;
import kr.co.kpcard.backoffice.component.approval.ObejctToJsonContentConverter;
import kr.co.kpcard.backoffice.component.approval.content.ChargeCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.KconCouponContent;
import kr.co.kpcard.backoffice.component.approval.content.MerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.ProductBrochureContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantSVCBillingContent;
import kr.co.kpcard.backoffice.component.approval.content.SubMerchantServiceContent;
import kr.co.kpcard.backoffice.component.util.DateUtil;
import kr.co.kpcard.backoffice.controller.approval.protocol.ApprovalPagination;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalDetail;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalDetailInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfoList;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalForList;
import kr.co.kpcard.backoffice.repository.approval.model.Approver;
import kr.co.kpcard.backoffice.repository.approval.model.CardRestrictUsage;
import kr.co.kpcard.backoffice.repository.merchant.NewMerchantRepository;
import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
import kr.co.kpcard.backoffice.repository.system.EmployeeRepository;
import kr.co.kpcard.backoffice.repository.system.model.Employee;
import kr.co.kpcard.backoffice.service.billing.BillingService;
import kr.co.kpcard.backoffice.service.card.CardService;
import kr.co.kpcard.backoffice.service.card.ReqBalanceRefundInfo;
import kr.co.kpcard.backoffice.service.card.ReqRestrictCardInfo;
import kr.co.kpcard.backoffice.service.coupon.CouponService;
import kr.co.kpcard.backoffice.service.coupon.model.ProductBrochure;
import kr.co.kpcard.backoffice.service.coupon.protocol.KConDetail;
import kr.co.kpcard.backoffice.service.coupon.protocol.ResAdminCoupon;
import kr.co.kpcard.common.utils.EncodeString;


/**
 * 승인 관련 서비스
 * Created by @author : MinWook on 2018. 6. 4.
 *
 */
@Service
public class NewApprovalService {

	private final Logger logger = LoggerFactory.getLogger(NewApprovalService.class);
	
	@Autowired
	@Qualifier("backOfficeTransactionManager")
	private DataSourceTransactionManager backOfficeTransactionManager;
	
	@Autowired
	private NewApprovalRepository approvalRepository;

	@Autowired
	private NewMerchantRepository merchantRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CouponService couponService;
		
	@Autowired
	private CardService cardService;
	
	@Autowired
	private BillingService billingService;
		
	/**
	 *  대표 거래처 [등록 승인 요청]
	 * @param merchant 거래처 정보
	 * @param apprInfo 승인 요청 정보
	 * @return
	 */
	public void createApprovalForMerchant(Merchant merchant, Approval apprInfo){

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApprovalForMerchant");

		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();
			apprContent.setContent(ObejctToJsonContentConverter.setMerchantJsonContent(merchant));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0001);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0001);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(merchant.getName());
			apprInfo.setDescription("대표 거래처 등록 승인 요청 정보 입니다.");
			
			//거래처명, 대표자명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(merchant.getName(), merchant.getDetail().getCeoName()));

			apprInfo.setKeyword(keyword);
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("대표 거래처 등록 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("대표 거래처 등록 요청 중 오류 발생");
		}
	}
	
	/**
	 * 대표 거래처 [수정 승인 요청]
	 * @param merchant 거래처 정보
	 * @param apprInfo 승인 요청 정보
	 */
	public void updateApprovalForMerchant(Merchant merchant, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalForMerchant");
		
		try {
			if (merchant.getMerchantId() != null) {
				
				//해당 거래처의 [승인대기] 요청 정보가 있는지 확인
				boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(merchant.getMerchantId());
				
				if (!existApprovalReqeust) {
					//기존 거래처 정보 조회
					Merchant previousMBInfo = merchantRepository.readMerchantBasic(merchant.getMerchantId());
					MerchantDetail previousMDInfo = merchantRepository.readMerchantDetail(merchant.getMerchantId());
					
					previousMBInfo.setDetail(previousMDInfo);
					
					if (previousMBInfo != null & previousMDInfo != null) {
						//승인 요청 정보 생성
						ApprovalContent apprContent = new ApprovalContent();
						apprContent.setContent(ObejctToJsonContentConverter.setMerchantJsonContent(merchant));
						apprContent.setPreviousContent(ObejctToJsonContentConverter.setMerchantJsonContent(previousMBInfo));
						apprContent.setWorkType(SystemCodeConstant.AWRK_0001);
						Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
						
						//승인 정보 생성 
						apprInfo.setContentSeq(apprContentSeq);
						apprInfo.setWorkType(SystemCodeConstant.AWRK_0001);
						apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
						apprInfo.setStatus(SystemCodeConstant.ARST_0001);
						apprInfo.setRefTitle(merchant.getName());
						apprInfo.setRefId(merchant.getMerchantId());
						apprInfo.setDescription("대표 거래처 수정 승인 요청 정보 입니다.");
						
						//거래처ID, 거래처명, 대표자명 3개가 키워드
						String keyword = createApprovalInfoKeyword(Arrays.asList(merchant.getMerchantId(), merchant.getName(), merchant.getDetail().getCeoName()));
						apprInfo.setKeyword(keyword);
						
						Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
						
						//승인 정보 이력 생성
						approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
						
						backOfficeTransactionManager.commit(txStatus);
					}
					else {
						throw new RuntimeException("기존 거래처 정보가 없습니다.");
					}
				}
				else {
					throw new RuntimeException("승인대기 중인 정보가 있습니다.");
				}
			}
			else {
				throw new RuntimeException("수정 승인 요청을 위한 거래처 ID가 없습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("대표 거래처 수정 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("대표 거래처 수정 요청 중 오류 발생");
		}
	}
	
	/**
	 * 요청정보가 [요청대기] 중인 대표거래처 요청 정보 수정
	 * @param merchant 거래처 정보
	 * @param apprInfo 승인 요청 정보
	 */
	public void updateApprovalRequestForMerchant(Merchant merchant, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForMerchant");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				apprContent.setContent(ObejctToJsonContentConverter.setMerchantJsonContent(merchant));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0001);
				
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setRefTitle(merchant.getName());
				
				//키워드는 거래처ID, 거래처명, 대표자성명 
				List<String> keywordList = new ArrayList<String>();
				keywordList.add(merchant.getName());
				keywordList.add(merchant.getDetail().getCeoName());
				if (merchant.getMerchantId() != null) {
					keywordList.add(merchant.getMerchantId());
				}
				String keyword = createApprovalInfoKeyword(keywordList);
				
				beforeApprInfo.setKeyword(keyword);
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());

				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인요청 대기 정보 수정 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인요청 대기 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 * 대표 거래처 [삭제 승인 요청]
	 * @param merchantId
	 * @param apprInfo
	 */
	public void deleteApprovalForMerchant(String merchantId, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "deleteApprovalForMerchant");
		
		try {
			
			//해당 거래처의 [승인대기] 요청 정보가 있는지 확인
			boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(merchantId);
			
			if (!existApprovalReqeust) {
				//삭제 요청할 거래처 정보 조회
				Merchant merchantBasic = merchantRepository.readMerchantBasic(merchantId);
				MerchantDetail merchantDetail = merchantRepository.readMerchantDetail(merchantId);
				
				merchantBasic.setDetail(merchantDetail);
				
				if(merchantBasic != null && merchantDetail != null) {
					//승인 요청 컨텐츠 생성
					ApprovalContent apprContent = new ApprovalContent();
					apprContent.setWorkType(SystemCodeConstant.AWRK_0001);
					apprContent.setContent(ObejctToJsonContentConverter.setMerchantJsonContent(merchantBasic));
					Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
					
					//승인 요청 정보 생성 
					apprInfo.setContentSeq(apprContentSeq);
					apprInfo.setWorkType(SystemCodeConstant.AWRK_0001);
					apprInfo.setReqType(SystemCodeConstant.AREQ_0003);
					apprInfo.setStatus(SystemCodeConstant.ARST_0001);
					apprInfo.setRefTitle(merchantBasic.getName());
					apprInfo.setRefId(merchantBasic.getMerchantId());
					apprInfo.setDescription("대표 거래처 삭제 승인 요청 정보 입니다.");
					
					//대표 거래처ID, 거래처명, 대표자명 3개가 키워드
					String keyword = createApprovalInfoKeyword(Arrays.asList(merchantBasic.getMerchantId(), merchantBasic.getName(), merchantBasic.getDetail().getCeoName()));
					apprInfo.setKeyword(keyword);
					
					Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
					
					//승인 정보 이력 생성
					approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
					
					backOfficeTransactionManager.commit(txStatus);
				}
				else {
					throw new RuntimeException("삭제 요청 할 거래처 정보가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("승인대기 중인 정보가 있습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("대표 거래처 삭제 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("대표 거래처 삭제 요청 중 오류 발생");
		}
	}
	
	// 일반거래처-----------------------------------------------------------------------------------------
	
	/**
	 * [일반거래처 등록] 승인 요청
	 * @param subMerchant
	 * @param apprInfo
	 */
	public void createApprovalForSubMerchant(SubMerchant subMerchant, Approval apprInfo) {
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApprovalForSubMerchant");

		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();
			
			//비밀번호는 암호화
			subMerchant.setAgentPw(EncodeString.encodeSha512(subMerchant.getAgentPw()));
			
			apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(subMerchant));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0002);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0002);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(subMerchant.getName());
			apprInfo.setDescription("일반 거래처 등록 승인 요청 정보 입니다.");
			
			//거래처명, 대표자명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(subMerchant.getName(), subMerchant.getDetail().getCeoName()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("일반 거래처 등록 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("일반 거래처 등록 요청 중 오류 발생");
		}
	}

	/**
	 * [일반거래처 수정] 승인 요청
	 * @param subMerchant
	 * @param apprInfo
	 */
	public void updateApprovalForSubMerchant(SubMerchant subMerchant, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalForSubMerchant");
		
		try {
			if (subMerchant.getSubMerchantId() != null) {
				
				//해당 거래처의 [승인대기] 요청 정보가 있는지 확인
				boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(subMerchant.getSubMerchantId());
				
				if (!existApprovalReqeust) {
					//기존 거래처 정보 조회
					SubMerchant previousBasicInfo = merchantRepository.readSubMerchantBasic(subMerchant.getSubMerchantId());
					SubMerchantDetail previousDetailInfo = merchantRepository.readSubMerchantDetail(subMerchant.getSubMerchantId());
					
					previousBasicInfo.setDetail(previousDetailInfo);
					
					if (previousBasicInfo != null & previousDetailInfo != null) {
						
						//기존 비밀번호와 비교 후 새로운 비밀번호라면 다시 암호화.
						String newAgentPw = subMerchant.getAgentPw();
						if (!previousBasicInfo.getAgentPw().equals(newAgentPw)) {
							subMerchant.setAgentPw(EncodeString.encodeSha512(newAgentPw));
						}
						
						//승인 요청 정보 생성
						ApprovalContent apprContent = new ApprovalContent();
						apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(subMerchant));
						apprContent.setPreviousContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(previousBasicInfo));
						apprContent.setWorkType(SystemCodeConstant.AWRK_0002);
						Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
						
						//승인 정보 생성 
						apprInfo.setContentSeq(apprContentSeq);
						apprInfo.setWorkType(SystemCodeConstant.AWRK_0002);
						apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
						apprInfo.setStatus(SystemCodeConstant.ARST_0001);
						apprInfo.setRefTitle(subMerchant.getName());
						apprInfo.setRefId(subMerchant.getSubMerchantId());
						apprInfo.setDescription("일반 거래처 수정 승인 요청 정보 입니다.");
						
						//거래처ID, 거래처명, 대표자명 3개가 키워드
						String keyword = createApprovalInfoKeyword(Arrays.asList(subMerchant.getSubMerchantId(), subMerchant.getName(), subMerchant.getDetail().getCeoName()));
						apprInfo.setKeyword(keyword);
						
						Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
						
						//승인 정보 이력 생성
						approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
						
						backOfficeTransactionManager.commit(txStatus);
					}
					else {
						throw new RuntimeException("기존 거래처 정보가 없습니다.");
					}
				}
				else {
					throw new RuntimeException("승인대기 중인 정보가 있습니다.");
				}
			}
			else {
				throw new RuntimeException("수정 승인 요청을 위한 거래처 Sub ID가 없습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [일반 거래처] 수정 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인대기 중인 [일반 거래처] 수정 요청 중 오류 발생");
		}
	}
	
	/**
	 * 요청정보가 [요청대기] 중인 일반거래처 요청 정보 수정
	 * @param merchant 거래처 정보
	 * @param apprInfo 승인 요청 정보
	 */
	public void updateApprovalRequestForSubMerchant(SubMerchant subMerchant, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForSubMerchant");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(subMerchant));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0002);
				
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setRefTitle(subMerchant.getName());
				
				//키워드는 거래처ID, 거래처명, 대표자성명 
				List<String> keywordList = new ArrayList<String>();
				keywordList.add(subMerchant.getName());
				keywordList.add(subMerchant.getDetail().getCeoName());
				if (subMerchant.getSubMerchantId() != null) {
					keywordList.add(subMerchant.getSubMerchantId());
				}
				String keyword = createApprovalInfoKeyword(keywordList);
				
				beforeApprInfo.setKeyword(keyword);
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());

				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [일반거래처] 정보 수정 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인대기 중인 [일반거래처] 정보 수정 중 오류 발생");
		}
	}

	/**
	 * [일반거래처 삭제] 승인 요청
	 * @param subMerchantId
	 * @param apprInfo
	 */
	public void deleteApprovalForSubMerchant(String subMerchantId, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "deleteApprovalForSubMerchant");
		
		try {
			
			//해당 거래처의 [승인대기] 요청 정보가 있는지 확인
			boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(subMerchantId);
			
			if (!existApprovalReqeust) {
				//삭제 요청할 거래처 정보 조회
				SubMerchant basic = merchantRepository.readSubMerchantBasic(subMerchantId);
				SubMerchantDetail detail = merchantRepository.readSubMerchantDetail(subMerchantId);
				
				basic.setDetail(detail);
				
				if(basic != null && detail != null) {
					//승인 요청 컨텐츠 생성
					ApprovalContent apprContent = new ApprovalContent();
					apprContent.setWorkType(SystemCodeConstant.AWRK_0002);
					apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(basic));
					Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
					
					//승인 요청 정보 생성 
					apprInfo.setContentSeq(apprContentSeq);
					apprInfo.setWorkType(SystemCodeConstant.AWRK_0002);
					apprInfo.setReqType(SystemCodeConstant.AREQ_0003);
					apprInfo.setStatus(SystemCodeConstant.ARST_0001);
					apprInfo.setRefTitle(basic.getName());
					apprInfo.setRefId(basic.getSubMerchantId());
					apprInfo.setDescription("일반 거래처 삭제 승인 요청 정보 입니다.");
					
					//거래처ID, 거래처명, 대표자명 3개가 키워드
					String keyword = createApprovalInfoKeyword(Arrays.asList(basic.getSubMerchantId(), basic.getName(), basic.getDetail().getCeoName()));
					apprInfo.setKeyword(keyword);
					
					Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
					
					//승인 정보 이력 생성
					approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
					
					backOfficeTransactionManager.commit(txStatus);
				}
				else {
					throw new RuntimeException("삭제 요청 할 거래처 정보가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("승인대기 중인 정보가 있습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("일반 거래처 삭제 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("일반 거래처 삭제 요청 중 오류 발생");
		}
	}
	
	
	// 거래처서비스-----------------------------------------------------------------------------------------
	
	/**
	 * [거래처서비스 등록] 승인 요청
	 * @param service
	 * @param apprInfo
	 */
	public void createApprovalForSubMerchantService(SubMerchantService service, Approval apprInfo) {
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApprovalForSubMerchantService");

		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();
			
			//비밀번호는 암호화
			service.setSvcConnPw(service.getSvcConnPw());
			
			apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(service));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0003);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0003);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(service.getName());
			apprInfo.setDescription("거래처 서비스 등록 승인 요청 정보 입니다.");
			
			//거래처 ID, 서비스명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(service.getSubMerchantId(), service.getName()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("거래처 서비스 등록 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("거래처 서비스 등록 요청 중 오류 발생");
		}
	}
	
	/**
	 * [거래처서비스 수정] 승인 요청
	 * @param service
	 * @param apprInfo
	 */
	public void updateApprovalForSubMerchantService(SubMerchantService service, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalForSubMerchantService");
		
		try {
			if (service.getServiceId() != null) {
				
				//해당 거래처 서비스의 [승인대기] 요청 정보가 있는지 확인
				boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(service.getServiceId());
				
				if (!existApprovalReqeust) {
					//거래처 서비스 정보 조회
					SubMerchantService previousServiceInfo = merchantRepository.readSubMerchantService(service.getServiceId());
					
					if (previousServiceInfo != null) {
												
						//승인 요청 정보 생성
						ApprovalContent apprContent = new ApprovalContent();
						apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(service));
						apprContent.setPreviousContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(previousServiceInfo));
						apprContent.setWorkType(SystemCodeConstant.AWRK_0003);
						Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
						
						//승인 정보 생성 
						apprInfo.setContentSeq(apprContentSeq);
						apprInfo.setWorkType(SystemCodeConstant.AWRK_0003);
						apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
						apprInfo.setStatus(SystemCodeConstant.ARST_0001);
						apprInfo.setRefTitle(service.getName());
						apprInfo.setRefId(service.getServiceId());
						apprInfo.setDescription("거래처 서비스 수정 승인 요청 정보 입니다.");
						
						//거래처 ID, 서비스 ID, 서비스명 3개가 키워드
						String keyword = createApprovalInfoKeyword(Arrays.asList(service.getSubMerchantId(), service.getServiceId(), service.getName()));
						apprInfo.setKeyword(keyword);
						
						Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
						
						//승인 정보 이력 생성
						approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
						
						backOfficeTransactionManager.commit(txStatus);
					}
					else {
						throw new RuntimeException("기존 거래처 서비스 정보가 없습니다.");
					}
				}
				else {
					throw new RuntimeException("승인대기 중인 정보가 있습니다.");
				}
			}
			else {
				throw new RuntimeException("수정 승인 요청을 위한 거래처 서비스 ID가 없습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("거래처 서비스 수정 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("거래처 서비스 수정 요청 중 오류 발생");
		}
	}
	
	/**
	 *  요청정보가 [요청대기] 중인 거래처서비스 요청 정보 수정
	 * @param service 거래처 정보
	 * @param apprInfo 승인 요청 정보
	 */
	public void updateApprovalRequestForSubMerchantService(SubMerchantService service, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForSubMerchantService");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(service));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0003);
				
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setRefTitle(service.getName());
				
				//키워드는 거래처ID, 서비스명, 서비스 ID
				List<String> keywordList = new ArrayList<String>();
				keywordList.add(service.getSubMerchantId());
				keywordList.add(service.getName());
				if (service.getSubMerchantId() != null) {
					keywordList.add(service.getSubMerchantId());
				}
				String keyword = createApprovalInfoKeyword(keywordList);
				
				beforeApprInfo.setKeyword(keyword);
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());
				
				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [거래서 서비스] 정보 수정 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인대기 중인 [거래서 서비스] 정보 수정 중 오류 발생");
		}
	}

	/**
	 * [거래처서비스 삭제] 승인 요청
	 * @param serviceId
	 * @param apprInfo
	 */
	public void deleteApprovalForSubMerchantService(String serviceId, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "deleteApprovalForSubMerchantService");
		
		try {
			
			//해당 거래처 서비스의 [승인대기] 요청 정보가 있는지 확인
			boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(serviceId);
			
			if (!existApprovalReqeust) {
				//삭제 요청할 거래처 정보 조회
				SubMerchantService service = merchantRepository.readSubMerchantService(serviceId);
				
				if(service != null) {
					//승인 요청 컨텐츠 생성
					ApprovalContent apprContent = new ApprovalContent();
					apprContent.setWorkType(SystemCodeConstant.AWRK_0003);
					apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(service));
					Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
					
					//승인 요청 정보 생성 
					apprInfo.setContentSeq(apprContentSeq);
					apprInfo.setWorkType(SystemCodeConstant.AWRK_0003);
					apprInfo.setReqType(SystemCodeConstant.AREQ_0003);
					apprInfo.setStatus(SystemCodeConstant.ARST_0001);
					apprInfo.setRefTitle(service.getName());
					apprInfo.setRefId(service.getServiceId());
					apprInfo.setDescription("거래처 서비스 삭제 승인 요청 정보 입니다.");
					
					//거래처ID, 서비스ID, 서비스명 3개가 키워드
					String keyword = createApprovalInfoKeyword(Arrays.asList(service.getSubMerchantId(), serviceId, service.getName()));
					apprInfo.setKeyword(keyword);
					
					Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
					
					//승인 정보 이력 생성
					approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
					
					backOfficeTransactionManager.commit(txStatus);
				}
				else {
					throw new RuntimeException("삭제 요청 할 거래처 정보가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("승인대기 중인 정보가 있습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("거래처 서비스 삭제 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("거래처 서비스 삭제 요청 중 오류 발생");
		}
	}
	
	// 서비스정산-----------------------------------------------------------------------------------------

	/**
	 * [서비스정산 등록] 승인 요청
	 * @param billing 정산 정보
	 * @param apprInfo
	 */
	public void createApprovalForSubMerchantSVCBilling(SubMerchantSVCBilling billing, Approval apprInfo) {
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApprovalForSubMerchantSVCBilling");

		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();
			
			apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0004);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0004);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(billing.getName());
			apprInfo.setDescription("서비스 정산 등록 승인 요청 정보 입니다.");
			
			//서비스 ID, 정산명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(billing.getServiceId(), billing.getName()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("서비스 정산 등록 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("서비스 정산 등록 요청 중 오류 발생");
		}
	}

	/**
	 * [서비스정산 수정] 승인 요청
	 * @param billing
	 * @param apprInfo
	 */
	public void updateApprovalForSubMerchantSVCBilling(SubMerchantSVCBilling billing, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalForSubMerchantSVCBilling");
		
		try {
			if (billing.getServiceBillingId() != null) {
				
				//해당 거래처 서비스의 [승인대기] 요청 정보가 있는지 확인
				boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(billing.getServiceBillingId());
				
				if (!existApprovalReqeust) {
					//서비스 정산 정보 조회
					SubMerchantSVCBilling previousBillingInfo = merchantRepository.readSubMerchantSVCBilling(billing.getServiceBillingId());
					
					if (previousBillingInfo != null) {
						
						Long currentCommisionId = merchantRepository.readSubmerchantCurrentCommisionId(previousBillingInfo.getServiceBillingId());
						SubMerchantCommision previousCommisionInfo = merchantRepository.readSubmerchantCommision(currentCommisionId);
						previousBillingInfo.setCommision(previousCommisionInfo);
						
						//정산정보를 수정할땐 기존 수수료 정보를 보여줘야함
						billing.setCommision(previousCommisionInfo);
						
						//승인 요청 정보 생성
						ApprovalContent apprContent = new ApprovalContent();
						apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
						apprContent.setPreviousContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(previousBillingInfo));
						apprContent.setWorkType(SystemCodeConstant.AWRK_0004);
						Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
						
						//승인 정보 생성 
						apprInfo.setContentSeq(apprContentSeq);
						apprInfo.setWorkType(SystemCodeConstant.AWRK_0004);
						apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
						apprInfo.setStatus(SystemCodeConstant.ARST_0001);
						apprInfo.setRefTitle(billing.getName());
						apprInfo.setRefId(billing.getServiceBillingId());
						apprInfo.setDescription("서비스 정산 수정 승인 요청 정보 입니다.");
						
						//정산 ID, 정산명 2개가 키워드
						String keyword = createApprovalInfoKeyword(Arrays.asList(billing.getServiceBillingId(), billing.getName()));
						apprInfo.setKeyword(keyword);
						
						Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
						
						//승인 정보 이력 생성
						approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
						backOfficeTransactionManager.commit(txStatus);
					}
					else {
						throw new RuntimeException("기존 서비스정산 정보가 없습니다.");
					}
				}
				else {
					throw new RuntimeException("승인대기 중인 정보가 있습니다.");
				}
			}
			else {
				throw new RuntimeException("수정 승인 요청을 위한 서비스정산 ID가 없습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("서비스 정산 수정 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("서비스 정산 수정 요청 중 오류 발생");
		}
	}

	/**
	 * 요청정보가 [요청대기] 중인 서비스정산 요청 정보 수정 
	 * @param billing
	 * @param apprInfo
	 */
	public void updateApprovalRequestForSubMerchantSVCBilling(SubMerchantSVCBilling billing, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForSubMerchantSVCBilling");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				
				if (billing.getServiceBillingId() != null && !billing.getServiceBillingId().equals("")) {
					Long currentCommisionId = merchantRepository.readSubmerchantCurrentCommisionId(billing.getServiceBillingId());
					SubMerchantCommision currentCommisionInfo = merchantRepository.readSubmerchantCommision(currentCommisionId);
					billing.setCommision(currentCommisionInfo);
				}
				
				
				apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0004);
				
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setRefTitle(billing.getName());
				
				//키워드는 정산명, 서비스정산ID
				List<String> keywordList = new ArrayList<String>();
				keywordList.add(billing.getName());
				if (billing.getServiceBillingId() != null) {
					keywordList.add(billing.getServiceBillingId());
				}
				String keyword = createApprovalInfoKeyword(keywordList);
				
				beforeApprInfo.setKeyword(keyword);
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());

				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [서비스 정산] 정보 수정 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인대기 중인 [서비스 정산] 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 *  [서비스정산 삭제] 승인 요청
	 * @param serviceBillingId
	 * @param apprInfo
	 */
	public void deleteApprovalForSubMerchantSVCBilling(String serviceBillingId, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "deleteApprovalForSubMerchantSVCBilling");
		
		try {
			
			//해당 거래처 서비스의 [승인대기] 요청 정보가 있는지 확인
			boolean existApprovalReqeust = approvalRepository.existApprovalRequestForRefId(serviceBillingId);
			
			if (!existApprovalReqeust) {
				//삭제 요청할 서비스 정산 정보 조회
				SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(serviceBillingId);
				
				if(billing != null) {
					//승인 요청 컨텐츠 생성
					ApprovalContent apprContent = new ApprovalContent();
					apprContent.setWorkType(SystemCodeConstant.AWRK_0004);
					apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
					Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
					
					//승인 요청 정보 생성 
					apprInfo.setContentSeq(apprContentSeq);
					apprInfo.setWorkType(SystemCodeConstant.AWRK_0004);
					apprInfo.setReqType(SystemCodeConstant.AREQ_0003);
					apprInfo.setStatus(SystemCodeConstant.ARST_0001);
					apprInfo.setRefTitle(billing.getName());
					apprInfo.setRefId(billing.getServiceBillingId());
					apprInfo.setDescription("서비스 정산 삭제 승인 요청 정보 입니다.");
					
					//정산ID, 정산명 2개가 키워드
					String keyword = createApprovalInfoKeyword(Arrays.asList(serviceBillingId, billing.getName()));
					apprInfo.setKeyword(keyword);
					
					Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
					
					//승인 정보 이력 생성
					approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
					
					backOfficeTransactionManager.commit(txStatus);
				}
				else {
					throw new RuntimeException("삭제 요청 할 서비스 정산 정보가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("승인대기 중인 정보가 있습니다.");
			}
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("서비스 정산 삭제 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("서비스 정산 삭제 요청 중 오류 발생");
		}
	}
	
	/**
	 * [서비스정산 수수료 등록] 승인 요청 
	 * @param serviceBillingId
	 * @param commision
	 * @param apprInfo
	 * @param beforeBillingEndDate
	 */
	public void createSubMerchantCommision(String serviceBillingId, SubMerchantCommision commision, Approval apprInfo, String beforeBillingEndDate) {
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createSubMerchantCommision");

		try {
			
			//정산 정보 조회
			SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(serviceBillingId);
			Long currentCommisionId = merchantRepository.readSubmerchantCurrentCommisionId(serviceBillingId);
			SubMerchantCommision previousCommision = merchantRepository.readSubmerchantCommision(currentCommisionId);
			previousCommision.setBillingEndDate(beforeBillingEndDate);
			
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();

			//기존 수수료 정보로 설정해서 previous content 설정
			billing.setCommision(previousCommision);
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
			
			//입력받은 수수료 정보로 설정해서 new content 설정
			billing.setCommision(commision);
			apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0008);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0008);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(billing.getName());
			apprInfo.setRefId(billing.getServiceBillingId());
			apprInfo.setDescription("서비스 정산 수수료 등록 승인 요청 정보 입니다.");
			
			//정산ID, 정산명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(billing.getServiceBillingId(), billing.getName()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("서비스 정산 수수료 등록 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("서비스 정산 수수료 등록 요청 중 오류 발생");
		}
	}
	
	/**
	 * [서비스정산 수수료 수정] 승인 요청 
	 * @param serviceBillingId
	 * @param commision
	 * @param apprInfo
	 * @param beforeBillingEndDate
	 */
	public void updateSubMerchantCommision(String serviceBillingId, SubMerchantCommision commision, Approval apprInfo, String beforeBillingEndDate) {
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createSubMerchantCommision");

		try {
			
			//정산 정보 조회
			SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(serviceBillingId);

			Long currentSecondCommisionId = merchantRepository.readSubmerchantSecondCurrentCommisionId(serviceBillingId);
			
			Long commisionId;
			if (currentSecondCommisionId != null ) {
				commisionId = currentSecondCommisionId;
			}
			else {
				commisionId = merchantRepository.readSubmerchantCurrentCommisionId(serviceBillingId);
			}
			SubMerchantCommision previousCommision = merchantRepository.readSubmerchantCommision(commisionId);
			previousCommision.setBillingEndDate(beforeBillingEndDate);
			
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();

			//기존 수수료 정보로 설정해서 previous content 설정
			billing.setCommision(previousCommision);
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
			
			//입력받은 수수료 정보로 설정해서 new content 설정
			commision.setBillingEndDate("21001231");
			billing.setCommision(commision);
			apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
			
			apprContent.setWorkType(SystemCodeConstant.AWRK_0008);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0008);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(billing.getName());
			apprInfo.setRefId(billing.getServiceBillingId());
			apprInfo.setDescription("서비스 정산 수수료 수정 승인 요청 정보 입니다.");
			
			//정산ID, 정산명 2개가 키워드
			String keyword = createApprovalInfoKeyword(Arrays.asList(billing.getServiceBillingId(), billing.getName()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("서비스 정산 수수료 수정 요청 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("서비스 정산 수수료 수정 요청 중 오류 발생");
		}
	}
	
	/**
	 * 요청정보가 [요청대기] 중인 [서비스정산 수수료] 요청 정보 수정
	 * @param serviceBillingId
	 * @param commision
	 * @param apprInfo
	 */
	public void updateApprovalRequestForSubMerchantCommision(String serviceBillingId, SubMerchantCommision commision, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForSubMerchantCommision");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(serviceBillingId);
				
				Long currentCommisionId = merchantRepository.readSubmerchantCurrentCommisionId(serviceBillingId);
				
				SubMerchantCommision previousCommision = merchantRepository.readSubmerchantCommision(currentCommisionId);
				if (commision.getBillingStartDate() == null || commision.getBillingStartDate().equals("")) {
					commision.setBillingStartDate(previousCommision.getBillingStartDate());
				}
				if (commision.getBillingEndDate() == null || commision.getBillingEndDate().equals("")) {
					commision.setBillingEndDate(previousCommision.getBillingEndDate());
				}
				billing.setCommision(commision);
				
				apprContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billing));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0008);
				
				//정산 수수료 컨텐츠 수정
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				
				//정산 수수료 컨텐츠 수정 히스토리 생성
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());

				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [서비스 정산] 정보 수정 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인대기 중인 [서비스 정산] 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 * [KCON 등록] 승인 요청
	 * @param brochure
	 * @param apprInfo
	 */
	public void createApprovalKconBrochure(ProductBrochure brochure, Approval apprInfo) {

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApprovalRequestForKconBrochure");
		
		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();

			//KCON 상품 content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(brochure));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0009);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0009);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0001);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(brochure.getTitle());
			apprInfo.setDescription("KCON 상품 등록 승인 요청 정보 입니다.");
			apprInfo.setKeyword(brochure.getTitle());
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("KCON 상품 등록 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			throw new RuntimeException("KCON 상품 등록 승인 요청 중 오류 발생");
		}
	}

	/**
	 * [KCON 수정] 승인 요청
	 * @param brochure
	 * @param apprInfo
	 */
	public void updateApprovalForKconBrochure(ProductBrochure brochure, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForKconBrochure");
		
		try {
			//승인 요청 컨텐츠 생성
			ApprovalContent apprContent = new ApprovalContent();

			//기존 정보 조회해서 Previous Content 만들어야함.
			ProductBrochure previousBrochure = couponService.readProductBrochure(brochure.getProdId());
			
			//KCON 상품 content 설정
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(previousBrochure));
			apprContent.setContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(brochure));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0009);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0009);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0002);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(brochure.getTitle());
			apprInfo.setRefId(brochure.getProdId());
			apprInfo.setDescription("KCON 상품 수정 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(brochure.getProdId(), brochure.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("KCON 상품 수정 승인 요청 중 오류 발생 : " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("KCON 상품 수정 승인  요청 중 오류 발생");
		}
	}
	
	/**
	 * 요청정보가 [요청대기] 중인 KCON 요청 정보 수정
	 * @param brochure Kcon 정보
	 * @param apprInfo 승인 요청 정보
	 */
	public void updateApprovalRequestForKconBrochure(ProductBrochure brochure, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForKconBrochure");
		
		try {
			if (apprInfo.getContentSeq() != null) {
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
				
				apprContent.setContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(brochure));
				apprContent.setWorkType(SystemCodeConstant.AWRK_0009);
				
				approvalRepository.updateApprovalContent(apprContent);
				
				Approval beforeApprInfo = approvalRepository.readApprovalInfoByContentSeq(apprInfo.getContentSeq());
				beforeApprInfo.setRefTitle(brochure.getTitle());
				
				List<String> keywordList = new ArrayList<String>();
				keywordList.add(brochure.getTitle());
				if (brochure.getProdId() != null) {
					keywordList.add(brochure.getProdId());
				}
				String keyword = createApprovalInfoKeyword(keywordList);
				
				beforeApprInfo.setKeyword(keyword);
				beforeApprInfo.setApprEmpId(apprInfo.getApprEmpId());
				beforeApprInfo.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(beforeApprInfo);
				approvalRepository.createKpcApprovalHistoryByRequestor(beforeApprInfo.getSeq(), apprInfo.getReqEmpId());

				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Content Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 * [KCON 삭제] 승인 요청
	 * @param brochure
	 * @param apprInfo
	 */
	public void deleteApprovalRequestForKconBrochure(ProductBrochure brochure, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "deleteApprovalRequestForKconBrochure");
		
		try {
			ApprovalContent apprContent = new ApprovalContent();

			//기존 정보 조회해서 Content 만들어야함.
			ProductBrochure targetBrochure = couponService.readProductBrochure(brochure.getProdId());
			
			//KCON 상품 content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(targetBrochure));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0009);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0009);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0003);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(targetBrochure.getTitle());
			apprInfo.setRefId(targetBrochure.getProdId());
			apprInfo.setDescription("KCON 상품 삭제 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(targetBrochure.getProdId(), targetBrochure.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("KCON 상품 삭제 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("KCON 상품 삭제 승인 요청 중 오류 발생");
		}
	}
	
	/**
	 * [충전권 연장] 승인 요청
	 * @param couponNo
	 * @param endDate
	 * @param apprInfo
	 */
	public void extendRequestChargeCoupon(String couponNo, String endDate, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "extendRequestChargeCoupon");
		
		try {
			ApprovalContent apprContent = new ApprovalContent();
			
			//기존 정보 조회해서 Content 만들어야함.
			ResAdminCoupon chargeCoupon = couponService.readChargeCoupon(couponNo);
			
			//기존 content 백업
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setChargeCouponJsonContent(chargeCoupon));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = dateFormat.parse(endDate);
			chargeCoupon.setExpireDate(date);
			
			//KCON 상품 content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setChargeCouponJsonContent(chargeCoupon));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0005);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0005);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0004);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(chargeCoupon.getTitle());
			apprInfo.setRefId(chargeCoupon.getCouponNo());
			apprInfo.setDescription("충전권 연장 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(chargeCoupon.getCouponNo(), chargeCoupon.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("충전권 연장 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("충전권 연장 승인 요청 중 오류 발생");
		}
	}
	
	/**
	 * [충전권 사용해제] 승인요청
	 * @param couponNo
	 * @param apprInfo
	 */
	public void unRestrictChargeCoupon(String couponNo, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "unRestrictChargeCoupon");
		
		try {
			ApprovalContent apprContent = new ApprovalContent();

			//기존 정보 조회해서 Content 만들어야함.
			ResAdminCoupon chrageCoupon = couponService.readChargeCoupon(couponNo);
			
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setChargeCouponJsonContent(chrageCoupon));
			if("Y".equals(chrageCoupon.getRestrictFlag())) {
				chrageCoupon.setRestrictFlag("N");
			}
			else {
				throw new RuntimeException("이미 사용해제 된 충전권입니다.");
			}
			
			//content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setChargeCouponJsonContent(chrageCoupon));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0005);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0005);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0005);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(chrageCoupon.getTitle());
			apprInfo.setRefId(chrageCoupon.getCouponNo());
			apprInfo.setDescription("충전권 사용해제 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(chrageCoupon.getCouponNo(), chrageCoupon.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("충전권 사용해제 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("충전권 사용해제 승인 요청 중 오류 발생");
		}
	}
	
	/**
	 * [충전권 승인 요청] 승인대기 정보 수정
	 * @param endDate
	 * @param apprInfo
	 */
	public void updateApprovalRequestForChargeCoupon(String endDate, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForChargeCoupon");
		
		try {
			if (apprInfo.getSeq() != null) {
				
				Approval targetAppr = approvalRepository.readApprovalInfo(apprInfo.getSeq());
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(targetAppr.getContentSeq());
				
				if(SystemCodeConstant.AREQ_0004.equals(targetAppr.getReqType())) {
					KconCouponContent couponContent = JsonContentToObjectConverter.convertKconCouponContent(apprContent.getContent());
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					Date expireDate = dateFormat.parse(endDate);
					couponContent.getCoupon().setExpireDate(expireDate);
					apprContent.setContent(ObejctToJsonContentConverter.setKconCouponJsonContent(couponContent.getCoupon()));

					approvalRepository.updateApprovalContent(apprContent);
				}
				targetAppr.setApprEmpId(apprInfo.getApprEmpId());
				targetAppr.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(targetAppr);
				
				approvalRepository.createKpcApprovalHistoryByRequestor(apprInfo.getSeq(), apprInfo.getReqEmpId());
				
				backOfficeTransactionManager.commit(txStatus);
				
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 * [KCON 쿠폰 연장] 승인 요청
	 * @param couponNo
	 * @param endDate
	 * @param apprInfo
	 */
	public void extendRequestKCoupon(String couponNo, String endDate, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "extendRequestKCoupon");
		
		try {
			ApprovalContent apprContent = new ApprovalContent();

			//기존 정보 조회해서 Content 만들어야함.
			KConDetail kconDetail = couponService.readKconCoupon(couponNo);
			
			//기존정보 백업
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setKconCouponJsonContent(kconDetail));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = dateFormat.parse(endDate);
			kconDetail.setExpireDate(date);
			
			//KCON 상품 content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setKconCouponJsonContent(kconDetail));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0010);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0010);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0004);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(kconDetail.getTitle());
			apprInfo.setRefId(kconDetail.getCouponNo());
			apprInfo.setDescription("KCON 쿠폰 연장 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(kconDetail.getCouponNo(), kconDetail.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("KCON 쿠폰 연장 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("KCON 쿠폰 연장 승인 요청 중 오류 발생");
		}
	}
	
	/**
	 * [KCON 쿠폰 사용해제] 승인 요청
	 * @param couponNo
	 * @param apprInfo
	 */
	public void unRestrictKCoupon(String couponNo, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "unRestrictKCoupon");
		
		try {
			ApprovalContent apprContent = new ApprovalContent();

			//기존 정보 조회해서 Content 만들어야함.
			KConDetail kconDetail = couponService.readKconCoupon(couponNo);
			
			//기존정보 백업
			apprContent.setPreviousContent(ObejctToJsonContentConverter.setKconCouponJsonContent(kconDetail));
			
			if("Y".equals(kconDetail.getRestrictFlag())) {
				kconDetail.setRestrictFlag("N");
			}
			else {
				throw new RuntimeException("이미 사용해제 된 쿠폰입니다.");
			}
			
			//KCON 상품 content 설정
			apprContent.setContent(ObejctToJsonContentConverter.setKconCouponJsonContent(kconDetail));
			apprContent.setWorkType(SystemCodeConstant.AWRK_0010);
			Long apprContentSeq = approvalRepository.createApprovalContent(apprContent);
			
			//승인 정보 생성 
			apprInfo.setContentSeq(apprContentSeq);
			apprInfo.setWorkType(SystemCodeConstant.AWRK_0010);
			apprInfo.setReqType(SystemCodeConstant.AREQ_0005);
			apprInfo.setStatus(SystemCodeConstant.ARST_0001);
			apprInfo.setRefTitle(kconDetail.getTitle());
			apprInfo.setRefId(kconDetail.getCouponNo());
			apprInfo.setDescription("KCON 쿠폰 사용해제 승인 요청 정보 입니다.");
			
			//키워드 생성
			String keyword = createApprovalInfoKeyword(Arrays.asList(kconDetail.getCouponNo(), kconDetail.getTitle()));
			apprInfo.setKeyword(keyword);
			
			Long apprSeq = approvalRepository.createApprovalInfo(apprInfo);
			
			//승인 정보 히스토리 생성
			approvalRepository.createKpcApprovalHistoryByRequestor(apprSeq, apprInfo.getReqEmpId());
			
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("KCON 쿠폰 사용해제 승인 요청 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("KCON 쿠폰 사용해제 승인 요청 중 오류 발생");
		}
	}
	
	public void updateApprovalRequestForKCoupon(String endDate, Approval apprInfo) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "updateApprovalRequestForKCoupon");
		
		try {
			if (apprInfo.getSeq() != null) {
				
				Approval targetAppr = approvalRepository.readApprovalInfo(apprInfo.getSeq());
				
				ApprovalContent apprContent = approvalRepository.readApprovalContent(targetAppr.getContentSeq());
				
				if(SystemCodeConstant.AREQ_0004.equals(targetAppr.getReqType())) {
					KconCouponContent couponContent = JsonContentToObjectConverter.convertKconCouponContent(apprContent.getContent());
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					Date expireDate = dateFormat.parse(endDate);
					couponContent.getCoupon().setExpireDate(expireDate);
					apprContent.setContent(ObejctToJsonContentConverter.setKconCouponJsonContent(couponContent.getCoupon()));

					approvalRepository.updateApprovalContent(apprContent);
				}
				targetAppr.setApprEmpId(apprInfo.getApprEmpId());
				targetAppr.setReqMemo(apprInfo.getReqMemo());
				approvalRepository.updateApprovalRequestInfo(targetAppr);
				
				approvalRepository.createKpcApprovalHistoryByRequestor(apprInfo.getSeq(), apprInfo.getReqEmpId());
				
				backOfficeTransactionManager.commit(txStatus);
			}
			else {
				throw new RuntimeException("요청정보 수정을 위한 Seq가 없습니다.");
			}
			
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("승인대기 중인 [KCON] 정보 수정 중 오류 발생");
		}
	}
	
	/**
	 * 승인 요청 정보 상세 조회
	 * @param seq
	 * @return
	 */
	public ResponseApprovalDetailInfo readApprovalDetailInfo(Long seq) {
		//승인 요청 정보 조회
		ResponseApprovalDetailInfo responseData = new ResponseApprovalDetailInfo();

		Approval apprInfo = approvalRepository.readApprovalInfo(seq);
		
		if (apprInfo != null) {
			
			//승인 요청 정보 내용 조회
			ApprovalContent apprContent = approvalRepository.readApprovalContent(apprInfo.getContentSeq());
			if (apprContent != null) {
				Employee requestor = employeeRepository.readEmployee(apprInfo.getReqEmpId(), null, null);
				Employee approver = employeeRepository.readEmployee(apprInfo.getApprEmpId(), null, null);
				
				responseData.setApprovalInfo(new ResponseApprovalInfo(apprInfo));
				responseData.getApprovalInfo().setReqEmpName(requestor.getName());
				responseData.getApprovalInfo().setApprEmpName(approver.getName());

				if (SystemCodeConstant.AWRK_0001.equals(apprContent.getWorkType())) {
					MerchantContent merchantContent = JsonContentToObjectConverter.convertMerchantContent(apprContent.getContent());
					responseData.setContent(merchantContent);
				}
				else if (SystemCodeConstant.AWRK_0002.equals(apprContent.getWorkType())) {
					SubMerchantContent subMerchantContent = JsonContentToObjectConverter.convertSubMerchantContent(apprContent.getContent());
					responseData.setContent(subMerchantContent);
				}
				else if (SystemCodeConstant.AWRK_0003.equals(apprContent.getWorkType())) {
					SubMerchantServiceContent subMerchantServiceContent = JsonContentToObjectConverter.convertSubMerchantServiceContent(apprContent.getContent());
					responseData.setContent(subMerchantServiceContent);
				}
				else if (SystemCodeConstant.AWRK_0004.equals(apprContent.getWorkType()) || SystemCodeConstant.AWRK_0008.equals(apprContent.getWorkType())) {
					SubMerchantSVCBillingContent serviceBillingContent = JsonContentToObjectConverter.convertSubMerchantSVCBillingContent(apprContent.getContent());
					responseData.setContent(serviceBillingContent);
				}
				else if (SystemCodeConstant.AWRK_0005.equals(apprContent.getWorkType())) {
					ChargeCouponContent content = JsonContentToObjectConverter.convertChargeCouponContent(apprContent.getContent());
					ChargeCouponContent previousContent = JsonContentToObjectConverter.convertChargeCouponContent(apprContent.getPreviousContent());
					
					responseData.setContent(content);
					responseData.setPreviousContent(previousContent);
				}
				else if (SystemCodeConstant.AWRK_0009.equals(apprContent.getWorkType())) {
					ProductBrochureContent brochureContent = JsonContentToObjectConverter.convertKconProductBrochureContent(apprContent.getContent());
					
					Employee creator = employeeRepository.readEmployee(brochureContent.getBrochure().getCreateId(), null, null);
					brochureContent.getBrochure().setCreateName(creator.getName());
					responseData.setContent(brochureContent);
				}
				else if (SystemCodeConstant.AWRK_0010.equals(apprContent.getWorkType())) {
					KconCouponContent couponContent = JsonContentToObjectConverter.convertKconCouponContent(apprContent.getContent());
					KconCouponContent previousContent = JsonContentToObjectConverter.convertKconCouponContent(apprContent.getPreviousContent());

					responseData.setContent(couponContent);
					responseData.setPreviousContent(previousContent);
				}
				else if (SystemCodeConstant.AWRK_0011.equals(apprContent.getWorkType())) {
					Gson gson =  new Gson();
					ReqBalanceRefundInfo cardContent = gson.fromJson(apprContent.getContent(),ReqBalanceRefundInfo.class);
					ReqBalanceRefundInfo previousContent = gson.fromJson(apprContent.getContent(),ReqBalanceRefundInfo.class);
					
					responseData.setContent(cardContent);
					responseData.setPreviousContent(previousContent);
				}
				else if (SystemCodeConstant.AWRK_0012.equals(apprContent.getWorkType())) {
					Gson gson =  new Gson();
					ReqRestrictCardInfo cardContent = gson.fromJson(apprContent.getContent(),ReqRestrictCardInfo.class);
					ReqRestrictCardInfo previousContent = gson.fromJson(apprContent.getContent(),ReqRestrictCardInfo.class);

					responseData.setContent(cardContent);
					responseData.setPreviousContent(previousContent);
				}
				return responseData;
			} else {
				throw new RuntimeException("승인 요청 정보의 컨텐츠가 없습니다.");
			}
		} else {
			throw new RuntimeException("승인 요청 정보가 없습니다.");
		}
	}
	
	/**
	 * 승인 신청내역 조회
	 * @param pagination 승인 페이징 객체
	 * @return
	 */
	public ResponseApprovalInfoList readApprovalRequestList(ApprovalPagination pagination){
		
		//목록 건수 조회
		int count = approvalRepository.readApprovalRequestListCount(pagination);
		
		List<ApprovalForList> approvalRequestList = approvalRepository.readApprovalRequestList(pagination);
		
		List<ResponseApprovalInfo> approvalList = approvalRequestList.stream().map(approval -> new ResponseApprovalInfo(approval)).collect(Collectors.toList());
		
		ResponseApprovalInfoList approvalInfoList = new ResponseApprovalInfoList();
		approvalInfoList.setCount(count);
		approvalInfoList.setResultList(approvalList);
		
		return approvalInfoList;
	}
	
	/**
	 * Excel파일 생성을 위한 승인 신청내역 조회
	 * @param pagination 승인 페이징 객체
	 * @return
	 */
	public List<ResponseApprovalInfo> readApprovalRequestListForExcel(ApprovalPagination pagination){
		
		List<ApprovalForList> approvalRequestList = approvalRepository.readApprovalRequestListForExcel(pagination);
		
		List<ResponseApprovalInfo> approvalExcelList = approvalRequestList.stream().map(approval -> new ResponseApprovalInfo(approval)).collect(Collectors.toList());

		return approvalExcelList;
	}
	
	/**
	 * 승인 결제내역 조회
	 * @param pagination 승인 페이징 객체
	 * @return
	 */
	public ResponseApprovalInfoList readApprovalAnswerList(ApprovalPagination pagination) {
		
		List<String> availableApprWorkTypeList = getAvailableApprWorkTypeList(pagination);
		
		//목록 건수 조회
		int count = approvalRepository.readApprovalAnswerListCount(pagination, availableApprWorkTypeList);
		
		List<ApprovalForList> approvalRequestList = approvalRepository.readApprovalAnswerList(pagination, availableApprWorkTypeList);
		
		List<ResponseApprovalInfo> approvalList = approvalRequestList.stream().map(approval -> new ResponseApprovalInfo(approval)).collect(Collectors.toList());
		
		ResponseApprovalInfoList approvalInfoList = new ResponseApprovalInfoList();
		approvalInfoList.setCount(count);
		approvalInfoList.setResultList(approvalList);
		
		return approvalInfoList;
	}

	/**
	 * Excel파일 생성을 위한 승인 결제내역 조회
	 * @param pagination 승인 페이징 객체
	 * @return
	 */
	public List<ResponseApprovalInfo> readApprovalAnswerListForExcel(ApprovalPagination pagination) {
		
		List<String> availableApprWorkTypeList = getAvailableApprWorkTypeList(pagination);
		
		List<ApprovalForList> approvalRequestList = approvalRepository.readApprovalAnswerListForExcel(pagination, availableApprWorkTypeList);
		
		List<ResponseApprovalInfo> approvalList = approvalRequestList.stream().map(approval -> new ResponseApprovalInfo(approval)).collect(Collectors.toList());
		
		return approvalList;
	}
	
	public ResponseApprovalInfoList readApprovalNotiList(ApprovalPagination pagination) {
		List<String> availableApprWorkTypeList = getAvailableApprWorkTypeList(pagination);
		
		//목록 건수 조회
		int count = approvalRepository.readApprovalNotiListCount(pagination.getLoginEmpId(), availableApprWorkTypeList);
		
		List<ApprovalForList> approvalRequestList = approvalRepository.readApprovalNotiList(pagination, availableApprWorkTypeList);
		
		List<ResponseApprovalInfo> approvalList = approvalRequestList.stream().map(approval -> new ResponseApprovalInfo(approval)).collect(Collectors.toList());
		
		ResponseApprovalInfoList approvalInfoList = new ResponseApprovalInfoList();
		approvalInfoList.setCount(count);
		approvalInfoList.setResultList(approvalList);
		
		return approvalInfoList;
	}
	
	/**
	 * 승인 요청 취소
	 * @param seq
	 * @param loginEmpId
	 */
	public void cancelApprovalRequest(Long seq, String loginEmpId) {
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "cancelApprovalRequest");
		
		try {
			
			Approval approvalInfo = approvalRepository.readApprovalInfo(seq);
			
			if (approvalInfo != null) {
				if (approvalInfo.getReqEmpId().equals(loginEmpId)) {
					
					//승인 요청 취소
					approvalRepository.cancelApprovalInfo(approvalInfo);
					approvalRepository.createKpcApprovalHistoryByRequestor(seq, loginEmpId);
					
				} else {
					throw new RuntimeException("요청 신청자와 요청 취소자가 불일치 합니다.");
				}
			} else {
				throw new RuntimeException("취소할 요청 정보가 없습니다.");
			}
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인요청 승인 처리 중 오류 발생 : " + e.getMessage());
			throw new RuntimeException("승인요청 승인 처리 중 오류 발생");
		}
	}
	
	/**
	 * 승인 요청 건 승인
	 * @param approvalList 승인 대상 목록
	 * @param apprEmpId 승인 처리자 사번
	 */
	public void approveApprovalRequest(List<Approval> approvalList, String apprEmpId){

		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "approveApprovalRequest");
		
		try {
			for (Approval approvalInfo : approvalList) {

				//승인 요청 정보 조회
				Approval approvalTarget = approvalRepository.readApprovalInfo(approvalInfo.getSeq());
				
				if (approvalTarget != null && SystemCodeConstant.ARST_0001.equals(approvalTarget.getStatus())) {
					//1.승인 [요청 정보], [요청 정보 히스토리]는 공통처리 대상
					approvalRepository.createKpcApprovalHistoryByApprover(approvalTarget.getSeq(), apprEmpId, SystemCodeConstant.ARST_0002);
					
					approvalTarget.setStatus(SystemCodeConstant.ARST_0002);
					approvalRepository.updateApprovalInfo(approvalTarget);
					
					//2.각 타입별 등록,수정,삭제에 따른 처리는 내부 메소드로 구현
					if(SystemCodeConstant.AWRK_0001.equals(approvalTarget.getWorkType())) {
						this.approveMerchant(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0002.equals(approvalTarget.getWorkType())) {
						this.approveSubMerchant(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0003.equals(approvalTarget.getWorkType())) {
						this.approveSubMerchantService(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0004.equals(approvalTarget.getWorkType())) {
						this.approveSubMerchantServiceBilling(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0005.equals(approvalTarget.getWorkType())) {
						this.approveChargeCoupon(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0006.equals(approvalTarget.getWorkType())) {
						boolean result = billingService.approveBillingApproval(approvalTarget);	
						if(!result){
							throw new RuntimeException("승인처리를 진행하는도중 오류가 발생했습니다.");
						}
					}
					else if (SystemCodeConstant.AWRK_0007.equals(approvalTarget.getWorkType())) {
						//TODO 경비
					}
					else if (SystemCodeConstant.AWRK_0008.equals(approvalTarget.getWorkType())) {
						this.approveSubMerchantCommision(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0009.equals(approvalTarget.getWorkType())) {
						this.approveKconProductBrochure(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0010.equals(approvalTarget.getWorkType())) {
						this.approveKconCoupon(approvalTarget, apprEmpId);
					}
					else if (SystemCodeConstant.AWRK_0011.equals(approvalTarget.getWorkType())) {						
						boolean result = cardService.approvePopCardRefundApproval(approvalTarget);
						if(!result){
							throw new RuntimeException("승인처리를 진행하는도중 오류가 발생했습니다.");
						}
					}					
					else if (SystemCodeConstant.AWRK_0012.equals(approvalTarget.getWorkType())) {
						boolean result = cardService.approvePopCardRestirctApproval(approvalTarget);
						if(!result){
							throw new RuntimeException("승인처리를 진행하는도중 오류가 발생했습니다.");
						}
					}
					else {
						throw new RuntimeException("승인요청 처리에 필요한 [업무유형] 코드가 없습니다.");
					}
				}
				else {
					throw new RuntimeException("승인하려는 요청 정보가 잘못 되었습니다.");
				}
			}
			backOfficeTransactionManager.commit(txStatus);
		} catch(Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인요청 승인 처리 중 오류 발생 in approveApprovalRequest Method");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("승인처리를 진행하는도중 오류가 발생했습니다.");
		}
	}

	/**
	 * 승인 요청 건 반려
	 * @param apprSequenceList 반려처리할 요청 정보 seq 목록
	 * @param apprEmpId 반려 처리자 사번
	 * @param apprMemo 반려 사유
	 */
	public void rejectApprovalReqeust(Set<Long> apprSequenceList, String apprEmpId, String apprMemo){
		
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "rejectApprovalReqeust");
		
		try {
			for(Long apprSeq : apprSequenceList){
				
				//반려하려는 승인 요청 정보 조회
				Approval approvalInfo = approvalRepository.readApprovalInfo(apprSeq);
				approvalInfo.setApprMemo(apprMemo);
				
				//1.승인 [요청 정보], [요청 정보 히스토리]는 공통처리 대상
				if(approvalInfo != null && SystemCodeConstant.ARST_0001.equals(approvalInfo.getStatus())) {
					approvalRepository.createKpcApprovalHistoryByApprover(approvalInfo.getSeq(), apprEmpId, SystemCodeConstant.ARST_0003);
					
					approvalInfo.setStatus(SystemCodeConstant.ARST_0003);
					approvalRepository.updateApprovalInfo(approvalInfo);								
				}
				else {
					throw new RuntimeException("반려하려는 요청 정보가 잘못 되었습니다.");
				}
			}

			backOfficeTransactionManager.commit(txStatus);
		} catch (Exception e) {
			backOfficeTransactionManager.rollback(txStatus);
			logger.error("승인요청 반려 처리 중 오류 발생 in rejectApprovalReqeust Method");
			logger.error(e.getMessage());
			throw new RuntimeException("승인처리를 진행하는도중 오류가 발생했습니다.");
		}
	}

	/**
	 * 승인자 목록 조회
	 * @param menuId
	 * @param loginEmpId 자신의 사번
	 * @return
	 */
	public List<Approver> readApproverList(String menuId, String loginEmpId){
		return approvalRepository.readApproverList(menuId, loginEmpId);
	}
	
	/**
	 * 승인요청(승인대기) 정보가 있는지 확인
	 * @param refId
	 * @return
	 */
	public boolean existApprovalRequestForRefId(String refId) {
		return approvalRepository.existApprovalRequestForRefId(refId);
	}

	/**
	 * 서비스에 정산정보가 있는지 확인
	 * @param serviceId
	 * @return
	 */
	public boolean existBillingByService(String serviceId) {
		return merchantRepository.existBillingByService(serviceId);
	}
	
	/**
	 * 재승인이 가능한 요청 정보 인지 확인. 
	 * @param workType
	 * @param refId
	 * @return
	 */
	public boolean possibleReApproval(String workType, String refId) {
		
		//TODO 업무유형이 추가될때마다 수정.
		if (SystemCodeConstant.AWRK_0001.equals(workType)) {
			return merchantRepository.existMerchant(refId); 
		}
		else if (SystemCodeConstant.AWRK_0002.equals(workType)) {
			return merchantRepository.existSubMerchant(refId); 
		}
		else if (SystemCodeConstant.AWRK_0003.equals(workType)) {
			return merchantRepository.existSubMerchantService(refId); 
		}
		else if (SystemCodeConstant.AWRK_0004.equals(workType) || SystemCodeConstant.AWRK_0008.equals(workType)) {
			return merchantRepository.existSubMerchantSVCBilling(refId); 
		}
		else if (SystemCodeConstant.AWRK_0005.equals(workType)) {
			ResAdminCoupon chargeCoupon = couponService.readChargeCoupon(refId);
			return "D".equals(chargeCoupon.getStatus()) ? false : true; // D는 폐기 
		}
		else if (SystemCodeConstant.AWRK_0006.equals(workType)) {
			return false; 
		}
		else if (SystemCodeConstant.AWRK_0007.equals(workType)) {
			return false; 
		}
		else if (SystemCodeConstant.AWRK_0009.equals(workType)) {
			ProductBrochure brochure = couponService.readProductBrochure(refId);
			return "N".equals(brochure.getDelFlag()) ? true : false;
		}
		else if (SystemCodeConstant.AWRK_0010.equals(workType)) {
			KConDetail kconDetail = couponService.readKconCoupon(refId);
			return SystemCodeConstant.CONS_0001.equals(kconDetail.getStatus()) ? true : false;			
		}
		else {
			throw new RuntimeException("업무 유형 코드가 잘못되었습니다.");
		}
	}
	
	public List<String> getAvailableApprWorkTypeList(ApprovalPagination pagination) {
		//로그인한 사용자가 가지고 있는 메뉴에 대한 승인 권한 조회
		List<String> menuList = employeeRepository.readEmployeeApprovalAuthList(pagination.getLoginEmpId());
		
		List<String> availableApprWorkTypeList = new ArrayList<>();
		for (String menuId : menuList) {
			if (SystemCodeConstant.AWRK_0001.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0001);
			}
			else if (SystemCodeConstant.AWRK_0002.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0002);
			}
			else if (SystemCodeConstant.AWRK_0003.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0003);
			}
			else if (SystemCodeConstant.AWRK_0004.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0004);
			}
			else if (SystemCodeConstant.AWRK_0005.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0005);
			}
			else if (SystemCodeConstant.AWRK_0006.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0006);
			}
			else if (SystemCodeConstant.AWRK_0007.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0007);
			}
			else if (SystemCodeConstant.AWRK_0008.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0008);
			}
			else if (SystemCodeConstant.AWRK_0009.equals(menuId)) {
				availableApprWorkTypeList.add(SystemCodeConstant.AWRK_0009);
			}
		}
		return availableApprWorkTypeList;
	}
	
	/**
	 * 요청 정보 생성시 키워드 만들기
	 * @param keywordList
	 * @return
	 */
	public String createApprovalInfoKeyword(List<String> keywordList) {
		return keywordList.stream().map(Object::toString).collect(Collectors.joining(", "));
	}
	
	/**
	 * 승인 요청 타입에 따른 대표 거래처 등록,수정,삭제 승인 처리
	 * @param approvalInfo 승인 처리할 승인 요청 정보
	 * @param apprEmpId 승인자 사번
	 */
	public boolean approveMerchant(Approval approvalInfo, String apprEmpId) {

		//임시 대표 거래처 정보 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0001.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//대표 거래처 정보를 json -> obejct 로 변경 
			MerchantContent merchantContent = JsonContentToObjectConverter.convertMerchantContent(approvalContent.getContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {
				String merchantId = merchantRepository.createMerchantId();
				
				Merchant merchantBasic = merchantContent.getMerchant();
				merchantBasic.setCreateDesc("대표 거래처 등록 승인 요청에 의한 등록");
				
				merchantRepository.createMerchantBasic(merchantId, merchantBasic);
				merchantRepository.createMerchantDetail(merchantId, merchantContent.getMerchant().getDetail());
				
				//등록 요청 정보에 ID 업데이트.
				merchantContent.getMerchant().setMerchantId(merchantId);
				approvalContent.setContent(ObejctToJsonContentConverter.setMerchantJsonContent(merchantContent.getMerchant()));
				approvalRepository.updateApprovalContent(approvalContent);
				
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					
					Merchant merchantBasic = merchantContent.getMerchant();
					merchantBasic.setUpdateDesc("대표 거래처 등록 승인 요청에 의한 수정");
					
					merchantRepository.updateMerchantBasic(merchantBasic);
					merchantRepository.updateMerchantDetail(approvalInfo.getRefId(), merchantContent.getMerchant().getDetail());
				}
				else {
					throw new RuntimeException("거래처 수정 승인에 필요한 거래처 ID가 없습니다.");
				}
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				if (approvalInfo.getRefId() != null) {
					merchantRepository.deleteBillingByMerchantId(approvalInfo.getRefId());
					merchantRepository.deleteServiceByMerchantId(approvalInfo.getRefId());
					merchantRepository.deleteSubMerchantByMerchantId(approvalInfo.getRefId());
					merchantRepository.deleteMerchant(approvalInfo.getRefId());
				}
				else {
					throw new RuntimeException("거래처 삭제 승인에 필요한 거래처 ID가 없습니다.");
				}
			}
		}
		return true;
	}
	
	/**
	 * 승인 요청 타입에 따른 일반 거래처 [등록],[수정],[삭제] 승인 처리
	 * @param approvalInfo 승인 처리할 승인 요청 정보
	 * @param apprEmpId 승인자 사번
	 */
	public boolean approveSubMerchant(Approval approvalInfo, String apprEmpId) {

		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0002.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//일반 거래처 정보를 json -> obejct 로 변경 
			SubMerchantContent subMerchantContent = JsonContentToObjectConverter.convertSubMerchantContent(approvalContent.getContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {
				
				subMerchantContent.getSubMerchant().setCreateDesc("일반 거래처 등록요청 승인 처리");
				String subMerchantId = merchantRepository.createSubMerchant(subMerchantContent.getSubMerchant());
				
				SubMerchantDetail detail = subMerchantContent.getSubMerchant().getDetail();
				detail.setSubMerchantId(subMerchantId);
				merchantRepository.createSubMerchantDetail(detail);
				
				//등록 요청 정보에 ID 업데이트.
				subMerchantContent.getSubMerchant().setSubMerchantId(subMerchantId);
				approvalContent.setContent(ObejctToJsonContentConverter.setSubMerchantJsonContent(subMerchantContent.getSubMerchant()));
				approvalRepository.updateApprovalContent(approvalContent);
				
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					subMerchantContent.getSubMerchant().setCreateDesc("일반 거래처 수정요청 승인 처리");
					merchantRepository.updateSubMerchant(subMerchantContent.getSubMerchant());
					merchantRepository.updateSubMerchantDetail(subMerchantContent.getSubMerchant().getDetail());
				}
				else {
					throw new RuntimeException("거래처 수정 승인에 필요한 거래처 ID가 없습니다.");
				}
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					merchantRepository.deleteBillingBySubMerchantId(approvalInfo.getRefId());
					merchantRepository.deleteServiceBySubMerchantId(approvalInfo.getRefId());
					merchantRepository.deleteSubMerchant(approvalInfo.getRefId());
				}
				else {
					throw new RuntimeException("거래처 삭제 승인에 필요한 거래처 ID가 없습니다.");
				}
				
			}
		}
		return true;
	}

	/**
	 * 승인 요청 타입에 따른 거래처 서비스 [등록],[수정],[삭제] 승인 처리
	 * @param approvalInfo 승인 처리할 승인 요청 정보
	 * @param apprEmpId 승인자 사번
	 */
	public boolean approveSubMerchantService(Approval approvalInfo, String apprEmpId) {

		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0003.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//대표 거래처 정보를 json -> obejct 로 변경 
			SubMerchantServiceContent serviceContent = JsonContentToObjectConverter.convertSubMerchantServiceContent(approvalContent.getContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {
				
				serviceContent.getService().setCreateDesc("거래처 서비스 등록요청 승인 처리");
				String serviceId = merchantRepository.createSubMerchantService(serviceContent.getService());
				
				
				//등록 요청 정보에 ID 업데이트.
				serviceContent.getService().setServiceId(serviceId);
				approvalContent.setContent(ObejctToJsonContentConverter.setSubMerchantServiceJsonContent(serviceContent.getService()));
				approvalRepository.updateApprovalContent(approvalContent);
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					serviceContent.getService().setUpdateDesc("거래처 서비스 수정요청 승인 처리");
					merchantRepository.updateSubMerchantService(serviceContent.getService());
				}
				else {
					throw new RuntimeException("거래처 서비스 수정 승인에 필요한 서비스 ID가 없습니다.");
				}
				
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					merchantRepository.deleteSubMerchantService(approvalInfo.getRefId());
					merchantRepository.deleteServiceBillingByServiceId(approvalInfo.getRefId());
				}
				else {
					throw new RuntimeException("거래처 서비스 삭제 승인에 필요한 서비스 ID가 없습니다.");
				}
			}
		}
		return true;
	}
	
	public boolean approveSubMerchantServiceBilling(Approval approvalInfo, String apprEmpId) {

		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0004.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//서비스정산 정보를 json -> obejct 로 변경 
			SubMerchantSVCBillingContent billingContent = JsonContentToObjectConverter.convertSubMerchantSVCBillingContent(approvalContent.getContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {
				billingContent.getBilling().setCreateDesc("서비스 정산 등록요청 승인 처리");
				billingContent.getBilling().setCreateAdmId(approvalInfo.getReqEmpId()); //정산정보 생성자는 등록요청한 사번
				
				String serviceBillingId = merchantRepository.createServiceBilling(billingContent.getBilling());
				billingContent.getBilling().getCommision().setServiceBillingId(serviceBillingId);
				billingContent.getBilling().getCommision().setCreateAdmId(approvalInfo.getReqEmpId());
				
				billingContent.getBilling().getCommision().setBillingStartDate(billingContent.getBilling().getCommision().getBillingStartDate());
				
				merchantRepository.createServiceBillingCommision(billingContent.getBilling().getCommision());
				
				//등록 요청 정보에 ID 업데이트.
				billingContent.getBilling().setServiceBillingId(serviceBillingId);
				approvalContent.setContent(ObejctToJsonContentConverter.setSubMerchantSVCBillingJsonContent(billingContent.getBilling()));
				approvalRepository.updateApprovalContent(approvalContent);
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {
					billingContent.getBilling().setUpdateDesc("서비스 정산 수정요청 승인 처리");
					merchantRepository.updateServiceBilling(billingContent.getBilling());
				}
				else {
					throw new RuntimeException("서비스 정산 수정 승인에 필요한 정산 ID가 없습니다.");
				}
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				//현재 정산 정보에 대한 삭제 기능은 없다. 필요하다면 여기에 구현
			}
		}
		return true;
	}
	
	public boolean approveSubMerchantCommision(Approval approvalInfo, String apprEmpId) {

		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0008.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//정산수수료 정보를 json -> obejct 로 변경 
			SubMerchantSVCBillingContent billingContent = JsonContentToObjectConverter.convertSubMerchantSVCBillingContent(approvalContent.getContent());
			SubMerchantSVCBillingContent billingPreviousContent = JsonContentToObjectConverter.convertSubMerchantSVCBillingContent(approvalContent.getPreviousContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {				
				merchantRepository.updateBillingCommisionBillingEndDate(
						billingPreviousContent.getBilling().getServiceBillingId(), 
						billingPreviousContent.getBilling().getCommision().getId(),
						billingPreviousContent.getBilling().getCommision().getBillingEndDate());
				
				billingContent.getBilling().getCommision().setCreateAdmId(approvalInfo.getReqEmpId());
				merchantRepository.createServiceBillingCommision(billingContent.getBilling().getCommision());
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				
				if (approvalInfo.getRefId() != null) {					
					//최근 수수료 정보 조회해서 그 정보를 업데이트
					Long currentSecondCommisionId = merchantRepository.readSubmerchantSecondCurrentCommisionId(billingContent.getBilling().getServiceBillingId());
					if (currentSecondCommisionId != null ) {
						merchantRepository.updateBillingCommisionBillingEndDate(
								billingPreviousContent.getBilling().getServiceBillingId(), 
								billingPreviousContent.getBilling().getCommision().getId(),
								billingPreviousContent.getBilling().getCommision().getBillingEndDate());
					}
					
					merchantRepository.updateServiceBillingCommision(billingContent.getBilling().getCommision());
				}
				else {
					throw new RuntimeException("서비스 정산 수정 승인에 필요한 정산 ID가 없습니다.");
				}
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				//현재 정산 수수료 정보에 대한 삭제 기능은 없다. 필요하다면 여기에 구현
			}
		}
		return true;
	}
	
	/**
	 * 충전권에 대한 승인 요청 수락
	 * @param approvalInfo
	 * @param apprEmpId
	 */
	public boolean approveChargeCoupon(Approval approvalInfo, String apprEmpId) {
		
		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0005.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			ChargeCouponContent content = JsonContentToObjectConverter.convertChargeCouponContent(approvalContent.getContent());
			
			if (SystemCodeConstant.AREQ_0004.equals(approvalInfo.getReqType())) {
				String endDate = DateUtil.convertDateToString(content.getCoupon().getExpireDate(), "yyyyMMdd");
				endDate = endDate+"235959";
				
				couponService.extendChargeCoupon(content.getCoupon().getCouponNo(), endDate);
			}
			else if (SystemCodeConstant.AREQ_0005.equals(approvalInfo.getReqType())) {

				couponService.unRestrictionChargeCoupon(content.getCoupon().getCouponNo());
			}
		}
		return true;		
	}
	
	public boolean approveKconProductBrochure(Approval approvalInfo, String apprEmpId) {

		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0009.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			//KCON 정보를 json -> obejct 로 변경 
			ProductBrochureContent content = JsonContentToObjectConverter.convertKconProductBrochureContent(approvalContent.getContent());
			
			//등록 승인 요청 처리
			if (SystemCodeConstant.AREQ_0001.equals(approvalInfo.getReqType())) {
				String kconProductId = couponService.createKconProductBrochure(content.getBrochure());
				
				//등록 요청 정보에 ID 업데이트.
				content.getBrochure().setProdId(kconProductId);
				approvalContent.setContent(ObejctToJsonContentConverter.setProductBrochureJsonContent(content.getBrochure()));
				approvalRepository.updateApprovalContent(approvalContent);
				
			}
			//수정 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0002.equals(approvalInfo.getReqType())) {
				couponService.updateProductBrochure(content.getBrochure());
			}
			//삭제 승인 요청 처리
			else if (SystemCodeConstant.AREQ_0003.equals(approvalInfo.getReqType())) {
				couponService.deleteProductBrochure(content.getBrochure().getProdId());
			}
		}
		return true;
	}
	
	public boolean approveKconCoupon(Approval approvalInfo, String apprEmpId) {
		
		//승인 컨텐츠 조회
		ApprovalContent approvalContent = approvalRepository.readApprovalContent(approvalInfo.getContentSeq());
		
		if (SystemCodeConstant.AWRK_0010.equals(approvalInfo.getWorkType()) && approvalContent != null) {
			
			KconCouponContent content = JsonContentToObjectConverter.convertKconCouponContent(approvalContent.getContent());
			
			if (SystemCodeConstant.AREQ_0004.equals(approvalInfo.getReqType())) {
				String startDate = DateUtil.nowDateToString("yyyyMMddHHmmss");
				String endDate = DateUtil.convertDateToString(content.getCoupon().getExpireDate(), "yyyyMMdd");
				endDate = endDate+"235959";
				
				couponService.extendKconCoupon(content.getCoupon().getCouponNo(), startDate, endDate);
			}
			else if (SystemCodeConstant.AREQ_0005.equals(approvalInfo.getReqType())) {

				couponService.unRestrictionKconCoupon(content.getCoupon().getCouponNo(), content.getCoupon().getRestrictFlag());
			}
		}
		return true;
	}	
}
