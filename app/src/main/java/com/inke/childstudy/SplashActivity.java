package com.inke.childstudy;

import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        if(BmobUtils.getInstance().getCurrentLoginChild() != null) {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Home);
        } else {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Main);
        }
    }
}
