package com.example.goog.activity.addpter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.MainActivity;
import com.example.goog.activity.bean.Dynamic;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.Grigu;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseViewHolder;
import com.example.goog.fragment.Fragment_Me;
import com.example.goog.view.GifView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/3/29.
 */

public class Fragrment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private ArrayList<Dynamic> listdat;
    private RecyclerView recyclerView;

    private ArrayList<String> arrayList;

    private DisplayImageOptions imageOptions;

    public static Fragrment newInstace(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragrment fa = new Fragrment();
        fa.setArguments(args);
        return fa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout1, container, false);

//        gif=(GifView) view.findViewById(R.id.gif_iv);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
//        gif.setMovieResource(R.raw.gif);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recl);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
//        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
//        recyclerView.setNestedScrollingEnabled(false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrayList = new ArrayList<>();


        Therdone threadOne = new Therdone();
        threadOne.start();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            BmobQuery<Grigu> query = new BmobQuery<Grigu>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
//执行查询方法
            query.findObjects(new FindListener<Grigu>() {
                @Override
                public void done(List<Grigu> object, BmobException e) {
                    if (e == null) {

                        listdat = new ArrayList<Dynamic>();
                        Log.d("Log", "查询成功：共" + object.size());

                        for (Grigu gameScore : object) {
                            //获得playerName的信息
                            Dynamic getimg = new Dynamic();
                            getimg.dynamic_img = gameScore.getDynamic_img();
                            getimg.dynamic_img2 = gameScore.getDynamic_img2();
                            getimg.dynamic_name = gameScore.getDynamic_name();
                            getimg.dynamic_text = gameScore.getDynamic_text();
                            getimg.dynamic_time = gameScore.getDynamic_time();
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


        final BaseAdapter<Dynamic> adapter = new BaseAdapter<Dynamic>(listdat, getContext(), R.layout.gridview_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                Dynamic mimg = this.getmLists().get(position);
                TextView mTitle3 = holder.getView(R.id.gridview_tv3);
                TextView mTitle2 = holder.getView(R.id.gridview_tv2);
                TextView mTitle = holder.getView(R.id.gridview_tv);
                ImageView imageView = holder.getView(R.id.gridview_img);
                ImageView imageView2 = holder.getView(R.id.gridview_iv2);
                mTitle.setText(mimg.dynamic_name);
                mTitle2.setText(mimg.dynamic_time);
                mTitle3.setText(mimg.dynamic_text);
                ImageLoader.getInstance().displayImage(mimg.dynamic_img, imageView, imageOptions);
                ImageLoader.getInstance().displayImage(mimg.dynamic_img2, imageView2, imageOptions);
            }

            @Override
            protected void setPositionClick(int position) {


//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity. gotoDownloadFragment ("jjj");


            }

        };
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
