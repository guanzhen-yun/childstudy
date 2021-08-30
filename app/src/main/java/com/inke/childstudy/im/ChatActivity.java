package com.inke.childstudy.im;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
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

    private String myAccount;
    private String otherAccount;
    private ChatAdapter chatAdapter;
    private List<IMMessage> mChatList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initViews() {
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

    @Override
    public void initDatas() {
        getMsgList();

        Observer<List<IMMessage>> incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        getMsgList();
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    private void getMsgList() {
        NIMClient.getService(MsgService.class).queryMessageList(otherAccount, SessionTypeEnum.P2P, 0,
                20).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                if(result != null) {
                    mChatList.clear();
                    mChatList.addAll(result);
                    chatAdapter.notifyDataSetChanged();
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
                getMsgList();
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
