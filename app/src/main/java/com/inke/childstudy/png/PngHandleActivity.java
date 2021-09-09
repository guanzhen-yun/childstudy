package com.inke.childstudy.png;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

@Route(path = RouterConstants.App.Png)
public class PngHandleActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_pnghandle;
    }
}
