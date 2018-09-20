package kr.co.kpcard.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public interface HttpManager {

	public HttpMethod getHttpMethod(String baseUrl);
	
	public Response<ResponseBody> postHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException;
	
	public Response<ResponseBody> getHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException;
	
	public Response<ResponseBody> deleteHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException;
	
	public Response<ResponseBody> putHttpResponse(String baseUrl , String pathUrl, Map<String,String> param) throws IOException;
	
	public Call<?> getHttpResponseByWildcard(String baseUrl , String pathUrl, Map<String,String> param) throws IOException;
	
	public boolean writeResponseBodyToDisk(ResponseBody body , String fileName);

	public List<String> responseBodyToList(ResponseBody body);
	
	public RequestBody toRequestBody(String value);

	public RequestBody toRequestBody(File file);

	public List<String> responseBodyToListByCharset(ResponseBody body, String charset);
	
}
