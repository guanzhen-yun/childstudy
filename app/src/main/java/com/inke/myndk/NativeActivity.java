package com.inke.myndk;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.ClickEventUtils;
import com.inke.playgame.PlayGameUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Ndk开发
 */
@Route(path = RouterConstants.App.Native)
public class NativeActivity extends BaseActivity {
    @BindView(R.id.tv_study)
    TextView mTvStudy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_native;
    }

    @Override
    public void initViews() {
        //ndk
        JniUtils jniUtils = new JniUtils();
        mTvStudy.setText(jniUtils.getString());
    }

    @OnClick({R.id.tv_play, R.id.tv_video, R.id.tv_back, R.id.tv_png})
    public void onClickView(View v) {
        if(ClickEventUtils.isFastDoubleClick(v)) return;
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_play:
                //aar文件
                PlayGameUtils.playGame(this, "游戏");
                break;
            case R.id.tv_video:
                RouterUtils.jump(RouterConstants.App.Video);
                break;
            case R.id.tv_png:

                break;
        }
    }
}
