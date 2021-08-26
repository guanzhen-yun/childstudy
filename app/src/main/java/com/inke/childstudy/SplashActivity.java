package com.inke.childstudy;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
        } else {
            jump();
        }
    }

    private void jump() {
        if(TextUtils.isEmpty(SharedPrefUtils.getInstance().getLoginToken())) {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Main);
        } else if(BmobUtils.getInstance().getCurrentLoginChild() != null) {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Home);
        } else {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Main);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jump();
        } else {
            ToastUtils.showToast("请开启拍照权限");
        }
    }
}
