package com.inke.childstudy.home;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.ziroom.base.BaseActivity;

import butterknife.BindView;

/**
 * 首页
 */
@Route(path = RouterConstants.App.Home)
public class HomeActivity extends BaseActivity {
    @BindView(R.id.tv_nickname)
    TextView tvNickname;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {
        if(BmobUtils.getInstance().getCurrentLoginChild() != null) {
            Child child = BmobUtils.getInstance().getCurrentLoginChild();
            tvNickname.setText("你好" + child.getNickname() + "小朋友");
        }
    }
}
