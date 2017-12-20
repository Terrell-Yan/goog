package com.example.goog.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Collection;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.New_data;
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
 * Created by SeanM on 2017/4/26.
 */

public class CollectionActivity extends BaseActivity {
    private RecyclerView fragment_recl;
    private List<New_data> listdat;

    private DisplayImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.layout1);
        fragment_recl = (RecyclerView) findViewById(R.id.fragment_recl);
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
        final BaseAdapter<New_data> adapter = new BaseAdapter<New_data>(listdat, act, R.layout.news_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                New_data mimg = this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.fg_news_item_tv);
                ImageView imageView = holder.getView(R.id.fg_news_item_iv);
                mTitle.setText(mimg.new_name);
                ImageLoader.getInstance().displayImage(mimg.new_url, imageView, imageOptions);
                Log.d("Log", "-------" + mimg.new_name + "----------" + mimg.new_url);
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
            BmobQuery<Collection> query = new BmobQuery<Collection>();
            query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
            query.order("-updatedAt");
            query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
            query.findObjects(new FindListener<Collection>() {

                @Override
                public void done(List<Collection> object, BmobException e) {
                    if (e == null) {
                        listdat = new ArrayList<>();

                        for (Collection collection : object) {
                            New_data new_data = new New_data();
                            new_data.new_name = collection.getName();
                            new_data.new_img = collection.getImg();
                            new_data.new_url = collection.getUrl();
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
