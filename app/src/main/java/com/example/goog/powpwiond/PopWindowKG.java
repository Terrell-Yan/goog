package com.example.goog.powpwiond;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.goog.R;
import com.example.goog.activity.bean.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class PopWindowKG extends PopupWindow{

	private Context context;
	private LayoutInflater inflater;
	private Handler handler;
	private View view;
	private Button btqueding,btquxiao;
	private LinearLayout llnull;
	private EditText etText;
   public PopWindowKG(Context context,Handler handler){
	   this.context = context;
	   this.handler = handler;
	   inflater = LayoutInflater.from(context);
	   view = inflater.inflate(R.layout.popwindow_kg, null);
	   initView();
	   this.setWidth(LayoutParams.MATCH_PARENT);
	   this.setHeight(LayoutParams.WRAP_CONTENT);
	   this.setBackgroundDrawable(new BitmapDrawable());
	 //设置SelectPicPopupWindow弹出窗体动画效果
       this.setAnimationStyle(R.style.AppTheme);
	   this.setFocusable(true);
	   this.setOutsideTouchable(true);
	   this.setContentView(view);
   }
   public void initView(){
	   RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.kg_popwindow);
		btqueding=(Button) view.findViewById(R.id.popwindow_kg_queding);
		btquxiao=(Button) view.findViewById(R.id.popwindow_kg_quxiao);
		etText=(EditText) view.findViewById(R.id.popwindow_kg);
		llnull=(LinearLayout) view.findViewById(R.id.ll_null);
		llnull.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PopWindowKG.this.dismiss();
			}
		});
		btquxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PopWindowKG.this.dismiss();
			}
		});
		btqueding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message message=new Message();
				Bundle bundle=new Bundle();
				bundle.putString("kg", etText.getText().toString());
				message.setData(bundle);
				message.what=404;
				handler.sendMessage(message);
				MyUser bmobUser = MyUser.getCurrentUser(MyUser.class);
// 修改用户的邮箱为xxx@163.com
				bmobUser.setNickName(etText.getText().toString());
				bmobUser.update(new UpdateListener() {
					@Override
					public void done(BmobException e) {
						if(e==null){
							PopWindowKG.this.dismiss();
						}else{

						}
					}
				});
			}
		});
   }
   /**
    * 从底部展示
    * @param parent
    */
   public void showPopupWindowBottom(View parent) {  
       if (!this.isShowing()) {  
       	 this.showAtLocation(
       			 parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);  
       } else {  
           this.dismiss();  
       }  
   }
	
}

