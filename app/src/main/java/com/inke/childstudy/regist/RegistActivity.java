package com.inke.childstudy.regist;

import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.entity.event.FinishMainEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 */
@Route(path = RouterConstants.App.Regist)
public class RegistActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void initViews() {
        StatusBarUtil.setStatusFrontColorDark(this);
    }

    @OnClick(R.id.tv_regist)
    public void onClickView() {
        regist();
    }

    private void regist() {
        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();
        String age = etAge.getText().toString();

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

        Child child = new Child();
        child.setUsername(userName);
        child.setPassword(password);
        child.setNickname(name);
        child.setAge(age);
        BmobUtils.getInstance().registData(child, new BmobUtils.OnBmobListener() {
            @Override
            public void onSuccess(String objectId) {
                ToastUtils.showToast("注册成功, 请使用注册的账号登录");
                RouterUtils.jumpWithFinish(RegistActivity.this, RouterConstants.App.Main);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast("注册失败" + err);
            }
        });

    }

}
