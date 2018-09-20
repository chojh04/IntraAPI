package kr.co.kpcard.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("apacheFtpManager")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ApacheFtpManager {
	
	Logger logger = LoggerFactory.getLogger(ApacheFtpManager.class	);
	
	private FTPClient ftpClient;
	
	public ApacheFtpManager() {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
	}
	
	public void connect(String url,String username, String password) throws IOException{
		ftpClient.connect(url);
		ftpClient.setDefaultPort(21);
		setSoTimeout(1000 * 60); // default 1분
		login(username,password); 
		setFtpMode(false); // defaultPassiveMode
	}
	
	public List<String> getFileDataToList(String fileName , String path) throws IOException{
		List<String> resultList = new ArrayList<String>();
		InputStream stream = ftpClient.retrieveFileStream(path + "/" + fileName);
		if(stream != null){
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream ,"UTF-8"));
			String readLine = null;
			while ((readLine = bufferedReader.readLine()) != null) {
				resultList.add(readLine);
			}		
		}
		disconnect();
		return resultList;
	}
	
	public void checkDirectoryExists(String dirPath) throws IOException{
		ftpClient.changeWorkingDirectory(dirPath);
	}
	
	public void setSoTimeout(int millisecond) throws SocketException{
		ftpClient.setSoTimeout(millisecond);
	}
	
	public void login(String username , String password) throws IOException{
		ftpClient.login(username, password);
	}
	
	public void setFtpMode(boolean isActive){
		if(isActive){
			ftpClient.enterLocalActiveMode();
		}else{
			ftpClient.enterLocalPassiveMode();
		}
	}
	
	public void disconnect() throws IOException{
		if(ftpClient != null){
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}
	
	public boolean upload(String uploadDir , String fileFullPath, String fileName) throws Exception{
		InputStream in = null;
		try{
			logger.info("fileName : {}" , fileName);
			logger.info("fileFullPath : {}" , fileFullPath);
			in = new FileInputStream(fileFullPath);
			ftpClient.changeWorkingDirectory(uploadDir);
			boolean isSuccess = ftpClient.storeFile(fileName, in);
			logger.info("isSuccess : {} " , isSuccess);
		}catch(Exception e){
			throw e;
		} finally{
			if (in != null){
				in.close();
			}
			disconnect();
		}
		return true;
	}
	

}
