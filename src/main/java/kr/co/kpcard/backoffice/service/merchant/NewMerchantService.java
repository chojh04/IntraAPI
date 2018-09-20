package kr.co.kpcard.backoffice.service.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseMerchant;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseSubMerchantBilling;
import kr.co.kpcard.backoffice.controller.merchant.protocol.ResponseSubMerchantPath;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.merchant.NewMerchantRepository;
import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;

/**
 * 거래처 관련 서비스
 * Created by @author : MinWook on 2018. 6. 4.
 *
 */
@Service
public class NewMerchantService {

	private final Logger logger = LoggerFactory.getLogger(NewMerchantService.class);
	
	@Autowired
	private NewMerchantRepository merchantRepository;
	
	@Autowired
	private NewApprovalRepository approvalRepository;
	
	/**
	 * 거래처 상세 정보 조회
	 * @param merchantId 거래처 아이디
	 * @return
	 */
	public ResponseMerchant readMerchantInfo(String merchantId) {
		
		//거래처 기본정보 조회
		Merchant merchantBasic = merchantRepository.readMerchantBasic(merchantId);
		
		//거래처 부가정보 조회
		MerchantDetail merchantDetail = merchantRepository.readMerchantDetail(merchantId);
		
		if (merchantBasic != null && merchantDetail != null) {
			ResponseMerchant merchantInfo = new ResponseMerchant(merchantBasic, merchantDetail);
			
			//현재 승인 요청건이 있는지 확인.
			boolean existRequest = approvalRepository.existApprovalRequestForRefId(merchantId);
			if(existRequest) {
				merchantInfo.setApprovalRequesting(true);
			} else {
				merchantInfo.setApprovalRequesting(false);
			}
			
			return merchantInfo;
		}
		else {
			logger.error("거래처 조회 중 오류");
			logger.error("거래처 아이디 "+merchantId+"에 해당하는 거래처 정보가 조회되지 않습니다.");
			throw new RuntimeException("거래처 아이디 "+merchantId+"에 해당하는 거래처 정보가 조회되지 않습니다.");
		}
	}
	
	/**
	 * 일반 거래처 경로 조회
	 * @param subMerchantId
	 * @return
	 */
	public ResponseSubMerchantPath readSubMerchantPath(String subMerchantId) {
		
		ResponseSubMerchantPath response = new ResponseSubMerchantPath();
		response.setPath(merchantRepository.readSubMerchantPath(subMerchantId));
		
		return response;
	}
	
	/**
	 * 서비스정산 수수료 이력 조회
	 * @param serviceId
	 * @return
	 */
	public List<ResponseSubMerchantBilling> readSubmerchantCommisionHistories(String serviceId) {

		//정산 정보 조회
		SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBillingByServiceId(serviceId);
		
		if (billing != null) {
			//정산 수수료 이력 조회
			List<SubMerchantCommision> commisionHistories = merchantRepository.readSubmerchantCommisionHistories(billing.getServiceBillingId());
			
			return commisionHistories.stream().map(commision -> new ResponseSubMerchantBilling(billing, commision)).collect(Collectors.toList());
		}
		else
			return new ArrayList<ResponseSubMerchantBilling>();
	}

	/**
	 * 수수료 정산 정보 조회
	 * @param serviceBillingId
	 * @param searchType
	 * @return
	 */
	public ResponseSubMerchantBilling readSubMerchantServiceBilling(String serviceBillingId, String searchType) {
		
		//서비스정산 정보 조회
		SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(serviceBillingId);
		
		ResponseSubMerchantBilling result = new ResponseSubMerchantBilling();
		result.setBilling(billing);
		
		if ("today".equals(searchType)) {
			//오늘 날짜에 해당하는 정산 수수료 정보 조회
			
			SubMerchantCommision commision = merchantRepository.readSubmerchantTodayCommision(serviceBillingId);
			
			if (commision != null) {
				result.setCommision(commision);
			}
			else {
				readCurrentCommision(serviceBillingId, result);
			}
		}
		else if ("last".equals(searchType)) {
			readCurrentCommision(serviceBillingId, result);
		}
		else if ("last-second".equals(searchType)) {
			//정산 수수료 마지막 두번째 정보 조회
			Long lastSecondId = merchantRepository.readSubmerchantSecondCurrentCommisionId(serviceBillingId);
			
			if (lastSecondId == null) {
				//수수료 정보가 최초 등록건만 이라면. 오늘날짜부터 시작일을 수정할 수 있게 하기 위해 수수료 적용일을 없애서 클라이언트에 전달. 
				readCurrentCommision(serviceBillingId, result);
				result.getCommision().setBillingStartDate("");
			}
			else {
				//정산 수수료 정보가 1건이면 null이므로 currentId로 다시 조회해야합니다.
				result.setCommision(merchantRepository.readSubmerchantCommision(lastSecondId));
			}
			
		}
		
		return result;
	}

	/**
	 * 	정산 수수료 마지막(최근) 정보 조회
	 * @param serviceBillingId
	 * @param result
	 */
	private void readCurrentCommision(String serviceBillingId, ResponseSubMerchantBilling result) {
		Long lastId = merchantRepository.readSubmerchantCurrentCommisionId(serviceBillingId);
		result.setCommision(merchantRepository.readSubmerchantCommision(lastId));
	}

	/**
	 * commisionId로 서비스정산 정보 조회
	 * @param commisionId
	 * @return
	 */
	public ResponseSubMerchantBilling readSubMerchantServiceBillingByCommisionId(Long commisionId) {
		
		SubMerchantCommision commision = merchantRepository.readSubmerchantCommision(commisionId);
		
		SubMerchantSVCBilling billing = merchantRepository.readSubMerchantSVCBilling(commision.getServiceBillingId());
		
		ResponseSubMerchantBilling result = new ResponseSubMerchantBilling();
		result.setBilling(billing);
		result.setCommision(commision);
		
		return result;
	}
}
