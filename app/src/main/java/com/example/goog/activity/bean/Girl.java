package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/3/23.
 */
public class Girl extends BmobObject {
    private String img4;
    private String img3;
    private String img2;
    private String img;
    private String name;
    private String name2;
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

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName2() {
        return name2;
    }
}