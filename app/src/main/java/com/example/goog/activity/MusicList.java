package com.example.goog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Getimg;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyMusic;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseViewHolder;
import com.example.goog.base.MyAppication;
import com.example.goog.fragment.Fragment_Me;
import com.example.goog.services.MusicService;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/4/10.
 */

public class MusicList extends BaseActivity {

    private ArrayList<MyMusic> listdat;
    private RecyclerView recyclerView;
    private ImageView imageView;
private  ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.music_list);
        imageView = imgByid(R.id.music_list_topimg);
        recyclerView = (RecyclerView) findViewById(R.id.music_list_recy);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        Intent intent = getIntent();
        String te = intent.getStringExtra("music");
        if ("1".equals(te)) {
            imageView.setImageResource(R.drawable.newmusic);
        } else if ("2".equals(te)) {
            imageView.setImageResource(R.drawable.newmusic4);
        } else if ("3".equals(te)) {
            imageView.setImageResource(R.drawable.newmusic2);
        } else {
            imageView.setImageResource(R.drawable.newmusic3);
        }
        Therdone threadOne = new Therdone();
        threadOne.start();
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
                        listdat = new ArrayList<MyMusic>();
                        arrayList=new ArrayList<String>();
                        Log.d("Log", "查询成功：共" + object.size());

                        for (Getimg gameScore : object) {
                            //获得playerName的信息
                            MyMusic getimg = new MyMusic();
                            getimg.music_name = gameScore.getName();
                            getimg.music_singer = gameScore.getMusic();
                            getimg.music_url = gameScore.getImg();
                            arrayList.add(gameScore.getImg());
                            listdat.add(getimg);
                        }

                        initViewOper();
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    protected void initViewOper() {


        final BaseAdapter<MyMusic> adapter = new BaseAdapter<MyMusic>(listdat, act, R.layout.music_list_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                MyMusic mimg = this.getmLists().get(position);
                TextView mTitle = holder.getView(R.id.music_list_item_name);
                TextView mTitle2 = holder.getView(R.id.music_list_item_singer);
                mTitle.setText(mimg.music_singer);
                mTitle2.setText(mimg.music_name);
            }

            @Override
            protected void setPositionClick(int position) {
//
             MyMusic mimg = this.getmLists().get(position);
                String url=mimg.music_url;
                Intent intent=new Intent(act,MusicAct.class);
                intent.putExtra("music_url",url);
                intent.putStringArrayListExtra("music_list",arrayList);
                startActivity(intent);
                MyAppication.put("music_url",url);
                MyAppication.put("music_list",arrayList);

                Log.d("Log",arrayList.size()+"");
                Log.d("Log",url);
            }

        };
        recyclerView.setAdapter(adapter);


    }
}
