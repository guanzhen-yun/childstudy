package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;

import java.util.List;

public class SexAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String mSelectSex;
    public SexAdapter(@Nullable List<String> data) {
        super(R.layout.item_sex, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_sex, item);
        helper.getView(R.id.tv_sex).setSelected(item.equals(mSelectSex));
    }

    public void setSelectSex(String selectSex) {
        this.mSelectSex = selectSex;
    }
}
