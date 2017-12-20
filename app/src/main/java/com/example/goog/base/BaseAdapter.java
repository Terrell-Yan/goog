package com.example.goog.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

import static com.example.goog.base.BaseHeadFootAdapter.TYPE_ITEM;

/**
 * Created by SeanM on 2017/3/30.
 */

public abstract class BaseAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {


    protected List<M> mLists;
    protected Context mContext;
    protected int layoutID;

//    private OnReclItemClickListener onItemClickListener;




    public BaseAdapter(List<M> mLists, Context mContext, int layoutID) {
        this.mLists = mLists;
        this.mContext = mContext;
        this.layoutID = layoutID;
    }

    public List<M> getmLists() {
        return mLists;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutID, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (TYPE_ITEM == getItemViewType(position)
                || super.getItemViewType(position) == getItemViewType(position)) {
            Log.e("TAG", "自定义的adapter的item设置点击事件");
            //当然这里也可以使用里面写的自定义接口的方法来实现，本人觉得
            //这样在使用的时候会更简洁，不需要设置adapter的监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPositionClick(position);
                }
            });

            onBindView(holder, position);
        }
    }
    protected abstract void onBindView(BaseViewHolder holder, int position);
    /**
     *itemview 的点击事件（抽象方法）
     * @param
     */
    protected abstract void setPositionClick(int position);

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }


}





