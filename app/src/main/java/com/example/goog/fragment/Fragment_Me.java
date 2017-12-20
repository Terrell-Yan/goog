package com.example.goog.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.MainActivity;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.adbanner.BaseWebActivity;
import com.example.goog.adbanner.ImagePagerAdapter;
import com.example.goog.bannerview.CircleFlowIndicator;
import com.example.goog.bannerview.ViewFlow;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseFragment;
import com.example.goog.base.BaseHeadFootAdapter;
import com.example.goog.base.BaseViewHolder;

import com.example.goog.view.PullScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import android.os.Handler.Callback;
import android.widget.ViewFlipper;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.os.Looper.getMainLooper;

/**主界面数据的显示
 * Created by SeanM on 2017/3/29.
 */

public class Fragment_Me extends BaseFragment implements View.OnClickListener {
    public static final String ARG_PAGEG = "ARG_PAGEG";
    private int type;
    private ScrollView slideShowView;
    private ArrayList<Myimg> listdat;
    private RecyclerView recyclerView;
    private DisplayImageOptions imageOptions;

    //轮播图
    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();
    private LinearLayout notice_parent_ll;
    private LinearLayout notice_ll;
    private TextView tv, tv2, tv3, tv4;
    private int mCurrPos;
    private ViewFlipper notice_vf;

    @Override
    public View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.four, null);
        return view;
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.four,null);
//        return view;
//    }
    public static Fragment_Me newInstace(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGEG, page);
        Fragment_Me fa = new Fragment_Me();
        fa.setArguments(args);
        return fa;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取TYPE
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(ARG_PAGEG);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //lun
        mViewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
        notice_vf = (ViewFlipper) findViewById(R.id.homepage_notice_vf2);
        tv = (TextView) findViewById(R.id.homepage_tv);
        tv2 = (TextView) findViewById(R.id.homepagetv2);
        tv3 = (TextView) findViewById(R.id.homepage_tv3);
        tv4 = (TextView) findViewById(R.id.homepage_tv4);
        //
        tv.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        slideShowView = (ScrollView) view.findViewById(R.id.four_sv);
        recyclerView = (RecyclerView) view.findViewById(R.id.four_recl);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        //参数1. 上下文环境 参数2.一行/一列的条数  参数3.方向, 参数4. 是否倒序展示
//        recycl.setLayoutManager(new GridLayoutManager(this,4,RecyclerView.VERTICAL,false));
        //垂直的瀑布流
//        recycl.setLayoutManager(new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL));
//        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        //设置不可滑动
        recyclerView.setNestedScrollingEnabled(false);


        Therdone threadOne = new Therdone();
        threadOne.start();
//初始化轮播图
        imageUrlList
                .add("http://bmob-cdn-9958.b0.upaiyun.com/2017/04/27/ce70a4d2403bbe9a808295f1c689e23f.jpg");
        imageUrlList
                .add("http://bmob-cdn-9958.b0.upaiyun.com/2017/04/27/d8e17f7740dfe8618030e5280b289b3f.jpg");
        imageUrlList
                .add("http://bmob-cdn-9958.b0.upaiyun.com/2017/04/27/94b40561403ee5d5802741fbb8e9a31c.jpg");
        imageUrlList
                .add("http://bmob-cdn-9958.b0.upaiyun.com/2017/04/27/5736d59c40794b8380d74e5cfbee902a.jpg");

        linkUrlArray
                .add("http://www.iconfitness.com.cn/counselor_detail.php?id=547");
        linkUrlArray
                .add("http://www.iconfitness.com.cn/counselor_detail.php?id=545");
        linkUrlArray
                .add("http://www.iconfitness.com.cn/counselor_detail.php?id=530");
        linkUrlArray
                .add("http://www.iconfitness.com.cn/counselor_detail.php?id=524");
        titleList.add("常见Android进阶笔试题");
        titleList.add("GridView之仿支付宝钱包首页");
        titleList.add("仿手机QQ网络状态条的显示与消失 ");
        titleList.add("Android循环滚动广告条的完美实现 ");
        initBanner(imageUrlList);
        initRollNotice();

    }

    private void initRollNotice() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
                        Log.d("Task", "下一个");
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 4000);
    }

    private void moveNext() {
//        setView(this.mCurrPos, this.mCurrPos + 1);
        notice_vf.setInAnimation(getActivity(), R.anim.in_bottomtop);
        notice_vf.setOutAnimation(getActivity(), R.anim.out_bottomtop);
        notice_vf.showNext();
        notice_vf.startFlipping();
    }

    protected void initViewOper() {

//        recyclerView.setLayoutManager(linearLayoutManager);
        final BaseHeadFootAdapter<Myimg> adapter = new BaseHeadFootAdapter<Myimg>(listdat, getContext(), R.layout.recl_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                Myimg mimg = this.getmLists().get(position);

                TextView mTitle = holder.getView(R.id.recl_item_tv);
                ImageView imageView = holder.getView(R.id.recl_item_iv);
                mTitle.setText(mimg.name);
                ImageLoader.getInstance().displayImage(mimg.img, imageView, imageOptions);
            }

            @Override
            protected void setPositionClick(int position) {

                Myimg mimg = this.getmLists().get(position);
                String img = mimg.img;
                String img2 = mimg.img2;
                String name = mimg.name2;

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.gotoDownloadFragment(img, name, img2);


            }

            @Override
            protected void onBindHeaderView(BaseViewHolder holder, int position) {
                addHeader(R.layout.head);
            }

            @Override
            protected void onBindFooterView(BaseViewHolder holder, int position) {
                addFooter(R.layout.foot);
            }
        };
        recyclerView.setAdapter(adapter);

    }

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            BmobQuery<Girl> query = new BmobQuery<Girl>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Girl>() {
                @Override
                public void done(List<Girl> object, BmobException e) {
                    if (e == null) {
                        listdat = new ArrayList<Myimg>();
                        Log.d("Log", "查询成功：共" + object.size());

                        for (Girl gameScore : object) {
                            //获得playerName的信息
                            Myimg getimg = new Myimg();
                            getimg.img2 = gameScore.getImg2();
                            getimg.img = gameScore.getImg();
                            getimg.name2 = gameScore.getName2();
                            getimg.name = gameScore.getName();
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


    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_tv:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setWeb("http://www.iconfitness.com.cn/counselor_detail.php?id=547", "goole");

                break;
            case R.id.homepagetv2:
                MainActivity mainActivity2 = (MainActivity) getActivity();
                mainActivity2.setWeb("http://www.iconfitness.com.cn/counselor_detail.php?id=545", "goole");

                break;
            case R.id.homepage_tv3:
                MainActivity mainActivity3 = (MainActivity) getActivity();
                mainActivity3.setWeb("http://www.iconfitness.com.cn/counselor_detail.php?id=530", "goole");

                break;
            case R.id.homepage_tv4:
                MainActivity mainActivity4 = (MainActivity) getActivity();
                mainActivity4.setWeb("http://www.iconfitness.com.cn/counselor_detail.php?id=524", "goole");


                break;
        }
    }

}
