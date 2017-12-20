package com.example.goog.activity.diolag;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;

public class ProgersssDialog extends Dialog {
    private Context context;
    private ImageView img;
    private TextView txt;
        
    public ProgersssDialog(Context context) {
            super(context, R.style.progress_dialog);
            this.context=context;
            //���ز����ļ�
           LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View  view=inflater.inflate(R.layout.loading_lay, null);
            img=(ImageView) view.findViewById(R.id.progress_dialog_img);
            txt=(TextView) view.findViewById(R.id.progress_dialog_txt);
            //��ͼƬ��Ӷ�̬Ч��
            Animation anim=AnimationUtils.loadAnimation(context, R.anim.loading_dialog_progressbar);
            img.setAnimation(anim);

            //dialog�����ͼ
           setContentView(view);
           show();
//           dismiss();
    }

    public void setMsg(String msg){
            txt.setText(msg);
    }
    public void setMsg(int msgId){
            txt.setText(msgId);
    }

}