package com.example.goog.activity.addpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.goog.R;

import java.util.ArrayList;


/**
 * Created by SeanM on 2017/3/7.
 */
public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String>arrayList;
    private LayoutInflater inflater;
    public ItemAdapter(Context context, ArrayList<String>arrayList) {
        super();
        this.context=context;
        this.arrayList=arrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }



    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         view = inflater.inflate(R.layout.gridview_item, null);
        TextView image_photo2 = (TextView) view.findViewById(R.id.gridview_img);
        image_photo2.setText(arrayList.get(i));
        return view;
    }
}
