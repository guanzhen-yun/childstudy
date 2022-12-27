package com.tantan.study.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tantan.study.R;
import java.util.List;

public class IsOrNotAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

  private String mSelected;

  public IsOrNotAdapter(@Nullable List<String> data) {
    super(R.layout.item_isornot, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, String item) {
    helper.setText(R.id.tv_isornot, item);
    helper.getView(R.id.tv_isornot).setSelected(item.equals(mSelected));
  }

  public void setSelected(String selected) {
    this.mSelected = selected;
    notifyDataSetChanged();
  }
}
