package com.tantan.userinfo;

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
import java.util.ArrayList;
import java.util.List;

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

  private SexAdapter mSexAdapter;
  private String mSelectSex;

  private int mChangeType;

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
    setSexAdapter();
    tvNickname = findViewById(R.id.tv_nickname);
    ivHead = findViewById(R.id.iv_head);
    tvAddphoto = findViewById(R.id.tv_addphoto);
    ivArrowRight = findViewById(R.id.iv_arrow_right);
  }

  private void setSexAdapter() {
    RecyclerView rvSex = findViewById(R.id.rv_sex);
    List<String> sexList = new ArrayList<>();
    sexList.add("男");
    sexList.add("女");
    mSexAdapter = new SexAdapter(sexList);
    mSexAdapter.setOnItemClickListener((adapter, view, position) -> {
      String sex = sexList.get(position);
      if (!sex.equals(mSelectSex)) {
        changeSex(sex);
      }
    });
    rvSex.setAdapter(mSexAdapter);
  }

  /**
   * 修改性别
   */
  private void changeSex(String sex) {
    mPresenter.getCurrentUser().setSex(sex);
    mChangeType = UserInfoContract.CHANGE_GENDER;
    mPresenter.updateUserInfo(mChangeType);
  }

  @Override
  public void initDatas() {
    mPresenter.getUserInfo();
  }

  private void setCurrentSex(String sex) {
    mSelectSex = sex;
    mSexAdapter.setSelectSex(sex);
    mSexAdapter.notifyItemRangeChanged(0, 2);
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();
    if (id == R.id.view_left) {//返回
      finish();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mSexAdapter.destroyAdapter();
  }

  @Override
  public void setUserInfo() {
    UserInfoEntity currentUser = mPresenter.getCurrentUser();
    String headPath = currentUser.getHeadPath();//头像
    String nick = currentUser.getNick();//昵称
    String sex = currentUser.getSex();//性别
    int age = currentUser.getAge();//年龄
    if (!TextUtils.isEmpty(headPath)) {
      ViewUtils.setViewsGone(tvAddphoto, ivArrowRight);
      ViewUtils.setViewsVisible(ivHead);
    }
    if (!TextUtils.isEmpty(nick)) {
      tvNickname.setText(nick);
    }
    if (!TextUtils.isEmpty(sex)) {
      setCurrentSex(sex);
    }
  }

  @Override
  public void changeUserInfoSuccess(int currentType) {
    switch (currentType) {
      case UserInfoContract.CHANGE_GENDER:
        setCurrentSex(mPresenter.getCurrentUser().getSex());
        break;
      default:
        break;
    }
  }
}
