package com.example.goog.activity;

import android.os.Bundle;

import com.example.goog.R;
import com.example.goog.base.BaseActivity;
import com.example.goog.view.GamePintuLayout;

/**
 * Created by SeanM on 2017/4/13.
 */

public class Game_show extends BaseActivity{
    GamePintuLayout gamePintuLayout;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.game_show);
        gamePintuLayout=(GamePintuLayout)findViewById(R.id.game_show_gl);
    }
}
