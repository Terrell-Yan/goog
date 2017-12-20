package com.example.goog.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.goog.R;
import com.example.goog.base.BaseActivity;
import com.example.goog.listener.ScrollViewListener;
import com.example.goog.view.MyScrollView;

/**
 * Created by SeanM on 2017/4/13.
 */

public class GameStart extends BaseActivity implements ScrollViewListener {
    MyScrollView myscrollview;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.start_game);
        myscrollview=(MyScrollView)findViewById(R.id.game_sv);
        myscrollview.setOnclick(this);
    }
    @Override
    public boolean myOnclick(int select) {
        Intent intent=new Intent(act,Game_show.class);
        startActivity(intent);
        return false;
    }
}
