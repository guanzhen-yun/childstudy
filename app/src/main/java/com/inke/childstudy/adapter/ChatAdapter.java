package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.utils.BmobUtils;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class ChatAdapter extends BaseQuickAdapter<IMMessage, BaseViewHolder> {
    private String username;
    public ChatAdapter(@Nullable List<IMMessage> data) {
        super(R.layout.item_im, data);
        Child currentLoginChild = BmobUtils.getInstance().getCurrentLoginChild();
        username = currentLoginChild.getUsername();
    }

    @Override
    protected void convert(BaseViewHolder helper, IMMessage item) {
        helper.setText(R.id.tv_msg, item.getContent());
        if(item.getDirect() == MsgDirectionEnum.In) {//接收的消息
            helper.setGone(R.id.tv_receiver, true);
            helper.setGone(R.id.tv_sender, false);
        } else {
            helper.setGone(R.id.tv_receiver, false);
            helper.setGone(R.id.tv_sender, true);
        }
        if(username.equals("15711175963")) {
            helper.setText(R.id.tv_receiver, "妈妈");
            helper.setText(R.id.tv_sender, "淼淼");
        } else {
            helper.setText(R.id.tv_receiver, "淼淼");
            helper.setText(R.id.tv_sender, "妈妈");
        }
    }
}
