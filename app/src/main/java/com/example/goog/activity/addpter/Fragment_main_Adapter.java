package com.example.goog.activity.addpter;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.goog.fragment.Fragemnet;
import com.example.goog.fragment.FragmentBook;
import com.example.goog.fragment.FragmentGame;
import com.example.goog.fragment.FragmentNews;
import com.example.goog.fragment.FragmentSteep;
import com.example.goog.fragment.Fragment_Me;
import com.example.goog.fragment.VdoFragment;

/**
 * Created by SeanM on 2017/3/29.
 */

public class Fragment_main_Adapter extends FragmentPagerAdapter {
    int PAGR_COUNT = 7;
    private Context context;
    private String tabtitles[] = {"推荐", "课程", "动态","书籍" , "学习", "饮食", "记步"};

    public Fragment_main_Adapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("Log", position + "postion");
        if (position == 0) {
            return Fragment_Me.newInstace(position + 1);

        } else if(position==1)  {
            return VdoFragment.newInstace(position + 1);
        }else if (position==2){
            return Fragrment.newInstace(position + 1);
        }
        else if (position==3){
            return FragmentBook.newInstace(position+1);
        }
        else if(position==4){
            return FragmentGame.newInstace(position+1);
        }else if(position==5){
            return FragmentNews.newInstace(position+1);
        }else {
            return FragmentSteep.newInstace(position+1);
        }

    }

    @Override
    public int getCount() {
        return PAGR_COUNT;
    }

    //设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
