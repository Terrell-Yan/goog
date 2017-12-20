package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/3/23.
 */
public class Grigu extends BmobObject{
    private String dynamic_img;
    private String dynamic_time;
    private String dynamic_img2;
    private String dynamic_name;
    private String dynamic_text;

    public void setDynamic_img(String dynamic_img) {
        this.dynamic_img = dynamic_img;
    }

    public void setDynamic_img2(String dynamic_img2) {
        this.dynamic_img2 = dynamic_img2;
    }

    public void setDynamic_name(String dynamic_name) {
        this.dynamic_name = dynamic_name;
    }

    public void setDynamic_text(String dynamic_text) {
        this.dynamic_text = dynamic_text;
    }

    public void setDynamic_time(String dynamic_time) {
        this.dynamic_time = dynamic_time;
    }

    public String getDynamic_img() {
        return dynamic_img;
    }

    public String getDynamic_img2() {
        return dynamic_img2;
    }

    public String getDynamic_name() {
        return dynamic_name;
    }

    public String getDynamic_text() {
        return dynamic_text;
    }

    public String getDynamic_time() {
        return dynamic_time;
    }
    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyUser getAuthor() {
        return author;
    }
}
