package kr.co.kpcard.backoffice.controller.merchant.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMerchantAction {
	private String status;
	private String message;
	private String data;
}
