package com.example.goog.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

/**
 * Created by SeanM on 2017/3/30.
 */

public abstract class BaseHeadFootAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {


    protected List<M> mLists;
    protected Context mContext;
    protected int layoutID;
    private boolean isHasHeader = false;
    private boolean isHasFooter = false;

    private int headerLayoutID;
    private int footerLayoutID;


    protected static final int TYPE_HEADER = -2;
    protected static final int TYPE_ITEM = -1;
    protected static final int TYPE_FOOTER = -3;

    public BaseHeadFootAdapter(List<M> mLists, Context mContext, int layoutID) {
        this.mLists = mLists;
        this.mContext = mContext;
        this.layoutID = layoutID;
    }

    public BaseHeadFootAdapter(int layoutID, Context mContext, List<M> mLists, int headerLayoutID, int footerLayoutID) {
        this.layoutID = layoutID;
        this.mContext = mContext;
        this.mLists = mLists;
        addHeader(headerLayoutID);
        addFooter(footerLayoutID);
    }
//    public void setOnItemClickListener(OnReclItemClickListener ItemClickListener ) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    private OnReclItemClickListener onItemClickListener;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        if (viewType == TYPE_HEADER) {
            if (headerLayoutID == 0) {
                throw new NullPointerException("header 资源ID尚未初始化");
            } else {
                holder = new BaseViewHolder(LayoutInflater.from(mContext).inflate(headerLayoutID, parent, false));
            }
        } else if (viewType == TYPE_FOOTER) {
            if (footerLayoutID == 0) {
                throw new NullPointerException("footer 资源ID尚未初始化");
            } else {
                holder = new BaseViewHolder(LayoutInflater.from(mContext).inflate(footerLayoutID, parent, false));
            }
        } else {
            holder = new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutID, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (isHasFooter) {
            if (isFooterPosition(position)) {
                onBindFooterView(holder, position);
                return;
            }
        }
        if (isHasHeader) {
            if (isHeaderPosition(position)) {
                onBindHeaderView(holder, position);
                return;
            } else {
                onBindView(holder, position - 1);
            }
        } else {
            onBindView(holder, position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPositionClick(position);
            }
        });
    }
    /**
     *itemview 的点击事件（抽象方法）
     * @param
     */
    protected abstract void setPositionClick(int position);
    protected abstract void onBindHeaderView(BaseViewHolder holder, int position);

    protected abstract void onBindFooterView(BaseViewHolder holder, int position);

    protected abstract void onBindView(BaseViewHolder holder, int position);

    public void addHeader(@LayoutRes int headLayoutID) {
        if (headLayoutID == 0)
            return;
        this.headerLayoutID = headLayoutID;
        isHasHeader = true;
    }

    public List<M> getmLists() {
        return mLists;
    }

    public void addFooter(@LayoutRes int footLayoutID) {
        if (footLayoutID == 0)
            return;
        this.footerLayoutID = footLayoutID;
        isHasFooter = true;
    }


    @Override
    public int getItemViewType(int position) {

        int viewType = TYPE_ITEM;
        if (isHasHeader) {
            if (isHeaderPosition(position)) {
                viewType = TYPE_HEADER;
            }
        }
        if (isHasFooter) {
            if (isFooterPosition(position)) {
                viewType = TYPE_FOOTER;
            }
        }
        return viewType;
    }


    protected boolean isFooterPosition(int position) {
        return position == getItemCount() - 1 ? true : false;

    }

    protected boolean isHeaderPosition(int position) {
        return position == 0 ? true : false;
    }

    @Override
    public int getItemCount() {
        int count = mLists == null ? 0 : mLists.size();
        if (isHasFooter) {
            count++;
        }
        if (isHasHeader) {
            count++;
        }
        return count;
    }


}
