package com.example.goog.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.goog.base.BaseFragment;

/**
 * Created by SeanM on 2017/4/20.
 */

public class Fragment_interaction extends BaseFragment{
    public static final String ARG_PAGEG="ARG_PAGEG";
    @Override
    public View initLayout(LayoutInflater inflater) {
        return null;
    }
    public  static Fragment_interaction newInstace(int page){
        Bundle args =new Bundle();
        args.putInt(ARG_PAGEG,page);
        Fragment_interaction fa=new Fragment_interaction();
        fa.setArguments(args);
        return  fa;
    }
}
