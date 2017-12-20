package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/4/26.
 */

public class Collection extends BmobObject{
    private String url;
    private String img;
    private String name;
    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    private String steep_time;
    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setSteep_time(String steep_time) {
        this.steep_time = steep_time;
    }

    public String getSteep_time() {
        return steep_time;
    }
}
