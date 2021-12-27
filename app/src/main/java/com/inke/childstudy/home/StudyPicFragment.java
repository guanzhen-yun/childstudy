package com.inke.childstudy.home;

import android.view.View;

import com.inke.childstudy.R;
import com.inke.childstudy.view.picview.PicParentView;
import com.umeng.analytics.MobclickAgent;
import com.ziroom.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 学习图片
 */
public class StudyPicFragment extends BaseFragment {
    @BindView(R.id.pic_parent)
    PicParentView mPicParent;

    public static StudyPicFragment getInstance() {
        return new StudyPicFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_pic;
    }

    @OnClick({R.id.tv_change_pic})
    public void onClickView(View v) {
        changePic();
    }

    /**
     * 切换图形
     */
    private void changePic() {
        mPicParent.setRandomView();
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
}
