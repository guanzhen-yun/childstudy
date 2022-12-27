package com.tantan.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import com.tantan.base.RouterConstants;
import com.tantan.base.adapter.SexAdapter;
import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.ToastUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils.OnDaoListener;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.tantan.userinfo.UserInfoContract.IView;
import com.tantan.userinfo.utils.PhotoUtils;
import com.tantan.userinfo.view.MyPopView;
import com.tantan.userinfo.view.dialog.BottomAgeDialog;
import com.tantan.userinfo.view.dialog.BottomEditDialog;
import com.ziroom.base.RouterUtils;
import com.ziroom.mvp.base.BaseMvpPresenter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInfoPresenter extends BaseMvpPresenter<UserInfoContract.IView> implements
    UserInfoContract.IPresenter {

  private UserInfoEntity currentUser;

  private SexAdapter mSexAdapter;
  private File file;
  private boolean isFirstSetPhoto;

  public UserInfoPresenter(IView view) {
    super(view);
  }

  @Override
  public void setSexAdapter() {
    List<String> sexList = new ArrayList<>();
    sexList.add("男");
    sexList.add("女");
    mSexAdapter = new SexAdapter(sexList);
    mSexAdapter.setOnItemClickListener((adapter, view, position) -> {
      String sex = sexList.get(position);
      if (!sex.equals(mSexAdapter.getSelectSex())) {
        currentUser.setSex(sex);
        updateUserInfo(UserInfoContract.CHANGE_GENDER);
      }
    });
    mView.setSexListView(mSexAdapter);
  }

  @Override
  public void setCurrentSex(String sex) {
    mSexAdapter.setSelectSex(sex);
    mSexAdapter.notifyItemRangeChanged(0, 2);
  }

  @Override
  public void getUserInfo() {
    currentUser = DataUtils.getCurrentUser(
        SharedPrefUtils.getInstance().getLastedMobile());
    if (currentUser != null) {
      isFirstSetPhoto = TextUtils.isEmpty(currentUser.getHeadPath());
      mView.setUserInfo(currentUser);
    }
  }

  @Override
  public void updateUserInfo(int currentType) {
    DataUtils.updateUserInfo(currentUser, new OnDaoListener() {
      @Override
      public void onSuccess() {
        if (currentType == UserInfoContract.CHANGE_GENDER) {
          ToastUtils.showToast("修改性别成功");
          setCurrentSex(currentUser.getSex());
        } else if (currentType == UserInfoContract.CHANGE_ALBULM) {
          if (isFirstSetPhoto) {
            ToastUtils.showToast("设置头像成功");
          } else {
            ToastUtils.showToast("修改头像成功");
          }
          isFirstSetPhoto = false;
          loadImage(currentUser.getHeadPath());
        } else {
          if (currentType == UserInfoContract.CHANGE_NAME) {
            ToastUtils.showToast("修改昵称成功");
          } else {
            ToastUtils.showToast("修改年龄成功");
          }
          mView.changeUserInfoSuccess(currentType);
        }
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

  @Override
  public void addPhoto(Activity activity, View parentGroup) {
    MyPopView puWindow = new MyPopView(activity);
    puWindow.showPopupWindow(parentGroup);
    puWindow.setOnGetTypeClickListener(file -> this.file = file);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mSexAdapter.destroyAdapter();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == UserInfoContract.REQUEST_TAKEPHOTOS) {
        changeHeadPhoto(file.getPath());
      } else if (requestCode == UserInfoContract.REQUEST_ALBUMS) {
        if (data != null) {
          String imagePath = PhotoUtils.getAlbumPath(mView.getContext(), data);
          // 根据图片路径显示图片
          changeHeadPhoto(imagePath);
        }
      }
    }
  }

  private void changeHeadPhoto(String headPath) {
    currentUser.setHeadPath(headPath);
    updateUserInfo(UserInfoContract.CHANGE_ALBULM);
  }

  @Override
  public void loadImage(String imagePath) {
    if (imagePath != null) {
      Thread thread = new Thread(() -> {
        Message message = new Message();
        message.obj = PhotoUtils.loadCompletePic(imagePath);
        handler.sendMessage(message);
      });
      thread.start();
    } else {
      ToastUtils.showToast("获取相册图片失败");
    }
  }

  Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      mView.setImageBitmap((Bitmap) msg.obj);
    }
  };

  @Override
  public void changeNickname() {
    BottomEditDialog bottomDialog = new BottomEditDialog(mView.getContext());
    bottomDialog.setCurrentNickName(currentUser.getNick());
    bottomDialog.setOnChangeListener(nick -> {
      currentUser.setNick(nick);
      updateUserInfo(UserInfoContract.CHANGE_NAME);
    });
    bottomDialog.show();
  }

  @Override
  public void changeAge() {
    BottomAgeDialog dialog = new BottomAgeDialog(mView.getContext());
    dialog.setCurrentAge(currentUser.getAge());
    dialog.setOnSelectListener(age -> {
      if (!TextUtils.isEmpty(age)) {
        currentUser.setAge(Integer.parseInt(age.replace("岁", "")));
        updateUserInfo(UserInfoContract.CHANGE_AGE);
      }
    });
    dialog.show();
  }

  @Override
  public void jumpBigHead() {
    Bundle bundle = new Bundle();
    bundle.putString("imagePath", currentUser.getHeadPath());
    RouterUtils.jump(RouterConstants.UserInfo.BigHead, bundle);
  }
}
