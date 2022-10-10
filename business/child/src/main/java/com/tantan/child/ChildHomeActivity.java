package com.tantan.child;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.RouterConstants.Child;
import com.tantan.base.RouterConstants.Setting;
import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.TrackUtils;
import com.tantan.child.fragment.StudyNumFragment;
import com.tantan.child.fragment.StudyPicFragment;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import com.ziroom.base.StatusBarUtil;

/**
 * 孩子首页
 */
@Route(path = Child.Home)
public class ChildHomeActivity extends BaseActivity implements OnClickListener {

  private TextView mTvHello;
  private TextView mTvStudynum;
  private TextView mTvStudypic;
  private TextView mTvSet;

  private UserInfoEntity mContainsUser;

  private static final int PAGE_NUM = 0;//数字
  private static final int PAGE_PIC = 1;//图形

  private int mCurrentPage = PAGE_NUM;
  private FragmentTransaction mFragmentTransaction;
  private StudyNumFragment mStudyNumFragment;
  private StudyPicFragment mStudyPicFragment;

  @Override
  public int getLayoutId() {
    return R.layout.child_activity_child_home;
  }

  @Override
  public void initViews() {
    StatusBarUtil.setStatusFrontColorDark(this);
    mTvHello = findViewById(R.id.tv_hello);
    mTvStudynum = findViewById(R.id.tv_studynum);
    mTvStudypic = findViewById(R.id.tv_studypic);
    mTvSet = findViewById(R.id.tv_set);

    mTvStudynum.setOnClickListener(this);
    mTvStudypic.setOnClickListener(this);
    mTvSet.setOnClickListener(this);

    mStudyNumFragment = StudyNumFragment.getInstance();
    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
    mFragmentTransaction.add(R.id.fl_body, mStudyNumFragment).show(mStudyNumFragment)
        .commitAllowingStateLoss();

  }

  @Override
  public void initDatas() {
    String lastedMobile = SharedPrefUtils.getInstance().getLastedMobile();
    UserInfoEntity userInfo = new UserInfoEntity(lastedMobile);
    mContainsUser = DataUtils.getContainsUser(userInfo);
    mTvHello.setText("您好," + mContainsUser.getNick() + "小朋友, 欢迎使用该app!");
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();
    if (id == R.id.tv_set) {
      TrackUtils.track(this, "toSet", null);
      RouterUtils.jump(Setting.Main);
    } else {
      mFragmentTransaction = getSupportFragmentManager().beginTransaction();
      if (id == R.id.tv_studynum) {
        if (mCurrentPage != PAGE_NUM) {
          mCurrentPage = PAGE_NUM;
          mTvStudynum.setTextColor(Color.parseColor("#000000"));
          mTvStudypic.setTextColor(Color.parseColor("#999999"));
          if (mStudyPicFragment == null) {
            mFragmentTransaction.show(mStudyNumFragment).commitAllowingStateLoss();
          } else {
            mFragmentTransaction.show(mStudyNumFragment).hide(mStudyPicFragment)
                .commitAllowingStateLoss();
          }
        }
      } else if (id == R.id.tv_studypic) {
        if (mCurrentPage != PAGE_PIC) {
          mCurrentPage = PAGE_PIC;
          mTvStudynum.setTextColor(Color.parseColor("#999999"));
          mTvStudypic.setTextColor(Color.parseColor("#000000"));
          if (mStudyPicFragment == null) {
            mStudyPicFragment = StudyPicFragment.getInstance();
            mFragmentTransaction.hide(mStudyNumFragment).add(R.id.fl_body, mStudyPicFragment)
                .show(mStudyPicFragment).commitAllowingStateLoss();
          } else {
            mFragmentTransaction.hide(mStudyNumFragment).show(mStudyPicFragment)
                .commitAllowingStateLoss();
          }
        }
      }
    }
  }
}
