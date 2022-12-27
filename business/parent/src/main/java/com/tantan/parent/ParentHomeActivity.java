package com.tantan.parent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tantan.base.RouterConstants;
import com.tantan.base.RouterConstants.Parent;
import com.tantan.base.adapter.SettingAdapter;
import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.LoginUtils;
import com.tantan.base.utils.SettingItemFactory;
import com.tantan.base.view.dialog.ExitDialog;
import com.tantan.mydata.SettingItem;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;
import java.util.List;

/**
 * 父母首页
 */
@Route(path = Parent.Home)
public class ParentHomeActivity extends BaseActivity implements OnClickListener,
    BaseQuickAdapter.OnItemClickListener {

  private TextView mTvHello;
  private List<SettingItem> itemList;

  @Override
  public int getLayoutId() {
    return R.layout.activity_parent_home;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
    mTvHello = findViewById(R.id.tv_hello);
    RecyclerView rvList = findViewById(R.id.rv_list);
    itemList = SettingItemFactory.getInstance()
        .getSettingItemList();
    SettingAdapter adapter = new SettingAdapter(itemList);
    adapter.setOnItemClickListener(this);
    rvList.setAdapter(adapter);
  }

  @Override
  public void initDatas() {
    String lastedMobile = SharedPrefUtils.getInstance().getLastedMobile();
    UserInfoEntity userInfo = new UserInfoEntity(lastedMobile);
    UserInfoEntity containsUser = DataUtils.getContainsUser(userInfo);
    mTvHello.setText("您好," + containsUser.getNick() + ", 可以和宝宝互动啦!");
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

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    SettingItem settingItem = itemList.get(position);
    SettingItemFactory.getInstance().jumpToPage(this, settingItem.itemType);
  }
}
