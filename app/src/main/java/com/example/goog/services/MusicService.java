package com.example.goog.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.goog.activity.bean.Getimg;
import com.example.goog.activity.bean.MyMusic;
import com.example.goog.base.MyAppication;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/4/10.
 */

public class MusicService extends Service{
    private  ArrayList<String> arrayList;
    private int musicIndex=0;
//    private ArrayList<String> data;
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder{
        public MusicService getService() {
            return MusicService.this;
        }
    }
    /**
     * onBind 是 Service 的虚方法，因此我们不得不实现它。
     * 返回 null，表示客服端不能建立到此服务的连接。
     */
    public static MediaPlayer mp = new MediaPlayer();
    private String url;
    public MusicService() {
        Therdone therdone=new Therdone();
        therdone.start();

    }
//    public void setDatalist(ArrayList<String> datalist){
//        this.data=datalist;
//    }
//    public void setData(String url){
//      this.url=url;
//    }

    public void playOrPause() {
        if(mp.isPlaying()){
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void stop() {
        if(mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void nextMusic() {

        if(mp != null && arrayList.size()>0) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(arrayList.get(musicIndex+1));
                musicIndex++;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump next music");
                e.printStackTrace();
            }
        }
    }
    public void preMusic() {
        if(mp != null && musicIndex > 0) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(arrayList.get(musicIndex-1));
                musicIndex--;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
private void initView(){
    url= (String) MyAppication.get("music_url",true);
//        data=(ArrayList<String>) MyAppication.get("music_list",true);
    Log.d("Log",url+"音乐");
//    Log.d("Log",(ArrayList<String>) MyAppication.get("music_list",true)+"音乐");
        for (int i=0;i<arrayList.size();i++){
            if (url.equals(arrayList.get(i))){
                musicIndex=i;
            }
        }
    try {
        mp.setDataSource(url);
        //mp.setDataSource(Environment.getDataDirectory().getAbsolutePath()+"/You.mp3");
        mp.prepare();

    } catch (Exception e) {
        Log.d("hint","can't get to the song");
        e.printStackTrace();
    }
    }












    @Override
    public IBinder onBind(Intent intent) {
//        Bundle bundle = intent.getExtras();
////        data=bundle.getStringArrayList("music_list2");
//        url=bundle.getString("music2_url");
        return binder;
    }
    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            BmobQuery<Getimg> query = new BmobQuery<Getimg>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Getimg>() {
                @Override
                public void done(List<Getimg> object, BmobException e) {
                    if (e == null) {

                        arrayList=new ArrayList<String>();
                        Log.d("Log", "查询成功：共" + object.size());

                        for (Getimg gameScore : object) {
                            //获得playerName的信息
                            MyMusic getimg = new MyMusic();
                            getimg.music_name = gameScore.getName();
                            getimg.music_singer = gameScore.getMusic();
                            getimg.music_url = gameScore.getImg();
                            arrayList.add(gameScore.getImg());

                        }
                    initView();

                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }
}
