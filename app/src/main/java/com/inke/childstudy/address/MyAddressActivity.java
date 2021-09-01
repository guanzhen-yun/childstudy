package com.inke.childstudy.address;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

import butterknife.BindView;

@Route(path = RouterConstants.App.MyAddress)
public class MyAddressActivity extends BaseActivity {


    @BindView(R.id.tv_location)
    TextView mTvLocation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myaddress;
    }

    @Override
    public void initViews() {


    }

}
