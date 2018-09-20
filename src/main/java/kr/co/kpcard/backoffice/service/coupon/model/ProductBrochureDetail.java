package kr.co.kpcard.backoffice.service.coupon.model;

import java.util.Date;

import lombok.Data;

/**
 * KCON 상품 상세 클래스
 * Created by @author : MinWook on 2018. 7. 16.
 *
 */
@Data
public class ProductBrochureDetail {
	
    private String prodId;    
    private String expireDaysType;    
    private String createId;    
    private Date createDate;    
    private Date updateDate;

}
