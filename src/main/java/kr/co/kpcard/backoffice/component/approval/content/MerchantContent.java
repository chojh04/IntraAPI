package kr.co.kpcard.backoffice.component.approval.content;

import kr.co.kpcard.backoffice.repository.merchant.model.Merchant;
import lombok.Getter;
import lombok.Setter;

/**
 * 승인 요청 정보(Content)에 사용할 대표 거래처 클래스
 * PREVIOUS_CONTENT, CONTENT에 JSON타입으로 insert,update,select 할때 사용
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
@Getter
@Setter
public class MerchantContent {
	
	private Merchant merchant;

}
