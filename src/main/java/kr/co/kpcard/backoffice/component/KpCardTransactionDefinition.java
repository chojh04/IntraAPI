package kr.co.kpcard.backoffice.component;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * 백오피스 시스템에서 사용할 트랙잭션 정의 객체.
 * Created by @author : MinWook on 2018. 6. 11.
 *
 */
public class KpCardTransactionDefinition {

	/**
	 * 트랜잭션 기본 옵션 정의.
	 * @param txManager 데이터소스 txManager 객체
	 * @param defintionName 트랜잭션 이름
	 * @return
	 */
	public static TransactionStatus backOfficeDefaultTransactionDefinition(DataSourceTransactionManager txManager, String defintionName) {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(defintionName);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txStatus = txManager.getTransaction(def);	
		
		return txStatus;
	}
}
