package com.example.goog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.activity.bean.Collection;
import com.example.goog.activity.bean.Grigu;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.base.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 首页的item的点击事件
 * Created by SeanM on 2017/4/20.
 */

public class Fe_item_listen extends BaseActivity implements View.OnClickListener{
    private DisplayImageOptions imageOptions;
    private ImageView imageView;
    private TextView textView,but,shuoming;
    private RelativeLayout relativeLayout;
    private String name;
    private String url;
    private String img;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fe_item_listen);
        imageView=(ImageView)findViewById(R.id.fe_item_lis_img);
        textView=tvByid(R.id.fe_item_lis_name);
        but=tvByid(R.id.fe_item_listen_but);
        shuoming=tvByid(R.id.fe_item_listen_shuoming);
        relativeLayout=(RelativeLayout)findViewById( R.id.fe_item_listen_re);
        Intent intent=getIntent();
        url=intent.getStringExtra("Fe_item_listen_img_url");
        name=intent.getStringExtra("Fe_item_listen_img_name");
        img=intent.getStringExtra("Fe_item_listen_img_img");
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        ImageLoader.getInstance().displayImage( url, imageView, imageOptions);
        textView.setText(name);
        shuoming.setText(img);
        but.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fe_item_listen_but:
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                if(user!=null){
                    Intent intent2 = new Intent(act, GifActivity.class);
                    intent2.putExtra("vido_iv", url);
                    intent2.putExtra("vido_name", name);
                    startActivity(intent2);

// 创建帖子信息
                    String username = (String) user.getObjectByKey("nickName");
                    String avatar2 = (String) user.getObjectByKey("avatarimg");
                    Collection post = new Collection();
                    post.setImg(img);
                    post.setName(name);
                    post.setUrl(url);
//添加一对一关联
                    post.setAuthor(user);


                    post.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                                finish();
                            }else{

                            }
                        }
                    });
                }else{
                    Toast.makeText(act,"您还未登录，请登录后操作",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.fe_item_listen_re:
//                Intent intent = new Intent(act, Vidoshow.class);
//                intent.putExtra("vido_url", "http://bmob-cdn-9958.b0.upaiyun.com/2017/04/24/d83baab640effe06801ac2de73b91d8c.mp4");
//                intent.putExtra("vido_name", "A-T运动物理治疗中心");
//                startActivity(intent);
                break;
        }
    }
}
