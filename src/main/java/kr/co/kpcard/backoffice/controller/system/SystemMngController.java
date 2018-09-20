package kr.co.kpcard.backoffice.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kpcard.backoffice.component.system.SystemMngColumnFailureMessageException;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestCommonCode;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestDeleteBatch;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestDeleteCommonCode;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestMenu;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestMngBatch;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestSubUrl;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestSystemHistory;
import kr.co.kpcard.backoffice.controller.system.protocol.RequestTableColumn;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseBatch;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseString;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetAuthMenus;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetBatch;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetBatchs;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetCommonCode;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetMenus;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetSubMenus;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetSystemHistory;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetTableColumn;
import kr.co.kpcard.backoffice.controller.system.protocol.ResponseSystemMngGetTypeCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngBatch;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngCommonCode;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngMenu;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSubMenuUrl;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngSystemHistory;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTableColumn;
import kr.co.kpcard.backoffice.repository.system.model.SystemMngTypeCode;
import kr.co.kpcard.backoffice.service.system.ResGetSystemMngAuthMenus;
import kr.co.kpcard.backoffice.service.system.ResGetSystemMngBatch;
import kr.co.kpcard.backoffice.service.system.ResGetSystemMngSystemHistory;
import kr.co.kpcard.backoffice.service.system.SystemMngService;
import kr.co.kpcard.billingservice.app.controller.protocol.ResultCode;


@RestController
public class SystemMngController  implements ResultCode{
	
	Logger logger = LoggerFactory.getLogger(SystemMngController.class);
	
	@Autowired
	SystemMngService systemMngService;
	
	private static final String SYSTEM_MNG_MENUS = "/systemMng/menus";
	private static final String SYSTEM_MNG_MENU = SYSTEM_MNG_MENUS+"/menu";
	private static final String SYSTEM_MNG_AUTH_MENUS = "/systemMng/authMenus";
	private static final String SYSTEM_MNG_COMMON = "/systemMng/common";
	private static final String SYSTEM_MNG_COMMON_CODES = SYSTEM_MNG_COMMON+"/commonCodes";
	private static final String SYSTEM_MNG_COMMON_CODE = SYSTEM_MNG_COMMON+"/commonCode";
	private static final String SYSTEM_MNG_TYPE_CODES = SYSTEM_MNG_COMMON+"/typeCodes";
	private static final String SYSTEM_MNG_SYSTEM_HISTORY = SYSTEM_MNG_COMMON+"/systemHistory";
	private static final String SYSTEM_MNG_BATCHS = SYSTEM_MNG_COMMON+"/batchMngs";
	private static final String SYSTEM_MNG_BATCH = SYSTEM_MNG_COMMON+"/batchMng";
	private static final String SYSTEM_MNG_TABLE_COLUMN = SYSTEM_MNG_COMMON+"/tableColumnMng";
	private static final String SYSTEM_MNG_SUB_MENU_URL = SYSTEM_MNG_MENUS+"/menuSubUrl";
		
	/**
	 * 1.R2 메뉴 조회
	 * @param name
	 * @param menuId
	 * @param perMenuId
	 * @param selType
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_MENUS, method=RequestMethod.GET, produces="application/json")	
	public ResponseEntity<ResponseSystemMngGetMenus> getMenus(
			@RequestParam(value="name", required=true, defaultValue="") String name,
			@RequestParam(value="menuId", required=true, defaultValue="") String menuId,
			@RequestParam(value="parMenuId", required=true, defaultValue="") String perMenuId,
			@RequestParam(value="selType", required=true, defaultValue="") String selType
			)
	{
		ResponseSystemMngGetMenus responseGetMenus = new ResponseSystemMngGetMenus();
		try
		{
			List<SystemMngMenu> mngMenu = systemMngService.getSystemMngMenu(name, menuId, perMenuId, selType);
			if(mngMenu == null || mngMenu.size()<1)
			{
				//responseGetMenus.setMessage("No SystemMngMenu Data");
				return new ResponseEntity<ResponseSystemMngGetMenus>(responseGetMenus, HttpStatus.OK);
			}
			else
			{
				responseGetMenus.setResultList(mngMenu);
			}		
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			//responseGetMenus.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetMenus>(responseGetMenus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetMenus>(responseGetMenus, HttpStatus.OK);
	}
	
	/**
	 * 2. R2 신규메뉴 등록
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_MENU, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> postMenu(
			@RequestBody RequestMenu requestParam
			)
	{
		ResponseString response = new ResponseString();	
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(requestParam.getMenuId())){
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+requestParam.getMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getName()))
			{
				response.setMessage("Not Validate Parameter 'name' requstValue="+requestParam.getName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getCreateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+requestParam.getCreateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.postSystemMngMenu(requestParam.getMenuId(), requestParam.getName(), requestParam.getParMenuId(), requestParam.getMenuUrl(), requestParam.getCreateId());
			if(result)
			{
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"' insert.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"' not insert.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 3. 메뉴 정보 수정
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_MENU, method=RequestMethod.PUT, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> putSystemMngMenu(
			@RequestBody RequestMenu requestParam
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{			
			if("".equals(requestParam.getMenuId())){
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+requestParam.getMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getName()))
			{
				response.setMessage("Not Validate Parameter 'name' requstValue="+requestParam.getName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getUpdateId()))
			{
				response.setMessage("Not Validate Parameter 'updateId' requstValue="+requestParam.getUpdateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.putSystemMngMenu(requestParam.getMenuId(), requestParam.getName(), requestParam.getParMenuId(), requestParam.getMenuUrl(), requestParam.getUpdateId());
			if(result){
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"-"+requestParam.getName()+"' update.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result){
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"-"+requestParam.getName()+"' not update.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	
	/**
	 * 4. 메뉴 삭제
	 * @param menuId
	 * @param name
	 * @param parMenuId
	 * @param menuUrl
	 * @param createId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_MENU, method=RequestMethod.DELETE, produces="application/json")
	public @ResponseBody ResponseEntity<ResponseString> deleteSystemMngMenu(
			@RequestBody RequestMenu requestParam
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(requestParam.getMenuId())){
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+requestParam.getMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getName()))
			{
				response.setMessage("Not Validate Parameter 'name' requstValue="+requestParam.getName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getCreateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+requestParam.getUpdateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			
			boolean result = systemMngService.deleteSystemMngMenu(requestParam.getMenuId(), requestParam.getName(), requestParam.getParMenuId(), requestParam.getMenuUrl(), requestParam.getUpdateId());
			if(result){
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"-"+requestParam.getName()+"' delete.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result){
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"-"+requestParam.getName()+"' not delete.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 5. 유저 메뉴별 권한 조회
	 * @param employeeId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_AUTH_MENUS, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetAuthMenus> getAuthMenus(
			@RequestParam(value="empId", required=true, defaultValue="") String employeeId)	
	{
		ResponseSystemMngGetAuthMenus responseGetAuthMenus = new ResponseSystemMngGetAuthMenus();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			logger.debug("employeeId : "+employeeId);
			if("".equals(employeeId)){
				responseGetAuthMenus.setMessage("Not Validate Parameter 'employeeId' requstValue="+employeeId);
				return new ResponseEntity<ResponseSystemMngGetAuthMenus>(responseGetAuthMenus, httpStatus);
			}
			ResGetSystemMngAuthMenus resGetSystemMngAuthMenus = systemMngService.getSystemMngAuthMenu(employeeId);
			if(resGetSystemMngAuthMenus.getSystemMngCommonCode()==null || resGetSystemMngAuthMenus.getSystemMngCommonCode().size()<1){
				responseGetAuthMenus.setMessage("No SystemMngMenu Data");				
			}else if(resGetSystemMngAuthMenus.getSystemMngAuthMenu()==null || resGetSystemMngAuthMenus.getSystemMngAuthMenu().size()<1){
				responseGetAuthMenus.setMessage("No SystemMngParentAuthMenu Date");
			}else if(resGetSystemMngAuthMenus.getSystemMngSubUrlAuth()==null || resGetSystemMngAuthMenus.getSystemMngSubUrlAuth().size()<1){
				responseGetAuthMenus.setMessage("No SystemMngSubUrlAuth Data");				
			}else{
				responseGetAuthMenus.setResultHighMenuList(resGetSystemMngAuthMenus.getSystemMngAuthMenu());
				responseGetAuthMenus.setResultSubUrlList(resGetSystemMngAuthMenus.getSystemMngSubUrlAuth());
				responseGetAuthMenus.setResultCommCodeList(resGetSystemMngAuthMenus.getSystemMngCommonCode());
			}
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responseGetAuthMenus.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetAuthMenus>(responseGetAuthMenus, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<ResponseSystemMngGetAuthMenus>(responseGetAuthMenus, HttpStatus.OK); 
	}
	
	
	/**
	 * 6. 서브 메뉴등록
	 * @param parMenuId
	 * @param url
	 * @param name
	 * @param createId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SUB_MENU_URL, method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<ResponseString> postSystemMngSubMenuUrl(
			@RequestBody RequestSubUrl requestParam
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		try{
			if("".equals(requestParam.getParMenuId()))
			{
				response.setMessage("Not Validate Parameter 'parMenuId' requstValue="+requestParam.getParMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getUrl()))
			{
				response.setMessage("Not Validate Parameter 'url' requstValue="+requestParam.getUrl());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getName()))
			{
				response.setMessage("Not Validate Parameter 'name' requstValue="+requestParam.getName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getCreateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+requestParam.getCreateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.postSystemMngMenuSubUrl(requestParam.getParMenuId(), requestParam.getName(), requestParam.getUrl(), requestParam.getCreateId());
			if(result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+requestParam.getParMenuId()+"' insert.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+requestParam.getParMenuId()+"' not insert.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}	
	
	
	/**
	 * 7. 서브메뉴 정보 수정
	 * @param urlId
	 * @param url
	 * @param name
	 * @param updateId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SUB_MENU_URL, method=RequestMethod.PUT, produces="application/json")
	public ResponseEntity<ResponseString> putSystemMngSubMenuUrl(
			@RequestBody RequestSubUrl requestParam
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		try{
			if("".equals(requestParam.getUrlId()))
			{
				response.setMessage("Not Validate Parameter 'urlId' requstValue="+requestParam.getUrlId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getUrl()))
			{
				response.setMessage("Not Validate Parameter 'url' requstValue="+requestParam.getUrl());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getName()))
			{
				response.setMessage("Not Validate Parameter 'name' requstValue="+requestParam.getName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getUpdateId()))
			{
				response.setMessage("Not Validate Parameter 'updateId' requstValue="+requestParam.getUpdateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.putSystemMngMenuSubUrl(requestParam.getUrlId(), requestParam.getName(), requestParam.getUrl(), requestParam.getUpdateId());
			if(result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+requestParam.getUrlId()+"' inserted.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+requestParam.getUrlId()+"' not inserted.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}	
	
	
	/**
	 * 8. 서브메뉴 삭제
	 * @param urlId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SUB_MENU_URL, method=RequestMethod.DELETE, produces="application/json")
	public ResponseEntity<ResponseString> deleteSystemMngSubMenuUrl(
			@RequestParam(value="urlId", required=true, defaultValue="") String urlId
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;		
		try{
			if("".equals(urlId))
			{
				response.setMessage("Not Validate Parameter 'urlId' requstValue="+urlId);
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.deleteSystemMngMenuSubUrl(urlId);
			
			if(result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+urlId+"' deleteed.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu parMenuId '"+urlId+"' not deleteed.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}	
	
	
	/**
	 * 9. 서브 메뉴 정보 조회
	 * @param parMenuId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SUB_MENU_URL, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetSubMenus> getSystemMngSubMenuUrl(
			@RequestParam(value="parMenuId", required=true, defaultValue="")String parMenuId
			)
	{
		ResponseSystemMngGetSubMenus responeSystemMngGetSubMenus = new ResponseSystemMngGetSubMenus();		
		try{
			List<SystemMngSubMenuUrl> systemMngSubMenuUrl = systemMngService.getSystemMngSubMenuUrl(parMenuId);
			if(systemMngSubMenuUrl==null || systemMngSubMenuUrl.size()<1){
				responeSystemMngGetSubMenus.setMessage("No SystemMngMenu Data");
			}
			else
			{
				responeSystemMngGetSubMenus.setMessage("success");
				responeSystemMngGetSubMenus.setResultList(systemMngSubMenuUrl);
			}
		}catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responeSystemMngGetSubMenus.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetSubMenus>(responeSystemMngGetSubMenus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetSubMenus>(responeSystemMngGetSubMenus, HttpStatus.OK);
	}
	
	/**
	 * 10. 일반 코드 조회
	 * @param typeCode
	 * @param code
	 * @param codeName
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_COMMON_CODES, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetCommonCode> getSystemMngCommonCode(
			@RequestParam(value="typeCode", required=true, defaultValue="") String typeCode,
			@RequestParam(value="code", required=true, defaultValue="") String code,
			@RequestParam(value="codeName", required=true, defaultValue="") String codeName			
			)
	{
		logger.info("common Codes");
		ResponseSystemMngGetCommonCode responseSystemMngGetCommonCode = new ResponseSystemMngGetCommonCode();
		try{
			List<SystemMngCommonCode> systemMngCommonCode = systemMngService.getSystemMngCommonCode(typeCode, code, codeName);
			
			if(systemMngCommonCode==null || systemMngCommonCode.size()<1)
			{
				responseSystemMngGetCommonCode.setMessage("No SystemMngMenu CommonCode Data");
			}
			else
			{
				responseSystemMngGetCommonCode.setMessage("success");
				responseSystemMngGetCommonCode.setResultList(systemMngCommonCode);
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responseSystemMngGetCommonCode.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetCommonCode>(responseSystemMngGetCommonCode, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetCommonCode>(responseSystemMngGetCommonCode, HttpStatus.OK);
	}
	
	/**
	 * 10. 타입 코드 조회
	 * @param typeCode
	 * @param code
	 * @param codeName
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_TYPE_CODES, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetTypeCode> getSystemMngTypeCode(
			@RequestParam(value="typeCode", required=true, defaultValue="") String typeCode		
			)
	{
		ResponseSystemMngGetTypeCode responseSystemMngGetTypeCode = new ResponseSystemMngGetTypeCode();
		try{
			List<SystemMngTypeCode> systemMngtypeCode = systemMngService.getSystemMngTypeCode(typeCode);
			if(systemMngtypeCode==null || systemMngtypeCode.size()<1)
			{
				responseSystemMngGetTypeCode.setMessage("No SystemMngMenu TypeCode Data");
			}
			else
			{
				responseSystemMngGetTypeCode.setMessage("success");
				responseSystemMngGetTypeCode.setResultList(systemMngtypeCode);
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responseSystemMngGetTypeCode.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetTypeCode>(responseSystemMngGetTypeCode, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetTypeCode>(responseSystemMngGetTypeCode, HttpStatus.OK);
	}
	
	
	/**
	 * 11. 일반 코드 등록
	 * @param typeCode
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_COMMON_CODE, method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<ResponseString> postSystemMngTypeCode(
			@RequestBody RequestCommonCode requestParam	
			)
	{
		
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(requestParam.getTypeCode()))
			{
				response.setMessage("Not Validate Parameter 'typeCode' requstValue="+requestParam.getTypeCode());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getCode()))
			{
				response.setMessage("Not Validate Parameter 'code' requstValue="+requestParam.getCode());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getCodeName()))
			{
				response.setMessage("Not Validate Parameter 'codeName' requstValue="+requestParam.getCodeName());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getCreateId()))
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+requestParam.getCreateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.postSystemMngCommonCode(requestParam.getTypeCode()
																	, requestParam.getCode()
																	, requestParam.getCodeName()
																	, requestParam.getDescText()
																	, requestParam.getCreateId());
			if(result)
			{
				response.setMessage("SystemMngMenu typeCode '"+requestParam.getTypeCode()+"' insert.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu typeCode '"+requestParam.getTypeCode()+"' not insert.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, HttpStatus.OK);
	}
	

	
	/**
	 * 12. 일반 코드 삭제
	 * @param urlId
	 * @param url
	 * @param name
	 * @param updateId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_COMMON_CODE, method=RequestMethod.DELETE, produces="application/json")
	public ResponseEntity<ResponseString> deleteSystemMngCpmmonCode(@RequestBody RequestDeleteCommonCode requestParam)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;		
		try{
			if("".equals(requestParam.getTypeCode()))
			{
				response.setMessage("Not Validate Parameter 'typeCode' requstValue="+requestParam.getTypeCode());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}else if("".equals(requestParam.getCode()))			
			{
				response.setMessage("Not Validate Parameter 'code' requstValue="+requestParam.getCode());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}			
			
			boolean result = systemMngService.deleteSystemMngCommonCode(requestParam.getTypeCode(), requestParam.getCode());
			
			if(result)
			{
				response.setMessage("SystemMngMenu typeCode '"+requestParam.getTypeCode()+"' deleteed.");
				httpStatus = HttpStatus.CREATED;
			}
			else if(!result)
			{
				response.setMessage("SystemMngMenu typeCode '"+requestParam.getTypeCode()+"' not deleteed.");
				httpStatus = HttpStatus.BAD_REQUEST;
			}			
		}catch(Exception e){
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}	
	
	/**
	 * 13. 관리자 수정 내역 조회
	 * @param menuId
	 * @param typeCode
	 * @param desc1
	 * @param desc2
	 * @param desc3
	 * @param regId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SYSTEM_HISTORY, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetSystemHistory> getSystemHistory(
			@RequestParam(value="menuId", required=true, defaultValue="") String menuId,
			@RequestParam(value="typeCode", required=true, defaultValue="") String typeCode,
			@RequestParam(value="desc1", required=false, defaultValue="") String desc1,
			@RequestParam(value="desc2", required=false, defaultValue="") String desc2,
			@RequestParam(value="desc3", required=false, defaultValue="") String desc3,
			@RequestParam(value="start", required=false, defaultValue="") String start,
			@RequestParam(value="length", required=false, defaultValue="") String length
			)
	{
		ResponseSystemMngGetSystemHistory responseSystemMngGetSystemHistory = new ResponseSystemMngGetSystemHistory();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(menuId))
			{
				//responseSystemMngGetSystemHistory.setMessage("Not Validate Parameter 'menuId' requstValue="+menuId);
				return new ResponseEntity<ResponseSystemMngGetSystemHistory>(responseSystemMngGetSystemHistory, httpStatus);
			}
			/*
			else if("".equals(typeCode))			
			{
				responseSystemMngGetSystemHistory.setMessage("Not Validate Parameter 'typeCode' requstValue="+typeCode);
				return new ResponseEntity<ResponseSystemMngGetSystemHistory>(responseSystemMngGetSystemHistory, httpStatus);
			}
			*/		
			ResGetSystemMngSystemHistory resGetSystemMngSystemHistory = systemMngService.getSystemMngSystemHistory(menuId, typeCode, desc1, desc2, desc3, start, length);
			
			if(resGetSystemMngSystemHistory.getSystemMngSystemHistorySummary().getCount()<1)
			{
				//responseSystemMngGetSystemHistory.setMessage("No SystemMngMenu TypeCode Data");
			}
			else
			{
				//responseSystemMngGetSystemHistory.setMessage("success");
				responseSystemMngGetSystemHistory.setResultList(resGetSystemMngSystemHistory.getSystemMngSystemHistoryList());
				responseSystemMngGetSystemHistory.setSummary(resGetSystemMngSystemHistory.getSystemMngSystemHistorySummary());
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			//responseSystemMngGetSystemHistory.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetSystemHistory>(responseSystemMngGetSystemHistory, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetSystemHistory>(responseSystemMngGetSystemHistory, HttpStatus.OK);
	}	
	
	/**
	 * 14.관리자 수정내역 등록
	 * @param menuId
	 * @param typeCode
	 * @param desc1
	 * @param desc2
	 * @param desc3
	 * @param regId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_SYSTEM_HISTORY, method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<ResponseString> postSystemHistory(
			@RequestBody RequestSystemHistory requestParam
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(requestParam.getMenuId()))
			{
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+requestParam.getMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getTypeCode()))			
			{
				response.setMessage("Not Validate Parameter 'typeCode' requstValue="+requestParam.getTypeCode());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getRegId()))			
			{
				response.setMessage("Not Validate Parameter 'regId' requstValue="+requestParam.getRegId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
						
			boolean result = systemMngService.postSystemMngSystemHistory(requestParam.getMenuId(), requestParam.getTypeCode(), requestParam.getDesc1(), requestParam.getDesc2(), requestParam.getDesc3(), requestParam.getRegId());
			
			if(result)
			{
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"' inserted.");
				httpStatus=HttpStatus.CREATED;
			}
			else
			{
				response.setMessage("SystemMngMenu menuId '"+requestParam.getMenuId()+"' not inserted.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}	
	
	/**
	 * 15. 배치정보 조회
	 * @param limit
	 * @param offset
	 * @param empId
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_BATCHS, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetBatchs> getSystemMngBatchs(
			@RequestParam(value="limit", required=true, defaultValue="0") long limit,
			@RequestParam(value="offset", required=true, defaultValue="0") long offset,
			@RequestParam(value="reqId", required=false, defaultValue="") String reqId,
			@RequestParam(value="startDate", required=false, defaultValue="") String startDate,
			@RequestParam(value="endDate", required=false, defaultValue="") String endDate,
			@RequestParam(value="status", required=false, defaultValue="") String status
			)
	{
		logger.info("get Batchs startDate"+startDate+"/////endDate:"+endDate );
		ResponseSystemMngGetBatchs responseSystemMngGetBatchs = new ResponseSystemMngGetBatchs();
		if(limit<=0 || offset<0)
		{
			responseSystemMngGetBatchs.setMessage("Not Validate Parameter 'limit' or 'offset' requestValue[limit:"+limit+", offset:"+offset+"]");
			return new ResponseEntity<ResponseSystemMngGetBatchs>(responseSystemMngGetBatchs, HttpStatus.FORBIDDEN);
		}
					
		ResGetSystemMngBatch systemMngSystemBatchs = systemMngService.getSystemMngBatchs(limit, offset, reqId, startDate, endDate, status);
		
		if(systemMngSystemBatchs.getBatchSummary().getCount()<1)
		{
			responseSystemMngGetBatchs.setMessage("No SystemMngMenu TypeCode Data");
		}
		else
		{
			responseSystemMngGetBatchs.setMessage("success");
			responseSystemMngGetBatchs.setSummary(systemMngSystemBatchs.getBatchSummary());
			responseSystemMngGetBatchs.setResultList(systemMngSystemBatchs.getSystemMngBatchs());
		}
		return new ResponseEntity<ResponseSystemMngGetBatchs>(responseSystemMngGetBatchs, HttpStatus.OK);
	}
	
	/**
	 * 16. 배치 정보 등록
	 * @param reqId
	 * @param status
	 * @param filePath
	 * @param content
	 * @param errMsg
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_BATCH, method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<ResponseBatch> postSystemMngBatch(			
			@RequestBody RequestMngBatch request
			)
	{
		ResponseBatch response = new ResponseBatch();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(request.getReqId()))
			{
				response.setMessage("Not Validate Parameter 'reqId' requstValue="+request.getReqId());
				return new ResponseEntity<ResponseBatch>(response, httpStatus);
			}
			else if("".equals(request.getStatus()))			
			{
				response.setMessage("Not Validate Parameter 'status' requstValue="+request.getStatus());
				return new ResponseEntity<ResponseBatch>(response, httpStatus);
			}
			else if("".equals(request.getFilePath()))			
			{
				response.setMessage("Not Validate Parameter 'filePath' requstValue="+request.getFilePath());
				return new ResponseEntity<ResponseBatch>(response, httpStatus);
			}
						
			SystemMngBatch mng = systemMngService.postSystemMngBatch(request.getReqId(), request.getStatus(), request.getFilePath(), request.getContent(), request.getErrMsg());
			
			if(mng.getSeq()>0)
			{
				response.setMessage("SystemMngBatch inserted.");
				httpStatus=HttpStatus.CREATED;
				response.setBatchId(mng.getSeq());
			}
			else
			{
				response.setMessage("SystemMngBatch not inserted.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseBatch>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseBatch>(response, httpStatus);
	}
	
	/**
	 * 17. 배치 정보 수정
	 * @param batchId
	 * @param reqId
	 * @param status
	 * @param errMsg
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_BATCH, method=RequestMethod.PUT, produces="application/json")
	public ResponseEntity<ResponseString> putSystemMngBatch(
			@RequestBody RequestMngBatch request			
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(request.getReqId()))
			{
				response.setMessage("Not Validate Parameter 'reqId' requstValue="+request.getReqId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(request.getStatus()))			
			{
				response.setMessage("Not Validate Parameter 'status' requstValue="+request.getStatus());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(request.getFilePath()))			
			{
				response.setMessage("Not Validate Parameter 'filePath' requstValue="+request.getFilePath());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
						
			boolean result = systemMngService.putSystemMngBatch(request.getBatchId(), request.getReqId(), request.getStatus(), request.getErrMsg());
			
			if(result)
			{
				response.setMessage("SystemMngBatch batchId '"+request.getBatchId()+"' updated");
				httpStatus=HttpStatus.CREATED;
			}
			else
			{
				response.setMessage("SystemMngBatch batchId '"+request.getBatchId()+"' not updated.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}

	/**
	 * 18. 배치 정보 조회
	 * @param seq
	 * @param reqId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_BATCH, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetBatch> getSystemMngBatch(
			@RequestParam(value="seq", required=true, defaultValue="0") String seq,
			@RequestParam(value="reqId", required=true, defaultValue="0") String reqId
			)
	{
		ResponseSystemMngGetBatch responseSystemMngGetBatch = new ResponseSystemMngGetBatch();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(seq))
			{
				responseSystemMngGetBatch.setMessage("Not Validate Parameter 'seq' requstValue="+seq);
				return new ResponseEntity<ResponseSystemMngGetBatch>(responseSystemMngGetBatch, httpStatus);
			}
			else if("".equals(reqId))			
			{
				responseSystemMngGetBatch.setMessage("Not Validate Parameter 'reqId' requstValue="+reqId);
				return new ResponseEntity<ResponseSystemMngGetBatch>(responseSystemMngGetBatch, httpStatus);
			}
						
			SystemMngBatch systemBatch = systemMngService.getSystemMngBatch(seq, reqId);
			
			if(systemBatch==null)
			{
				responseSystemMngGetBatch.setMessage("No SystemMngMenu TypeCode Data");
			}
			else
			{
				responseSystemMngGetBatch.setMessage("success");
				responseSystemMngGetBatch.setSystemMngBatch(seq, 
															reqId,
															systemBatch.getStatus(),
															systemBatch.getStatusName(),
															systemBatch.getContent(),
															systemBatch.getErrMsg(),
															systemBatch.getStartDt(),
															systemBatch.getEndDt(),
															systemBatch.getFilePath()
															);
			}
		}catch(Exception e){
			responseSystemMngGetBatch.setMessage("Error '"+e.toString()+"'");
			responseSystemMngGetBatch.setStatus("FAILED");
			return new ResponseEntity<ResponseSystemMngGetBatch>(responseSystemMngGetBatch, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetBatch>(responseSystemMngGetBatch, HttpStatus.OK);
	}
	
	/**
	 * 19. 배치 정보 삭제
	 * @param seq
	 * @param reqId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_BATCH, method=RequestMethod.DELETE, produces="application/json")
	public ResponseEntity<ResponseString> deleteSystemMngBatch(
			@RequestParam(value="seq", required=true, defaultValue="0") String seq,
			@RequestParam(value="reqId", required=true, defaultValue="0") String reqId)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			/*
			if("".equals(requestParam.getSeq()))
			{
				response.setMessage("Not Validate Parameter 'seq' requstValue="+requestParam.getSeq());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(requestParam.getReqId()))			
			{
				response.setMessage("Not Validate Parameter 'reqId' requstValue="+requestParam.getReqId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
						
			boolean result = systemMngService.deleteSystemMngBatch(requestParam.getSeq(), requestParam.getReqId());
			
			if(result)
			{
				response.setMessage("SystemMngBatch reqId '"+requestParam.getReqId()+"' deleted.");
				httpStatus=HttpStatus.CREATED;
			}
			else
			{
				response.setMessage("SystemMngBatch reqId '"+requestParam.getReqId()+"' not deleted.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
			*/
			if("".equals(seq))
			{
				response.setMessage("Not Validate Parameter 'seq' requstValue="+seq);
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(reqId))			
			{
				response.setMessage("Not Validate Parameter 'reqId' requstValue="+reqId);
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
						
			boolean result = systemMngService.deleteSystemMngBatch(seq, reqId);
			
			if(result)
			{
				response.setMessage("SystemMngBatch reqId '"+reqId+"' deleted.");
				httpStatus=HttpStatus.CREATED;
			}
			else
			{
				response.setMessage("SystemMngBatch reqId '"+reqId+"' not deleted.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}

	/**
	 * 20. 유저별 노출정보 제한 등록
	 * @param menuId
	 * @param empId
	 * @param tableId
	 * @param descText
	 * @param createId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_TABLE_COLUMN, method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<ResponseString> postSystemMngTableColumn(			
			@RequestBody RequestTableColumn request
			)
	{
		ResponseString response = new ResponseString();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(request.getMenuId()))
			{
				response.setMessage("Not Validate Parameter 'menuId' requstValue="+request.getMenuId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(request.getEmpId()))			
			{
				response.setMessage("Not Validate Parameter 'empId' requstValue="+request.getEmpId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			else if("".equals(request.getTableId()))			
			{
				response.setMessage("Not Validate Parameter 'tableId' requstValue="+request.getTableId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}			
			else if("".equals(request.getCreateId()))			
			{
				response.setMessage("Not Validate Parameter 'createId' requstValue="+request.getCreateId());
				return new ResponseEntity<ResponseString>(response, httpStatus);
			}
			
			boolean result = systemMngService.postSystemMngTableColumn(request.getMenuId(), request.getEmpId(), request.getTableId(), request.getDescText(), request.getCreateId());
			
			if(result)
			{
				response.setMessage("SystemMngTableColumn inserted.");
				httpStatus=HttpStatus.CREATED;
			}
			else
			{
				response.setMessage("SystemMngTableColumn not inserted.");
				httpStatus=HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			response.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseString>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseString>(response, httpStatus);
	}
	
	/**
	 * 21.유저별 노출 컬럼 조회
	 * @param menuId
	 * @param empId
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value=SYSTEM_MNG_TABLE_COLUMN, method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<ResponseSystemMngGetTableColumn> getSystemMngTableColumn(			
			@RequestParam(value="menuId", required=true, defaultValue="") String menuId,
			@RequestParam(value="empId", required=true, defaultValue="") String empId,
			@RequestParam(value="tableId", required=true, defaultValue="") String tableId
			)
	{
		ResponseSystemMngGetTableColumn responseSystemMngGetTableColumn = new ResponseSystemMngGetTableColumn();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		try{
			if("".equals(menuId))
			{
				responseSystemMngGetTableColumn.setMessage("Not Validate Parameter 'menuId' requstValue="+menuId);
				return new ResponseEntity<ResponseSystemMngGetTableColumn>(responseSystemMngGetTableColumn, httpStatus);
			}
			else if("".equals(empId))			
			{
				responseSystemMngGetTableColumn.setMessage("Not Validate Parameter 'empId' requstValue="+empId);
				return new ResponseEntity<ResponseSystemMngGetTableColumn>(responseSystemMngGetTableColumn, httpStatus);
			}
			else if("".equals(tableId))			
			{
				responseSystemMngGetTableColumn.setMessage("Not Validate Parameter 'tableId' requstValue="+tableId);
				return new ResponseEntity<ResponseSystemMngGetTableColumn>(responseSystemMngGetTableColumn, httpStatus);
			}			
			
			SystemMngTableColumn systemMngTableColumn = systemMngService.getSystemMngTableColumn(menuId, empId, tableId);
			
			if(systemMngTableColumn==null)
			{
				//responseSystemMngGetTableColumn.setMessage("No SystemMngMenu table Data");
				throw new SystemMngColumnFailureMessageException(false,"No SystemMngMenu table Data");
			}
			else
			{
				responseSystemMngGetTableColumn.setMessage("success");
				responseSystemMngGetTableColumn.setSystemMngTableColumn(systemMngTableColumn.getMenuId(),
																		systemMngTableColumn.getEmployeeId(),
																		systemMngTableColumn.getTableId(),
																		systemMngTableColumn.getDescText()
																		);
			}
		}
		catch(SystemMngColumnFailureMessageException se){
			logger.error("No SystemMngMenu table Data");
			throw se;
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.toString());
			responseSystemMngGetTableColumn.setMessage("Exception : "+ e.toString());
			return new ResponseEntity<ResponseSystemMngGetTableColumn>(responseSystemMngGetTableColumn, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseSystemMngGetTableColumn>(responseSystemMngGetTableColumn, HttpStatus.OK);
	}
}
