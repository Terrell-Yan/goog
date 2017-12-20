package com.example.goog.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;


public class BaseActivity extends FragmentActivity {
     private ProgressDialog dialog;
     public  BaseActivity act;
	 private TextView textView;
     @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
		 textView=(TextView)findViewById(R.id.in_tv);
    	act=this;
    }
     public  ProgressDialog dialogShow(boolean flag, CharSequence message, CharSequence title){
    	 if (dialog==null) {
			dialog=new ProgressDialog(this);
		}
    	 //�Ƿ�ȡ��
    	 dialog.show();
    	 dialog.setCancelable(flag);
    	 dialog.setMessage(message);
    	 dialog.setTitle(title);
    	 dialog.setIcon(R.drawable.ic_launcher);
		 return dialog;
    	 
     }
     public void dialogDismiss(){
    	 if (dialog==null &&dialog.isShowing()) {
    			dialog.dismiss();
		}
		
     }

     public ImageView imgByid(int id){
    	 return (ImageView)findViewById(id);
     }
     public TextView tvByid(int id){
    	 return (TextView)findViewById(id);
     }
     public Button butByid(int id){
    	 return (Button)findViewById(id);
     }
     public void taost(String text){
    	 Toast.makeText(this, text, Toast.LENGTH_LONG).show();
     }
}
