package kr.co.kpcard.backoffice.repository.merchant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantBasicMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantDetailMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.MerchantMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantBasicMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantCommisionMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantDetailMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantSVCBillingMapper;
import kr.co.kpcard.backoffice.repository.merchant.mapper.SubMerchantServiceMapper;
import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;

/**
 * 거래처 레파지토리
 * Created by @author : MinWook on 2018. 6. 4.
 *
 */
@Repository
public class NewMerchantRepository {
	
	//새로 만든 맵퍼
	@Autowired private MerchantBasicMapper merchantBasicMapper; 
	@Autowired private MerchantDetailMapper merchantDetailMapper;
	@Autowired private SubMerchantBasicMapper subMerchantBasicMapper;
	@Autowired private SubMerchantDetailMapper subMerchantDetailMapper;
	@Autowired private SubMerchantServiceMapper subMerchantServiceMapper;
	@Autowired private SubMerchantSVCBillingMapper subMerchantSVCBillingMapper;
	@Autowired private SubMerchantCommisionMapper subMerchantCommisionMapper;
	
	//기존 맵퍼
	@Autowired private MerchantMapper merchantMapper;
	
	/**
	 * 대표 거래처 기본 정보 조회
	 * @param merchantId
	 * @return
	 */
	public Merchant readMerchantBasic(String merchantId) {
		return merchantBasicMapper.readMerchant(merchantId);
	}

	/**
	 * 대표 거래처 부가 정보 조회
	 * @param merchantId
	 * @return
	 */
	public MerchantDetail readMerchantDetail(String merchantId) {
		return merchantDetailMapper.readMerchant(merchantId);
	}

	/**
	 * 대표 거래처 아이디 생성
	 * @return
	 */
	public String createMerchantId() {
		return merchantMapper.createMerchantId();
	}
	
	/**
	 * 승인처리에 의한 대표 거래처 기본 정보 생성
	 * @param merchantId
	 * @param merchantBasic
	 */
	public void createMerchantBasic(String merchantId, Merchant merchantBasic) {
		merchantMapper.createMerchant(merchantId, merchantBasic);
	}

	/**
	 * 승인처리에 의한 대표 거래처 상세 정보 생성
	 * @param merchantId
	 * @param merchantDetail
	 */
	public void createMerchantDetail(String merchantId, MerchantDetail merchantDetail) {
		merchantMapper.createMerchantDetail(merchantId, merchantDetail);
	}
	
	/**
	 * 승인처리에 의한 대표 거래처 기본 정보 수정
	 * @param merchantTmp
	 */
	public void updateMerchantBasic(Merchant merchantBasic) {
		merchantBasicMapper.updateMerchant(merchantBasic);
	}
	
	/**
	 * 승인처리에 의한 대표 거래처 부가 정보 수정
	 * @param merchantId 
	 * @param merchantDetail
	 */
	public void updateMerchantDetail(String merchantId, MerchantDetail merchantDetail) {
		merchantDetailMapper.updateMerchantDetail(merchantId, merchantDetail);
	}
	
	/**
	 * 대표 거래처 삭제 처리
	 * @param merchantId
	 */
	public void deleteMerchant(String merchantId) {
		merchantMapper.deleteMerchantByMerchant(merchantId);
	}
	
	/**
	 * 대표 거래처 ID에 해당하는 일반 거래처 삭제 처리
	 * @param merchantId
	 */
	public void deleteSubMerchantByMerchantId(String merchantId) {
		merchantMapper.deleteMerchantBySubMerchant(merchantId);
	}

	/**
	 * 대표 거래처 ID에 해당하는 서비스 삭제 처리
	 * @param merchantId
	 */
	public void deleteServiceByMerchantId(String merchantId) {
		merchantMapper.deleteMerchantByService(merchantId);
	}
	
	/**
	 * 대표 거래처 ID에 해당하는 정산 정보 삭제 처리
	 * @param merchantId
	 */
	public void deleteBillingByMerchantId(String merchantId) {
		merchantMapper.deleteMerchantByBilling(merchantId);
	}
	
	/**
	 * 삭제되지 않은 대표 거래처가 있는지 확인
	 * @param merchantId
	 * @return
	 */
	public boolean existMerchant(String merchantId) {
		return merchantBasicMapper.existMerchant(merchantId);
	}
	
	/* 일반 거래처----------------------------------------------------------- */
	
	/**
	 * 일반 거래처 기본정보 생성
	 * @param subMerchant
	 * @return 일반거래처 ID
	 */
	public String createSubMerchant(SubMerchant subMerchant) {
		//일반 거래처 아이디 생성
		String subMerchantId = merchantMapper.createSubMerchantId();
		subMerchant.setSubMerchantId(subMerchantId);
		subMerchantBasicMapper.createSubMerchant(subMerchant);
		return subMerchantId;
	}

	/**
	 * 일반 거래처 부가정보 생성
	 * @param subMerchantDetail
	 */
	public void createSubMerchantDetail(SubMerchantDetail subMerchantDetail) {
		subMerchantDetailMapper.createSubMerchantDetail(subMerchantDetail);
	}
	
	/**
	 * 일반 거래처 기본정보 조회
	 * @param subMerchantId
	 * @return
	 */
	public SubMerchant readSubMerchantBasic(String subMerchantId) {
		return subMerchantBasicMapper.readSubMerchant(subMerchantId); 
	}
	
	/**
	 * 일반 거래처 부가정보 조회
	 * @param subMerchantId
	 * @return
	 */
	public SubMerchantDetail readSubMerchantDetail(String subMerchantId) {
		return subMerchantDetailMapper.readSubMerchantDetail(subMerchantId); 
	}
	
	/**
	 * 삭제되지 않은 일반 거래처가 있는지 확인
	 * @param subMerchantId
	 * @return
	 */
	public boolean existSubMerchant(String subMerchantId) {
		return subMerchantBasicMapper.existSubMerchant(subMerchantId);
	}
	
	/**
	 * 일반 거래처 기본정보 수정
	 * @param subMerchant
	 */
	public void updateSubMerchant(SubMerchant subMerchant) {
		subMerchantBasicMapper.updateSubMerchant(subMerchant);
	}
	
	/**
	 * 일반 거래처 부가정보 수정
	 * @param subMerchantDetail
	 */
	public void updateSubMerchantDetail(SubMerchantDetail subMerchantDetail) {
		subMerchantDetailMapper.updateSubMerchantDetail(subMerchantDetail);
	}
	
	/**
	 * 일반 거래처 삭제 처리
	 * @param subMerchantId
	 */
	public void deleteSubMerchant(String subMerchantId) {
		merchantMapper.deleteSubMerchantBySubMerchant(subMerchantId);	
	}
	
	/**
	 * 일반 거래처 ID에 해당하는 서비스 삭제 처리
	 * @param merchantId
	 */
	public void deleteServiceBySubMerchantId(String subMerchantId) {
		merchantMapper.deleteSubMerchantByService(subMerchantId);		
	}
	
	/**
	 * 일반 거래처 ID에 해당하는 정산 정보 삭제 처리
	 * @param merchantId
	 */
	public void deleteBillingBySubMerchantId(String subMerchantId) {
		merchantMapper.deleteSubMerchantByBilling(subMerchantId);	
	}
	
	public String readSubMerchantPath(String subMerchantId) {
		return merchantMapper.readSubMerchantPath(subMerchantId);
	}
	
	/* 거래처 서비스 ----------------------------------------------------------- */
	
	/**
	 * 거래처 서비스 생성
	 * @param service
	 */
	public String createSubMerchantService(SubMerchantService service) {
		String serviceId = merchantMapper.createServiceId();
		service.setServiceId(serviceId);
		subMerchantServiceMapper.createService(service);
		
		return serviceId;
	}
	
	/**
	 * 거래처 서비스 조회
	 * @param serviceId
	 * @return
	 */
	public SubMerchantService readSubMerchantService(String serviceId) {
		return subMerchantServiceMapper.readService(serviceId);
	}
	
	/**
	 * 삭제되지 않은 거래처 서비스가 있는지 확인
	 * @param subMerchantId
	 * @return
	 */
	public boolean existSubMerchantService(String serviceId) {
		return subMerchantServiceMapper.existSubMerchantService(serviceId);
	}
	
	/**
	 * 거래처 서비스 수정
	 * @param service
	 */
	public void updateSubMerchantService(SubMerchantService service) {
		subMerchantServiceMapper.updateService(service);
	}
	
	/**
	 * 거래처 서비스 삭제
	 * @param serviceId
	 */
	public void deleteSubMerchantService(String serviceId) {
		merchantMapper.deleteService(serviceId);	
	}

	/**
	 * 거래처 서비스에 해당하는 정산 정보 삭제
	 * @param serviceId
	 */
	public void deleteServiceBillingByServiceId(String serviceId) {
		merchantMapper.deleteServiceByBilling(serviceId);
	}
	
	/* 서비스 정산 ----------------------------------------------------------- */

	/**
	 * 서비스 정산 정보 등록
	 * @param billing
	 */
	public String createServiceBilling(SubMerchantSVCBilling billing) {
		String billingId = merchantMapper.createBillingId();
		billing.setServiceBillingId(billingId);
		subMerchantSVCBillingMapper.createServiceBilling(billing);
		
		return billingId;
	}
	
	/**
	 * 서비스 정산 정보 조회
	 * @param serviceBillingId
	 * @return
	 */
	public SubMerchantSVCBilling readSubMerchantSVCBilling(String serviceBillingId) {
		return subMerchantSVCBillingMapper.readServiceBilling(serviceBillingId);
	}
	
	/**
	 * 서비스ID로 서비스 정산 정보 조회
	 * @param serviceBillingId
	 * @return
	 */
	public SubMerchantSVCBilling readSubMerchantSVCBillingByServiceId(String serviceId) {
		return subMerchantSVCBillingMapper.readServiceBillingByServiceId(serviceId);
	}
	
	/**
	 * 삭제되지 않은 서비스정산 정보가 있는지 확인
	 * @param serviceBillingId
	 * @return
	 */
	public boolean existSubMerchantSVCBilling(String serviceBillingId) {
		return subMerchantSVCBillingMapper.existSubMerchantSVCBilling(serviceBillingId);
	}
	
	/**
	 * 서비스 정산 정보 수정
	 * @param billing
	 */
	public void updateServiceBilling(SubMerchantSVCBilling billing) {
		subMerchantSVCBillingMapper.updateServiceBilling(billing);
	}

	/**
	 * 서비스 정산 정보 삭제
	 * @param serviceBillingId
	 */
	public void deleteServiceBilling(String serviceBillingId) {
		merchantMapper.deleteBilling(serviceBillingId);
	}

	/**
	 * 정산 수수료 정보 등록
	 * @param commision
	 */
	public void createServiceBillingCommision(SubMerchantCommision commision) {
		Long commisionId = subMerchantCommisionMapper.createCommisionId();
		commision.setId(commisionId);
		subMerchantCommisionMapper.createSubMerchantCommision(commision);
	}
	
	public void updateServiceBillingCommision(SubMerchantCommision commision) {
		subMerchantCommisionMapper.updateSubMerchantCommision(commision);
	}
	
	/**
	 * 서비스정산 정보의 수수료 이력 조회
	 * @param serviceBillingId
	 * @return
	 */
	public List<SubMerchantCommision> readSubmerchantCommisionHistories(String serviceBillingId) {
		return subMerchantCommisionMapper.readSubMerchantCommisionHistories(serviceBillingId);
	}
	
	/**
	 * 서비스정산 수수료 정보 조회
	 * @param commisionId
	 * @return
	 */
	public SubMerchantCommision readSubmerchantCommision(Long commisionId) {
		return subMerchantCommisionMapper.readSubMerchantCommision(commisionId);
	}

	/**
	 * 정산정보의 최근 수수료정보의 commisionId 조회
	 * @param serviceBillingId
	 * @return
	 */
	public Long readSubmerchantCurrentCommisionId(String serviceBillingId) {
		return subMerchantCommisionMapper.readSubmerchantCurrentCommisionId(serviceBillingId);
	}
	
	/**
	 * 정산정보의 최근 두번째 수수료정보의 commisionId 조회
	 * @param serviceBillingId
	 * @return
	 */
	public Long readSubmerchantSecondCurrentCommisionId(String serviceBillingId) {
		return subMerchantCommisionMapper.readSubmerchantSecondCurrentCommisionId(serviceBillingId);
	}
	
	/**
	 * 오늘 날짜에 해당하는 정산 수수료 정보 조회
	 * @param serviceBillingId
	 * @return
	 */
	public SubMerchantCommision readSubmerchantTodayCommision(String serviceBillingId) {
		return subMerchantCommisionMapper.readSubmerchantTodayCommision(serviceBillingId);
	}
	
	/**
	 * 수수료 정보의 수수료 적용 종료일 수정
	 * @param serviceBillingId
	 * @param commisionId
	 * @param billingEndDate
	 */
	public void updateBillingCommisionBillingEndDate(String serviceBillingId, Long commisionId, String billingEndDate) {
		subMerchantCommisionMapper.updateBillingCommisionBillingEndDate(serviceBillingId, commisionId, billingEndDate);
	}

	/**
	 * 서비스에 정산정보가 있는지 확인
	 * @param ServiceId
	 * @return
	 */
	public boolean existBillingByService(String ServiceId) {
		return subMerchantSVCBillingMapper.existBillingByService(ServiceId);
	}

}
