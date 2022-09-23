package com.tantan.main;

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
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tantan.base.RouterConstants.Child;
import com.tantan.base.RouterConstants.Parent;
import com.tantan.base.dialog.BottomTwoButtonDialog;
//import com.tantan.mydata.utils.BmobUtils;
import com.tantan.base.utils.NotificationUtils;
import com.tantan.base.RouterConstants;
import com.tantan.mydata.utils.BmobUtils;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.tantan.base.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.net.LogUtils;

//启动页
public class SplashActivity extends BaseActivity implements OnClickListener {

  private TextView mTvJump;
  private TextView mTvContent;

  private boolean isFromNotify = false;//是否从通知返回
  private ValueAnimator mAnim;
  private AnimatorSet animSet;

  @Override
  public int getLayoutId() {
    return R.layout.activity_splash;
  }

  @Override
  public void initViews() {
    mTvJump = findViewById(R.id.tv_jump);
    mTvJump.setOnClickListener(this);
    mTvContent = findViewById(R.id.tv_content);
    requestPermissions();
  }

  //请求权限
  private void requestPermissions() {
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
        "android.permission.CAMERA", Manifest.permission.WRITE_SETTINGS,
        "android.permission.CALL_PHONE"};
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      requestPermissions(permissions, 100);
    } else {
      jumpPage();
    }
  }

  //跳转页面
  private void jumpPage() {
    //有通知权限
    if (NotificationUtils.isNotificationEnabled(this)) {
      //没有登录的token 跳转登录 注册页面
      if (TextUtils.isEmpty(SharedPrefUtils.getInstance().getLoginToken())
          || BmobUtils.getInstance().getCurrentLoginChild() == null) {
        toMainPage();
      } else {
        //正式环境增加动画
        if (!BuildConfig.DEBUG) {
          startAnim();
        } else {
          jumpHomePage();
        }
      }
    } else {//没有通知权限 申请弹窗跳转开启通知
      BottomTwoButtonDialog dialog = new BottomTwoButtonDialog(this);
      dialog.setOnOpenListener(() -> {
        isFromNotify = true;
        NotificationUtils.toSetting(SplashActivity.this);
      });
      dialog.show();
    }
  }

  //播放动画 放大 平移 渐变效果
  private void startAnim() {
    mTvContent.setTranslationY(300f);
    mTvContent.setVisibility(View.VISIBLE);
    ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTvContent, "alpha", 0f, 1f);
    float curTranslationY = mTvContent.getTranslationY();
    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTvContent, "translationY", curTranslationY,
        0f);
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
                jumpHomePage();
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
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (isFromNotify) {
      jumpPage();
    }
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

  //跳转已登录的信息功能页
  private void jumpHomePage() {
    if (mAnim != null && mAnim.isRunning()) {
      mAnim.cancel();
      mAnim = null;
    }
    RouterUtils.jumpWithFinish(this, RouterConstants.App.Home);
  }

  //跳转首页 可以注册和登录
  private void toMainPage() {
    String lastedMobile = SharedPrefUtils.getInstance().getLastedMobile();
    if (TextUtils.isEmpty(lastedMobile)) {
      RouterUtils.jumpWithFinish(SplashActivity.this, RouterConstants.Main.Main);
    } else if (SharedPrefUtils.getInstance().isParent()) {
      RouterUtils.jumpWithFinish(SplashActivity.this, Parent.Home);
    } else {
      RouterUtils.jumpWithFinish(SplashActivity.this, Child.Home);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      jumpPage();
    } else {
      ToastUtils.showToast("请开启权限");
    }
  }

  @Override
  public void onClick(View view) {
    jumpHomePage();
  }
}
