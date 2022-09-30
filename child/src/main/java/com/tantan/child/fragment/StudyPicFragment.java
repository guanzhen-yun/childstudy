package com.tantan.child.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.tantan.child.R;
import com.tantan.child.view.tuxing.PicParentView;
import com.umeng.analytics.MobclickAgent;
import com.ziroom.base.BaseFragment;

/**
 * 学习图片
 */
public class StudyPicFragment extends BaseFragment implements OnClickListener {

  private PicParentView mPicParent;

  public static StudyPicFragment getInstance() {
    return new StudyPicFragment();
  }

  @Override
  public int getLayoutId() {
    return R.layout.child_fragment_study_pic;
  }

  @Override
  public void initViews(View mView) {
    mPicParent = mView.findViewById(R.id.pic_parent);
    TextView tvChangePic = mView.findViewById(R.id.tv_change_pic);
    tvChangePic.setOnClickListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("StudyPic"); //统计页面
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("StudyPic");
  }

  @Override
  public void onClick(View view) {
    changePic();
  }

  /**
   * 切换图形
   */
  private void changePic() {
    mPicParent.setRandomView();
  }
}
