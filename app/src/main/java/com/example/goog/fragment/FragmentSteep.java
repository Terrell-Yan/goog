package com.example.goog.fragment;

import android.content.Intent;
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
import com.example.goog.activity.StepActivity;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.activity.bean.Steep;
import com.example.goog.activity.bean.Steep_num;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseFragment;
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
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by SeanM on 2017/4/14.
 */

public class FragmentSteep extends BaseFragment implements View.OnClickListener{
    public static final String ARG_PAGEE="ARG_PAGEGE";
    private ScrollView slideShowView;
    private ArrayList<Steep_num> listdat;
    private RecyclerView recyclerView;
    private DisplayImageOptions imageOptions;
    private TextView textView;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View v=inflater.inflate(R.layout.steep,null);
        return v;
    }

   public static  FragmentSteep newInstace(int a){
       Bundle args =new Bundle();
       args.putInt(ARG_PAGEE,a);
       FragmentSteep fa=new FragmentSteep();
       fa.setArguments(args);
       return fa;
   }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=(TextView) findViewById(R.id.steep_start);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        slideShowView = (ScrollView) view.findViewById(R.id.steep_sv);
        recyclerView=(RecyclerView) view.findViewById(R.id.steep_ry);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,RecyclerView.VERTICAL));

        recyclerView.setNestedScrollingEnabled(false);
        textView.setOnClickListener(this);
        Log.d("Log","news" );
     Therdone threadOne = new Therdone();
        threadOne.start();
    }
    protected void initViewOper()
    {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseAdapter<Steep_num> adapter=  new BaseAdapter<Steep_num>(listdat, getContext(), R.layout.steep_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                Steep_num mimg=this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.steep_nickname);
                ImageView imageView=holder.getView(R.id.steep_avatar);
                TextView mTitle2 = holder.getView(R.id.steep_score);
                mTitle.setText(mimg.steep_name);
                mTitle2.setText(mimg.steep_num);
                ImageLoader.getInstance().displayImage( mimg.steep_img, imageView, imageOptions);
            }

            @Override
            protected void setPositionClick(int position) {
//




//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity. gotoDownloadFragment ("kkk");




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
            BmobQuery<Steep> query = new BmobQuery<Steep>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Steep>() {
                @Override
                public void done(List<Steep> object, BmobException e) {
                    if (e == null) {
                        listdat=new ArrayList<Steep_num>();
                        Log.d("Log","news查询成功：共" + object.size() );

                        for (Steep gameScore : object) {
                            //获得playerName的信息
                            Steep_num getimg=new Steep_num();
//                            getimg.img= gameScore.getImg();
//                            getimg.img2= gameScore.getImg2();
//                            getimg.img3= gameScore.getImg3();
//                            getimg.img4= gameScore.getImg4();
                            getimg.steep_name=gameScore.getSteep_name();
                            getimg.steep_img=gameScore.getSteep_img();
                            getimg.steep_num=gameScore.getSteep_num();

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

    @Override
    public void onClick(View v) {
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//// 创建帖子信息
//        Steep post = new Steep();
//        post.setSteep_num("0");
////添加一对一关联
//        post.setAuthor(user);
//        post.save(new SaveListener<String>() {
//
//            @Override
//            public void done(String objectId,BmobException e) {
//                if(e==null){
//                    Log.i("bmob","保存成功");
//                }else{
//                    Log.i("bmob","保存失败："+e.getMessage());
//                }
//            }
//        });
        Intent intent=new Intent(getActivity(), StepActivity.class);
        startActivity(intent);

    }
}
