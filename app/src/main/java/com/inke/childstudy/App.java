package com.inke.childstudy;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.mydata.greendao.DaoMaster;
import com.tantan.mydata.greendao.DaoSession;
import com.tantan.mydata.utils.BmobUtils;
import com.tantan.base.utils.greendao.MySqliteOpenHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tantan.base.utils.ToastUtils;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import com.ziroom.net.LogUtils;

public class App extends Application {

  private static App mApp;

  public static App getApp() {
    return mApp;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mApp = this;
    //Arouter初始化
    ARouter.init(this);
    if (BuildConfig.DEBUG) {
      ARouter.openLog();
      ARouter.openDebug();
    }
    BmobUtils.getInstance().init(this);
    ToastUtils.init(this);
    //bomb初始化
    Bmob.initialize(this, "6449e49ab0f71abe4abdcf4175572956");
    SharedPrefUtils.getInstance().init(this);
    LogUtils.debug = BuildConfig.DEBUG;
    // queryAndLoadNewPatch为拉取控制台补丁
    //不可放在attachBaseContext 中，否则无网络权限，建议放在主进程任意时刻，如Application的onCreate中
    //阿里sophix热更新
    SophixManager.getInstance().queryAndLoadNewPatch();

    //极光推送初始化
    JPushInterface.setDebugMode(LogUtils.debug);
    JPushInterface.init(this);

    // 网易云信SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将进行自动登录）。不能对初始化语句添加进程判断逻辑。
    NIMClient.init(this, loginInfo(), options());
    // 使用 `NIMUtil` 类可以进行主进程判断。
    // boolean mainProcess = NIMUtil.isMainProcess(context)
    if (NIMUtil.isMainProcess(this)) {
      // 注意：以下操作必须在主进程中进行
      // 1、UI相关初始化操作
      // 2、相关Service调用
    }

    //友盟统计
    //设置LOG开关，默认为false
    UMConfigure.setLogEnabled(LogUtils.debug);

    UMConfigure.preInit(this, "612cc46a4bede245d9f06944", getMetaDataValue("channel"));

    new Thread(() -> {
      UMConfigure.init(mApp, "612cc46a4bede245d9f06944", getMetaDataValue("channel"),
          UMConfigure.DEVICE_TYPE_PHONE, "");
      // 选用AUTO页面采集模式
      MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }).start();
    //百度地图初始化
    SDKInitializer.initialize(getApplicationContext());
    //bugly初始化
    CrashReport.initCrashReport(getApplicationContext(), "b2bbfcd40a", LogUtils.debug);
    //greenDao初始化
    DaoSessionUtils.getInstance().initGreenDao(this);
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
//    Person currentLoginChild = BmobUtils.getInstance().getCurrentLoginChild();
//    if (currentLoginChild != null) {
//      return new LoginInfo(currentLoginChild.getUsername(), "123456");
//    }
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
