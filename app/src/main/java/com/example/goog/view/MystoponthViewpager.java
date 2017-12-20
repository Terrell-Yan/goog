package com.example.goog.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by SeanM on 2017/3/29.
 */

public class MystoponthViewpager extends ViewPager {
    private boolean isScrollable = true;
    public MystoponthViewpager(Context context) {
        super(context);
    }
    public MystoponthViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */

            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

            return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
