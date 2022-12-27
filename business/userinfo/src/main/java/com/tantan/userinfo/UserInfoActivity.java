package com.tantan.userinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants.UserInfo;
import com.tantan.base.adapter.SexAdapter;
import com.tantan.base.utils.ViewUtils;
import com.tantan.base.view.ArrowRightView;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.ziroom.base.BaseActivity;
import java.util.Locale;

/**
 * 用户资料页
 */
@Route(path = UserInfo.Main)
public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements OnClickListener,
    UserInfoContract.IView {

  private TextView tvNickname;
  private ImageView ivHead;
  private TextView tvAddphoto;
  private ArrowRightView ivArrowRight;
  private TextView tvAgeValue;

  @Override
  public int getLayoutId() {
    return R.layout.activity_user_info;
  }

  @Override
  public UserInfoPresenter getPresenter() {
    return new UserInfoPresenter(this);
  }

  @Override
  public void initViews() {
    TextView tvTitle = findViewById(R.id.tv_title);
    tvTitle.setText("用户信息");
    findViewById(R.id.view_left).setOnClickListener(this);
    tvNickname = findViewById(R.id.tv_nickname);
    tvNickname.setOnClickListener(this);
    ivHead = findViewById(R.id.iv_head);
    ivHead.setOnClickListener(this);
    tvAddphoto = findViewById(R.id.tv_addphoto);
    ivArrowRight = findViewById(R.id.iv_arrow_right);
    tvAgeValue = findViewById(R.id.tv_age_value);
    tvAgeValue.setOnClickListener(this);
    findViewById(R.id.view_photo).setOnClickListener(this);
    mPresenter.setSexAdapter();
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void initDatas() {
    mPresenter.getUserInfo();
  }

  @Override
  public void setSexListView(SexAdapter adapter) {
    RecyclerView rvSex = findViewById(R.id.rv_sex);
    rvSex.setAdapter(adapter);
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();
    if (id == R.id.view_left) {//返回
      finish();
    } else if (id == R.id.view_photo) {//修改头像
      mPresenter.addPhoto(this, findViewById(R.id.cl_body));
    } else if (id == R.id.iv_head) {//查看大图
      mPresenter.jumpBigHead();
    } else if (id == R.id.tv_nickname) {//修改昵称
      mPresenter.changeNickname();
    } else if (id == R.id.tv_age_value) {//修改年龄
      mPresenter.changeAge();
    }
  }

  @Override
  public void setUserInfo(UserInfoEntity currentUser) {
    String headPath = currentUser.getHeadPath();//头像
    String nick = currentUser.getNick();//昵称
    String sex = currentUser.getSex();//性别
    int age = currentUser.getAge();//年龄
    if (!TextUtils.isEmpty(headPath)) {
      setHasUserPhoto(true);
      mPresenter.loadImage(headPath);
    }
    if (!TextUtils.isEmpty(nick)) {
      tvNickname.setText(nick);
    }
    if (!TextUtils.isEmpty(sex)) {
      mPresenter.setCurrentSex(sex);
    }
    tvAgeValue.setText(String.format(Locale.getDefault(), "%d岁", age));
  }

  private void setHasUserPhoto(boolean isHasPhoto) {
    if (isHasPhoto) {
      ViewUtils.setViewsGone(ivArrowRight);
      ViewUtils.setViewsVisible(ivHead);
      tvAddphoto.setText("修改头像");
    } else {
      ViewUtils.setViewsGone(ivArrowRight, ivHead);
    }
  }

  @Override
  public void changeUserInfoSuccess(int currentType) {
    switch (currentType) {
      case UserInfoContract.CHANGE_AGE:
        tvAgeValue.setText(
            String.format(Locale.getDefault(), "%d岁", mPresenter.getCurrentUser().getAge()));
        break;
      case UserInfoContract.CHANGE_NAME:
        tvNickname.setText(mPresenter.getCurrentUser().getNick());
        break;
      default:
        break;
    }
  }

  @Override
  public void setImageBitmap(Bitmap bitmap) {
    ivHead.setImageBitmap(bitmap);
    setHasUserPhoto(true);
  }
}
