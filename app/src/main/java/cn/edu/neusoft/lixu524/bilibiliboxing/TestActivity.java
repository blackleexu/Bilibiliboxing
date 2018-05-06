package cn.edu.neusoft.lixu524.bilibiliboxing;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.io.File;

import jp.wasabeef.blurry.Blurry;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by www44 on 2018/4/25.
 */

public class TestActivity extends TakePhotoActivity {
    private TextView tv;
    private ImageView img;
    private Uri imageUri;
    private Button btn;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        //设置一个裁剪后图片的存储位置
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        //绑定视图
        tv=findViewById(R.id.textView);
        img=findViewById(R.id.imageView);
        btn=findViewById(R.id.button2);
        //获取一张图片不裁剪
        //getTakePhoto().onPickFromGallery();
        //获取一张图片并裁剪

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setAspectY(1).setOutputX(50).setOutputY(50).setWithOwnCrop(true).create();
                getTakePhoto().onPickFromGalleryWithCrop(imageUri,cropOptions);
            }
        });
    }


    //重写3个方法：成功后操作、失败、取消，其实一般重写takesuccess就行了。
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
//        Log.e("success",""+result.getImage().getOriginalPath().toString());
        //成功后将图片地址和图片显示到控件上
        tv.setText(result.getImage().getOriginalPath().toString());
        path=result.getImage().getOriginalPath().toString();
        Glide.with(this).load(new File(result.getImage().getOriginalPath())).into(img);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://39.105.20.169/php_upload/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        File file = new File(path);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
        if(!file.exists()){
            Log.e("fail","not exist");
            return;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<Result> call = service.uploadImage(body);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult().equals("success"))
                        Log.e("success","success");
                    Toast.makeText(TestActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("fail","fail");
            }
        });

//        File file = new File(result.getImage().getOriginalPath());
//        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
//        if (file.exists() && file.isFile()) {
//            file.delete();//操作完成后删除临时文件
//        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
}
