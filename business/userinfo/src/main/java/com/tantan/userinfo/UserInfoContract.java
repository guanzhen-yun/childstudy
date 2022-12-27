package com.tantan.userinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.tantan.base.adapter.SexAdapter;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.ziroom.mvp.IMvpView;

public interface UserInfoContract {

  int CHANGE_ALBULM = 0;//修改头像
  int CHANGE_NAME = 1;//修改昵称
  int CHANGE_AGE = 2;//修改年龄
  int CHANGE_GENDER = 3;//修改性别

  int REQUEST_TAKEPHOTOS = 1;//打开相机拍照
  int REQUEST_ALBUMS = 2;//相册选择照片

  interface IView extends IMvpView {

    void setSexListView(SexAdapter adapter);

    void setUserInfo(UserInfoEntity currentUser);

    void changeUserInfoSuccess(int currentType);

    Context getContext();

    void setImageBitmap(Bitmap bitmap);
  }

  interface IPresenter {

    void setSexAdapter();

    void setCurrentSex(String sex);

    void getUserInfo();

    void updateUserInfo(int currentType);

    void addPhoto(Activity activity, View parentGroup);

    void loadImage(String imagePath);

    void changeNickname();

    void changeAge();

    void jumpBigHead();

  }
}
