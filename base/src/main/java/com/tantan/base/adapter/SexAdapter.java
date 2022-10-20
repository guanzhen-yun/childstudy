package com.tantan.base.adapter;

import android.graphics.Color;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tantan.base.R;
import java.util.List;
import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

public class SexAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

  private String mSelectSex;
  private final Broccoli broccoli;//骨架屏

  public SexAdapter(@Nullable List<String> data) {
    super(R.layout.item_sex, data);
    broccoli = new Broccoli();
  }

  @Override
  protected void convert(BaseViewHolder helper, String item) {
    broccoli.addPlaceholder(new PlaceholderParameter.Builder()
        .setView(helper.itemView)
        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
        .build());
    broccoli.show();
    helper.setText(R.id.tv_sex, item);
    helper.getView(R.id.tv_sex).setSelected(item.equals(mSelectSex));
    broccoli.clearAllPlaceholders();
  }

  public void setSelectSex(String selectSex) {
    this.mSelectSex = selectSex;
  }

  public void destroyAdapter() {
    broccoli.removeAllPlaceholders();
  }
}
