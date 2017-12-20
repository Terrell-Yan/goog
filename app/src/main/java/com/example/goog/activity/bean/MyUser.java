package com.example.goog.activity.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by SeanM on 2017/3/22.
 */
public class MyUser extends BmobUser{
    private String avatarimg;
    private String nickName;
    private BmobFile userhead;
    private String avatar;
    private List<String> loveGatherIds;
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public BmobFile getUserhead() {
        return userhead;
    }
    public void setUserhead(BmobFile userhead) {
        this.userhead = userhead;
    }
    public List<String> getLoveGatherIds() {
        return loveGatherIds;
    }
    public void setLoveGatherIds(List<String> loveGatherIds) {
        this.loveGatherIds = loveGatherIds;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatarimg(String avatarimg) {
        this.avatarimg = avatarimg;
    }

    public String getAvatarimg() {
        return avatarimg;
    }
    private String age;
    private String constelLation;
    private String posiTion;

    public void setAge(String age) {
        this.age = age;
    }

    public void setConstelLation(String constelLation) {
        this.constelLation = constelLation;
    }

    public void setPosiTion(String posiTion) {
        this.posiTion = posiTion;
    }

    public String getAge() {
        return age;
    }

    public String getConstelLation() {
        return constelLation;
    }

    public String getPosiTion() {
        return posiTion;
    }
}
