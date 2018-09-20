package kr.co.kpcard.backoffice.component;

public class ListSummary {
	private int count;
	private int offset;
	private int limit;
	public ListSummary(int count, int offset, int limit) {
		super();
		this.count = count;
		this.offset = offset;
		this.limit = limit;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
