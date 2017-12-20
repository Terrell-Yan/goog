package com.example.goog.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Steep;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class StepActivity extends Activity {
	private Pedometer pedometer;
	private TextView tv,tv2;
	StringBuilder mBuilder = new StringBuilder();
	private TextView tvD;
	private ViewPropertyAnimator mStepEventAnim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.steep_move);
		pedometer = new Pedometer(this);
		tv=(TextView)findViewById(R.id.steep_move_tv);
		tvD=(TextView)findViewById(R.id.steep_move_tv2);

		TimerTask task = new TimerTask() {


			@Override
			public void run() {
				tv.post(new Runnable() {

					@Override
					public void run() {
						tv.setText(""+pedometer.getStepCount());
						tv.postInvalidate();
					}
				});

				tvD.post(new Runnable() {

					@Override
					public void run() {

						if (mStepEventAnim != null) {
							mStepEventAnim.cancel();
						}
						tvD.setText(""+pedometer.getmDetector());
						tvD.postInvalidate();
						tvD.setAlpha(1f);
						mStepEventAnim =tvD.animate().setDuration(500).alpha(0f);
					}
				});

				Steep gameScore = new Steep();
				gameScore.setSteep_num(""+pedometer.getStepCount());
				gameScore.update("cWn3999K", new UpdateListener() {

					@Override
					public void done(BmobException e) {
						if(e==null){
							Log.i("bmob","更新成功");
						}else{
							Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
						}
					}
				});

			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 100,1000);
	}

	@Override
	protected void onStart() {
		super.onStart(); 
		pedometer.register();
	}

	@Override
	protected void onStop() {
		super.onStop();
		pedometer.unRegister();
	}
}
