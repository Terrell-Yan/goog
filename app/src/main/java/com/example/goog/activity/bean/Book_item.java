package com.example.goog.activity.bean;

/**
 * Created by SeanM on 2017/4/11.
 */

public class Book_item {
    public String book_name;
    public String book_img;
    public String book_url;
    private String encoding;
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }
}
