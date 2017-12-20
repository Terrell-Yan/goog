package com.example.goog.activity.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by will on 2016/10/29.
 */

public class Book extends BmobObject{
    private String book_name;
    private String book_img;
    private String book_path;

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_path(String book_path) {
        this.book_path = book_path;
    }

    public String getBook_path() {
        return book_path;
    }
}
