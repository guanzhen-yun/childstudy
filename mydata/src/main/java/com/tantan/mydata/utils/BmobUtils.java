package com.tantan.mydata.utils;

import android.content.Context;
import android.text.TextUtils;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import com.tantan.mydata.Person;
import com.tantan.mydata.LoginToken;
import com.ziroom.net.LogUtils;
import java.util.List;

/**
 * bmob工具类
 */
public class BmobUtils<T extends BmobObject> {

  private static final String TAG = "Bomb";
  private Context context;

  public void init(Context context) {
    this.context = context;
  }

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
    t.save(context, new SaveListener() {
      @Override
      public void onSuccess() {
        onBmobListener.onSuccess("");
      }

      @Override
      public void onFailure(int i, String s) {
        onBmobListener.onError(s);
      }
    });
  }

  /**
   * token 传入该用户的token
   */
  public void queryTokenData(String token, OnBmobTokenListener onBmobTokenListener) {
    BmobQuery<LoginToken> query = new BmobQuery<>();
    query.addWhereEqualTo("tokenId", token);
    query.findObjects(context, new FindListener<LoginToken>() {
      @Override
      public void onSuccess(List<LoginToken> list) {
        if (list != null && list.size() > 0) {
          onBmobTokenListener.onSuccess(list.get(0).getObjectId(), list.get(0).isLogin());
        } else {
          onBmobTokenListener.onSuccess("", false);
        }
      }

      @Override
      public void onError(int i, String s) {
        onBmobTokenListener.onError(s);
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
      login.update(context, loginToken, new UpdateListener() {
        @Override
        public void onSuccess() {
          onBmobListener.onSuccess("");
        }

        @Override
        public void onFailure(int i, String s) {
          onBmobListener.onError(s);
        }
      });
    }
  }

  /**
   * 更新数据
   */
  public void updateBmobDate(String objectId, BmobObject bmobObject,
      OnBmobListener onBmobListener) {
    bmobObject.update(context, objectId, new UpdateListener() {
      @Override
      public void onSuccess() {
        onBmobListener.onSuccess("");
      }

      @Override
      public void onFailure(int i, String s) {
        onBmobListener.onError(s);
      }
    });
  }

  /**
   * 注册
   */
  public void registData(Person child, OnBmobListener onBmobListener) {
    child.signUp(context, new SaveListener() {

      @Override
      public void onSuccess() {
        onBmobListener.onSuccess("");
      }

      @Override
      public void onFailure(int i, String s) {
        onBmobListener.onError(s);
      }
    });
  }

  /**
   * 登录
   */
  public void loginData(Person child, OnBmobListener onBmobListener) {
    child.login(context, new SaveListener() {

      @Override
      public void onSuccess() {
        onBmobListener.onSuccess("");
      }

      @Override
      public void onFailure(int i, String s) {
        onBmobListener.onError(s);
      }
    });
  }

  /**
   * 获取当前登录的用户
   */
  public BmobObject getCurrentLoginChild() {
    return BmobUser.getCurrentUser(context);
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
