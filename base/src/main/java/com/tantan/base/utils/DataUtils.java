package com.tantan.base.utils;

import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.mydata.greendao.DbBean;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.greendao.UserInfoEntityDao;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * 数据查询工具类
 */
public class DataUtils {

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

  //查询数据库里该用户是否是父母
  public static boolean isParent(UserInfoEntity userInfo) {
    List<WhereCondition> whereConditions = new ArrayList<>();
    whereConditions.add(UserInfoEntityDao.Properties.AccountNum.eq(userInfo.getAccountNum()));
    List<? extends DbBean> dbBeans = DaoSessionUtils.getInstance()
        .queryConditionAll(userInfo, whereConditions);
    if (dbBeans.size() > 0) {
      return ((UserInfoEntity) dbBeans.get(0)).getIsParent();
    }
    return false;
  }
}
