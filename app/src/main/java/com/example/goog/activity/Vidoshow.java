package com.example.goog.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.goog.R;
import com.example.goog.activity.bean.Collection;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.UserExtend;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.Util;
import com.example.goog.base.Utils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by SeanM on 2017/4/1.
 */

public class Vidoshow extends BaseActivity implements View.OnClickListener {
    private VideoView videoView;
    private ImageView imageView;
    private TextView textView;
    private String vidourl;
    private String name;
    private RelativeLayout relativeLayout;
    boolean falag = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.vido_show);

        videoView = (VideoView) findViewById(R.id.vido_show);
        imageView = (ImageView) findViewById(R.id.vido_show_iv);
        textView = (TextView) findViewById(R.id.vido_show_tv);
        Intent intent = getIntent();
        vidourl = intent.getStringExtra("vido_url");
        name = intent.getStringExtra("vido_name");
        textView.setText(name);
        relativeLayout = (RelativeLayout) findViewById(R.id.vido_show_re);

        Uri uri = Uri.parse(vidourl);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);
//        获取视频的控制器
        videoView.setMediaController(new MediaController(this));
//        播放完成的回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletioanListener());
//        设置视频的路径
        videoView.setVideoURI(uri);
//开始播放
        videoView.start();
        imageView.setOnClickListener(this);


        if (falag == false) {
//            Thredthree thredthree = new Thredthree();
//            thredthree.start();

        }

        //得到总时长
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    class MyPlayerOnCompletioanListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(getApplicationContext(), "播放完成了", Toast.LENGTH_LONG).show();
            falag = true;
//            Thredtwo thredtwo = new Thredtwo();
//            thredtwo.start();


        }
    }

    class Thredtwo extends Thread {
        @Override
        public void run() {
            super.run();
            MyUser user = BmobUser.getCurrentUser(MyUser.class);

// 创建帖子信息

            UserExtend post = new UserExtend();

            post.setUser_fans(name);
            post.setUser_steep(vidourl);
//添加一对一关联
            post.setAuthor(user);


            post.save(new SaveListener<String>() {

                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        finish();
                    } else {

                    }
                }
            });
        }
    }

    class Thredthree extends Thread {
        @Override
        public void run() {
            super.run();
            MyUser user = BmobUser.getCurrentUser(MyUser.class);

// 创建帖子信息

            UserExtend post = new UserExtend();

            post.setUser_claller(name);
            post.setFinsh_url(vidourl);
//添加一对一关联
            post.setAuthor(user);


            post.save(new SaveListener<String>() {

                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        finish();
                    } else {

                    }
                }
            });
        }
    }
}
