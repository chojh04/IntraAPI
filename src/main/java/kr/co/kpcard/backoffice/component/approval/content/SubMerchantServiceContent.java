package kr.co.kpcard.backoffice.component.approval.content;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchantService;
import lombok.Data;

/**
 * 승인 요청 정보(Content)에 사용할 거래처 서비스 클래스
 * PREVIOUS_CONTENT, CONTENT에 JSON타입으로 insert,update,select 할때 사용
 * Created by @author : MinWook on 2018. 7. 5.
 *
 */
@Data
public class SubMerchantServiceContent {

	private SubMerchantService service;
	
}
