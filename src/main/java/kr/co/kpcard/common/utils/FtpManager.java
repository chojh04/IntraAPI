package kr.co.kpcard.common.utils;

import java.io.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpManager {

	private static final Logger logger = LoggerFactory.getLogger(FtpManager.class);

	public String ErrorMessage;
	
	private FtpClient client;

	private String server;
	//private String userid;
	//private String userpw;
	
	
	public FtpManager() 
	{
		this.client = new FtpClient();
	}

	// FTP 접속 정보 초기화
	public boolean Initialize() throws MalformedURLException 
	{

		if (this.server.equals("")) 
		{
			this.ErrorMessage = "서버 URL 정보가 없습니다.";
			return false;
		}

		client.setLocation(this.server);
		
		return true;
	}

	// FTP 접속
	public boolean FtpConnect() throws UnknownHostException, IOException 
	{
		boolean result = false;
	
		if (client.Connect()) 
		{
			result = true;
		}
		else 
		{
			result = false;
			this.ErrorMessage = String.format("서버 접속에 실패하였습니다.({0})", this.server);
		}

		return result;
	}

	// FTP 로그인
	public boolean FtpLogin(String userId, String userPwd) throws IOException 
	{
		boolean result = false;

		if (client.login_id(userId)) 
		{
			result = true;
		}
		else 
		{
			result = false;
			this.ErrorMessage = String.format("접속자 ID가 잘못되었습니다.({0})", userId);
		}

		if (client.login_pw(userPwd)) 
		{
			result = true;
		}
		else 
		{
			result = false;
			this.ErrorMessage = String.format("접속자 Password가 잘못되었습니다.()");

		}

		client.send_command("TYPE ", "I");
		client.check_reply("200");
		
		return result;
	}

	
	public int chkFileSize(String FileName) throws IOException 
	{
		
		String sRes = null;
		int nRes = 0;

		client.send_command("SIZE", FileName);
		sRes = client.getLine();

		if (sRes != null) 
		{
			if (sRes.substring(0, 3).trim().equals("213")) nRes = Integer.parseInt(sRes.substring(4).trim());
			else nRes = 0;
		}
		else
		{
			nRes = 0;
		}
		
		return nRes;
	}

	public int FtpDownload(String server_path, String save_path) 
	{
		int bRes = 0;

		try 
		{
			File f = new File(save_path);
			FileOutputStream out = new FileOutputStream(f);
			BufferedOutputStream bout = new BufferedOutputStream(out, 2048);

			byte buf[] = new byte[2048];

			int len = 0;
			int total_len = 0;
			BufferedInputStream bin = new BufferedInputStream(client.get(server_path));

			while ((len = bin.read(buf, 0, buf.length)) != -1) {
				bout.write(buf, 0, len);
				total_len += len;
			}
			/*
			 * String sRes = client.getLine();
			 * 
			 * if(sRes != null) { if(sRes.substring(0,3).trim().equals("226"))
			 * bRes = Integer.parseInt(sRes.substring(4).trim()); else bRes = 0;
			 * } else { bRes = 0; }
			 */

			bin.close();
			bout.close();
			out.close();
			bRes = total_len;

			String sRes = client.getLine();

			if (sRes != null) {
				if (!sRes.substring(0, 3).trim().equals("226"))
					bRes = 0;
			} else {
				bRes = 0;
			}

		} catch (Exception e) {
			this.ErrorMessage = String.format("파일 다운로드 실패 (%s)\r\n%s", server_path, e.getMessage());
			bRes = 0;
		}
		return bRes;
	}

	public int FtpUpload(String file_path, String server_path) 
	{
		int bRes = 0;

		try {
			File f = new File(file_path);
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis, 2048);

			byte buf[] = new byte[2048];

			int len = 0;
			int total_len = 0;
			BufferedOutputStream bos = new BufferedOutputStream(client.put(server_path));

			while ((len = bis.read(buf, 0, buf.length)) != -1) 
			{
				bos.write(buf, 0, len);
				total_len += len;
			}
			/*
			 * String sRes = client.getLine();
			 * 
			 * if(sRes != null) { if(sRes.substring(0,3).trim().equals("226"))
			 * bRes = Integer.parseInt(sRes.substring(4).trim()); else bRes = 0;
			 * } else { bRes = 0; }
			 */

			bos.close();
			bis.close();
			fis.close();
			bRes = total_len;

			String sRes = client.getLine();

			if (sRes != null) {
				if (!sRes.substring(0, 3).trim().equals("226"))
					bRes = 0;
			} else {
				bRes = 0;
			}

		} catch (Exception e) {
			this.ErrorMessage = String.format("파일 업로드 실패 (%s)\r\n%s", server_path, e.getMessage());
			bRes = 0;
		}
		return bRes;
	}

	public boolean FileDownload(String FileName) 
	{
		boolean result = false;
		
		try
		{
			client.send_command("binary", "");
		
			if (!client.check_reply("200")) 
			{
				this.ErrorMessage = "바이너리 변환 오류";
				return false;
			}

			client.send_command("hash", "");
			client.send_command("get", FileName);
			// String tmp = client.getLine();
			// System.out.println(tmp);

			result = true;
		}
		catch (Exception e) 
		{
			logger.error("exception : " + e.getMessage());
			result = false;
			this.ErrorMessage = e.getMessage();
		}

		return result;
	}

	public void FtpClose() throws UnknownHostException, IOException 
	{
		client.DisConnect();
	}

	public void setServer(String server) 
	{
		this.server = server;
	}

	public String getServer() 
	{
		return this.server;
	}

	
}
