package com.inke.myndk;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.playgame.PlayGameUtils;
import com.ziroom.base.BaseActivity;

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
        JniUtils jniUtils = new JniUtils();
        mTvStudy.setText(jniUtils.getString());
    }

    @OnClick(R.id.tv_play)
    public void onClickView() {
        PlayGameUtils.playGame(this, "游戏");
    }
}
