package kr.co.kpcard.common.utils;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	public static byte[] copy(byte[] target, int offset, int lenght)
	{
		byte[] result = new byte[lenght];
		
		if(target == null) return result;
		System.arraycopy(target, offset, result, 0, result.length);
		
		return result;
	}
	
	public static String copyToString(byte[] target, int offset, int lenght)
	{
		String result = "";
		
		if(target == null) return result;
		
		byte[] copyBytes = new byte[lenght];
		System.arraycopy(target, offset, copyBytes, 0, copyBytes.length);
		
		result = new String(copyBytes);
		
		return result.trim();
	}
	
	public static String copyToString(byte[] target, int offset, int lenght, String charSet)
	{
		String result = "";
		
		try 
		{
			if(target == null || charSet == null 
					|| "".equals(charSet) ) return result;
			
			byte[] copyBytes = new byte[lenght];
			System.arraycopy(target, offset, copyBytes, 0, copyBytes.length);
			
			result = new String(copyBytes, charSet);
		}
		catch (UnsupportedEncodingException e) 
		{
			result = "";
		}
		
		return result.trim();
	}
	
	public static String padRight(String target, int lenght)
	{
		StringBuffer result = new StringBuffer();
		int targetLenght = 0;
		String targetStr = "";
		try 
		{
			if(target != null && !"".equals(target) && !"null".equals(target)){
				targetLenght = target.getBytes("KSC5601").length;
				targetStr = target;
			}
			int paddingLenght = lenght - targetLenght;
			
			if(targetLenght <= lenght)
			{
				result.append(targetStr);
				for(int i=0; i<paddingLenght; i++)
				{
					result.append(" ");
				}
			}
		}
		catch (Exception e) 
		{
			return "";
		}
		
		return result.toString();
	}
	
	public static String paddRight(int intValue, int lenght)
	{
		StringBuffer result = new StringBuffer();
		
		try 
		{
			String strValue = String.valueOf(intValue); 
			int paddingLength = lenght - strValue.getBytes().length;
			
			if(strValue.getBytes().length <= lenght)
			{
				result.append(strValue);
				
				for(int i = 0; i < paddingLength; i++)
				{
					result.append(" ");
				}
			}

		} 
		catch (Exception e) 
		{
			return "";
		}
		
		return result.toString();
	}
	
	
	public static String padLeft(String target, int lenght)
	{
		StringBuffer result = new StringBuffer();
		int targetLenght =0;
		String targetStr = "";
		try 
		{
			if(target != null && !"".equals(target)){
				targetLenght = target.getBytes("KSC5601").length;
			}else{
				targetStr = target;
			}
			int paddingLenght = lenght - targetLenght;
			
			if(targetLenght <= lenght)
			{
				result.append(targetStr);
				for(int i=0; i<paddingLenght; i++)
				{
					result.insert(0, " ");
				}
			}
		}
		catch (Exception e) 
		{
			return "";
		}
		
		return result.toString();
	}
	
	public static String padLeft(String target, String pad, int lenght)
	{
		StringBuffer result = new StringBuffer();
		int targetLenght = 0;
		String targetStr = "";
		try 
		{
			if(target != null && !"".equals(target) && !"null".equals(target)){
				targetLenght = target.getBytes("KSC5601").length;
				targetStr = target;
			}
			int paddingLenght = lenght - targetLenght;
			
			if(targetLenght <= lenght)
			{
				result.append(targetStr);
				for(int i=0; i<paddingLenght; i++)
				{
					result.insert(0, pad);
				}
			}
		}
		catch (Exception e) 
		{
			return "";
		}
		
		return result.toString();
	}
	
	public static String padLeft(int intValue, int lenght)
	{
		StringBuffer result = new StringBuffer();
		
		try 
		{
			String strValue = String.valueOf(intValue); 
			int paddingLength = lenght - strValue.getBytes().length;
			
			if(strValue.getBytes().length <= lenght)
			{
				
				for(int i = 0; i < paddingLength; i++)
				{
					result.append(" ");
				}
				
				result.append(strValue);
			}

		} 
		catch (Exception e) 
		{
			return "";
		}
		
		return result.toString();
	}
	
	
	
	public static String getLengthString(StringBuffer sb) {
		int dataLenght = 0; 
		
		for(int i =0; i < sb.length(); i++)
		{
			char c = sb.charAt(i);
			
			if((c  < 0x00) || (c > 0xFF)) dataLenght = dataLenght + 2;
			else dataLenght = dataLenght + 1;
		}
		
		return String.format("%04d", dataLenght);
	}
	
	
	public static int convertToInteger(String value)
	{
		int result = -1;
		
		try 
		{
			result = Integer.parseInt(value);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			
			result = -1;
		}
		
		return result;
	}
	
	
	public static float convertToFloat(String value)
	{
		float result = -1;
		
		try 
		{
			result = Long.parseLong(value);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			result = -1;
		}
		
		return result;
	}
	
	public static long convertToLong(String value)
	{
		long result = -1;
		
		try 
		{
			result = Long.parseLong(value);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			result = -1;
		}
		
		return result;
	}
	
	/**
	 * @author sanghyun
	 * @param String Number
	 * @return
	 */
	public static long strToLong(String value){
		long result = 0;
		String str = trim(value);
		if(!"".equals(str)){
			result = Long.parseLong(str);
		}
		return result;
	}
	
	/**
	 * @author sanghyun
	 * @param String Number
	 * @return
	 */
	public static int strToInt(String value){
		int result = 0;
		String str = trim(value);
		if(!"".equals(str)){
			result = Integer.parseInt(str);
		}
		return result;
	}
	
	/**
	 * @author sanghyun
	 * @param String Number
	 * @return
	 */
	public static float strToFloat(String value){
		float result = 0;
		String str = trim(value);
		if(!"".equals(str)){
			result = Float.parseFloat(str);
		}
		return result;
	}
	
	/**
	 * @author sanghyun
	 * @param String Number
	 * @return
	 */
	public static double strToDouble(String value){
		double result = 0;
		String str = trim(value);
		if(!"".equals(str)){
			result = Double.parseDouble(str);
		}
		return result;
	}
	
	/**
	 * @author sanghyun
	 * @param value
	 * @return
	 */
	public static String nullToBlank(String value){
		if(value == null){
			return "";
		}
		return value;
	}
	
	/**
	 * @author sanghyun
	 * @param value
	 * @return
	 */
	public static String trim(String value){
		if("".equals(nullToBlank(value))){
			return "";
		}
		return value.trim();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String strTest = "4100e123412345store112345678112345678123456";
		//byte[] testBytes = strTest.getBytes();
		
		//System.out.println("testBytes len : " + testBytes.length);
		
		//byte[] preTest = copy(testBytes, 0, 4);
		//System.out.println("preTest : " + new String(preTest));
		
		//String test = copyToString(testBytes, 0, 4);
		
		//System.out.println("test : " + test);
		//System.out.println(padLeft("200", "0", 10));
		
		//System.out.println(paddRight(200, 10));
		
		StringBuffer sb = new StringBuffer();
		sb.append("hello");
		System.out.println("1st : " + sb.toString());
		System.out.println("1st : " + sb.length());
		
		sb.delete(0, sb.length());
		System.out.println("2st : " + sb.toString());
		System.out.println("2st : " + sb.length());
		
		
	}
	
}
