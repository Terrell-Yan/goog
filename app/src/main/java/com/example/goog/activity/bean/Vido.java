package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/3/31.
 */

public class Vido extends BmobObject {
    private String vido_url;
    private String vido_data;
    private String vido_img;
    public void setvido_url(String vido_url) {
        this.vido_url = vido_url;
    }

    public String getvido_url() {
        return vido_url;
    }
    public void setvido_data(String vido_data) {
        this.vido_data = vido_data;
    }

    public String getvido_data() {
        return vido_data;
    }
    public void setvido_img(String vido_img) {
        this.vido_img = vido_img;
    }

    public String getvido_img() {
        return vido_img;
    }

}
