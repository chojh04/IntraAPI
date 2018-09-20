package kr.co.kpcard.billingservice.app.service.settlement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.kpcard.billingservice.app.repository.settlement.popcharge.HappyCashSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.popcharge.HappyCashSettlement;
import kr.co.kpcard.billingservice.app.repository.settlement.popcharge.HappyCashSettlementMapper;
import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.HttpUtil;
import kr.co.kpcard.common.utils.JobStatusCode;
import kr.co.kpcard.protocol.settlement.happycash.HappyCashDataRecord;
import kr.co.kpcard.protocol.settlement.happycash.HappyCashHeaderRecord;
import kr.co.kpcard.protocol.settlement.happycash.HappyCashTailRecord;

@Service
public class HappyCashSettlementService {
	
	private Logger logger = LoggerFactory.getLogger(GsPointSettlementService.class);
	private static final String lineEnd = "\r\n";
	private static final String FILLER = "";
	private static final String ADMIT_TYPE_PAY = "0";
	
	@Value("${kpc.http.baseUrl}")
	String baseUrl;
	@Value("${kpc.http.pathUrl}")
	String pathUrl;
	@Value("${http.happycash.id}")
	String httpId;
	@Value("${http.happycash.upload.name}")
	String uploadName;	
	@Value("${upload_file_path}")
	String UPLOAD_FILE_PATH;	
	@Value("${production_code}")
	String production_code;	
	
	@Autowired
	private HappyCashSettlementMapper happyCashSettlementMapper;
	@Autowired
	private SettlementJobHistoryService settlementJobHistoryService;		
	
	public boolean upload(int year, int month, int day)
	{
		String uploadDate = DateUtil.getDateFormatString(DateUtil.createDate(year, month, day) , DateUtil.DATE_FORMAT);
		String fileName = uploadName + uploadDate;
		String filePath = "";
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("id", httpId);
		param.put("type", "upload");
		param.put("name", "file");	
		param.put("filename", fileName);	
		
		// 대사 HST data 생성
		settlementJobHistoryService.jobStart(JobStatusCode.HAPPYCASH,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_START_CODE,JobStatusCode.JOB_FILE_UPLOAD_START_MSG);
		try
		{
			// Head Data 
			HappyCashHeaderRecord gssmHeaderRecord = new HappyCashHeaderRecord(HappyCashHeaderRecord.HEADER_DIVIDER,uploadDate,DateUtil.getCurrenntDateYYYYMMDD(),FILLER);
			StringBuffer data = new StringBuffer();
			String header = gssmHeaderRecord.makeRecordData() + lineEnd;
			data.append(header);
			// Content data
			// DB data 조회
			List<HappyCashSettlement> happyCashRecordList =happyCashSettlementMapper.select(uploadDate);
			logger.info("happyCashRecordList count  : {}"  , happyCashRecordList.size());
			HappyCashTailRecord happyCashTailRecord = new HappyCashTailRecord();
			for(HappyCashSettlement happyCashSettlement: happyCashRecordList)
			{
				HappyCashDataRecord happyCashDataRecord = new HappyCashDataRecord();
				happyCashDataRecord.setDivider(HappyCashDataRecord.DATA_DIVIDER);
				happyCashDataRecord.setDealDate    (happyCashSettlement.getTransDate());
				happyCashDataRecord.setCardNo      (GetBarcodetNo(happyCashSettlement.getGiftNo()) + happyCashSettlement.getGiftCheck().substring(0, 1));
				happyCashDataRecord.setChargeAmount(String.valueOf(happyCashSettlement.getChargeAmt()));
				happyCashDataRecord.setDealDivider (happyCashSettlement.getaType());
				if (ADMIT_TYPE_PAY.equals(happyCashSettlement.getaType()) ) {
					happyCashTailRecord.setApprovalCount(happyCashTailRecord.getApprovalCount() + 1);
					happyCashTailRecord.setApprvoalAmount(happyCashTailRecord.getApprvoalAmount() + happyCashSettlement.getChargeAmt());					
				} else {
					happyCashTailRecord.setCancelCount(happyCashTailRecord.getCancelCount() + 1);
					happyCashTailRecord.setCancelAmount(happyCashTailRecord.getCancelAmount() + happyCashSettlement.getChargeAmt());
				}
				happyCashTailRecord.setTotalCount(happyCashTailRecord.getTotalCount()+1);	
				data.append(happyCashDataRecord.makeRecordData() + lineEnd);
			}
			// Tail Data
			happyCashTailRecord.setTotalCount(happyCashRecordList.size());
			happyCashTailRecord.setDivider(HappyCashTailRecord.TAIL_DIVIDER);
			happyCashTailRecord.setFiller(FILLER);
			logger.info("happyCashTailRecord : {} " , happyCashTailRecord);
			String tailData = happyCashTailRecord.makeRecordData()  + lineEnd;
			data.append(tailData);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("데이터 조회 끝 파일 생성 시작:  {}" ,JobStatusCode.HAPPYCASH);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			
			filePath = UPLOAD_FILE_PATH + "/" + uploadName + uploadDate;
			File file = new File(UPLOAD_FILE_PATH);
			if (!file.exists())
			{
				file.mkdirs();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			out.write(data.toString());
			out.flush();
			out.close();
			
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("파일생성 끝 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info("업로드 시작 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			String url = baseUrl + pathUrl + "?id=" + httpId + "&type=upload&name="+fileName+"&filename=" + fileName;
			HttpUtil.upload(url, filePath , fileName);
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" upload  종료 ");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

			if(happyCashRecordList.size() > 0)
			{
				// 대사 종료 HST   
				settlementJobHistoryService.jobStart(JobStatusCode.HAPPYCASH,uploadDate,JobStatusCode.JOB_FILE_UPLOAD_END_CODE,JobStatusCode.JOB_FILE_UPLOAD_END_MSG);
			}
			else 
			{
				// 데이터없음   
				settlementJobHistoryService.jobStart(JobStatusCode.HAPPYCASH,uploadDate,JobStatusCode.JOB_DATA_NOTFOUND_CODE,JobStatusCode.JOB_DATA_NOTFOUND_MSG);
			}
		}
		catch(Exception e)
		{
			logger.error("downloadGsPopcardDealDetails " , e);
			settlementJobHistoryService.jobStart(JobStatusCode.HAPPYCASH,uploadDate,JobStatusCode.JOB_ERROR_CODE,e.getCause().getMessage());
			return false;
		} 
		finally
		{
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			logger.info(" 파일삭제");
			logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			// 파일 삭제
			try
			{
				new File(filePath).delete();			
			}
			catch(Exception e)
			{
			}			
		}
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info(" gsDailySettlementUpload  종료 ");
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		return true;  
	}
	
	/**
	 * 티켓번호로 활성화 바코드 번호  추출
	 * 맨 뒤 13자리 중 앞 6자리는 영문 ASCII번호 숫자 6자리 끝 1자리 체크비트
	 * ABC012345 > 6566670123457 
	 */
	protected String GetBarcodetNo(String from)
	{
		if(from.length() < 9){
			return "";
		}

		StringBuffer ticketNo = new StringBuffer();
		//properties 에서 상품코드 가져옴....
		ticketNo.append(production_code);
		
		try {
			int tempInt = 0;
			tempInt = (int)from.charAt(0);	
			ticketNo.append(tempInt);
			
			tempInt = (int)from.charAt(1);		
			ticketNo.append(tempInt);
			
			tempInt = (int)from.charAt(2);		
			ticketNo.append(tempInt);
			
			ticketNo.append(from.substring(3, 9));
		} catch (Exception e) {
			return "";
		}

		return ticketNo.toString();
	}	
	
}
