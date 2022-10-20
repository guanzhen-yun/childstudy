package com.tantan.userinfo;

import com.ziroom.mvp.IMvpView;

public interface UserInfoContract {

  public static final int CHANGE_ALBULM = 0;//修改头像
  public static final int CHANGE_NAME = 1;//修改昵称
  public static final int CHANGE_AGE = 2;//修改年龄
  public static final int CHANGE_GENDER = 3;//修改性别

  interface IView extends IMvpView {

    void setUserInfo();

    void changeUserInfoSuccess(int currentType);
  }

  interface IPresenter {

    void getUserInfo();

    void updateUserInfo(int currentType);
  }
}
