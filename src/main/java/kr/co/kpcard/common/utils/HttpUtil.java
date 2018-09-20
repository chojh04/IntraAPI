package kr.co.kpcard.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kpcard.billingservice.app.service.settlement.PopCardSettlementService;

public class HttpUtil {

	private final static int BUFFER_SIZE = 1024;
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	
	public static void pumpBytes(InputStream inputStream, OutputStream outputStream )
			throws IOException 
	{
		
		byte[] buffer = new byte[BUFFER_SIZE];
		
		int count;
		
		while((count = inputStream.read(buffer)) != -1 )
			outputStream.write( buffer, 0, count );
		
		buffer = null;
	}
	
	
	private static void enableSSL() 
	{
        
		TrustManager[] trustAllCerts = new TrustManager[] 
        {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return null; }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                }
        };

        HostnameVerifier allHostsValid = new HostnameVerifier() 
        {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        
        try 
        {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            
            sslcontext.init(null, trustAllCerts,  new SecureRandom());
           
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } 
        catch(Exception ex) 
        {
        	
        }
    }
	
	
	public static boolean download(String fileUrl, String filePath) throws Exception 
	{
		boolean result = false;
		
		InputStream	is = null;
		OutputStream os = null;
		
		try 
		{
			//enableSSL();
			URL url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			is = conn.getInputStream();
			os = new FileOutputStream(filePath);
	
			pumpBytes(is, os);
			
			result = true;
		} 
		catch (IOException ioe) 
		{
			
		} 
		finally
		{
			try
			{
				is.close();
			}
			catch ( Exception e ) {}
			
			try 
			{
				os.close();
			} 
			catch ( Exception e ) {}
		}
		
		return result;
	}
	
	
	public static boolean upload(String urlString, String filePath, String fileName) throws Exception {
		HttpURLConnection 	conn = null;
		DataOutputStream 	dos = null;
		String 				fixedFileName = "unknown.zip";

		boolean				rtn = false;

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath));
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\""+fileName+"\";"
					+ " filename=\"" + fixedFileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {
			logger.error("Error: {}" ,  ex);
		} catch (IOException ioe) {
			logger.error("Error: {}" , ioe);
		}

		try {
			InputStreamReader 	isr = new InputStreamReader(conn.getInputStream());
			BufferedReader		br = new BufferedReader(isr);
			String 				str;
			
			while ((str = br.readLine()) != null) {
				logger.debug("Response : " + str);
			}
			br.close();
			rtn = true;
		} catch (Exception e) {
			logger.error("Error: "  ,  e);
		}

		return rtn;
		
	}
	
	
	
}
