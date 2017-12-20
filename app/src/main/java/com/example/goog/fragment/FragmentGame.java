package com.example.goog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.GameStart;
import com.example.goog.base.BaseFragment;
import com.example.goog.listener.ScrollViewListener;
import com.example.goog.view.MyScrollView;

/**
 * Created by SeanM on 2017/4/13.
 */

public class FragmentGame extends BaseFragment  {
    public static final String ARG_PAGEG="ARG_PAGEG";
private TextView button;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.game,null);
        return view;
    }
    public  static FragmentGame newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGEG,page);
        FragmentGame fa=new FragmentGame();
        fa.setArguments(args);
        return  fa;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(TextView)view.findViewById(R.id.game_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), GameStart.class);
                startActivity(intent);
            }
        });
    }


}
