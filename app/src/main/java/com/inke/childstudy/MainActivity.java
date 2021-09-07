package com.inke.childstudy;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.entity.event.FinishMainEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * 主页面
 */
@Route(path = RouterConstants.App.Main)
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        StatusBarUtil.setStatusFrontColorDark(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_regist})
    public void onViewClick(View v) {
         switch (v.getId()) {
             case R.id.tv_login:
                 RouterUtils.jump(RouterConstants.App.Login);
                 break;
             case R.id.tv_regist:
                 RouterUtils.jump(RouterConstants.App.Regist);
                 break;
         }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishPage(FinishMainEvent finishEvent) {
        finish();
    }

    @Override
    public boolean registEventBus() {
        return true;
    }
}