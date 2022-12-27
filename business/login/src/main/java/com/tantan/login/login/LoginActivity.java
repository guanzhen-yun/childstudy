package com.tantan.login.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.RouterConstants.Child;
import com.tantan.base.RouterConstants.Parent;
import com.tantan.base.utils.StatusBarUtils;
import com.tantan.base.utils.ToastUtils;
import com.tantan.login.R;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

/**
 * 登录页
 */
@Route(path = RouterConstants.Login.Login)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.IView,
    View.OnClickListener {

  private EditText etUsername;
  private EditText etPassword;

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  public void initViews() {
    StatusBarUtils.setStatusBarImmence(this);
    etUsername = findViewById(R.id.et_username);
    etPassword = findViewById(R.id.et_password);
    TextView tvLogin = findViewById(R.id.tv_login);
    tvLogin.setOnClickListener(this);
    useTestData();
  }

  //测试数据
  private void useTestData() {
    etUsername.setText("15711175963");
    etPassword.setText("123456");
  }

  @Override
  public LoginPresenter getPresenter() {
    return new LoginPresenter(this);
  }

  @Override
  public void onClick(View view) {
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
    UserInfoEntity userInfo = new UserInfoEntity(userName, password);
    mPresenter.login(userInfo);
  }

  @Override
  public void loginSuccess(boolean isParent) {
    //自动跳转主页
    if (isParent) {
      RouterUtils.jumpWithFinish(this, Parent.Home);
    } else {
      RouterUtils.jumpWithFinish(this, Child.Home);
    }
  }
}
