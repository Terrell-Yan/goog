package com.example.goog.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.MainActivity;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyVido;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.activity.bean.Vido;
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
 * 视频播放
 * Created by SeanM on 2017/3/31.
 */

public class VdoFragment extends BaseFragment implements View.OnClickListener{
    public static final String ARG_PAGEE="ARG_PAGEGE";
    private ScrollView scrollView;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MyVido> listdat;
    private DisplayImageOptions imageOptions;
    private ImageView imageView;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View v=inflater.inflate(R.layout.vidofg,null);
        return v;
    }
    public  static VdoFragment newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGEE,page);
        VdoFragment fa=new VdoFragment();
        fa.setArguments(args);
        return  fa;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        scrollView =(ScrollView)view.findViewById(R.id.vidoag_sv);
        relativeLayout=(RelativeLayout)findViewById(R.id.vidofg_re);
        recyclerView=(RecyclerView)findViewById(R.id.vidoag_recl);
        imageView=(ImageView) findViewById(R.id.vidofg_banner_img);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,RecyclerView.VERTICAL));
//        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(false);
        Therdone threadOne = new Therdone();
        threadOne.start();
        imageView.setOnClickListener(this);
    }
    class Therdone extends Thread{
        @Override
        public void run() {
            super.run();
            BmobQuery<Vido> query = new BmobQuery<Vido>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Vido>() {
                @Override
                public void done(List<Vido> object, BmobException e) {
                    if (e == null) {
                        listdat=new ArrayList<MyVido>();
                        Log.d("Log","vido查询成功：共" + object.size() );

                        for (Vido gameScore : object) {
                            //获得playerName的信息
                            MyVido getimg=new MyVido();
                            getimg.vido_url= gameScore.getvido_url();
                            getimg.vido_img= gameScore.getvido_img();
                            getimg.vido_data=gameScore.getvido_data();
                            listdat.add(getimg);
                        }

                        initViewOper();
                    } else {
                        Log.i("Log", "vido查询失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }
    private String url2;
    private String url;
    private String name;
    private String iv;
    protected void initViewOper()
    {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseAdapter<MyVido> adapter=  new BaseAdapter<MyVido>(listdat, getContext(), R.layout.vido_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                MyVido mimg=this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.vido_item_tv);
                ImageView imageView=holder.getView(R.id.vido_item_iv);
                mTitle.setText(mimg.vido_img);
                url2=mimg.vido_data;
                ImageLoader.getInstance().displayImage( mimg.vido_url, imageView, imageOptions);
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

                MyVido mimg=this.getmLists().get(position);
                url=mimg.vido_data;
                name=mimg.vido_img;
                iv=mimg.vido_url;

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity. goFragment (url,name,iv);




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

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity. goFragment (url2,"跑步训练",iv);
    }
}
