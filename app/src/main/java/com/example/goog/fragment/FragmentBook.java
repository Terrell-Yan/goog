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
import com.example.goog.activity.bean.Book;
import com.example.goog.activity.bean.Book_item;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseApp;
import com.example.goog.base.BaseFragment;
import com.example.goog.base.BaseViewHolder;
import com.example.goog.base.MyAppication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 阅读书籍
 * Created by SeanM on 2017/4/11.
 */

public class FragmentBook extends BaseFragment {
    private RecyclerView recyclerView;
    private ArrayList<Book_item> listdat;
    public static final String ARG_PAGEG="ARG_PAGEG";
    private DisplayImageOptions imageOptions;
    private ScrollView slideShowView;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.fragment_book,null);
        return view;
    }
    public  static FragmentBook newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGEG,page);
        FragmentBook fa=new FragmentBook();
        fa.setArguments(args);
        return  fa;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        slideShowView=(ScrollView)view.findViewById(R.id.boo_sv);
        recyclerView=(RecyclerView)view.findViewById(R.id.fg_book_ry);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,RecyclerView.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        Therdone threadOne =new Therdone();
        threadOne.start();
    }
    protected void initViewOper()
    {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseAdapter<Book_item> adapter=  new BaseAdapter<Book_item>(listdat, getContext(), R.layout.fg_book_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                Book_item mimg=this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.book_name);
                ImageView imageView=holder.getView(R.id.book_img);
                mTitle.setText(mimg.book_name);
                ImageLoader.getInstance().displayImage( mimg.book_img, imageView, imageOptions);
            }

            @Override
            protected void setPositionClick(int position) {
//                Fragment_Me fragment_me=new Fragment_Me();
//                Fragment fragment=new Fragment();
//                showFragment(fragment_me,fragment,R.id.fra_move);
//                Handler handler=new MainActivity.RecHandler();
//                Message msg = handler.obtainMessage();
//
//                msg.obj = position;
//
//                handler.sendMessage(msg);
                Book_item mimg=this.getmLists().get(position);
                MyAppication.put("bookname",mimg.book_name);
                MyAppication.put("bookurl",mimg.book_url);


                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity. gotoBook (mimg.book_name,mimg.book_url);




            }
//            @Override
//            protected void onBindHeaderView(BaseViewHolder holder, int position) {
//              addHeader(R.layout.head);
//            }
//
//            @Override
//            protected void onBindFooterView(BaseViewHolder holder, int position) {
//                addFooter(R.layout.foot);
//            }
        };
        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new OnReclItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.d("Log","onclick");
//                Toast.makeText(getActivity(),"position"+position,Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    class Therdone extends Thread{
        @Override
        public void run() {
            super.run();
            BmobQuery<Book> query = new BmobQuery<Book>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Book>() {
                @Override
                public void done(List<Book> object, BmobException e) {
                    if (e == null) {
                        listdat=new ArrayList<Book_item>();
                        Log.d("Log","查询成功：共" + object.size() );

                        for (Book gameScore : object) {
                            //获得playerName的信息
                            Book_item getimg=new Book_item();
                            getimg.book_img= gameScore.getBook_img();
                            getimg.book_name=gameScore.getBook_name();
                            getimg.book_url=gameScore.getBook_path();
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
