package com.inke.childstudy.studyobject;

import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

/**
 * 查看大图页
 */
@Route(path = RouterConstants.App.BigPic)
public class BigPicActivity extends BaseActivity {

  @BindView(R.id.iv_pic)
  ImageView mIvPic;

  @Autowired(name = "imagePath")
  String imagePath;

  @Override
  public int getLayoutId() {
    return R.layout.activity_bighead;
  }

  @Override
  public void initViews() {
    displayImage(imagePath);
  }

  private void displayImage(String imagePath) {
    Glide.with(BigPicActivity.this).load(imagePath).into(mIvPic);
  }

  @OnClick(R.id.iv_pic)
  public void onClickView() {
    finish();
  }
}
