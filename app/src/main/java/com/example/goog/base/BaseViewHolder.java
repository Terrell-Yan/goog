package com.example.goog.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;



/**
 * Created by SeanM on 2017/3/30.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected final SparseArray<View> mViews;
    protected View mConvertView;
    private OnReclItemClickListener onItemClickListener;


    public BaseViewHolder(View itemView) {
        super(itemView);
       this.mViews = new SparseArray<>();
        this.mConvertView = itemView;
        this.mConvertView .setOnClickListener(this);
//        mConvertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text =  holder.tv.getText().toString().trim();
//
//                listener.onItemtClick(v,datas.indexOf(text));
//            }
//        });
    }


    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    public interface OnReclItemClickListener {
        void onItemClick(View v, int position);
    }
    public View getmConvertView() {
        return mConvertView;
    }
    //添加点击事件
    public void setOnItemClickListener(OnReclItemClickListener ItemClickListener ) {
        Log.d("Log","onclick2");
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public void onClick(View v) {
        if(  onItemClickListener== null)
       onItemClickListener.onItemClick(v,getAdapterPosition());
    }



}

