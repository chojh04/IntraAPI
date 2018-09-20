package kr.co.kpcard.billingservice.app;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.billingservice.app.service.erp.FiAdocuService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FiAdocuServiceTest {

	private Logger logger = LoggerFactory.getLogger(FiAdocuServiceTest.class);

	@Autowired
	FiAdocuService fiAdocuService;
	
	@Test
	public void testInsert(){
		boolean	rtn = false;
		
		try {
			rtn = fiAdocuService.insert();
		} catch (Exception ex) {
			logger.error("Error message : " + ex.getMessage());
		}
		assertTrue(rtn);
	}
	@Test
	public void testUpdate(){
		boolean	rtn = false;
		
		try {
			rtn = fiAdocuService.update();
		} catch (Exception ex) {
			logger.error("Error message : " + ex.getMessage());
		}
		assertTrue(rtn);
	}

	@Test
	public void testDigest(){
//		fiAdocuService.insert();
		try {
			//49cd017d5aff930cc9636d2bfba95c9c319c7164a330ecce35ec23271643c4bd1623be510f25d5efcc4b031c5d68c25f908636a106a41d29f5657a0759cf0687
			//49cd017d5aff930cc9636d2bfba95c9c319c7164a330ecce35ec23271643c4bd1623be510f25d5efcc4b031c5d68c25f908636a106a41d29f5657a0759cf0687
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			String password = "test!wegwgweg34f#$%^";
			byte[] digestb = md.digest(password.getBytes("UTF-8"));
			System.out.println(Hex.encodeHex(digestb));
			System.out.println(Hex.encodeHex(digestb).length);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
	
}
