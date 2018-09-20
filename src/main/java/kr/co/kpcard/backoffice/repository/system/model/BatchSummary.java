package kr.co.kpcard.backoffice.repository.system.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchSummary {
	private Long count; //총 카운트 수
	private Long offset; //페이지
	private Long limit; //노출갯수
	
	public BatchSummary(long count, long limit, long offset) {
		this.count=count;
		this.limit=limit;
		this.offset=offset;
		
	}	
}
