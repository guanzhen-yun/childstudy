package com.inke.childstudy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sp缓存文件
 */
public class SharedPrefUtils {
    private static final String SPFILE = "sp-file";
    private static final String SP_ISFIRSTLOGIN = "sp-firstlogin";
    private SharedPreferences mSharedPreferences;

    private SharedPrefUtils(){}

    private static SharedPrefUtils mInstance;

    public static SharedPrefUtils getInstance() {
        if(mInstance == null) {
            synchronized (SharedPrefUtils.class) {
                if(mInstance == null) {
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

}
