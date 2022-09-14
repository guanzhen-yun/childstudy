package com.inke.childstudy.login;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.tantan.mydata.Person;
import com.tantan.mydata.LoginToken;
import com.inke.childstudy.routers.RouterConstants;
import com.tantan.mydata.event.FinishMainEvent;
import com.tantan.mydata.utils.BmobUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tantan.base.utils.ToastUtils;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.umeng.analytics.MobclickAgent;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页
 */
@Route(path = RouterConstants.App.Login)
public class LoginActivity extends BaseActivity {

  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_password)
  EditText etPassword;

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
  }

  @OnClick(R.id.tv_login)
  public void onClickView() {
    login();
  }

  private void login() {
    String userName = etUsername.getText().toString();
    String password = etPassword.getText().toString();

    if (TextUtils.isEmpty(userName)) {
      ToastUtils.showToast("请输入账号");
      return;
    }
    if (TextUtils.isEmpty(password)) {
      ToastUtils.showToast("请输入密码");
      return;
    }

    Person child = new Person();
    child.setUsername(userName);
    child.setPassword(password);

    BmobUtils.getInstance().loginData(child, new BmobUtils.OnBmobListener() {
      @Override
      public void onSuccess(String objectId) {
        saveLoginToken();
      }

      @Override
      public void onError(String err) {
        ToastUtils.showToast("登录失败" + err);
      }
    });
  }

  /**
   * 保存登录Token
   */
  private void saveLoginToken() {
    LoginToken token = new LoginToken();
    if (BmobUtils.getInstance().getCurrentLoginChild() != null) {
//      String objectId = BmobUtils.getInstance().getCurrentLoginChild().getUsername();
//      token.setTokenId(objectId);
//      BmobUtils.getInstance().queryTokenData(objectId, new BmobUtils.OnBmobTokenListener() {
//        @Override
//        public void onSuccess(String objectId, boolean isLogin) {
//          if (TextUtils.isEmpty(objectId)) {
//            BmobUtils.getInstance().addData(token, new BmobUtils.OnBmobListener() {
//              @Override
//              public void onSuccess(String objectId) {
//                loginSuccess(objectId);
//              }
//
//              @Override
//              public void onError(String err) {
//                ToastUtils.showToast("登录失败" + err);
//              }
//            });
//          } else {
//            loginSuccess(objectId);
//          }
//        }
//
//        @Override
//        public void onError(String err) {
//          BmobUtils.getInstance().addData(token, new BmobUtils.OnBmobListener() {
//            @Override
//            public void onSuccess(String objectId) {
//              loginSuccess(objectId);
//            }
//
//            @Override
//            public void onError(String err) {
//              ToastUtils.showToast("登录失败" + err);
//            }
//          });
//        }
//      });
    }
  }

  private void loginSuccess(String objectId) {
    SharedPrefUtils.getInstance().saveLoginToken(objectId);
    BmobUtils.getInstance().updateLoginState(true, new BmobUtils.OnBmobListener() {
      @Override
      public void onSuccess(String oId) {
        loginIm();
      }

      @Override
      public void onError(String err) {
        ToastUtils.showToast("登录失败" + err);
      }
    });
  }

  private void loginIm() {
    LoginInfo info = new LoginInfo(etUsername.getText().toString(), "123456");
    RequestCallback<LoginInfo> callback =
        new RequestCallback<LoginInfo>() {
          @Override
          public void onSuccess(LoginInfo param) {
            ToastUtils.showToast("登录成功");
            MobclickAgent.onProfileSignIn(param.getAccount());
//            SharedPrefUtils.getInstance().saveMotherAccount(mIsMother);
            SharedPrefUtils.getInstance().saveImToken(param.getToken());
            EventBus.getDefault().post(new FinishMainEvent());
            RouterUtils.jumpWithFinish(LoginActivity.this, RouterConstants.App.Home);
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
        };
    NIMClient.getService(AuthService.class).login(info).setCallback(callback);
  }
}
