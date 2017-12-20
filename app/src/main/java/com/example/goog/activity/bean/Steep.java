package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/4/14.
 */

public class Steep extends BmobObject {
    private String steep_img;
    private String steep_name;
    private String steep_num;
    private String steep_id;
    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    public void setSteep_img(String steep_img) {
        this.steep_img = steep_img;
    }

    public void setSteep_name(String steep_name) {
        this.steep_name = steep_name;
    }

    public void setSteep_num(String steep_num) {
        this.steep_num = steep_num;
    }

    public String getSteep_img() {
        return steep_img;
    }

    public String getSteep_name() {
        return steep_name;
    }

    public String getSteep_num() {
        return steep_num;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setSteep_id(String steep_id) {
        this.steep_id = steep_id;
    }

    public String getSteep_id() {
        return steep_id;
    }
}
