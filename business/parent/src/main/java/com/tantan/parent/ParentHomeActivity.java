package com.tantan.parent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.RouterConstants.Parent;
import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.LoginUtils;
import com.tantan.base.view.dialog.ExitDialog;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

/**
 * 父母首页
 */
@Route(path = Parent.Home)
public class ParentHomeActivity extends BaseActivity implements OnClickListener {

  private TextView mTvHello;

  private UserInfoEntity mContainsUser;

  @Override
  public int getLayoutId() {
    return R.layout.activity_parent_home;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
    mTvHello = findViewById(R.id.tv_hello);
    TextView tvAddress = findViewById(R.id.tv_address);
    tvAddress.setOnClickListener(this);
    TextView tvInfo = findViewById(R.id.tv_info);
    tvInfo.setOnClickListener(this);
    TextView tvTostudy = findViewById(R.id.tv_tostudy);
    tvTostudy.setOnClickListener(this);
    TextView tvLoginout = findViewById(R.id.tv_loginout);
    tvLoginout.setOnClickListener(this);

  }

  @Override
  public void initDatas() {
    String lastedMobile = SharedPrefUtils.getInstance().getLastedMobile();
    UserInfoEntity userInfo = new UserInfoEntity(lastedMobile);
    mContainsUser = DataUtils.getContainsUser(userInfo);
    mTvHello.setText("您好," + mContainsUser.getNick() + ", 可以和宝宝互动啦!");
  }

  @Override
  public void onClick(View view) {
    int viewId = view.getId();
    if (viewId == R.id.tv_loginout) { //注销账号
      ExitDialog exitDialog = new ExitDialog(this);
      exitDialog.setOnExitListener(this::loginOut);
      exitDialog.show();
    }
  }

  private void loginOut() {
    LoginUtils.loginout();
    RouterUtils.jumpWithFinish(this, RouterConstants.Main.Main);
  }
}
