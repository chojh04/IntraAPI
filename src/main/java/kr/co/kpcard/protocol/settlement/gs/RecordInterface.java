package kr.co.kpcard.protocol.settlement.gs;

public interface RecordInterface {

	public boolean parse(byte[] record);
	public String makeRecordData();
}
