package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.AddressTrace;

import java.util.List;

public class AddressTraceAdapter extends BaseQuickAdapter<AddressTrace, BaseViewHolder> {
    public AddressTraceAdapter(@Nullable List<AddressTrace> data) {
        super(R.layout.item_address_trace, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressTrace item) {
        helper.setText(R.id.tv_address, item.getAddress());
        helper.setText(R.id.tv_latlng, item.getLatlng());
        helper.setText(R.id.tv_time, item.getTime());
    }
}
