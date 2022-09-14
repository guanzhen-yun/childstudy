package com.tantan.login.regist;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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
import com.ziroom.base.StatusBarUtil;

//注册页面
@Route(path = RouterConstants.Login.Regist)
public class RegistActivity extends BaseActivity<RegistPresenter> implements OnClickListener,
    RegistContract.IView {

  private EditText etName;
  private EditText etAge;
  private EditText etUsername;
  private EditText etPassword;
  private CheckBox cbIsparent;

  private boolean isParent;//是否注册的账号是父母

  @Override
  public int getLayoutId() {
    return R.layout.activity_regist;
  }

  @Override
  public void initViews() {
    StatusBarUtils.setStatusBarImmence(this);
    etName = findViewById(R.id.et_name);
    etPassword = findViewById(R.id.et_password);
    etUsername = findViewById(R.id.et_username);
    etAge = findViewById(R.id.et_age);
    cbIsparent = findViewById(R.id.cb_isparent);
    TextView tvRegist = findViewById(R.id.tv_regist);
    tvRegist.setOnClickListener(this);
//    setTestData();
  }

  @Override
  public void onClick(View view) {
    String userName = etUsername.getText().toString();
    String password = etPassword.getText().toString();
    String name = etName.getText().toString();
    String age = etAge.getText().toString();
    isParent = cbIsparent.isChecked();
    if (TextUtils.isEmpty(userName)) {
      ToastUtils.showToast("请输入账号");
      return;
    }
    if (TextUtils.isEmpty(password)) {
      ToastUtils.showToast("请输入密码");
      return;
    }
    if (TextUtils.isEmpty(name)) {
      ToastUtils.showToast("请输入姓名");
      return;
    }
    if (TextUtils.isEmpty(age)) {
      ToastUtils.showToast("请输入年龄");
      return;
    }
    UserInfoEntity userInfo = new UserInfoEntity(userName, password, name, Integer.parseInt(age),
        isParent);
    mPresenter.regist(userInfo);
  }

  @Override
  public RegistPresenter getPresenter() {
    return new RegistPresenter(this);
  }

  @Override
  public void registSuccess() {
    //自动跳转主页
    if (isParent) {
      RouterUtils.jumpWithFinish(this, Parent.Home);
    } else {
      RouterUtils.jumpWithFinish(this, Child.Home);
    }
  }

  //测试数据
  private void setTestData() {
    etUsername.setText("15711175963");
    etPassword.setText("123456");
    etName.setText("关震");
    etAge.setText("32");
    cbIsparent.setChecked(true);
  }
}
