package com.inke.childstudy.address;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.AddressTraceAdapter;
import com.inke.childstudy.entity.AddressTrace;
import com.inke.childstudy.routers.RouterConstants;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ziroom.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 位置追踪
 */
@Route(path = RouterConstants.App.AddressTrace)
public class AddressTraceActivity extends BaseActivity {
    @BindView(R.id.rv_trace)
    RecyclerView mRvTrace;

    private AddressTraceAdapter addressTraceAdapter;
    private List<AddressTrace> mListTrace = new ArrayList<>();
    private Observer<List<IMMessage>> mIncomingMessageObserver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_trace;
    }

    @Override
    public void initViews() {
        addressTraceAdapter = new AddressTraceAdapter(mListTrace);
        mRvTrace.setAdapter(addressTraceAdapter);
    }

    @Override
    public void initDatas() {
        getMsgList();
        mIncomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        getMsgList();
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(mIncomingMessageObserver, true);
    }

    private void getMsgList() {
        String otherAccount = "15711175963";
        NIMClient.getService(MsgService.class).queryMessageList(otherAccount, SessionTypeEnum.P2P, 0,
                20).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                if(result != null && mRvTrace != null) {
                    Collections.reverse(result);
                    for (IMMessage imMessage : result) {
                        if(!TextUtils.isEmpty(imMessage.getAttachStr())) {
                            AddressTrace addressTrace = new Gson().fromJson(imMessage.getAttachStr(), AddressTrace.class);
                            mListTrace.add(addressTrace);
                        }
                    }
                    addressTraceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.tv_back)
    public void onClickView(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(mIncomingMessageObserver, false);
    }
}
