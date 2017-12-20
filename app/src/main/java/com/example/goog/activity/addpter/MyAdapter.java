package com.example.goog.activity.addpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Myimg;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by SeanM on 2017/3/29.
 */

public class MyAdapter extends BaseAdapter {

    private List<Myimg> stuList;
    private LayoutInflater inflater;
    private DisplayImageOptions imageOptions;
    public MyAdapter() {
    }

    public MyAdapter(List<Myimg> stuList, Context context) {
        this.stuList = stuList;
        this.inflater = LayoutInflater.from(context);
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();

    }

    @Override
    public int getCount() {
        return stuList.size();
    }

    @Override
    public Myimg getItem(int position) {
        return stuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.gridview_item, null);
        Myimg myimg = stuList.get(position);
        //在view视图中查找id为image_photo的控件
        ImageView image_photo = (ImageView) view.findViewById(R.id.gridview_img);
        TextView image_photo2 = (TextView) view.findViewById(R.id.gridview_tv);
//        image_photo.setImageResource(R.drawable.girl);
        ImageLoader.getInstance().displayImage( myimg.img, image_photo, imageOptions);
        image_photo2.setText(myimg.name);
        return view;
    }
}