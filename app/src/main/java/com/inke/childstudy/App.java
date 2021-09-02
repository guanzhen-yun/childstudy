package com.inke.childstudy;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.greendao.DaoMaster;
import com.inke.childstudy.greendao.DaoSession;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.greendao.MySqliteOpenHelper;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.ziroom.net.LogUtils;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    private App mApp;
    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private static DaoSession daoSession;

    private void initGreenDao() {
        // 初始化//如果你想查看日志信息，请将 DEBUG 设置为 true
        if (BuildConfig.DEBUG){
            MigrationHelper.DEBUG = true;
        }else {
            MigrationHelper.DEBUG = false;
        }

        //数据库名字
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(mApp, "greenDaoTest.db",null);

        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
    }

    /**
     * 提供一个全局的会话
     * @return
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        ARouter.init(this);
        ToastUtils.init(this);
        Bmob.initialize(this, "2b7ef1eda2b02905fa9334a15b60492a");
        SharedPrefUtils.getInstance().init(this);
        LogUtils.debug = BuildConfig.DEBUG;
        // queryAndLoadNewPatch为拉取控制台补丁
        //不可放在attachBaseContext 中，否则无网络权限，建议放在主进程任意时刻，如Application的onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

        JPushInterface.setDebugMode(LogUtils.debug);
        JPushInterface.init(this);

        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将进行自动登录）。不能对初始化语句添加进程判断逻辑。
        NIMClient.init(this, loginInfo(), options());
        // 使用 `NIMUtil` 类可以进行主进程判断。
        // boolean mainProcess = NIMUtil.isMainProcess(context)
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
        }

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(LogUtils.debug);

        UMConfigure.preInit(this, "612cc46a4bede245d9f06944", getMetaDataValue("channel"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                UMConfigure.init(mApp, "612cc46a4bede245d9f06944", getMetaDataValue("channel"), UMConfigure.DEVICE_TYPE_PHONE, "");
                // 选用AUTO页面采集模式
                MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
            }
        }).start();
        SDKInitializer.initialize(getApplicationContext());
        CrashReport.initCrashReport(getApplicationContext(), "b2bbfcd40a", LogUtils.debug);
        initGreenDao();
    }

    private String getMetaDataValue(String metaDataName) {
        PackageManager pm = getPackageManager();
        ApplicationInfo appinfo;
        String metaDataValue = "";
        try {
            appinfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appinfo.metaData;
            metaDataValue = metaData.getString(metaDataName);
            return metaDataValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaDataValue;
    }

    // 如果提供，将同时进行自动登录。如果当前还没有登录用户，请传入null。详见自动登录章节。
    private LoginInfo loginInfo() {
        Child currentLoginChild = BmobUtils.getInstance().getCurrentLoginChild();
        if (currentLoginChild != null) {
            return new LoginInfo(currentLoginChild.getUsername(), "123456");
        }
        return null;
    }

    // 设置初始化配置参数，如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();
        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;
        return options;
    }
}
