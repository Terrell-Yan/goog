package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/4/14.
 */

public class New extends BmobObject {
   private String new_img;
    private String new_name;

    public void setNew_img(String new_img) {
        this.new_img = new_img;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getNew_img() {
        return new_img;
    }

    public String getNew_name() {
        return new_name;
    }
}
