package cn.edu.neusoft.lixu524.bilibiliboxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView img;
    private boolean blurred = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.button);
        img= (ImageView) findViewById(R.id.right_top);
        final ViewGroup vv= (ViewGroup) findViewById(R.id.content);

        //获取bigmap模糊处理后填充到对应的imageview中
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rat);
//        Blurry.with(MainActivity.this)
//                .radius(25)
//                .sampling(2)
//                .async()
//                .from(bitmap)
//                .into(img);
//        //对imageview中的图片提出进行处理后再填充回去
//        Blurry.with(MainActivity.this)
//                        .radius(25)
//                        .sampling(2)
//                        .async()
//                        .capture(img)
//                        .into(img);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (blurred) {
//                    Blurry.delete((ViewGroup) findViewById(R.id.content));
//                } else {
                    //对整个viewgroup：整个LinearLayout布局
                    Blurry.with(MainActivity.this)
                            .radius(10)
                            .sampling(8)
                            .color(Color.argb(66, 255, 255, 0))
                            .async()
                            .animate(500)
                            .onto(vv);

//                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
//                    startActivity(intent);
//                }
//                blurred = !blurred;
            }
        });
    }
}
