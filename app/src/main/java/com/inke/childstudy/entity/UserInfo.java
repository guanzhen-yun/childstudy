package com.inke.childstudy.entity;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;

/**
 * 用户信息
 */
public class UserInfo extends BmobObject {
    private String token;
    private Bitmap bitmap;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String headPhoto;//头像
    private String sex;
    private int age;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
