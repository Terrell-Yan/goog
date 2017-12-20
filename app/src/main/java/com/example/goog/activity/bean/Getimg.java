package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/3/23.
 */
public class Getimg extends BmobObject{
    private String img;
    private String name;
    private String music;
    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMusic() {
        return music;
    }
}
