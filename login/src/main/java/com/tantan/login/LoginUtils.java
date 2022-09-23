package com.tantan.login;

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
  }

  //查询数据库里是否有该用户
  public static UserInfoEntity getContainsUser(UserInfoEntity userInfo) {
    List<WhereCondition> whereConditions = new ArrayList<>();
    whereConditions.add(UserInfoEntityDao.Properties.AccountNum.eq(userInfo.getAccountNum()));
    List<? extends DbBean> dbBeans = DaoSessionUtils.getInstance()
        .queryConditionAll(userInfo, whereConditions);
    if (dbBeans.size() > 0) {
      return (UserInfoEntity) dbBeans.get(0);
    }
    return null;
  }
}
