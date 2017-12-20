package com.example.goog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.NetUtils;


public class Initacvity extends BaseActivity {
	private ImageView iv;
	private Animation anim;
	private boolean isnet=false ;
    private TextView tv,tv2;
private boolean flag=true;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_init);
		iv = imgByid(R.id.activity_init_img);
		tv=tvByid(R.id.activity_init_text);
		tv2=tvByid(R.id.init_lin);
		inidata();
		inistart();

		tv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent =new Intent(Initacvity.this,MainActivity.class);
//				startActivity(intent);
//				Initacvity.this.finish();
				flag=false;

			}
		});
		if (flag==true){
			ininText();
		}

	}

	private void inistart() {
		// TODO Auto-generated method stub
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
                  
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				 isnet= NetUtils.getNetstae(act);
				if (!isnet) {

				}else{

				}
			}
		});
		iv.startAnimation(anim);
	}

	private void inidata() {
		// TODO Auto-generated method stub
		anim = AnimationUtils.loadAnimation(this, R.anim.init_aplha);
		anim.setDuration(7000);
	}
	private void ininText() {
		// TODO Auto-generated method stub
		CountDownTimer timer = new CountDownTimer(5000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				tv.setText(millisUntilFinished / 1000 + "s");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Intent intent =new Intent(Initacvity.this,MainActivity.class);
				startActivity(intent);
				Initacvity.this.finish();
			}
		};
		timer.start();
	}
}
