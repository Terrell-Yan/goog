package com.example.goog.activity;


import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.goog.R;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.base.BaseActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Register extends BaseActivity implements OnClickListener {
    private EditText username, password, topassword;
    private TextView ontain, button;
    private Handler handler;
    private String url = "http://192.168.1.251/regist.php/";
    private InputStream in;
    private StringBuffer sb;
    private String code = null;
    private ImageView inculde_back;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.activity_register_name);
        password = (EditText) findViewById(R.id.activity_register_password);
        topassword = (EditText) findViewById(R.id.activity_register_nextpass);
        ontain = tvByid(R.id.activity_register_obtain);
        button = tvByid(R.id.activity_register_rediserbutton);
        ontain.setOnClickListener(this);
        button.setOnClickListener(this);
        handler = new MyHandler();
        inculde_back = imgByid(R.id.inculde_back);
        inculde_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String name = username.getText().toString();
        String pass = password.getText().toString();

        switch (v.getId()) {

            case R.id.activity_register_obtain:
                CountDownTimer timer = new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        ontain.setText(millisUntilFinished / 1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        ontain.setClickable(true);
                        ontain.setText("00:00");
                        MediaPlayer mp = new MediaPlayer();
                        try {
                            mp.setDataSource(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                            mp.prepare();
                            mp.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.start();

                break;
            case R.id.activity_register_rediserbutton:

                ThreadOne threadOne = new ThreadOne();
                threadOne.start();
                break;
            case R.id.inculde_back:
                finish();
                break;
        }
    }

    class MyHandler extends Handler {


        @Override

        public void handleMessage(Message msg) {

            String s = (String) msg.obj;

            taost(s);

        }
    }

    class ThreadOne extends Thread {

        @Override
        public void run() {
            super.run();
            String text = "oi";

            String name = username.getText().toString();
            String pass = password.getText().toString();
            BmobUser bu = new BmobUser();
            bu.setUsername(name);
            bu.setPassword(pass);

//注意：不能用save方法进行注册
            bu.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser s, BmobException e) {
                    if (e == null) {
                        taost("注册成功:" + s.toString());
                    } else {
                        System.out.print("" + e);
                    }
                }
            });

            Message msg = handler.obtainMessage();

            msg.obj = text;

            handler.sendMessage(msg);
            System.out.println("thread :" + this.getName());
        }
    }

    private boolean gotoLogin(String userName, String password, String connectUrl) {
        String result = null;
        boolean isLoginSucceed = false;

        HttpPost httpRequest = new HttpPost(connectUrl);
        //Post运作传送变数必须用NameValuePair[]阵列储存
        ArrayList<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("name", userName));
        params.add(new BasicNameValuePair("pwd", password));
        try {
            HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            //发出HTTP请求
            httpRequest.setEntity(httpEntity);
            //发送post请求
            HttpClient httpClient = new DefaultHttpClient();
            //设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.
                    CONNECTION_TIMEOUT, 5000);

            //设置读取超时
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 5000);
            //取得HTTP response
            System.out.println("请求中请我去玩去----------------------------------------- :");
            System.out.println("请求数据中----------------------------------------- :");
            HttpResponse httpResponse = httpClient.execute(httpRequest);


            //通过流获取具体的内容
            in = httpResponse.getEntity().getContent();
            //创建缓冲区
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();

            System.out.println("请求数据中----------------------------------------- :" + httpResponse.getEntity());
//			result=new String( EntityUtils.toString(httpResponse.getEntity()).getBytes("ISO_8859_1"),HTTP.UTF_8);
//            result=EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
            System.out.println("result----------------------------------------- :" + result);
            if (result.equals("registsuccess")) {
                isLoginSucceed = true;
                System.out.println("isLoginSucceed----------------------------------------- :" + isLoginSucceed);
            }
//            System.out.println("请求数据中----------------------------------------- :"+EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
//            System.out.println("请求数据中----------------------------------------- :"+EntityUtils.toString(httpResponse.getEntity(), "ISO-8859-1"));
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("e----------------------------------------- :" + e);
        }


        //判断返回的数据是否为php中成功登入是输出的


        return isLoginSucceed;

    }
}
