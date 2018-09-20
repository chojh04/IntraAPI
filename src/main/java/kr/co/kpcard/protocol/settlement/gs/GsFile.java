package kr.co.kpcard.protocol.settlement.gs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GsFile {
	
	private static final Logger logger = LoggerFactory.getLogger(GsFile.class);
	
	public final static int RESULT_PARSE_ERROR 			= 0;
	public final static int RESULT_PARSE_SUCESS 		= 1;
	public final static int RESULT_PARSE_FAIL_TO_HEADER = 2;
	public final static int RESULT_PARSE_FAIL_TO_DATA 	= 3;
	public final static int RESULT_PARSE_FAIL_TO_TAIL 	= 4;
	
	private GsHeaderRecord		headerRecord;
	private List<GsDataRecord>	dataRecordList;
	private GsTailRecord			tailRecord;
	
	
	public GsFile()
	{
		this.dataRecordList = new ArrayList<>();
	}
	
	public void addDataRecord(GsDataRecord dataRecord)
	{
		if(dataRecordList != null) dataRecordList.add(dataRecord);
	}
	
	public void resetDataRecordList()
	{
		if(dataRecordList != null) dataRecordList.clear();
	}
	
	public GsHeaderRecord getHeaderRecord() 
	{
		return headerRecord;
	}
	
	public List<GsDataRecord> getDataRecordList() 
	{
		return dataRecordList;
	}
	
	public GsTailRecord getTailRecord() 
	{
		return tailRecord;
	}

	@SuppressWarnings("resource")
	public int parseFile(String inputFile)
	{
		int result = RESULT_PARSE_ERROR;
		
		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
			
			if(!bufferedReader.ready())
			{
				logger.warn("parseFile() : bufferReader not ready | inputFile : " + inputFile );
				return result;
			}
			
			String readLine = "";
			
			while (bufferedReader.readLine() != null)
			{
				readLine = bufferedReader.readLine();
				
				String divider = readLine.substring(0, 1);
				
				if(GsHeaderRecord.HEADER_DIVIDER.equals(divider))
				{
					if(this.headerRecord == null) this.headerRecord = new GsHeaderRecord();
					if(!this.headerRecord.parse(readLine.getBytes())) return  RESULT_PARSE_FAIL_TO_HEADER;
				}
				else if(GsDataRecord.DATA_DIVIDER.equals(divider))
				{
					if(this.dataRecordList == null) this.dataRecordList = new ArrayList<>();
					
					GsDataRecord dataRecord = new GsDataRecord();
					if(!dataRecord.parse(readLine.getBytes())) return RESULT_PARSE_FAIL_TO_DATA;
					else this.dataRecordList.add(dataRecord);
				}
				else if(GsTailRecord.TAIL_DIVIDER.equals(divider))
				{
					if(this.tailRecord == null) this.tailRecord = new GsTailRecord();
					if(!this.headerRecord.parse(readLine.getBytes())) return RESULT_PARSE_FAIL_TO_TAIL;
				}
				else
				{
					logger.warn("parseFile() : wrong divider | readLine : " + readLine );
				}
				
			}
			
			bufferedReader.close();
			
			result = RESULT_PARSE_SUCESS;
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
			result = RESULT_PARSE_ERROR;
		}
		
		return result;
	}
	
	public boolean makeFile(String outputFile)
	{
		boolean result = false;

		try 
		{
			if(this.headerRecord != null && this.tailRecord != null && this.dataRecordList != null) return result;
				
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
			
			bufferedWriter.write(this.headerRecord.makeRecordData());
			bufferedWriter.write("\n");
			
			for(GsDataRecord record : this.dataRecordList)
			{
				bufferedWriter.write(record.makeRecordData());
				bufferedWriter.write("\n");
			}
			
			bufferedWriter.write(this.tailRecord.makeRecordData());
			bufferedWriter.write("\n");
			
			bufferedWriter.flush();
			bufferedWriter.close();
			
			result = true;
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
			result = false; 
		}
		
		return result;
	}
	
		
	
}
