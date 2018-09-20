package kr.co.kpcard.protocol.settlement.happycash;

public interface RecordInterface 
{
	public boolean parse(byte[] record);
	public String makeRecordData();
}
