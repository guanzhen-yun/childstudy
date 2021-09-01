package com.inke.childstudy.entity;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

public class AddressTrace implements MsgAttachment {
    private String time;
    private String address;
    private String latlng;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toJson(boolean send) {
        return new Gson().toJson(this);
    }
}
