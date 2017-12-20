package com.example.goog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.goog.R;
import com.example.goog.base.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by SeanM on 2017/6/29.
 */

public class GifActivity extends BaseActivity {
    ImageView iv,iv1,iv2,iv3,iv4;
    TextView tv,gif_title;
    private LinearLayout lin;
    private RelativeLayout re;
    private DisplayImageOptions imageOptions;
    private int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_layout);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        Intent intent=getIntent();
        String vido="";
        vido=intent.getStringExtra("vido_iv");
        String name=intent.getStringExtra("vido_name");
        lin=(LinearLayout)findViewById(R.id.lin_gif);
        re=(RelativeLayout)findViewById(R.id.gif_lin);
        iv=(ImageView)findViewById(R.id.gif_iv);
        tv=(TextView) findViewById(R.id.gif_tv5);
        iv1=(ImageView)findViewById(R.id.gif_g1);
        iv2=(ImageView)findViewById(R.id.gif_g2);
        iv3=(ImageView)findViewById(R.id.gif_g3);
        iv4=(ImageView)findViewById(R.id.gif_g4);
        gif_title=(TextView) findViewById(R.id.gif_title);
        ImageLoader.getInstance().displayImage(vido, iv, imageOptions);
        gif_title.setText(name);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    gif_title.setText("腿部扩展");
                    Glide.with(getApplication()).load("http://bmob-cdn-9958.b0.upaiyun.com/2017/05/25/33f5130940e713dc808480e5dd35840f.gif").asGif().into(iv);
                }else if(count==1){
                    gif_title.setText("平板支撑");
                    Glide.with(getApplication()).load("http://bmob-cdn-9958.b0.upaiyun.com/2017/07/03/07b1d5da40c891108057e9e94ad3da88.gif").asGif().into(iv);
                }else if (count==2){
                    gif_title.setText("膝盖侧压");
                    Glide.with(getApplication()).load("http://bmob-cdn-9958.b0.upaiyun.com/2017/07/03/a0d9d731409bde7b80dadd50f6a92977.gif").asGif().into(iv);
                }

                tv.setText("下一个动作");
                count++;
            }
        });
    }
}
