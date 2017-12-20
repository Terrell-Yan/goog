package com.example.goog.fragment;

import android.accessibilityservice.AccessibilityService;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.activity.bean.User;
import com.example.goog.activity.diolag.ProgersssDialog;
import com.example.goog.view.CardConfig;
import com.example.goog.view.CardItemTouchHelperCallback;
import com.example.goog.view.CardLayoutManager;
import com.example.goog.view.ContainLoadViewLayout;
import com.example.goog.view.ExplosionField;
import com.example.goog.view.MyImageView;
import com.example.goog.view.OnSwipeListener;
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
 * 附近人点赞
 * Created by SeanM on 2017/3/29.
 */

public class Fragment_sister extends Fragment {
    ContainLoadViewLayout containLoadViewLayout;
    private List<Integer> list = new ArrayList<>();
    public DisplayImageOptions imageOptions;
    RecyclerView recyclerView;
    private ArrayList<User> listdat = new ArrayList<>();
    //爆炸区域
    private ExplosionField mExplosionField;

    ProgersssDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.code_time, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.code_recyclerView);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog=new ProgersssDialog(getActivity());
        dialog.show();

        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        mExplosionField = ExplosionField.attach2Window(getActivity());


        initData();
        initView();

        Therdone therdone=new Therdone();
        therdone.start();
    }

    //给需要爆炸的视图添加到爆炸区域中
    private void addListener(View root) {

        //如果是view group 类型 就把它的子视图添加到区域中
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        }

        //这里是View 类型的视图
        else {

            //设置它为可点击的
            root.setClickable(true);

            //添加监听器
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //爆炸该视图
                    mExplosionField.explode(v);
                    //取消注册其点击事件
                    v.setOnClickListener(null);
                }
            });
        }
    }

    private void initView() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());
        new MyAdapter().notifyDataSetChanged();

        new MyAdapter().setOnItemOnclik(new OnItemOnclik() {
            @Override
            public void onItemOnclick(View v, int position) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        {

        }
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), listdat);
        cardCallback.setOnSwipedListener(new OnSwipeListener<User>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
//                if (direction == CardConfig.SWIPING_LEFT) {
//                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
//                } else if (direction == CardConfig.SWIPING_RIGHT) {
//                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
//                } else {
//                    myHolder.dislikeImageView.setAlpha(0f);
//                    myHolder.likeImageView.setAlpha(0f);
//                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, User o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1f);
//                myHolder.dislikeImageView.setAlpha(0f);
//                myHolder.likeImageView.setAlpha(0f);
//                Toast.makeText(MainActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
//                Toast.makeText(MainActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);


    }

    private void initData() {

        BmobQuery<MyUser> query = new BmobQuery<MyUser>();

//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {

                    Log.d("Log", "查询成功：共" + object.size());

                    for (MyUser gameScore : object) {
                        //获得playerName的信息

                        User getimg = new User();
                        getimg.age = gameScore.getAge();
                        getimg.constelLation = gameScore.getConstelLation();
                        getimg.headPhoto = gameScore.getAvatarimg();
                        getimg.posiTion = gameScore.getPosiTion();
                        getimg.nickName = gameScore.getNickName();
                        listdat.add(getimg);
                        list.add(R.drawable.img_avatar_01);
                    }


                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter {
        private OnItemOnclik onItemOnclik;

        public void setOnItemOnclik(OnItemOnclik onItemOnclik) {
            this.onItemOnclik = onItemOnclik;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView posiTion=((MyViewHolder) holder).posiTion;
            TextView name=((MyViewHolder) holder).name;
            TextView constelLation=((MyViewHolder) holder).constelLation;
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            User user = listdat.get(position);
            final ImageView hert = ((MyViewHolder) holder).likeImageView;
            hert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addListener(hert);
                }
            });
            hert.setImageResource(R.drawable.img_like);
            ImageLoader.getInstance().displayImage(user.headPhoto, avatarImageView, imageOptions);
            posiTion.setText(user.posiTion);
            name.setText(user.nickName);
            constelLation.setText(user.constelLation);
//            dialog.dismiss();
        }

        @Override
        public int getItemCount() {
            return listdat.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;

            TextView name, posiTion, age, constelLation;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);

                name = (TextView) itemView.findViewById(R.id.tv_name);
                posiTion = (TextView) itemView.findViewById(R.id.code_position);
                age = (TextView) itemView.findViewById(R.id.tv_age);
                constelLation = (TextView) itemView.findViewById(R.id.code_tv_constellation);
            }

        }
    }

    interface OnItemOnclik {
        void onItemOnclick(View v, int position);
    }

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                sleep(3000);
                dialog.dismiss();
            }catch (InterruptedException e){

            }

        }
    }
}

