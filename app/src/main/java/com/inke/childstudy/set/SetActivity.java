package com.inke.childstudy.set;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * 设置
 */
@Route(path = RouterConstants.App.Set)
public class SetActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @OnClick({R.id.tv_loginout, R.id.tv_back, R.id.tv_changeinfo})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_loginout:
                loginout();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_changeinfo:
                RouterUtils.jump(RouterConstants.App.UserInfo);
                break;
            default:
                break;
        }
    }

    private void loginout() {
        BmobUtils.getInstance().updateLoginState(false, new BmobUtils.OnBmobListener() {
            @Override
            public void onSuccess(String objectId) {
                SharedPrefUtils.getInstance().saveLoginToken("");
                EventBus.getDefault().post(new FinishHomeEvent());
                RouterUtils.jumpWithFinish(SetActivity.this, RouterConstants.App.Main);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast("退出失败");
            }
        });
    }
}
