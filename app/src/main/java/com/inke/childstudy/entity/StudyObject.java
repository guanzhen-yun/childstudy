package com.inke.childstudy.entity;

import cn.bmob.v3.BmobObject;

public class StudyObject extends BmobObject {
    public String getObjUrl() {
        return objUrl;
    }

    public void setObjUrl(String objUrl) {
        this.objUrl = objUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String objUrl;
    private int type;
    private String objName;
    private String token;
    private String objWord;

    public String getObjWord() {
        return objWord;
    }

    public void setObjWord(String objWord) {
        this.objWord = objWord;
    }
}
