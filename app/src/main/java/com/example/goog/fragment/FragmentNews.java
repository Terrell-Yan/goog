package com.example.goog.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.MainActivity;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.activity.bean.New;
import com.example.goog.activity.bean.New_data;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseFragment;
import com.example.goog.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/4/14.
 */

public class FragmentNews extends BaseFragment{
    public static final String ARG_PAGEGH="ARG_PAGEGH";
    private ScrollView slideShowView;
    private ArrayList<New_data> listdat;
    private RecyclerView recyclerView;
    private DisplayImageOptions imageOptions;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View v=inflater.inflate(R.layout.fragnews,null);
        return v;
    }
    public  static FragmentNews newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGEGH,page);
        FragmentNews fa=new FragmentNews();
        fa.setArguments(args);
        return  fa;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        slideShowView = (ScrollView) view.findViewById(R.id.fg_news_sv);
        recyclerView=(RecyclerView) view.findViewById(R.id.fg_news);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,RecyclerView.VERTICAL));

        recyclerView.setNestedScrollingEnabled(false);

        Log.d("Log","news" );
        Therdone threadOne = new Therdone();
        threadOne.start();
    }
    protected void initViewOper()
    {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseAdapter<New_data> adapter=  new BaseAdapter<New_data>(listdat, getContext(), R.layout.news_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                New_data mimg=this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.fg_news_item_tv);
                ImageView imageView=holder.getView(R.id.fg_news_item_iv);

                mTitle.setText(mimg.new_name);
                ImageLoader.getInstance().displayImage( mimg.new_img, imageView, imageOptions);


            }

            @Override
            protected void setPositionClick(int position) {
//




//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity. gotoDownloadFragment ("lll");




            }
//
        };
        recyclerView.setAdapter(adapter);

//
    }
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };
    class Therdone extends Thread{
        @Override
        public void run() {
            super.run();
            BmobQuery<New> query = new BmobQuery<New>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<New>() {
                @Override
                public void done(List<New> object, BmobException e) {
                    if (e == null) {
                        listdat=new ArrayList<New_data>();
                        Log.d("Log","news查询成功：共" + object.size() );

                        for (New gameScore : object) {
                            //获得playerName的信息
                            New_data getimg=new New_data();
                            getimg.new_img= gameScore.getNew_img();

                            getimg.new_name=gameScore.getNew_name();
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
}
