package com.inke.childstudy.userinfo;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import butterknife.OnClick;

/**
 * 个人信息页
 */

@Route(path = RouterConstants.App.UserInfo)
public class UserInfoActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @OnClick({R.id.tv_addphoto})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_addphoto:
                addPhoto();
                break;
            default:
                break;
        }
    }

    private void addPhoto() {

    }


}
