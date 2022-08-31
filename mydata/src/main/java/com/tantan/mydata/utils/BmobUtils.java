package com.tantan.mydata.utils;

import android.text.TextUtils;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import com.tantan.mydata.Child;
import com.tantan.mydata.LoginToken;
import com.ziroom.net.LogUtils;
import java.util.List;

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
        if (onBmobListener != null) {
          if (e == null) {
            LogUtils.d(TAG, objectId);
            onBmobListener.onSuccess(objectId);
          } else {
            onBmobListener.onError(e.getMessage());
          }
        }
      }
    });
  }

  /**
   * token 传入该用户的token
   */
  public void queryTokenData(String token, OnBmobTokenListener onBmobTokenListener) {
    BmobQuery<LoginToken> query = new BmobQuery<>();
    query.addWhereEqualTo("tokenId", token);
    query.findObjects(new FindListener<LoginToken>() {
      @Override
      public void done(List<LoginToken> object, BmobException e) {
        if (e == null && object != null && object.size() > 0) {
          onBmobTokenListener.onSuccess(object.get(0).getObjectId(), object.get(0).isLogin());
        } else if (e == null) {
          onBmobTokenListener.onSuccess("", false);
        } else {
          onBmobTokenListener.onError(e.getMessage());
        }
      }
    });
  }

  /**
   * 更新登录状态--退出用
   */
  public void updateLoginState(boolean isLogin, OnBmobListener onBmobListener) {
    String loginToken = SharedPrefUtils.getInstance().getLoginToken();
    if (!TextUtils.isEmpty(loginToken)) {
      LoginToken login = new LoginToken();
      login.setLogin(isLogin);
      login.update(loginToken, new UpdateListener() {
        @Override
        public void done(BmobException e) {
          if (e == null) {
            onBmobListener.onSuccess("");
          } else {
            onBmobListener.onError(e.getMessage());
          }
        }
      });
    }
  }

  /**
   * 更新数据
   */
  public void updateBmobDate(String objectId, BmobObject bmobObject,
      OnBmobListener onBmobListener) {
    bmobObject.update(objectId, new UpdateListener() {
      @Override
      public void done(BmobException e) {
        if (e == null) {
          onBmobListener.onSuccess("");
        } else {
          onBmobListener.onError(e.getMessage());
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
        if (e == null) {
          onBmobListener.onSuccess("");
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
        if (e == null) {
          onBmobListener.onSuccess("");
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

    void onSuccess(String objectId);

    void onError(String err);
  }

  public interface OnBmobTokenListener {

    void onSuccess(String objectId, boolean isLogin);

    void onError(String err);
  }
}
