package com.inke.childstudy.utils;

import com.inke.childstudy.entity.Child;
import com.ziroom.net.LogUtils;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * bmob工具类
 */
public class BmobUtils<T extends BmobObject> {
    private static final String TAG = "Bomb";
    private BmobUtils() {
    }

    private static BmobUtils mInstance;

    public static BmobUtils getInstance() {
        if (mInstance == null) {
            synchronized (BmobUtils.class) {
                if (mInstance == null) {
                    mInstance = new BmobUtils();
                }
            }
        }
        return mInstance;
    }

    public void addData(T t, OnBmobListener onBmobListener) {
        t.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(onBmobListener != null) {
                    if(e == null) {
                        LogUtils.d(TAG, objectId);
                        onBmobListener.onSuccess();
                    } else {
                        onBmobListener.onError(e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 注册
     */
    public void registData(Child child, OnBmobListener onBmobListener) {
        child.signUp(new SaveListener<Child>() {

            @Override
            public void done(Child child, BmobException e) {
                if(e == null) {
                    onBmobListener.onSuccess();
                } else {
                    onBmobListener.onError(e.getMessage());
                }
            }
        });
    }

    /**
     * 登录
     */
    public void loginData(Child child, OnBmobListener onBmobListener) {
        child.login(new SaveListener<Child>() {

            @Override
            public void done(Child child, BmobException e) {
                if(e == null) {
                    onBmobListener.onSuccess();
                } else {
                    onBmobListener.onError(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取当前登录的用户
     */
    public Child getCurrentLoginChild() {
        if (BmobUser.isLogin()) {
            return BmobUser.getCurrentUser(Child.class);
        } else {
            return null;
        }
    }

    public interface OnBmobListener {
        void onSuccess();
        void onError(String err);
    }
}
