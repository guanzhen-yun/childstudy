package com.inke.childstudy.userinfo;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.UserInfo;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.view.pop.MyPopView;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 个人信息页
 */

@Route(path = RouterConstants.App.UserInfo)
public class UserInfoActivity extends BaseActivity {
    private File file;

    private MyPopView puWindow;

    @BindView(R.id.iv_head)
    ImageView mIvHead;

    private String mObjectId;
    private UserInfo mUserInfo;
    private String mPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initDatas() {
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();
        String loginToken = SharedPrefUtils.getInstance().getLoginToken();
        bmobQuery.addWhereEqualTo("token", loginToken);
        bmobQuery.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e != null) {
                    if (e.getErrorCode() == 101) {
                        mIvHead.setVisibility(View.GONE);
                        mUserInfo = new UserInfo();
                        mUserInfo.setToken(loginToken);
                        mUserInfo.setHeadPath("");
                        mUserInfo.setSex("");
                        BmobUtils.getInstance().addData(mUserInfo, new BmobUtils.OnBmobListener() {
                            @Override
                            public void onSuccess(String objectId) {
                                mObjectId = objectId;
                            }

                            @Override
                            public void onError(String err) {
                                ToastUtils.showToast(err);
                            }
                        });
                    } else {
                        ToastUtils.showToast(e.getMessage());
                    }
                } else if (list != null && list.size() > 0) {
                    mUserInfo = list.get(0);
                    mObjectId = mUserInfo.getObjectId();
                    String head = mUserInfo.getHeadPath();
                    mPath = head;
                    Bitmap bitmap = BitmapFactory.decodeFile(head);
                    if (bitmap != null) {
                        mIvHead.setImageBitmap(bitmap);
                    }
                } else {
                    mIvHead.setVisibility(View.GONE);
                    mUserInfo = new UserInfo();
                    mUserInfo.setToken(loginToken);
                    mUserInfo.setHeadPath("");
                    mUserInfo.setSex("");
                    BmobUtils.getInstance().addData(mUserInfo, new BmobUtils.OnBmobListener() {
                        @Override
                        public void onSuccess(String objectId) {
                            mObjectId = objectId;
                        }

                        @Override
                        public void onError(String err) {
                            ToastUtils.showToast(err);
                        }
                    });
                }
            }
        });
    }

    @OnClick({R.id.tv_addphoto, R.id.iv_head})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_addphoto:
                addPhoto();
                break;
            case R.id.iv_head:
                Bundle bundle = new Bundle();
                bundle.putString("imagePath", mPath);
                RouterUtils.jump(RouterConstants.App.BigHead, bundle);
                break;
            default:
                break;
        }
    }

    private void addPhoto() {
        puWindow = new MyPopView(UserInfoActivity.this);
        puWindow.showPopupWindow(findViewById(R.id.rl_body));
        puWindow.setOnGetTypeClckListener(new MyPopView.onGetTypeClckListener() {

            @Override
            public void getImgUri(File file) {
                UserInfoActivity.this.file = file;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            changeHeadPhoto(file.getPath());
        } else if (requestCode == 2) {
            if (data != null) {
                if (Build.VERSION.SDK_INT >= 19) {
                    // 4.4及以上系统使用这个方法处理图片
                    handleImageOnKitKat(data);
                } else {
                    // 4.4以下系统使用这个方法处理图片
                    handleImageBeforeKitKat(data);
                }
            }
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        changeHeadPhoto(imagePath);
    }

    /**
     * android 4.4以前的处理方式
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mIvHead.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeHeadPhoto(String headPath) {
        mUserInfo.setHeadPath(headPath);
        mPath = headPath;
        BmobUtils.getInstance().updateBmobDate(mObjectId, mUserInfo, new BmobUtils.OnBmobListener() {

            @Override
            public void onSuccess(String objectId) {
                displayImage(headPath);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(err + "修改失败");
            }
        });
    }
}
