package com.example.goog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ScrollingView;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.addpter.ScanViewAdapter;
import com.example.goog.activity.bean.Book;
import com.example.goog.activity.bean.Book_item;
import com.example.goog.activity.bean.Book_read;
import com.example.goog.base.BaseActivity;
import com.example.goog.view.PageFactory;
import com.example.goog.view.PageView;
import com.example.goog.view.ScanView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SeanM on 2017/4/11.
 */

public class BookRead extends BaseActivity {
    //        private PageView pageView;
//    private boolean isAnimating;
    private PageFactory mPageFactory;
    private Handler handler;
    StringBuffer buffer;
    private TextView textView;
    private ScrollView scrollingView;
    BookRead interRead;
    private String url;
    private String result;
    private String newstr;
    ScanView scanview;
    ScanViewAdapter adapter;
    List<String> items;
    List<Book_read> items2;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        interRead=this;
        setContentView(R.layout.book_read);
        handler = new MyHandler();
        scrollingView = (ScrollView) findViewById(R.id.book_read_sv);
//        pageView = (PageView) findViewById(R.id.book_pv);
        textView = tvByid(R.id.book_text);
        Intent intent = getIntent();
        String name = intent.getStringExtra("book_name");
        url = intent.getStringExtra("book_url");
        Log.d("Log", "==================" + url);
        scanview = (ScanView) findViewById(R.id.scanview);
        items = new ArrayList<String>();
        items2 = new ArrayList<>();


        Therdone therdone = new Therdone();
        therdone.start();
        adapter = new ScanViewAdapter(this, items);
        scanview.setAdapter(adapter);
//        String a = interRead.opennerbook(url);
//        textView.setText(a);
//        Log.d("Log","a=================="+a);
//        opennerbook(url);
//        Book_item book=new Book_item();
//        book.book_name=name;
//        book.book_url=url;
//        Log.d("Log", book.book_name+"book------------"+book.book_url);
//        mPageFactory = PageFactory.getInstance(pageView);
//        mPageFactory.nextPage();
//        pageView.setOnClickCallback(new PageView.OnClickCallback() {
//            @Override
//            public void onLeftClick() {
//
//                    mPageFactory.prePage();
//
//            }
//
//            @Override
//            public void onMiddleClick() {
//
//            }
//
//            @Override
//            public void onRightClick() {
//
//                    mPageFactory.nextPage();
//
//            }
//        });
    }


    /**
     * 打开网络书籍
     *
     * @param strFilePath
     * @return
     */
    public void opennerbook(String strFilePath) {
        InputStream istream = null;
        URL url = null;

        HttpURLConnection httpConn = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(strFilePath);
            httpConn = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows 2000)");
            istream = httpConn.getInputStream();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(istream, "GB2312"));
            String data = "";
            String data2 = "";
            final String RE = "([第].{1,9}[章])(.+)";
            int count = 0;
            Book_read book_read = new Book_read();
            while ((data = br.readLine()) != null) {
                sb.append(data);
                count++;
                data2 = data2 + data.trim();
                if (count < 4) {
                    if ((count % 3 == 0))
                        items.add(data2);
                    data2 = "";
                } else {
                    if (count % 20 == 0) {
                        items.add(data2);
                        data2 = "";
                    }
                }

//             items.add(data.trim());

            }
//            initView();
            result = sb.toString();
            newstr = result.replace("document.write('", "");
            newstr = newstr.replace("');", "");
//            newstr = newstr.replace("<a href="+"http://www.qidian.com" mce_href="http://www.qidian.com">起点中文网 www.qidian.com 欢迎广大书友光临阅读，最新、最快、最火的连, "");
            newstr = newstr.replace("求推荐票！", "");
            newstr = newstr.replace("新书上传，<p>", "");
            newstr = newstr.replace("—————", "");

            Log.d("Log", result);
            //wv.loadDataWithBaseURL("http://www.baidu.com", "<mce:script src="http://files.qidian.com/Author4/1680011/28846222.txt" mce_src="http://files.qidian.com/Author4/1680011/28846222.txt"></mce:script>", mimeType, encoding, "");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.d("Log", e + "2eeee=====");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("Log", e + "eeee=====");
            e.printStackTrace();
        }

    }

    ;

    private void initView() {

    }

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            opennerbook(url);
//            Log.d("Log","text"+text);
//            Message msg = handler.obtainMessage();
//
//            msg.obj = opennerbook(text);
//
//            handler.sendMessage(msg);
        }
    }

    class MyHandler extends Handler {


        @Override

        public void handleMessage(Message msg) {

            String s = (String) msg.obj;

//            textView.setText(s);
            Log.d("Log", "s" + s);
        }
    }
}