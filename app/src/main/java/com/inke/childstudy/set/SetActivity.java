package com.inke.childstudy.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
@Route(path = RouterConstants.App.Set)
public class SetActivity extends BaseActivity {
    @BindView(R.id.tv_change)
    TextView mTvChange;

    private boolean isChange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @OnClick({R.id.tv_loginout, R.id.tv_back, R.id.tv_changeinfo, R.id.tv_change, R.id.tv_color, R.id.tv_word})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_loginout:
                loginout();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_changeinfo:
                RouterUtils.jump(RouterConstants.App.UserInfo);
                break;
            case R.id.tv_change:
                isChange = !isChange;
                if(isChange) {//该状态为修改
                    mTvChange.setText("切换查看");
                } else {//该状态为查看
                    mTvChange.setText("切换修改");
                }
                break;
            case R.id.tv_color:
                Bundle bundle = new Bundle();
                bundle.putBoolean("isEdit", isChange);
                RouterUtils.jump(RouterConstants.App.StudyColor, bundle);
                break;
            case R.id.tv_word:
                RouterUtils.jump(RouterConstants.App.StudyWord);
                break;
            default:
                break;
        }
    }

    private void loginout() {
        BmobUtils.getInstance().updateLoginState(false, new BmobUtils.OnBmobListener() {
            @Override
            public void onSuccess(String objectId) {
                SharedPrefUtils.getInstance().saveLoginToken("");
                EventBus.getDefault().post(new FinishHomeEvent());
                RouterUtils.jumpWithFinish(SetActivity.this, RouterConstants.App.Main);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast("退出失败");
            }
        });
    }
}
