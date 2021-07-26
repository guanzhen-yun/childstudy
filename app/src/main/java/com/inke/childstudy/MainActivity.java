package com.inke.childstudy;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

/**
 * 主页面
 */
@Route(path = RouterConstants.App.Main)
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}