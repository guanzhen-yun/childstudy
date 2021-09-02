package com.inke.childstudy.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.address.MyAddressActivity;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.service.LocationService;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.utils.TrackUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
        if(SharedPrefUtils.getInstance().isMother()) {
            RouterUtils.jumpWithFinish(this, RouterConstants.App.Set);
            return;
        }
        StatusBarUtil.setStatusFrontColorDark(this);
        if (BmobUtils.getInstance().getCurrentLoginChild() != null) {
            Child child = BmobUtils.getInstance().getCurrentLoginChild();
            tvNickname.setText("你好" + child.getNickname() + "小朋友");
        }
        mStudyNumFragment = StudyNumFragment.getInstance();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.fl_body, mStudyNumFragment).show(mStudyNumFragment).commitAllowingStateLoss();

        List<String> permissionList = new ArrayList<>();
        //如果没有启动下面权限，就询问用户让用户打开
        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(HomeActivity.this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(HomeActivity.this, permissions, 1);
        }
        else {
            startService(new Intent(this, LocationService.class));
        }
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
                TrackUtils.track(this, "toSet", null);
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
    }

    /*只有同意打开相关权限才可以开启本程序*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    startService(new Intent(this, LocationService.class));
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}
