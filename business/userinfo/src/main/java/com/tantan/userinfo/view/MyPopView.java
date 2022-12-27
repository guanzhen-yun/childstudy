package com.tantan.userinfo.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import com.tantan.userinfo.R;
import com.tantan.userinfo.UserInfoContract;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyPopView extends PopupWindow implements View.OnClickListener {

  public Context mContext;
  public Activity mActivity;
  private File file;

  public MyPopView(Activity mActivity) {
    initView(mActivity);
    this.mActivity = mActivity;
  }

  @SuppressLint("InflateParams")
  private void initView(Context mContext) {
    this.mContext = mContext;
    View v = LayoutInflater.from(mContext).inflate(R.layout.view_popu,
        null);
    setContentView(v);

    TextView mTakePhoto = (TextView) v.findViewById(R.id.tv_photo_take);
    TextView mAlbumPhoto = (TextView) v.findViewById(R.id.tv_photo_album);
    TextView mCancel = (TextView) v.findViewById(R.id.tv_photo_cancel);

    mTakePhoto.setOnClickListener(this);
    mAlbumPhoto.setOnClickListener(this);
    mCancel.setOnClickListener(this);
    v.findViewById(R.id.cl_body).setOnClickListener(this);

    this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

    // 设置SelectPicPopupWindow弹出窗体可点
    this.setTouchable(true);
    this.setFocusable(true);
    this.setOutsideTouchable(true);
    this.setClippingEnabled(false);

    this.update();
    // 设置SelectPicPopupWindow弹出窗体动画效果
    this.setAnimationStyle(R.style.popuwindow_from_bottom);
    // 实例化一个ColorDrawable颜色为半透明
    ColorDrawable dw = new ColorDrawable(0x50000000);
    // 设置SelectPicPopupWindow弹出窗体的背景
    this.setBackgroundDrawable(dw);
  }

  public void showPopupWindow(View parent) {
    if (!this.isShowing()) {
      this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    } else {
      this.dismiss();
    }
  }

  @SuppressLint("QueryPermissionsNeeded")
  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.tv_photo_take) {
      Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
      if (takePhotoIntent.resolveActivity(mActivity.getPackageManager())
          != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        File imageFile = createImageFile();//创建用来保存照片的文件
        if (imageFile != null) {
          Uri imgUri;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            /*7.0以上要通过FileProvider将File转化为Uri*/
            imgUri = FileProvider.getUriForFile(mActivity, "com.inke.childstudy.fileprovider",
                imageFile);
          } else {
            /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
            imgUri = Uri.fromFile(imageFile);
          }
          file = imageFile;
          takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);//将用于输出的文件Uri传递给相机
          mActivity.startActivityForResult(takePhotoIntent,
              UserInfoContract.REQUEST_TAKEPHOTOS);//打开相机
        }
      }
      if (listener != null) {
        listener.getImgUri(file);
      }
      this.dismiss();
    } else if (id == R.id.tv_photo_album) {
      Intent intent2 = new Intent("android.intent.action.PICK");
      intent2.setType("image/*");
      mActivity.startActivityForResult(intent2, UserInfoContract.REQUEST_ALBUMS);
      this.dismiss();
    } else if (id == R.id.tv_photo_cancel || id == R.id.cl_body) {
      this.dismiss();
    }
  }

  private File createImageFile() {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File imageFile = null;
    try {
      imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imageFile;
  }

  public interface onGetTypeClickListener {

    void getImgUri(File file);
  }

  private onGetTypeClickListener listener;

  public void setOnGetTypeClickListener(onGetTypeClickListener listener) {
    this.listener = listener;
  }
}
