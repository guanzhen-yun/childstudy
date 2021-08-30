package com.inke.childstudy.im;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.ChatAdapter;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ziroom.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.App.Chat)
public class ChatActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_im)
    RecyclerView mRvIm;
    @BindView(R.id.et_msg)
    EditText mEtMsg;

    private String myAccount;
    private String otherAccount;
    private ChatAdapter chatAdapter;
    private List<String> mChatList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initViews() {
        Child currentLoginChild = BmobUtils.getInstance().getCurrentLoginChild();
        myAccount = currentLoginChild.getUsername();
        if (myAccount.equals("15711175963")) {
            otherAccount = "18324602696";
            mTvTitle.setText("和妈妈聊天");
        } else {
            otherAccount = "15711175963";
            mTvTitle.setText("和淼淼聊天");
        }
        chatAdapter = new ChatAdapter(mChatList);
        mRvIm.setAdapter(chatAdapter);
    }

    @OnClick(R.id.tv_send)
    public void onClickView(View v) {
        if (v.getId() == R.id.tv_send) {
            String msg = mEtMsg.getText().toString();
            if (!TextUtils.isEmpty(msg)) {
                sendMsg(msg);
            }
        }
    }

    private void sendMsg(String msg) {
        // 以单聊类型为例
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        // 创建一个文本消息
        IMMessage textMessage = MessageBuilder.createTextMessage(otherAccount, sessionType, msg);
        // 发送给对方
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                ToastUtils.showToast("发送成功");
            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showToast("发送失败");
            }

            @Override
            public void onException(Throwable exception) {
                ToastUtils.showToast(exception.getMessage());
            }
        });
    }
}
