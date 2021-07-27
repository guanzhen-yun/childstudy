package com.inke.childstudy.login;

import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

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

        Child child = new Child();
        child.setUsername(userName);
        child.setPassword(password);
        BmobUtils.getInstance().loginData(child, new BmobUtils.OnBmobListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast("登录成功");
                RouterUtils.jumpWithFinish(LoginActivity.this, RouterConstants.App.Home);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast("登录失败" + err);
            }
        });

    }

}
