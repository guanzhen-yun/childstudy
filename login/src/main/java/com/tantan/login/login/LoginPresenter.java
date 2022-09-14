package com.tantan.login.login;

import com.tantan.login.login.LoginContract.IView;
import com.ziroom.mvp.base.BaseMvpPresenter;

public class LoginPresenter extends BaseMvpPresenter<LoginContract.IView> implements
    LoginContract.IPresenter {

  public LoginPresenter(IView view) {
    super(view);
  }
}