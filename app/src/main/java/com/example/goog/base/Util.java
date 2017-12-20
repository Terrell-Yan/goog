package com.example.goog.base;

import android.widget.Toast;


import com.example.goog.activity.bean.Book;
import com.example.goog.activity.bean.Book_item;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by will on 2016/10/29.
 */

public class Util {
    private static Toast mToast;
    public static void makeToast(String message){
        if(mToast == null){
            mToast = Toast.makeText(BaseApp.getGlobalContext(),"", Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.show();
    }

    public static String getEncoding(Book_item book){
        UniversalDetector detector = new UniversalDetector(null);
        byte[] bytes = new byte[1024];
        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("//sdcard//人道至尊.txt")));
            int length;
            while ((length = bufferedInputStream.read(bytes)) > 0){
                detector.handleData(bytes,0,length);
            }
            detector.dataEnd();
            bufferedInputStream.close();
        }catch (FileNotFoundException f){
            f.printStackTrace();
        }catch (IOException i){
            i.printStackTrace();
        }
        return detector.getDetectedCharset();
    }
    public static int getPXWithDP(int dp){
        float density = BaseApp.getGlobalContext().getResources().getDisplayMetrics().density;
        return (int)(dp*density);
    }
}
