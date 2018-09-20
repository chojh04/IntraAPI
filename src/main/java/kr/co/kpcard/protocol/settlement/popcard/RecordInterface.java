package kr.co.kpcard.protocol.settlement.popcard;

public interface RecordInterface {

	public boolean parse(byte[] record);
	public String makeRecordData();
}
