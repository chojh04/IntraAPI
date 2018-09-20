package kr.co.kpcard.backoffice.service.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.backoffice.repository.system.SystemMngRepository;
import kr.co.kpcard.backoffice.repository.system.mapper.SystemMngMapper;
import kr.co.kpcard.backoffice.repository.system.model.BatchSummary;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngAuthMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngCommonCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuAuth;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuUrl;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistory;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistorySummary;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTableColumn;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTypeCode;
import kr.co.kpcard.common.utils.EncodeString;

@Service
public class SystemMngService {
	
	Logger logger = LoggerFactory.getLogger(SystemMngService.class);
	
	@Autowired
	SystemMngMapper systemMngMapper;
	
	@Autowired
	SystemMngRepository systemMngRepository;
		
	EncodeString encodeString = new EncodeString();
	
	/**
	 * 메뉴 리스트 조회
	 * @param name
	 * @param menuId
	 * @param perMenuId
	 * @param selType
	 * @return
	 */
	public List<SystemMngMenu> getSystemMngMenu(String name, String menuId, String perMenuId, String selType)
	{
		List<SystemMngMenu> mngMenu = systemMngMapper.getSystemMngMenu(name, menuId, perMenuId, selType);
		return mngMenu;
	}
	
	/**
	 * 메뉴 정보 등록
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	public boolean postSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId)
	{			
		boolean result = systemMngRepository.postSystemMngMenu(menuId, name, parMenuId, menuUrl, createId);			
		return result;
	}
	
	

	/**
	 * 메뉴 정보 수정
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	public boolean putSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId)
	{			
		boolean result = systemMngRepository.putSystemMngMenu(menuId, name, parMenuId, menuUrl, createId);			
		return result;
	}
	
	/**
	 * 메뉴 삭제
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	public boolean deleteSystemMngMenu(String menuId, String name, String parMenuId, String menuUrl, String createId)
	{			
		boolean result = systemMngRepository.deleteSystemMngMenu(menuId, name, parMenuId, menuUrl, createId);			
		return result;
	}
	
	/**
	 * 메뉴 권한 조회
	 * @param employeeId
	 * @return
	 */
	public ResGetSystemMngAuthMenus getSystemMngAuthMenu(String employeeId)
	{		
		List<SystemMngAuthMenu> systemMngAuthMenu = systemMngMapper.getSystemMngAuthMenu(employeeId);
				
		List<SystemMngSubMenuAuth> systemMngSubUrlAuth = systemMngMapper.getSystemMngSubMenuAuth(employeeId);
		
		List<SystemMngCommonCode> systemMngCommonCode = systemMngMapper.getSystemMngCommonCodeForMenuAuth();
						
		return new ResGetSystemMngAuthMenus(systemMngAuthMenu, systemMngSubUrlAuth, systemMngCommonCode);
	}
	
	
	/**
	 * 서브 메뉴 등록
	 * @param parMenuId
	 * @param name
	 * @param url
	 * @param createId
	 * @return
	 */
	public boolean postSystemMngMenuSubUrl(String parMenuId, String name, String url, String createId)
	{
		boolean result = false;
		
		try
		{
			systemMngMapper.postSystemMngSubMenuUrl(parMenuId, name, url, createId);
			result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 서브메뉴 수정
	 * @param urlId
	 * @param name
	 * @param url
	 * @param updateId
	 * @return
	 */
	public boolean putSystemMngMenuSubUrl(String urlId, String name, String url, String updateId)
	{
		boolean result = false;
		
		try
		{
			long updateCount = systemMngMapper.putSystemMngSubMenuUrl(urlId, name, url, updateId);
			if(updateCount>0) result = true;			
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 서브메뉴 삭제
	 * @param urlId
	 * @return
	 */
	public boolean deleteSystemMngMenuSubUrl(String urlId)
	{
		boolean result = false;
		
		try
		{
			long deleteCount = systemMngMapper.deleteSystemMngSubMenuUrl(urlId);
			if(deleteCount>0) result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}	
	
	/**
	 * 서브 메뉴 정보 조회
	 * @param parMenuId
	 * @return
	 */
	public List<SystemMngSubMenuUrl> getSystemMngSubMenuUrl(String parMenuId)
	{
		List<SystemMngSubMenuUrl> systemMngSubMenuUrl = systemMngMapper.getSystemMngSubMenuUrl(parMenuId);
		return systemMngSubMenuUrl;
	}
	
	/**
	 * 일반 코드 조회
	 * @param typeCode
	 * @param code
	 * @param codeName
	 * @return
	 */
	public List<SystemMngCommonCode> getSystemMngCommonCode(String typeCode, String code, String codeName){
		List<SystemMngCommonCode> systemMngCommonCode = systemMngMapper.getSystemMngCommonCode(typeCode, code, codeName);		
		return systemMngCommonCode;
	}
	
	/**
	 * 타입 코드 조회
	 * @param typeCode
	 * @param code
	 * @param codeName
	 * @return
	 */
	public List<SystemMngTypeCode> getSystemMngTypeCode(String typeCode){
		List<SystemMngTypeCode> systemMngTypeCode = systemMngMapper.getSystemMngTypeCode(typeCode);
		return systemMngTypeCode;
	}

	/**
	 * 일반 코드 등록
	 * @param typeCode
	 * @param code
	 * @param codeName
	 * @param createId
	 * @return
	 */
	public boolean postSystemMngCommonCode(String typeCode, String code, String codeName, String descText, String createId){
		boolean result = false;
		
		try
		{
			systemMngMapper.postSystemMngCommonCode(typeCode, code, codeName, descText, createId);
			result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 일반 코드 삭제
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public boolean deleteSystemMngCommonCode(String typeCode, String code){
		boolean result = false;
		
		try
		{
			long deleteCount = systemMngMapper.deleteSystemMngCommonCode(typeCode, code);
			if(deleteCount>0)result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 관리자 수정내역 조회
	 * @param menuId
	 * @param typeCode
	 * @param desc1
	 * @param desc2
	 * @param desc3
	 * @return
	 */
	public ResGetSystemMngSystemHistory getSystemMngSystemHistory(String menuId, String typeCode, String desc1, String desc2, String desc3, String start, String length)
	{
		ResGetSystemMngSystemHistory resGetSystemMngSystemHistory = new ResGetSystemMngSystemHistory();
		
		
		List<SystemMngSystemHistory> systemHistory = systemMngMapper.getSystemMngSystemHistory(menuId, typeCode, desc1, desc2, desc3, start, length);
		int totalCount = systemMngMapper.getSystemMngSystemHistoryCount(menuId, typeCode, desc1, desc2, desc3);
		
		SystemMngSystemHistorySummary systemMngSystemHistorySummary = new SystemMngSystemHistorySummary(totalCount, Integer.parseInt(start), Integer.parseInt(length));
		
		resGetSystemMngSystemHistory.setSystemMngSystemHistorySummary(systemMngSystemHistorySummary);
		resGetSystemMngSystemHistory.setSystemMngSystemHistoryList(systemHistory);
		
		return resGetSystemMngSystemHistory;
	}
	
	
	/**
	 * 관리자 수정내역 등록
	 * @param menuId
	 * @param typeCode
	 * @param desc1
	 * @param desc2
	 * @param desc3
	 * @param regId
	 * @return
	 */
	public boolean postSystemMngSystemHistory(String menuId, String typeCode, String desc1, String desc2, String desc3, String regId)
	{
		boolean result = false;
		
		try
		{
			systemMngMapper.postSystemMngSystemHistory(menuId, typeCode, desc1, desc2, desc3, regId);
			result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	
	/**
	 * 배치 리스트 조회
	 * @param limit
	 * @param offset
	 * @param empId
	 * @param startDate
	 * @param status
	 * @return
	 */
	public ResGetSystemMngBatch getSystemMngBatchs(Long limit, Long offset, String reqId, String startDate, String endDate, String status)
	{
		ResGetSystemMngBatch resGetSystemMngBatch = new ResGetSystemMngBatch();
		
		long count = systemMngMapper.getSystemMngBatchCount(limit, offset, reqId, startDate, endDate, status);
		
		BatchSummary batchSummary = new BatchSummary(count, limit, offset);		
		resGetSystemMngBatch.setBatchSummary(batchSummary);		
		
		if(count>0)
		{
			resGetSystemMngBatch.setSystemMngBatchs(systemMngMapper.getSystemMngBatchs(limit, offset, reqId, startDate, endDate, status));
		}
		
		return resGetSystemMngBatch;
	}
	
	/**
	 * 배치 정보 등록
	 * @param reqId
	 * @param status
	 * @param filePath
	 * @param content
	 * @param errMsg
	 * @return
	 */
	public SystemMngBatch postSystemMngBatch(String reqId, String status, String filePath, String content, String errMsg){
		SystemMngBatch mng = new SystemMngBatch();
		try
		{
			long batchId = systemMngMapper.getBatchNumber();
			logger.info("batchNumber:"+batchId);
			systemMngMapper.postSystemMngBatch(batchId, reqId, status, filePath, content, errMsg);
			mng.setSeq(batchId);
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return mng;
	}
	
	/**
	 * 배치 정보 수정
	 * @param batchId
	 * @param reqId
	 * @param status
	 * @param errMsg
	 * @return
	 */
	public boolean putSystemMngBatch(long batchId, String reqId, String status, String errMsg)
	{
		boolean result = false;
		
		try
		{
			long updateCount = systemMngMapper.putSystemMngBatch(batchId, reqId, status, errMsg);
			if(updateCount>0) result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 배치 정보 조회
	 * @param seq
	 * @param reqId
	 * @return
	 */
	public SystemMngBatch getSystemMngBatch(String seq, String reqId)
	{
		SystemMngBatch systemMngBatch = systemMngMapper.getSystemMngBatch(seq, reqId);
		return systemMngBatch;
	}
	
	/**
	 * 배치 정보 삭제
	 * @param seq
	 * @param reqId
	 * @return
	 */
	public boolean deleteSystemMngBatch(String seq, String reqId)
	{
		boolean result = false;
		
		try
		{
			long deleteCount = systemMngMapper.deleteSystemMngBatch(seq, reqId);
			if(deleteCount>0) result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	/**
	 * 유저별 노출정보제한 등록
	 * @param reqId
	 * @param status
	 * @param filePath
	 * @param content
	 * @param errMsg
	 * @return
	 */
	public boolean postSystemMngTableColumn(String menuId, String empId, String tableId, String descText, String createId){
		boolean result = false;
		
		try
		{
			systemMngMapper.postSystemMngTableColumn(menuId, empId, tableId, descText, createId);
			result = true;
		}
		catch(Exception e)
		{
			logger.error("DB Exception :"+e.toString());
		}
		return result;
	}
	
	
	/**
	 * 유저별 노출제한 항못 조회
	 * @param menuId
	 * @param empId
	 * @param tableId
	 * @return
	 */
	public SystemMngTableColumn getSystemMngTableColumn(String menuId, String employeeId, String tableId)
	{
		SystemMngTableColumn tableColumn = systemMngMapper.getSystemMngTableColumn(menuId, employeeId, tableId);
		return tableColumn;
	}
}
