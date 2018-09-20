package kr.co.kpcard.protocol.settlement.smartro;

import java.util.List;

public class DepositDataSegment {

	private DepositStartRecord startRecord;
	private List<DepositDataRecord> dataList;
	private DepositTotalRecord totalRecord;
}
