package kr.co.kpcard.backoffice.repository.account.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountPayment {
	private String seq;                    // 결제승인 시퀀스        
	private String summ_seq;               // 결제승인 요약 시퀀스     
	private String submerchant_id;         // 거래처 아이디         
	private String submerchant_nm;         // 거래처명            
	private String service_id;             // 서비스 시퀀스         
	private String service_nm;             // 서비스명            
	private String service_type;           // 서비스타입           
	private String service_category;       // 서비스 카테고리        
	private String svc_conn_id;                               
	private String deal_divider;           // 거래 구분(거래일/영업일)  
	private String deal_dt;                // 거래 구분 일(거래일/영업일)
	private Date approval_dt;            //                 
	private String approval_status_nm;     //                 
	private String approval_status;        // 승인상태(결제/취소)     
	private String approval_no;            // 승인번호            
	private String order_no;               // 주문번호            
	private String sale_divider;           //                 
	private String saleDividerName;        //                 
	private String cardCdName;             //                 
	private String card_cd;                // 카드코드            
	private String card_nm;                // 카드명             
	private String card_no;                // 카드번호            
	private String card_enc_no;            // 암호화 카드번호        
	private String card_mng_no;            // 카드관리번호          
	private long amount;                 // 결제 금액
	private long origAmount;                 // 결제 금액   
	private long dcAmount;                 // 결제 금액   
	private String balance;                // 잔액              
	private String payTypeName;            //                 
	private String pay_type;               // 결제 타입           
	private String pay_method;             // 결제 수단           
	private String payMethodName;          //                 
	private String store_cd;               // 매장(점포) 코드       
	private String store_nm;               // 매장(점포) 이름       
	private String pos_no;                 // 매장(점포) POS No   
	private String cp_id;                  // CP ID           
	private String cp_user_id;             // CP User ID      
	private String cp_user_ip;             // CP User IP      
	private String agent_id;               // AGENT_ID        
	private String commision;              // 거래처 수수료                  
	private String merchant_commision;     // 거래처 수수료         
	private String merchant_tax_type;      // 거래처부가세타입        
	private String merchant_tax;           // 거래처 부가세         
	private String kpc_comm_type;          // KPC수수료타입        
	private float kpc_commision;          // KPC 수수료         
	private String kpc_tax_type;           // KPC부가세타입        
	private String kpc_tax;                // KPC 부가세         
	private String add_comm_type01;        // 추가 수수료타입01      
	private String add_commision01;        // 부가 수수료 01       
	private String add_tax_type01;         // 추가 부가세타입01      
	private String add_tax01;              // 부가 부가세 01       
	private String add_comm_type02;        // 추가 수수료타입02      
	private String add_commision02;        // 부가 수수료 02       
	private String add_tax_type02;         // 추가 부가세타입02      
	private String add_tax02;              // 부가 부가세 02       
	private String billing_sum;            // 정산 금액           
	private String desc01;                 // 승인 상세01         
	private String desc02;                 // 승인 상세02         
	
	public String getLingUrl()
	{
		return "/approvals/payments/payment?seq=" + this.seq;
	}
	
	public String getApproval_dt()
	{		
		SimpleDateFormat transForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return transForm.format(this.approval_dt); 
	}
}
