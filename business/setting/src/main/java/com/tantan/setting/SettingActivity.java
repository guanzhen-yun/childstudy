package com.tantan.setting;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tantan.base.RouterConstants;
import com.tantan.base.RouterConstants.Setting;
import com.tantan.base.utils.LoginUtils;
import com.tantan.base.view.dialog.ExitDialog;
import com.tantan.setting.bean.SettingItem;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;
import java.util.List;

/**
 * 设置首页
 */
@Route(path = Setting.Main)
public class SettingActivity extends BaseActivity implements View.OnClickListener,
    BaseQuickAdapter.OnItemClickListener {

  private List<SettingItem> itemList;

  @Override
  public int getLayoutId() {
    return R.layout.activity_setting;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
    TextView tvTitle = findViewById(R.id.tv_title);
    tvTitle.setText("设置");
    findViewById(R.id.view_left).setOnClickListener(this);
    findViewById(R.id.tv_loginout).setOnClickListener(this);
    RecyclerView rvList = findViewById(R.id.rv_list);
    itemList = SettingItemFactory.getInstance()
        .getSettingItemList();
    SettingAdapter adapter = new SettingAdapter(itemList);
    adapter.setOnItemClickListener(this);
    rvList.setAdapter(adapter);
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();
    if (id == R.id.view_left) {//返回
      finish();
    } else if (id == R.id.tv_loginout) {//退出登录
      loginOut();
    }
  }

  private void loginOut() {
    ExitDialog exitDialog = new ExitDialog(this);
    exitDialog.setOnExitListener(() -> {
      LoginUtils.loginout();
      RouterUtils.jumpWithFinish(SettingActivity.this, RouterConstants.Main.Main);
    });
    exitDialog.show();
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    SettingItem settingItem = itemList.get(position);
    SettingItemFactory.getInstance().jumpToPage(this, settingItem.itemType);
  }
}
