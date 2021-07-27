package com.inke.childstudy.entity;

import cn.bmob.v3.BmobUser;

/**
 * 孩童类
 */
public class Child extends BmobUser {
    private String age;
    private String nickname;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
