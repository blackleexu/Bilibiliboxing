package cn.edu.neusoft.lixu524.bilibiliboxing;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by www44 on 2018/5/6.
 */
public interface ApiService {
    @Multipart
    @POST("upload.php")
    Call<Result> uploadImage(@Part MultipartBody.Part file);

    @Multipart
    @POST("/test/fileabout.php")
    Call<String> uploadFile(@PartMap Map<String, RequestBody> params);
}