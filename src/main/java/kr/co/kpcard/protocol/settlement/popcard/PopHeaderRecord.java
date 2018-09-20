package kr.co.kpcard.protocol.settlement.popcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kpcard.common.utils.StringUtil;

public class PopHeaderRecord implements RecordInterface {

public final static String HEADER_DIVIDER = "H";
	


	protected final static int LEN_SIZE 				= 300;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_CREATE_DATE 			= 8;
	protected final static int LEN_SEND_DATE 			= 8;
	protected final static int LEN_FILLER 				= 133;

	private final Logger logger = LoggerFactory.getLogger(PopHeaderRecord.class);
	
	
	private String divider;   	 // 레고드 구분 	: 1 
	private String createDate;   // 생성 일자 	: 8 
	private String sendDate;     // 전송 일자 	: 8
	private String filler;       // 필러 		: 283 
	
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getCreateDate() 
	{
		return createDate;
	}
	
	public String getSendDate() 
	{
		return sendDate;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	@Override
	public String toString() {
		return String
				.format("PopHeaderRecord [divider=%s, createDate=%s, sendDate=%s, filler=%s]",
						divider, createDate, sendDate, filler);
	}

	@Override
	public boolean parse(byte[] record) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try 
		{
			this.divider = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.createDate = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_CREATE_DATE);
			
			this.sendDate = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE
					, LEN_SEND_DATE);
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_CREATE_DATE + LEN_SEND_DATE
					, LEN_FILLER);
			
			result = true;
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("Exception : " + e.getMessage());
			return false;
		}
		
		return result;
	}

	@Override
	public String makeRecordData()
	{
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			sb.append(StringUtil.padRight(this.divider, LEN_DIVIDER));
			sb.append(StringUtil.padRight(this.createDate, LEN_CREATE_DATE));
			sb.append(StringUtil.padRight(this.sendDate, LEN_SEND_DATE));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("Exception : " + e.getMessage());
			sb.delete(0, sb.length());
		}
		
		return sb.toString();
	}

	public PopHeaderRecord(String divider, String createDate, String sendDate, String filler) {
		this.divider = divider;
		this.createDate = createDate;
		this.sendDate = sendDate;
		this.filler = filler;
	}

	public PopHeaderRecord() {
	}
	
	
}
