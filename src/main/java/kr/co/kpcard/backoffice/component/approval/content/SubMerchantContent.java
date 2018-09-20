package kr.co.kpcard.backoffice.component.approval.content;

import kr.co.kpcard.backoffice.repository.merchant.model.SubMerchant;
import lombok.Data;

/**
 * 승인 요청 정보(Content)에 사용할 일반 거래처 클래스
 * PREVIOUS_CONTENT, CONTENT에 JSON타입으로 insert,update,select 할때 사용

 * Created by @author : MinWook on 2018. 7. 2.
 *
 */
@Data
public class SubMerchantContent {
	
	private SubMerchant subMerchant;
}
