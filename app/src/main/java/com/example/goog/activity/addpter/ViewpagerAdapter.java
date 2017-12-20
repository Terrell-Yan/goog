package com.example.goog.activity.addpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SeanM on 2017/3/29.
 */

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>arrayList;
    public  ViewpagerAdapter(FragmentManager fm,ArrayList<Fragment> arrayList){
        super(fm);
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }
}
