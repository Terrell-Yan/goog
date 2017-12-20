package com.example.goog.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;



import com.example.goog.R;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.base.BaseActivity;
import com.example.goog.base.MyAppication;
import com.example.goog.utils.HttpPostdata;
import com.example.goog.utils.SPUtils;

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
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginAcivity extends BaseActivity implements OnClickListener, HttpGetListener {
    private EditText username, password;
    private TextView reges;
    private Button login;
    private Handler handler;
    private String url = "http://192.168.1.251/ceshi.php/";
    private InputStream in;
    private StringBuffer sb;
    private HttpPostdata httpPostdata;
    //先定义
    private SharedPreferences sp ;

    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        checkBox=(CheckBox)findViewById(R.id.checkbox2);
        username = (EditText) findViewById(R.id.activity_longin_pass);
        password = (EditText) findViewById(R.id.activity_longin_name);
        reges = tvByid(R.id.activity_longin_res);
        login = butByid(R.id.activity_longin_buttom);

        reges.setOnClickListener(this);
        login.setOnClickListener(this);
        handler = new MyHandler();

//对uname 和 upswd 的操作
        System.out.println("请求中请我去玩去1111----------------------------------------- :"+sp.getBoolean("checkboxBoolean", false));
        System.out.println("请求中请我去玩去222----------------------------------------- :" + sp.getString("name", null));
        System.out.println("请求中请我去玩去3333----------------------------------------- :"+sp.getString("pass", null));
        if (sp.getBoolean("ISCHECK", false))
        {
            System.out.println("请求中请我去玩去222----------------------------------------- :"+sp.getString("name", null));
            System.out.println("请求中请我去玩去3333----------------------------------------- :"+sp.getString("pass", null));
            username.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            checkBox.setChecked(true);

        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (checkBox.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("PASSWORD", false).commit();

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.activity_longin_res:
                Intent intent = new Intent(LoginAcivity.this, Register.class);
                startActivity(intent);

                break;
            case R.id.activity_longin_buttom:
                String name = username.getText().toString();
                String pass = password.getText().toString();
                System.out.println("请求中请我去玩去222name----------------------------------------- :"+name);
                boolean CheckBoxlogin=checkBox.isChecked();
                if(checkBox.isChecked())
                {
                    //记住用户名、密码、
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", name);
                    editor.putString("PASSWORD",pass);
                    editor.commit();
                }


//                startActivity(intent2);
                ThreadOne threadOne = new ThreadOne();
                threadOne.start();
//			String name = username.getText().toString();
//			String pass = password.getText().toString();
//			ArrayList<NameValuePair> params = new ArrayList();
//			params.add(new BasicNameValuePair("name", name));
//			params.add(new BasicNameValuePair("pwd", pass));
//			httpPostdata=(HttpPostdata)new HttpPostdata(url,params,this).execute();
                break;
            default:
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
            String text = "提示：";

            String name = username.getText().toString();
            String pass = password.getText().toString();
            BmobUser bu2 = new BmobUser();
            bu2.setUsername(name);
            bu2.setPassword(pass);
            bu2.login(new SaveListener<BmobUser>() {

                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if(e==null){
                        MyUser user=BmobUser.getCurrentUser(MyUser.class);
                        String name=(String) user.getObjectByKey("username");
                        String avatar=(String) user.getObjectByKey("avatarimg");
//                        Log.d("Log",name);
//                        SPUtils.put(act,"avatar",avatar);
//                        SPUtils.put(act,"user_name_key",name);
//                        MyAppication.put("avatar",avatar);
//                        MyAppication.put("user_name_key",name);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("avatar", avatar);
                        editor.putString("user_name_key",name);
                        taost("登录成功:");
//                        finish();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                    }else{

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
                    CONNECTION_TIMEOUT, 10000);

            //设置读取超时
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 10000);
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
            if (result.equals("success")) {
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

    @Override
    public void GetDataUrl(String data) {


//        tv.setText(data);


    }



}
