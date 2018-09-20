package kr.co.kpcard.protocol.settlement.popcard;

import kr.co.kpcard.common.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopTailRecord implements RecordInterface {

	public final static String TAIL_DIVIDER = "T";
	
	protected final static int LEN_SIZE 				= 300;
	protected final static int LEN_DIVIDER 				= 1;
	protected final static int LEN_SEND_COUNT 			= 10;
	protected final static int LEN_FILLER 				= 289;

	private final Logger logger = LoggerFactory.getLogger(PopTailRecord.class);
	
	private String divider;
	private String sendCount;
	private String filler;
	
	
	public String getDivider() 
	{
		return divider;
	}
	
	public String getSendCount() 
	{
		return sendCount;
	}
	
	public String getFiller() 
	{
		return filler;
	}

	
	
	@Override
	public String toString() 
	{
		return String
				.format("PopTailRecord [divider=%s, sendCount=%s, filler=%s]",
						divider, sendCount, filler);
	}

	@Override
	public boolean parse(byte[] record) 
	{
		// TODO Auto-generated method stub
		boolean result = false;
		
		try 
		{
			this.divider = StringUtil.copyToString(record
					, 0
					, LEN_DIVIDER);
			
			this.sendCount = StringUtil.copyToString(record
					, LEN_DIVIDER 
					, LEN_SEND_COUNT);
			
			
			this.filler = StringUtil.copyToString(record
					, LEN_DIVIDER + LEN_SEND_COUNT
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
			sb.append(StringUtil.padRight(this.sendCount, LEN_SEND_COUNT));
			sb.append(StringUtil.padRight(this.filler, LEN_FILLER));
			
		}
		catch (Exception e) 
		{
			logger.error("Exception : " + e.getMessage());
			sb.delete(0, sb.length());
		}
	
		return sb.toString();
	}

	public PopTailRecord() {
	}

	public PopTailRecord(String divider, String sendCount, String filler) {
		this.divider = divider;
		this.sendCount = sendCount;
		this.filler = filler;
	}
	
	
}
