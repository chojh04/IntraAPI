package kr.co.kpcard.common.utils;

import java.net.*;
import java.io.*;

public class FtpClient {

	public static int ACTIVE_MODE=1;
	public static int PASV_MODE=0;

	protected int connectMode=PASV_MODE;
	
	protected int timeout = 60000;
	protected final static byte[] CRLF = {0x0d,0x0A};
	protected int contentLength;
	
	protected URL location;
	
	protected Socket sock_control;
	protected Socket sock_data;
	
	
	protected InputStream in;
	protected OutputStream out;
	protected Writer writer;
	
	protected String welcome="";
	protected String lastMessage;
	
	public FtpClient(){}
	
	public FtpClient(String url) throws MalformedURLException
	{
		this.location = new URL(url);
	}
	
	public void setUrl(String url) throws MalformedURLException
	{
		this.location = new URL(url);
	}
	
	public boolean Connect() throws IOException, UnknownHostException
	{
		boolean bRes = false;
		sock_control = new Socket();
		int port = (location.getPort() < 0) ? 21 : location.getPort();
		InetSocketAddress addr = new InetSocketAddress(location.getHost(), port);
		sock_control.connect(addr);
		
		sock_control.setSoTimeout(timeout);
		
		in = sock_control.getInputStream();
		out = sock_control.getOutputStream();
		writer = new OutputStreamWriter(out);
		if(check_reply("220"))
			bRes = true;
		else
		{
			
		}
		return bRes;
	}
	
	public boolean login_id(String userid) throws IOException
	{
		boolean bRes = true;
		
		if(!user(userid))
			bRes = false;
		
		return bRes;
	}
	
	public boolean login_pw(String userpw) throws IOException
	{
		boolean bRes = true;
		
		if(!pass(userpw))
			bRes = false;
		
		return bRes;
	}
	
	public boolean login(String userid, String userpw) throws IOException
	{
		boolean bRes = true;
		
		if(!user(userid))
			bRes = false;
		//System.out.println(lastMessage);
		if(!pass(userpw))
			bRes = false;
		//System.out.println(lastMessage);		
		return bRes;
	}
	
	public boolean user(String user) throws IOException
	{
		String user_cmd = "user " + user;
		writeln(user_cmd);
		return check_reply("331");
	}
	
	public boolean pass(String pass) throws IOException
	{
		send_command("PASS", (pass==null) ? "anonymous@localhost" : pass);
		if(check_reply("230-"))
		{
			/* we have a welcome message */
			while(true)
			{
				welcome += lastMessage;
				if(! check_reply("230-"))
				{
					return lastMessage.startsWith("230");
				}
			}
		}
		else
		{
			return lastMessage.startsWith("230");
		}
	}
	
	//응답 체크
	public boolean check_reply(String code) throws IOException
	{
		
		if(code.length() == 4)
		{
			/*
			 * a specific check is made for a multiline reply (eg '230-')
			 */
			lastMessage = getLine();
			//System.out.println("> "+lastMessage);
			if(lastMessage== null || !lastMessage.startsWith(code))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			/*
			 * the programmer doesn't want to bother with handling multi
			 * line replies himself so let's handle it for him.
			 */
			lastMessage="";
			String code2 = code +"-";
			String s;

			while(true)
			{
				s = getLine();
				//System.out.println("> " + s);


				if(s != null)
				{
					lastMessage += s;
					if(s.startsWith(code2))
					{
						continue;
					}
				}
				break;
			}
			if(s== null || !s.startsWith(code))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	
	
	
	public String getLine() throws IOException
	{
		
		int iBufLen=4096;
		int i=0;
		byte[] buf = new byte[iBufLen];
		try {
			for(i=0 ; i < iBufLen; i++)
		    {
				buf[i] = (byte) in.read();
				if(buf[i] == CRLF[1])
				{
					break;
				}
			}
		}catch (IOException ex) {
		    ex.printStackTrace();
			throw (ex);
		}

		return new String(buf,0,i);
	}
	
	public int switchMode()
	{
		connectMode = (connectMode == ACTIVE_MODE) ? PASV_MODE : ACTIVE_MODE;
		return connectMode;
	}

	// Active 모드
	public ServerSocket port() throws IOException
	{
		ServerSocket socket = new ServerSocket(0);
		InetAddress localhost =  sock_control.getLocalAddress();

		int s_port =  socket.getLocalPort();

		byte[] ip = localhost.getAddress();
		byte[] port = new byte[2];

		port[0] =(byte) (s_port >> 8); // most significant babes
		port[1] =(byte) (s_port & 0x00FF);

		String cmd = "PORT " + makeUnsignedShort(ip[0]) + "," +
					  makeUnsignedShort(ip[1]) + "," + makeUnsignedShort(ip[2]) +
					  "," +  makeUnsignedShort(ip[3]) + "," +
					  makeUnsignedShort(port[0]) + "," + makeUnsignedShort(port[1]);

		writeln(cmd);
		if(check_reply("200"))
		{
			return socket;
		}
		else
		{
		    return null;
		}
	}
	
	// Passive 모드 데이터 커넥션
	//protected Socket pasv() throws IOException
	protected Socket pasv() 
	{
		

		
		try {
			writeln("pasv");
		if(check_reply("227"))
		{
			int start = lastMessage.indexOf('(');
            int end = lastMessage.indexOf(')');
            String sockaddr = lastMessage.substring(start+1,end);
			String[] parts = sockaddr.split(",");
			/* why loop when it's only a single statement? */
			String s_hostIP = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
			
			/* get the port */
			int port = (Integer.parseInt(parts[4]) << 8) + Integer.parseInt(parts[5]);
			/* create a socket and return it */
			return new Socket(s_hostIP, port);
			
			
            
		}
		else
		{
		   return null;
		}
		} catch (IOException e) {
			e.printStackTrace();
			
			
		}
		return null;
	}	
	
	/**
	 * FTP Raw Command - 파일 다운로드 
	 * Active 모드와 Passive 모드 선택에 따라 DataConnection 연결을 맺은 후
	 * 해당 연결을 통해 파일을 다운로드 한다.
	 */
	public InputStream get(String path) throws IOException
	{
		
		/* switch to passive mode */
		DataConnection data_sock = makeDataConnection();
		if(data_sock != null)
		{
			if(path == null || path.equals(""))
			{
			    return null;
			}
			else
			{
				writeln("RETR " + path);
			}
			
			if(check_reply("150") || lastMessage.startsWith("125"))
			{
				return data_sock.getInputStream();
			}
		}
		
		return null;
	}
	
	//파일 업로드
	public OutputStream put(String path) throws IOException
	{
		/* switch to passive mode */
		DataConnection data_sock = makeDataConnection();
		if(data_sock != null)
		{
			if(path == null || path.equals(""))
			{
			    return null;
			}
			else
			{
				writeln("STOR " + path);
			}
			if(check_reply("150") || lastMessage.startsWith("125"))
			{
				return data_sock.getOutputStream();
			}
		}

		return null;
	}
	
	public DataConnection makeDataConnection() throws IOException
	{
		DataConnection con = new DataConnection();
		if(connectMode == ACTIVE_MODE)
		{
			con.sock_active = port();
		}
		else
		{
			con.sock_pasv = pasv();
		}

		return con;
	}

	
	// port/ip number 계산
	private short makeUnsignedShort(byte b)
	{
		return ( b < 0 ) ? (short) (b + 256)  : (short) b;
	}

	public void send_command(String command, String params) throws IOException
	{
		writeln(command + " " + params);
	}

	protected void writeln(String s) throws IOException
	{
		writer.write(s);
		writer.write("\r\n");
		writer.flush();
	}
	
	public void DisConnect() throws IOException, UnknownHostException
	{
		writeln("quit");
		in.close();
		out.close();
		writer.close();
		
		sock_control.close();
		//System.out.println("FTP 종료");
	}
	
	public void setLocation(String location) throws MalformedURLException
	{
		this.location = new URL(location);
	}
	
	/**
	 * <p>
	 * </p>
	 */
	public class DataConnection
	{
		ServerSocket sock_active;
		Socket sock_pasv;

		/**
		 */
		public InputStream getInputStream() throws IOException
		{
			if(connectMode == ACTIVE_MODE)
			{
				return sock_active.accept().getInputStream();
			}
			else
			{
				return sock_pasv.getInputStream();
			}
		}

		/**
		 * returns the output stream of the client or server socket depending
		 * on whether the active or passive mode is in effect.
		 *
		 * @return <code>OutputStream</code> for the data connection.
		 * @throws IOException
		 */
		public OutputStream getOutputStream() throws IOException
		{
			if(connectMode == ACTIVE_MODE)
			{
				return sock_active.accept().getOutputStream();
			}
			else
			{
				return sock_pasv.getOutputStream();
			}
		}
	}	
	
}
