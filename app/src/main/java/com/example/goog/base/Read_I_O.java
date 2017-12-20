package com.example.goog.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeanM on 2017/4/12.
 */

public class Read_I_O {
    List<String> textList;
    public static List<String> readTxtFile(){
        List<String> re=new ArrayList<String>();
        String name = "black_keywords.txt";
        try {
            String encoding="UTF-8";
            FileInputStream fis = new FileInputStream("/sdcard/人道至尊.txt");
//            InputStream in = BaseApp.getInstance().getApplicationContext().getAssets().open(name);//该文件位置为assets下
            //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    fis,encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){

                re.add(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return re;
    }
    public List<String> getTextList() {
        if (textList==null) {
            textList=readTxtFile();
        }
        return textList;
    }
}

