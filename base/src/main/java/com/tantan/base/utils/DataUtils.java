package com.tantan.base.utils;

import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils.OnDaoListener;
import com.tantan.mydata.greendao.DbBean;
import com.tantan.mydata.greendao.StudyColor;
import com.tantan.mydata.greendao.StudyColorDao.Properties;
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

  /**
   * 查询当前用户
   */
  public static UserInfoEntity getCurrentUser(String userAccount) {
    UserInfoEntity userInfoEntity = new UserInfoEntity(userAccount);
    List<WhereCondition> whereConditions = new ArrayList<>();
    whereConditions.add(UserInfoEntityDao.Properties.AccountNum.eq(userAccount));
    List<? extends DbBean> dbBeans = DaoSessionUtils.getInstance()
        .queryConditionAll(userInfoEntity, whereConditions);
    if (dbBeans.size() > 0) {
      return (UserInfoEntity) dbBeans.get(0);
    }
    return null;
  }

  /**
   * 修改用户信息
   */
  public static void updateUserInfo(UserInfoEntity userInfo, OnDaoListener onDaoListener) {
    DaoSessionUtils.getInstance().updateDbBean(userInfo, onDaoListener);
  }

  //查询数据库里是否有该用户
  public static boolean isContainsColor(StudyColor studyColor) {
    List<WhereCondition> whereConditions = new ArrayList<>();
    whereConditions.add(Properties.ColorStr.eq(studyColor.getColorStr()));
    List<? extends DbBean> dbBeans = DaoSessionUtils.getInstance()
        .queryConditionAll(studyColor, whereConditions);
    return (dbBeans.size() > 0);
  }
}
