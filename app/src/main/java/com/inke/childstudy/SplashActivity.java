package com.inke.childstudy;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.NotificationUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.view.dialog.BottomTwoButtonDialog;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_jump)
    TextView mTvJump;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private ValueAnimator mAnim;
    private AnimatorSet animSet;
    private boolean isFirst = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, "android.permission.CAMERA", Manifest.permission.WRITE_SETTINGS, "android.permission.CALL_PHONE"};
            this.requestPermissions(permissions, 100);
        } else {
            jump();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            jump();
        }
    }

    private void jump() {
        if (NotificationUtils.isNotificationEnabled(this)) {
            if (TextUtils.isEmpty(SharedPrefUtils.getInstance().getLoginToken())) {
                toMain();
            } else if (BmobUtils.getInstance().getCurrentLoginChild() != null) {
                toHome();
            } else {
                toMain();
            }
        } else {
            BottomTwoButtonDialog dialog = new BottomTwoButtonDialog(this);
            dialog.setOnOpenListener(new BottomTwoButtonDialog.OnOpenListener() {
                @Override
                public void onOpen() {
                    isFirst = false;
                    NotificationUtils.toSetting(SplashActivity.this);
                }
            });
            dialog.show();
        }
    }

    private void toHome() {
        if(!BuildConfig.DEBUG) {
            //播放动画 放大 平移 渐变效果
            mTvContent.setTranslationY(300f);
            mTvContent.setVisibility(View.VISIBLE);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTvContent, "alpha", 0f, 1f);
            float curTranslationY = mTvContent.getTranslationY();
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTvContent, "translationY", curTranslationY, 0f);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(mTvContent, "scaleY", 1f, 3f, 1f);
            animSet = new AnimatorSet();
            animSet.play(animator1).with(animator3).after(animator2);
            animSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //倒计时3秒进入主页面
                    if (mTvJump != null) {
                        mTvJump.setVisibility(View.VISIBLE);
                        mTvJump.setText("跳过(3)");
                        mAnim = ValueAnimator.ofFloat(3f, 0f);
                        mAnim.setDuration(3000);
                        mAnim.addUpdateListener(animation1 -> {
                            if (mTvJump != null) {
                                float currentValue = (Float) animation1.getAnimatedValue();
                                if (currentValue < 1) {
                                    jumpHome();
                                } else {
                                    mTvJump.setText("跳过(" + Math.round(currentValue) + ")");
                                }
                            }
                        });
                        mAnim.start();
                    }
                }
            });
            animSet.setDuration(2000);
            animSet.start();
        } else {
            jumpHome();
        }
    }

    private void toMain() {
        RouterUtils.jumpWithFinish(SplashActivity.this, RouterConstants.App.Main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animSet != null && animSet.isRunning()) {
            animSet.cancel();
            animSet = null;
        }
        if (mAnim != null && mAnim.isRunning()) {
            mAnim.cancel();
            mAnim = null;
        }
    }

    private void jumpHome() {
        if (mAnim != null && mAnim.isRunning()) {
            mAnim.cancel();
            mAnim = null;
        }
        RouterUtils.jumpWithFinish(this, RouterConstants.App.Home);
    }

    @OnClick(R.id.tv_jump)
    public void clickView(View v) {
        if (v.getId() == R.id.tv_jump) {
            jumpHome();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jump();
        } else {
            ToastUtils.showToast("请开启权限");
        }
    }
}
