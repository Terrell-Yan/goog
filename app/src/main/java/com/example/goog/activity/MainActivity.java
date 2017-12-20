package com.example.goog.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.addpter.ViewpagerAdapter;
import com.example.goog.activity.bean.Book_item;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.adbanner.BaseWebActivity;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseViewHolder;
import com.example.goog.fragment.Fragemnet;
import com.example.goog.fragment.FragmentMusic;
import com.example.goog.fragment.Fragment_Me;
import com.example.goog.fragment.Fragment_edit;
import com.example.goog.fragment.Fragment_main;
import com.example.goog.fragment.Fragment_sister;
import com.example.goog.fragment.Fragment_weather;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by SeanM on 2017/3/29.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, BaseViewHolder.OnReclItemClickListener {
    private LinearLayout lin1, lin2, lin3, lin4, lin5;
    private ViewPager viewPager;
    private ArrayList<Fragment> arrayList;
    private ViewpagerAdapter adapter;

    public class RecHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = (int) msg.obj;
            if (i == 0) {
                Intent intent = new Intent(MainActivity.this, Show.class);
                startActivity(intent);
            }
        }
    }

    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    //首页recycle的点击事件
    public void gotoDownloadFragment(String url, String name, String img2) {    //去下载页面Fe_item_listen
        Intent intent = new Intent(MainActivity.this, Fe_item_listen.class);
        intent.putExtra("Fe_item_listen_img_url", url);
        intent.putExtra("Fe_item_listen_img_name", name);
        intent.putExtra("Fe_item_listen_img_img", img2);
        startActivity(intent);

    }

    /**
     * 阅读界面
     *
     * @param name
     * @param url
     */
    public void gotoBook(String name, String url) {    //去下载页面
        Intent intent = new Intent(MainActivity.this, BookRead.class);
        intent.putExtra("book_url", url);
        intent.putExtra("book_name", name);

        startActivity(intent);
    }

    /**
     * 网页展示
     *
     * @param url
     * @param title
     */
    public void setWeb(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        Intent intent = new Intent(MainActivity.this,
                BaseWebActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 视频展示
     *
     * @param data
     * @param name
     */
    public void goFragment(String data, String name, String url) {
        Intent intent = new Intent(MainActivity.this, Vidoshow.class);
        intent.putExtra("vido_url", data);
        intent.putExtra("vido_name", name);
        intent.putExtra("vido_iv",url);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.main_face);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user == null) {
            taost("亲，您还没有登录，登录后有惊喜哦");
        }
        lin1 = (LinearLayout) findViewById(R.id.main_face_lin);
        lin2 = (LinearLayout) findViewById(R.id.main_face_lin2);
        lin3 = (LinearLayout) findViewById(R.id.main_face_lin3);
        lin4 = (LinearLayout) findViewById(R.id.main_face_lin4);
        lin5 = (LinearLayout) findViewById(R.id.main_face_lin5);
        viewPager = (ViewPager) findViewById(R.id.main_face_view);
        arrayList = new ArrayList<>();
        lin1.setOnClickListener(this);
        lin2.setOnClickListener(this);
        lin3.setOnClickListener(this);
        lin4.setOnClickListener(this);
        lin5.setOnClickListener(this);
        //添加主界面四个碎片
        arrayList.add(new Fragment_main());
        arrayList.add(new Fragment_sister());
        arrayList.add(new Fragment_edit());
        arrayList.add(new FragmentMusic());
        //绑定
        viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(), arrayList));
        viewPager.setCurrentItem(0);
        TextView in_tv = (TextView) findViewById(R.id.in_tv);
        SpannableStringBuilder sb = new SpannableStringBuilder("Gril Motion"); // 包装字体内容
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.white)); // 设置字体颜色
        StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // 设置字体样式
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(60);  // 设置字体大小
        sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(ass, 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        in_tv.setText(sb);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(act, Show.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_face_lin:
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_face_lin2:
                viewPager.setCurrentItem(1);

                break;
            case R.id.main_face_lin3:
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                if (user == null) {
                    taost("亲，您还没有登录，请登录后操作");
                } else {
                    Intent intent = new Intent(act, EditActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.main_face_lin4:
                viewPager.setCurrentItem(2);
                break;
            case R.id.main_face_lin5:
                viewPager.setCurrentItem(3);
                break;
        }
    }
}
