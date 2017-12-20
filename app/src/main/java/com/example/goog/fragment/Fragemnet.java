package com.example.goog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.activity.addpter.Fragrment;

import java.util.ArrayList;

/**
 * Created by SeanM on 2017/3/30.
 */

public class Fragemnet extends Fragment{
    public static final String ARG_PAGE="ARG_PAGE";
    /**
     * 导入RecyclerView
     */
    private ArrayList<String> datas;
    private RecyclerView recycl;
    private int optionId = 0;
    private MyweAdapter adapter;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frab,null);
        linearLayout=(LinearLayout)view.findViewById(R.id.fra_move);
        return view;
    }
    public   static Fragment newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGE,page);
        Fragemnet fa=new Fragemnet();
        fa.setArguments(args);
       return fa;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        recycl = (RecyclerView) view.findViewById(R.id.far_recyc);
//        datas = new ArrayList<>();
//        for (int i='A';i<'z';i++){
//            datas.add("" + (char) i);
//
//        }
//        recycl.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new MyweAdapter();
//        recycl.setAdapter(adapter);
//        recycl.setItemAnimator(new DefaultItemAnimator());

    }
    class MyweAdapter extends RecyclerView.Adapter<MyweViewHolder>{



        //用来注册观察者的位置
        private OnfaItemClickListener listener;
        public void OnfaItemClickListener( OnfaItemClickListener listener){
            this.listener = listener;
        }

        /**
         *创建ViewHolder , 此方法只有当第一页item被创建时 会执行!
         * @param parent 参数1. RecyclerView对象
         * @param viewType 当前的View类型!
         * @return
         */
        @Override
        public MyweViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /**
             * 参数1. 布局id
             * 参数2. 根节点
             * 参数3. 是否绑定这个根节点
             */
            View view = getLayoutInflater(getArguments()).inflate(R.layout.fra, parent, false);
           MyweViewHolder holder = new MyweViewHolder(view);
            return holder;
        }

        /**
         * 根据position 系统自动传入应优化的包含item的holder对象!
         * 一般我们在此方法中 将对应position的数据, 设置到holder中的item上
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final MyweViewHolder holder, final int position) {
            /**
             * 获取View的参数配置对象
             */
            ViewGroup.LayoutParams para = holder.tv.getLayoutParams();


            holder.tv.setLayoutParams(para);
            holder.tv.setText(datas.get(position));

            /**
             * 监听每一个Item的点击
             * 如果点击,  通知观察者, 并且传入被点击的View对象,与item下标
             */
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text =  holder.tv.getText().toString().trim();

                    listener.onfaItemtClick(v,datas.indexOf(text));
                }
            });


        }

        /**
         * 获取item个数
         * @return
         */
        @Override
        public int getItemCount() {
            return datas.size();
        }


    }
    /**
     * 继承系统提供的ViewHolder  用来给RecyclerView 进行布局的优化与重用
     */
    class MyweViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        /**
         * 通过构造方法, 将需要优化重用的子布局View对象, 传递到ViewHolder中 ,由RecyclerView自己进行优化的操作!
         * @param itemView
         */
        public MyweViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.fra_item_tv);
        }
    }
    //OnItemClickListener
    //onItemClick( View view, int position)

    /**
     * 用来注册观察者的回调
     */
    interface OnfaItemClickListener{
        void onfaItemtClick(View v,int position);
    }
}
