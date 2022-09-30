package com.tantan.login.login;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.ToastUtils;
import com.tantan.base.utils.Utils;
import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.login.LoginUtils;
import com.tantan.login.login.LoginContract.IView;
import com.tantan.mydata.greendao.DbBean;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.greendao.UserInfoEntityDao;
import com.ziroom.mvp.base.BaseMvpPresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

public class LoginPresenter extends BaseMvpPresenter<LoginContract.IView> implements
    LoginContract.IPresenter, RequestCallback {

  public LoginPresenter(IView view) {
    super(view);
  }

  @Override
  public void login(UserInfoEntity userInfo) {
    //查询该用户是否已经注册过
    UserInfoEntity entity = DataUtils.getContainsUser(userInfo);
    if (entity == null) {
      ToastUtils.showToast("该用户不存在，请先注册...");
    } else {
      String password = entity.getPassword();
      if (password.equals(userInfo.getPassword())) {
        loginIm(userInfo.getAccountNum());
      } else {
        ToastUtils.showToast("该账号密码错误, 请确认你的密码...");
      }
    }
  }

  //登录网易云信
  private void loginIm(String mobile) {
    LoginInfo info = new LoginInfo(mobile, Utils.getDefaultToken());
    NIMClient.getService(AuthService.class).login(info).setCallback(this);
  }

  @Override
  public void onSuccess(Object param) {
    LoginInfo loginInfo = (LoginInfo) param;
    ToastUtils.showToast("登录成功");
    LoginUtils.saveLoginInfo(loginInfo.getAccount());
    mView.loginSuccess();
  }

  @Override
  public void onFailed(int code) {
    if (code == 302) {
      ToastUtils.showToast("账号密码错误");
    } else {
      ToastUtils.showToast("登录失败");
    }
  }

  @Override
  public void onException(Throwable exception) {
    ToastUtils.showToast(exception.getMessage());
  }
}