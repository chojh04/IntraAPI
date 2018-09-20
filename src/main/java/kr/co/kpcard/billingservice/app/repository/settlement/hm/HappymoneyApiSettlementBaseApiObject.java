package kr.co.kpcard.billingservice.app.repository.settlement.hm;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class HappymoneyApiSettlementBaseApiObject implements Serializable{
	
	private static final long serialVersionUID = -8323330169637208425L;
	
	private String resultCode;
	private String message;
	private String ayMethod;
	private String tartDate;
	private String ndDate;
	private List<?> list;
	private long   payCount;
	private long   payAmount;
	private long   cancelCount;
	private long   cancelAmount;
	private long   totalCount;
	private long   totalAmount;
}
