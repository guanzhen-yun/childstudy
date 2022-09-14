package com.tantan.login.regist;

import com.tantan.mydata.greendao.UserInfoEntity;
import com.ziroom.mvp.IMvpView;

public interface RegistContract {

  interface IView extends IMvpView {

    void registSuccess();
  }

  interface IPresenter {

    void regist(UserInfoEntity userInfo);
  }
}
