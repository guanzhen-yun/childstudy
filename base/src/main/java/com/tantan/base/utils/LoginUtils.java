package com.tantan.base.utils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.tantan.mydata.event.FinishMainEvent;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.umeng.analytics.MobclickAgent;
import org.greenrobot.eventbus.EventBus;

//登录工具类
public class LoginUtils {

  //保存登录信息
  public static void saveLoginInfo(String accountNum) {
    MobclickAgent.onProfileSignIn(accountNum);
    EventBus.getDefault().post(new FinishMainEvent());
    SharedPrefUtils.getInstance().saveLastedMobile(accountNum);
    boolean isParent = DataUtils.isParent(new UserInfoEntity(accountNum));
    SharedPrefUtils.getInstance().saveParentAccount(isParent);
  }

  //注销登录
  public static void loginout() {
    MobclickAgent.onProfileSignOff();
    NIMClient.getService(AuthService.class).logout();
    SharedPrefUtils.getInstance().saveParentAccount(false);
    SharedPrefUtils.getInstance().saveLastedMobile("");
  }
}
