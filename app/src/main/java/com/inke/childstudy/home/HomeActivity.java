package com.inke.childstudy.home;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
@Route(path = RouterConstants.App.Home)
public class HomeActivity extends BaseActivity {
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_studynum)
    TextView mTvStudynum;
    @BindView(R.id.tv_studypic)
    TextView mTvStudypic;

    private static final int PAGE_NUM = 0;//数字
    private static final int PAGE_PIC = 1;//图形

    private int mCurrentPage = PAGE_NUM;
    private FragmentTransaction mFragmentTransaction;
    private StudyNumFragment mStudyNumFragment;
    private StudyPicFragment mStudyPicFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {
        StatusBarUtil.setStatusFrontColorDark(this);
//        ToastUtils.showToast(getMetaDataValue("channel"));
        if (BmobUtils.getInstance().getCurrentLoginChild() != null) {
            Child child = BmobUtils.getInstance().getCurrentLoginChild();
            tvNickname.setText("你好" + child.getNickname() + "小朋友");
        }
        mStudyNumFragment = StudyNumFragment.getInstance();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.fl_body, mStudyNumFragment).show(mStudyNumFragment).commitAllowingStateLoss();
    }

    public String getMetaDataValue(String metaDataName) {
        PackageManager pm = getPackageManager();
        ApplicationInfo appinfo;
        String metaDataValue = "";
        try {
            appinfo = pm.getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
            Bundle metaData = appinfo.metaData;
            metaDataValue = metaData.getString(metaDataName);
            return metaDataValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaDataValue;
    }

    @OnClick({R.id.tv_studynum, R.id.tv_studypic, R.id.tv_set})
    public void clickView(View v) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.tv_studynum:
                if (mCurrentPage != PAGE_NUM) {
                    mCurrentPage = PAGE_NUM;
                    mTvStudynum.setTextColor(Color.parseColor("#000000"));
                    mTvStudypic.setTextColor(Color.parseColor("#999999"));
                    if(mStudyPicFragment == null) {
                        mFragmentTransaction.show(mStudyNumFragment).commitAllowingStateLoss();
                    } else {
                        mFragmentTransaction.show(mStudyNumFragment).hide(mStudyPicFragment).commitAllowingStateLoss();
                    }
                }
                break;
            case R.id.tv_studypic:
                if (mCurrentPage != PAGE_PIC) {
                    mCurrentPage = PAGE_PIC;
                    mTvStudynum.setTextColor(Color.parseColor("#999999"));
                    mTvStudypic.setTextColor(Color.parseColor("#000000"));
                    if(mStudyPicFragment == null) {
                        mStudyPicFragment = StudyPicFragment.getInstance();
                        mFragmentTransaction.hide(mStudyNumFragment).add(R.id.fl_body, mStudyPicFragment).show(mStudyPicFragment).commitAllowingStateLoss();
                    } else {
                        mFragmentTransaction.hide(mStudyNumFragment).show(mStudyPicFragment).commitAllowingStateLoss();
                    }
                }
                break;
            case R.id.tv_set:
                RouterUtils.jump(RouterConstants.App.Set);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishPage(FinishHomeEvent finishEvent) {
        finish();
    }

    @Override
    public boolean registEventBus() {
        return true;
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            mFragmentTransaction.remove(fragment).commitAllowingStateLoss();
        }
        mFragmentTransaction.commitAllowingStateLoss();
    }
}
