package kr.co.kpcard.billingservice.app.service.erp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.erp.dao.FiAdocuDao;
import kr.co.kpcard.billingservice.app.repository.erp.dto.FiAdocuDto;
import kr.co.kpcard.common.utils.DateUtil;

@Service
public class FiAdocuService {
	
	private Logger logger = LoggerFactory.getLogger(FiAdocuService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private FiAdocuDao fiAdocuDao;
	
	@Transactional
	public boolean insert(){
		
		String currdate = DateUtil.getCurrentDate("yyyyMMddHHmm");
		String rowId = "DM" + currdate;
		TypedQuery<String> query = em.createQuery(FiAdocuDao.SELECT_MAX_ROW_NO,String.class);
		query.setParameter("rowId", rowId);
		String rowNo = query.getSingleResult();
		logger.debug("singleData : {} " , rowNo);
		FiAdocuDto fiAdocuDto = new FiAdocuDto();
		fiAdocuDto.setRowId         (rowId);   
		fiAdocuDto.setRowNo         (rowNo);   
		fiAdocuDto.setNoTax         ("*");   
		fiAdocuDto.setCdPc          ("1000");   
		fiAdocuDto.setCdWdept       ("2001");   
		fiAdocuDto.setNoDocu        (rowId);   
		fiAdocuDto.setNoDoline      (Long.valueOf(rowNo));   
		fiAdocuDto.setCdCompany     ("KPCARD");   
		fiAdocuDto.setIdWrite       ("1");	// for test   
		fiAdocuDto.setCdDocu        ("11"); // for test  
		fiAdocuDto.setDtAcct        (currdate.substring(0, 7));	// for test   
		fiAdocuDto.setStDocu        ("1"); 	// for test 
		fiAdocuDto.setTpDrcr        ("1");   // for test 
		fiAdocuDto.setCdAcct        ("12002");   // for test 
		fiAdocuDto.setAmt           (1000000);   // for test 
		fiAdocuDto.setCdPartner     ("00651");   // for test 
		fiAdocuDto.setNmPartner     ("팝카드_판매");   // for test 
		fiAdocuDto.setTpJob         ("");   
		fiAdocuDto.setClsJob        ("");   
		fiAdocuDto.setAdsHd         ("");   
		fiAdocuDto.setNmCeo         ("");   
		fiAdocuDto.setDtStart       ("");   
		fiAdocuDto.setDtEnd         ("");   
		fiAdocuDto.setAmTaxstd      (0);   
		fiAdocuDto.setAmAddtax      (0);   
		fiAdocuDto.setTpTax         ("");   
		fiAdocuDto.setNoCompany     ("");   
		fiAdocuDto.setDtsInsert     ("");   
		fiAdocuDto.setIdInsert      ("");   
		fiAdocuDto.setDtsUpdate     ("");   
		fiAdocuDto.setIdUpdate      ("");   
		fiAdocuDto.setNmNote        ("");   
		fiAdocuDto.setCdBizarea     ("");   
		fiAdocuDto.setCdDept        ("");   
		fiAdocuDto.setCdCc          ("");   
		fiAdocuDto.setCdPjt         ("");   
		fiAdocuDto.setCdFund        ("");   
		fiAdocuDto.setCdBudget      ("");   
		fiAdocuDto.setNoCash        ("");   
		fiAdocuDto.setStMutual      ("");   
		fiAdocuDto.setCdCard        ("");   
		fiAdocuDto.setNoDeposit     ("");   
		fiAdocuDto.setCdBank        ("");   
		fiAdocuDto.setUcdMng1       ("");   
		fiAdocuDto.setUcdMng2       ("");   
		fiAdocuDto.setUcdMng3       ("");   
		fiAdocuDto.setUcdMng4       ("");   
		fiAdocuDto.setUcdMng5       ("");   
		fiAdocuDto.setCdEmploy      ("");   
		fiAdocuDto.setCdMng         ("");   
		fiAdocuDto.setNoBdocu       ("");   
		fiAdocuDto.setNoBdoline     (0);   
		fiAdocuDto.setTpDocu        ("");   
		fiAdocuDto.setNoAcct        (0);   
		fiAdocuDto.setTpTrade       ("");   
		fiAdocuDto.setNoCheck       ("");   
		fiAdocuDto.setNoCheck1      ("");   
		fiAdocuDto.setCdExch        ("");   
		fiAdocuDto.setRtExch        (0);   
		fiAdocuDto.setCdTrade       ("");   
		fiAdocuDto.setNoCheck2      ("");   
		fiAdocuDto.setNoCheck3      ("");   
		fiAdocuDto.setNoCheck4      ("");   
		fiAdocuDto.setTpCross       ("");   
		fiAdocuDto.setErpCd         ("");   
		fiAdocuDto.setAmEx          (0);   
		fiAdocuDto.setTpExport      ("");   
		fiAdocuDto.setNoTo          ("");   
		fiAdocuDto.setDtShipping    ("");   
		fiAdocuDto.setTpGubun       ("");   
		fiAdocuDto.setNoInvoice     ("");   
		fiAdocuDto.setMdTax1        ("");   
		fiAdocuDto.setNmItem1       ("");   
		fiAdocuDto.setNmSize1       ("");   
		fiAdocuDto.setQtTax1        (0);   
		fiAdocuDto.setAmPrc1        (0);   
		fiAdocuDto.setAmSupply1     (0);   
		fiAdocuDto.setAmTax1        (0);   
		fiAdocuDto.setNmNote1       ("");   
		fiAdocuDto.setNoItem        ("");   
		fiAdocuDto.setCdBizplan     ("");   
		fiAdocuDto.setCdBgacct      ("");   
		fiAdocuDto.setCdMngd1       ("");   
		fiAdocuDto.setNmMngd1       ("");   
		fiAdocuDto.setCdMngd2       ("");   
		fiAdocuDto.setNmMngd2       ("");   
		fiAdocuDto.setCdMngd3       ("");   
		fiAdocuDto.setNmMngd3       ("");   
		fiAdocuDto.setCdMngd4       ("");   
		fiAdocuDto.setNmMngd4       ("");   
		fiAdocuDto.setCdMngd5       ("");   
		fiAdocuDto.setNmMngd5       ("");   
		fiAdocuDto.setCdmngd6       ("");   
		fiAdocuDto.setNmMngd6       ("");   
		fiAdocuDto.setCdMngd7       ("");   
		fiAdocuDto.setNmMngd7       ("");   
		fiAdocuDto.setCdMngd8       ("");   
		fiAdocuDto.setNmMngd8       ("");   
		fiAdocuDto.setYnIss         ("");   
		fiAdocuDto.setFinalStatus   ("");   
		fiAdocuDto.setNoBill        ("");   
		fiAdocuDto.setTpBill        ("");   
		fiAdocuDto.setTpRecord      ("");   
		fiAdocuDto.setTpEtcacct     ("");   
		fiAdocuDto.setStGware       ("");   
		fiAdocuDto.setSellDamNm     ("");   
		fiAdocuDto.setSellDamEmail  ("");   
		fiAdocuDto.setSellDamMobil  ("");   
		fiAdocuDto.setNmPumm        ("");   
		fiAdocuDto.setJeonjasend15Yn("");   
		fiAdocuDto.setDtWrite       ("");   
		fiAdocuDto.setStTax         ("");   
		fiAdocuDto.setMdTax2        ("");   
		fiAdocuDto.setNmItem2       ("");   
		fiAdocuDto.setNmSize2       ("");   
		fiAdocuDto.setQtTax2        (0);   
		fiAdocuDto.setAmPrc2        (0);   
		fiAdocuDto.setAmSupply2     (0);   
		fiAdocuDto.setAmTax2        (0);   
		fiAdocuDto.setNmNote2       ("");   
		fiAdocuDto.setMdTax3        ("");   
		fiAdocuDto.setNmItem3       ("");   
		fiAdocuDto.setNmSize3       ("");   
		fiAdocuDto.setQtTax3        (0);   
		fiAdocuDto.setAmPrc3        (0);   
		fiAdocuDto.setAmSupply3     (0);   
		fiAdocuDto.setAmTax3        (0);   
		fiAdocuDto.setNmNote3       ("");   
		fiAdocuDto.setMdTax4        ("");   
		fiAdocuDto.setNmItem4       ("");   
		fiAdocuDto.setNmSize4       ("");   
		fiAdocuDto.setQtTax4        (0);   
		fiAdocuDto.setAmPrc4        (0);   
		fiAdocuDto.setAmSupply4     (0);   
		fiAdocuDto.setAmTax4        (0);   
		fiAdocuDto.setNmNote4       ("");   
		fiAdocuDto.setNoAsset       ("");   
		fiAdocuDto.setNmBigo        ("");   
		fiAdocuDto.setNmPtr         ("");   
		fiAdocuDto.setExHp          ("");   
		fiAdocuDto.setExEmil        ("");   
		fiAdocuDto.setNoBiztax      ("");   
		fiAdocuDto.setYnImport      ("");   
		fiAdocuDto.setRefNoDocu     ("");   
		fiAdocuDto.setCdFx          ("");   
		fiAdocuDto.setFxBill        ("");   
		fiAdocuDto.setNoIss         ("");   
		fiAdocuDto.setTpEvidence    ("");   
		fiAdocuDto.setStBizbox      ("");   
		fiAdocuDto.setTpInput       ("");   
		fiAdocuDto.setSellDamTel    ("");   
		fiAdocuDto.setTextUserdef1  ("");   
		fiAdocuDto.setNoCar         ("");   
		fiAdocuDto.setNoCarbody     ("");   
		fiAdocuDto.setPummNo        ("");   
		fiAdocuDto.setDecLease      ("");   
		fiAdocuDto.setNoTdocu       ("");   
		fiAdocuDto.setNoTdoline     (0);   
		fiAdocuDto.setCdBizcar      ("");   
		fiAdocuDto.setTaxId         ("");   
		fiAdocuDto.setCdTaxacct     ("");
		em.persist(fiAdocuDto);
		FiAdocuDto result = fiAdocuDao.findByRowIdAndRowNo(rowId, rowNo);
		logger.debug("insert 결과 체크  : {}" , result.toString());
		String rollbackTest = null;
		rollbackTest.toString();
		return true;
	}
	
	@Transactional
	public boolean update(){
		
		FiAdocuDto fiAdocuDto = fiAdocuDao.findByRowIdAndRowNo("DM201711141609", "1");
		logger.debug("조회 : {}" , fiAdocuDto.toString());
		fiAdocuDto.setIdWrite("updateTest");
		fiAdocuDao.save(fiAdocuDto);
		return true;
	}
	
	
	
}
