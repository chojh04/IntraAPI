package com.bnl.brts.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link BrtsBarCodeLib} 테스트 코드
 *
 * @author deepfree
 */
public class BrtsBarCodeLibTest {
	private static final Logger logger = LoggerFactory.getLogger(BrtsBarCodeLibTest.class);

	/** 바코드 역직렬화 테스트 */
	@Test
	public void testBrtsBarCodeDecodeBy() {
		try {
			logger.debug("바코드 역직렬화 테스트...");
			logger.debug(" sun.arch.data.model: {}", System.getProperty("sun.arch.data.model"));
			logger.debug(" os.arch: {}", System.getProperty("os.arch"));
			BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
			String cardNo = "1019192392102510";
			int len = 9;
			String result = brtsBarCodeLib.DecodeBy(cardNo, len);
			logger.debug("decodeBy({}, {}) => {}", cardNo, len, result);
		} catch (Throwable e) {
			//UnsatisfiedLinkError를 catch하기 위해 throwable을 catch
			logger.error("{} - {}", e.getClass().getCanonicalName(), e.getLocalizedMessage(), e);
			throw e;
		}
	}

}
