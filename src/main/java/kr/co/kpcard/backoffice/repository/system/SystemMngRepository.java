package kr.co.kpcard.backoffice.repository.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import kr.co.kpcard.backoffice.component.KpCardTransactionDefinition;
import kr.co.kpcard.backoffice.repository.system.mapper.SystemMngMapper;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTypeCode;

@Repository
public class SystemMngRepository{

	Logger logger = LoggerFactory.getLogger(SystemMngRepository.class);

	@Autowired
	SystemMngMapper systemMngMapper;
	
	@Autowired
	@Qualifier("backOfficeTransactionManager")
	private DataSourceTransactionManager backOfficeTransactionManager;
	/**
	 * 신규 메뉴 등록 
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	public Boolean postSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId){
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		
		boolean result = false;
		
		try{			
			//메뉴정보 등록
			systemMngMapper.postSystemMngMenu(menuId, name, parMenuId, menuUrl, createId);
			//메뉴정보 생성 내역 등록
			systemMngMapper.postSystemMngMenuHst(menuId, name, menuUrl, createId, "신규메뉴 생성");
			
			backOfficeTransactionManager.commit(txStatus);
			
			result = true;
		}catch(Exception e)
		{
			backOfficeTransactionManager.rollback(txStatus);
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
			return result;
		}		
		return result;		
	}
	
	/**
	 * 
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	public Boolean putSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId){
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		
		boolean result = false;
		
		try{			
			//메뉴정보 수정
			long updateResult = systemMngMapper.putSystemMngMenu(menuId, name, parMenuId, menuUrl, createId);
			
			if(updateResult>0){
				//메뉴정보 수정 내역 등록
				systemMngMapper.postSystemMngMenuHst(menuId, name, menuUrl, createId,"수정 메뉴명 : "+name+", url : "+menuUrl);
				result = true;
				backOfficeTransactionManager.commit(txStatus);
			}else{
				backOfficeTransactionManager.rollback(txStatus);
				result=false;
			}
		}catch(Exception e)
		{
			backOfficeTransactionManager.rollback(txStatus);
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
			return result;
		}		
		return result;		
	}
	
	public Boolean deleteSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId){		
		boolean result = false;
		TransactionStatus txStatus = KpCardTransactionDefinition.backOfficeDefaultTransactionDefinition(backOfficeTransactionManager, "createApproval");
		try{			
			//메뉴정보 수정
			long deleteResult = systemMngMapper.deleteSystemMngMenu(menuId);
			
			if(deleteResult>0){
				//메뉴정보 삭제 내역 등록
				systemMngMapper.postSystemMngMenuHst(menuId, name, menuUrl, createId,"삭제 메뉴명 : "+name+", url : "+menuUrl);
				backOfficeTransactionManager.commit(txStatus);
				result = true;
			}else{
				backOfficeTransactionManager.rollback(txStatus);
				result=false;
			}
		}catch(Exception e)
		{
			backOfficeTransactionManager.rollback(txStatus);
			e.printStackTrace();
			logger.error("Exception Message : " + e.getMessage());
			return result;
		}		
		return result;		
	}
	
	public SystemMngTypeCode readSystemCodeByCode(String code) {
		return systemMngMapper.readSystemCodeByCode(code);
	};
}
