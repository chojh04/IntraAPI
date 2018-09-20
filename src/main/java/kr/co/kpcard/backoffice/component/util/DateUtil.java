package kr.co.kpcard.backoffice.component.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 날짜 관련 유틸
 * Created by @author : MinWook on 2018. 6. 12.
 *
 */
public abstract class DateUtil {
	
	/**
	 * 날짜 객체를 문자열로 변환
	 * @param date
 	 * @param dateFormat (ex 1."YYYY-MM-dd" 2."YYYY-MM-dd kk:mm:ss")
	 * @return
	 */
	public static String convertDateToString(Date date, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		String result = date != null ? simpleDateFormat.format(date) : "";
		return result;
	}

	public static String nowDateToString(String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.KOREA);
		
		Calendar calendar = Calendar.getInstance();
		
		return simpleDateFormat.format(calendar.getTime());
	}
	
}
