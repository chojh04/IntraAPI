package kr.co.kpcard.protocol.settlement.smartro;

public interface RecordInterface 
{
	public boolean pasre(byte[] record);
	public int validate();
}
