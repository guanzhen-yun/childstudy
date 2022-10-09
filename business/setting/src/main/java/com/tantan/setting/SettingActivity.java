package com.tantan.setting;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.setting.R;
import com.ziroom.base.BaseActivity;

/**
 * xx页
 */
@Route(path = RouterConstants.xx)
public class SettingActivity extends BaseActivity {

  @Override
  public int getLayoutId() {
    return R.layout.activity_setting;
  }

  @Override
  public void initViews() {
  }
}
