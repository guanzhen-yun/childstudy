package com.tantan.base.utils.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.tantan.mydata.BuildConfig;
import com.tantan.mydata.greendao.DaoMaster;
import com.tantan.mydata.greendao.DaoSession;
import com.tantan.mydata.greendao.DbBean;

import com.ziroom.net.LogUtils;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * 本地数据库工具类
 */
public class DaoSessionUtils {

  private DaoSession daoSession;

  private static DaoSessionUtils instance;

  private DaoSessionUtils() {
  }

  public static DaoSessionUtils getInstance() {
    synchronized (DaoSessionUtils.class) {
      if (instance == null) {
        synchronized (DaoSessionUtils.class) {
          instance = new DaoSessionUtils();
        }
      }
    }
    return instance;
  }

  public DaoSession getDaoInstance() {
    //清空所有数据表的缓存数据
    //daoSession.clear();
    return daoSession;
  }

  public void initGreenDao(Context context) {
    // 初始化//如果你想查看日志信息，请将 DEBUG 设置为 true
    MigrationHelper.DEBUG = BuildConfig.DEBUG;
    //数据库名字
    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(context, "greenDaoTest.db",
        null);

    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
    DaoMaster daoMaster = new DaoMaster(db);

    daoSession = daoMaster.newSession();
  }

  /**
   * insert() 插入数据
   */
  public void insertDbBean(DbBean bean, OnDaoListener daoListener) {
    try {
      getDaoInstance().insert(bean);
      daoListener.onSuccess();
    } catch (Exception e) {
      e.printStackTrace();
      daoListener.onError("插入本地数据失败：" + e.getMessage());
    }
  }


  /**
   * insertOrReplace()数据存在则替换，数据不存在则插入
   */
  public void insertOrReplaceDbBean(DbBean bean) {
    try {
      getDaoInstance().insertOrReplace(bean);

    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("插入或替换本地数据失败：" + e.getMessage());
    }

  }


  /**
   * delete()删除单个数据
   */
  public void deleteDbBean(DbBean bean) {
    try {
      getDaoInstance().delete(bean);

    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("删除本地数据失败：" + e.getMessage());
    }

  }


  /**
   * deleteAll()删除所有数据
   */
  public void deleteAllDbBean(DbBean bean) {
    try {
      getDaoInstance().deleteAll(bean.getClass());

    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("删除本地所有数据失败：" + e.getMessage());
    }

  }


  /**
   * update()修改本地数据
   */
  public void updateDbBean(DbBean bean) {
    try {
      getDaoInstance().update(bean);
    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("修改本地所有数据失败：" + e.getMessage());
    }

  }

  /**
   * loadAll()查询本地所有数据
   */
  public List<? extends DbBean> queryAll(DbBean bean) {
    List<DbBean> beanList = null;
    try {
      beanList = (List<DbBean>) getDaoInstance().loadAll(bean.getClass());
    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("查询本地所有数据失败：" + e.getMessage());
    }

    return beanList;
  }


  /**
   * 根据条件查询本地所有数据 调用时传值方法whereConditions List<WhereCondition> whereConditions = new ArrayList<>();
   * whereConditions.add(StudentDao.Properties.Name.eq("小明")); whereConditions.add(StudentDao.Properties.Age.eq(22));
   */
  public List<? extends DbBean> queryConditionAll(DbBean bean,
      List<WhereCondition> whereConditions) throws ClassCastException {
    List<DbBean> beanList = null;
    try {

      QueryBuilder queryBuilder = getDaoInstance().queryBuilder(bean.getClass());
      //把条件循环加入
      if (null != whereConditions) {
        for (WhereCondition whereCondition : whereConditions) {
          queryBuilder.where(whereCondition);
        }

      }
      beanList = queryBuilder.build().list();
    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("按条件查询本地数据失败：" + e.getMessage());
    }

    return beanList;
  }


  /**
   * 根据原始 SQL 数据查询 手输写 SQL 语句sqlConditions
   */
  public List<? extends DbBean> querySqlAll(DbBean bean, String sqlConditions)
      throws ClassCastException {
    List<DbBean> beanList = null;
    try {
      //查询条件
      WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition(
          sqlConditions);
      //查询QueryBuilder
      QueryBuilder<DbBean> queryBuilder = (QueryBuilder<DbBean>) getDaoInstance().queryBuilder(
          bean.getClass());
      //添加查询条件
      queryBuilder.where(stringCondition);

      beanList = queryBuilder.build().list();
    } catch (Exception e) {
      e.printStackTrace();
      LogUtils.d("sql按条件查询本地数据失败：" + e.getMessage());
    }

    return beanList;
  }

  public interface OnDaoListener {

    void onSuccess();

    void onError(String err);
  }
}
