package com.example.goog.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.goog.R;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.BaseFragment;

/**
 * Created by SeanM on 2017/4/5.
 */

public class EditActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout iv1,iv2,iv3,iv4,iv5;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.edit_ani);
        iv1=(LinearLayout) findViewById(R.id.edit_iv1);
        iv2=(LinearLayout) findViewById(R.id.edit_iv2);
        iv3=(LinearLayout) findViewById(R.id.edit_iv3);
        iv4=(LinearLayout) findViewById(R.id.edit_iv4);
        iv5=(LinearLayout) findViewById(R.id.edit_iv5);
//        //上下摇摆
//        TranslateAnimation alphaAnimation2 = new TranslateAnimation(50f, 50F,50F, 120F);  //同一个x轴 (开始结束都是50f,所以x轴保存不变)  y轴开始点50f  y轴结束点80f
//        alphaAnimation2.setDuration(5000);  //设置时间
//        alphaAnimation2.setRepeatCount(Animation.INFINITE);  //为重复执行的次数。如果设置为n，则动画将执行n+1次。INFINITE为无限制播放
//        alphaAnimation2.setRepeatMode(Animation.REVERSE);  //为动画效果的重复模式，常用的取值如下。RESTART：重新从头开始执行。REVERSE：反方向执行
//        // AccelerateDecelerateInterpolator 在动画开始与介绍的地方速率改变比较慢，在中间的时候加速
//        // AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
//        // AnticipateInterpolator 开始的时候向后然后向前甩
//        // AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
//        // BounceInterpolator   动画结束的时候弹起
//        // CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
//        // DecelerateInterpolator 在动画开始的地方快然后慢
//        // LinearInterpolator   以常量速率改变
//        // OvershootInterpolator    向前甩一定值后再回到原来位置
//        //上面那些效果可以自已尝试下
//        alphaAnimation2.setInterpolator(new AccelerateInterpolator());
//        alphaAnimation2.setInterpolator(new AnticipateOvershootInterpolator());
//        alphaAnimation2.setInterpolator(new BounceInterpolator());//动画结束的时候弹起
//        iv1.setAnimation(alphaAnimation2);
//        iv2.setAnimation(alphaAnimation2);
//        iv3.setAnimation(alphaAnimation2);
//        iv4.setAnimation(alphaAnimation2);
//        iv5.setAnimation(alphaAnimation2);
//        alphaAnimation2.start();
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_iv1:
                break;
            case R.id.edit_iv2:
                Intent intent=new Intent(act,EditshowActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_iv3:
                break;
            case R.id.edit_iv4:
                break;
            case R.id.edit_iv5:
                break;
        }
    }
}
