package com.tantan.userinfo;

import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.ToastUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils.OnDaoListener;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.tantan.userinfo.UserInfoContract.IView;
import com.ziroom.mvp.base.BaseMvpPresenter;

public class UserInfoPresenter extends BaseMvpPresenter<UserInfoContract.IView> implements
    UserInfoContract.IPresenter {

  private UserInfoEntity currentUser;

  public UserInfoPresenter(IView view) {
    super(view);
  }

  @Override
  public void getUserInfo() {
    currentUser = DataUtils.getCurrentUser(
        SharedPrefUtils.getInstance().getLastedMobile());
    if (currentUser != null) {
      mView.setUserInfo();
    }
  }

  @Override
  public void updateUserInfo(int currentType) {
    DataUtils.updateUserInfo(currentUser, new OnDaoListener() {
      @Override
      public void onSuccess() {
        ToastUtils.showToast("修改成功");
        mView.changeUserInfoSuccess(currentType);
      }

      @Override
      public void onError(String err) {
        ToastUtils.showToast(err);
      }
    });
  }

  public UserInfoEntity getCurrentUser() {
    return currentUser;
  }
}
