//package com.example.goog.activity.addpter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.goog.R;
//import com.example.goog.intenrfacae.OnReclItemClickListener;
//
///**
// * Created by SeanM on 2017/3/30.
// */
//
//public class ReclAdapter extends RecyclerView.Adapter<MyViewHolder>{
//    //用来注册观察者的位置
//    private OnReclItemClickListener listener;
//    public void setOnItemCickListener( OnReclItemClickListener listener){
//        this.listener = listener;
//    }
//    /**
//     *创建ViewHolder , 此方法只有当第一页item被创建时 会执行!
//     * @param parent 参数1. RecyclerView对象
//     * @param viewType 当前的View类型!
//     * @return
//     */
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        /**
//         * 参数1. 布局id
//         * 参数2. 根节点
//         * 参数3. 是否绑定这个根节点
//         */
//        View view = getLayoutInflater().inflate(R.layout.item, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//        return holder;
//    }
//    /**
//     * 根据position 系统自动传入应优化的包含item的holder对象!
//     * 一般我们在此方法中 将对应position的数据, 设置到holder中的item上
//     * @param holder
//     * @param position
//     */
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        /**
//         * 获取View的参数配置对象
//         */
//        ViewGroup.LayoutParams para = holder.tv.getLayoutParams();
//        if(optionId==4) {
//            para.height = heights.get(position);
//        }else if(optionId==5){
//            para.width = heights.get(position);
//        }else{
//            para.width = 100;
//            para.height = 100;
//        }
//
//        holder.tv.setLayoutParams(para);
//        holder.tv.setText(datas.get(position));
//
//        /**
//         * 监听每一个Item的点击
//         * 如果点击,  通知观察者, 并且传入被点击的View对象,与item下标
//         */
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text =  holder.tv.getText().toString().trim();
//
//                listener.onItemtClick(v,datas.indexOf(text));
//            }
//        });
//
//
//    }
//
//    /**
//     * 获取item个数
//     * @return
//     */
//    @Override
//    public int getItemCount() {
//        return datas.size();
//    }
//    /**
//     * 继承系统提供的ViewHolder  用来给RecyclerView 进行布局的优化与重用
//     */
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv;
//
//        /**
//         * 通过构造方法, 将需要优化重用的子布局View对象, 传递到ViewHolder中 ,由RecyclerView自己进行优化的操作!
//         *
//         * @param itemView
//         */
//        public MyViewHolder(View itemView) {
//            super(itemView);
////            tv= (TextView) itemView.findViewById(R.id.item_tv);item_tv
//        }
//    }
//}
