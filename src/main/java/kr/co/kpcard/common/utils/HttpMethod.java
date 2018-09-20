package kr.co.kpcard.common.utils;

import java.util.Map;

import kr.co.kpcard.billingservice.app.repository.settlement.hm.HappymoneyApiSettlementBaseApiObject;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface HttpMethod {
	
	@GET
	Call<ResponseBody> get(@Url String url , @QueryMap Map<String,String> argsMap);
	
	@GET
	Call<HappymoneyApiSettlementBaseApiObject> getByWildcard(@Url String url , @QueryMap Map<String,String> argsMap);	
	
	@PUT
	Call<ResponseBody> put(@Url String url , @QueryMap Map<String,String> argsMap);

	@DELETE
	Call<ResponseBody> delete(@Url String url , @QueryMap Map<String,String> argsMap);
	
	@POST
	Call<ResponseBody> post(@Url String url , @QueryMap Map<String,String> argsMap);
	
	@POST
	@Multipart
	Call<ResponseBody> postFileUpload(@Url String url , @QueryMap Map<String,String> argsMap , @Part MultipartBody.Part file);	

}
