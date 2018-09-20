package kr.co.kpcard.backoffice.controller.approval;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kpcard.backoffice.component.FailureMessageException;
import kr.co.kpcard.backoffice.controller.approval.protocol.ApprovalPagination;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalCancellationForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestApprovalRejectionForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestCouponForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestMerchantForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestProductBrochureForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestBalanceRefundCardInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestRestrictCardInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestSubMerchantForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestSubMerchantSVCBillingForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.RequestSubMerchantServiceForm;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalDetailInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfo;
import kr.co.kpcard.backoffice.controller.approval.protocol.ResponseApprovalInfoList;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.Approver;
import kr.co.kpcard.backoffice.repository.approval.model.CardRestrictUsage;
import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import kr.co.kpcard.backoffice.repository.merchant.model.MerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantCommision;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantDetail;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantSVCBilling;
import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
import kr.co.kpcard.backoffice.service.approval.NewApprovalService;
import kr.co.kpcard.backoffice.service.card.ReqBalanceRefundInfo;
import kr.co.kpcard.backoffice.service.card.ReqRestrictCardInfo;
import kr.co.kpcard.backoffice.service.coupon.model.ProductBrochure;

@RestController
@RequestMapping("/approvals")
public class NewApprovalController {

	private final Logger logger = LoggerFactory.getLogger(NewApprovalController.class);
	
	@Autowired
	NewApprovalService approvalService;
	
	/**
	 * [대표거래처 등록, 수정] 승인 요청
	 * @param requestParam 수정
	 * @return
	 */
	@RequestMapping(value="/request/merchant", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> insertMerchant(@RequestBody RequestMerchantForm form, HttpServletRequest request){

		Merchant merchant = setMerchant(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), null);
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			//대표 거래처에 [등록 승인 요청] 정보 생성
			approvalService.createApprovalForMerchant(merchant, apprInfo);
			
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			//대표 거래처 [수정 승인 요청] 정보 생성
			apprInfo.setRefId(form.getMerchantId());
			approvalService.updateApprovalForMerchant(merchant, apprInfo);
		}
		
		return ResponseEntity.ok().build();
	}

	/**
	 * [대표거래처] 승인 요청 수정
	 * @return
	 */
	@PutMapping("/request/merchant/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForMerchant(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestMerchantForm form ) {
		
		Merchant merchant = setMerchant(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForMerchant(merchant, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [대표거래처 삭제] 승인 요청
	 * @param requestForm
	 * @return
	 */
	@PutMapping("/request/merchant/delete")
	public ResponseEntity<Void> deleteMerchant(@RequestBody RequestMerchantForm form){
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setRefId(form.getMerchantId());

		//대표 거래처 [삭제 승인 요청] 정보 생성
		approvalService.deleteApprovalForMerchant(form.getMerchantId(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	//--- 일반거래처 ------------------------------------------------------------------------------------------
	
	/**
	 * [일반거래처 등록, 수정] 승인 요청 
	 * @param from
	 * @return
	 */
	@RequestMapping(value="/request/sub-merchant", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> insertSubMerchant(@RequestBody RequestSubMerchantForm form, HttpServletRequest request) {
		
		//일반 거래처 정보 설정
		SubMerchant subMerchant = this.setSubMerchant(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			//일반 거래처 [등록 승인 요청] 정보 생성
			approvalService.createApprovalForSubMerchant(subMerchant, apprInfo);
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			//일반 거래처 [수정 승인 요청] 정보 생성
			apprInfo.setRefId(form.getSubMerchantId());
			approvalService.updateApprovalForSubMerchant(subMerchant, apprInfo);
		}
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [일반거래처] 승인 요청 수정
	 * @return
	 */
	@PutMapping("/request/sub-merchant/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForSubMerchant(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestSubMerchantForm form ) {
		
		//일반 거래처 정보 설정
		SubMerchant subMerchant = this.setSubMerchant(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForSubMerchant(subMerchant, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [일반거래처 삭제] 승인 요청
	 * @param requestForm
	 * @return
	 */
	@PutMapping("/request/sub-merchant/delete")
	public ResponseEntity<Void> deleteSubMerchant(@RequestBody RequestSubMerchantForm form){
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setRefId(form.getSubMerchantId());
		
		//대표 거래처 [삭제 승인 요청] 정보 생성
		approvalService.deleteApprovalForSubMerchant(form.getSubMerchantId(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	//--- 거래처서비스 ------------------------------------------------------------------------------------------
	
	/**
	 * [거래처서비스 등록, 수정] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/sub-merchant/service", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> insertSubMerchantService(@RequestBody RequestSubMerchantServiceForm form, HttpServletRequest request) {
		
		//거래처 서비스 정보 설정
		SubMerchantService service = this.setSubMerchanServicet(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			//[거래처서비스 등록] 승인 요청
			approvalService.createApprovalForSubMerchantService(service, apprInfo);
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			//[거래처서비스 수정] 승인 요청
			approvalService.updateApprovalForSubMerchantService(service, apprInfo);
		}
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [거래처서비스] 승인 요청 수정
	 * @return
	 */
	@PutMapping("/request/sub-merchant/service/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForSubMerchantService(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestSubMerchantServiceForm form ) {
		
		//일반 거래처 정보 설정
		SubMerchantService service = this.setSubMerchanServicet(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForSubMerchantService(service, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [거래처서비스 삭제] 승인 요청
	 * @param form
	 * @return
	 */
	@PutMapping("/request/sub-merchant/service/delete")
	public ResponseEntity<Void> deleteSubMerchantService(@RequestBody RequestSubMerchantServiceForm form) {
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());

		//[거래처서비스 삭제] 승인 요청
		approvalService.deleteApprovalForSubMerchantService(form.getServiceId(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	//--- 서비스정산 ------------------------------------------------------------------------------------------
	
	/**
	 * [서비스정산 등록, 수정] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/sub-merchant/service/billing", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> insertSubMerchantSVCBilling(@RequestBody RequestSubMerchantSVCBillingForm form, HttpServletRequest request) {
		
		//서비스 정산 수수료 설정
		SubMerchantCommision commision = this.setSubMerchantCommision(form);
		
		//서비스정산 정보 설정
		SubMerchantSVCBilling billing = this.setSubMerchantSVCBilling(form, commision);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			//[서비스정산 등록] 승인 요청
			approvalService.createApprovalForSubMerchantSVCBilling(billing, apprInfo);
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			//[서비스정산 등록] 승인 요청
			approvalService.updateApprovalForSubMerchantSVCBilling(billing, apprInfo);
		}
		
		return ResponseEntity.ok().build();
	}
		
	/**
	 * [서비스정산] 승인 요청 수정
	 * @return
	 */
	@PutMapping("/request/sub-merchant/service/billing/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForSubMerchantBilling(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestSubMerchantSVCBillingForm form ) {
		
		//서비스 정산 수수료 설정
		SubMerchantCommision commision = this.setSubMerchantCommision(form);
		
		//서비스정산 정보 설정
		SubMerchantSVCBilling billing = this.setSubMerchantSVCBilling(form, commision);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForSubMerchantSVCBilling(billing, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [서비스정산 수수료 등록, 수정] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/sub-merchant/service/billing/commision", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> insertSubMerchantCommision(@RequestBody RequestSubMerchantSVCBillingForm form, HttpServletRequest request) {
		//서비스 정산 수수료 설정
		SubMerchantCommision commision = this.setSubMerchantCommision(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			//[서비스정산 등록] 승인 요청
			approvalService.createSubMerchantCommision(form.getServiceBillingId(), commision, apprInfo, form.getBeforeBillingEndDate());
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			//[서비스정산 수정] 승인 요청
			approvalService.updateSubMerchantCommision(form.getServiceBillingId(), commision, apprInfo, form.getBeforeBillingEndDate());
		}
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [서비스정산 수수료] 승인 요청 수정
	 * @return
	 */
	@PutMapping("/request/sub-merchant/service/billing/commision/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForBillingCommision(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestSubMerchantSVCBillingForm form ) {
		
		//서비스 정산 수수료 설정
		SubMerchantCommision commision = this.setSubMerchantCommision(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForSubMerchantCommision(form.getServiceBillingId(), commision, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [KCON 등록,수정] 승인 요청
	 * @param form
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/request/kcon/brochure", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Void> createKconBrochure(@RequestBody RequestProductBrochureForm form, HttpServletRequest request) {
		
		ProductBrochure brochure = this.setProductBrochure(form);

		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		if (request.getMethod().equals(HttpMethod.POST.toString())) {
			approvalService.createApprovalKconBrochure(brochure, apprInfo);
		}
		else if (request.getMethod().equals(HttpMethod.PUT.toString())) {
			approvalService.updateApprovalForKconBrochure(brochure, apprInfo);
		}
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [KCON 삭제] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/kcon/brochure/delete", method={RequestMethod.PUT})
	public ResponseEntity<Void> deleteKconBrochure(@RequestBody RequestProductBrochureForm form) {
		
		ProductBrochure brochure = this.setProductBrochure(form);
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		approvalService.deleteApprovalRequestForKconBrochure(brochure, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	

	/**
	 * [충전권 연장] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/coupon/charge/extension", method={RequestMethod.PUT})
	public ResponseEntity<Void> extendChargeCouponExpireDate(@RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		approvalService.extendRequestChargeCoupon(form.getCouponNo(), form.getEndDate(), apprInfo);
		
		return ResponseEntity.ok().build();
	}

	/**
	 * [충전권 사용해제] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/coupon/charge/un-restriction", method={RequestMethod.PUT})
	public ResponseEntity<Void> unRestrictCouponCharge(@RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		approvalService.unRestrictChargeCoupon(form.getCouponNo(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [충전권] 승인대기 정보 수정
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/coupon/charge/{apprSeq}/update", method={RequestMethod.PUT})
	public ResponseEntity<Void> updateApprovalRequestChargeCoupon(
			@PathVariable("apprSeq") Long apprSeq, @RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setSeq(apprSeq);
		
		approvalService.updateApprovalRequestForChargeCoupon(form.getEndDate(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [KCON 쿠폰 기간연장] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/kcon/coupon/extension", method={RequestMethod.PUT})
	public ResponseEntity<Void> extendKCouponExpireDate(@RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		approvalService.extendRequestKCoupon(form.getCouponNo(), form.getEndDate(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [KCON 쿠폰 사용해제] 승인 요청
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/kcon/coupon/un-restriction", method={RequestMethod.PUT})
	public ResponseEntity<Void> unRestrictKCoupon(@RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		
		approvalService.unRestrictKCoupon(form.getCouponNo(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * [KCON 쿠폰] 승인대기 정보 수정
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/request/kcon/coupon/extension/{apprSeq}/update", method={RequestMethod.PUT})
	public ResponseEntity<Void> updateApprovalRequestKCoupon(
			@PathVariable("apprSeq") Long apprSeq, @RequestBody RequestCouponForm form) {
		
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setSeq(apprSeq);
		
		approvalService.updateApprovalRequestForKCoupon(form.getEndDate(), apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * [KCON] 승인대기 상태인 정보 수정
	 * @param apprContentSeq
	 * @param form
	 * @return
	 */
	@PutMapping("/request/kcon/brochure/{apprContentSeq}/update")
	public ResponseEntity<Void> updateApprovalRequestForKconBrochure(
			@PathVariable("apprContentSeq") Long apprContentSeq, @RequestBody RequestProductBrochureForm form) {
		
		//KCON 정보 설정
		ProductBrochure brochure = this.setProductBrochure(form);
		
		//승인 요청 정보
		Approval apprInfo = this.setApprovalInfo(form.getReqEmpId(), form.getApprEmpId(), form.getReqMemo());
		apprInfo.setContentSeq(apprContentSeq);
		
		approvalService.updateApprovalRequestForKconBrochure(brochure, apprInfo);
		
		return ResponseEntity.ok().build();
	}
	
	
		
	/**
	 * 승인자 목록 조회
	 * @param menuId
	 * @param loginEmpId
	 * @return
	 */
	@GetMapping("/approvers")
	public ResponseEntity<List<Approver>> readApproverList(
			@RequestParam(value="menuId", required=true) String menuId,
			@RequestParam(value="loginEmpId", required=true) String loginEmpId){
		
		List<Approver> approvers = approvalService.readApproverList(menuId, loginEmpId);
		
		return ResponseEntity.ok(approvers);
	}
	
	/**
	 * 승인 신청내역 조회
	 * @param pagination
	 * @return
	 */
	@GetMapping("/request")
	public ResponseEntity<ResponseApprovalInfoList> readApprovalRequestList (@ModelAttribute ApprovalPagination pagination){

		ResponseApprovalInfoList approvalRequestList = approvalService.readApprovalRequestList(pagination);
		
		return ResponseEntity.ok(approvalRequestList);
	}
		
	/**
	 * Excel파일 생성을 위한 승인 신청내역 조회
	 * @param pagination
	 * @return
	 */
	@GetMapping("/request/excel")
	public ResponseEntity<List<ResponseApprovalInfo>> readApprovalRequestListForExcel (@ModelAttribute ApprovalPagination pagination){
		
		List<ResponseApprovalInfo> approvalRequestExcelList = approvalService.readApprovalRequestListForExcel(pagination);
		
		return ResponseEntity.ok(approvalRequestExcelList);
	}
	
	/**
	 * 승인 결제내역 조회
	 * @param pagination
	 * @return
	 */
	@GetMapping("/answer")
	public ResponseEntity<ResponseApprovalInfoList> readApprovalAnswerList (@ModelAttribute ApprovalPagination pagination){
		
		ResponseApprovalInfoList approvalAnswerList = approvalService.readApprovalAnswerList(pagination);
		
		return ResponseEntity.ok(approvalAnswerList);
	}
	
	/**
	 * Excel파일 생성을 위한 승인 결제내역 조회
	 * @param pagination
	 * @return
	 */
	@GetMapping("/answer/excel")
	public ResponseEntity<List<ResponseApprovalInfo>> readApprovalAnswerListForExcel(@ModelAttribute ApprovalPagination pagination){
		
		List<ResponseApprovalInfo> approvalAnswerList = approvalService.readApprovalAnswerListForExcel(pagination);
		
		return ResponseEntity.ok(approvalAnswerList);
	}
	
	/**
	 * 승인대기 중인 승인요청건 조회
	 * @param pagination
	 * @return
	 */
	@GetMapping("/answer/noti-list")
	public ResponseEntity<ResponseApprovalInfoList> readApprovalNotiList(@ModelAttribute ApprovalPagination pagination) {
		
		ResponseApprovalInfoList approvalNotiList = approvalService.readApprovalNotiList(pagination);
		
		return ResponseEntity.ok(approvalNotiList);
	}
	
	/**
	 * 신청자의 승인 요청 상세 조회
	 * @param seq
	 * @param empId
	 * @return
	 */
	@GetMapping("/detail/{seq}")
	public ResponseEntity<ResponseApprovalDetailInfo> readApprovalDetailByRequestor(@PathVariable("seq") Long seq){

		ResponseApprovalDetailInfo result = approvalService.readApprovalDetailInfo(seq);
		
		return ResponseEntity.ok(result);
	}

	/**
	 * 승인 요청 취소
	 * @return
	 */
	@PutMapping("/request/cancellation")
	public ResponseEntity<Void> cancelApprovalRequest(@RequestBody RequestApprovalCancellationForm form) {
		
		approvalService.cancelApprovalRequest(form.getSeq(), form.getLoginEmpId());
		
		return ResponseEntity.ok().build();
	}
		
	/**
	 * 승인 요청 승인
	 * @param form
	 * @return
	 */
	@PostMapping("/response/approval")
	public ResponseEntity<Void> approveApprovalRequest(@RequestBody RequestApprovalForm form){ 
		
		approvalService.approveApprovalRequest(form.getApprovalList(), form.getEmpId());
				
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 승인 요청 반려
	 * @param form
	 * @return
	 */
	@PostMapping("/response/rejection")
	public ResponseEntity<Void> rejectApprovalReqeust(@RequestBody RequestApprovalRejectionForm form){
		
		approvalService.rejectApprovalReqeust(form.getRejectApprSeqList(), form.getApprEmpId(), form.getApprMemo());
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 승인 요청한 정보가 있는지 확인
	 * @param refId
	 * @return
	 */
	@GetMapping("/request/{refId}/exist")
	public ResponseEntity<Boolean> existApprovalRequest(@PathVariable("refId")String refId) {
		
		boolean isExist = approvalService.existApprovalRequestForRefId(refId);
		
		return ResponseEntity.ok(isExist);
	}

	/**
	 * 서비스에 정산 정보가 있는지 확인
	 * @param refId
	 * @return
	 */
	@GetMapping("/request/billing/exist/{serviceId}")
	public ResponseEntity<Boolean> existBillingByService(@PathVariable("serviceId")String serviceId) {
		
		boolean isExist = approvalService.existBillingByService(serviceId);
		
		return ResponseEntity.ok(isExist);
	}
	
	/**
	 * 재승인이 가능한 승인 정보 인지 확인
	 * @param workType
	 * @param refId
	 * @return
	 */
	@GetMapping("/request/{workType}/{refId}/re-approval/possibility")
	public ResponseEntity<Boolean> possibleReApproval(@PathVariable("workType")String workType, @PathVariable("refId")String refId) {
		
		boolean isPossible = approvalService.possibleReApproval(workType, refId);
		
		return ResponseEntity.ok(isPossible);
	}
	
	/**
	 * client에서 넘어온 데이터 [대표거래처] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @return
	 */
	private Merchant setMerchant(RequestMerchantForm form) {
		//대표 거래처 기본 정보 
		Merchant merchant = new Merchant();
		merchant.setMerchantId(form.getMerchantId());
		merchant.setName(form.getName());
		merchant.setAlias(form.getAlias());
		merchant.setUseFlag(form.getUseFlag());
		
		//대표 거래처 상세 정보
		MerchantDetail merchantDetail = new MerchantDetail();
		merchantDetail.setMerchantId(form.getMerchantId());
		merchantDetail.setBizKind(form.getBizKind());
		merchantDetail.setBizCond(form.getBizCond());
		merchantDetail.setBizGrp(form.getBizGrp());
		merchantDetail.setCorpRegNo(form.getCorpRegNo());
		merchantDetail.setBizRegNo(form.getBizRegNo());
		merchantDetail.setOpenDate(form.getOpenDate());
		merchantDetail.setCeoName(form.getCeoName());
		merchantDetail.setZipCode(form.getZipCode());
		merchantDetail.setAddress(form.getAddress());
		merchantDetail.setAddressDetail(form.getAddressDetail());
		merchantDetail.setTel(form.getTel());
		merchantDetail.setFax(form.getFax());
		
		merchant.setDetail(merchantDetail);
		return merchant;
	}
		
	/**
	 * client에서 넘어온 데이터 [일반거래처] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @return
	 */
	private SubMerchant setSubMerchant(RequestSubMerchantForm form) {
		
		//일반 거래처 기본정보
		SubMerchant subMerchant = new SubMerchant();
		subMerchant.setSubMerchantId(form.getSubMerchantId());
		subMerchant.setParentId(form.getParentId());
		subMerchant.setName(form.getName());
		subMerchant.setAlias(form.getAlias());
		subMerchant.setAgentId(form.getAgentId());
		subMerchant.setAgentPw(form.getAgentPw());
		subMerchant.setUseFlag(form.getUseFlag());
		
		//일반 거래처 상세정보
		SubMerchantDetail subMerchantDetail = new SubMerchantDetail();
		subMerchantDetail.setSubMerchantId(form.getSubMerchantId());
		subMerchantDetail.setOpenDate(form.getOpenDate());
		subMerchantDetail.setBizRegNo(form.getBizRegNo());
		subMerchantDetail.setCorpRegNo(form.getCorpRegNo());
		subMerchantDetail.setBizKind(form.getBizKind());
		subMerchantDetail.setBizCond(form.getBizCond());
		subMerchantDetail.setCeoName(form.getCeoName());
		subMerchantDetail.setZipCode(form.getZipCode());
		subMerchantDetail.setAddress(form.getAddress());
		subMerchantDetail.setAddressDetail(form.getAddressDetail());
		subMerchantDetail.setTel(form.getTel());
		subMerchantDetail.setFax(form.getFax());
		subMerchantDetail.setType(form.getType());
		subMerchantDetail.setUrlHome(form.getUrlHome());

		subMerchantDetail.setBankName(form.getBankName());
		subMerchantDetail.setBankAccountNo(form.getBankAccountNo());
		subMerchantDetail.setBankHolder(form.getBankHolder());
		
		subMerchantDetail.setTaxCustName(form.getTaxCustName());
		subMerchantDetail.setTaxTel(form.getTaxTel());
		subMerchantDetail.setTaxFax(form.getTaxFax());
		subMerchantDetail.setTaxPhone(form.getTaxPhone());
		subMerchantDetail.setTaxEmail(form.getTaxEmail());
		
		subMerchantDetail.setSalesName(form.getSalesName());
		subMerchantDetail.setSalesTel(form.getSalesTel());
		subMerchantDetail.setBillingName(form.getBillingName());
		subMerchantDetail.setBillingTel(form.getBillingTel());
		
		subMerchantDetail.setKpcSalesName(form.getKpcSalesName());
		subMerchantDetail.setKpcSalesTel(form.getKpcSalesTel());
		subMerchantDetail.setKpcBillingName(form.getKpcBillingName());
		subMerchantDetail.setKpcBillingTel(form.getKpcBillingTel());

		subMerchant.setDetail(subMerchantDetail);
		
		return subMerchant;
	}
	
	/**
	 * client에서 넘어온 데이터 [거래처서비스] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @return
	 */
	private SubMerchantService setSubMerchanServicet(RequestSubMerchantServiceForm form) {
		
		SubMerchantService service = new SubMerchantService();
		
		service.setSubMerchantId(form.getSubMerchantId());
		service.setServiceId(form.getServiceId());
		service.setName(form.getServiceName());
		service.setType(form.getType());
		service.setCategory(form.getCategory());
		service.setSaleDivider(form.getSaleDivider());
		service.setUseFlag(form.getUseFlag());
		service.setSvcConnId(form.getSvcConnId());
		service.setSvcConnPw(form.getSvcConnPw());
		service.setCreateDesc(form.getCreateDesc());
		service.setCreateAdmId(form.getCreateAdmId());
		
		service.setUpdateDesc(form.getUpdateDesc());
		service.setUpdateAdmId(form.getUpdateAdmId());
		
		return service;
	}
	
	/**
	 * client에서 넘어온 데이터 [서비스 정산 수수료] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @return
	 */
	private SubMerchantCommision setSubMerchantCommision(RequestSubMerchantSVCBillingForm form) {
		SubMerchantCommision commision = new SubMerchantCommision();
		commision.setId(form.getCommisionId());
		commision.setServiceBillingId(form.getServiceBillingId());
		
		commision.setBillingStartDate(form.getBillingStartDate());
		
		commision.setBillingDate(form.getBillingDate());
		commision.setBillingDuration(form.getBillingDuration());
		commision.setBillingCommisionType(form.getBillingCommisionType());
		
		commision.setMerchantCommisionType(form.getMerchantCommisionType());
		commision.setMerchantCommision(form.getMerchantCommision());
		commision.setMerchantTaxType(form.getMerchantTaxType());
		
		return commision;
	}
	
	/**
	 * client에서 넘어온 데이터 [서비스 정산] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @param commision 정산 수수료
	 * @return
	 */
	private SubMerchantSVCBilling setSubMerchantSVCBilling(RequestSubMerchantSVCBillingForm form, SubMerchantCommision commision) {
		
		SubMerchantSVCBilling billing = new SubMerchantSVCBilling();
		billing.setServiceBillingId(form.getServiceBillingId());
		billing.setServiceId(form.getServiceId());
		billing.setName(form.getName());
		
		billing.setBankName(form.getBankCode());
		billing.setBankAccountNo(form.getBankAccountNo());
		billing.setBankHolder(form.getBankHolder());
		
		billing.setManagerName(form.getManagerName());
		billing.setManagerTel(form.getManagerTel());
		billing.setManagerEmail(form.getManagerEmail());
		
		billing.setKpcManagerName(form.getKpcManagerName());
		billing.setKpcManagerTel(form.getKpcManagerTel());
		billing.setKpcManagerEmail(form.getKpcManagerEmail());
		
		billing.setCode(form.getCode());
		billing.setDivider(form.getDivider());
		
		billing.setCommision(commision);
		
		return billing;
	}
	
	/**
	 * client에서 넘어온 데이터 [KCON 상품] 등록,수정을 위한 객체 맵핑 메소드
	 * @param form
	 * @return
	 */
	private ProductBrochure setProductBrochure(RequestProductBrochureForm form) {
		
		ProductBrochure brochure = new ProductBrochure();
		brochure.setProdId(form.getProductId());
		brochure.setTitle(form.getTitle());
		brochure.setType(form.getTypeCode());
		brochure.setTypeDetail(form.getTypeDetail());
		brochure.setAmount(form.getAmount());
		brochure.setExpireDays(form.getExpireDays());
		brochure.setExpireDaysType(form.getExpireDaysType());
		brochure.setSeller(form.getSeller());
		brochure.setUsage(form.getUsage());
		brochure.setCouponNoLength(form.getCouponLength());
		brochure.setStatus(form.getStatus());
		brochure.setCreateId(form.getReqEmpId());
		
		return brochure;
	}
	
	/**
	 * 승인요청 정보 설정
	 * @param reqEmpId
	 * @param apprEmpId
	 * @param reqMemo
	 * @return
	 */
	private Approval setApprovalInfo(String reqEmpId, String apprEmpId, String reqMemo) {
		Approval apprInfo = new Approval();
		apprInfo.setReqEmpId(reqEmpId);
		apprInfo.setApprEmpId(apprEmpId);
		apprInfo.setReqMemo(reqMemo);
		
		return apprInfo;
	}
}
