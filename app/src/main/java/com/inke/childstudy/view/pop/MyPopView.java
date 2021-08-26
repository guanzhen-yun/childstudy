package com.inke.childstudy.view.pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.inke.childstudy.R;
import com.inke.childstudy.userinfo.UserInfoActivity.Type;
import com.inke.childstudy.utils.ScreenUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPopView extends PopupWindow implements View.OnClickListener {
    public Context mContext;
    private Type type;

    public Activity mActivity;

    private File file;
    private Uri ImgUri;

    private TextView mTakePhoto, mAlbumPhoto, mCancel;

    public MyPopView(Activity mActivity) {
        initView(mActivity);
        this.mActivity = mActivity;
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_popu,
                null);
        setContentView(v);

        mTakePhoto = (TextView) v.findViewById(R.id.photo_take);
        mAlbumPhoto = (TextView) v.findViewById(R.id.photo_album);
        mCancel = (TextView) v.findViewById(R.id.photo_cancel);

        mTakePhoto.setOnClickListener(this);
        mAlbumPhoto.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ScreenUtils.getScreenHeight(mContext));

        // 设置SelectPicPopupWindow弹出窗体可点
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_take:
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
                if(takePhotoIntent.resolveActivity(mActivity.getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
                    File imageFile = createImageFile();//创建用来保存照片的文件
                    if(imageFile!=null){
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                            /*7.0以上要通过FileProvider将File转化为Uri*/
                            ImgUri = FileProvider.getUriForFile(mActivity,"com.inke.childstudy.fileprovider",imageFile);
                        }else {
                            /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                            ImgUri = Uri.fromFile(imageFile);
                        }
                        file = imageFile;
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,ImgUri);//将用于输出的文件Uri传递给相机
                        mActivity.startActivityForResult(takePhotoIntent, 1);//打开相机
                    }
                }
                type = Type.CAMERA;
                if (listener != null) {
                    listener.getType(type);
                    listener.getImgUri(ImgUri, file);
                }
                this.dismiss();
                break;
            case R.id.photo_album:
                Intent intent2 = new Intent("android.intent.action.PICK");
                intent2.setType("image/*");
                mActivity.startActivityForResult(intent2, 2);
                type = Type.PHONE;
                if (listener != null) {
                    listener.getType(type);
                }
                this.dismiss();
                break;
            case R.id.photo_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public void onPhoto(Uri uri, int outputX, int outputY) {
        Intent intent = null;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        mActivity.startActivityForResult(intent, 3);
    }

    public void onPhotoNew(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
//7.0之后 data 限制大小为1M  所以不能为true 7.0 之前还是以前的方式获取 数据
        intent.putExtra("return-data", false);
        // 此处的URI必须和 原来的URI 一致 不能为新的URI  否则 系统提示 图片无效 或者 无效连接
        // 只需要和数据源URI 一致即可
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        mActivity.startActivityForResult(intent, 3);//这里的RESULT_REQUEST_CODE是在startActivityForResult里使用的返回值。
    }

    public interface onGetTypeClckListener {
        void getType(Type type);

        void getImgUri(Uri ImgUri, File file);
    }

    private onGetTypeClckListener listener;

    public void setOnGetTypeClckListener(onGetTypeClckListener listener) {
        this.listener = listener;
    }
}
