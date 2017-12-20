package com.example.goog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.Show;
import com.example.goog.activity.addpter.Fragment_main_Adapter;
import com.example.goog.base.BaseFragment;
import com.example.goog.base.BaseViewHolder;

/**
 * Created by SeanM on 2017/3/29.
 */

public class Fragment_main extends BaseFragment{
    private TextView in_tv;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Fragment_main_Adapter myadapter;



    @Override
    public View initLayout(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.tab_img,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        viewPager=(ViewPager)view.findViewById(R.id.viewpager);

        myadapter=new Fragment_main_Adapter(getChildFragmentManager(),getContext());
        //给viewpager设置适配器
        viewPager.setAdapter(myadapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
}
