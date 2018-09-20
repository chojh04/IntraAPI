package kr.co.kpcard.backoffice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bnl.brts.service.BrtsBarCodeLib;

import kr.co.kpcard.backoffice.controller.system.protocol.ResponseBarcode;

@Controller
public class TestController {

	private static Logger logger = LoggerFactory.getLogger(TestController.class);

	private static final String URI_TEST_BARCODE = "/kpctest/barcode";
	
	/**
	 * Agent 로그인 가능여부 조회
	 * @param id 아이디
	 * @param pwd 비밀번호
	 * @param loginIp 로그인 아이피
	 * 
	 * @return
	 */	
	@RequestMapping(value = URI_TEST_BARCODE, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseBarcode loadJNI(
			@RequestParam(value="barcode", required=true) String barcode) {	// for test 1019192392102510
		
		logger.debug("called jni test url : {}", URI_TEST_BARCODE);
		
		String		decoded = "";
		String		verbose = "";
		ResponseBarcode	response = new ResponseBarcode();
		
		try {
	    	// Libary path 표시
			verbose = System.getProperty("java.library.path");
			logger.info("Library path : {}", verbose);
			
	    	BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
	    	decoded = brtsBarCodeLib.DecodeBy(barcode,9);
	    	logger.info("barcode : {}, decoded : {}", barcode, decoded);
	    	
	    	response.setBarcode(barcode);
	    	response.setDecoded(decoded);
	    	response.setVerbose(verbose);	    	
		} catch ( Exception ex ) {
			logger.error(ex.getMessage());
		}
		logger.debug("finish jni test");
		
		return response;
	}	
	

}
