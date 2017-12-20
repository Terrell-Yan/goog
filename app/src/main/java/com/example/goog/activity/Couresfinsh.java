package com.example.goog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Collection;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.New_data;
import com.example.goog.activity.bean.UserExtend;
import com.example.goog.activity.bean.User_Et;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/5/4.
 */

public class Couresfinsh  extends BaseActivity {
    private RecyclerView fragment_recl;
    private List<User_Et> listdat;

    private DisplayImageOptions imageOptions;
    private Intent intent=getIntent();
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.course);
        fragment_recl = (RecyclerView) findViewById(R.id.course_recl);
        fragment_recl.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();

        initData();
    }
    private void initData() {
        Therdone therdone = new Therdone();
        therdone.start();

    }

    protected void initViewOper() {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseAdapter<User_Et> adapter = new BaseAdapter<User_Et>(listdat, act, R.layout.news_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                User_Et mimg = this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.fg_news_item_tv);
                ImageView imageView = holder.getView(R.id.fg_news_item_iv);
//                if ("1".equals(intent.getStringExtra("finsh"))){
//                    mTitle.setText(mimg.user_fans);
//                    ImageLoader.getInstance().displayImage(mimg.user_steep, imageView, imageOptions);
//                }else{
//
//                }
                mTitle.setText(mimg.user_claller);
                ImageLoader.getInstance().displayImage(mimg.finsh_url, imageView, imageOptions);
                Log.d("Log", "-------" + mimg.user_claller + "----------" + mimg.finsh_url);
            }

            @Override
            protected void setPositionClick(int position) {
//


//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity. gotoDownloadFragment ("lll");


            }
//
        };
        fragment_recl.setAdapter(adapter);

//
    }

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            MyUser user = BmobUser.getCurrentUser(MyUser.class);
            BmobQuery<UserExtend> query = new BmobQuery<UserExtend>();
            query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
            query.order("-updatedAt");
            query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
            query.findObjects(new FindListener<UserExtend>() {

                @Override
                public void done(List<UserExtend> object, BmobException e) {
                    if (e == null) {
                        listdat = new ArrayList<>();

                        for (UserExtend collection : object) {
                            User_Et new_data = new User_Et();
                            new_data.user_steep = collection.getUser_claller();
                            new_data.user_fans = collection.getUser_fans();
                            new_data.user_claller = collection.getUser_steep();
                            new_data.finsh_url=collection.getFinsh_url();
                            listdat.add(new_data);
                        }
                        Log.i("bmob", "成功" + object.size() + "数据" + listdat);
                        initViewOper();
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage());
                    }
                }

            });
        }
    }
}
