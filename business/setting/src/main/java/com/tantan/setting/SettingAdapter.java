package com.tantan.setting;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tantan.setting.bean.SettingItem;
import java.util.List;

/**
 * 设置列表适配器
 */
public class SettingAdapter extends BaseQuickAdapter<SettingItem, BaseViewHolder> {

  public SettingAdapter(@Nullable List<SettingItem> data) {
    super(R.layout.item_setting, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, SettingItem item) {
    helper.setText(R.id.tv_item_name, item.title);
  }
}
