package com.inke.childstudy;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.taobao.sophix.SophixManager;
import com.ziroom.net.LogUtils;

import cn.bmob.v3.Bmob;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        ToastUtils.init(this);
        Bmob.initialize(this, "2b7ef1eda2b02905fa9334a15b60492a");
        SharedPrefUtils.getInstance().init(this);
        LogUtils.debug = BuildConfig.DEBUG;
        // queryAndLoadNewPatch为拉取控制台补丁
        //不可放在attachBaseContext 中，否则无网络权限，建议放在主进程任意时刻，如Application的onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
