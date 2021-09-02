package com.inke.childstudy.im;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.ChatAdapter;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.SoftInputUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.App.Chat)
public class ChatActivity extends BaseActivity {
    TextView mTvTitle;
    @BindView(R.id.rv_im)
    RecyclerView mRvIm;
    @BindView(R.id.et_msg)
    EditText mEtMsg;

    private String otherAccount;
    private ChatAdapter chatAdapter;
    private List<IMMessage> mChatList = new ArrayList<>();
    private Observer<List<IMMessage>> mIncomingMessageObserver;
    private int offset = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public boolean isNotFitStatus() {
        return true;
    }

    @Override
    public void initViews() {
        StatusBarUtil.with(this).setColor(Color.parseColor("#eeeeee")).init();
        boolean isMother = SharedPrefUtils.getInstance().isMother();
        WindowManager mWindow = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        View titleView = View.inflate(this, R.layout.view_title, null);
        mTvTitle = titleView.findViewById(R.id.tv_title);
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.gravity = Gravity.START | Gravity.TOP;
        lp.format = PixelFormat.RGBA_8888;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (titleView.getRootView().getParent() == null){
            mWindow.addView(titleView.getRootView(),lp);
        }
        if (isMother) {
            otherAccount = "15711175963";
            mTvTitle.setText("和淼淼聊天");
        } else {
            otherAccount = "18324602696";
            mTvTitle.setText("和妈妈聊天");
        }
        chatAdapter = new ChatAdapter(mChatList);
        chatAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                offset = offset + 10;
                getMsgList(offset);
            }
        }, mRvIm);
        mRvIm.setAdapter(chatAdapter);
    }

    @Override
    public void initDatas() {
        getMsgList(0);

        mIncomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        getMsgList(0);
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(mIncomingMessageObserver, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(mIncomingMessageObserver, false);
    }

    private void getMsgList(int offset) {
        NIMClient.getService(MsgService.class).queryMessageList(otherAccount, SessionTypeEnum.P2P, offset,
                10).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                if(result != null && mRvIm != null) {
                    if(offset == 0) {
                        mChatList.clear();
                    }
                    Collections.reverse(result);
                    if(result.size() == 10) {
                        chatAdapter.setEnableLoadMore(true);
                    } else {
                        chatAdapter.setEnableLoadMore(false);
                    }
                    for (IMMessage imMessage : result) {
                        if(!TextUtils.isEmpty(imMessage.getContent())) {
                            mChatList.add(imMessage);
                        }
                    }
                    chatAdapter.notifyDataSetChanged();
                    chatAdapter.loadMoreComplete();
                }
            }
        });
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
                mEtMsg.setText("");
                SoftInputUtils.hintKeyBoard(ChatActivity.this);
                getMsgList(0);
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
