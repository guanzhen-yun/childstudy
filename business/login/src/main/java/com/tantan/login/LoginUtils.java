package com.tantan.login;

import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.mydata.event.FinishMainEvent;
import com.tantan.mydata.greendao.DbBean;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.greendao.UserInfoEntityDao;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.WhereCondition;

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
}
