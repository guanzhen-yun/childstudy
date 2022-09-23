package com.tantan.mydata.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sp缓存文件
 */
public class SharedPrefUtils {

  private static final String SPFILE = "sp-file";
  private static final String LASTEST_MOBILE = "lasted-mobile";

  private static final String SP_ISFIRSTLOGIN = "sp-firstlogin";
  private static final String LOGIN_TOKEN = "login-token";
  private static final String IM_TOKEN = "im-token";

  private static final String ISMOTHER = "ismother";
  private SharedPreferences mSharedPreferences;

  private SharedPrefUtils() {
  }

  private static SharedPrefUtils mInstance;

  public static SharedPrefUtils getInstance() {
    if (mInstance == null) {
      synchronized (SharedPrefUtils.class) {
        if (mInstance == null) {
          mInstance = new SharedPrefUtils();
        }
      }
    }
    return mInstance;
  }

  public void init(Context context) {
    mSharedPreferences = context.getSharedPreferences(SPFILE, Context.MODE_PRIVATE);
  }

  /**
   * 设置不是第一次登录
   */
  public void saveNotFirstLogin() {
    mSharedPreferences.edit().putBoolean(SP_ISFIRSTLOGIN, false).apply();
  }

  /**
   * 是否第一次登录
   */
  public boolean isFirstLogin() {
    return mSharedPreferences.getBoolean(SP_ISFIRSTLOGIN, true);
  }

  /**
   * 保存登录Token ObjectId
   */
  public void saveLoginToken(String tokenId) {
    mSharedPreferences.edit().putString(LOGIN_TOKEN, tokenId).apply();
  }

  /**
   * 获取TokenId
   */
  public String getLoginToken() {
    return mSharedPreferences.getString(LOGIN_TOKEN, "");
  }

  /**
   * 保存ImToken
   */
  public void saveImToken(String token) {
    mSharedPreferences.edit().putString(IM_TOKEN, token).apply();
  }

  /**
   * 获取ImToken
   */
  public String getImToken() {
    return mSharedPreferences.getString(IM_TOKEN, "");
  }

  public void saveString(String key, String value) {
    mSharedPreferences.edit().putString(key, value).apply();
  }

  public String getString(String key) {
    return mSharedPreferences.getString(key, "");
  }

  public void saveMotherAccount(boolean isMother) {
    mSharedPreferences.edit().putBoolean(ISMOTHER, isMother).apply();
  }

  public boolean isMother() {
    return mSharedPreferences.getBoolean(ISMOTHER, false);
  }

  public void saveLastedMobile(String accountNum) {
    mSharedPreferences.edit().putString(LASTEST_MOBILE, accountNum).apply();
  }

  public String getLastedMobile() {
    return mSharedPreferences.getString(LASTEST_MOBILE, "");
  }
}
