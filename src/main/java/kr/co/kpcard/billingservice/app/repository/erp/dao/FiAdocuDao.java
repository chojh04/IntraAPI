package kr.co.kpcard.billingservice.app.repository.erp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.co.kpcard.billingservice.app.repository.erp.dto.FiAdocuDto;

@Repository
public interface FiAdocuDao extends CrudRepository<FiAdocuDto,String>{
	
	
	public static final String SELECT_MAX_ROW_NO = "SELECT TO_CHAR(NVL(TO_NUMBER(max(A.rowNo)), 0) + 1) FROM FiAdocuDto A WHERE A.rowId = :rowId";
	
	public List<FiAdocuDto> findAllByOrderByDtStartAsc();
	
	public FiAdocuDto findByRowIdAndRowNo(String rowId,String rowNo);

}
