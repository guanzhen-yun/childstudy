package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;

import java.util.List;

public class StudyWordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public StudyWordAdapter(@Nullable List<String> data) {
        super(R.layout.item_word, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_word, item);
    }
}
