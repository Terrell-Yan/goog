package com.example.goog.utils;

import android.os.AsyncTask;


import com.example.goog.activity.HttpGetListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by SeanM on 2017/3/15.
 */
public class HttpPostdata extends AsyncTask<String, Void, String> {

    private HttpClient mhttpclient;
    private HttpPost mhttpget;
    private HttpResponse mhttpResponse;
    private HttpEntity mHttpEntity;
    private InputStream in;
    private StringBuffer sb;




    //声明url变量
    private String url;
    //声明接口
    private HttpGetListener listener;
    private ArrayList<NameValuePair> params1;
    public HttpPostdata(){
    }

    public HttpPostdata(String url){
        this.url = url;
    }

    public HttpPostdata(String url,ArrayList<NameValuePair> params){
        this.url = url;
        this.params1 = params;
    }
    public HttpPostdata(String url,ArrayList<NameValuePair> params,HttpGetListener listener){
        this.url = url;
        this.listener = listener;
        this.params1=params;
    }

    /**
     * 写后台需要执行的程序
     */
    @Override
    protected String doInBackground(String... params) {

        try{
            //首先创建一个客户端实例
            mhttpclient = new DefaultHttpClient();
            mhttpclient.getParams().setParameter(CoreConnectionPNames.
                    CONNECTION_TIMEOUT, 5000);

            //设置读取超时
            mhttpclient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 5000);
            //设置传递的方法
            mhttpget = new HttpPost(url);
            //通过客户端进行发送
            mhttpResponse = mhttpclient.execute(mhttpget);
            //通过HttpResponse获取方法体
            mHttpEntity =  new UrlEncodedFormEntity(params1, HTTP.UTF_8);
            mhttpget.setEntity(mHttpEntity);
            //取得HTTP response
            HttpResponse httpResponse = mhttpclient.execute(mhttpget);
            //通过流获取具体的内容
            in = httpResponse.getEntity().getContent();
            //创建缓冲区
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();


        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub

        listener.GetDataUrl(result);



        super.onPostExecute(result);
    }

}