package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;

import java.util.List;

public class ChatAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChatAdapter(@Nullable List<String> data) {
        super(R.layout.item_im, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
