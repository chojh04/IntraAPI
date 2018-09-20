package kr.co.kpcard.common.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class EncodeString {

	private static final int ENCODING_LENGTH = 512;
	private static final String SALT = "";
	
	public static String encodeSha512(String decodeStr){
		String resultString = "";
		try{
			ShaPasswordEncoder encoder = new ShaPasswordEncoder(ENCODING_LENGTH);
			
			if(decodeStr==null || "".equals(decodeStr))
			{
				return resultString = "Fail";
			}
			
			resultString = encoder.encodePassword(decodeStr, SALT);
			
		}catch(Exception e){
			resultString = "Exception Throw : "+e.toString();
		}
		
		return resultString;
	}
}
