package kr.co.kpcard.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RetrofitHttpManager implements HttpManager{
	
	private Logger logger = LoggerFactory.getLogger(RetrofitHttpManager.class);
	
	@Override
	public HttpMethod getHttpMethod(String baseUrl){
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		return retrofit.create(HttpMethod.class);
	}
	
	@Override
	public Response<ResponseBody> postHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException{
		int loopCnt = 0;
		Response<ResponseBody> response = null;
		while(true){
			try{
				response = getHttpMethod(baseUrl).post(pathUrl , param).execute();
				if(response != null || loopCnt == 3){
					break;
				}
			}catch (Exception e){
				logger.error("postHttpResponse : {}" , e);
				loopCnt ++;
				try {
					// TOODO : 오류 발생시 1초 대기후 재시작
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} 
		}
		return response;
	}
	
	@Override
	public Response<ResponseBody> getHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException{
		Response<ResponseBody> response = getHttpMethod(baseUrl).get(pathUrl, param).execute();
		return response;
	}
	
	@Override
	public Call<?> getHttpResponseByWildcard(String baseUrl, String pathUrl, Map<String, String> param) throws IOException {
		return getHttpMethod(baseUrl).getByWildcard(pathUrl, param);
	}	
	
	@Override
	public Response<ResponseBody> deleteHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException{
		Response<ResponseBody> response = getHttpMethod(baseUrl).delete(pathUrl , param).execute();
		return response;
	}
	
	@Override
	public Response<ResponseBody> putHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException{
		Response<ResponseBody> response = getHttpMethod(baseUrl).put(pathUrl , param).execute();
		return response;
	}
	

	@Override
	public boolean writeResponseBodyToDisk(ResponseBody body , String fileName) {  
	    try {
	        File file = new File(new File(".").getCanonicalPath() + File.separator + fileName);

	        InputStream inputStream = null;
	        OutputStream outputStream = null;

	        try {
	            byte[] fileReader = new byte[1024*3];

	            inputStream = body.byteStream();
	            outputStream = new FileOutputStream(file);

	            while (true) {
	                int read = inputStream.read(fileReader);
	                if (read == -1) {
	                    break;
	                }
	                outputStream.write(fileReader, 0, read);
	            }
	            outputStream.flush();
	            return true;
	        } catch (IOException e) {
	        	e.printStackTrace();
	            return false;
	        } finally {
	            if (inputStream != null) {
	                inputStream.close();
	            }

	            if (outputStream != null) {
	                outputStream.close();
	            }
	        }
	    } catch (IOException e) {
	        return false;
	    }
	}	
	
	@Override
	public List<String> responseBodyToList(ResponseBody body) {
		
		List<String> resultList = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try{
            resultList = new ArrayList<String>();
            bufferedReader = new BufferedReader(body.charStream());
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
            	
                resultList.add(readLine);
            }
        } catch (IOException e) {
        	logger.error("responseBodyToList IOException " , e);
        } finally {
            try {
            	if (inputStream != null) {
            		inputStream.close();
            	}
			} catch (IOException e) {
				logger.error("responseBodyToList IOException " , e);
			}

        }
        return resultList;
	}
	
	@Override
	public List<String> responseBodyToListByCharset(ResponseBody body, String charset) {
		
		List<String> resultList = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try{
			resultList = new ArrayList<String>();
			if (body != null){
				InputStream is = new ByteArrayInputStream(new String(body.bytes() , charset).getBytes("UTF-8"));
				bufferedReader = new BufferedReader(new InputStreamReader(is));
				String readLine = null;
				while ((readLine = bufferedReader.readLine()) != null) {
					
					resultList.add(readLine);
				}
			}
		} catch (IOException e) {
			logger.error("responseBodyToList IOException " , e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("responseBodyToList IOException " , e);
			}
			
		}
		return resultList;
	}
	
	@Override
	public RequestBody toRequestBody(String value) {
		return RequestBody.create(MediaType.parse("text/plain") , value);
	}

	@Override
	public RequestBody toRequestBody(File value) {
		return RequestBody.create(MediaType.parse("text/plain") , value);
	}

}
