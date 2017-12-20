package com.example.goog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.addpter.Gridadapter;
import com.example.goog.activity.addpter.MyAdapter;
import com.example.goog.activity.bean.Getimg;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.Grigu;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.BaseAdapter;
import com.example.goog.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/3/23.
 */
public class Show extends BaseActivity {
    private ListView gridView;
    private List<Getimg> datas;
    private Gridadapter gridadapter;
    private MyAdapter myAdapter;
    private   ArrayList<String> listdat;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.code_time);
        recyclerView = (RecyclerView) findViewById(R.id.four_recl);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        listdat = new ArrayList<>();
        listdat.add("1");
        listdat.add("2");
        listdat.add("3");
        listdat.add("4");

   BaseAdapter<String> adapter=  new BaseAdapter<String>(listdat, act, R.layout.recl_item) {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {


                TextView mTitle = holder.getView(R.id.recl_item_tv);

                mTitle.setText(listdat.get(position));

            }

            @Override
            protected void setPositionClick(int position) {
                Intent intent=new Intent(act,MainActivity.class);
                startActivity(intent);









            }

        };
        recyclerView.setAdapter(adapter);





    }
}
