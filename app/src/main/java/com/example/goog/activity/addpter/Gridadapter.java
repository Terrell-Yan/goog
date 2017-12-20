package com.example.goog.activity.addpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.base.Imagload;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by SeanM on 2017/3/23.
 */
public class Gridadapter extends BaseAdapter {
     private String  Tag="Log";
    private Context context;
    private List<String> listitem;
    private DisplayImageOptions imageOptions;
    private LayoutInflater layoutinflater;

    public Gridadapter(Context context, List<String> listitem) {
        this.context = context;
        this.listitem = listitem;
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        this.layoutinflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.gridview_item, null);
            viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        if (!"".equals(listitem.get(position))) {
            Log.d(Tag,listitem.get(position));
            System.out.print("数据"+listitem.get(position));

        } else {
            System.out.print("" + listitem.get(position));

        }
        return convertView;
    }
    class ViewHolder {
        public ImageView imageView;
    }


}
