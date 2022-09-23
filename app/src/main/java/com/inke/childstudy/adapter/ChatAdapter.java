package com.inke.childstudy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;
import com.inke.childstudy.utils.DateUtils;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import com.tantan.mydata.utils.SharedPrefUtils;
import java.util.List;

public class ChatAdapter extends BaseQuickAdapter<IMMessage, BaseViewHolder> {

  private boolean isMother;

  public ChatAdapter(@Nullable List<IMMessage> data) {
    super(R.layout.item_im, data);
    isMother = SharedPrefUtils.getInstance().isParent();
  }

  @Override
  protected void convert(BaseViewHolder helper, IMMessage item) {
    long time = item.getTime();
    String stampToDate = DateUtils.stampToDate(time);
    helper.setText(R.id.tv_time, stampToDate);
    helper.setText(R.id.tv_msg, item.getContent());
    if (item.getDirect() == MsgDirectionEnum.In) {//接收的消息
      helper.setGone(R.id.tv_receiver, true);
      helper.setGone(R.id.tv_sender, false);
    } else {
      helper.setGone(R.id.tv_receiver, false);
      helper.setGone(R.id.tv_sender, true);
    }
    if (!isMother) {
      helper.setText(R.id.tv_receiver, "妈妈");
      helper.setText(R.id.tv_sender, "淼淼");
    } else {
      helper.setText(R.id.tv_receiver, "淼淼");
      helper.setText(R.id.tv_sender, "妈妈");
    }
  }
}
