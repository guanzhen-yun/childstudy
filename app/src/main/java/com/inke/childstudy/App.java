package com.inke.childstudy;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.bmob.v3.Bmob;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        Bmob.initialize(this, "2b7ef1eda2b02905fa9334a15b60492a");
    }
}
