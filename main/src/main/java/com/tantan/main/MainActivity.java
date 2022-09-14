package com.tantan.main;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.mydata.event.FinishMainEvent;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 主页面
 */
@Route(path = RouterConstants.Main.Main)
public class MainActivity extends BaseActivity implements OnClickListener {

  private TextView mTvTitle;
  private TextView mTvLogin;
  private TextView mTvRegist;

  @Override
  public int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
    mTvTitle = findViewById(R.id.tv_main_title);
    mTvLogin = findViewById(R.id.tv_login);
    mTvRegist = findViewById(R.id.tv_regist);
    mTvLogin.setOnClickListener(this);
    mTvRegist.setOnClickListener(this);
    setTitle();
  }

  private void setTitle() {
    mTvTitle.post(() -> {
      ForegroundColorSpan titleSpan = new ForegroundColorSpan(Color.parseColor("#ff00ff"));
      String titleStr = mTvTitle.getText().toString();
      SpannableStringBuilder builder = new SpannableStringBuilder(titleStr);
      builder.setSpan(titleSpan, titleStr.length() - 4, titleStr.length(),
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      mTvTitle.setText(builder);
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void finishPage(FinishMainEvent finishEvent) {
    finish();
  }

  @Override
  public boolean registEventBus() {
    return true;
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.tv_login) {//登录
      RouterUtils.jumpWithFinish(this, RouterConstants.Login.Login);
    } else if (view.getId() == R.id.tv_regist) {//注册
      RouterUtils.jumpWithFinish(this, RouterConstants.Login.Regist);
    }
  }
}
