package com.example.goog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.goog.R;
import com.example.goog.activity.MusicAct;
import com.example.goog.activity.MusicList;
import com.example.goog.base.BaseFragment;
import com.example.goog.view.RippleView;

/**
 * Created by SeanM on 2017/4/7.
 */

public class FragmentMusic extends BaseFragment {
    private RippleView rippleView,rippleView2,rippleView3,rippleView4;
    private ImageView iv1,iv2,iv3,iv4;
    @Override
    public View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.music, null);
        rippleView=(RippleView)view.findViewById(R.id.musi_ri);
        rippleView2=(RippleView)view.findViewById(R.id.musi_ri2);
        rippleView3=(RippleView)view.findViewById(R.id.musi_ri3);
        rippleView4=(RippleView)view.findViewById(R.id.musi_ri4);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent =new Intent(getActivity(), MusicList.class);
                intent.putExtra("music","1");
                startActivity(intent);
            }
        });
        rippleView2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent =new Intent(getActivity(), MusicList.class);
                intent.putExtra("music","2");
                startActivity(intent);
            }
        });
        rippleView3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent =new Intent(getActivity(), MusicList.class);
                intent.putExtra("music","3");
                startActivity(intent);
            }
        });
        rippleView4.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent =new Intent(getActivity(), MusicList.class);
                intent.putExtra("music","4");
                startActivity(intent);
            }
        });
    }
}
