package kr.co.kpcard.protocol.settlement.nice;

import java.util.List;

public class InvoiceStatementsFile {

	public static final String NICE_DEMAND_RECORD_TYPE_HEADER = "H";
	public static final String NICE_DEMAND_RECORD_TYPE_DATA = "D";
	public static final String NICE_DEMAND_RECORD_TYPE_TAIL = "T";
	
	private InvoiceHeaderRecord headerRecord;
	private List<InvoiceDataRecord> dataRecordList;
	private InvoiceTraillerRecord traillerRecord;
	
}
