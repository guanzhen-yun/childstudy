package com.tantan.login.login;

import com.tantan.mydata.greendao.UserInfoEntity;
import com.ziroom.mvp.IMvpView;

public interface LoginContract {

  interface IView extends IMvpView {

    void loginSuccess(boolean isParent);
  }

  interface IPresenter {

    void login(UserInfoEntity userInfo);
  }
}
