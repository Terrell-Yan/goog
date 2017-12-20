package com.example.goog.activity.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by SeanM on 2017/4/28.
 */

public class UserExtend extends BmobObject{
    private String user_claller;
    private String user_steep;
    private String user_fans;
    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    private String finsh_url;
    public void setUser_claller(String user_claller) {
        this.user_claller = user_claller;
    }

    public void setUser_steep(String user_steep) {
        this.user_steep = user_steep;
    }

    public void setUser_fans(String user_fans) {
        this.user_fans = user_fans;
    }

    public String getUser_claller() {
        return user_claller;
    }

    public String getUser_fans() {
        return user_fans;
    }

    public String getUser_steep() {
        return user_steep;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setFinsh_url(String finsh_url) {
        this.finsh_url = finsh_url;
    }

    public String getFinsh_url() {
        return finsh_url;
    }
}
