package kr.co.kpcard.backoffice.service.coupon.protocol;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KConDetail {
	private String couponNo;
	private String title;
	private String status;
	private String useFlag;
	private String prodType;
	private String prodName;
	private String couponType;
	private String couponTypeName;
	private String productId;
	private Date issueDate;
	private Date useDate;
	private Date expireDate;
	private long amount;
	private String cardNumber;
	private String restrictFlag;
	private String seller;

	public KConDetail(){}
	
	

}
