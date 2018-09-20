package kr.co.kpcard.protocol.settlement.nice;

import java.util.List;

public class DepositStatementsFile {

	public static String NICE_TRANSFER_ENCODING = "euc-kr";
	
	public static final String NICE_TRANSFER_RECORD_TYPE_START = "51";
	public static final String NICE_TRANSFER_RECORD_TYPE_HEADER = "52";
	public static final String NICE_TRANSFER_RECORD_TYPE_DATA = "60,61,62,63,64,65,66,67";
	public static final String NICE_TRANSFER_RECORD_TYPE_TAIL = "53";
	public static final String NICE_TRANSFER_RECORD_TYPE_END = "54";

	private DepositStartRecord startRecord;
	private List<DepositDataSegment> dataSegmentList; 
	private DepositEndRecord endRecord;
	
	
}
